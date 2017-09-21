package com.example.yutauenishi.chordeditor;

/**
 * Created by YutaUenishi on 2017/09/21.
 */


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

import java.util.Calendar;

/**
 * Created by yuta on 2017/08/21.
 */

public class LyricsEdit extends AppCompatActivity {
    MyOpenHelper helper = new MyOpenHelper(this);
    int id =1;

    // ツールバー
    public void toolbar(String name){
        // ツールバーをアクションバーとして使う
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle(name);
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
        setContentView(R.layout.lyrics_edit);
        Intent intent = getIntent();
        id = intent.getIntExtra("id",1);


        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "select name,data from note where id ="+ id +";";
        Cursor c = db.rawQuery(sql,null);
        boolean mov1 = c.moveToFirst();

        String name = c.getString(0);
        String data = c.getString(1);
        toolbar(name);


        EditText editText = (EditText) findViewById(R.id.edit1);
        editText.setText(data);

        c.close();
        db.close();

    }





    @Override
    protected void onRestart() {
        super.onRestart();
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "select name,data from note where id ="+ id +";";
        Cursor c = db.rawQuery(sql,null);
        boolean mov1 = c.moveToFirst();

        String name = c.getString(0);
        String data = c.getString(1);
        toolbar(name);

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
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "UPDATE note SET " +
                "data = '"+ text +"'," +
                "year = "+ sYear +"," +
                "month = "+ sMonth +"," +
                "day = "+ sDate +" WHERE id = "+ id +";";
        db.execSQL(sql);
        Log.i("テスト  ", "ファイル保存！！");
        db.close();
    }



}
