package com.sakamalab.yutauenishi.chordeditor;


import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AnalysisChords extends AppCompatActivity {


    public HashMap<String, Integer> ChordType(String type,HashMap<String, Integer> map){
        int root=map.get("root");
        switch(type){
            //対応済
            case "add9":
                map.put("sub3", (root+2)%12);break;
            case "aug":
                map.put("sub2", (root+8)%12);break;
            case "dim":
                map.put("sub1", (root+3)%12);map.put("sub2", (root+6)%12);map.put("sub3", (root+9)%12);break;
            case "sus4":
                map.put("sub1", (root+5)%12);break;
            case "m":
                map.put("sub1", (root+3)%12);break;
            case "m6":
                map.put("sub1", (root+3)%12);
                map.put("sub3", (root+9)%12);break;
            case "m7":
                map.put("sub1", (root+3)%12);
                map.put("sub3", (root+10)%12);break;
            case "maj7":case "Maj7":case "M7":
                map.put("sub3", (root+11)%12);break;
            case "mM7":case "m+7":case "mMaj7":
                map.put("sub1", (root+3)%12);
                map.put("sub3", (root+11)%12);break;
            case "6":
                map.put("sub3", (root+9)%12);break;
            case "69":case "6/9":case "6(9)":
                map.put("sub3", (root+9)%12);
                map.put("sub4", (root+2)%12);break;
            case "7":
                map.put("sub3", (root+10)%12);break;
            case "7sus4":
                map.put("sub1", (root+5)%12);
                map.put("sub3", (root+10)%12);break;
            case "m7(b5)":case "m7♭5":case "m7(-5)":case "m7-5":
                map.put("sub1", (root+3)%12);
                map.put("sub2", (root+6)%12);
                map.put("sub3", (root+10)%12);
                break;
            case "7(b5)":case "7♭5":case "7(-5)":case "7-5":
                map.put("sub2", (root+6)%12);
                map.put("sub3", (root+10)%12);
                break;


            //未対応
            case "maj9":case"Maj9":case "M9":break;
            case "dim7":break;
            case "7aug":break;
            case "9":break;
            case "11":break;
            case "M7aug":break;

            default:
        }
        return map;
    }


    //♭を全部なくし、＃にする
    public String FlatToSharp(String text){

        String s="\\[\\$";
        String f="\\$\\]";
        text = Pattern.compile("\\|(.*?)\\|").matcher(text).replaceAll(s+"$1"+f);

        text = Pattern.compile(s+"(.*?)C♭(.*?)"+f).matcher(text).replaceAll(s+"$1B$2"+f);
        text = Pattern.compile(s+"(.*?)D♭(.*?)"+f).matcher(text).replaceAll(s+"$1C#$2"+f);
        text = Pattern.compile(s+"(.*?)E♭(.*?)"+f).matcher(text).replaceAll(s+"$1D#$2"+f);
        text = Pattern.compile(s+"(.*?)F♭(.*?)"+f).matcher(text).replaceAll(s+"$1E$2"+f);
        text = Pattern.compile(s+"(.*?)G♭(.*?)"+f).matcher(text).replaceAll(s+"$1F#$2"+f);
        text = Pattern.compile(s+"(.*?)A♭(.*?)"+f).matcher(text).replaceAll(s+"$1G#$2"+f);
        text = Pattern.compile(s+"(.*?)B♭(.*?)"+f).matcher(text).replaceAll(s+"$1A#$2"+f);
        text = Pattern.compile(s+"(.*?)B#(.*?)"+f).matcher(text).replaceAll(s+"$1C$2"+f);
        text = Pattern.compile(s+"(.*?)E#(.*?)"+f).matcher(text).replaceAll(s+"$1F$2"+f);

        text = Pattern.compile(s+"(.*?)"+f).matcher(text).replaceAll("\\|$1\\|");
        return text;
    }


    public String UnityRowNumber(String chordsALL) {
        if(chordsALL.equals(""))return "";
        chordsALL=Pattern.compile("-,").matcher(chordsALL).replaceAll("<->");
        return chordsALL;
    }


    //コード一文字目を下記に基づいて数字化する
    public  int ChordToNo(char first){
        /*
        0  C
        1  C#
        2  D
        3  D#
        4  E
        5  F
        6  F#
        7  G
        8  G#
        9  A
        10 A#
        11 B
         */
        switch (first){
            case 'C':
                return 0;
            case 'D':
                return 2;
            case 'E':
                return 4;
            case 'F':
                return 5;
            case 'G':
                return 7;
            case 'A':
                return 9;
            case 'B':
                return 11;
            default:
                return -1;
        }
    }

    //コード解析するさいの＃など
    public String HalfUpDown(String text,int count){

        while(count<0){//ハーフダウンの場合
            count=12+count;
        }
        String s2="<\\$";
        String f2="\\$>";
        //[]内を変更しないために
        text = Pattern.compile("\\[(.*?)B(.*?)\\]").matcher(text).replaceAll("[$1"+s2+"b"+f2+"$2]");
        text = Pattern.compile("\\[(.*?)A(.*?)\\]").matcher(text).replaceAll("[$1"+s2+"a"+f2+"$2]");
        text = Pattern.compile("\\[(.*?)G(.*?)\\]").matcher(text).replaceAll("[$1"+s2+"g"+f2+"$2]");
        text = Pattern.compile("\\[(.*?)F(.*?)\\]").matcher(text).replaceAll("[$1"+s2+"f"+f2+"$2]");
        text = Pattern.compile("\\[(.*?)E(.*?)\\]").matcher(text).replaceAll("[$1"+s2+"e"+f2+"$2]");
        text = Pattern.compile("\\[(.*?)D(.*?)\\]").matcher(text).replaceAll("[$1"+s2+"d"+f2+"$2]");
        text = Pattern.compile("\\[(.*?)C(.*?)\\]").matcher(text).replaceAll("[$1"+s2+"c"+f2+"$2]");

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

        //[]内を変更しないために
        text = Pattern.compile(s2+"b"+f2).matcher(text).replaceAll("B");
        text = Pattern.compile(s2+"a"+f2).matcher(text).replaceAll("A");
        text = Pattern.compile(s2+"g"+f2).matcher(text).replaceAll("G");
        text = Pattern.compile(s2+"f"+f2).matcher(text).replaceAll("F");
        text = Pattern.compile(s2+"e"+f2).matcher(text).replaceAll("E");
        text = Pattern.compile(s2+"d"+f2).matcher(text).replaceAll("D");
        text = Pattern.compile(s2+"c"+f2).matcher(text).replaceAll("C");




        return text;
    }


    //エディットページの#などの実装
    public String HalfUpDownEdit(String text,int count){

        text=FlatToSharp(text);
        while(count<0){//ハーフダウンの場合
            count=12+count;
        }
        String s="\\[\\$";
        String f="\\$\\]";
        String s2="<\\$";
        String f2="\\$>";

        text = Pattern.compile("::(.*?)::").matcher(text).replaceAll("|"+s2+"$1"+f2+"|");





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
        text = Pattern.compile("\\|"+s2+"(.*?)"+f2+"\\|").matcher(text).replaceAll("::$1::");

        return text;
    }



    //コードのルートを見つけ、和音の構成音を見つける
    public HashMap<String, Integer> ChordNameAnalysis(String chord){
        HashMap<String,Integer> map= new HashMap<>();
        int root;
        String type;
        switch (chord.length()) {
            case 0:
                return null;
            case 1:
                root=ChordToNo(chord.charAt(0));
                map.put("root", root);
                map.put("sub1", (root+4)%12);
                map.put("sub2", (root+7)%12);
                return map;
            default:
                root=ChordToNo(chord.charAt(0));
                if(chord.charAt(1)=='#'){
                    root++;
                    if(chord.length()==2){
                        map.put("root", root);
                        map.put("sub1", (root+4)%12);
                        map.put("sub2", (root+7)%12);
                        return map;
                    }
                    type=chord.substring(2);
                }else{
                    type=chord.substring(1);
                }
                map.put("root", root);
                map.put("sub1", (root+4)%12);
                map.put("sub2", (root+7)%12);
                map=ChordType(type,map);
                return map;
        }
    }


    //歌詞+コードからコードだけを抜き出す。
    public String GetChords(String text){
        Log.i("テスト  ", "GetChords！！");
        String chords;
        String chordsALL = "";
        text=FlatToSharp(text);


        Pattern p1 = Pattern.compile("\\|.*?\\|");
        if(text!=null) {
            text=Pattern.compile("\\[(.*?)\\]").matcher(text).replaceAll("\\|\\[$1\\]\\|");
            text=Pattern.compile("\uFEFF").matcher(text).replaceAll("");
            Matcher ma=Pattern.compile("\\{(?:.|\\n)*?\\}").matcher(text);
            while (ma.find()){
                Matcher m2=Pattern.compile("\\n").matcher(ma.group());
                String new_text=ma.group();
                while (m2.find()){
                    new_text=Pattern.compile("\\n").matcher(new_text).replaceAll("");
                }
                text = text.replace(ma.group(),new_text);
            }

            text=Pattern.compile("::(?:.|\\n)*?::").matcher(text).replaceAll("-,");
            text=Pattern.compile("\\|:(.*?):\\|").matcher(text).replaceAll("|$1,$1|");
            String[] data_split = text.split("\\n", 0);
            for (String data_s : data_split) {
                chords="";
                Matcher m1 = p1.matcher(data_s);
                while (m1.find()){
                    chords=chords+m1.group().substring(1, m1.group().length() - 1)+",";
                }
                if(!(chords.equals("")))chordsALL=chordsALL+chords+"\n";
            }
        }

        return chordsALL;
    }




    //targetの文字がchordsで何回つかわれているか数える
    public int Counter(String chords,String target,int f){//f=1完全一致f=0頭一致
        int num=0;

        if(chords!=null) {
            chords=Pattern.compile("\\n").matcher(chords).replaceAll("");
            chords=Pattern.compile("\\[.*?\\],").matcher(chords).replaceAll("");
            if(chords==null)return 0;
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



    //String Key[] = {"C", "C#","D","D#","E","F","F#","G","G#","A","A#","B"};
    //曲のキーを返す
    public int FindKey(String chords){
        int return_i;
        int C,Dm,Em,F,G,Am;
        int sum[] = {0, 0, 0, 0, 0,0,0,0,0,0,0,0};
        int max=0;
        int max_i=-1;
        int sub_max=0;

        for(int i=0;i<12;i++) {
            C = Counter(chords, "C",0) - Counter(chords, "C#",0);
            Dm = Counter(chords, "Dm",0);
            Em = Counter(chords, "Em",0);
            F = Counter(chords, "F",0) - Counter(chords, "F#",0);
            G = Counter(chords, "G",0) - Counter(chords, "G#",0);
            Am = Counter(chords, "Am",0);
            sum[i] = C + Dm + Em + F + G + Am;
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
            return_i=20;
        }else{
            return_i=max_i;//12-max_iカポするとCで弾ける=
        }
        return return_i;
    }



    //KeyをCにする
    public String ChangeKeyToC(String chords){
        String result_s;
        int num=FindKey(chords);
        result_s = HalfUpDown(chords, -num);
        return result_s;
    }


    //コードデータをStringからSparseArrayに変更
    public SparseArray<String[]> StringToMap(String chordsALL) {

        if(chordsALL.equals(""))return null;
        chordsALL=Pattern.compile("\\[.*?\\],").matcher(chordsALL).replaceAll("");

        String[] data_line = chordsALL.split("\\n", 0);
        SparseArray<String[]> map = new SparseArray<>();


        for(int i=0;i<data_line.length;i++){
            if(!(data_line[i].equals(""))&&!(data_line[i].equals(","))) {
                String[] data_chord = data_line[i].split(",", 0);
                if(data_chord.length!=0)map.put(i,data_chord);
            }
        }


        return map;
    }

    //コードデータをSparseArrayからStringに変更
    public String MapToString(SparseArray<String[]> map) {
        String result="";
        if(map==null)return result;

        for(int i=0;i<map.size();i++){
            int key = map.keyAt(i);
            String[] chord=map.get(key);
            for(String FE_chord:chord){
                result=result+FE_chord+",";
            }
            result=result+"\n";
        }
        return result;
    }


    //行 l 列 r　で指定された場所のコードをmapから取り出して返す。
    public String Choose_One(SparseArray<String[]> map,int l,int r) {
        if(map==null)return "";
        l=l-1;
        r=r-1;
        int key = map.keyAt(l);
        String[] chord=map.get(key);
        return chord[r];
    }

    //Aメロを抜き出す
    public String SelectPart(String chords,String choices) {

        if(chords.equals(""))return "";
        String[] SP_chords1 = chords.split("\\["+choices+"\\],*(\\n)*", 0);
        if(SP_chords1.length<2)return "";
        String[] SP_chords2 = SP_chords1[1].split("\\[.*?\\],*(\\n)*", 0);
        if(SP_chords2.length==0)return "";

        return SP_chords2[0];
    }








    //UCの出力を入力し、ランダムで１つのコードを出力
    public String RandomChoice(HashMap<String, Integer> UC){
        String result="";
        ArrayList<String> array = new ArrayList<>();
        int max=0;
        if(!UC.isEmpty()) {
            for (String key : UC.keySet()) {
                Integer n_times = UC.get(key);
                for(int j=0;j<n_times;j++){
                    array.add(key);
                    max++;
                }
            }
            int ran = (int)(Math.random()*(max));
            result=array.get(ran);
        }
        return result;
    }





    //コードのルートを見つける
    public String GetChordRoot(String chord){
        String root;
        if(chord.equals(""))return "";
        switch (chord.length()) {
            case 1:
                return chord;
            case 2:
                root=chord.substring(0, 1);
                if(chord.charAt(1)=='#'||chord.charAt(1)=='m'){
                    root=chord;
                }
                return root;
            default:
                if(chord.charAt(1)=='#'){
                    root=chord.substring(0, 2);
                    if(chord.charAt(2)=='m'&&chord.charAt(3)!='a'){
                        root=chord.substring(0, 3);
                    }
                }else{
                    root=chord.substring(0, 1);
                    if(chord.charAt(1)=='m'&& chord.charAt(2)!='a'){
                        root=chord.substring(0, 2);
                    }
                }
                return root;
        }
    }





    //曲で使われているすべてのコードの種類と出現回数
    public HashMap<String, Integer> UsedChord(String chords){
        HashMap<String,Integer> map = new HashMap<>();
        int num;
        int con=0;
        chords=Pattern.compile("\\n").matcher(chords).replaceAll("");
        chords=Pattern.compile("\\[.*?\\],").matcher(chords).replaceAll("");
        if(chords!=null) {
            chords=","+chords;
            while(con<100&&!(Pattern.compile(",").matcher(chords).replaceAll("").trim().equals(""))) {
                con++;
                String[] data_split = chords.split(",", 0);
                if(data_split[1].equals(""))continue;//[0]は""が入ってる。,C,D,E....だから
                num = Counter(chords, data_split[1],1);//完全一致を探す
                map.put(data_split[1], num);
                chords = Pattern.compile(",("+data_split[1]+",)+").matcher(chords).replaceAll(",");
            }
        }
        return map;
    }













    //出現コードの次のコードの割合を記憶
    public HashMap<String, List<String>> NextChordAnalysis(SparseArray<String[]> chords_map, HashMap<String, List<String>> result_map){
        if(chords_map==null)return result_map;
        String target;
        String[] target_str;

        for(int i=0;i<chords_map.size();i++){
            int key=chords_map.keyAt(i);
            target_str=chords_map.get(key);
            for(int j=0;j<3;j++){
                List<String> target_list;
                target=GetChordRoot(target_str[j]);
                target_list=result_map.get(target);
                if(target_list==null){
                    List<String> new_list = new ArrayList<>();
                    new_list.add(GetChordRoot(target_str[j+1]));
                    result_map.put(target,new_list);
                }else{
                    target_list.add(GetChordRoot(target_str[j+1]));
                    result_map.put(target,target_list);
                }
            }
        }

        return result_map;
    }




    public SparseArray<String[]> FirstChordProgression(String f_chord,int line_counter, HashMap<String, List<String>> NC_list_map) {
        SparseArray<String[]> new_chord_map = new SparseArray<>();
        String[] new_chord_p= new String[4];
        List<String> next_list;
        if(NC_list_map==null) {
            return null;
        }

        for(int i=0;i<line_counter;i++){
            new_chord_p[0]=f_chord;
            for(int j=1;j<4;j++){
                next_list=NC_list_map.get(GetChordRoot(new_chord_p[j-1]));//f_chord→GetChordRoot(f_chord)
                int ran = (int)(Math.random()*(next_list.size()));
                String next_chord=next_list.get(ran);
                new_chord_p[j]=next_chord;
            }
            new_chord_map.put(i,new_chord_p);
        }
        return new_chord_map;
    }





    /*
    0  C
    1  C#
    2  D
    3  D#
    4  E
    5  F
    6  F#
    7  G
    8  G#
    9  A
    10 A#
    11 B



    //対象の行とほかの行を比較し、同じコードの場合1点、違えば0点を加え、マップ生成する。
    public SparseArray<int[]> CompareLines(SparseArray<String[]> map,int target){

        if(map==null)return null;
        if(target>map.size())return null;
        int key=map.keyAt(target);
        String[] source=map.get(key);
        String[] target_str;
        int point[]=new int[4];
        SparseArray<int[]> result_map = new SparseArray<>();

        for(int i=0;i<map.size();i++){
            if(i==target-1)continue;
            key=map.keyAt(i);
            target_str=map.get(key);
            for(int j=0;j<4;j++){
                if(target_str[j].equals(source[j]))point[j]++;
            }
            result_map.put(i,point);
        }
        return result_map;
    }

     */

}