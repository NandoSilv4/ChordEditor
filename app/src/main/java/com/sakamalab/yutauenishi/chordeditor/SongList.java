package com.sakamalab.yutauenishi.chordeditor;



import android.app.AlertDialog;
import android.content.Intent;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

import android.database.DatabaseUtils;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SongList extends AppCompatActivity {
    MyOpenHelper helper = new MyOpenHelper(this);

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    //新規作成時タイトルを聞く
    public void TitleAsk(final int flag){
        //テキスト入力を受け付けるビューを作成します。
        //外枠とパーツの作成
        final LinearLayout layout = new LinearLayout(getApplicationContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        final LinearLayout Hlayout1 = new LinearLayout(getApplicationContext());
        Hlayout1.setOrientation(LinearLayout.HORIZONTAL);
        final LinearLayout Hlayout2 = new LinearLayout(getApplicationContext());
        Hlayout2.setOrientation(LinearLayout.HORIZONTAL);



        final EditText editView = new EditText(SongList.this);
        final EditText editView2 = new EditText(SongList.this);
            editView.setInputType(InputType.TYPE_CLASS_TEXT );
            editView2.setInputType(InputType.TYPE_CLASS_TEXT );

        //メッセージの設定
        final TextView song_title = new TextView(getApplicationContext());
        final TextView artist = new TextView(getApplicationContext());
            song_title.setText(" 　曲名:");
            song_title.setTextColor(Color.BLACK);
            song_title.setTextSize(18);
            artist.setText(" 作者名:");
            artist.setTextColor(Color.BLACK);
            artist.setTextSize(18);
        //外枠にパーツを組み込む
        Hlayout1.addView(song_title);
        Hlayout1.addView(editView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        Hlayout2.addView(artist);
        Hlayout2.addView(editView2, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        layout.addView(Hlayout1);
        layout.addView(Hlayout2);

        AlertDialog.Builder b = new AlertDialog.Builder(SongList.this);
        b.setTitle(" ");
        b.setView(layout);
        b.setPositiveButton(android.R.string.ok, null);
        b.setNegativeButton(android.R.string.cancel, null);


        final AlertDialog dialog = b.show();
        Button buttonP = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        Button buttonN = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        final SQLiteDatabase db = helper.getReadableDatabase();
        final String old_title;

        if(flag>=0){
            String sql = "select title,artist from note where id ="+ flag +";";
            Cursor c = db.rawQuery(sql,null);
            c.moveToFirst();
            old_title = c.getString(0);
            editView.setText(old_title);
            String art = c.getString(1);
            editView2.setText(art);
            c.close();
        }else {
            old_title = "";
        }
        // 通常のViewのように実装します。
        buttonP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editView.getText().toString().trim();
                String artist = editView2.getText().toString().trim();
                artist = artist.replaceAll("'", "''");
                title = title.replaceAll("'", "''");
                if (title.equals("")) {
                    Toast.makeText(SongList.this, "タイトルを正しく入力してください。", Toast.LENGTH_SHORT).show();
                }else{

                    String sql = "select * from note where title ='"+ title +"';";
                    Cursor c = db.rawQuery(sql,null);
                    boolean torf = c.moveToFirst();//cの中をすべて見終わるまでまでtrue
                    if(torf){
                        if(old_title.equals(title)){//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!artist更新ZZZZZZZZZZZZ!!!!!!!!!!!!
                            String sql1 = "update note set artist = '"+ artist +"' where id = "+flag+";";
                            db.execSQL(sql1);
                            Intent dbIntent = new Intent(getApplication(), SongList.class);
                            //dbIntent.putExtra("id", flag);
                            startActivity(dbIntent);
                            overridePendingTransition(0, 0);
                            dialog.dismiss();
                        }else{
                            Toast.makeText(SongList.this, "すでに存在するタイトルです。", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }else {
                        if(flag<0) {
                            int recode = (int) DatabaseUtils.queryNumEntries(db, "note");
                            String sql2 = "insert into note(title,artist) values('" + title + "','"+ artist +"');";
                            db.execSQL(sql2);
                            Intent dbIntent = new Intent(getApplication(), LyricsEdit.class);
                            dbIntent.putExtra("id", recode + 1);
                            startActivity(dbIntent);
                            overridePendingTransition(0, 0);
                            dialog.dismiss();
                        }else{

                            String sql3 = "update note set title = '"+ title +"' where id = "+flag+";";
                            db.execSQL(sql3);
                            String sql4 = "update note set artist = '"+ artist +"' where id = "+flag+";";
                            db.execSQL(sql4);
                            Intent dbIntent = new Intent(getApplication(), SongList.class);
                            //dbIntent.putExtra("id", flag);
                            startActivity(dbIntent);
                            dialog.dismiss();
                        }
                    }
                    c.close();
                }

                db.close();
            }
        });

        // 通常のViewのように実装します。
        buttonN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }





    // ツールバー
    public void toolbar(){

        // ツールバーをアクションバーとして使う
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // UPナビゲーションを有効化する
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dbIntent = new Intent(getApplication(),MainActivity.class);
                startActivity(dbIntent);
                overridePendingTransition(0, 0);
            }
        });
        */

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // アイテムクリック時の処理
                switch (item.getItemId()) {
                    case R.id.action_new:
                        TitleAsk(-1);
                        Log.i("テスト  ", "新規作成");
                        return true;
                    case R.id.action_hint:
                        new AlertDialog.Builder(SongList.this)
                                //.setTitle("ヒント")
                                .setMessage("Chord Editorの使用方法を見ますか？")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent dbIntent = new Intent(getApplication(), Hint.class);
                                        startActivity(dbIntent);
                                    }
                                })
                                .setNegativeButton("Cancel", null)
                                .show();
                        Log.i("テスト  ", "ヒント");
                        return true;
                    default:
                        return false;
                }
            }
        });

    }


    //private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ListView listView;
        super.onCreate(savedInstanceState);
        // ここで1秒間スリープし、スプラッシュを表示させたままにする。
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            Log.i("エラー", "スプラッシュでエラー");
        }

        // スプラッシュthemeを通常themeに変更する
        setTheme(R.style.AppTheme);
        setContentView(R.layout.songlist);
        toolbar();


        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.query("note", new String[] { "id", "title", "artist","year","month","day"}, null,null, null, null, null,null);
        boolean mov = c.moveToFirst();


        // ListView を取得
        listView = (ListView) findViewById(R.id.nameList);
        List<Map<String, String>> retDataList = new ArrayList<>();
        Map<String, String> data;



        // リストビューに渡すアダプタを生成します。
        SimpleAdapter adapter2 = new SimpleAdapter(this, retDataList,
                R.layout.list_layout, new String[] { "title", "artist","YMD" },
                new int[] {R.id.item1, R.id.item2,R.id.item3 });

        listView.setAdapter(adapter2);
        //listView.setTextFilterEnabled(true);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent dbIntent = new Intent(getApplication(),SongPage.class);
                dbIntent.putExtra("id", position+1);
                startActivity(dbIntent);
                overridePendingTransition(0, 0);
            }
        });



        // SearchViewにOnQueryChangeListenerを設定
        // SearchView を取得
        //SearchView search = (SearchView) findViewById(R.id.searchView);
        //search.setOnQueryTextListener(
        //    new SearchView.OnQueryTextListener(){
        //        public boolean onQueryTextChange(String text){
        //            Filter filter = ((Filterable) listView.getAdapter()).getFilter();
        //            if(text==null || text.equals("")){
        //                filter.filter(null);
        //            }else{
        //                filter.filter(text);
        //            }
        //            return false;
        //        }

        //        public boolean onQueryTextSubmit(String arg0){
        //            return false;
        //        }
        //    }
        //);






        int i=1;
        while (mov) {
            int id = c.getInt(0);
            if(id !=i){
                String sql2 = "update note set id = "+i+" where id = "+id+";";
                db.execSQL(sql2);
                id=i;
                Log.i("テスト  ", "更新あり"+id);
            }else{
                Log.i("テスト  ", "更新なし"+id+"  "+i);
            }
            String str = c.getString(1);
            String str2 = c.getString(2);
            data = new HashMap<>();
            data.put("title",str);
            data.put("artist", str2);


            int year= c.getInt(3);
            int month= c.getInt(4);
            int day= c.getInt(5);
            Calendar cal = Calendar.getInstance();
            int sYear = cal.get(Calendar.YEAR);
            if(year==sYear){
                String ymd = month +"月"+day+"日";
                data.put("YMD", ymd);
            }else {
                String ymd = year + "年" + month + "月" + day + "日";
                data.put("YMD", ymd);
            }

            retDataList.add(data);
            mov = c.moveToNext();
            i++;
        }



        // リストビューのアイテムが長押しされた時に呼び出す。
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(final AdapterView<?> parent, View view,
                                           final int position, long id) {


                final CharSequence[] shareItems = {"開く", "曲名・作者名 変更", "削除","キャンセル"};

                AlertDialog.Builder builder = new AlertDialog.Builder(SongList.this);
                builder.setItems(shareItems, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item) {
                            case 0:
                                Log.i("テスト  ", "開く");
                                Intent dbIntent = new Intent(getApplication(),SongPage.class);
                                dbIntent.putExtra("id", position+1);
                                startActivity(dbIntent);
                                overridePendingTransition(0, 0);
                                break;
                            case 1:
                                Log.i("テスト  ", "曲名・作者名 変更");
                                TitleAsk(position+1);
                                break;
                            case 2:
                                Log.i("テスト  ", "削除");
                                SQLiteDatabase db2 = helper.getReadableDatabase();
                                db2.delete("note", "id="+(position+1),null);
                                db2.close();
                                finish();
                                startActivity(getIntent());
                                overridePendingTransition(0, 0);

                                break;
                            case 3:
                                Log.i("テスト  ", "キャンセル");
                                break;
                        }
                    }
                });
                AlertDialog shareAlert = builder.create();
                shareAlert.show();



                return true;
            }
        });



        c.close();
        db.close();
    }







    @Override
    protected void onPause() {
        super.onPause();
        // ここでファイル保存処理を行う
    }
}
