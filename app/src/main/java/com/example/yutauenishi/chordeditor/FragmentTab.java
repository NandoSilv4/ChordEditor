package com.example.yutauenishi.chordeditor;

/**
 * Created by YutaUenishi on 2017/09/21.
 */


import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.support.v4.app.Fragment;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.ViewGroup;
import android.view.LayoutInflater;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FragmentTab extends Fragment {

    public static FragmentTab newInstance(int index,String data) {
        Bundle args = new Bundle();
        args.putString("data",data);
        args.putInt("index",index);
        FragmentTab fragment = new FragmentTab();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tab,container,false);
        Bundle args = getArguments();
        String data = args.getString("data");
        int index = args.getInt("index");
        int flag=0;

        if(data!=null) {
            String[] data_split = data.split("\\n", 0);
            String lyrics = "";
            String lyricsALL = "";
            String chords = "";
            String chordsALL = "";

            for (int i = 0; i < data_split.length; i++) {


                // { Curly Bracketだけを探す
                String cb1 = "^\\{";
                Pattern pcb1 = Pattern.compile(cb1);
                Matcher mcb1 = pcb1.matcher(data_split[i]);
                if(mcb1.find()){
                    flag=1;
                    data_split[i]=mcb1.replaceAll("");
                }

                // } Curly Bracketだけを探す
                String cb2 = "\\}$";
                Pattern pcb2 = Pattern.compile(cb2);
                Matcher mcb2 = pcb2.matcher(data_split[i]);
                if(mcb2.find()){
                    flag=0;
                    data_split[i]=mcb2.replaceAll("");
                }


                //[]と||の両方を探す
                String regex = "[\\|,\\[].*?[\\|,\\]]";
                Pattern p = Pattern.compile(regex);
                Matcher m = p.matcher(data_split[i]);

                //[]だけを探す(Aメロとかのために)
                String regex2 = "\\[.*?\\]";
                Pattern p2 = Pattern.compile(regex2);

                //||だけを探す(歌詞+コードの*をつけるため)
                String regex3 = "\\|.*?\\|";
                Pattern p3 = Pattern.compile(regex3);
                Matcher m3 = p3.matcher(data_split[i]);
                lyrics = lyrics + m3.replaceAll("*");
                lyricsALL = lyricsALL + m3.replaceAll("");

                while (m.find()) {
                    Matcher m2 = p2.matcher(m.group());
                    if (m2.find()) {
                        chordsALL = chordsALL + m2.group();//直接textviewで中央寄席設定したりで表示するのもあり
                    } else {
                        chords = chords + " | " + m.group().substring(1, m.group().length() - 1);
                        chordsALL = chordsALL + " | " + m.group().substring(1, m.group().length() - 1);
                    }
                }



                if(flag==0) {

                    if (!(chords.equals(""))) {
                        chords = chords + " |";
                        chordsALL = chordsALL + " |";
                    }
                    LinearLayout layout = (LinearLayout) view.findViewById(R.id.layout);
                    TextView textView = new TextView(getActivity());
                    textView.setTextSize(16);
                    TextView chordsText = new TextView(getActivity());
                    chordsText.setTextSize(16);
                    chordsText.setTextColor(Color.BLUE);
                    switch (index) {
                        case 0:
                            chordsText.setText(String.format("%s", chords));
                            if (!(chords.equals(""))) layout.addView(chordsText);
                            textView.setText(String.format("%s", lyrics));
                            layout.addView(textView);
                            break;
                        case 1:
                            textView.setText(String.format("%s", lyricsALL));
                            layout.addView(textView);
                            break;
                        case 2:
                            textView.setText(String.format("%s", chordsALL));
                            //if (!(chordsALL.equals("")))layout.addView(textView);????
                            layout.addView(textView);
                            break;
                    }


                    lyrics = "";
                    lyricsALL = "";
                    chords = "";
                    chordsALL = "";
                }else{
                    lyrics=lyrics+"\n";
                    lyricsALL = lyricsALL+"\n";
                }

            }

        }





        //((TextView) view.findViewById(R.id.text1)).setText(data);
        return view;
    }
}
