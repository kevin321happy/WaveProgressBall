package com.fips.huashun.modle.event;

/**
 * Created by kevin on 2017/2/11.
 * 留言墻右上菜單的點擊時間
 */

public class LeaveTopMenuEvent {
    String seei;

    public LeaveTopMenuEvent(String seei) {
        this.seei = seei;
    }

    public String getSeei() {
        return seei;
    }
}
