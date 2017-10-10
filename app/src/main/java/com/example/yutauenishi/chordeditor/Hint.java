package com.example.yutauenishi.chordeditor;



import android.content.Intent;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;




public class Hint extends AppCompatActivity {
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
                Intent dbIntent = new Intent(getApplication(),SongList.class);
                startActivity(dbIntent);
            }
        });
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hint);
        toolbar("");


    }



}
