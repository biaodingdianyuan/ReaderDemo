package com.example.liuhaifeng.readerdemo.ui.news;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import com.example.liuhaifeng.readerdemo.R;


/**
 * Created by liuhaifeng on 2017/4/18.
 */

public class NewsFragment extends Fragment {
    private Fragment_new_adapter fragment_new_adapter;

    public static NewsFragment newInstance(int param1) {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        args.putInt("key", param1);

        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new, container, false);
        TabLayout tab = (TabLayout) view.findViewById(R.id.fragment_new_tabl);
        ViewPager vp = (ViewPager) view.findViewById(R.id.fragment_new_vp);
        fragment_new_adapter = new Fragment_new_adapter(getChildFragmentManager());
        vp.setAdapter(fragment_new_adapter);
        tab.setupWithViewPager(vp);
        tab.setTabMode(TabLayout.MODE_SCROLLABLE);
//        tab.setTabGravity(TabLayout.GRAVITY_FILL);
        return view;
    }
}
