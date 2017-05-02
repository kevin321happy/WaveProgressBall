package com.fips.huashun.modle.bean;

/**
 * Created by Administrator on 2016/9/8.
 */
public class RankInfo {
    private String allRanking;

    private String allStudyTime;

    private String dayRanking;

    private String dayStudyTime;

    private String userid;

    private String weekRanking;

    private String weekStudyTime;

    public void setAllRanking(String allRanking){
        this.allRanking = allRanking;
    }
    public String getAllRanking(){
        return this.allRanking;
    }
    public void setAllStudyTime(String allStudyTime){
        this.allStudyTime = allStudyTime;
    }
    public String getAllStudyTime(){
        return this.allStudyTime;
    }
    public void setDayRanking(String dayRanking){
        this.dayRanking = dayRanking;
    }
    public String getDayRanking(){
        return this.dayRanking;
    }
    public void setDayStudyTime(String dayStudyTime){
        this.dayStudyTime = dayStudyTime;
    }
    public String getDayStudyTime(){
        return this.dayStudyTime;
    }
    public void setUserid(String userid){
        this.userid = userid;
    }
    public String getUserid(){
        return this.userid;
    }
    public void setWeekRanking(String weekRanking){
        this.weekRanking = weekRanking;
    }
    public String getWeekRanking(){
        return this.weekRanking;
    }
    public void setWeekStudyTime(String weekStudyTime){
        this.weekStudyTime = weekStudyTime;
    }
    public String getWeekStudyTime(){
        return this.weekStudyTime;
    }
}
