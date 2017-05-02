package com.fips.huashun.modle.event;

/**
 * Created by kevin on 2017/2/16.
 * 反饋室菜單
 */

public class FeedTopMenuEvent {

    private String type;
    private  String seei;
    private boolean lecturer;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public FeedTopMenuEvent() {

    }

    public void setSeei(String seei) {
        this.seei = seei;
    }

    public boolean isLecturer() {
        return lecturer;
    }

    public void setLecturer(boolean lecturer) {
        this.lecturer = lecturer;
    }

    public FeedTopMenuEvent(String seei) {
        this.seei = seei;
    }

    public String getSeei() {
        return seei;
    }
}
