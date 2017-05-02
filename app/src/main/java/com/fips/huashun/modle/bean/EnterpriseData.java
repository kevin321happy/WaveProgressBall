package com.fips.huashun.modle.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 功能：企业数据封装类
 * Created by Administrator on 2016/9/13.
 * @author 张柳 时间：2016年9月13日10:50:55
 */
public class EnterpriseData implements Serializable
{
    // 企业信息
    private EnterpriseInfo enterpriseinfo;
    // 企业课程列表
    private List<CourseInfo> entCourseList;

    public EnterpriseInfo getEnterpriseinfo() {
        return enterpriseinfo;
    }

    public void setEnterpriseinfo(EnterpriseInfo enterpriseinfo) {
        this.enterpriseinfo = enterpriseinfo;
    }

    public List<CourseInfo> getEntCourseList()
    {
        return entCourseList;
    }

    public void setEntCourseList(List<CourseInfo> entCourseList)
    {
        this.entCourseList = entCourseList;
    }
}
