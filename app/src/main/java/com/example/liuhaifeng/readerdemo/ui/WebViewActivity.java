package com.example.liuhaifeng.readerdemo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.webkit.WebView;

import com.example.liuhaifeng.readerdemo.R;


/**
 * Created by liuhaifeng on 2017/4/18.
 */

public class WebViewActivity extends AppCompatActivity {
    private WebView webview;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        webview = new WebView(this);
        //设置WebView属性，能够执行Javascript脚本
        webview.getSettings().setJavaScriptEnabled(true);
        //加载需要显示的网页
        webview.loadUrl(getIntent().getStringExtra("url"));
        //设置Web视图
        setContentView(webview);

    }
    @Override
    public void onBackPressed() {
        if (webview.canGoBack()) {
            webview.goBack();
        }
        super.onBackPressed();
    }


}
