package com.fips.huashun.modle.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 功能：投票实体类
 *
 * @author 张柳 时间：2016年9月18日14:15:12
 */
public class ActivtyVoteBean implements Serializable
{
    //
    private int pid;
    //大标题
    private String bigtitle;
    //投票标题
    private String votetitle;
    //说明
    private String explaincontent;
    //开始时间
    private String addtime;
    //结束时间
    private String endtime;
    //投票总票数
    private String selectNum;
    //奖励积分
    private String numerical;
    //是否已投票（0：已投票；1：未投票）
    private String isVote;

    private List<ActivtyOptionBean> optionList;

    public int getPid()
    {
        return pid;
    }

    public void setPid(int pid)
    {
        this.pid = pid;
    }

    public String getVotetitle()
    {
        return votetitle;
    }

    public void setVotetitle(String votetitle)
    {
        this.votetitle = votetitle;
    }

    public String getAddtime()
    {
        return addtime;
    }

    public void setAddtime(String addtime)
    {
        this.addtime = addtime;
    }

    public String getEndtime()
    {
        return endtime;
    }

    public void setEndtime(String endtime)
    {
        this.endtime = endtime;
    }

    public List<ActivtyOptionBean> getOptionList()
    {
        return optionList;
    }

    public void setOptionList(List<ActivtyOptionBean> optionList)
    {
        this.optionList = optionList;
    }

    public String getExplaincontent()
    {
        return explaincontent;
    }

    public void setExplaincontent(String explaincontent)
    {
        this.explaincontent = explaincontent;
    }

    public String getSelectNum()
    {
        return selectNum;
    }

    public void setSelectNum(String selectNum)
    {
        this.selectNum = selectNum;
    }

    public String getBigtitle()
    {
        return bigtitle;
    }

    public void setBigtitle(String bigtitle)
    {
        this.bigtitle = bigtitle;
    }

    public String getNumerical()
    {
        return numerical;
    }

    public void setNumerical(String numerical)
    {
        this.numerical = numerical;
    }

    public String getIsVote() {
        return isVote;
    }

    public void setIsVote(String isVote) {
        this.isVote = isVote;
    }
}