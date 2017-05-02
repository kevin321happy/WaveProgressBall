package com.fips.huashun.modle.bean;

import com.fips.huashun.ui.utils.StringUtil;

/**
 * Created by kevin on 2017/2/15.
 * 回复的model
 */

public class ReplyItemModel {
    private String pid;
    private String space_wordsid;
    private String topid;
    private String getid;
    private String getname;
    private String putname;
    private String words;
    private String addtime;
    private String putid;
    private String headurl;
    private String lable;

    public String getLable() {
        return lable;
    }

    public void setLable(String lable) {
        this.lable = lable;
    }

    public String getHeadurl() {
        return headurl;
    }

    public void setHeadurl(String headurl) {
        this.headurl = headurl;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public ReplyItemModel(String space_wordsid, String topid, String getid, String getname, String putname, String putid) {
        this.space_wordsid = space_wordsid;
        this.topid = topid;
        this.getid = getid;
        this.getname = getname;
        this.putname = putname;
        this.putid = putid;
    }

    public void setSpace_wordsid(String space_wordsid) {
        this.space_wordsid = space_wordsid;
    }

    public void setTopid(String topid) {
        this.topid = topid;
    }

    public void setGetid(String getid) {
        this.getid = getid;
    }

    public void setGetname(String getname) {
        this.getname = getname;
    }

    public void setPutid(String putid) {
        this.putid = putid;
    }

    public void setPutname(String putname) {
        this.putname = putname;
    }

    public void setWords(String words) {
        this.words = words;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getPid() {
        return pid;
    }

    public String getSpace_wordsid() {
        return space_wordsid;
    }

    public String getTopid() {
        return topid;
    }

    public String getGetid() {
        return getid;
    }

    public String getGetname() {
        return getname;
    }

    public String getPutid() {
        return putid;
    }

    public String getPutname() {
        return putname;
    }

    public String getWords() {
        return words;
    }

    public String getAddtime() {
        return addtime;
    }

    public ReplyItemModel(String pid, String space_wordsid, String topid, String getid, String getname, String putname, String words, String addtime, String putid) {
        this.pid = pid;
        this.space_wordsid = space_wordsid;
        this.topid = topid;
        this.getid = getid;
        this.getname = getname;
        this.putname = putname;
        this.words = words;
        this.addtime = addtime;
        this.putid = putid;
    }

    public ReplyItemModel() {
        this.pid = pid;
        this.space_wordsid = space_wordsid;
        this.topid = topid;
        this.getid = getid;
        this.getname = getname;
        this.putid = putid;
        this.putname = putname;
        this.words = words;
        this.addtime = addtime;
    }

    @Override
    public String toString() {
        return "ReplyItemModel{" +
                "pid='" + pid + '\'' +
                ", space_wordsid='" + space_wordsid + '\'' +
                ", topid='" + topid + '\'' +
                ", getid='" + getid + '\'' +
                ", getname='" + getname + '\'' +
                ", putid='" + putid + '\'' +
                ", putname='" + putname + '\'' +
                ", words='" + words + '\'' +
                ", addtime='" + addtime + '\'' +
                '}';
    }
}
