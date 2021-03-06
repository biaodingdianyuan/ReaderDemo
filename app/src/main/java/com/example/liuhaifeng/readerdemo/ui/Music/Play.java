package com.example.liuhaifeng.readerdemo.ui.Music;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by liuhaifeng on 2017/5/10.
 */

public class Play implements MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnPreparedListener , MediaPlayer.OnCompletionListener{
    public static MediaPlayer mediaPlayer; // 媒体播放器
    private SeekBar seekBar; // 拖动条
    private Timer mTimer = new Timer(); // 计时器
    private TextView time,song_time, musicname;
    private static String  urls;
static int i=0;
    List<SQLiteSongBean> list;
    // 初始化播放器
    public Play(SeekBar seekBar, TextView time, TextView song_time, List<SQLiteSongBean> list,TextView  musicname) {
        super();
        this.seekBar = seekBar;
        this.time=time;
        this.song_time=song_time;
        this.musicname=musicname;
        this.list=list;
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);// 设置媒体流类型
            mediaPlayer.setOnBufferingUpdateListener(this);
            mediaPlayer.setOnPreparedListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 每一秒触发一次
        mTimer.schedule(timerTask, 0, 1000);
    }

    // 计时器
    TimerTask timerTask = new TimerTask() {

        @Override
        public void run() {
            if (mediaPlayer == null)
                return;
            if (mediaPlayer.isPlaying() && seekBar.isPressed() == false) {
                handler.sendEmptyMessage(0); // 发送消息
            }
        }
    };

    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            int position = mediaPlayer.getCurrentPosition();
            int duration = mediaPlayer.getDuration();
            long pos = 0;
            if (duration > 0) {
                // 计算进度（获取进度条最大刻度*当前音乐播放位置 / 当前音乐时长）
                pos = seekBar.getMax() * position / duration;
                seekBar.setProgress((int) pos);
                Log.d("*********",pos+"");
                if(pos==99){
                    MusicFragment.donext();
                    if(list.size()>0){
                        for(int i=0;i<list.size();i++){
                            if(list.get(i).getFilelink().equals(urls)){
                                musicname.setText(list.get(i).getMusicname());
                            }
                    }
                    }
            }
            }

            SimpleDateFormat formatter = new SimpleDateFormat("mm:ss");
            song_time.setText(formatter.format(mediaPlayer.getDuration())+"");
            time.setText(formatter.format(mediaPlayer.getCurrentPosition())+"");
        };
    };

    public static void play() {
        mediaPlayer.seekTo(i);
        mediaPlayer.start();
    }

    /**
     *
     * @param url
     *            url地址
     */
    public static void playUrl(String url) {
        urls=url;
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(url); // 设置数据源
            mediaPlayer.prepare(); // prepare自动播放
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 暂停
    public static void pause() {
        mediaPlayer.pause();
      i=  mediaPlayer.getCurrentPosition();
    }

    // 停止
    public static void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    // 播放准备
    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }

    // 播放完成
    @Override
    public void onCompletion(MediaPlayer mp) {
        mp.start();
    }

    /**
     * 缓冲更新
     */
    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        seekBar.setSecondaryProgress(percent);
        int currentProgress = seekBar.getMax()
                * mediaPlayer.getCurrentPosition() / mediaPlayer.getDuration();
        Log.e(currentProgress + "% play", percent + " buffer");
    }
}
