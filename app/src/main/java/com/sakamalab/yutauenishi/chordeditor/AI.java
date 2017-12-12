package com.sakamalab.yutauenishi.chordeditor;



import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;


public class AI extends AppCompatActivity {
    MyOpenHelper helper = new MyOpenHelper(this);
    AnalysisChords AC=new AnalysisChords();
    String chords="";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_ai, menu);
        return true;
    }




    SoundPool S_Pool= new SoundPool(5, AudioManager.STREAM_MUSIC,0);
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
            }
        });


        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // アイテムクリック時の処理
                switch (item.getItemId()) {

                    case R.id.action_sound:
                        Play();
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
        setContentView(R.layout.ai);
        toolbar("AI","learning");


        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "select chords from note;";
        Cursor c = db.rawQuery(sql,null);

        boolean mov = c.moveToFirst();
        if(!mov)return;
        SparseArray<String[]> map_A;
        HashMap<String, List<String>> Connect_map_A = new HashMap<>();
        HashMap<String, Integer> Length_map_A= new HashMap<>();
        String allChords_A="------[Aメロ]------\n";
        String allChords_B="------[Bメロ]------\n";
        String first_chord_A,line_n_A,chords_A;
        first_chord_A=line_n_A="";



        SparseArray<String[]> map_B;
        HashMap<String, List<String>> Connect_map_B = new HashMap<>();
        HashMap<String, Integer> Length_map_B= new HashMap<>();
        String first_chord_B,line_n_B,chords_B;
        first_chord_B=line_n_B="";
        int sc;

        while (mov) {
            String chords = c.getString(0);
            chords=AC.UnityRowNumber(chords);
            String[] line_s;
            //----------[Aメロ]-----------------------------------------------------------
            chords_A=AC.SelectPart(chords,"Aメロ");
            sc=AC.CheckPartKey(chords_A);
            switch(sc){
                case 20:
                    break;
                case 0:
                    allChords_A=allChords_A+chords_A+"\n";
                    //----------↓コードの長さ設定↓----------
                    line_s = chords_A.split("\\n", 0);
                    Length_map_A=AC.ChordLengthAnalysis(line_s[0],Length_map_A);
                    //----------↑コードの長さ設定↑----------
                    map_A=AC.StringToMap(chords_A);
                    first_chord_A=first_chord_A+AC.GetChordRoot(AC.Choose_One(map_A,1,1))+",";
                    if(map_A!=null) line_n_A = line_n_A + String.valueOf(map_A.size()) + ",";
                    Connect_map_A=AC.NextChordAnalysis(map_A,Connect_map_A);
                    break;
                default:
                    chords_A = AC.HalfUpDown(chords_A, -sc);
                    allChords_A=allChords_A+chords_A+"\n";
                    //----------↓コードの長さ設定↓----------
                    line_s = chords_A.split("\\n", 0);
                    Length_map_A=AC.ChordLengthAnalysis(line_s[0],Length_map_A);
                    //----------↑コードの長さ設定↑----------
                    map_A=AC.StringToMap(chords_A);
                    first_chord_A=first_chord_A+AC.GetChordRoot(AC.Choose_One(map_A,1,1))+",";
                    if(map_A!=null) line_n_A = line_n_A + String.valueOf(map_A.size()) + ",";
                    Connect_map_A=AC.NextChordAnalysis(map_A,Connect_map_A);
            }
            //----------[Aメロ]-----------------------------------------------------------

            //----------[Bメロ]-----------------------------------------------------------
            chords_B=AC.SelectPart(chords,"Bメロ");
            sc=AC.CheckPartKey(chords_B);
            switch(sc){
                case 0:
                    allChords_B=allChords_B+chords_B+"\n";
                    //----------↓コードの長さ設定↓----------
                    line_s = chords_B.split("\\n", 0);
                    Length_map_B=AC.ChordLengthAnalysis(line_s[0],Length_map_B);
                    //----------↑コードの長さ設定↑----------
                    map_B=AC.StringToMap(chords_B);
                    first_chord_B=first_chord_B+AC.GetChordRoot(AC.Choose_One(map_B,1,1))+",";
                    if(map_B!=null) line_n_B = line_n_B + String.valueOf(map_B.size()) + ",";
                    Connect_map_B=AC.NextChordAnalysis(map_B,Connect_map_B);
                    break;
                case 20:
                    break;
                default:
                    chords_B = AC.HalfUpDown(chords_B, -sc);
                    allChords_B=allChords_B+chords_B+"\n";
                    //----------↓コードの長さ設定↓----------
                    line_s = chords_B.split("\\n", 0);
                    Length_map_B=AC.ChordLengthAnalysis(line_s[0],Length_map_B);
                    //----------↑コードの長さ設定↑----------
                    map_B=AC.StringToMap(chords_B);
                    first_chord_B=first_chord_B+AC.GetChordRoot(AC.Choose_One(map_B,1,1))+",";
                    if(map_B!=null) line_n_B = line_n_B + String.valueOf(map_B.size()) + ",";
                    Connect_map_B=AC.NextChordAnalysis(map_B,Connect_map_B);
            }


            //----------[Bメロ]-----------------------------------------------------------


            mov = c.moveToNext();
        }


        HashMap<String,Integer> UC_A,UC_B;
        //----------[Aメロ]-----------------------------------------------------------
        UC_A=AC.UsedChord(first_chord_A);
        first_chord_A=AC.RandomChoice(UC_A);//first_chord_Aの更新（新しい物を決める)
        line_n_A=AC.RandomChoice(AC.UsedChord(line_n_A));//line_n_Aの更新（新しい物を決める)
        int new_line_n_A=0;
        if(!line_n_A.equals(""))new_line_n_A=Integer.parseInt(line_n_A);

        map_A=AC.FirstChordProgression(first_chord_A, new_line_n_A, Connect_map_A,Length_map_A);//map_Aの使いまわし
        String new_A=AC.MapToString(map_A);
        //----------[Aメロ]-----------------------------------------------------------


        //----------[Bメロ]-----------------------------------------------------------
        UC_B=AC.UsedChord(first_chord_B);
        first_chord_B=AC.RandomChoice(UC_B);//first_chord_Bの更新（新しい物を決める)
        line_n_B=AC.RandomChoice(AC.UsedChord(line_n_B));//line_n_Bの更新（新しい物を決める)
        int new_line_n_B=0;
        if(!line_n_B.equals(""))new_line_n_B=Integer.parseInt(line_n_B);

        map_B=AC.FirstChordProgression(first_chord_B, new_line_n_B, Connect_map_B,Length_map_B);//map_Bの使いまわし
        Log.i("テスト  ", "FirstChordProgression finish");
        String new_B=AC.MapToString(map_B);
        //----------[Bメロ]-----------------------------------------------------------









        new_A="[Aメロ],\n"+new_A+"[Bメロ],\n"+new_B;
        chords=new_A;
        TextView textView = (TextView) findViewById(R.id.text_1);
        textView.setText(new_A);


        String UC="[Aメロ]\n"+AC.UCtoString(UC_A)+"\n[Bメロ]\n"+AC.UCtoString(UC_B);
        TextView textView2 = (TextView) findViewById(R.id.text_2);
        textView2.setText(UC);




        String Length="[Aメロ]\n"+AC.UCtoString(Length_map_A)+"\n[Bメロ]\n"+AC.UCtoString(Length_map_B)+"\n[ランダム]\n"+
                AC.RandomChoice(Length_map_A)+"\n"+AC.RandomChoice(Length_map_B);

        TextView textView3 = (TextView) findViewById(R.id.text_3);
        textView3.setText(Length);


        allChords_A=allChords_A+allChords_B;
        TextView textView4 = (TextView) findViewById(R.id.text_4);
        textView4.setText(allChords_A);



        String test=AC.GetChordRoot("A#m")+"\n"+AC.GetChordRoot("Amaj7")+"\n"+AC.GetChordRoot("A#madd9")+"\n"+AC.GetChordRoot("Amadd9");
        TextView textView5 = (TextView) findViewById(R.id.text_5);
        textView5.setText(test);





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
