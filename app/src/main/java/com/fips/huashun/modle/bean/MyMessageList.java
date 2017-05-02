package com.fips.huashun.modle.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 功能：我的消息列表
 * Created by Administrator on 2016/9/10.
 *
 * @author 张柳 时间：2016年9月10日11:20:51
 */
public class MyMessageList implements Serializable
{
    private String actMsgCount;
    private String sysMsgCount;
    private String courseMsgCount;
    private List<MyMessage> msgList;

    public String getActMsgCount()
    {
        return actMsgCount;
    }

    public void setActMsgCount(String actMsgCount)
    {
        this.actMsgCount = actMsgCount;
    }

    public String getSysMsgCount()
    {
        return sysMsgCount;
    }

    public void setSysMsgCount(String sysMsgCount)
    {
        this.sysMsgCount = sysMsgCount;
    }

    public String getCourseMsgCount()
    {
        return courseMsgCount;
    }

    public void setCourseMsgCount(String courseMsgCount)
    {
        this.courseMsgCount = courseMsgCount;
    }

    public List<MyMessage> getMsgList()
    {
        return msgList;
    }

    public void setMsgList(List<MyMessage> msgList)
    {
        this.msgList = msgList;
    }
}
