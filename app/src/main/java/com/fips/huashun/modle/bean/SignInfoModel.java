package com.fips.huashun.modle.bean;

import java.util.List;

/**
 * Created by kevin on 2017/2/6.
 */
public class SignInfoModel {

    /**
     * pid : 158
     * activitytitle : 活动主题2
     * activitytime : 2016-12-12 00:00:00
     * activityendtime : 2018-12-12 00:00:00
     * activityaddre : 武汉市洪山区融科智谷
     * row : [{"pid":"2","activityid":"158","signtime":"2017-02-06 10:50:25","signendtime":"2016-12-01 12:12:12","isfixed":"1","factsigntime":"2017-02-06 10:40:34","sstatus":"1","factsignendtime":null,"estatus":null},{"pid":"3","activityid":"158","signtime":"2016-12-01 12:12:12","signendtime":"2016-12-01 12:12:12","isfixed":"0","factsigntime":"2017-02-06 10:35:08","sstatus":"2","factsignendtime":null,"estatus":null}]
     * suc : y
     */

    private String pid;
    private String activitytitle;
    private String activitytime;
    private String activityendtime;
    private String activityaddre;
    private String suc;
    private String isshowregisterinfo;//是否显示报名信息

    public String getIsshowregisterinfo() {
        return isshowregisterinfo;
    }

    public void setIsshowregisterinfo(String isshowregisterinfo) {
        this.isshowregisterinfo = isshowregisterinfo;
    }

    /**
     * pid : 2
     * activityid : 158
     * signtime : 2017-02-06 10:50:25
     * signendtime : 2016-12-01 12:12:12
     * isfixed : 1
     * factsigntime : 2017-02-06 10:40:34
     * sstatus : 1
     * factsignendtime : null
     * estatus : null
     */

    private List<SignInfo> row;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getActivitytitle() {
        return activitytitle;
    }

    public void setActivitytitle(String activitytitle) {
        this.activitytitle = activitytitle;
    }

    public String getActivitytime() {
        return activitytime;
    }

    public void setActivitytime(String activitytime) {
        this.activitytime = activitytime;
    }

    public String getActivityendtime() {
        return activityendtime;
    }

    public void setActivityendtime(String activityendtime) {
        this.activityendtime = activityendtime;
    }

    public String getActivityaddre() {
        return activityaddre;
    }

    public void setActivityaddre(String activityaddre) {
        this.activityaddre = activityaddre;
    }

    public String getSuc() {
        return suc;
    }

    public void setSuc(String suc) {
        this.suc = suc;
    }

    public List<SignInfo> getRow() {
        return row;
    }

    public void setRow(List<SignInfo> row) {
        this.row = row;
    }

    public static class SignInfo {
        private String pid;
        private String activityid;
        private String signtime;
        private String signendtime;
        private String isfixed;
        private String factsigntime;
        private String sstatus;
        private String factsignendtime;
        private String estatus;

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getActivityid() {
            return activityid;
        }

        public void setActivityid(String activityid) {
            this.activityid = activityid;
        }

        public String getSigntime() {
            return signtime;
        }

        public void setSigntime(String signtime) {
            this.signtime = signtime;
        }

        public String getSignendtime() {
            return signendtime;
        }

        public void setSignendtime(String signendtime) {
            this.signendtime = signendtime;
        }

        public String getIsfixed() {
            return isfixed;
        }

        public void setIsfixed(String isfixed) {
            this.isfixed = isfixed;
        }

        public String getFactsigntime() {
            return factsigntime;
        }

        public void setFactsigntime(String factsigntime) {
            this.factsigntime = factsigntime;
        }

        public String getSstatus() {
            return sstatus;
        }

        public void setSstatus(String sstatus) {
            this.sstatus = sstatus;
        }

        public String getFactsignendtime() {
            return factsignendtime;
        }

        public void setFactsignendtime(String factsignendtime) {
            this.factsignendtime = factsignendtime;
        }

        public String getEstatus() {
            return estatus;
        }

        public void setEstatus(String estatus) {
            this.estatus = estatus;
        }
    }
}
