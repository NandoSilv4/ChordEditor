package com.sakamalab.yutauenishi.chordeditor;



import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;



public class SoundPoolTest extends AppCompatActivity {
    MyOpenHelper helper = new MyOpenHelper(this);
    SoundPool cSoundPool,eSoundPool,gSoundPool;
    int cSoundResID,eSoundResID,gSoundResID;
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

    public void sound_c(View v) {
        cSoundPool.play(cSoundResID,1.0f,1.0f,0,0,1.0f);
        eSoundPool.play(eSoundResID,1.0f,1.0f,0,0,1.0f);
        gSoundPool.play(gSoundResID,1.0f,1.0f,0,0,1.0f);
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sound_pool_test);
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

    @Override
    protected void onResume() {
        super.onResume();
        cSoundPool= new SoundPool(3, AudioManager.STREAM_MUSIC,0);
        cSoundResID=cSoundPool.load(this,R.raw.sound_c,1);
        eSoundPool= new SoundPool(3, AudioManager.STREAM_MUSIC,0);
        eSoundResID=eSoundPool.load(this,R.raw.sound_e,1);
        gSoundPool= new SoundPool(3, AudioManager.STREAM_MUSIC,0);
        gSoundResID=gSoundPool.load(this,R.raw.sound_g,1);
    }

    @Override
    protected void onPause() {
        super.onPause();
        cSoundPool.release();
        eSoundPool.release();
        gSoundPool.release();
    }




}
