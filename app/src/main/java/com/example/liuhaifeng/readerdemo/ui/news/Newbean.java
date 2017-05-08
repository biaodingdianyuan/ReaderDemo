package com.example.liuhaifeng.readerdemo.ui.news;

/**
 * Created by liuhaifeng on 2017/5/6.
 */

public class Newbean {


    private int status;
    private String error;
    private int count;
    private DataBean data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * news_id : 5628
         * title : 阿根廷男子性侵女儿22年生8子
         * top_image : http://img5.cache.netease.com/3g/2016/1/10/2016011015570932721.jpg
         * text_image0 : http:///img1.cache.netease.com/cnews/2016/1/10/201601101549260cae4_550.jpg
         * text_image1 : http:///img3.cache.netease.com/cnews/2016/1/10/201601101549536da93_550.jpg
         * source : 新华网
         * content :
         <img src="http:///img1.cache.netease.com/cnews/2016/1/10/201601101549260cae4_550.jpg"/><br/>阿根廷《自由报》社交网站公布“兽父”多明戈·布利西奥被捕画面。作者：刘曦

         * digest : 女儿9岁起被性侵至今31岁，期间还受到叔叔侵犯。
         * reply_count : 396
         * edit_time : 1452412209
         */

        private String news_id;
        private String title;
        private String top_image;
        private String text_image0;
        private String text_image1;
        private String source;
        private String content;
        private String digest;
        private String reply_count;
        private String edit_time;

        public String getNews_id() {
            return news_id;
        }

        public void setNews_id(String news_id) {
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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getDigest() {
            return digest;
        }

        public void setDigest(String digest) {
            this.digest = digest;
        }

        public String getReply_count() {
            return reply_count;
        }

        public void setReply_count(String reply_count) {
            this.reply_count = reply_count;
        }

        public String getEdit_time() {
            return edit_time;
        }

        public void setEdit_time(String edit_time) {
            this.edit_time = edit_time;
        }
    }
}
