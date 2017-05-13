package com.example.liuhaifeng.readerdemo.ui.Music;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liuhaifeng.readerdemo.Myapplication;
import com.example.liuhaifeng.readerdemo.R;
import com.example.liuhaifeng.readerdemo.tool.Urls;
import com.example.liuhaifeng.readerdemo.ui.Picture.Lv_fragment_picture;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuhaifeng on 2017/5/11.
 */

public class lv_music_fragment extends Fragment {
    SongBean s;
    private String  music_type;
    private ListView lv;
    private MusicLvAdapter adapter;
    private List<MusicsBean.SongListBean> list;
    SQLiteSongBean bean;
    List<SQLiteSongBean> list_song_sql;
    private Handler myhandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    MusicsBean n= (MusicsBean) msg.obj;
                    list.clear();
                    list.addAll(n.getSong_list());
                    adapter.notifyDataSetChanged();
                    break;
                case 2:
                   s= (SongBean) msg.obj;
                    MusicFragment.Song_url=s.getBitrate().getFile_link();
                    boolean o=true;
                    SQLiteDatabase db=Myapplication.musicDBOpenHelper.getWritableDatabase();
                    if(list_song_sql.size()==0){
                       o=true;
                    }else{
                  for(int i=0;i<list_song_sql.size();i++){

                           if(list_song_sql.get(i).getMusicname().equals(s.getSonginfo().getAlbum_title())){
                               Toast.makeText(getContext(),"此歌曲以在列表中",Toast.LENGTH_SHORT).show();
                               o=false;
                               break;
                            }
                      }
                  }
                  if(o){

                          ContentValues values = new ContentValues();
                          values.put("num", "1");
                          values.put("musicname", s.getSonginfo().getTitle());
                          values.put("file_link", s.getBitrate().getFile_link());
                          values.put("author", s.getSonginfo().getAuthor());
                          db.insert("music", null, values);

                  }

                    getActivity().onBackPressed();
                    break;
            }

        }
    };

    public static lv_music_fragment newInstance(String param1) {
        lv_music_fragment fragment = new lv_music_fragment();
        Bundle args = new Bundle();
        args.putString("key", param1);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            music_type = getArguments().getString("key");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.fragment_lv_music,container,false);
         lv= (ListView) view.findViewById(R.id.lv_music_lv);
        list=new ArrayList<MusicsBean.SongListBean>();
        adapter=new MusicLvAdapter(getContext(),list);
        lv.setAdapter(adapter);
        getdata(music_type);
        list_song_sql=new ArrayList<SQLiteSongBean>();
        query();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                getsong(list.get(position).getSong_id());

            }
        });
        return view;
    }

    class MusicLvAdapter extends BaseAdapter{
        Context context;
        List<MusicsBean.SongListBean> song_list;

        public MusicLvAdapter(Context context, List<MusicsBean.SongListBean> song_list) {
            this.context=context;
            this.song_list=song_list;
        }

        @Override
        public int getCount() {
            return song_list.size();
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
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView==null){
                convertView=LayoutInflater.from(context).inflate(R.layout.item_lv_music,parent,false);
            }
            TextView music_name= (TextView) convertView.findViewById(R.id.music_name);
            TextView music_author= (TextView) convertView.findViewById(R.id.music_author);
            ImageView add= (ImageView) convertView.findViewById(R.id.add_to);
            music_author.setText(song_list.get(position).getAuthor());
            music_name.setText(song_list.get(position).getTitle());

//            add.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    SQLiteDatabase db=Myapplication.musicDBOpenHelper.getWritableDatabase();
//                    ContentValues values;
//
//                }
//            });
            return convertView;
        }
    }

    public  void getdata(final String type){

        new Thread(new Runnable() {
            @Override
            public void run() {
                Request request=new Request.Builder().get().url(Urls.RUL_MUSIC_BAIDU_LIST+type+"&size=20&offset=0").build();
                try {
                    Response response= Myapplication.myokhttpclient.newCall(request).execute();
                    if(response.isSuccessful()){
                        Gson gson=new Gson();
                        MusicsBean   l =gson.fromJson(response.body().string(),new TypeToken<MusicsBean>(){}.getType());
                        myhandler.obtainMessage(1,l).sendToTarget();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public  void  getsong(final String  songid) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Request request=new Request.Builder().get().url(Urls.URL_MUSIC_BAIDU_PLAY+songid).build();
                Response response= null;
                try {
                    response = Myapplication.myokhttpclient.newCall(request).execute();
                    if(response.isSuccessful()){
                        Gson g=new Gson();
                        String json=response.body().string();
                        SongBean song=g.fromJson(json,new TypeToken<SongBean>(){}.getType());
                        myhandler.obtainMessage(2,song).sendToTarget();

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();


    }
    public  void query(){
        SQLiteDatabase db= Myapplication.musicDBOpenHelper.getReadableDatabase();

        Cursor cursor= db.query("music",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do {
                bean=new SQLiteSongBean();
                bean.setNum(cursor.getString(cursor.getColumnIndex("num")));
                bean.setAuthor(cursor.getString(cursor.getColumnIndex("author")));
                bean.setFilelink(cursor.getString(cursor.getColumnIndex("file_link")));
                bean.setMusicname(cursor.getString(cursor.getColumnIndex("musicname")));
                list_song_sql.add(bean);
            }while (cursor.moveToNext());
        }
    }
}
