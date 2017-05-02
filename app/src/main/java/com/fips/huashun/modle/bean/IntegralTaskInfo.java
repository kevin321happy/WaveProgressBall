package com.fips.huashun.modle.bean;

import java.io.Serializable;

/**
 * 功能：积分任务实体类
 */
public class IntegralTaskInfo implements Serializable
{
    //任务id
    private String taskid;
    //任务状态
    private String state;
    //任务名称
    private String tasktitle;
    //任务说明
    private String taskcontent;
    //任务动作
    private String taskaction;
    //任务奖励
    private String taskreward;
    //任务奖励类型
    private String taskrewardtype;
    //系统任务阶段
    private String taskstage;

    public String getTaskid()
    {
        return taskid;
    }

    public void setTaskid(String taskid)
    {
        this.taskid = taskid;
    }

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public String getTasktitle()
    {
        return tasktitle;
    }

    public void setTasktitle(String tasktitle)
    {
        this.tasktitle = tasktitle;
    }

    public String getTaskcontent()
    {
        return taskcontent;
    }

    public void setTaskcontent(String taskcontent)
    {
        this.taskcontent = taskcontent;
    }

    public String getTaskaction()
    {
        return taskaction;
    }

    public void setTaskaction(String taskaction)
    {
        this.taskaction = taskaction;
    }

    public String getTaskreward()
    {
        return taskreward;
    }

    public void setTaskreward(String taskreward)
    {
        this.taskreward = taskreward;
    }

    public String getTaskrewardtype()
    {
        return taskrewardtype;
    }

    public void setTaskrewardtype(String taskrewardtype)
    {
        this.taskrewardtype = taskrewardtype;
    }

    public String getTaskstage()
    {
        return taskstage;
    }

    public void setTaskstage(String taskstage)
    {
        this.taskstage = taskstage;
    }
}
