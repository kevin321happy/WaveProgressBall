package com.fips.huashun.modle.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/9/2.
 */
public class SearchInfo {
    private List<HistoryList> historyList ;

    private List<String> hotCourse ;

    private List<String> hotTeacher ;

    public List<HistoryList> getHistoryList() {
        return historyList;
    }

    public void setHistoryList(List<HistoryList> historyList) {
        this.historyList = historyList;
    }

    public List<String> getHotTeacher() {
        return hotTeacher;
    }

    public void setHotTeacher(List<String> hotTeacher) {
        this.hotTeacher = hotTeacher;
    }

    public List<String> getHotCourse() {
        return hotCourse;
    }

    public void setHotCourse(List<String> hotCourse) {
        this.hotCourse = hotCourse;
    }
}
