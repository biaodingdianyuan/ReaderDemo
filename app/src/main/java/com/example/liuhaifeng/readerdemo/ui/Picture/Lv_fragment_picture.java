package com.example.liuhaifeng.readerdemo.ui.Picture;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.liuhaifeng.readerdemo.R;
import com.example.liuhaifeng.readerdemo.tool.Urls;
import com.example.liuhaifeng.readerdemo.ui.WebViewActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.TypeVariable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.example.liuhaifeng.readerdemo.Myapplication.myokhttpclient;

/**
 * Created by liuhaifeng on 2017/5/4.
 */

public class Lv_fragment_picture extends Fragment {
    RecyclerView lvPictureRecycle;
    SwipeRefreshLayout lvPivtureSwipe;
    AVLoadingIndicatorView lvPictureLoading;
    private String mParam1;
    private List<PictureBean.TngouBean> L = null;
    private PictureAdapter adapter;
    int width = 0;
    int index = 1;
    StaggeredGridLayoutManager layoutManager;

    private Handler myhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case 1:
                    lvPictureLoading.setVisibility(View.GONE);
                    lvPivtureSwipe.setVisibility(View.VISIBLE);
                    lvPivtureSwipe.setRefreshing(false);
                     L.addAll(((PictureBean) msg.obj).getTngou());
                    break;
            }
            adapter.notifyDataSetChanged();
        }
    };

    public static Lv_fragment_picture newInstance(int param1) {
        Lv_fragment_picture fragment = new Lv_fragment_picture();
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
        View view = inflater.inflate(R.layout.lv_fragment_picture_item, container, false);
        init(view);
        lvPictureLoading.setVisibility(View.VISIBLE);
        lvPivtureSwipe.setVisibility(View.GONE);
        L = new ArrayList<PictureBean.TngouBean>();
        adapter = new PictureAdapter(getContext(), L);
        layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        lvPictureRecycle.setLayoutManager(layoutManager);
        lvPictureRecycle.setAdapter(adapter);
        SpacesItemDecoration decoration = new SpacesItemDecoration(8);
        lvPictureRecycle.addItemDecoration(decoration);
        width = getActivity().getWindowManager().getDefaultDisplay().getWidth();
        lvPivtureSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                L.clear();
                index = 1;
                getdata(index, mParam1);
                lvPivtureSwipe.setRefreshing(true);
            }
        });

//        getdata(index,mParam1);
        lvPictureRecycle.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int listitemposation;
            int[] lastPositions;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && listitemposation + 1 == adapter.getItemCount()) {
                    index = index + 1;
                    getdata(index, mParam1);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (lastPositions == null) {
                    lastPositions = new int[layoutManager.getSpanCount()];
                }
                layoutManager.findLastVisibleItemPositions(lastPositions);
                listitemposation = findMax(lastPositions);
            }
        });
        return view;

    }

    public void init(View view) {
        lvPictureLoading = (AVLoadingIndicatorView) view.findViewById(R.id.lv_picture_loading);
        lvPictureRecycle = (RecyclerView) view.findViewById(R.id.lv_picture_recycle);
        lvPivtureSwipe = (SwipeRefreshLayout) view.findViewById(R.id.lv_pivture_swipe);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    class PictureAdapter extends RecyclerView.Adapter {
        Context context;
        private LayoutInflater inflater;
        List<PictureBean.TngouBean> list;


        public PictureAdapter(Context context, List<PictureBean.TngouBean> list) {
            this.list = list;
            this.inflater = LayoutInflater.from(context);
            this.context = context;


        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.item_pivture, parent, false);
            PictureHolder holder = new PictureHolder(view);

            return holder;
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
            Transformation transformation = new Transformation() {
                @Override
                public Bitmap transform(Bitmap source) {
                    int targetWidth = width / 3;
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

            Picasso.with(context)
                    .load(Urls.URL_PICTURE_SHOW + list.get(position).getImg())
                    .transform(transformation)
                    .placeholder(R.drawable.picter)
                    .into(((PictureHolder) holder).img_item);
//
            ((PictureHolder) holder).img_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(), WebViewActivity.class).putExtra("url", Urls.URL_PICTURE_DETAILED + list.get(position).getId()));
                }
            });
        }


        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    class PictureHolder extends RecyclerView.ViewHolder {
        private ImageView img_item;

        public PictureHolder(View itemView) {
            super(itemView);

            img_item = (ImageView) itemView.findViewById(R.id.pivture_item_img);

        }
    }

    public void getdata(final int index, final String id) {
        Log.e("*******", index + "");
        new Thread(new Runnable() {
            @Override
            public void run() {
                PictureBean bean = null;
                String url = Urls.URL_PICTURE_LIST + "page=" + index + "&rows=40&id=" + id;
                Request request = new Request.Builder().url(url).get().build();
                try {
                    Response response = myokhttpclient.newCall(request).execute();
                    if (response.isSuccessful()) {
                        Gson gson = new Gson();
                        bean = gson.fromJson(response.body().string(), new TypeToken<PictureBean>() {
                        }.getType());

                        myhandler.obtainMessage(1, bean).sendToTarget();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    @Override
    public void onResume() {
        super.onResume();
        index = 1;
        L.clear();
        getdata(index, mParam1);
        lvPivtureSwipe.setRefreshing(true);
    }

    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }

        return max;
    }
}
