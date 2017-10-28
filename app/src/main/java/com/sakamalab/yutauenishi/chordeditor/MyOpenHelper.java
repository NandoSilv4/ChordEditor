package com.sakamalab.yutauenishi.chordeditor;




import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyOpenHelper extends SQLiteOpenHelper {

    public MyOpenHelper(Context context) {
        super(context, "Note.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE note(id INTEGER PRIMARY KEY," +
                "title TEXT," +
                "data TEXT," +
                "chords TEXT," +
                "artist TEXT," +
                "year INTEGER," +
                "month INTEGER," +
                "day INTEGER);");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //if( oldVersion == 1 && newVersion == 2 ){
        //    db.execSQL("ALTER TABLE note ADD datetime TEXT;" );
        //}
    }
}