package com.example.liuhaifeng.readerdemo;

import android.app.Application;
import android.app.FragmentContainer;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.liuhaifeng.readerdemo.tool.MusicDBOpenHelper;
import com.example.liuhaifeng.readerdemo.tool.Urls;
import com.squareup.okhttp.OkHttpClient;


/**
 * Created by liuhaifeng on 2017/4/18.
 */

public class Myapplication extends Application {
    public static OkHttpClient myokhttpclient;
    public  static MusicDBOpenHelper musicDBOpenHelper;
    @Override
    public void onCreate() {
        super.onCreate();

        myokhttpclient=new OkHttpClient();
        musicDBOpenHelper=new MusicDBOpenHelper(getApplicationContext(),"music",null,1);
//        SQLiteDatabase db = musicDBOpenHelper.getWritableDatabase();
//        Cursor cursor= db.query("music",null,null,null,null,null,null);
//        if(cursor.) {
//
//            ContentValues values = new ContentValues();
//            values.put("num", "1");
//            values.put("musicname", "喵小姐");
//            values.put("file_link", Urls.URL_MUSIC_SONG);
//            values.put("author", "王晓天");
//            db.insert("music", null, values);
//        }

    }
}
