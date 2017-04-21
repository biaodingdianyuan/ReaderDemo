package com.example.liuhaifeng.readerdemo.ui.news;

import android.app.Service;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.liuhaifeng.readerdemo.R;
import com.example.liuhaifeng.readerdemo.tool.OkHttpUtil;
import com.google.gson.Gson;
import com.google.gson.internal.Streams;
import com.google.gson.reflect.TypeToken;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by liuhaifeng on 2017/4/19.
 */

public class Lv_fragment_new extends Fragment {


    @InjectView(R.id.lv_new_Rv)
    RecyclerView lvNewRv;
    @InjectView(R.id.lv_new_srl)
    SwipeRefreshLayout lvNewSrl;
    @InjectView(R.id.lv_new_img)
    RollPagerView lvNewImg;
    String[] img = new String[3];
    String[] title = new String[3];
    TestNormalAdapter adapter;
    private String mParam1;
    public Handler myhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    NewsBean news = (NewsBean) msg.obj;


                    for (int i = 0; i < 3; i++) {
//                        if( news.getData().get(i).getTop_image()){}
                        title[i] = news.getData().get(i).getTitle();
                        img[i] = news.getData().get(i).getTop_image();
                    }
                    lvNewImg.setVisibility(View.VISIBLE);
                    lvNewImg.setPlayDelay(5000);
                    //设置透明度
                    lvNewImg.setAnimationDurtion(500);
                    adapter.setimg(img, title);
                    adapter.notifyDataSetChanged();
                    break;
            }

        }
    };

    public static Lv_fragment_new newInstance(int param1) {
        Lv_fragment_new fragment = new Lv_fragment_new();
        Bundle args = new Bundle();
        args.putString("key", param1+"");

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString("key");

        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lv, container, false);
        ButterKnife.inject(this, view);
        new Thread(new Runnable() {
            @Override
            public void run() {
                new OkHttpUtil().getdata(mParam1, myhandler);
            }
        }).start();
        adapter = new TestNormalAdapter();
        adapter.setimg(img, title);
        lvNewImg.setVisibility(View.GONE);
        lvNewImg.setAdapter(adapter);

        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    /**
     * 图片轮播适配器
     */
    private class TestNormalAdapter extends StaticPagerAdapter {


        private String[] imgs = {};
        private String[] titles = {};

        private void setimg(String[] imgs, String[] title) {
            this.imgs = imgs;
            this.titles = title;

        }


        @Override
        public View getView(ViewGroup container, int position) {
            View view = getLayoutInflater(getArguments()).inflate(R.layout.img_vp, null);
            ImageView img = (ImageView) view.findViewById(R.id.img_vp_img);
            TextView title = (TextView) view.findViewById(R.id.img_vp_title);
            title.setText(titles[position]);

            Picasso.with(getContext()).load(imgs[position]).into(img);
            img.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            return view;
        }


        @Override
        public int getCount() {
            return imgs.length;
        }
    }

    /**
     * 新闻列表适配器
     */
    class RecycleAdapter extends RecyclerView.Adapter {
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }
    }

    class NewsHolder extends RecyclerView.ViewHolder {
        public NewsHolder(View itemView) {
            super(itemView);
        }
    }


}
