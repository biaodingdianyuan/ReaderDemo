package com.example.liuhaifeng.readerdemo.ui.Music;

/**
 * Created by liuhaifeng on 2017/5/11.
 */

public class SQLiteSongBean {
    private  String num;
    private  String musicname;
    private String author;
    private String filelink;

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getMusicname() {
        return musicname;
    }

    public void setMusicname(String musicname) {
        this.musicname = musicname;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getFilelink() {
        return filelink;
    }

    public void setFilelink(String filelink) {
        this.filelink = filelink;
    }
}
