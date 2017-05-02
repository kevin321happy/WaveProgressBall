package com.fips.huashun.modle.bean;
import java.util.List;
/**
 * Created by kevin on 2017/2/15.
 * 留言回复的model
 */
public class LeaveReplyModel {

    /**
     * toprow : [{"top":{"pid":"14","space_wordsid":"2","topid":"4","getid":"76","getname":"李英武","label":null,"headurl":"http://v1.52qmct.com/pic/a0.jpg","putid":"56","putname":"王萌","words":"是的","addtime":null,"row":[]}},{"top":{"pid":"13","space_wordsid":"2","topid":"4","getid":"76","getname":"李英武","label":null,"headurl":"http://v1.52qmct.com/pic/a0.jpg","putid":"56","putname":"王萌","words":"是的","addtime":null,"row":[]}},{"top":{"pid":"12","space_wordsid":"2","topid":"4","getid":"76","getname":"李英武","label":null,"headurl":"http://v1.52qmct.com/pic/a0.jpg","putid":"56","putname":"王萌","words":"是的","addtime":null,"row":[]}},{"top":{"pid":"11","space_wordsid":"2","topid":"0","getid":"76","getname":"李英武","label":null,"headurl":"http://v1.52qmct.com/pic/a0.jpg","putid":null,"putname":null,"words":"是的","addtime":null,"row":[]}},{"top":{"pid":"10","space_wordsid":"2","topid":"4","getid":"76","getname":"李英武","label":null,"headurl":"http://v1.52qmct.com/pic/a0.jpg","putid":"56","putname":"王萌","words":"是的","addtime":null,"row":[]}},{"top":{"pid":"5","space_wordsid":"2","topid":"4","getid":"56","getname":"王萌","label":null,"headurl":"http://v1.52qmct.com/pic/a0.jpg","putid":"76","putname":"李英武","words":"三大件啊","addtime":null,"row":[]}},{"top":{"pid":"4","space_wordsid":"2","topid":"0","getid":"76","getname":"李英武","label":null,"headurl":"http://v1.52qmct.com/pic/a0.jpg","putid":null,"putname":"","words":"什么东西","addtime":null,"row":[{"pid":"14","space_wordsid":"2","topid":"4","getid":"76","getname":"李英武","label":null,"headurl":"http://v1.52qmct.com/pic/a0.jpg","putid":"56","putname":"王萌","words":"是的","addtime":null},{"pid":"13","space_wordsid":"2","topid":"4","getid":"76","getname":"李英武","label":null,"headurl":"http://v1.52qmct.com/pic/a0.jpg","putid":"56","putname":"王萌","words":"是的","addtime":"是的"},{"pid":"12","space_wordsid":"2","topid":"4","getid":"76","getname":"李英武","label":null,"headurl":"http://v1.52qmct.com/pic/a0.jpg","putid":"56","putname":"王萌","words":"是的","addtime":"56"},{"pid":"10","space_wordsid":"2","topid":"4","getid":"76","getname":"李英武","label":null,"headurl":"http://v1.52qmct.com/pic/a0.jpg","putid":"56","putname":"王萌","words":"是的","addtime":null},{"pid":"5","space_wordsid":"2","topid":"4","getid":"56","getname":"王萌","label":null,"headurl":"http://v1.52qmct.com/pic/a0.jpg","putid":"76","putname":"李英武","words":"三大件啊","addtime":null}]}}]
     * suc : y
     */

    private String suc;
    /**
     * top : {"pid":"14","space_wordsid":"2","topid":"4","getid":"76","getname":"李英武","label":null,"headurl":"http://v1.52qmct.com/pic/a0.jpg","putid":"56","putname":"王萌","words":"是的","addtime":null,"row":[]}
     */

    private List<ToprowBean> toprow;

    public String getSuc() {
        return suc;
    }

    public void setSuc(String suc) {
        this.suc = suc;
    }

    public List<ToprowBean> getToprow() {
        return toprow;
    }

    public void setToprow(List<ToprowBean> toprow) {
        this.toprow = toprow;
    }

