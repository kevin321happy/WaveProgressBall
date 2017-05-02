package com.fips.huashun.modle.bean;

import java.io.Serializable;

/**
 * 企业PK榜中积分排名对象
 *
 * @author 张柳 时间：2016年9月8日15:24:35
 */

public class PkList implements Serializable
{
    private static final long serialVersionUID = 1L;
    // 名次
    private String rank;
    // 姓名
    private String truename;
    // 头像路径
    private String filepath;
    // 总积分/参与数/完成数/均分
    private String total_points;
    // 部门人数
    private String peoplenum;
    // 部门名称
    private String depname;

    public String getRank()
    {
        return rank;
    }

    public void setRank(String rank)
    {
        this.rank = rank;
    }

    public String getTruename()
    {
        return truename;
    }

    public void setTruename(String truename)
    {
        this.truename = truename;
    }

    public String getFilepath()
    {
        return filepath;
    }

    public void setFilepath(String filepath)
    {
        this.filepath = filepath;
    }

    public String getTotal_points()
    {
        return total_points;
    }

    public void setTotal_points(String total_points)
    {
        this.total_points = total_points;
    }

    public String getPeoplenum()
    {
        return peoplenum;
    }
    public void setPeoplenum(String peoplenum)
    {
        this.peoplenum = peoplenum;
    }

    public String getDepname()
    {
        return depname;
    }

    public void setDepname(String depname)
    {
        this.depname = depname;
    }
}
