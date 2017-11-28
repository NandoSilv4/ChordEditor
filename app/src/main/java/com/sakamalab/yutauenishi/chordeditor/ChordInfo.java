package com.sakamalab.yutauenishi.chordeditor;



import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;


public class ChordInfo extends AppCompatActivity {
    MyOpenHelper helper = new MyOpenHelper(this);
    int id =1;

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
                Intent dbIntent = new Intent(getApplication(),SongPage.class);
                dbIntent.putExtra("id", id);
                startActivity(dbIntent);
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chord_info);
        Intent intent = getIntent();
        id = intent.getIntExtra("id",1);

        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "select title,chords,artist from note where id ="+ id +";";
        Cursor c = db.rawQuery(sql,null);
        c.moveToFirst();

        String title = c.getString(0);
        String chords = c.getString(1);
        String artist = c.getString(2);
        toolbar(title,artist);

        AnalysisChords AC=new AnalysisChords();


        HashMap<String,Integer> map;
        map=AC.UsedChord(chords);
        String text="";
        for (String key : map.keySet()) {
            Integer n_times = map.get(key);
            text=text+key+"が"+n_times+"回\n";
        }


        //String cp=AC.ChordProgression(chords,"2,2,4,4,2,2,4,4");
        SparseArray<String[]> map2;
        map2=AC.StringToMap(chords);
        String test=AC.Choose_One(map2,1,2);


        TextView textView = (TextView) findViewById(R.id.text);
        textView.setText(chords);

        TextView textView_2 = (TextView) findViewById(R.id.text_2);
        textView_2.setText(text);

        TextView textView_3 = (TextView) findViewById(R.id.text_3);
        textView_3.setText(test);

        c.close();
        db.close();

    }


}
