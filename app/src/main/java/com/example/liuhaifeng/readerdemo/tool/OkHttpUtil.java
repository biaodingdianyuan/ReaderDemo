package com.example.liuhaifeng.readerdemo.tool;

import android.os.Handler;
import android.util.Log;

import com.example.liuhaifeng.readerdemo.Myapplication;
import com.example.liuhaifeng.readerdemo.ui.news.NewsBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * Created by liuhaifeng on 2017/4/18.
 */

public class OkHttpUtil {
    public String result = null;

    Handler myhandler = null;

    public void getdata(String num, Handler handler,int page) {
        myhandler = handler;
        String url = Urls.URL_NEW_LIST + num + "&pagesize=20&callback=?&justList=1&page="+page;
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

                //TODO   添加新闻详细内容


                myhandler.obtainMessage(1, news).sendToTarget();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
