package com.example.liuhaifeng.readerdemo.ui.news;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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

import com.example.liuhaifeng.readerdemo.R;
import com.example.liuhaifeng.readerdemo.tool.OkHttpUtil;
import com.example.liuhaifeng.readerdemo.tool.Urls;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by liuhaifeng on 2017/4/19.
 */

public class Lv_fragment_new extends Fragment {


    RecyclerView lvNewRv;
    SwipeRefreshLayout lvNewSrl;

    RollPagerView rollPagerView;
    String[] img = null;
    String[] title = null;
    TestNormalAdapter adapter_img;
    RecycleAdapter adapter_recy;
    NewsBean news;
    List<Data> d;
    int page=1;
    LinearLayoutManager linearLayoutManager;
    AVLoadingIndicatorView lvNewLoading;
    private String mParam1;
    public Handler myhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    rollPagerView = (RollPagerView) LayoutInflater.from(getActivity()).inflate(R.layout.picturecarouselview, lvNewRv, false);
                    lvNewLoading.setVisibility(View.GONE);
                    lvNewSrl.setVisibility(View.VISIBLE);
                    img = new String[3];
                    title = new String[3];
                    news = (NewsBean) msg.obj;
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
                            img[i] = "";
                        }
                    }
                    if (!img[0].equals("") && !img[1].equals("") && !img[2].equals("")) {
                        rollPagerView.setVisibility(View.VISIBLE);
                        rollPagerView.setAdapter(new TestNormalAdapter(img, title));
                        adapter_recy.setHeaderView(rollPagerView);
                    } else {
                        rollPagerView.setVisibility(View.GONE);
                    }
                    d.addAll(news.getData());
                    adapter_recy.notifyDataSetChanged();
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
        init(view);
        new Thread(new Runnable() {
            @Override
            public void run() {
                d.clear();
                new OkHttpUtil().getdata(mParam1, myhandler,1);
            }
        }).start();
        d = new ArrayList<Data>();
        adapter_recy = new RecycleAdapter(getActivity(), d,mParam1);
        View p = inflater.inflate(R.layout.picturecarouselview, null);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager=new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL, false);
        lvNewRv.setLayoutManager(linearLayoutManager);
        lvNewRv.setItemAnimator(new DefaultItemAnimator());
        lvNewRv.setAdapter(adapter_recy);
        lvNewSrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        d.clear();
                        new OkHttpUtil().getdata(mParam1, myhandler,1);
                    }
                }).start();
            }
        });

        lvNewRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int listitemposition;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(listitemposition+1==adapter_recy.getItemCount()&& newState==RecyclerView.SCROLL_STATE_IDLE){
                    page=page+1;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            new OkHttpUtil().getdata(mParam1, myhandler,page);
                        }
                    }).start();
                }
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                listitemposition=linearLayoutManager.findLastVisibleItemPosition();
            }
        });
        return view;
    }

    public void init(View views){
        lvNewRv= (RecyclerView) views.findViewById(R.id.lv_new_Rv);
        lvNewSrl= (SwipeRefreshLayout) views.findViewById(R.id.lv_new_srl);
        lvNewLoading= (AVLoadingIndicatorView) views.findViewById(R.id.lv_new_loading);

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

        public TestNormalAdapter(String[] imgs, String[] titles) {
            this.imgs = imgs;
            this.titles = titles;
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


}
