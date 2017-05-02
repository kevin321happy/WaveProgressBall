package com.fips.huashun.modle.bean;

import java.io.Serializable;

/**
 * 功能：企业实体类
 * Created by Administrator on 2016/9/13.
 * @author 张柳 时间：2016年9月13日09:45:22
 *
 */
public class EnterpriseInfo implements Serializable
{
    //企业id
    private String enid;
    //部门id
    private String deptid;
    //企业名称
    private String ename;
    //企业logo
    private String elogo;
    //企业公告
    private String enotice;

    public String getDeptid() {
        return deptid;
    }

    public void setDeptid(String deptid) {
        this.deptid = deptid;
    }

    public String getEnid()
    {
        return enid;
    }

    public void setEnid(String enid)
    {
        this.enid = enid;
    }

    public String getEname()
    {
        return ename;
    }

    public void setEname(String ename)
    {
        this.ename = ename;
    }

    public String getEnotice()
    {
        return enotice;
    }

    public void setEnotice(String enotice)
    {
        this.enotice = enotice;
    }

    public String getElogo()
    {
        return elogo;
    }

    public void setElogo(String elogo)
    {
        this.elogo = elogo;
    }
}
