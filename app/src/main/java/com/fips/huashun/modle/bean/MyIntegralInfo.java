package com.fips.huashun.modle.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/9/17.
 */
public class MyIntegralInfo implements Serializable
{
    // 积分详情
    private IntegralInfo integralInfo;
    // 积分等级划分列表
    private List<IntegralLevel> integralLevelList;

    public IntegralInfo getIntegralInfo()
    {
        return integralInfo;
    }

    public void setIntegralInfo(IntegralInfo integralInfo)
    {
        this.integralInfo = integralInfo;
    }

    public List<IntegralLevel> getIntegralLevelList()
    {
        return integralLevelList;
    }

    public void setIntegralLevelList(List<IntegralLevel> integralLevelList)
    {
        this.integralLevelList = integralLevelList;
    }
}
