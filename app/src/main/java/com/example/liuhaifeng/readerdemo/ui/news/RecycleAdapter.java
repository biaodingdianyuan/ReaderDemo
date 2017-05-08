package com.example.liuhaifeng.readerdemo.ui.news;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.liuhaifeng.readerdemo.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by liuhaifeng on 2017/5/5.
 */

public class RecycleAdapter extends RecyclerView.Adapter {
    private LayoutInflater inflater;
    private List<Data> data;
    private Context context;
    public static final int LOAD_MORE = 4;
    public static final int LOADING = 5;
    public static final int NO_MORE_DATA = 6;
    public final int HEADER = 0;
    public final int NORMAL = 1;
    public final int MULTIIMAGE = 2;
    public final int FOOTER = 3;
    private int load_status = LOAD_MORE;
    String tableNum;
    View headerView;

    public void setHeaderView(View headerView) {
        this.headerView = headerView;
        notifyItemInserted(0);
    }

    public RecycleAdapter(Context context, List<Data> data,String tableNum) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.data = data;
        this.tableNum=tableNum;
    }

    @Override
    public int getItemViewType(int position) {
        if (headerView == null) {
//            if (position + 1 == getItemCount()) return FOOTER;
            if (data.size() == 20)
                return NORMAL;
            return MULTIIMAGE;
        }
        if (position == 0) return HEADER;
//        if (position + 1 == getItemCount()) return FOOTER;
        if (data.size() == 17)
            return NORMAL;
        return MULTIIMAGE;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (headerView != null && viewType == HEADER) {
            return new NewsHolder(headerView);
        }
        if (viewType == NORMAL) {
            View view = inflater.inflate(R.layout.lv_fragment_new_item, parent, false);
            return new NewsHolder(view);
        }
        View view = inflater.inflate(R.layout.lv_fragment_new_item, parent, false);
        return new NewsHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position) == HEADER) {
            return;
        }
        if (getItemViewType(position) == NORMAL && data.size() == 17) {

            if (data.get(position - 1).getText_image0() != "") {
                Picasso.with(context).load(data.get(position - 1).getText_image0()).into(((NewsHolder) holder).img_r);
            } else {
                ((NewsHolder) holder).img_r.setVisibility(View.GONE);
            }
            ((NewsHolder) holder).title_r.setText(data.get(position - 1).getTitle());
            ((NewsHolder) holder).layout_r.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context,DetailedActivity.class).putExtra("news",data.get(position-1)).putExtra("tableNum",tableNum));
                }
            });
        } else {
            if (data.get(position).getText_image0() != "") {
                Picasso.with(context).load(data.get(position).getText_image0()).into(((NewsHolder) holder).img_r);
            } else {
                ((NewsHolder) holder).img_r.setVisibility(View.GONE);
            }
            ((NewsHolder) holder).title_r.setText(data.get(position).getTitle());
            ((NewsHolder) holder).layout_r.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context,DetailedActivity.class).putExtra("news",data.get(position)).putExtra("tableNum",tableNum));
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class NewsHolder extends RecyclerView.ViewHolder {
        public TextView title_r;
        public ImageView img_r;
        public LinearLayout layout_r;

        public NewsHolder(View itemView) {
            super(itemView);
            if (itemView == headerView) return;
            title_r = (TextView) itemView.findViewById(R.id.news_item_title);
            img_r = (ImageView) itemView.findViewById(R.id.news_item_img);
            layout_r= (LinearLayout) itemView.findViewById(R.id.news_item_layout);
        }
    }
}