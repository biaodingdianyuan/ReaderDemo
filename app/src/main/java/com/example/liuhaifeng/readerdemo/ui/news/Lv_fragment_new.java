package com.example.liuhaifeng.readerdemo.ui.news;

import android.app.Service;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liuhaifeng.readerdemo.R;
import com.example.liuhaifeng.readerdemo.tool.OkHttpUtil;
import com.google.gson.Gson;
import com.google.gson.internal.Streams;
import com.google.gson.reflect.TypeToken;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    String[] img = null;
    String[] title = null;
    TestNormalAdapter adapter_img;
    RecycleAdapter adapter_recy;
    NewsBean news;
    List<Data> d;
    private String mParam1;
    public Handler myhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    img = new String[3];
                    title = new String[3];
                    news = (NewsBean) msg.obj;
                    d.clear();
                    d.addAll(news.getData());
                    Log.d("**", news.getData().size() + "");
                    if (lvNewImg != null) {
                        lvNewImg.setVisibility(View.VISIBLE);
                    } else {
                        Toast.makeText(getContext(), "kong 了", Toast.LENGTH_LONG).show();
                        lvNewImg = (RollPagerView) view.findViewById(R.id.lv_new_img);
                    }
                    for (int i = 0; i < 3; i++) {
                        if (news.getData().get(i).getTop_image() != "") {
                            title[i] = news.getData().get(i).getTitle();
                            img[i] = news.getData().get(i).getTop_image();
                            news.getData().remove(i);
                        } else if (news.getData().get(i).getText_image0() != "") {
                            title[i] = news.getData().get(i).getTitle();
                            img[i] = news.getData().get(i).getText_image0();
                            news.getData().remove(i);
                        } else if (news.getData().get(i).getText_image1() != "") {
                            title[i] = news.getData().get(i).getTitle();
                            img[i] = news.getData().get(i).getText_image1();
                            news.getData().remove(i);
                        } else {
                            lvNewImg.setVisibility(View.GONE);
                        }
                    }
                    if (lvNewImg.getVisibility() == 0) {
                        lvNewImg.setPlayDelay(5000);
                        //设置透明度
                        lvNewImg.setAnimationDurtion(500);
                        adapter_img.setimg(img, title);
                        adapter_img.notifyDataSetChanged();
                    }
                    adapter_recy.notifyDataSetChanged();
                    lvNewRv.scrollToPosition(adapter_recy.getItemCount() - 1);
                    lvNewSrl.setRefreshing(false);
                    break;
            }
        }
    };
    private View view;

    public static Lv_fragment_new newInstance(int param1) {
        Lv_fragment_new fragment = new Lv_fragment_new();
        Bundle args = new Bundle();
        args.putString("key", param1 + "");
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
        view = inflater.inflate(R.layout.fragment_lv, container, false);
        ButterKnife.inject(this, view);
        new Thread(new Runnable() {
            @Override
            public void run() {
                new OkHttpUtil().getdata(mParam1, myhandler);
            }
        }).start();
        img = new String[0];
        title = new String[0];
        adapter_img = new TestNormalAdapter();
        adapter_img.setimg(img, title);

        d = new ArrayList<Data>();

        adapter_recy = new RecycleAdapter(getActivity(), d);

        lvNewImg.setVisibility(View.GONE);
        lvNewImg.setAdapter(adapter_img);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        lvNewRv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL, true));
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        lvNewRv.setItemAnimator(new DefaultItemAnimator());
        lvNewRv.setAdapter(adapter_recy);


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
            final ImageView img = (ImageView) view.findViewById(R.id.img_vp_img);
            TextView title = (TextView) view.findViewById(R.id.img_vp_title);
            title.setText(titles[position]);


            Transformation transformation = new Transformation() {

                @Override
                public Bitmap transform(Bitmap source) {

                    int targetWidth = img.getWidth();
                    Log.i("*****", "source.getHeight()=" + source.getHeight() + ",source.getWidth()=" + source.getWidth() + ",targetWidth=" + targetWidth);

                    if (source.getWidth() == 0) {
                        return source;
                    }

                    //如果图片小于设置的宽度，则返回原图
                    if (source.getWidth() < targetWidth) {
                        return source;
                    } else {
                        //如果图片大小大于等于设置的宽度，则按照设置的宽度比例来缩放
                        double aspectRatio = (double) source.getHeight() / (double) source.getWidth();
                        int targetHeight = (int) (targetWidth * aspectRatio);
                        if (targetHeight != 0 && targetWidth != 0) {
                            Bitmap result = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, false);
                            if (result != source) {
                                // Same bitmap is returned if sizes are the same
                                source.recycle();
                            }
                            return result;
                        } else {
                            return source;
                        }
                    }

                }

                @Override
                public String key() {
                    return "transformation" + " desiredWidth";
                }
            };
            Picasso.with(getContext()).load(imgs[position]).transform(transformation).into(img);
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
        private LayoutInflater inflater;
        private List<Data> data;
        private Context context;

        public RecycleAdapter(Context context, List<Data> data) {
            this.inflater = LayoutInflater.from(context);
            this.context = context;
            this.data = data;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.lv_fragment_new_item, parent, false);
            NewsHolder holder = new NewsHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (data.get(position).getText_image0() != "") {
                Picasso.with(context).load(data.get(position).getText_image0()).into(((NewsHolder) holder).img_r);
            } else {
                ((NewsHolder) holder).img_r.setVisibility(View.GONE);
            }
            ((NewsHolder) holder).title_r.setText(data.get(position).getTitle());
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }

    class NewsHolder extends RecyclerView.ViewHolder {
        public TextView title_r;
        public ImageView img_r;

        public NewsHolder(View itemView) {
            super(itemView);
            title_r = (TextView) itemView.findViewById(R.id.news_item_title);
            img_r = (ImageView) itemView.findViewById(R.id.news_item_img);
        }
    }


}
