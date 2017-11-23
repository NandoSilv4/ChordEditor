package com.sakamalab.yutauenishi.chordeditor;



import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.support.v4.app.Fragment;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FragmentTab extends Fragment {

    public static FragmentTab newInstance(String data,String Key,int index) {
        Bundle args = new Bundle();
        args.putString("data",data);
        args.putString("key",Key);
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
        String Key = args.getString("key");
        int index = args.getInt("index");
        int flag=0;

        if(data!=null) {
            String[] data_split = data.split("\\n", 0);
            String lyrics = "";
            String lyricsALL = "";
            String chords = "";
            String comment = "";
            String start="<\\$";
            String finish="\\$>";


            LinearLayout layout = (LinearLayout) view.findViewById(R.id.layout);



            //Key=・GetFirstChordの表示
            if(index==2){
                TextView key_text = new TextView(getActivity());
                key_text.setTextSize(16);
                key_text.setTextColor(Color.DKGRAY);
                key_text.setText(String.format("%s", Key));
                layout.addView(key_text);
           }



            for (int i = 0; i < data_split.length; i++) {



                // ||???||を|start ??? finish|に変更してバグを防ぐ
                data_split[i] = Pattern.compile("\\|:(.*?):\\|").matcher(data_split[i]).replaceAll("\\|"+start+"$1"+finish+"\\|");

                // { Curly Bracketだけを探す
                Matcher mcb1 = Pattern.compile("^\\{").matcher(data_split[i]);
                if(mcb1.find()){
                    flag=1;
                    data_split[i]=mcb1.replaceAll("");
                }

                // } Curly Bracketだけを探す
                Matcher mcb2 = Pattern.compile("\\}").matcher(data_split[i]);
                if(mcb2.find()){
                    flag=-1;
                    data_split[i]=mcb2.replaceAll("");
                }


                String ls;
                int f=0;



                //[]だけを探す(Aメロとかのために)
                Pattern p2 = Pattern.compile("^\\[.*?\\]");

                //||だけを探す(歌詞+コードの*をつけるため)
                Pattern p3 = Pattern.compile("\\|.*?\\|");

                //::だけを探す
                Pattern p5 = Pattern.compile("::");
                //||?||だけを探す
                Pattern p6 = Pattern.compile("\\|"+start+"(.*?)"+finish+"\\|");

                String junk;
                junk=p5.matcher(data_split[i]).replaceAll("|");
                junk=p6.matcher(junk).replaceAll("");
                lyricsALL = lyricsALL + Pattern.compile("[|\\[].*?[|\\]]").matcher(junk).replaceAll("");

                Matcher m4 = p2.matcher(data_split[i]);
                if(m4.find()&&m4.replaceAll("").trim().equals("")){
                    f=1;
                }

                ls = m4.replaceAll("");
                ls = p5.matcher(ls).replaceAll("|");
                ls = p6.matcher(ls).replaceAll("|$1|");
                Matcher m3 = p3.matcher(ls);


            /*//前奏の****にならないように
                if(m3.find()&&m3.replaceAll("").trim().equals("")){
                    lyrics="";
                    f=1;
                }else {
                    lyrics = lyrics + m3.replaceAll("*");
                }
                */


                if(!(m3.find()&&m3.replaceAll("").trim().equals(""))){
                    lyrics = lyrics + m3.replaceAll("*");
                }


                //[]と||の両方を探す
                Matcher m = Pattern.compile("[|\\[].*?[|\\]]").matcher(Pattern.compile("::").matcher(data_split[i]).replaceAll(":<|>:"));
                while (m.find()) {
                    Matcher m2 = Pattern.compile("^\\[.*?\\]").matcher(m.group());
                    if (m2.find()) {
                        comment=m2.group();
                    } else {
                        chords = chords + " | " + m.group().substring(1, m.group().length() - 1);
                    }
                }



                chords = Pattern.compile(":<.*?>:").matcher(chords).replaceAll(" , ");

                if(flag==-1)flag=0;

                if(flag==0) {

                    if (!(chords.equals(""))) {
                        chords = chords + " |";
                    }
                    chords = Pattern.compile(start+"(.*?)"+finish).matcher(chords).replaceAll(" $1 (x2) ");

                    TextView textView = new TextView(getActivity());
                    textView.setTextSize(20);
                    TextView chordsText = new TextView(getActivity());
                    chordsText.setTextSize(20);
                    chordsText.setTextColor(Color.BLUE);
                    TextView Tcomment = new TextView(getActivity());
                    Tcomment.setTextSize(22);
                    Tcomment.setTextColor(Color.BLACK);
                    switch (index) {
                        case 0:
                            Tcomment.setText(String.format("%s", comment));
                            chordsText.setText(String.format("%s", chords));
                            textView.setText(String.format("%s", lyrics));
                            if (!(comment.equals(""))) layout.addView(Tcomment);
                            if (!(chords.equals(""))) layout.addView(chordsText);
                            if(f==0)layout.addView(textView);
                            break;
                        case 1:
                            Tcomment.setText(String.format("%s", comment));
                            textView.setText(String.format("%s", lyricsALL));
                            if (!(comment.equals(""))) layout.addView(Tcomment);
                            if(f==0)layout.addView(textView);
                            break;
                        case 2:
                            Tcomment.setText(String.format("%s", comment));
                            chordsText.setText(String.format("%s", chords));
                            if (!(comment.equals(""))) {
                                layout.addView(Tcomment);
                            }
                            if (!(chords.equals(""))) {
                                layout.addView(chordsText);
                            }
                            break;
                    }


                    lyrics = "";
                    lyricsALL = "";
                    chords = "";
                    comment = "";
                }else{
                    lyrics=lyrics+"\n";
                    lyricsALL = lyricsALL+"\n";
                }

            }

        }

        return view;
    }
}
