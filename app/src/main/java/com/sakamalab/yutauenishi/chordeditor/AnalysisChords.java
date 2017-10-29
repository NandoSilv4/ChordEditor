package com.sakamalab.yutauenishi.chordeditor;


import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AnalysisChords extends AppCompatActivity {



    //歌詞+コードからコードだけを抜き出す。
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




    //コード解析するさいの＃など
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




    //エディットページの#などの実装
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
            text = Pattern.compile(s+"(.*?)B(.*?)"+f).matcher(text).replaceAll(s+"$1X$2"+f);
            text = Pattern.compile(s+"(.*?)A#(.*?)"+f).matcher(text).replaceAll(s+"$1B$2"+f);
            text = Pattern.compile(s+"(.*?)A(.*?)"+f).matcher(text).replaceAll(s+"$1A#$2"+f);
            text = Pattern.compile(s+"(.*?)G#(.*?)"+f).matcher(text).replaceAll(s+"$1A$2"+f);
            text = Pattern.compile(s+"(.*?)G(.*?)"+f).matcher(text).replaceAll(s+"$1G#$2"+f);
            text = Pattern.compile(s+"(.*?)F#(.*?)"+f).matcher(text).replaceAll(s+"$1G$2"+f);
            text = Pattern.compile(s+"(.*?)F(.*?)"+f).matcher(text).replaceAll(s+"$1F#$2"+f);
            text = Pattern.compile(s+"(.*?)E(.*?)"+f).matcher(text).replaceAll(s+"$1F$2"+f);
            text = Pattern.compile(s+"(.*?)D#(.*?)"+f).matcher(text).replaceAll(s+"$1E$2"+f);
            text = Pattern.compile(s+"(.*?)D(.*?)"+f).matcher(text).replaceAll(s+"$1D#$2"+f);
            text = Pattern.compile(s+"(.*?)C#(.*?)"+f).matcher(text).replaceAll(s+"$1D$2"+f);
            text = Pattern.compile(s+"(.*?)C(.*?)"+f).matcher(text).replaceAll(s+"$1C#$2"+f);
            //XをCに戻す
            text = Pattern.compile(s+"(.*?)X(.*?)"+f).matcher(text).replaceAll(s+"$1C$2"+f);

        }
        text = Pattern.compile(s+"(.*?)"+f).matcher(text).replaceAll("\\|$1\\|");

        return text;
    }




    //曲のキーを返す
    public String FindKey(String chords){
        String return_s;
        int num=0;
        int C,D,E,F,G,A;
        int sum[] = {0, 0, 0, 0, 0,0,0,0,0,0,0,0};
        String Key[] = {"C", "C#","D","D#","E","F","F#","G","G#","A","A#","B"};
        int max=0;
        int max_i=-1;
        int sub_max=0;

        for(int i=0;i<12;i++) {
            C = Counter(chords, "C",0) - Counter(chords, "C#",0);
            D = Counter(chords, "D",0) - Counter(chords, "D#",0);
            E = Counter(chords, "E",0);
            F = Counter(chords, "F",0) - Counter(chords, "F#",0);
            G = Counter(chords, "G",0) - Counter(chords, "G#",0);
            A = Counter(chords, "A",0) - Counter(chords, "A#",0);
            sum[i] = C + D + E + F + G + A;
            if(max<sum[i]){
                max=sum[i];
                max_i=i;
            }else if(max==sum[i]){
                sub_max=sum[i];
            }
            chords=HalfUpDown(chords,-1);
        }


        if(sub_max==max){
            //解析不能
            return_s="Key=?";
        }else{
            return_s="Key="+Key[max_i];//12-max_iカポするとCで弾ける
        }

        return return_s;
    }






//targetの文字がchordsで何回つかわれているか数える
    public int Counter(String chords,String target,int f){//f=1完全一致f=0頭一致
        int num=0;
        chords=Pattern.compile("\\n").matcher(chords).replaceAll("");
        if(chords!=null) {
            String[] data_split = chords.split(",", 0);
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



//曲で使われているすべてのコードの種類と出現回数
    public String UsedChord(String chords){
        String result="";
        int num;
        int con=0;
        chords=Pattern.compile("\\n").matcher(chords).replaceAll("");

        if(chords!=null) {
            chords=","+chords;
            while(con<100&&!(Pattern.compile(",").matcher(chords).replaceAll("").trim().equals(""))) {
                con++;
                String[] data_split = chords.split(",", 0);
                if(data_split[1].equals(""))continue;//[0]は""が入ってる。,C,D,E....だから
                num = Counter(chords, data_split[1],1);
                result = result  + data_split[1] + "," + num + ",";
                chords = Pattern.compile(",("+data_split[1]+",)+").matcher(chords).replaceAll(",");

            }
        }
        return result;
    }



//UCの出力を入力し、ランダムで１つのコードを出力
    public String RandomChoice(String UC){
        String result="";

        if(UC!=null) {
            String[] data_split = UC.split(",", 0);
            ArrayList<String> array = new ArrayList<String>();
            int max=0;
            for (int i = 1 ; i < data_split.length ; i+=2){
                for(int j=0;j<Integer.valueOf(data_split[i]);j++){
                    array.add(data_split[i-1]);
                    max++;
                }
            }
            int ran = (int)(Math.random()*(max));

            result=array.get(ran);
        }

        return result;
    }

    public String ChordProgression(String chords,String rule) {
        String result="";
        String uc=UsedChord(chords);
        String[] rule_split = rule.split(",", 0);

        for (String rule_s : rule_split){
            for(int j=0;j<Integer.valueOf(rule_s);j++){
                result=result+"|"+RandomChoice(uc);
            }
            result=result+"|\n";
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