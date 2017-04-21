package com.example.liuhaifeng.readerdemo.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.media.Image;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Time;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.liuhaifeng.readerdemo.R;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by liuhaifeng on 2017/4/19.
 */


public class AppStartActivity extends AppCompatActivity {
    private RelativeLayout img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        super.onCreate(savedInstanceState);
        final View view = View.inflate(this, R.layout.activity_appstart, null);
        setContentView(view);
        img = (RelativeLayout) findViewById(R.id.imageView2);
        long time=System.currentTimeMillis();

        final Calendar mCalendar=Calendar.getInstance();
        mCalendar.setTimeInMillis(time);


        int h  =mCalendar.get(Calendar.HOUR);
        if (5 < h && h < 10) {
            img.setBackground(getResources().getDrawable(R.mipmap.morning));
        } else if (10 < h && h < 17) {
            img.setBackground(getResources().getDrawable(R.mipmap.afternoon));

        } else {
            img.setBackground(getResources().getDrawable(R.mipmap.night));
        }
        //渐变展示启动屏
        AlphaAnimation aa = new AlphaAnimation(0.3f, 1.0f);
        aa.setDuration(2000);
        view.startAnimation(aa);
        aa.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationEnd(Animation arg0) {
                ScaleAnimation sa=new ScaleAnimation(1.0f, 1.4f, 1.0f, 1.4f,
                        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                sa.setDuration(3000);
                view.startAnimation(sa);
                sa.setFillAfter(true);
                sa.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        startActivity(new Intent(AppStartActivity.this,MainActivity.class));
                        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                        finish();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationStart(Animation animation) {
            }

        });


    }
}
