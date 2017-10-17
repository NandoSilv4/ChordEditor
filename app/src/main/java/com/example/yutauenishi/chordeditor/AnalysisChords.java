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
            for (int i = 0; i < data_split.length; i++) {
                chords="";
                //||だけを探す(歌詞+コードの*をつけるため)
                Matcher m3 = p3.matcher(data_split[i]);
                while (m3.find()){
                    chords=chords+m3.group().substring(1, m3.group().length() - 1)+", ";
                }
                if(!(chords.equals("")))chordsALL=chordsALL+chords+"\n";
            }
        }
        return chordsALL;
    }





}