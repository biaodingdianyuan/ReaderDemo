package com.example.liuhaifeng.readerdemo.tool;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.liuhaifeng.readerdemo.Myapplication;
import com.example.liuhaifeng.readerdemo.ui.news.Lv_fragment_new;
import com.example.liuhaifeng.readerdemo.ui.news.NewsBean;
import com.example.liuhaifeng.readerdemo.ui.news.Urls;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

/**
 * Created by liuhaifeng on 2017/4/18.
 */

public class OkHttpUtil {
    public String result = null;

    Handler myhandler = null;

    public void getdata(String num, Handler handler) {
        myhandler = handler;
        String url = Urls.URL + num + "&pagesize=20&callback=?&justList=1";
        Type listType = new TypeToken<NewsBean>() {
        }.getType();
        NewsBean news = null;
        Request request = new Request.Builder().get().url(url).build();
        try {
            Response response = Myapplication.myokhttpclient.newCall(request).execute();
            if (response.isSuccessful()) {

                Gson gson = new Gson();
                String json = response.body().string();
                int end = json.length() - 1;
                Log.d("****", end + "");

                Log.d("****", json.substring(2, end));
                news = gson.fromJson(json.substring(2, end), new TypeToken<NewsBean>() {
                }.getType());
                myhandler.obtainMessage(1, news).sendToTarget();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
