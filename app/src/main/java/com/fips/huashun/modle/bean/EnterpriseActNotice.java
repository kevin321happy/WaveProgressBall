package com.fips.huashun.modle.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 功能：企业活动详情的通知
 * Created by Administrator on 2016/9/16.
 */
public class EnterpriseActNotice implements Serializable
{
    // 详情
    private EnterpriseActNoticeInfo activityRequ;
    // 课表
    private List<AcplanInfo> acplanList;

    public EnterpriseActNoticeInfo getActivityRequ()
    {
        return activityRequ;
    }

    public void setActivityRequ(EnterpriseActNoticeInfo activityRequ)
    {
        this.activityRequ = activityRequ;
    }

    public List<AcplanInfo> getAcplanList()
    {
        return acplanList;
    }

    public void setAcplanList(List<AcplanInfo> acplanList)
    {
        this.acplanList = acplanList;
    }
}
