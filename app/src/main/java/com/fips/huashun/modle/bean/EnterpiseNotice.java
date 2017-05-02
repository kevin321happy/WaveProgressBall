package com.fips.huashun.modle.bean;

import java.io.Serializable;

/**
 * 功能：企业公告实体类
 * @author 张柳 时间：2016年9月13日21:30:57
 */
public class EnterpiseNotice implements Serializable
{

    //企业公告id
    private int noticeid;
    //公告标题
    private String title;
    //公告发布时间
    private String addtime;
    //公告内容
    private String content;

    public int getNoticeid()
    {
        return noticeid;
    }

    public void setNoticeid(int noticeid)
    {
        this.noticeid = noticeid;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getAddtime()
    {
        return addtime;
    }

    public void setAddtime(String addtime)
    {
        this.addtime = addtime;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }
}
