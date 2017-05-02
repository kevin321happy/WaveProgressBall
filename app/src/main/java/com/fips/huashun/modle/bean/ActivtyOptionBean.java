package com.fips.huashun.modle.bean;

import java.io.Serializable;

/**
 * 功能：投票选项实体类
 *
 * @author 张柳 时间：2016年9月18日14:15:12
 */
public class ActivtyOptionBean implements Serializable
{
    private int pid;
    //项目ID
    private String activityid;
    //选项
    private int optionlets;
    //内容
    private String content;
    private String selectNum;
    private boolean isChecked=false;

    public boolean isChecked()
    {
        return isChecked;
    }

    public void setChecked(boolean checked)
    {
        this.isChecked = checked;
    }

    public int getPid()
    {
        return pid;
    }

    public void setPid(int pid)
    {
        this.pid = pid;
    }

    public String getActivityid()
    {
        return activityid;
    }

    public void setActivityid(String activityid)
    {
        this.activityid = activityid;
    }

    public int getOptionlets()
    {
        return optionlets;
    }

    public void setOptionlets(int optionlets)
    {
        this.optionlets = optionlets;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getSelectNum()
    {
        return selectNum;
    }

    public void setSelectNum(String selectNum)
    {
        this.selectNum = selectNum;
    }
}