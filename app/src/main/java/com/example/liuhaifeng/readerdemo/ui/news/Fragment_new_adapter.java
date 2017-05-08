package com.example.liuhaifeng.readerdemo.ui.news;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.liuhaifeng.readerdemo.tool.Urls;

/**
 * Created by liuhaifeng on 2017/4/19.
 */

public class Fragment_new_adapter extends FragmentPagerAdapter {

    private Context context;
    private String[] title_name={"头条","娱乐","军事","汽车","财经","笑话","体育","科技"};
    private String urls= Urls.URL_NEW_LIST;
    private int count=title_name.length;
    public Fragment_new_adapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return Lv_fragment_new.newInstance(position+1);
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title_name[position];
    }
}
