package com.fips.huashun.modle.event;

/**
 * Created by kevin on 2017/2/16.
 * 动态的刷新
 */

public class LeaveRefreshEvent {
    private int type;
    private String activityid;

    public String getActivityid() {
        return activityid;
    }

    public void setActivityid(String activityid) {
        this.activityid = activityid;
    }

    public LeaveRefreshEvent(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
