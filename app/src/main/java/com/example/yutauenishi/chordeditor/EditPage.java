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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import android.view.KeyEvent;

public class EditPage extends AppCompatActivity {
    MyOpenHelper helper = new MyOpenHelper(this);
    int id =1;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_page, menu);
        return true;
    }


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
                Intent dbIntent = new Intent(getApplication(),SongList.class);
                startActivity(dbIntent);
                overridePendingTransition(0, 0);
            }
        });


        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // アイテムクリック時の処理
                switch (item.getItemId()) {
                    case R.id.action_edit:
                        Intent dbIntent = new Intent(getApplication(),LyricsEdit.class);
                        dbIntent.putExtra("id", id);
                        startActivity(dbIntent);
                        overridePendingTransition(0, 0);
                        Log.i("テスト  ", "編集");
                        return true;
                    case R.id.chords_edit:
                        Intent dbIntent2 = new Intent(getApplication(),ChordsEdit.class);
                        dbIntent2.putExtra("id", id);
                        startActivity(dbIntent2);
                        overridePendingTransition(0, 0);
                        Log.i("テスト  ", "コード譜");
                        return true;
                    case R.id.action_play:
                        Log.i("テスト  ", "演奏モード");
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
        setContentView(R.layout.edit_page);
        Intent intent = getIntent();
        id = intent.getIntExtra("id",1);

        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "select name,data from note where id ="+ id +";";
        Cursor c = db.rawQuery(sql,null);
        boolean mov1 = c.moveToFirst();

        String name = c.getString(0);
        String data = c.getString(1);
        toolbar(name);

        TextView textView = (TextView) findViewById(R.id.text1);
        //textView.setText(data);
        String[] data_split = data.split("\\|", 0);//ここの「/」は分数コードに対応させるために「|」に変更したい
        String lyrics="";
        String chords="";

        //Matcher　findを使って歌詞中の[Am]とかのコードだけを抜き出して保存。


        for (int i = 0 ; i < data_split.length ; i++) {
            switch (data_split[i].trim()) {
                case "C":case "D":case "E":case "F":case "G":case "A":case "B":
                    chords=chords+data_split[i];
                    break;
                default:
                    lyrics=lyrics+data_split[i];
            }
        }
        textView.setText(lyrics);





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

        TextView textView = (TextView) findViewById(R.id.text1);
        textView.setText(data);

        c.close();
        db.close();
    }




    @Override
    protected void onPause() {
        super.onPause();
        // ここでファイル保存処理を行う
    }




}