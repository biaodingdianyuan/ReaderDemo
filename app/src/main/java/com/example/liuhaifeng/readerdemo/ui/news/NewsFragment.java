package com.example.liuhaifeng.readerdemo.ui.news;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.liuhaifeng.readerdemo.R;


/**
 * Created by liuhaifeng on 2017/4/18.
 */

public class NewsFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.fragment_new,container,false);

        return view;
    }
}
