package com.fips.huashun.modle.bean;

import com.shuyu.common.model.RecyclerBaseModel;

import java.util.List;

/**
 * Created by kevin on 2017/1/19.
 * 留言墙的model
 */
public class LeaveWallModel extends RecyclerBaseModel {
    private int icon;//
    private String name;
    private String time;
    private String content;
    private int forwardcount;
    private int commentcount;
    private int likecount;
    /*圖片路徑*/
    private List<String> imageList;

    public List<String> getImageList() {
        return imageList;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }

    public LeaveWallModel() {
    }

    public LeaveWallModel(int icon, String name, String time, String content, int forwardcount, int commentcount, int likecount) {
        this.icon = icon;
        this.name = name;
        this.time = time;
        this.content = content;
        this.forwardcount = forwardcount;
        this.commentcount = commentcount;
        this.likecount = likecount;
    }

    public LeaveWallModel(int icon, String name, String time, String content, int forwardcount, int commentcount, int likecount, List<String> list) {
        this.imageList = list;
        this.icon = icon;
        this.name = name;
        this.time = time;
        this.content = content;
        this.forwardcount = forwardcount;
        this.commentcount = commentcount;
        this.likecount = likecount;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setForwardcount(int forwardcount) {
        this.forwardcount = forwardcount;
    }

    public void setCommentcount(int commentcount) {
        this.commentcount = commentcount;
    }

    public void setLikecount(int likecount) {
        this.likecount = likecount;
    }

    public int getIcon() {
        return icon;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public String getContent() {
        return content;
    }

    public int getForwardcount() {
        return forwardcount;
    }

    public int getCommentcount() {
        return commentcount;
    }

    public int getLikecount() {
        return likecount;
    }

    @Override
    public String toString() {
        return "LeaveWallModel{" +
                "icon=" + icon +
                ", name='" + name + '\'' +
                ", time='" + time + '\'' +
                ", content='" + content + '\'' +
                ", forwardcount=" + forwardcount +
                ", commentcount=" + commentcount +
                ", likecount=" + likecount +
                '}';
    }
}
