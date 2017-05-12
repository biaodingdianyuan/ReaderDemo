package com.example.liuhaifeng.readerdemo.ui.Music;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.liuhaifeng.readerdemo.Myapplication;
import com.example.liuhaifeng.readerdemo.R;
import com.example.liuhaifeng.readerdemo.tool.Urls;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by liuhaifeng on 2017/4/18.
 */

public class MusicFragment extends Fragment {
    @InjectView(R.id.musics)
    TextView musics;
    @InjectView(R.id.imageView3)
    ImageView imageView3;
    @InjectView(R.id.song_word)
    TextView songWord;
    @InjectView(R.id.progressBar)
    SeekBar progressBar;
    @InjectView(R.id.img_song_last)
    ImageView imgSongLast;
    @InjectView(R.id.img_song_start)
    ImageView imgSongStart;
    @InjectView(R.id.img_song_next)
    ImageView imgSongNext;
    @InjectView(R.id.img_music_list)
    ImageView musicList;
    @InjectView(R.id.lin_song_tool)
    LinearLayout linSongTool;
    @InjectView(R.id.list_song_item)
    ListView listSongItem;
    @InjectView(R.id.lin_song_list)
    LinearLayout linSongList;
    @InjectView(R.id.img_song)
    RelativeLayout imgSong;
    boolean flg = false;
    public static String Song_url = "";
    int i = 0;
    @InjectView(R.id.time_now)
    TextView timeNow;
    @InjectView(R.id.song_time)
    TextView songTime;
    boolean t = false;
    SQLiteSongBean bean;
    List<SQLiteSongBean> list_song_sql;
    MusicSAdapter adapter;
    int p = 0;
    @InjectView(R.id.music_fragment_name)
    TextView musicFragmentName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music, container, false);
        ButterKnife.inject(this, view);
        Play play = new Play(progressBar, timeNow, songTime);
        list_song_sql = new ArrayList<SQLiteSongBean>();
        musicFragmentName.setText("喵小姐");
        getsongfromsql();

        listSongItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                musicFragmentName.setText(list_song_sql.get(position).getMusicname());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        p = position;
                        Play.playUrl(list_song_sql.get(position).getFilelink());
                    }
                }).start();
                linSongTool.setVisibility(View.VISIBLE);
                linSongList.setVisibility(View.GONE);
                imgSongStart.setImageDrawable(getResources().getDrawable(R.mipmap.btn_pause_normal));
                flg = true;
            }
        });
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        if (t) {
            linSongTool.setVisibility(View.VISIBLE);
            linSongList.setVisibility(View.GONE);
            imgSongStart.setImageDrawable(getResources().getDrawable(R.mipmap.btn_pause_normal));
            flg = true;
            list_song_sql.clear();

            getsongfromsql();
            musicFragmentName.setText(list_song_sql.get(list_song_sql.size()-1).getMusicname());

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Play.playUrl(list_song_sql.get(list_song_sql.size()-1).getFilelink());
                    }
                }).start();
            }

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick({R.id.musics, R.id.img_song_last, R.id.img_song_start, R.id.img_song_next, R.id.img_music_list, R.id.img_song})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.musics:
                startActivity(new Intent(getActivity(), MusicActivity.class));
                t = true;
                break;
            case R.id.img_song_last:
                if (p == 0) {
                    p = list_song_sql.size() - 1;
                } else {
                    p = p - 1;
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        Play.playUrl(list_song_sql.get(p).getFilelink());
                    }
                }).start();
                break;
            case R.id.img_song_start:
                if (flg) {
                    imgSongStart.setImageDrawable(getResources().getDrawable(R.mipmap.btn_play_normal));
                    Play.pause();
                    i = 1;
                    flg = false;
                } else {
                    imgSongStart.setImageDrawable(getResources().getDrawable(R.mipmap.btn_pause_normal));
                    if (i == 0) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Play.playUrl(Urls.URL_MUSIC_SONG);
                            }
                        }).start();
                    } else {
                        Play.play();
                    }
                    flg = true;
                }
                break;
            case R.id.img_song_next:
                if (p == list_song_sql.size() - 1) {
                    p = 0;
                } else {
                    p = p + 1;
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        Play.playUrl(list_song_sql.get(p).getFilelink());
                    }
                }).start();

                break;
            case R.id.img_music_list:
                linSongTool.setVisibility(View.GONE);
                linSongList.setVisibility(View.VISIBLE);
                break;
            case R.id.img_song:
                linSongTool.setVisibility(View.VISIBLE);
                linSongList.setVisibility(View.GONE);
                break;
        }
    }

    public void getsongfromsql() {
        SQLiteDatabase db = Myapplication.musicDBOpenHelper.getReadableDatabase();
        Cursor cursor = db.query("music", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                bean = new SQLiteSongBean();
                bean.setNum(cursor.getString(cursor.getColumnIndex("num")));
                bean.setAuthor(cursor.getString(cursor.getColumnIndex("author")));
                bean.setFilelink(cursor.getString(cursor.getColumnIndex("file_link")));
                bean.setMusicname(cursor.getString(cursor.getColumnIndex("musicname")));
                list_song_sql.add(bean);
            } while (cursor.moveToNext());
        }
        adapter = new MusicSAdapter(getContext(), list_song_sql);
        listSongItem.setAdapter(adapter);
//        adapter.notifyDataSetInvalidated();

    }

    class MusicSAdapter extends BaseAdapter {
        Context context;
        List<SQLiteSongBean> list;

        public MusicSAdapter(Context context, List<SQLiteSongBean> list) {
            this.context = context;
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_lv_music, parent, false);
            }
            TextView music_name = (TextView) convertView.findViewById(R.id.music_name);
            TextView music_author = (TextView) convertView.findViewById(R.id.music_author);
            ImageView add = (ImageView) convertView.findViewById(R.id.add_to);
            add.setImageDrawable(getResources().getDrawable(R.mipmap.delete));
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    SQLiteDatabase db = Myapplication.musicDBOpenHelper.getWritableDatabase();
                    db.delete("music", "musicname=?", new String[]{list_song_sql.get(position).getMusicname()});
                    list_song_sql.remove(position);
                    adapter.notifyDataSetChanged();
                }
            });
            music_author.setText(list.get(position).getAuthor());
            music_name.setText(list.get(position).getMusicname());

            return convertView;
        }
    }


}
