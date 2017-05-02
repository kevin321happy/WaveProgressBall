package com.fips.huashun.modle.bean;

import com.shuyu.common.model.RecyclerBaseModel;

import java.util.List;

/**
 * Created by kevin on 2017/2/1.
 */
public class FeedBackModel extends RecyclerBaseModel {
    private String picUrl;
    private String name;
    private String department;
    private String post;
    private String grade;
    private String time;
    private String content;
    private String collectCount;
    private String commentCount;
    private String likeCount;
    private List<String>imageList;

    public FeedBackModel() {
    }

    public FeedBackModel(String picUrl, String name, String department, String post, String grade, String time, String content, String collectCount, String commentCount, String likeCount, List<String> imageList) {
        this.picUrl = picUrl;
        this.name = name;
        this.department = department;
        this.post = post;
        this.grade = grade;
        this.time = time;
        this.content = content;
        this.collectCount = collectCount;
        this.commentCount = commentCount;
        this.likeCount = likeCount;
        this.imageList = imageList;
    }

    public FeedBackModel(String picUrl, String name, String department, String post, String grade, String time, String collectCount, String commentCount, String likeCount, List<String> imageList) {
        this.picUrl = picUrl;
        this.name = name;
        this.department = department;
        this.post = post;
        this.grade = grade;
        this.time = time;
        this.collectCount = collectCount;
        this.commentCount = commentCount;
        this.likeCount = likeCount;
        this.imageList = imageList;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setCollectCount(String collectCount) {
        this.collectCount = collectCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }

    public void setLikeCount(String likeCount) {
        likeCount = likeCount;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public String getPost() {
        return post;
    }

    public String getGrade() {
        return grade;
    }

    public String getTime() {
        return time;
    }

    public String getCollectCount() {
        return collectCount;
    }

    public String getCommentCount() {
        return commentCount;
    }

    public String getLikeCount() {
        return likeCount;
    }

    public List<String> getImageList() {
        return imageList;
    }

    @Override
    public String toString() {
        return "FeedBackModel{" +
                "picUrl='" + picUrl + '\'' +
                ", name='" + name + '\'' +
                ", department='" + department + '\'' +
                ", post='" + post + '\'' +
                ", grade='" + grade + '\'' +
                ", time='" + time + '\'' +
                ", collectCount='" + collectCount + '\'' +
                ", commentCount='" + commentCount + '\'' +
                ", likeCount='" + likeCount + '\'' +
                ", imageList=" + imageList +
                '}';
    }
}
