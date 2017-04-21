package com.example.liuhaifeng.readerdemo;

import android.app.Application;

import com.squareup.okhttp.OkHttpClient;


/**
 * Created by liuhaifeng on 2017/4/18.
 */

public class Myapplication extends Application {
    public static OkHttpClient myokhttpclient;
    @Override
    public void onCreate() {
        super.onCreate();

        myokhttpclient=new OkHttpClient();

    }
}
