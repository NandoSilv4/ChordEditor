package com.example.yutauenishi.chordeditor;




import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.TextView;

public class SongPage extends AppCompatActivity {
    MyOpenHelper helper = new MyOpenHelper(this);
    public String chordsALL;
    int id =1;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_page, menu);
        return true;
    }

    public void test(String a,int id){
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql1 = "update note set chords = '" + a + "' where id = "+id+";";
        db.execSQL(sql1);
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
        String sql = "select title,data,artist from note where id ="+ id +";";
        Cursor c = db.rawQuery(sql,null);
        boolean mov1 = c.moveToFirst();

        String title = c.getString(0);
        String data = c.getString(1);
        String artist = c.getString(2);
        toolbar(title,artist);




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
        pagerAdapter.addFragment(FragmentTab.newInstance(data,0));
        pagerAdapter.addFragment(FragmentTab.newInstance(data,1));
        pagerAdapter.addFragment(FragmentTab.newInstance(data,2));
        viewPager.setAdapter(pagerAdapter);// ViewPagerにアダプタをセット
        mTabLayout.setupWithViewPager(viewPager);// TabLayoutとViewPagerをバインド??必要あるか不明

        // -----タブレイアウト--------------------------------------------------------------------




        c.close();
        db.close();
    }




    @Override
    protected void onPause() {
        super.onPause();
        // ここでファイル保存処理を行う
    }




}
