package com.fips.huashun.modle.bean;

import java.util.List;

/**
 * Created by kevin on 2016/12/22.
 */
public class CouseMuluInfo1{
    /**
     * buytype : 1
     * data : [{"addtime":"","collection":"","commquality":"","courseId":"","courseImage":"","courseInfo":"","courseName":"","coursePrice":"","coursedoc":"","courseintro":"","coursevideo":"","docUrl":"","easyquality":"","filenamemark":"","highquality":"","imageUrl":"","integral":"","mediaid":"","pricetype":"","riokin":"","sectionname":"公司失败的两个原因要如何避免","sessionfile":"","sessioninfo":"","sessiontime":"","sessiontype":"2","sessonid":"329","stanquality":"http://v1.52qmct.com/test/2016120613211510.mp4","superquality":"","teacherid":"","thumbnail":"","tip":"","updatetime":"","videoUrl":"99719057","viewnum":""}]
     */

    private DataBean data;
    /**
     * data : {"buytype":"1","data":[{"addtime":"","collection":"","commquality":"","courseId":"","courseImage":"","courseInfo":"","courseName":"","coursePrice":"","coursedoc":"","courseintro":"","coursevideo":"","docUrl":"","easyquality":"","filenamemark":"","highquality":"","imageUrl":"","integral":"","mediaid":"","pricetype":"","riokin":"","sectionname":"公司失败的两个原因要如何避免","sessionfile":"","sessioninfo":"","sessiontime":"","sessiontype":"2","sessonid":"329","stanquality":"http://v1.52qmct.com/test/2016120613211510.mp4","superquality":"","teacherid":"","thumbnail":"","tip":"","updatetime":"","videoUrl":"99719057","viewnum":""}]}
     * msg : 获取数据成功
     * suc : y
     */
    private String msg;
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

    public String getSuc() {
        return suc;
    }

    public void setSuc(String suc) {
        this.suc = suc;
    }

    public static class DataBean {
        private String buytype;
        /**
         * addtime :
         * collection :
         * commquality :
         * courseId :
         * courseImage :
         * courseInfo :
         * courseName :
         * coursePrice :
         * coursedoc :
         * courseintro :
         * coursevideo :
         * docUrl :
         * easyquality :
         * filenamemark :
         * highquality :
         * imageUrl :
         * integral :
         * mediaid :
         * pricetype :
         * riokin :
         * sectionname : 公司失败的两个原因要如何避免
         * sessionfile :
         * sessioninfo :
         * sessiontime :
         * sessiontype : 2
         * sessonid : 329
         * stanquality : http://v1.52qmct.com/test/2016120613211510.mp4
         * superquality :
         * teacherid :
         * thumbnail :
         * tip :
         * updatetime :
         * videoUrl : 99719057
         * viewnum :
         */

        private List<MuluBean> data;

        public String getBuytype() {
            return buytype;
        }

        public void setBuytype(String buytype) {
            this.buytype = buytype;
        }

        public List<MuluBean> getData() {
            return data;
        }

        public void setData(List<MuluBean> data) {
            this.data = data;
        }

        public static class MuluBean {
            private String addtime;
            private String collection;
            private String commquality;
            private String courseId;
            private String courseImage;
            private String courseInfo;
            private String courseName;
            private String coursePrice;
            private String coursedoc;
            private String courseintro;
            private String coursevideo;
            private String docUrl;
            private String easyquality;
            private String filenamemark;
            private String highquality;
            private String imageUrl;
            private String integral;
            private String mediaid;
            private String pricetype;
            private String riokin;
            private String sectionname;
            private String sessionfile;
            private String sessioninfo;
            private String sessiontime;
            private String sessiontype;
            private String sessonid;
            private String stanquality;
            private String superquality;
            private String teacherid;
            private String thumbnail;
            private String tip;
            private String updatetime;
            private String videoUrl;
            private String viewnum;

            public String getAddtime() {
                return addtime;
            }

            public void setAddtime(String addtime) {
                this.addtime = addtime;
            }

            public String getCollection() {
                return collection;
            }

            public void setCollection(String collection) {
                this.collection = collection;
            }

