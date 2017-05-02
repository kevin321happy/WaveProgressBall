package com.fips.huashun.modle.bean;

/**
 * Created by kevin on 2017/2/8.
 * 返回状态信息的模型
 */
public class BackInfoModel {
    /**
     * info : 信息
     * status : true
     * suc : y
     */

    private String info;
    private boolean status;
    private String suc;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getSuc() {
        return suc;
    }

    public void setSuc(String suc) {
        this.suc = suc;
    }
}
