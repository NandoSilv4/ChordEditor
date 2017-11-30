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
        SparseArray<String[]> map,new_chord_map;
        String allChords="";
        String first_chord="";
        String line_numALL="";


        HashMap<String, List<String>> List_map = new HashMap<>();

        Log.i("テスト  ", "1dayo");
        while (mov) {
            String chords = c.getString(0);
            chords=AC.SelectPart(chords,"Aメロ");

            chords=AC.UnityRowNumber(chords);

            map=AC.StringToMap(chords);
            first_chord=first_chord+AC.GetChordRoot(AC.Choose_One(map,1,1))+",";
            if(map!=null) {
                line_numALL = line_numALL + String.valueOf(map.size()) + ",";
            }

            chords=AC.MapToString(map);
            allChords=allChords+chords+"\n";
            List_map=AC.NextChordAnalysis(map,List_map);

            mov = c.moveToNext();
        }








        HashMap<String,Integer> hash_map;
        hash_map=AC.UsedChord(first_chord);
        String uc="";
        for (String key : hash_map.keySet()) {
            Integer n_times = hash_map.get(key);
            uc=uc+key+"が"+n_times+"回\n";
        }

        HashMap<String,Integer> hash_map2;
        hash_map2=AC.UsedChord(line_numALL);
        String uc2="";
        for (String key : hash_map2.keySet()) {
            Integer n_times = hash_map2.get(key);
            uc2=uc2+key+"行が"+n_times+"回\n";
        }

        String new_first_chord=AC.RandomChoice(hash_map);
        String ln=AC.RandomChoice(hash_map2);
        int new_line_number=0;
        if(!ln.equals("")){
            new_line_number=Integer.parseInt(ln);
        }


        new_chord_map=AC.FirstChordProgression(new_first_chord,new_line_number,List_map);
        String new_chords=AC.MapToString(new_chord_map);




        TextView textView = (TextView) findViewById(R.id.text_1);
        textView.setText(allChords);

        TextView textView2 = (TextView) findViewById(R.id.text_2);
        textView2.setText(uc);

        TextView textView3 = (TextView) findViewById(R.id.text_3);
        textView3.setText(uc2);

        TextView textView4 = (TextView) findViewById(R.id.text_4);
        textView4.setText(new_chords);


        c.close();
        db.close();
    }







    @Override
    protected void onPause() {
        super.onPause();
        // ここでファイル保存処理を行う
    }
}
