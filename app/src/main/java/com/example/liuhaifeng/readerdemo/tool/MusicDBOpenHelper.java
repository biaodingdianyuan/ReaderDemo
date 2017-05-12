package com.example.liuhaifeng.readerdemo.tool;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by liuhaifeng on 2017/5/11.
 */

public class MusicDBOpenHelper extends SQLiteOpenHelper {
        public MusicDBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "music", factory, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE music(num VARCHAR(20),musicname VARCHAR(20),file_link VARCHAE(100),author VARCHAR(80))");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
