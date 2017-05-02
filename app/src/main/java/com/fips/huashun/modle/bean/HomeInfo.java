package com.fips.huashun.modle.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/8/30.
 */
public class HomeInfo implements Serializable{
    private List<CourseInfo> courseList1;

    private List<ActivityInfo> activityList;

    private List<CourseInfo> courseList2;

    private List<CourseInfo> courseList3;
    private List<CourseInfo> courseList4;

    private List<TeacherCourse> teacherList;

    private List<TopImgInfo> topImg;

    public List<CourseInfo> getCourseList1() {
        return courseList1;
    }

    public void setCourseList1(List<CourseInfo> courseList1) {
        this.courseList1 = courseList1;
    }

    public List<CourseInfo> getCourseList2() {
        return courseList2;
    }

    public void setCourseList2(List<CourseInfo> courseList2) {
        this.courseList2 = courseList2;
    }

    public List<ActivityInfo> getActivityList() {
        return activityList;
    }

    public void setActivityList(List<ActivityInfo> activityList) {
        this.activityList = activityList;
    }

    public List<CourseInfo> getCourseList3() {
        return courseList3;
    }

    public void setCourseList3(List<CourseInfo> courseList3) {
        this.courseList3 = courseList3;
    }

    public List<TeacherCourse> getTeacherList() {
        return teacherList;
    }

    public void setTeacherList(List<TeacherCourse> teacherList) {
        this.teacherList = teacherList;
    }

    public List<TopImgInfo> getTopImg() {
        return topImg;
    }

    public void setTopImg(List<TopImgInfo> topImg) {
        this.topImg = topImg;
    }

    public List<CourseInfo> getCourseList4() {
        return courseList4;
    }

    public void setCourseList4(List<CourseInfo> courseList4) {
        this.courseList4 = courseList4;
    }
}
