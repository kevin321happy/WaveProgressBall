package com.fips.huashun.modle.event;

/**
 * Created by kevin on 2017/2/18.
 * 企业活动列表加载
 */

public class ActListLoadEvent {
    private boolean loading;//是否正在加载中

    public ActListLoadEvent(boolean loading) {
        this.loading = loading;
    }

    public boolean isLoading() {
        return loading;
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
    }
}
