package com.fips.huashun.modle.event;

/**
 * Created by kevin on 2017/2/8.
 * 参数信息,evenbus传递参数
 */
public class ArgumentInfo {
    String uid;
    String activityid;

    public ArgumentInfo() {
    }

    public ArgumentInfo(String activityid) {
        this.activityid = activityid;
    }

    public ArgumentInfo(String uid, String activityid) {
        this.uid = uid;
        this.activityid = activityid;
    }

    public String getUid() {
        return uid;
    }

    public String getActivityid() {
        return activityid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setActivityid(String activityid) {
        this.activityid = activityid;
    }

    @Override
    public String toString() {
        return "ArgumentInfo{" +
                "uid='" + uid + '\'' +
                ", activityid='" + activityid + '\'' +
                '}';
    }
}
