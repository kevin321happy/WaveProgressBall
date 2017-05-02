package com.fips.huashun.modle.bean;

import com.shuyu.common.model.RecyclerBaseModel;

import java.util.List;

/**
 * Created by kevin on 2017/1/5.
 */
public class TeacherInfo extends RecyclerBaseModel{

    /**
     * teacherList : [{"content":"NLP教练辅导、企业劳资咨询、创业团队精益辅导","courses":"《NLP行动教练》、《企业体验式培训》","field":"商业服务、房地产|建筑业","impowerId":0,"riokin":"17","sumsanum":0,"sumviewnum":0,"teachCourse":"被毁掉的工作观","teacherEdu":"","teacherEmail":"","teacherId":1,"teacherName":"宁文娟","teacherPhone":"","teacherPhoto":"/Public/Upload/2016-12-06/201612061111413.png","userId":0},{"content":"企业猎头招聘、高阶沟通课程授课","courses":"《心学讲堂》、《企业高尔夫管理》","field":"商业服务、房地产|建筑业","impowerId":0,"riokin":"5","sumsanum":0,"sumviewnum":0,"teachCourse":"","teacherEdu":"","teacherEmail":"","teacherId":2,"teacherName":"JAMES","teacherPhone":"","teacherPhoto":"/Public/Upload/2016-12-06/201612061111412.png","userId":0},{"content":"服务行业一线培训、结构化思维教练","courses":"《人力资源法律》、《劳动法实务》","field":"商业服务、房地产|建筑业","impowerId":0,"riokin":"8","sumsanum":0,"sumviewnum":0,"teachCourse":"","teacherEdu":"","teacherEmail":"","teacherId":3,"teacherName":"吴良涛","teacherPhone":"","teacherPhoto":"/Public/Upload/2016-12-06/201612061111417.png","userId":0},{"content":"投资环境尽职调查、投资法律风险评估报告、企业内部合同及劳动法律培训、高级员工资信调查、取证，电子合同文本审查等。","courses":"《九型与自我认识、超越、实现》、《九型与沟通》、《九型与职业规划》、《九型与健康》","field":"商业服务、房地产|建筑业","impowerId":0,"riokin":"7","sumsanum":0,"sumviewnum":0,"teachCourse":"","teacherEdu":"","teacherEmail":"","teacherId":4,"teacherName":"汪发军","teacherPhone":"","teacherPhoto":"/Public/Upload/2016-12-06/201612061111416.jpg","userId":0},{"content":"零售/服务连锁业门店营运；卖场布局与陈列；商业库存与物流；加盟与连锁的初步建设；职业素质及销售管理；人力资源等","courses":"《连锁店运营》、《加盟管理》、《卖场陈列与布局》等","field":"商业服务、房地产|建筑业","impowerId":0,"riokin":"5","sumsanum":0,"sumviewnum":0,"teachCourse":"","teacherEdu":"","teacherEmail":"","teacherId":5,"teacherName":"万湘疆","teacherPhone":"","teacherPhoto":"/Public/Upload/2016-12-06/201612061111415.png","userId":0},{"content":"核心课程：生命动力一二三阶段、CP教练技术理论课程、九型人格、MCC管理教练","courses":"CP、九型微讲堂","field":"商业服务、教育培训","impowerId":0,"riokin":"4","sumsanum":0,"sumviewnum":0,"teachCourse":"","teacherEdu":"","teacherEmail":"","teacherId":41,"teacherName":"星海","teacherPhone":"","teacherPhoto":"/Public/Upload/2016-12-19/58579cecbccc4.png","userId":0}]
     * suc : y
     * msg : 获取数据成功
     */

    private String suc;
    private String msg;
    /**
     * content : NLP教练辅导、企业劳资咨询、创业团队精益辅导
     * courses : 《NLP行动教练》、《企业体验式培训》
     * field : 商业服务、房地产|建筑业
     * impowerId : 0
     * riokin : 17
     * sumsanum : 0
     * sumviewnum : 0
     * teachCourse : 被毁掉的工作观
     * teacherEdu :
     * teacherEmail :
     * teacherId : 1
     * teacherName : 宁文娟
     * teacherPhone :
     * teacherPhoto : /Public/Upload/2016-12-06/201612061111413.png
     * userId : 0
     */

    private List<TeacherListBean> teacherList ;

    public String getSuc() {
        return suc;
    }

    public void setSuc(String suc) {
        this.suc = suc;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<TeacherListBean> getTeacherList() {
        return teacherList;
    }

    public void setTeacherList(List<TeacherListBean> teacherList) {
        this.teacherList = teacherList;
    }

    public static class TeacherListBean extends RecyclerBaseModel{
        private String content;
        private String courses;
        private String field;
        private int impowerId;
        private String riokin;
        private int sumsanum;
        private int sumviewnum;
        private String teachCourse;
        private String teacherEdu;
        private String teacherEmail;
        private int teacherId;
        private String teacherName;
        private String teacherPhone;
        private String teacherPhoto;
        private int userId;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCourses() {
            return courses;
        }

        public void setCourses(String courses) {
            this.courses = courses;
        }

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public int getImpowerId() {
            return impowerId;
        }

        public void setImpowerId(int impowerId) {
            this.impowerId = impowerId;
        }

        public String getRiokin() {
            return riokin;
        }

        public void setRiokin(String riokin) {
            this.riokin = riokin;
        }

        public int getSumsanum() {
            return sumsanum;
        }

        public void setSumsanum(int sumsanum) {
            this.sumsanum = sumsanum;
        }

        public int getSumviewnum() {
            return sumviewnum;
        }

        public void setSumviewnum(int sumviewnum) {
            this.sumviewnum = sumviewnum;
        }

        public String getTeachCourse() {
            return teachCourse;
        }

        public void setTeachCourse(String teachCourse) {
            this.teachCourse = teachCourse;
        }

        public String getTeacherEdu() {
            return teacherEdu;
        }

        public void setTeacherEdu(String teacherEdu) {
            this.teacherEdu = teacherEdu;
        }

        public String getTeacherEmail() {
            return teacherEmail;
        }

        public void setTeacherEmail(String teacherEmail) {
            this.teacherEmail = teacherEmail;
        }

        public int getTeacherId() {
            return teacherId;
        }

        public void setTeacherId(int teacherId) {
            this.teacherId = teacherId;
        }

        public String getTeacherName() {
            return teacherName;
        }

        public void setTeacherName(String teacherName) {
            this.teacherName = teacherName;
        }

        public String getTeacherPhone() {
            return teacherPhone;
        }

        public void setTeacherPhone(String teacherPhone) {
            this.teacherPhone = teacherPhone;
        }

        public String getTeacherPhoto() {
            return teacherPhoto;
        }

        public void setTeacherPhoto(String teacherPhoto) {
            this.teacherPhoto = teacherPhoto;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }
    }
}
