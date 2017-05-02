package com.fips.huashun.modle.event;

/**
 * Created by kevin on 2017/2/13.
 * 课程类型是否企业课程
 */

public class CourseTypeEvent {
    private boolean isEnterpriseCourse;

    public CourseTypeEvent(boolean isEnterpriseCourse) {
        this.isEnterpriseCourse = isEnterpriseCourse;
    }

    public boolean isEnterpriseCourse() {
        return isEnterpriseCourse;
    }

    public void setEnterpriseCourse(boolean enterpriseCourse) {
        isEnterpriseCourse = enterpriseCourse;
    }
}
