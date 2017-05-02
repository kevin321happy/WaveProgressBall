package com.fips.huashun.modle.bean;

/**
 * Created by Administrator on 2016/9/22.
 */
public class BeginEventType {
    private String courseId;//课程的Id
    private String sessonid;//课程的类型
    private String couseMulusName;//课程目录的名字
    private String courseImage;//课程的封面图片
    // 0 未购买 1 已购买
    private String videoUrl;//课程的播放路径

    public void setCourseImage(String courseImage) {
        this.courseImage = courseImage;
    }

    public String getCourseImage() {
        return courseImage;
    }

    public void setCouseMulusName(String couseMulusName) {
        this.couseMulusName = couseMulusName;
    }

    public String getCouseMulusName() {
        return couseMulusName;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getSessonid() {
        return sessonid;
    }

    public void setSessonid(String sessonid) {
        this.sessonid = sessonid;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public BeginEventType() {
    }
}
