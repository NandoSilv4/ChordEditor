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



    SoundPool s_00,s_01,s_02,s_03,s_04,s_05,s_06,s_07,s_08,s_09,s_10,s_11;
    int ID_00,ID_01,ID_02,ID_03,ID_04,ID_05,ID_06,ID_07,ID_08,ID_09,ID_10,ID_11;
    public void SoundPlay(HashMap<String, Integer> SM){
        for (String ToneName : SM.keySet()) {
            Integer tone = SM.get(ToneName);
            switch (tone){
                case 0:s_00.play(ID_00, 0.9f, 0.9f, 0, 0, 1.0f);break;
                case 1:s_01.play(ID_01, 0.9f, 0.9f, 0, 0, 1.0f);break;
                case 2:s_02.play(ID_02, 0.9f, 0.9f, 0, 0, 1.0f);break;
                case 3:s_03.play(ID_03, 0.9f, 0.9f, 0, 0, 1.0f);break;
                case 4:s_04.play(ID_04, 0.9f, 0.9f, 0, 0, 1.0f);break;
                case 5:s_05.play(ID_05, 0.9f, 0.9f, 0, 0, 1.0f);break;
                case 6:s_06.play(ID_06, 0.9f, 0.9f, 0, 0, 1.0f);break;
                case 7:s_07.play(ID_07, 0.9f, 0.9f, 0, 0, 1.0f);break;
                case 8:s_08.play(ID_08, 0.9f, 0.9f, 0, 0, 1.0f);break;
                case 9:s_09.play(ID_09, 0.9f, 0.9f, 0, 0, 1.0f);break;
                case 10:s_10.play(ID_10, 0.9f, 0.9f, 0, 0, 1.0f);break;
                case 11:s_11.play(ID_11, 0.9f, 0.9f, 0, 0, 1.0f);break;
            }
        }
    }
    public void SoundLoad(){
        s_00= new SoundPool(5, AudioManager.STREAM_MUSIC,0);
        s_01= new SoundPool(5, AudioManager.STREAM_MUSIC,0);
        s_02= new SoundPool(5, AudioManager.STREAM_MUSIC,0);
        s_03= new SoundPool(5, AudioManager.STREAM_MUSIC,0);
        s_04= new SoundPool(5, AudioManager.STREAM_MUSIC,0);
        s_05= new SoundPool(5, AudioManager.STREAM_MUSIC,0);
        s_06= new SoundPool(5, AudioManager.STREAM_MUSIC,0);
        s_07= new SoundPool(5, AudioManager.STREAM_MUSIC,0);
        s_08= new SoundPool(5, AudioManager.STREAM_MUSIC,0);
        s_09= new SoundPool(5, AudioManager.STREAM_MUSIC,0);
        s_10= new SoundPool(5, AudioManager.STREAM_MUSIC,0);
        s_11= new SoundPool(5, AudioManager.STREAM_MUSIC,0);
        ID_00=s_00.load(this,R.raw.sound_00,1);
        ID_01=s_01.load(this,R.raw.sound_01,1);
        ID_02=s_02.load(this,R.raw.sound_02,1);
        ID_03=s_03.load(this,R.raw.sound_03,1);
        ID_04=s_04.load(this,R.raw.sound_04,1);
        ID_05=s_05.load(this,R.raw.sound_05,1);
        ID_06=s_06.load(this,R.raw.sound_06,1);
        ID_07=s_07.load(this,R.raw.sound_07,1);
        ID_08=s_08.load(this,R.raw.sound_08,1);
        ID_09=s_09.load(this,R.raw.sound_09,1);
        ID_10=s_10.load(this,R.raw.sound_10,1);
        ID_11=s_11.load(this,R.raw.sound_11,1);
    }
    public void SoundRelease() {
        s_00.release();
        s_01.release();
        s_02.release();
        s_03.release();
        s_04.release();
        s_05.release();
        s_06.release();
        s_07.release();
        s_08.release();
        s_09.release();
        s_10.release();
        s_11.release();
    }
    int count=0;
    public void Play() {
        String chords2;
        chords2= Pattern.compile("\\n").matcher(chords).replaceAll("");
        chords2=Pattern.compile("\\[.*?\\],").matcher(chords2).replaceAll("");
        HashMap<String, Integer> SoundMap;
        if(!chords2.equals("")){
            String[] data_split = chords2.split(",", 0);
            if(data_split.length-1>count) {
                SoundMap = AC.ChordNameAnalysis(data_split[count]);
                SoundPlay(SoundMap);
                count++;
            }else{
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
        String sql = "select title,data,artist,chords from note where id ="+ id +";";
        Cursor c = db.rawQuery(sql,null);
        c.moveToFirst();

        String title = c.getString(0);
        String data = c.getString(1);
        String artist = c.getString(2);
        chords = c.getString(3);
        toolbar(title,artist);


        AnalysisChords AC=new AnalysisChords();
        String Key=AC.FindKey(chords);



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
