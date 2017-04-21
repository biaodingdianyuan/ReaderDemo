package com.example.liuhaifeng.readerdemo.ui.news;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by liuhaifeng on 2017/4/20.
 */

public class NewsBean implements Serializable {
    private int status;
    private String error;

    @Override
    public String toString() {
        return "NewsBean{" +
                "status='" + status + '\'' +
                ", error='" + error + '\'' +
                ", count='" + count + '\'' +
                ", data=" + data +
                '}';
    }

    private int count;
    private List<Data> data;
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

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }




}
