package com.fips.huashun.modle.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/9/29.
 */
public class CouserMulu {
    private String buytype;

    private List<CouseMuluInfo> data;

    public String getBuytype() {
        return buytype;
    }

    public void setBuytype(String buytype) {
        this.buytype = buytype;
    }

    public List<CouseMuluInfo> getData() {
        return data;
    }

    public void setData(List<CouseMuluInfo> data) {
        this.data = data;
    }
}
