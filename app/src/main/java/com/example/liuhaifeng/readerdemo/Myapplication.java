package com.example.liuhaifeng.readerdemo;

import android.app.Application;

import com.squareup.okhttp.OkHttpClient;

import java.io.BufferedInputStream;
import java.io.IOException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import static com.example.liuhaifeng.readerdemo.tool.OkHttpUtil.getSafeFromServer;

/**
 * Created by liuhaifeng on 2017/4/18.
 */

public class Myapplication extends Application {
    public static OkHttpClient myokhttpclient;
    @Override
    public void onCreate() {
        super.onCreate();
        try {
            myokhttpclient=new OkHttpClient().setSocketFactory( getSafeFromServer(
                    new BufferedInputStream(getAssets().open("openssl_cer(1).cer"))
            ).getSocketFactory()).setHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
