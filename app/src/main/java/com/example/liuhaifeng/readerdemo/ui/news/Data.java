package com.example.liuhaifeng.readerdemo.ui.news;

import java.io.Serializable;

/**
 * Created by liuhaifeng on 2017/4/20.
 */

public class Data implements Serializable {
    private int news_id;
    private String title;
    private String top_image;    //头部图片
    private String text_image0;    //内容中的图片（可能为空）
    private String text_image1;    //内容中的图片（可能为空）
    private String source;    //新闻来源
    private String content;//	新闻体
    private String digest;    //	概要
    private int reply_count;//	回复数
    private int edit_time;

    public int getNews_id() {
        return news_id;
    }

    public void setNews_id(int news_id) {
        this.news_id = news_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTop_image() {
        return top_image;
    }

    public void setTop_image(String top_image) {
        this.top_image = top_image;
    }

    public String getText_image0() {
        return text_image0;
    }

    public void setText_image0(String text_image0) {
        this.text_image0 = text_image0;
    }

    public String getText_image1() {
        return text_image1;
    }

    public void setText_image1(String text_image1) {
        this.text_image1 = text_image1;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getcontent() {
        return content;
    }

    public void setcontent(String stringcontent) {
        content = stringcontent;
    }

    public String getdigest() {
        return digest;
    }

    public void setdigest(String stringdigest) {
        digest = stringdigest;
    }

    public int getReply_count() {
        return reply_count;
    }

    public void setReply_count(int reply_count) {
        this.reply_count = reply_count;
    }

    public int getEdit_time() {
        return edit_time;
    }

    public void setEdit_time(int edit_time) {
        this.edit_time = edit_time;
    }


}
