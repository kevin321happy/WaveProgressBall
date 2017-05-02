package com.fips.huashun.modle.bean;
//保存签到签退信息
public class SignInfo{
    private String pid;
    private String activityid;
    private String signtime;
    private String signendtime;
    private String isfixed;
    private String factsigntime;
    private String sstatus;
    private String factsignendtime;
    private String estatus;
    //private boolean is;//当为ture时是签到，false是签退
    private int type;//类型 1为签到 2签退
    private int position;//当前是第几签
    public SignInfo() {
    }

    public SignInfo(String pid, String activityid, String signtime, String signendtime, String isfixed, String factsigntime, String sstatus, String factsignendtime, String estatus, int type , int position) {
        this.pid = pid;
        this.activityid = activityid;
        this.signtime = signtime;
        this.signendtime = signendtime;
        this.isfixed = isfixed;
        this.factsigntime = factsigntime;
        this.sstatus = sstatus;
        this.factsignendtime = factsignendtime;
        this.estatus = estatus;
        this.type = type;
        this.position = position;
    }

    public SignInfo(String pid, String activityid, String signtime, String signendtime, String isfixed, String factsigntime, String sstatus, String factsignendtime, String estatus, int type) {
        this.pid = pid;
        this.activityid = activityid;
        this.signtime = signtime;
        this.signendtime = signendtime;
        this.isfixed = isfixed;
        this.factsigntime = factsigntime;
        this.sstatus = sstatus;
        this.factsignendtime = factsignendtime;
        this.estatus = estatus;
        this.type = type;
    }

    public SignInfo(String pid, String activityid, String signtime, String signendtime, String factsigntime, String isfixed, String sstatus, String factsignendtime, String estatus) {
        this.pid = pid;
        this.activityid = activityid;
        this.signtime = signtime;
        this.signendtime = signendtime;
        this.factsigntime = factsigntime;
        this.isfixed = isfixed;
        this.sstatus = sstatus;
        this.factsignendtime = factsignendtime;
        this.estatus = estatus;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getPid() {
        return pid;
    }

    public String getActivityid() {
        return activityid;
    }

    public String getSigntime() {
        return signtime;
    }

    public String getSignendtime() {
        return signendtime;
    }

    public String getIsfixed() {
        return isfixed;
    }

    public String getFactsigntime() {
        return factsigntime;
    }

    public String getSstatus() {
        return sstatus;
    }

    public String getFactsignendtime() {
        return factsignendtime;
    }

    public String getEstatus() {
        return estatus;
    }


    public void setPid(String pid) {
        this.pid = pid;
    }

    public void setActivityid(String activityid) {
        this.activityid = activityid;
    }

    public void setSigntime(String signtime) {
        this.signtime = signtime;
    }

    public void setSignendtime(String signendtime) {
        this.signendtime = signendtime;
    }

    public void setIsfixed(String isfixed) {
        this.isfixed = isfixed;
    }

    public void setFactsigntime(String factsigntime) {
        this.factsigntime = factsigntime;
    }

    public void setSstatus(String sstatus) {
        this.sstatus = sstatus;
    }

    public void setFactsignendtime(String factsignendtime) {
        this.factsignendtime = factsignendtime;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "SignInfo{" +
                "pid='" + pid + '\'' +
                ", activityid='" + activityid + '\'' +
                ", signtime='" + signtime + '\'' +
                ", signendtime='" + signendtime + '\'' +
                ", isfixed='" + isfixed + '\'' +
                ", factsigntime='" + factsigntime + '\'' +
                ", sstatus='" + sstatus + '\'' +
                ", factsignendtime='" + factsignendtime + '\'' +
                ", estatus='" + estatus + '\'' +
                ", type=" + type +
                '}';
    }
}
