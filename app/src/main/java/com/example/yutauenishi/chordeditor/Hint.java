package com.example.yutauenishi.chordeditor;



import android.app.AlertDialog;
import android.content.Intent;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;




public class Hint extends AppCompatActivity {
    MyOpenHelper helper = new MyOpenHelper(this);
    int id =1;


    public void hint0(View v){
        new AlertDialog.Builder(this)
                .setTitle("hint0")
                .setMessage("hint0をクリックしました。")
                .setPositiveButton("OK", null)
                .show();
    }

    public void hint1(View v){
        new AlertDialog.Builder(this)
                .setTitle("hint1")
                .setMessage("hint1をクリックしました。")
                .setPositiveButton("OK", null)
                .show();
    }

    public void hint2(View v){
        new AlertDialog.Builder(this)
                .setTitle("hint2")
                .setMessage("hint2をクリックしました。")
                .setPositiveButton("OK", null)
                .show();
    }

    public void hint3(View v){
        new AlertDialog.Builder(this)
                .setTitle("hint3")
                .setMessage("hint3をクリックしました。")
                .setPositiveButton("OK", null)
                .show();
    }

    public void hint4(View v){
        new AlertDialog.Builder(this)
                .setTitle("hint4")
                .setMessage("hint4をクリックしました。")
                .setPositiveButton("OK", null)
                .show();
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
            }
        });
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hint);
        toolbar("Chord Editor");


    }



}
