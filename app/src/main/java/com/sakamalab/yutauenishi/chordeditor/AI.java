package com.sakamalab.yutauenishi.chordeditor;



import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;


public class AI extends AppCompatActivity {
    MyOpenHelper helper = new MyOpenHelper(this);




    // ツールバー
    public void toolbar(String name,String artist){
        // ツールバーをアクションバーとして使う
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle(name);
        toolbar.setSubtitle(artist);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        // UPナビゲーションを有効化する
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dbIntent = new Intent(getApplication(),SongList.class);
                startActivity(dbIntent);
            }
        });
    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.ai);
        toolbar("AI","learning");
        AnalysisChords AC=new AnalysisChords();

        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "select chords from note;";
        Cursor c = db.rawQuery(sql,null);

        boolean mov = c.moveToFirst();
        if(!mov)return;
        SparseArray<String[]> map_A;
        HashMap<String, List<String>> Connect_map_A = new HashMap<>();
        HashMap<String, Integer> Length_map_A= new HashMap<>();
        String allChords_A="------[Aメロ]------\n";
        String allChords_B="------[Bメロ]------\n";
        String first_chord_A,line_n_A,chords_A;
        first_chord_A=line_n_A="";



        SparseArray<String[]> map_B;
        HashMap<String, List<String>> Connect_map_B = new HashMap<>();
        HashMap<String, Integer> Length_map_B= new HashMap<>();
        String first_chord_B,line_n_B,chords_B;
        first_chord_B=line_n_B="";
        int sc;

        while (mov) {
            String chords = c.getString(0);
            chords=AC.UnityRowNumber(chords);
            String[] line_s;
            //----------[Aメロ]-----------------------------------------------------------
            chords_A=AC.SelectPart(chords,"Aメロ");
            sc=AC.CheckPartKey(chords_A);
            switch(sc){
                case 20:
                    break;
                case 0:
                    allChords_A=allChords_A+chords_A+"\n";
                    //----------↓コードの長さ設定↓----------
                    line_s = chords_A.split("\\n", 0);
                    Length_map_A=AC.ChordLengthAnalysis(line_s[0],Length_map_A);
                    //----------↑コードの長さ設定↑----------
                    map_A=AC.StringToMap(chords_A);
                    first_chord_A=first_chord_A+AC.GetChordRoot(AC.Choose_One(map_A,1,1))+",";
                    if(map_A!=null) line_n_A = line_n_A + String.valueOf(map_A.size()) + ",";
                    Connect_map_A=AC.NextChordAnalysis(map_A,Connect_map_A);
                    break;
                default:
                    chords_A = AC.HalfUpDown(chords_A, -sc);
                    allChords_A=allChords_A+chords_A+"\n";
                    //----------↓コードの長さ設定↓----------
                    line_s = chords_A.split("\\n", 0);
                    Length_map_A=AC.ChordLengthAnalysis(line_s[0],Length_map_A);
                    //----------↑コードの長さ設定↑----------
                    map_A=AC.StringToMap(chords_A);
                    first_chord_A=first_chord_A+AC.GetChordRoot(AC.Choose_One(map_A,1,1))+",";
                    if(map_A!=null) line_n_A = line_n_A + String.valueOf(map_A.size()) + ",";
                    Connect_map_A=AC.NextChordAnalysis(map_A,Connect_map_A);
            }
            //----------[Aメロ]-----------------------------------------------------------

            //----------[Bメロ]-----------------------------------------------------------
            chords_B=AC.SelectPart(chords,"Bメロ");
            sc=AC.CheckPartKey(chords_B);
            switch(sc){
                case 0:
                    allChords_B=allChords_B+chords_B+"\n";
                    //----------↓コードの長さ設定↓----------
                    line_s = chords_B.split("\\n", 0);
                    Length_map_B=AC.ChordLengthAnalysis(line_s[0],Length_map_B);
                    //----------↑コードの長さ設定↑----------
                    map_B=AC.StringToMap(chords_B);
                    first_chord_B=first_chord_B+AC.GetChordRoot(AC.Choose_One(map_B,1,1))+",";
                    if(map_B!=null) line_n_B = line_n_B + String.valueOf(map_B.size()) + ",";
                    Connect_map_B=AC.NextChordAnalysis(map_B,Connect_map_B);
                    break;
                case 20:
                    break;
                default:
                    chords_B = AC.HalfUpDown(chords_B, -sc);
                    allChords_B=allChords_B+chords_B+"\n";
                    //----------↓コードの長さ設定↓----------
                    line_s = chords_B.split("\\n", 0);
                    Length_map_B=AC.ChordLengthAnalysis(line_s[0],Length_map_B);
                    //----------↑コードの長さ設定↑----------
                    map_B=AC.StringToMap(chords_B);
                    first_chord_B=first_chord_B+AC.GetChordRoot(AC.Choose_One(map_B,1,1))+",";
                    if(map_B!=null) line_n_B = line_n_B + String.valueOf(map_B.size()) + ",";
                    Connect_map_B=AC.NextChordAnalysis(map_B,Connect_map_B);
            }


            //----------[Bメロ]-----------------------------------------------------------


            mov = c.moveToNext();
        }


        HashMap<String,Integer> UC_A,UC_B;
        //----------[Aメロ]-----------------------------------------------------------
        UC_A=AC.UsedChord(first_chord_A);
        first_chord_A=AC.RandomChoice(UC_A);//first_chord_Aの更新（新しい物を決める)
        line_n_A=AC.RandomChoice(AC.UsedChord(line_n_A));//line_n_Aの更新（新しい物を決める)
        int new_line_n_A=0;
        if(!line_n_A.equals(""))new_line_n_A=Integer.parseInt(line_n_A);

        map_A=AC.FirstChordProgression(first_chord_A, new_line_n_A, Connect_map_A,Length_map_A);//map_Aの使いまわし
        String new_A=AC.MapToString(map_A);
        //----------[Aメロ]-----------------------------------------------------------


        //----------[Bメロ]-----------------------------------------------------------
        UC_B=AC.UsedChord(first_chord_B);
        first_chord_B=AC.RandomChoice(UC_B);//first_chord_Bの更新（新しい物を決める)
        line_n_B=AC.RandomChoice(AC.UsedChord(line_n_B));//line_n_Bの更新（新しい物を決める)
        int new_line_n_B=0;
        if(!line_n_B.equals(""))new_line_n_B=Integer.parseInt(line_n_B);

        map_B=AC.FirstChordProgression(first_chord_B, new_line_n_B, Connect_map_B,Length_map_B);//map_Bの使いまわし
        Log.i("テスト  ", "FirstChordProgression finish");
        String new_B=AC.MapToString(map_B);
        //----------[Bメロ]-----------------------------------------------------------









        new_A="[Aメロ]\n"+new_A+"[Bメロ]\n"+new_B;
        TextView textView = (TextView) findViewById(R.id.text_1);
        textView.setText(new_A);


        String UC="[Aメロ]\n"+AC.UCtoString(UC_A)+"\n[Bメロ]\n"+AC.UCtoString(UC_B);
        TextView textView2 = (TextView) findViewById(R.id.text_2);
        textView2.setText(UC);




        String Length="[Aメロ]\n"+AC.UCtoString(Length_map_A)+"\n[Bメロ]\n"+AC.UCtoString(Length_map_B)+"\n[ランダム]\n"+
                AC.RandomChoice(Length_map_A)+"\n"+AC.RandomChoice(Length_map_B);

        TextView textView3 = (TextView) findViewById(R.id.text_3);
        textView3.setText(Length);


        allChords_A=allChords_A+allChords_B;
        TextView textView4 = (TextView) findViewById(R.id.text_4);
        textView4.setText(allChords_A);


        c.close();
        db.close();
    }







    @Override
    protected void onPause() {
        super.onPause();
        // ここでファイル保存処理を行う
    }
}
