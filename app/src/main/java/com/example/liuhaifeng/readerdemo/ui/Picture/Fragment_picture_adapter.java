package com.example.liuhaifeng.readerdemo.ui.Picture;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by liuhaifeng on 2017/5/4.
 */

public class Fragment_picture_adapter extends FragmentPagerAdapter {

    private String[] title_picture={"性感美女","韩日美女","丝袜美腿","美女照片","美女写真","清纯美女","性感车模"};
    private    int content=title_picture.length;

    public Fragment_picture_adapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return Lv_fragment_picture.newInstance(position+1);
    }

    @Override
    public int getCount() {
        return content;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title_picture[position];
    }
}
