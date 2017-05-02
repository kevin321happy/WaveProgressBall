package com.fips.huashun.modle.bean;

import java.io.Serializable;

/**
 * 功能：用户积分查询
 *
 * @author 张柳 时间：2016年9月7日17:23:20
 */
public class IntegralInfo implements Serializable
{

    private static final long serialVersionUID = 1L;
    // 我的当前积分
    private int points;
    //我的总积分
    private int total_points;
    //本周新增积分
    private int weekintegral;
    // 等级编码
    private String levelcode;
    // 等级名称
    private String levelname;
    // 等级范围大
    private int levelpointsmax;
    // 用户本周新增积分排名
    private String weekRank;
    // 用户积分在企业中排名
    private String enterpriseRangk;

    public String getWeekRank()
    {
        return weekRank;
    }

    public void setWeekRank(String weekRank)
    {
        this.weekRank = weekRank;
    }

    public String getEnterpriseRangk()
    {
        return enterpriseRangk;
    }

    public void setEnterpriseRangk(String enterpriseRangk)
    {
        this.enterpriseRangk = enterpriseRangk;
    }

    public int getPoints()
    {
        return points;
    }

    public void setPoints(int points)
    {
        this.points = points;
    }

    public int getTotal_points()
    {
        return total_points;
    }

    public void setTotal_points(int total_points)
    {
        this.total_points = total_points;
    }

    public int getWeekintegral()
    {
        return weekintegral;
    }

    public void setWeekintegral(int weekintegral)
    {
        this.weekintegral = weekintegral;
    }

    public String getLevelcode()
    {
        return levelcode;
    }

    public void setLevelcode(String levelcode)
    {
        this.levelcode = levelcode;
    }

    public String getLevelname()
    {
        return levelname;
    }

    public void setLevelname(String levelname)
    {
        this.levelname = levelname;
    }

    public int getLevelpointsmax()
    {
        return levelpointsmax;
    }

    public void setLevelpointsmax(int levelpointsmax)
    {
        this.levelpointsmax = levelpointsmax;
    }

}
