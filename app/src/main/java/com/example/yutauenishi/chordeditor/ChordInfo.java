package com.example.yutauenishi.chordeditor;



import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;



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
        boolean mov1 = c.moveToFirst();

        String title = c.getString(0);
        String chords = c.getString(1);
        String artist = c.getString(2);
        toolbar(title,artist);


        TextView textView = (TextView) findViewById(R.id.text);
        textView.setText(chords);

        c.close();
        db.close();

    }


}
