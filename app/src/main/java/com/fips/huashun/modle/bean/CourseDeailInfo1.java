package com.fips.huashun.modle.bean;

/**
 * Created by kevin on 2016/12/22.
 */
public class CourseDeailInfo1
{
    /**
     * data : {"Coursrdetail":{"collection":"4","courseId":"401","courseImage":"http://v1.52qmct.com/pic/J7tZ64bp2C.png","courseName":"裘马草堂简介","coursePrice":"0","courseintro":"裘马草堂课程简介","integral":"0","riokin":"4","teacherid":"0","tip":"裘马,草堂"},"buytype":"0","comments":{"context":"不错","create_date":"2017-03-31","score":"5","sturate":"0","userimg":"http://v1.52qmct.com/pic/c701bbfe-53fa-446b-b77c-f6a143beab1b.jpg","username":"娜娜"},"iscollect":"1","isriokin":"1","welltoken":"5.0"}
     * msg : 查询成功
     * status :
     * suc : y
     */

    private DataBean data;
    private String msg;
    private String status;
    private String suc;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSuc() {
        return suc;
    }

    public void setSuc(String suc) {
        this.suc = suc;
    }

    public static class DataBean {

        /**
         * Coursrdetail : {"collection":"4","courseId":"401","courseImage":"http://v1.52qmct.com/pic/J7tZ64bp2C.png","courseName":"裘马草堂简介","coursePrice":"0","courseintro":"裘马草堂课程简介","integral":"0","riokin":"4","teacherid":"0","tip":"裘马,草堂"}
         * buytype : 0
         * comments : {"context":"不错","create_date":"2017-03-31","score":"5","sturate":"0","userimg":"http://v1.52qmct.com/pic/c701bbfe-53fa-446b-b77c-f6a143beab1b.jpg","username":"娜娜"}
         * iscollect : 1
         * isriokin : 1
         * welltoken : 5.0
         */

        private CoursrdetailBean Coursrdetail;
        private String buytype;
        private CommentsBean comments;
        private String iscollect;
        private String isriokin;
        private String welltoken;

        public CoursrdetailBean getCoursrdetail() {
            return Coursrdetail;
        }

        public void setCoursrdetail(CoursrdetailBean Coursrdetail) {
            this.Coursrdetail = Coursrdetail;
        }

        public String getBuytype() {
            return buytype;
        }

        public void setBuytype(String buytype) {
            this.buytype = buytype;
        }

        public CommentsBean getComments() {
            return comments;
        }

        public void setComments(CommentsBean comments) {
            this.comments = comments;
        }

        public String getIscollect() {
            return iscollect;
        }

        public void setIscollect(String iscollect) {
            this.iscollect = iscollect;
        }

        public String getIsriokin() {
            return isriokin;
        }

        public void setIsriokin(String isriokin) {
            this.isriokin = isriokin;
        }

        public String getWelltoken() {
            return welltoken;
        }

        public void setWelltoken(String welltoken) {
            this.welltoken = welltoken;
        }

        public static class CoursrdetailBean {

            /**
             * collection : 4
             * courseId : 401
             * courseImage : http://v1.52qmct.com/pic/J7tZ64bp2C.png
             * courseName : 裘马草堂简介
             * coursePrice : 0
             * courseintro : 裘马草堂课程简介
             * integral : 0
             * riokin : 4
             * teacherid : 0
             * tip : 裘马,草堂
             */

            private String collection;
            private String courseId;
            private String courseImage;
            private String courseName;
            private String coursePrice;
            private String courseintro;
            private String integral;
            private String riokin;
            private String teacherid;
            private String tip;

            public String getCollection() {
                return collection;
            }

            public void setCollection(String collection) {
                this.collection = collection;
            }

            public String getCourseId() {
                return courseId;
            }

            public void setCourseId(String courseId) {
                this.courseId = courseId;
            }

            public String getCourseImage() {
                return courseImage;
            }

            public void setCourseImage(String courseImage) {
                this.courseImage = courseImage;
            }

            public String getCourseName() {
                return courseName;
            }

            public void setCourseName(String courseName) {
                this.courseName = courseName;
            }

            public String getCoursePrice() {
                return coursePrice;
            }

            public void setCoursePrice(String coursePrice) {
                this.coursePrice = coursePrice;
            }

            public String getCourseintro() {
                return courseintro;
            }

            public void setCourseintro(String courseintro) {
                this.courseintro = courseintro;
            }

            public String getIntegral() {
                return integral;
            }

            public void setIntegral(String integral) {
                this.integral = integral;
            }

            public String getRiokin() {
                return riokin;
            }

            public void setRiokin(String riokin) {
                this.riokin = riokin;
            }

            public String getTeacherid() {
                return teacherid;
            }

            public void setTeacherid(String teacherid) {
                this.teacherid = teacherid;
            }

            public String getTip() {
                return tip;
            }

            public void setTip(String tip) {
                this.tip = tip;
            }
        }

        public static class CommentsBean {

            /**
             * context : 不错
             * create_date : 2017-03-31
             * score : 5
             * sturate : 0
             * userimg : http://v1.52qmct.com/pic/c701bbfe-53fa-446b-b77c-f6a143beab1b.jpg
             * username : 娜娜
             */

            private String context;
            private String create_date;
            private String score;
            private String sturate;
            private String userimg;
            private String username;

            public String getContext() {
                return context;
            }

            public void setContext(String context) {
                this.context = context;
            }

            public String getCreate_date() {
                return create_date;
            }

            public void setCreate_date(String create_date) {
                this.create_date = create_date;
            }

            public String getScore() {
                return score;
            }

            public void setScore(String score) {
                this.score = score;
            }

            public String getSturate() {
                return sturate;
            }

            public void setSturate(String sturate) {
                this.sturate = sturate;
            }

            public String getUserimg() {
                return userimg;
            }

            public void setUserimg(String userimg) {
                this.userimg = userimg;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }
        }
    }
}
