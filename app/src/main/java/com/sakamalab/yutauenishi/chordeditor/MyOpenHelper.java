package com.sakamalab.yutauenishi.chordeditor;




import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyOpenHelper extends SQLiteOpenHelper {

    MyOpenHelper(Context context) {
        super(context, "Note.db", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE note(id INTEGER PRIMARY KEY," +
                "title TEXT," +
                "data TEXT," +
                "chords TEXT," +
                "key INTEGER," +
                "artist TEXT," +
                "year INTEGER," +
                "month INTEGER," +
                "day INTEGER);");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if( oldVersion == 1 && newVersion == 2 ){
            db.execSQL("ALTER TABLE note ADD key INTEGER AFTER chords;" );
            String sql = "UPDATE note SET key = 20;";
            db.execSQL(sql);
        }
    }
}
