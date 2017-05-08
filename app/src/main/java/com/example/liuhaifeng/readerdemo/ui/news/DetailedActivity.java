package com.example.liuhaifeng.readerdemo.ui.news;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.liuhaifeng.readerdemo.Myapplication;
import com.example.liuhaifeng.readerdemo.R;
import com.example.liuhaifeng.readerdemo.tool.Urls;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by liuhaifeng on 2017/5/6.
 */

public class DetailedActivity extends AppCompatActivity {
    @InjectView(R.id.news_detailed_toolbar)
    Toolbar newsDetailedToolbar;
    @InjectView(R.id.news_detailed_img)
    ImageView newsDetailedImg;
    @InjectView(R.id.news_detailed)
    WebView newsDetailed;
    @InjectView(R.id.news_detailed_toolbar_title)
    TextView newsDetailedToolbarTitle;
    @InjectView(R.id.news_detailed_toolbar_back)
    ImageView newsDetailedToolbarBack;
    Newbean.DataBean bean;
    private Handler myhandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    bean= (Newbean.DataBean) msg.obj;
                    String str;
                   if(bean.getContent().equals("")){
                       str=bean.getDigest();
                   }else{
                       str=bean.getContent();
                   }
                    newsDetailed.loadDataWithBaseURL(null,str,"text/html", "utf-8",null);
                    break;
            }


        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        ButterKnife.inject(this);
        setSupportActionBar(newsDetailedToolbar);

        Data bean= (Data) getIntent().getSerializableExtra("news");
        newsDetailedToolbarTitle.setText(bean.getTitle());
        getdata(getIntent().getStringExtra("tableNum"),bean.getNews_id()+"");
        newsDetailedToolbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    public void getdata(final String tableNum , final String news_id){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url= Urls.URL_NEW_DETAILED+tableNum+"&news_id="+news_id;
                Request request=new Request.Builder().get().url(url).build();
                Gson gson=new Gson();

                try {
                    Response response= Myapplication.myokhttpclient.newCall(request).execute();
                    if(response.isSuccessful()){
                        String json = response.body().string();
                        int end = json.length() - 1;
                        Log.d("****", end + "");


                        json=json.substring(2, end);
                        Newbean bean = gson.fromJson(json, new TypeToken<Newbean>() {
                        }.getType());
                        myhandler.obtainMessage(1,bean.getData()).sendToTarget();

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();



    }
}
