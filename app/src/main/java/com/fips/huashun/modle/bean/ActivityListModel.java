package com.fips.huashun.modle.bean;

import com.shuyu.common.model.RecyclerBaseModel;

import java.util.List;

/**
 * Created by kevin on 2017/2/4.
 */
public class ActivityListModel  {
    /**
     * cur_page : 1
     * page_count : 1
     * row : [{"activityaddre":"新的活动测试","activityendtime":"2018-12-12 00:00:00","activitytime":"2016-12-12 00:00:00","activitytitle":"活动测试1","filepath":"http://v1.52qmct.com/pic/KbjCj5hJTE.jpg","isonline":"0","ispaper":"1","issettickling":"0","issetwords":"0","issign":"1","issignup":"1","issurvey":"0","pid":"175"},{"activityaddre":"武汉市洪山区融科智谷","activityendtime":"2018-12-12 00:00:00","activitytime":"2016-12-12 00:00:00","activitytitle":"活动主题","filepath":"http://v1.52qmct.com/pic/fFcWhnMP3a.jpg","isonline":"0","ispaper":"0","issettickling":"0","issetwords":"0","issign":"0","issignup":"0","issurvey":"0","pid":"167"},{"activityaddre":"武汉市汉阳区武汉动物园","activityendtime":"2018-12-12 00:00:00","activitytime":"2016-12-12 00:00:00","activitytitle":"www","filepath":"http://v1.52qmct.com/pic/BnWPxJbmh6.jpg","isonline":"0","ispaper":"0","issettickling":"0","issetwords":"0","issign":"0","issignup":"0","issurvey":"0","pid":"166"},{"activityaddre":"武汉市洪山区融科智谷","activityendtime":"2018-12-12 00:00:00","activitytime":"2016-12-12 00:00:00","activitytitle":"活动主题2","filepath":"http://v1.52qmct.com/pic/2Z7zirENK7.jpg","isonline":"0","ispaper":"1","issettickling":"0","issetwords":"0","issign":"1","issignup":"1","issurvey":"1","pid":"158"},{"activityaddre":"武汉市洪山区融科智谷","activityendtime":"2018-12-12 12:00:00","activitytime":"2016-12-12 12:00:00","activitytitle":"活动主题1","isonline":"0","ispaper":"0","issettickling":"0","issetwords":"0","issign":"0","issignup":"0","issurvey":"0","pid":"157"},{"activityaddre":"武汉市洪山区融科智谷","activityendtime":"2018-12-12 12:00:00","activitytime":"2016-12-12 12:00:00","activitytitle":"活动主题","isonline":"0","ispaper":"0","issettickling":"0","issetwords":"0","issign":"0","issignup":"0","issurvey":"0","pid":"156"},{"activityaddre":"武汉市洪山区融科智谷","activityendtime":"2018-12-12 12:00:00","activitytime":"2016-12-12 12:00:00","activitytitle":"活动主题","isonline":"0","ispaper":"0","issettickling":"0","issetwords":"0","issign":"0","issignup":"0","issurvey":"0","pid":"155"},{"activityaddre":"武汉市洪山区融科智谷","activityendtime":"2018-12-12 12:00:00","activitytime":"2016-12-12 12:00:00","activitytitle":"一起来玩撒","isonline":"0","ispaper":"0","issettickling":"0","issetwords":"0","issign":"0","issignup":"0","issurvey":"0","pid":"143"}]
     * suc : y
     * total : 8
     */
    private String cur_page;
    private int page_count;
    private String suc;
    private String total;
    /**
     * activityaddre : 新的活动测试
     * activityendtime : 2018-12-12 00:00:00
     * activitytime : 2016-12-12 00:00:00
     * activitytitle : 活动测试1
     * filepath : http://v1.52qmct.com/pic/KbjCj5hJTE.jpg
     * isonline : 0
     * ispaper : 1
     * issettickling : 0
     * issetwords : 0
     * issign : 1
     * issignup : 1
     * issurvey : 0
     * pid : 175
     */

    private List<ActivityItemInfo> row;

    public String getCur_page() {
        return cur_page;
    }

    public void setCur_page(String cur_page) {
        this.cur_page = cur_page;
    }

    public int getPage_count() {
        return page_count;
    }

    public void setPage_count(int page_count) {
        this.page_count = page_count;
    }

    public String getSuc() {
        return suc;
    }

    public void setSuc(String suc) {
        this.suc = suc;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<ActivityItemInfo> getRow() {
        return row;
    }

    public void setRow(List<ActivityItemInfo> row) {
        this.row = row;
    }

    public static class ActivityItemInfo  extends RecyclerBaseModel{
        private String activityaddre;
        private String activityendtime;
        private String activitytime;
        private String activitytitle;
        private String filepath;
        private String isonline;
        private String ispaper;
        private String issettickling;
        private String issetwords;
        private String issign;
        private String issignup;
        private String issurvey;
        private String pid;

        public String getActivityaddre() {
            return activityaddre;
        }

        public void setActivityaddre(String activityaddre) {
            this.activityaddre = activityaddre;
        }

        public String getActivityendtime() {
            return activityendtime;
        }

        public void setActivityendtime(String activityendtime) {
            this.activityendtime = activityendtime;
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

        public String getFilepath() {
            return filepath;
        }

        public void setFilepath(String filepath) {
            this.filepath = filepath;
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

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }
    }
}
