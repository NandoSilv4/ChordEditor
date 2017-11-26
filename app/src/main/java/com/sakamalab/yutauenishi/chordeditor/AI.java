package com.sakamalab.yutauenishi.chordeditor;



import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;


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
        SparseArray<String[]> map;
        String allChords="";

        while (mov) {
            String chords = c.getString(0);
            chords=AC.SelectPart(chords,"Aメロ");
            map=AC.StringToMap(chords);
            chords=AC.MapToString(map);
            allChords=allChords+chords+"\n";
            mov = c.moveToNext();
        }

        TextView textView = (TextView) findViewById(R.id.text_1);
        textView.setText(allChords);


        c.close();
        db.close();
    }







    @Override
    protected void onPause() {
        super.onPause();
        // ここでファイル保存処理を行う
    }
}
