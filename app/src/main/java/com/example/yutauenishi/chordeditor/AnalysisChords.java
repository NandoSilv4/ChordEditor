package com.example.yutauenishi.chordeditor;


import android.support.v7.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AnalysisChords extends AppCompatActivity {


    public String GetChords(String text){

        Pattern p3 = Pattern.compile("\\|.*?\\|");
        String chords;
        String chordsALL = "";
        if(text!=null) {
            String[] data_split = text.split("\\n", 0);
            for (String data_s : data_split) {
                chords="";
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
        String s="\\[\\$";
        String f="\\$\\]";

        text = Pattern.compile("\\|(.*?)\\|").matcher(text).replaceAll(s+"$1"+f);
        for(int i=0; i<count; i++) {
            //まず間違いがあったら訂正する  必要なかった
            //text = Pattern.compile("\\|B#(.*?)\\|").matcher(text).replaceAll("\\|C$1\\|");
            //text = Pattern.compile("\\|E(.*?)\\|").matcher(text).replaceAll("|F");
            //2回変換することがないように、とりあえずBをいったんXに置き換え
            text = Pattern.compile(s+"B(.*?)"+f).matcher(text).replaceAll(s+"X$1"+f);
            text = Pattern.compile(s+"A#(.*?)"+f).matcher(text).replaceAll(s+"B$1"+f);
            text = Pattern.compile(s+"A(.*?)"+f).matcher(text).replaceAll(s+"A#$1"+f);
            text = Pattern.compile(s+"G#(.*?)"+f).matcher(text).replaceAll(s+"A$1"+f);
            text = Pattern.compile(s+"G(.*?)"+f).matcher(text).replaceAll(s+"G#$1"+f);
            text = Pattern.compile(s+"F#(.*?)"+f).matcher(text).replaceAll(s+"G$1"+f);
            text = Pattern.compile(s+"F(.*?)"+f).matcher(text).replaceAll(s+"F#$1"+f);
            text = Pattern.compile(s+"E(.*?)"+f).matcher(text).replaceAll(s+"F$1"+f);
            text = Pattern.compile(s+"D#(.*?)"+f).matcher(text).replaceAll(s+"E$1"+f);
            text = Pattern.compile(s+"D(.*?)"+f).matcher(text).replaceAll(s+"D#$1"+f);
            text = Pattern.compile(s+"C#(.*?)"+f).matcher(text).replaceAll(s+"D$1"+f);
            text = Pattern.compile(s+"C(.*?)"+f).matcher(text).replaceAll(s+"C#$1"+f);
            //XをCに戻す
            text = Pattern.compile(s+"X(.*?)"+f).matcher(text).replaceAll(s+"C$1"+f);

        }
        text = Pattern.compile(s+"(.*?)"+f).matcher(text).replaceAll("\\|$1\\|");

        return text;
    }





    public String FindKey(String text){
        String return_s;
        int num=0;
        int C,D,E,F,G,A;
        int sum[] = {0, 0, 0, 0, 0,0,0,0,0,0,0,0};
        String Key[] = {"C", "C#","D","D#","E","F","F#","G","G#","A","A#","B"};
        int max=0;
        int max_i=-1;
        int sub_max=0;

        for(int i=0;i<12;i++) {
            C = Counter(text, "C",0) - Counter(text, "C#",0);
            D = Counter(text, "D",0) - Counter(text, "D#",0);
            E = Counter(text, "E",0);
            F = Counter(text, "F",0) - Counter(text, "F#",0);
            G = Counter(text, "G",0) - Counter(text, "G#",0);
            A = Counter(text, "A",0) - Counter(text, "A#",0);
            sum[i] = C + D + E + F + G + A;
            if(max<sum[i]){
                max=sum[i];
                max_i=i;
            }else if(max==sum[i]){
                sub_max=sum[i];
            }
            text=HalfUpDown(text,-1);
        }


        if(sub_max==max){
            //解析不能
            return_s="Key=?";
        }else{
            return_s="Key="+Key[max_i];//12-max_iカポするとCで弾ける
        }

        return return_s;
    }







    public int Counter(String text,String target,int f){//f=1完全一致f=0頭一致
        int num=0;
        text=Pattern.compile("\\n").matcher(text).replaceAll("");
        if(text!=null) {
            String[] data_split = text.split(",", 0);
            Pattern p_t;
            switch (f){
                case 0:
                    p_t= Pattern.compile("^" + target + ".*");
                    break;
                case 1:
                    p_t= Pattern.compile("^" + target + "$");
                    break;
                default:
                    return num;
            }
            for(String data_s : data_split){
                Matcher m_t = p_t.matcher(data_s);
                if (m_t.find())num++;
            }
        }
        return num;
    }




    public String UsedChord(String text){
        String result="";
        int num;
        int con=0;
        text=Pattern.compile("\\n").matcher(text).replaceAll("");

        if(text!=null) {
            text=","+text;
            while(con<100&&!(Pattern.compile(",").matcher(text).replaceAll("").trim().equals(""))) {
                con++;
                String[] data_split = text.split(",", 0);
                    if(data_split[1].equals(""))continue;//[0]は""が入ってる。,C,D,E....だから
                        num = Counter(text, data_split[1],1);
                        result = result + "|" + data_split[1] + "{+}" + num;
                        text = Pattern.compile(",("+data_split[1]+",)+").matcher(text).replaceAll(",");

            }
        }
        return result;
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