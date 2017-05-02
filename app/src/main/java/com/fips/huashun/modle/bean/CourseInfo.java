package com.fips.huashun.modle.bean;

/**
 * Created by Administrator on 2016/8/30.
 * 课程
 */
public class CourseInfo {
    private int courseClickNum;

    private String courseId;

    private String courseImage;

    private String courseInfo;

    private String courseName;

    private String coursePrice;
    private String courseIntro;

    private int studyNum;

    private String teacherName;

    private String tip;

    private String typeName;
    //课程完成结果
    private String result;
    // 好评率
    private String welltoken;
    // 试卷ID
    private String paperid;

    public String getResult()
    {
        return result;
    }

    public void setResult(String result)
    {
        this.result = result;
    }

    public void setCourseClickNum(int courseClickNum){
        this.courseClickNum = courseClickNum;
    }
    public int getCourseClickNum(){
        return this.courseClickNum;
    }
    public void setCourseId(String courseId){
        this.courseId = courseId;
    }
    public String getCourseId(){
        return this.courseId;
    }
    public void setCourseImage(String courseImage){
        this.courseImage = courseImage;
    }
    public String getCourseImage(){
        return this.courseImage;
    }
    public void setCourseInfo(String courseInfo){
        this.courseInfo = courseInfo;
    }
    public String getCourseInfo(){
        return this.courseInfo;
    }
    public void setCourseName(String courseName){
        this.courseName = courseName;
    }
    public String getCourseName(){
        return this.courseName;
    }
    public void setCoursePrice(String coursePrice){
        this.coursePrice = coursePrice;
    }
    public String getCoursePrice(){
        return this.coursePrice;
    }
    public void setStudyNum(int studyNum){
        this.studyNum = studyNum;
    }
    public int getStudyNum(){
        return this.studyNum;
    }
    public void setTeacherName(String teacherName){
        this.teacherName = teacherName;
    }
    public String getTeacherName(){
        return this.teacherName;
    }
    public void setTip(String tip){
        this.tip = tip;
    }
    public String getTip(){
        return this.tip;
    }
    public void setTypeName(String typeName){
        this.typeName = typeName;
    }
    public String getTypeName(){
        return this.typeName;
    }

    public String getWelltoken()
    {
        return welltoken;
    }

    public void setWelltoken(String welltoken)
    {
        this.welltoken = welltoken;
    }

    public String getPaperid()
    {
        return paperid;
    }

    public void setPaperid(String paperid)
    {
        this.paperid = paperid;
    }

    public String getCourseIntro() {
        return courseIntro;
    }

    public void setCourseIntro(String courseIntro) {
        this.courseIntro = courseIntro;
    }
}
