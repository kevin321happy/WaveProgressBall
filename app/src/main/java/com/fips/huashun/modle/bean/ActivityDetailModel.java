package com.fips.huashun.modle.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by kevin on 2017/2/4.
 * 使用了缓存需实现序列化接口
 */
public class ActivityDetailModel implements Serializable {

    /**
     * activityaddre : 武汉市洪山区融科智谷
     * activitycontent : 新的活动
     * activityendtime : 2018-12-12 00:00:00
     * activityimg : 99719976
     * activitytime : 2016-12-12 00:00:00
     * activitytitle : 活动主题2
     * imgpath : http://v1.52qmct.com/pic/2Z7zirENK7.jpg
     * isonline : 0
     * ispaper : 1
     * issettickling : 0
     * issetwords : 0
     * issign : 1
     * issignup : 1
     * issurvey : 1
     * maxnum : 5
     * pid : 158
     * sign_up : [{"headpath":"http://v1.52qmct.com/pic/upload/head/185_20161216110515.jpg","uid":"116"},{"headpath":"http://v1.52qmct.com/pic/upload/head/185_20161216110515.jpg","uid":"118"},{"headpath":"http://v1.52qmct.com/pic/upload/head/185_20161216110515.jpg","uid":"22"}]
     * sign_up_total : 3
     * suc : y
     */

    private String activityaddre;
    private String activitycontent;
    private String activityendtime;
    private String activityimg;
    private String activitytime;
    private String activitytitle;
    private String imgpath;
    private String isonline;
    private String ispaper;
    private String issettickling;
    private String issetwords;
    private String issign;
    private String issignup;
    private String issurvey;
    private String maxnum;
    private String pid;
    private String sign_up_total;
    private String usr_sign_up;
    private String suc;
    private String registerenddate;
    private String registerstartdate;
    private String isshowregisterinfo;//是否显示报名信息

    public String getIsshowregisterinfo() {
        return isshowregisterinfo;
    }

    public void setIsshowregisterinfo(String isshowregisterinfo) {
        this.isshowregisterinfo = isshowregisterinfo;
    }

    /**
     * headpath : http://v1.52qmct.com/pic/upload/head/185_20161216110515.jpg
     * uid : 116
     */

    private List<SignUpBean> sign_up;

    public String getUsr_sign_up() {
        return usr_sign_up;
    }

    public void setUsr_sign_up(String usr_sign_up) {
        this.usr_sign_up = usr_sign_up;
    }

    public void setRegisterenddate(String registerenddate) {
        this.registerenddate = registerenddate;
    }

    public void setRegisterstartdate(String registerstartdate) {
        this.registerstartdate = registerstartdate;
    }


    public String getRegisterenddate() {
        return registerenddate;
    }

    public String getRegisterstartdate() {
        return registerstartdate;
    }

    public String getActivityaddre() {
        return activityaddre;
    }

    public void setActivityaddre(String activityaddre) {
        this.activityaddre = activityaddre;
    }

    public String getActivitycontent() {
        return activitycontent;
    }

    public void setActivitycontent(String activitycontent) {
        this.activitycontent = activitycontent;
    }

    public String getActivityendtime() {
        return activityendtime;
    }

    public void setActivityendtime(String activityendtime) {
        this.activityendtime = activityendtime;
    }

    public String getActivityimg() {
        return activityimg;
    }

    public void setActivityimg(String activityimg) {
        this.activityimg = activityimg;
    }

    public String getActivitytime() {
        return activitytime;
    }

    public void setActivitytime(String activitytime) {
        this.activitytime = activitytime;
    }

    public String getActivitytitle() {
        return activitytitle;
    }

    public void setActivitytitle(String activitytitle) {
        this.activitytitle = activitytitle;
    }

    public String getImgpath() {
        return imgpath;
    }

    public void setImgpath(String imgpath) {
        this.imgpath = imgpath;
    }

    public String getIsonline() {
        return isonline;
    }

    public void setIsonline(String isonline) {
        this.isonline = isonline;
    }

    public String getIspaper() {
        return ispaper;
    }

    public void setIspaper(String ispaper) {
        this.ispaper = ispaper;
    }

    public String getIssettickling() {
        return issettickling;
    }

    public void setIssettickling(String issettickling) {
        this.issettickling = issettickling;
    }

    public String getIssetwords() {
        return issetwords;
    }

    public void setIssetwords(String issetwords) {
        this.issetwords = issetwords;
    }

    public String getIssign() {
        return issign;
    }

    public void setIssign(String issign) {
        this.issign = issign;
    }

    public String getIssignup() {
        return issignup;
    }

    public void setIssignup(String issignup) {
        this.issignup = issignup;
    }

    public String getIssurvey() {
        return issurvey;
    }

    public void setIssurvey(String issurvey) {
        this.issurvey = issurvey;
    }

    public String getMaxnum() {
        return maxnum;
    }

    public void setMaxnum(String maxnum) {
        this.maxnum = maxnum;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getSign_up_total() {
        return sign_up_total;
    }

    public void setSign_up_total(String sign_up_total) {
        this.sign_up_total = sign_up_total;
    }

    public String getSuc() {
        return suc;
    }

    public void setSuc(String suc) {
        this.suc = suc;
    }

    public List<SignUpBean> getSign_up() {
        return sign_up;
    }

    public void setSign_up(List<SignUpBean> sign_up) {
        this.sign_up = sign_up;
    }

    public static class SignUpBean {
        private String headpath;
        private String uid;

        public String getHeadpath() {
            return headpath;
        }

        public void setHeadpath(String headpath) {
            this.headpath = headpath;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }
    }
}
