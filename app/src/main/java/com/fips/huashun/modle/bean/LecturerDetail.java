package com.fips.huashun.modle.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 功能;讲师详情页的数据
 * Created by Administrator on 2016/9/27.
 */
public class LecturerDetail implements Serializable
{
    private TeacherCourse teacherinfo;
    private List<CourseInfo> courseList;
    // 是否收藏（0否 1是）
    private String iscollect;
    // 是否点赞（0否 1是）
    private String isroick;

    public TeacherCourse getTeacherinfo()
    {
        return teacherinfo;
    }

    public void setTeacherinfo(TeacherCourse teacherinfo)
    {
        this.teacherinfo = teacherinfo;
    }

    public List<CourseInfo> getCourseList()
    {
        return courseList;
    }

    public void setCourseList(List<CourseInfo> courseList)
    {
        this.courseList = courseList;
    }
    public String getIscollect()
    {
        return iscollect;
    }

    public void setIscollect(String iscollect)
    {
        this.iscollect = iscollect;
    }

    public String getIsroick()
    {
        return isroick;
    }

    public void setIsroick(String isroick)
    {
        this.isroick = isroick;
    }
}
