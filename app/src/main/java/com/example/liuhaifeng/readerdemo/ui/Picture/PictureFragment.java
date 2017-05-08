package com.example.liuhaifeng.readerdemo.ui.Picture;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.liuhaifeng.readerdemo.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by liuhaifeng on 2017/4/18.
 */

public class PictureFragment extends Fragment {
    @InjectView(R.id.fragment_picture_tab)
    TabLayout fragmentPictureTab;
    @InjectView(R.id.fragment_picture_vp)
    ViewPager fragmentPictureVp;
    Fragment_picture_adapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_picture, container, false);
        ButterKnife.inject(this, view);
        adapter=new Fragment_picture_adapter(getChildFragmentManager());
        fragmentPictureVp.setAdapter(adapter);
        fragmentPictureTab.setupWithViewPager(fragmentPictureVp);
        fragmentPictureTab.setTabMode(TabLayout.MODE_SCROLLABLE);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
