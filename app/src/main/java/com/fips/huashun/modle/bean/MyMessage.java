package com.fips.huashun.modle.bean;

import java.io.Serializable;

/**
 * 功能：我的消息
 * Created by Administrator on 2016/9/10.
 * @author 张柳 时间：2016年9月10日11:20:51
 */
public class MyMessage implements Serializable
{
    //消息id
    private String msgid;

    //消息标题
    private String msgtitle;

    //消息时间
    private String msgtime;

    //消息内容
    private String msgcontent;

    //消息状态
    private String msgstate;
    // 消息类型 1系统消息/2课程消息/3活动消息/4企业活动

    private String msgtype;

    // 活动或课程id 跳转需要
    private String detailid;
    // true 为展开 false 为收起
    private boolean msgOpen = false;

    public boolean getMsgOpen()
    {
        return msgOpen;
    }

    public void setMsgOpen(boolean msgOpen)
    {
        this.msgOpen = msgOpen;
    }
    
    public String getMsgid() {
        return msgid;
    }

    public void setMsgid(String msgid) {
        this.msgid = msgid;
    }

    public String getMsgtitle() {
        return msgtitle;
    }

    public void setMsgtitle(String msgtitle) {
        this.msgtitle = msgtitle;
    }

    public String getMsgtime() {
        return msgtime;
    }

    public void setMsgtime(String msgtime) {
        this.msgtime = msgtime;
    }

    public String getMsgcontent() {
        return msgcontent;
    }

    public void setMsgcontent(String msgcontent) {
        this.msgcontent = msgcontent;
    }

    public String getMsgstate() {
        return msgstate;
    }

    public void setMsgstate(String msgstate) {
        this.msgstate = msgstate;
    }

    public String getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }

    public String getDetailid() {
        return detailid;
    }

    public void setDetailid(String detailid) {
        this.detailid = detailid;
    }
}
