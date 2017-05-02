package com.fips.huashun.modle.event;

/**
 * Created by kevin on 2017/2/14.
 */

public class FeedBackEvent {
    private String type;//类型：收藏 点赞 评论
    private String mark;//评分
    private String pid;//留言/反馈的条目的ID
    private String ismanager;//是否为管理员
    private String itemjson;

    public String getItemjson() {
        return itemjson;
    }

    public void setItemjson(String itemjson) {
        this.itemjson = itemjson;
    }

    public FeedBackEvent() {
    }

    public void setIsmanager(String ismanager) {
        this.ismanager = ismanager;
    }

    public String getIsmanager() {
        return ismanager;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public FeedBackEvent(String type, String mark) {
        this.type = type;
        this.mark = mark;
    }

    public FeedBackEvent(String mark) {
        this.mark = mark;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }
}
