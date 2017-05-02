package com.fips.huashun.modle.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/9/17.
 */
public class PkInfo implements Serializable
{
    private List<PkList> pkList;
    private int rank;
    private int transcend;

    public List<PkList> getPkList()
    {
        return pkList;
    }

    public void setPkList(List<PkList> pkList)
    {
        this.pkList = pkList;
    }

    public int getRank()
    {
        return rank;
    }

    public void setRank(int rank)
    {
        this.rank = rank;
    }

    public int getTranscend()
    {
        return transcend;
    }

    public void setTranscend(int transcend)
    {
        this.transcend = transcend;
    }
}
