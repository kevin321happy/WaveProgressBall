package com.fips.huashun.modle.bean;

import java.io.Serializable;

/**
 * 考试任务
 * Created by Administrator on 2016/10/9.
 */
public class ExamTask implements Serializable
{
    //考试id
    private String paperid;
    //考试名称
    private String testname;
    //考试时间
    private String testime;
    //用时
    private String showtime;
    //排名
    private String ordernum;
    //答对
    private String isright;
    //答错
    private String iswrong;
    //得分
    private String score;

    public String getPaperid()
    {
        return paperid;
    }

    public void setPaperid(String paperid)
    {
        this.paperid = paperid;
    }

    public String getTestname()
    {
        return testname;
    }

    public void setTestname(String testname)
    {
        this.testname = testname;
    }

    public String getTestime()
    {
        return testime;
    }

    public void setTestime(String testime)
    {
        this.testime = testime;
    }

    public String getShowtime()
    {
        return showtime;
    }

    public void setShowtime(String showtime)
    {
        this.showtime = showtime;
    }

    public String getOrdernum()
    {
        return ordernum;
    }

    public void setOrdernum(String ordernum)
    {
        this.ordernum = ordernum;
    }

    public String getIsright()
    {
        return isright;
    }

    public void setIsright(String isright)
    {
        this.isright = isright;
    }

    public String getIswrong()
    {
        return iswrong;
    }

    public void setIswrong(String iswrong)
    {
        this.iswrong = iswrong;
    }

    public String getScore()
    {
        return score;
    }

    public void setScore(String score)
    {
        this.score = score;
    }
}
