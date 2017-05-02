package com.fips.huashun.modle.bean;

/**
 * Created by zallds on 2016/4/2.
 */
public class GridViewBean {
    private int imageRes;
    private String title;

    public GridViewBean() {
    }

    public GridViewBean(int imageRes, String title) {
        this.imageRes = imageRes;
        this.title = title;

    }

    public int getImageRes() {
        return imageRes;
    }

    public void setImageRes(int imageRes) {
        this.imageRes = imageRes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
