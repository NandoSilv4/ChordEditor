package com.example.yutauenishi.chordeditor;



import android.app.AlertDialog;
import android.content.Intent;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


public class Hint extends AppCompatActivity {
    MyOpenHelper helper = new MyOpenHelper(this);
    int id =1;
    int hint0_f=0;
    int hint1_f=0;
    int hint2_f=0;
    int hint3_f=0;
    int hint4_f=0;

    public void hint0(View v){
        if(hint0_f==0) {
            LinearLayout hint0_L = (LinearLayout) findViewById(R.id.hint0_L);
            hint0_L.setVisibility(View.VISIBLE);
            hint0_f=1;
        }else{
            LinearLayout hint0_L = (LinearLayout) findViewById(R.id.hint0_L);
            hint0_L.setVisibility(View.GONE);
            hint0_f=0;
        }
    }

    public void hint1(View v){
        if(hint1_f==0) {
            LinearLayout hint1_L = (LinearLayout) findViewById(R.id.hint1_L);
            hint1_L.setVisibility(View.VISIBLE);
            hint1_f=1;
        }else{
            LinearLayout hint1_L = (LinearLayout) findViewById(R.id.hint1_L);
            hint1_L.setVisibility(View.GONE);
            hint1_f=0;
        }
    }

    public void hint2(View v){
        if(hint2_f==0) {
            LinearLayout hint2_L = (LinearLayout) findViewById(R.id.hint2_L);
            hint2_L.setVisibility(View.VISIBLE);
            hint2_f=1;
        }else{
            LinearLayout hint2_L = (LinearLayout) findViewById(R.id.hint2_L);
            hint2_L.setVisibility(View.GONE);
            hint2_f=0;
        }
    }

    public void hint3(View v){
        if(hint3_f==0) {
            LinearLayout hint3_L = (LinearLayout) findViewById(R.id.hint3_L);
            hint3_L.setVisibility(View.VISIBLE);
            hint3_f=1;
        }else{
            LinearLayout hint3_L = (LinearLayout) findViewById(R.id.hint3_L);
            hint3_L.setVisibility(View.GONE);
            hint3_f=0;
        }
    }

    public void hint4(View v){
        if(hint4_f==0) {
            LinearLayout hint4_L = (LinearLayout) findViewById(R.id.hint4_L);
            hint4_L.setVisibility(View.VISIBLE);
            hint4_f=1;
        }else{
            LinearLayout hint4_L = (LinearLayout) findViewById(R.id.hint4_L);
            hint4_L.setVisibility(View.GONE);
            hint4_f=0;
        }
    }

    String text;
    public void set_hint0(){
        text=   "Chord Editorは、「歌詞+コード」が記述された1つのファイルから「歌詞」と「コード」を分けて読み取り、見やすく表示する作曲支援ツールです。\n" +
                "コード進行と歌詞をメモする際にご活用ください。\n";
        TextView hint0_text = (TextView) findViewById(R.id.hint0_text);
        hint0_text.setText(text);
    }

    public void set_hint1(){
        text=   "最初のページの右上にある「＋」をクリックをしてファイルを作成します。\n" +
                "ポップアップに曲名と作者名を入力・決定すると自動でファイルの編集ページに移行します。";
        TextView hint1_text = (TextView) findViewById(R.id.hint1_text);
        hint1_text.setText(text);
    }

    public void set_hint2(){
        text=   "曲名と作者名の編集、ファイルの削除するには、まず目的のリストを長押しします。";
        TextView hint2_text = (TextView) findViewById(R.id.hint2_text);
        hint2_text.setText(text);
        text=   "すると、選択メニューが表示されますので目的の項目を選んでください。";
        TextView hint2_2_text = (TextView) findViewById(R.id.hint2_2_text);
        hint2_2_text.setText(text);
    }

    public void set_hint3(){
        text=   "コード（和音）を記入する際は、コードを | | で囲んでください。（例）Eコードの場合 |E|\n" +
                "歌詞の途中のコードが変わるタイミングで、上記の方法でコードを記入することによって、弾き語りをする場合でも理解しやすい楽譜を生成することができます。\n" +
                "[Aメロ]など、[]で囲まれた部分は「歌詞」にも「コード」にも表示されます。\n" +
                "{ } で囲まれた部分は、歌詞以外改行されません。";
        TextView hint3_text = (TextView) findViewById(R.id.hint3_text);
        hint3_text.setText(text);
    }

    public void set_hint4(){
        text=   "バグの報告やご要望は、アプリストアにある評価・コメントからお願い致します。";
        TextView hint4_text = (TextView) findViewById(R.id.hint4_text);
        hint4_text.setText(text);
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
        toolbar("使用方法");
        set_hint0();
        set_hint1();
        set_hint2();
        set_hint3();
        set_hint4();

    }



}
