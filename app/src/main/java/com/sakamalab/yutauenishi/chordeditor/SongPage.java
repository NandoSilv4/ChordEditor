package com.sakamalab.yutauenishi.chordeditor;




import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.HashMap;
import java.util.regex.Pattern;

public class SongPage extends AppCompatActivity {
    MyOpenHelper helper = new MyOpenHelper(this);
    int id =1;
    String chords="";
    AnalysisChords AC=new AnalysisChords();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_song_page, menu);
        return true;
    }










    SoundPool S_Pool;
    int[] S_ID_load=new int[12];
    int[] S_ID_stop=new int[12];
    public void SoundPlay(HashMap<String, Integer> SM){
        for (String ToneName : SM.keySet()) {
            Integer tone = SM.get(ToneName);
            switch (tone){
                case 0:S_ID_stop[0]=S_Pool.play(S_ID_load[0], 0.9f, 0.9f, 0, 0, 1.0f);break;
                case 1:S_ID_stop[1]=S_Pool.play(S_ID_load[1], 0.9f, 0.9f, 0, 0, 1.0f);break;
                case 2:S_ID_stop[2]=S_Pool.play(S_ID_load[2], 0.9f, 0.9f, 0, 0, 1.0f);break;
                case 3:S_ID_stop[3]=S_Pool.play(S_ID_load[3], 0.9f, 0.9f, 0, 0, 1.0f);break;
                case 4:S_ID_stop[4]=S_Pool.play(S_ID_load[4], 0.9f, 0.9f, 0, 0, 1.0f);break;
                case 5:S_ID_stop[5]=S_Pool.play(S_ID_load[5], 0.9f, 0.9f, 0, 0, 1.0f);break;
                case 6:S_ID_stop[6]=S_Pool.play(S_ID_load[6], 0.9f, 0.9f, 0, 0, 1.0f);break;
                case 7:S_ID_stop[7]=S_Pool.play(S_ID_load[7], 0.9f, 0.9f, 0, 0, 1.0f);break;
                case 8:S_ID_stop[8]=S_Pool.play(S_ID_load[8], 0.9f, 0.9f, 0, 0, 1.0f);break;
                case 9:S_ID_stop[9]=S_Pool.play(S_ID_load[9], 0.9f, 0.9f, 0, 0, 1.0f);break;
                case 10:S_ID_stop[10]=S_Pool.play(S_ID_load[10], 0.9f, 0.9f, 0, 0, 1.0f);break;
                case 11:S_ID_stop[11]=S_Pool.play(S_ID_load[11], 0.9f, 0.9f, 0, 0, 1.0f);break;
            }
        }
    }
    public void SoundStop(HashMap<String, Integer> SM){
        for (String ToneName : SM.keySet()) {
            Integer tone = SM.get(ToneName);
            switch (tone){
                case 0:S_Pool.stop(S_ID_stop[0]);break;
                case 1:S_Pool.stop(S_ID_stop[1]);break;
                case 2:S_Pool.stop(S_ID_stop[2]);break;
                case 3:S_Pool.stop(S_ID_stop[3]);break;
                case 4:S_Pool.stop(S_ID_stop[4]);break;
                case 5:S_Pool.stop(S_ID_stop[5]);break;
                case 6:S_Pool.stop(S_ID_stop[6]);break;
                case 7:S_Pool.stop(S_ID_stop[7]);break;
                case 8:S_Pool.stop(S_ID_stop[8]);break;
                case 9:S_Pool.stop(S_ID_stop[9]);break;
                case 10:S_Pool.stop(S_ID_stop[10]);break;
                case 11:S_Pool.stop(S_ID_stop[11]);break;
            }
        }
    }
    public void SoundLoad(){
        Log.i("テスト  ", "SoundPool00");

        S_Pool= new SoundPool(5, AudioManager.STREAM_MUSIC,0);

        S_ID_load[0]=S_Pool.load(this,R.raw.sound_00,1);
        S_ID_load[1]=S_Pool.load(this,R.raw.sound_01,1);
        S_ID_load[2]=S_Pool.load(this,R.raw.sound_02,1);
        S_ID_load[3]=S_Pool.load(this,R.raw.sound_03,1);
        S_ID_load[4]=S_Pool.load(this,R.raw.sound_04,1);
        S_ID_load[5]=S_Pool.load(this,R.raw.sound_05,1);
        S_ID_load[6]=S_Pool.load(this,R.raw.sound_06,1);
        S_ID_load[7]=S_Pool.load(this,R.raw.sound_07,1);
        S_ID_load[8]=S_Pool.load(this,R.raw.sound_08,1);
        S_ID_load[9]=S_Pool.load(this,R.raw.sound_09,1);
        S_ID_load[10]=S_Pool.load(this,R.raw.sound_10,1);
        S_ID_load[11]=S_Pool.load(this,R.raw.sound_11,1);

    }
    public void SoundRelease() {
        S_Pool.release();
        S_Pool.release();
        S_Pool.release();
        S_Pool.release();
        S_Pool.release();
        S_Pool.release();
        S_Pool.release();
        S_Pool.release();
        S_Pool.release();
        S_Pool.release();
        S_Pool.release();
        S_Pool.release();
    }



    int count=0;
    HashMap<String, Integer> old_map;
    public void Play() {
        String chords2;
        chords2= Pattern.compile("\\n").matcher(chords).replaceAll("");
        chords2=Pattern.compile("\\[.*?\\],").matcher(chords2).replaceAll("");
        HashMap<String, Integer> SoundMap;
        if(!chords2.equals("")){
            String[] data_split = chords2.split(",", 0);
            if(data_split.length-1>count) {
                if(old_map!=null){
                    SoundStop(old_map);
                }
                SoundMap = AC.ChordNameAnalysis(data_split[count]);

                SoundPlay(SoundMap);
                old_map=SoundMap;
                count++;
            }else{
                if(old_map!=null)SoundStop(old_map);
                SoundMap = AC.ChordNameAnalysis(data_split[count]);
                SoundPlay(SoundMap);
                count=0;
                new AlertDialog.Builder(this)
                        //.setTitle("演奏終了")
                        .setMessage("演奏終了しました。")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
            }
        }else{
            new AlertDialog.Builder(this)
                    .setTitle("コードの再生について")
                    .setMessage("使用方法を見ますか？")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent dbIntent = new Intent(getApplication(), Hint.class);
                            startActivity(dbIntent);
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        }
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
                    case R.id.action_sound:
                        Play();
                        Log.i("テスト  ", "音");
                        return true;
                    case R.id.action_new:
                        Intent dbIntent2 = new Intent(getApplication(),ChordInfo.class);
                        dbIntent2.putExtra("id", id);
                        startActivity(dbIntent2);
                        overridePendingTransition(0, 0);
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
        setContentView(R.layout.song_page);


        Intent intent = getIntent();
        id = intent.getIntExtra("id",1);



        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "select title,data,artist,chords,key from note where id ="+ id +";";
        Cursor c = db.rawQuery(sql,null);
        c.moveToFirst();

        String title = c.getString(0);
        String data = c.getString(1);
        String artist = c.getString(2);
        chords = c.getString(3);
        int key_i = c.getInt(4);
        toolbar(title,artist);



        String Chord_No[] = {"C", "C#","D","D#","E","F","F#","G","G#","A","A#","B"};
        String Key;
        if(key_i<13){
            Key="Key="+Chord_No[key_i];
        }else{
            Key="Key=?";
        }




        // -----タブレイアウト--------------------------------------------------------------------
        TabLayout mTabLayout;
        // TabLayoutにTabを追加
        mTabLayout = (TabLayout) findViewById(R.id.tablayout);
        mTabLayout.addTab(mTabLayout.newTab());
        mTabLayout.addTab(mTabLayout.newTab());
        mTabLayout.addTab(mTabLayout.newTab());
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        // 各コンテンツとなるフラグメントをセットするアダプターをViewPagerにセット
        // Fragmentを操作するためにコンストラクタの引数にFragmentManagerを渡しスーパークラスにセットします。
        FragmentPA pagerAdapter = new FragmentPA(getSupportFragmentManager());

        // アダプターに各ページ要素となるフラグメントを追加
        pagerAdapter.addFragment(FragmentTab.newInstance(data,Key,0));
        pagerAdapter.addFragment(FragmentTab.newInstance(data,Key,1));
        pagerAdapter.addFragment(FragmentTab.newInstance(data,Key,2));
        viewPager.setAdapter(pagerAdapter);// ViewPagerにアダプタをセット
        mTabLayout.setupWithViewPager(viewPager);// TabLayoutとViewPagerをバインド??必要あるか不明

        // -----タブレイアウト--------------------------------------------------------------------




        c.close();
        db.close();
    }






    @Override
    protected void onResume() {
        super.onResume();
        SoundLoad();
    }

    @Override
    protected void onPause() {
        super.onPause();
        SoundRelease();
    }


}
