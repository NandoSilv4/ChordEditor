package com.example.yutauenishi.chordeditor;


import android.support.v7.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AnalysisChords extends AppCompatActivity {


    public String GetChords(String text){

        String regex3 = "\\|.*?\\|";
        Pattern p3 = Pattern.compile(regex3);
        String chords;
        String chordsALL = "";
        if(text!=null) {
            String[] data_split = text.split("\\n", 0);
            for (String data_s : data_split) {
                chords="";
                //||だけを探す(歌詞+コードの*をつけるため)
                Matcher m3 = p3.matcher(data_s);
                while (m3.find()){
                    chords=chords+m3.group().substring(1, m3.group().length() - 1)+",";
                }
                if(!(chords.equals("")))chordsALL=chordsALL+chords+"\n";
            }
        }
        return chordsALL;
    }






    public String Counter(String text,String target){
        String return_s = "";
        int num=0;
        text=Pattern.compile("\\n").matcher(text).replaceAll("");

        if(text!=null) {

            String[] data_split = text.split(",", 0);
            String t_chord = "^" + target + ".*";
            Pattern p_t= Pattern.compile(t_chord);

            for(String data_s : data_split){
                Matcher m_t = p_t.matcher(data_s);
                if (m_t.find())num++;
            }
            return_s= target+ " : " + String.valueOf(num)+"回";
        }

        return return_s;
    }







}