package com.example.liuhaifeng.readerdemo.ui.Music;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.liuhaifeng.readerdemo.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by liuhaifeng on 2017/5/11.
 */

public class MusicActivity extends AppCompatActivity {

    @InjectView(R.id.img_music_back)
    ImageView imgMusicBack;
    @InjectView(R.id.textView2)
    TextView textView2;
    @InjectView(R.id.toolbar_music)
    Toolbar toolbar;
    @InjectView(R.id.tab_music)
    TabLayout tabMusic;
    @InjectView(R.id.vp_music)
    ViewPager vpMusic;
        MusicAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        ButterKnife.inject(this);
        setSupportActionBar(toolbar);
        adapter=new MusicAdapter(getSupportFragmentManager());
        vpMusic.setAdapter(adapter);
        tabMusic.setupWithViewPager(vpMusic);
        tabMusic.setTabMode(TabLayout.MODE_SCROLLABLE);

    }

    @OnClick(R.id.img_music_back)
    public void onViewClicked() {
        finish();
    }

    class MusicAdapter extends FragmentPagerAdapter{
        public  final String[] titile_music={"新歌榜","热歌榜","摇滚榜","爵士","流行","欧美金曲榜","经典老歌榜","情歌对唱榜","影视金曲榜","网络歌曲榜"};
        public final String[]   types={"1","2","11","12","16","21","22","23","24","25"};
        public MusicAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return lv_music_fragment.newInstance(types[position]);
        }

        @Override
        public int getCount() {
            return titile_music.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titile_music[position];
        }
    }

}
