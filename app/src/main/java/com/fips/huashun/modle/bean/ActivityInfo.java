package com.fips.huashun.modle.bean;

import java.io.Serializable;

/**
 * 功能：活动信息类
 *
 * @author xiefuqiang
 */

public class ActivityInfo implements Serializable
{
    private static final long serialVersionUID = 1L;
    //活动id
    private int activityid;
    //活动名称
    private String activityname;
    //活动图片
    private String activityimg;
    //活动标签
    private String activitytip;
    //活动内容
    private String activitycontent;
    //活动开始时间
    private String starttime;
    //活动结束时间
    private String endtime;
    //参与人数
    private String joinnum;
    //活动地点
    private String activityaddre;
    //活动地点X坐标
    private Double signpointx;
    //活动地点Y坐标
    private Double signpointy;
    //活动价格
    private String activityprice;
    //活动状态1进行中2已结束
    private String status;
    private String label1;
    private String label2;



    public int getActivityid()
    {
        return activityid;
    }

    public void setActivityid(int activityid)
    {
        this.activityid = activityid;
    }

    public String getActivityname()
    {
        return activityname;
    }

    public void setActivityname(String activityname)
    {
        this.activityname = activityname;
    }

    public String getActivityimg()
    {
        return activityimg;
    }

    public void setActivityimg(String activityimg)
    {
        this.activityimg = activityimg;
    }

    public String getActivitytip()
    {
        return activitytip;
    }

    public void setActivitytip(String activitytip)
    {
        this.activitytip = activitytip;
    }

    public String getActivitycontent()
    {
        return activitycontent;
    }

    public void setActivitycontent(String activitycontent)
    {
        this.activitycontent = activitycontent;
    }

    public String getStarttime()
    {
        return starttime;
    }

    public void setStarttime(String starttime)
    {
        this.starttime = starttime;
    }

    public String getEndtime()
    {
        return endtime;
    }

    public void setEndtime(String endtime)
    {
        this.endtime = endtime;
    }

    public String getJoinnum()
    {
        return joinnum;
    }

    public void setJoinnum(String joinnum)
    {
        this.joinnum = joinnum;
    }

    public String getActivityaddre()
    {
        return activityaddre;
    }

    public void setActivityaddre(String activityaddre)
    {
        this.activityaddre = activityaddre;
    }

    public Double getSignpointx()
    {
        return signpointx;
    }

    public void setSignpointx(Double signpointx)
    {
        this.signpointx = signpointx;
    }

    public Double getSignpointy()
    {
        return signpointy;
    }

    public void setSignpointy(Double signpointy)
    {
        this.signpointy = signpointy;
    }

    public String getActivityprice()
    {
        return activityprice;
    }

    public void setActivityprice(String activityprice)
    {
        this.activityprice = activityprice;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getLabel1() {
        return label1;
    }

    public void setLabel1(String label1) {
        this.label1 = label1;
    }

    public String getLabel2() {
        return label2;
    }

    public void setLabel2(String label2) {
        this.label2 = label2;
    }
}
