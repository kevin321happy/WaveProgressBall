package com.fips.huashun.modle.bean;

import java.io.Serializable;

/**
 * 功能：企业活动详情的通知
 */
public class EnterpriseActNoticeInfo implements Serializable
{

    /*//项目id
    private String activityid;*/
    //开始时间
    private String activitytime;
    //结束时间
    private String endtime;
    //地点
    private String address;
    //授课内容
    private String activityinfo;
    //服务
    private String acserver;
    //课堂要求
    private String acrequire;
    //项目负责人
    private String leaderName;
    //联系方式
    private String phone;
    //讲师姓名
    private String teacherName;


    public String getTeacherName()
    {
        return teacherName;
    }

    public void setTeacherName(String teacherName)
    {
        this.teacherName = teacherName;
    }

    /*public String getActivityid() {
        return activityid;
    }
    public void setActivityid(String activityid) {
        this.activityid = activityid;
    }*/
    public String getActivityinfo()
    {
        return activityinfo;
    }

    public void setActivityinfo(String activityinfo)
    {
        this.activityinfo = activityinfo;
    }

    public String getActivitytime()
    {
        return activitytime;
    }

    public void setActivitytime(String activitytime)
    {
        this.activitytime = activitytime;
    }

    public String getEndtime()
    {
        return endtime;
    }

    public void setEndtime(String endtime)
    {
        this.endtime = endtime;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getAcserver()
    {
        return acserver;
    }

    public void setAcserver(String acserver)
    {
        this.acserver = acserver;
    }

    public String getAcrequire()
    {
        return acrequire;
    }

    public void setAcrequire(String acrequire)
    {
        this.acrequire = acrequire;
    }

    public String getLeaderName()
    {
        return leaderName;
    }

    public void setLeaderName(String leaderName)
    {
        this.leaderName = leaderName;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }
}
