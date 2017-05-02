package com.fips.huashun.modle.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 功能：活动详情页面接收信息类
 * Created by Administrator on 2016/9/14.
 * @author 张柳 时间：2016年9月19日14:24:02
 */
public class ActivityDetail implements Serializable
{
    // 是否报名（0 未报名 1 已报名）
    private String joinstate;
    // 活动信息类
    private ActivityInfo activity;
    // 已报名人数集合
    private List<ActivityJoinInfo> joinnerList;

    public String getJoinstate()
    {
        return joinstate;
    }

    public void setJoinstate(String joinstate)
    {
        this.joinstate = joinstate;
    }

    public ActivityInfo getActivity()
    {
        return activity;
    }

    public void setActivity(ActivityInfo activity)
    {
        this.activity = activity;
    }

    public List<ActivityJoinInfo> getJoinnerList()
    {
        return joinnerList;
    }

    public void setJoinnerList(List<ActivityJoinInfo> joinnerList)
    {
        this.joinnerList = joinnerList;
    }
}