            public String getCommquality() {
                return commquality;
            }

            public void setCommquality(String commquality) {
                this.commquality = commquality;
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

            public String getCourseInfo() {
                return courseInfo;
            }

            public void setCourseInfo(String courseInfo) {
                this.courseInfo = courseInfo;
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

            public String getCoursedoc() {
                return coursedoc;
            }

            public void setCoursedoc(String coursedoc) {
                this.coursedoc = coursedoc;
            }

            public String getCourseintro() {
                return courseintro;
            }

            public void setCourseintro(String courseintro) {
                this.courseintro = courseintro;
            }

            public String getCoursevideo() {
                return coursevideo;
            }

            public void setCoursevideo(String coursevideo) {
                this.coursevideo = coursevideo;
            }

            public String getDocUrl() {
                return docUrl;
            }

            public void setDocUrl(String docUrl) {
                this.docUrl = docUrl;
            }

            public String getEasyquality() {
                return easyquality;
            }

            public void setEasyquality(String easyquality) {
                this.easyquality = easyquality;
            }

            public String getFilenamemark() {
                return filenamemark;
            }

            public void setFilenamemark(String filenamemark) {
                this.filenamemark = filenamemark;
            }

            public String getHighquality() {
                return highquality;
            }

            public void setHighquality(String highquality) {
                this.highquality = highquality;
            }

            public String getImageUrl() {
                return imageUrl;
            }

            public void setImageUrl(String imageUrl) {
                this.imageUrl = imageUrl;
            }

            public String getIntegral() {
                return integral;
            }

            public void setIntegral(String integral) {
                this.integral = integral;
            }

            public String getMediaid() {
                return mediaid;
            }

            public void setMediaid(String mediaid) {
                this.mediaid = mediaid;
            }

            public String getPricetype() {
                return pricetype;
            }

            public void setPricetype(String pricetype) {
                this.pricetype = pricetype;
            }

            public String getRiokin() {
                return riokin;
            }

            public void setRiokin(String riokin) {
                this.riokin = riokin;
            }

            public String getSectionname() {
                return sectionname;
            }

            public void setSectionname(String sectionname) {
                this.sectionname = sectionname;
            }

            public String getSessionfile() {
                return sessionfile;
            }

            public void setSessionfile(String sessionfile) {
                this.sessionfile = sessionfile;
            }

            public String getSessioninfo() {
                return sessioninfo;
            }

            public void setSessioninfo(String sessioninfo) {
                this.sessioninfo = sessioninfo;
            }

            public String getSessiontime() {
                return sessiontime;
            }

            public void setSessiontime(String sessiontime) {
                this.sessiontime = sessiontime;
            }

            public String getSessiontype() {
                return sessiontype;
            }

            public void setSessiontype(String sessiontype) {
                this.sessiontype = sessiontype;
            }

            public String getSessonid() {
                return sessonid;
            }

            public void setSessonid(String sessonid) {
                this.sessonid = sessonid;
            }

            public String getStanquality() {
                return stanquality;
            }

            public void setStanquality(String stanquality) {
                this.stanquality = stanquality;
            }

            public String getSuperquality() {
                return superquality;
            }

            public void setSuperquality(String superquality) {
                this.superquality = superquality;
            }

            public String getTeacherid() {
                return teacherid;
            }

            public void setTeacherid(String teacherid) {
                this.teacherid = teacherid;
            }

            public String getThumbnail() {
                return thumbnail;
            }

            public void setThumbnail(String thumbnail) {
                this.thumbnail = thumbnail;
            }

            public String getTip() {
                return tip;
            }

            public void setTip(String tip) {
                this.tip = tip;
            }

            public String getUpdatetime() {
                return updatetime;
            }

            public void setUpdatetime(String updatetime) {
                this.updatetime = updatetime;
            }

            public String getVideoUrl() {
                return videoUrl;
            }

            public void setVideoUrl(String videoUrl) {
                this.videoUrl = videoUrl;
            }

            public String getViewnum() {
                return viewnum;
            }

            public void setViewnum(String viewnum) {
                this.viewnum = viewnum;
            }
        }
    }
}
