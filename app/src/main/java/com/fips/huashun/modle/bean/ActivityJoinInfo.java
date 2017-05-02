package com.fips.huashun.modle.bean;

import java.io.Serializable;

/**
 * 功能：活动详情的加入人员信息
 * Created by Administrator on 2016/9/14.
 */
public class ActivityJoinInfo implements Serializable
{
    // ID
    private String joinnerid;
    //头像
    private String joinnerimg;
    // 姓名
    private String joinnername;
    // 手机号
    private String joinnerphone;

    public String getJoinnerid()
    {
        return joinnerid;
    }

    public void setJoinnerid(String joinnerid)
    {
        this.joinnerid = joinnerid;
    }

    public String getJoinnerimg()
    {
        return joinnerimg;
    }

    public void setJoinnerimg(String joinnerimg)
    {
        this.joinnerimg = joinnerimg;
    }

    public String getJoinnername()
    {
        return joinnername;
    }

    public void setJoinnername(String joinnername)
    {
        this.joinnername = joinnername;
    }

    public String getJoinnerphone()
    {
        return joinnerphone;
    }

    public void setJoinnerphone(String joinnerphone)
    {
        this.joinnerphone = joinnerphone;
    }
}
