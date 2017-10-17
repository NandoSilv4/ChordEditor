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

        chordsALL=Pattern.compile("C♭").matcher(chordsALL).replaceAll("B");
        chordsALL=Pattern.compile("D♭").matcher(chordsALL).replaceAll("C#");
        chordsALL=Pattern.compile("E♭").matcher(chordsALL).replaceAll("D#");
        chordsALL=Pattern.compile("F♭").matcher(chordsALL).replaceAll("E");
        chordsALL=Pattern.compile("G♭").matcher(chordsALL).replaceAll("F#");
        chordsALL=Pattern.compile("A♭").matcher(chordsALL).replaceAll("G#");
        chordsALL=Pattern.compile("B♭").matcher(chordsALL).replaceAll("A#");


        return chordsALL;
    }





    public String HalfUpDown(String text,int count){

        while(count<0){//ハーフダウンの場合
            count=12+count;
        }

        for(int i=0; i<count; i++) {
            //まず間違いがあったら訂正する
            text = Pattern.compile("B#").matcher(text).replaceAll("C");
            text = Pattern.compile("E#").matcher(text).replaceAll("F");
            //2回変換することがないように、とりあえずBをいったんXに置き換え
            text = Pattern.compile("B").matcher(text).replaceAll("X");
            text = Pattern.compile("A#").matcher(text).replaceAll("B");
            text = Pattern.compile("A").matcher(text).replaceAll("A#");
            text = Pattern.compile("G#").matcher(text).replaceAll("A");
            text = Pattern.compile("G").matcher(text).replaceAll("G#");
            text = Pattern.compile("F#").matcher(text).replaceAll("G");
            text = Pattern.compile("F").matcher(text).replaceAll("F#");
            text = Pattern.compile("E").matcher(text).replaceAll("F");
            text = Pattern.compile("D#").matcher(text).replaceAll("E");
            text = Pattern.compile("D").matcher(text).replaceAll("D#");
            text = Pattern.compile("C#").matcher(text).replaceAll("D");
            text = Pattern.compile("C").matcher(text).replaceAll("C#");
            //XをCに戻す
            text = Pattern.compile("X").matcher(text).replaceAll("C");
        }

        return text;
    }





    public String HalfUpDownEdit(String text,int count){

        while(count<0){//ハーフダウンの場合
            count=12+count;
        }

        for(int i=0; i<count; i++) {
            //まず間違いがあったら訂正する
            text = Pattern.compile("\\|B#").matcher(text).replaceAll("|C");
            text = Pattern.compile("\\|E#").matcher(text).replaceAll("|F");
            //2回変換することがないように、とりあえずBをいったんXに置き換え
            text = Pattern.compile("\\|B").matcher(text).replaceAll("|X");
            text = Pattern.compile("\\|A#").matcher(text).replaceAll("|B");
            text = Pattern.compile("\\|A").matcher(text).replaceAll("|A#");
            text = Pattern.compile("\\|G#").matcher(text).replaceAll("|A");
            text = Pattern.compile("\\|G").matcher(text).replaceAll("|G#");
            text = Pattern.compile("\\|F#").matcher(text).replaceAll("|G");
            text = Pattern.compile("\\|F").matcher(text).replaceAll("|F#");
            text = Pattern.compile("\\|E").matcher(text).replaceAll("|F");
            text = Pattern.compile("\\|D#").matcher(text).replaceAll("|E");
            text = Pattern.compile("\\|D").matcher(text).replaceAll("|D#");
            text = Pattern.compile("\\|C#").matcher(text).replaceAll("|D");
            text = Pattern.compile("\\|C").matcher(text).replaceAll("|C#");
            //XをCに戻す
            text = Pattern.compile("\\|X").matcher(text).replaceAll("|C");
        }

        return text;
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










    public String ToRomanNumeral(String text){
        text=Pattern.compile("C").matcher(text).replaceAll("Ⅰ");
        text=Pattern.compile("D").matcher(text).replaceAll("Ⅱ");
        text=Pattern.compile("E").matcher(text).replaceAll("Ⅲ");
        text=Pattern.compile("F").matcher(text).replaceAll("Ⅳ");
        text=Pattern.compile("G").matcher(text).replaceAll("Ⅴ");
        text=Pattern.compile("A").matcher(text).replaceAll("Ⅵ");
        text=Pattern.compile("B").matcher(text).replaceAll("Ⅶ");
        text=Pattern.compile("#").matcher(text).replaceAll("+");
        return text;
    }




}