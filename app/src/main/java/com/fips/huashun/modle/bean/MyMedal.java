package com.fips.huashun.modle.bean;

import java.io.Serializable;

/**
 * 功能：我的勋章
 * Created by Administrator on 2016/10/7.
 * @author 张柳 时间：2016年10月7日14:59:04
 */
public class MyMedal implements Serializable
{
    //勋章id
    private String medid;
    //勋章名称
    private String medalname;
    //勋章说明
    private String medinfo;
    //动作
    private String medalation;
    //勋章图标
    private String medalimg;
    //获得状态 0未完成 1已完成
    private String state;

    public String getMedid()
    {
        return medid;
    }

    public void setMedid(String medid)
    {
        this.medid = medid;
    }

    public String getMedalname()
    {
        return medalname;
    }

    public void setMedalname(String medalname)
    {
        this.medalname = medalname;
    }

    public String getMedinfo()
    {
        return medinfo;
    }

    public void setMedinfo(String medinfo)
    {
        this.medinfo = medinfo;
    }

    public String getMedalation()
    {
        return medalation;
    }

    public void setMedalation(String medalation)
    {
        this.medalation = medalation;
    }

    public String getMedalimg()
    {
        return medalimg;
    }

    public void setMedalimg(String medalimg)
    {
        this.medalimg = medalimg;
    }

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }
}
