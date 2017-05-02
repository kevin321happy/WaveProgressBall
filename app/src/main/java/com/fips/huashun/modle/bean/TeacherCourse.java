package com.fips.huashun.modle.bean;

/**
 * Created by Administrator on 2016/8/30.
 */
public class TeacherCourse
{
    // 讲师服务内容
    private String content;
    // 讲师擅长领域
    private String field;

    private int impowerId;
    // 讲师被点赞次数
    private String riokin;

    private int sumsanum;

    private int sumviewnum;
    // 讲师主讲课程
    private String teachCourse;

    private String teacherEdu;
    // 讲师Email
    private String teacherEmail;
    // 讲师姓名
    private String teacherName;
    // 讲师手机号码
    private String teacherPhone;
    // 讲师头像
    private String teacherPhoto;
    // 讲师ID
    private int teacherId;
    // 讲师介绍
    private String introduction;
    private String collection;
    private String courses;
    private int userId;

    public String getCourses() {
        return courses;
    }

    public void setCourses(String courses) {
        this.courses = courses;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getContent()
    {
        return this.content;
    }

    public void setField(String field)
    {
        this.field = field;
    }

    public String getField()
    {
        return this.field;
    }

    public void setImpowerId(int impowerId)
    {
        this.impowerId = impowerId;
    }

    public int getImpowerId()
    {
        return this.impowerId;
    }

    public void setRiokin(String riokin)
    {
        this.riokin = riokin;
    }

    public String getRiokin()
    {
        return this.riokin;
    }

    public void setSumsanum(int sumsanum)
    {
        this.sumsanum = sumsanum;
    }

    public int getSumsanum()
    {
        return this.sumsanum;
    }

    public void setSumviewnum(int sumviewnum)
    {
        this.sumviewnum = sumviewnum;
    }

    public int getSumviewnum()
    {
        return this.sumviewnum;
    }

    public void setTeachCourse(String teachCourse)
    {
        this.teachCourse = teachCourse;
    }

    public String getTeachCourse()
    {
        return this.teachCourse;
    }

    public void setTeacherEdu(String teacherEdu)
    {
        this.teacherEdu = teacherEdu;
    }

    public String getTeacherEdu()
    {
        return this.teacherEdu;
    }

    public void setTeacherEmail(String teacherEmail)
    {
        this.teacherEmail = teacherEmail;
    }

    public String getTeacherEmail()
    {
        return this.teacherEmail;
    }

    public void setTeacherName(String teacherName)
    {
        this.teacherName = teacherName;
    }

    public String getTeacherName()
    {
        return this.teacherName;
    }

    public void setTeacherPhone(String teacherPhone)
    {
        this.teacherPhone = teacherPhone;
    }

    public String getTeacherPhone()
    {
        return this.teacherPhone;
    }

    public void setTeacherPhoto(String teacherPhoto)
    {
        this.teacherPhoto = teacherPhoto;
    }

    public String getTeacherPhoto()
    {
        return this.teacherPhoto;
    }

    public void setTeacherId(int teacherId)
    {
        this.teacherId = teacherId;
    }

    public int getTeacherId()
    {
        return this.teacherId;
    }

    public void setUserId(int userId)
    {
        this.userId = userId;
    }

    public int getUserId()
    {
        return this.userId;
    }

    public String getIntroduction()
    {
        return introduction;
    }

    public void setIntroduction(String introduction)
    {
        this.introduction = introduction;
    }


}
