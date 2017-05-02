package com.fips.huashun.modle.bean;

import java.util.List;

/**
 * Created by kevin on 2017/2/6.
 */
public class HadSignUpModel {

    /**
     * cur_page : 1
     * page_count : 1
     * row : [{"depname":"测试部","filepath":"http://v1.52qmct.com/pic/upload/head/20_20161226035250.jpg","name":"肖文祥","tel":"15900956532","uid":"20"},{"depname":"学习部","filepath":"http://v1.52qmct.com/pic/upload/head/22_20161205091213.jpg","jobname":"部长","name":"吴卫","tel":"13016407747","uid":"22"}]
     * suc : y
     * total : 2
     */

    private int cur_page;
    private int page_count;
    private String suc;
    private String total;
    /**
     * depname : 测试部
     * filepath : http://v1.52qmct.com/pic/upload/head/20_20161226035250.jpg
     * name : 肖文祥
     * tel : 15900956532
     * uid : 20
     */

    private List<HadSignUpList> row;

    public int getCur_page() {
        return cur_page;
    }

    public void setCur_page(int cur_page) {
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

    public List<HadSignUpList> getRow() {
        return row;
    }

    public void setRow(List<HadSignUpList> row) {
        this.row = row;
    }

    public static class HadSignUpList {
        private String depname;
        private String filepath;
        private String name;
        private String tel;
        private String uid;
        private String jobname;

        public void setJobname(String jobname) {
            this.jobname = jobname;
        }

        public String getJobname() {
            return jobname;
        }

        public String getDepname() {
            return depname;
        }

        public void setDepname(String depname) {
            this.depname = depname;
        }

        public String getFilepath() {
            return filepath;
        }

        public void setFilepath(String filepath) {
            this.filepath = filepath;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }
    }
}
