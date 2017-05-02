package com.fips.huashun.modle.event;

import java.util.List;

/**
 * Created by kevin on 2017/2/10.
 * 留言墙事件EvenBus传递事件
 */
public class LeaveWallEvent {
    private String type;
    private String pid;
    private String content;
    private String publicname;
    private List<String>filepaths;
    private String json;
    private String isforward;//是否为转发
    private String uid;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getIsforward() {
        return isforward;
    }

    public void setIsforward(String isforward) {
        this.isforward = isforward;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getFilepaths() {
        return filepaths;
    }

    public void setFilepaths(List<String> filepaths) {
        this.filepaths = filepaths;
    }

    public String getPublicname() {
        return publicname;
    }

    public void setPublicname(String publicname) {
        this.publicname = publicname;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public LeaveWallEvent(String type, String pid) {
        this.type = type;
        this.pid = pid;
    }

    public String getPid() {
        return pid;
    }

    public LeaveWallEvent() {
    }

    public LeaveWallEvent(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