    public static class ToprowBean {

        /**
         * pid : 14
         * space_wordsid : 2
         * topid : 4
         * getid : 76
         * getname : 李英武
         * label : null
         * headurl : http://v1.52qmct.com/pic/a0.jpg
         * putid : 56
         * putname : 王萌
         * words : 是的
         * addtime : null
         * row : []
         */

        private TopBean top;

        public TopBean getTop() {
            return top;
        }

        public void setTop(TopBean top) {
            this.top = top;
        }

        public static class TopBean {

            private String pid;
            private String space_wordsid;
            private String topid;
            private String getid;
            private String getname;
            private String label;
            private String headurl;
            private String putid;
            private String putname;
            private String words;
            private String addtime;
            private List<RowBean> row;

            public String getPid() {
                return pid;
            }

            public void setPid(String pid) {
                this.pid = pid;
            }

            public String getSpace_wordsid() {
                return space_wordsid;
            }

            public void setSpace_wordsid(String space_wordsid) {
                this.space_wordsid = space_wordsid;
            }

            public String getTopid() {
                return topid;
            }

            public void setTopid(String topid) {
                this.topid = topid;
            }

            public String getGetid() {
                return getid;
            }

            public void setGetid(String getid) {
                this.getid = getid;
            }

            public String getGetname() {
                return getname;
            }

            public void setGetname(String getname) {
                this.getname = getname;
            }

            public String getLabel() {
                return label;
            }

            public void setLabel(String label) {
                this.label = label;
            }

            public String getHeadurl() {
                return headurl;
            }

            public void setHeadurl(String headurl) {
                this.headurl = headurl;
            }

            public String getPutid() {
                return putid;
            }

            public void setPutid(String putid) {
                this.putid = putid;
            }

            public String getPutname() {
                return putname;
            }

            public void setPutname(String putname) {
                this.putname = putname;
            }

            public String getWords() {
                return words;
            }

            public void setWords(String words) {
                this.words = words;
            }

            public String getAddtime() {
                return addtime;
            }

            public void setAddtime(String addtime) {
                this.addtime = addtime;
            }

            public List<RowBean> getRow() {
                return row;
            }

            public void setRow(List<RowBean> row) {
                this.row = row;
            }
        }
        public static class RowBean {
            /**
             * pid : 5
             * space_wordsid : 2
             * topid : 4
             * getid : 56
             * getname : 王萌
             * putid : 76
             * putname : 李英武
             * words : 三大件啊
             *
             */
            private String pid;
            private String space_wordsid;
            private String topid;
            private String getid;
            private String getname;
            private String putid;
            private String putname;
            private String words;
            private String addtime;
            private String headurl;
            private String label;

            public String getLabel() {
                return label;
            }

            public void setLabel(String label) {
                this.label = label;
            }

            public String getHeadurl() {
                return headurl;
            }
            public void setHeadurl(String headurl) {
                this.headurl = headurl;
            }
            public String getAddtime() {
                return addtime;
            }
            public void setAddtime(String addtime) {
                this.addtime = addtime;
            }
            public String getPid() {
                return pid;
            }
            public void setPid(String pid) {
                this.pid = pid;
            }
            public String getSpace_wordsid() {
                return space_wordsid;
            }
            public void setSpace_wordsid(String space_wordsid) {
                this.space_wordsid = space_wordsid;
            }
            public String getTopid() {
                return topid;
            }
            public void setTopid(String topid) {
                this.topid = topid;
            }
            public String getGetid() {
                return getid;
            }
            public void setGetid(String getid) {
                this.getid = getid;
            }
            public String getGetname() {
                return getname;
            }
            public void setGetname(String getname) {
                this.getname = getname;
            }
            public String getPutid() {
                return putid;
            }
            public void setPutid(String putid) {
                this.putid = putid;
            }
            public String getPutname() {
                return putname;
            }
            public void setPutname(String putname) {
                this.putname = putname;
            }
            public String getWords() {
                return words;
            }
            public void setWords(String words) {
                this.words = words;
            }
        }
    }
}