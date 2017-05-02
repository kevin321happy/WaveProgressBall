package com.fips.huashun.modle.bean;

import java.util.List;

/**
 * Created by kevin on 2017/2/11.
 * 互动空间公告的模型
 */

public class SpaceNoticeModel {

    /**
     * pid : 8
     * activityid : 158
     * proclamation : 试试就试试
     * type : 2
     * manager : 413
     * addtime : 2017-02-11 13:27:44
     * isfunction : 1
     * logo_url : http://v1.52qmct.com/pic/4hRRhGwm4C.jpg
     * suc : y
     * user : [{"pid":"413","uid":"34","name":"陈峰","worknum":"cf"}]
     */

    private String pid;
    private String activityid;
    private String proclamation;
    private String type;
    private String manager;
    private String addtime;
    private String isfunction;
    private String logo_url;
    private String suc;
    private List<UserBean> user;

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

    public String getProclamation() {
        return proclamation;
    }

    public void setProclamation(String proclamation) {
        this.proclamation = proclamation;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getIsfunction() {
        return isfunction;
    }

    public void setIsfunction(String isfunction) {
        this.isfunction = isfunction;
    }

    public String getLogo_url() {
        return logo_url;
    }

    public void setLogo_url(String logo_url) {
        this.logo_url = logo_url;
    }

    public String getSuc() {
        return suc;
    }

    public void setSuc(String suc) {
        this.suc = suc;
    }

    public List<UserBean> getUser() {
        return user;
    }

    public void setUser(List<UserBean> user) {
        this.user = user;
    }

    public static class UserBean {
        /**
         * pid : 413
         * uid : 34
         * name : 陈峰
         * worknum : cf
         */

        private String pid;
        private String uid;
        private String name;
        private String worknum;

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getWorknum() {
            return worknum;
        }

        public void setWorknum(String worknum) {
            this.worknum = worknum;
        }
    }
}
