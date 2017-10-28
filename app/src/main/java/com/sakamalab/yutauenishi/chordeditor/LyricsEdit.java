package com.sakamalab.yutauenishi.chordeditor;



import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.util.Calendar;



public class LyricsEdit extends AppCompatActivity {
    MyOpenHelper helper = new MyOpenHelper(this);
    int id =1;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lyrics_edit, menu);
        return true;
    }


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
                overridePendingTransition(0, 0);
            }
        });


        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // アイテムクリック時の処理
                AnalysisChords AC = new AnalysisChords();
                EditText editText = (EditText) findViewById(R.id.edit1);
                String text = editText.getText().toString();
                switch (item.getItemId()) {
                    case R.id.sharp:
                        text=AC.HalfUpDownEdit(text,1);
                        editText.setText(text);
                        return true;
                    case R.id.flat:
                        text=AC.HalfUpDownEdit(text,-1);
                        editText.setText(text);
                        return true;
                    default:
                        return false;
                }

            }
        });
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyrics_edit);
        Intent intent = getIntent();
        id = intent.getIntExtra("id",1);


        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "select title,data,artist from note where id ="+ id +";";
        Cursor c = db.rawQuery(sql,null);
        boolean mov1 = c.moveToFirst();

        String title = c.getString(0);
        String data = c.getString(1);
        String artist = c.getString(2);
        toolbar(title,artist);


        EditText editText = (EditText) findViewById(R.id.edit1);
        editText.setText(data);

        c.close();
        db.close();

    }





    @Override
    protected void onRestart() {
        super.onRestart();
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "select title,data,artist from note where id ="+ id +";";
        Cursor c = db.rawQuery(sql,null);
        boolean mov1 = c.moveToFirst();

        String title = c.getString(0);
        String data = c.getString(1);
        String artist = c.getString(2);
        toolbar(title,artist);

        EditText editText = (EditText) findViewById(R.id.edit1);
        editText.setText(data);

        c.close();
        db.close();
    }



    @Override
    protected void onPause() {
        super.onPause();
        // ここでファイル保存処理を行う
        Calendar cal = Calendar.getInstance();       //カレンダーを取得
        int sYear = cal.get(Calendar.YEAR);         //年を取得
        int sMonth = cal.get(Calendar.MONTH)+1;       //月を取得
        int sDate = cal.get(Calendar.DATE);         //日を取得
        EditText editText = (EditText) findViewById(R.id.edit1);
        String text = editText.getText().toString();
        String chord;
        text = text.replaceAll("'", "''");
        AnalysisChords AC = new AnalysisChords();
        chord = AC.GetChords(text);
        Log.i("テスト  ", "ファイル保存！！"+chord);
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "UPDATE note SET " +
                "data = '"+ text +"'," +
                "chords = '"+ chord +"'," +
                "year = "+ sYear +"," +
                "month = "+ sMonth +"," +
                "day = "+ sDate +" WHERE id = "+ id +";";
        db.execSQL(sql);
        Log.i("テスト  ", "ファイル保存！！");
        db.close();
    }



}
