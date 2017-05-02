package com.fips.huashun.modle.bean;

/**
 * Created by Administrator on 2016/9/16.
 */
public class CouseMuluInfo {

  /* "eid": "-1",
       "fileSize": "0",*/
  private String eid;
  private String fileSize;
  private String addtime;

  private String collection;

  private String courseId;

  private String courseImage;

  private String courseInfo;

  private String courseName;

  private String coursePrice;

  private String coursedoc;

  private String courseintro;

  private String coursevideo;

  private String docUrl;

  private String imageUrl;

  private String integral;

  private boolean pricetype;

  private String riokin;

  private String sectionname;

  private String sessionfile;

  private String sessioninfo;

  private String sessiontime;
  // 1 文档 2 视频
  private String sessiontype;
  // 是否已经学习
  private String sessonid;

  private String teacherid;

  private String tip;

  private String updatetime;

  private String videoUrl;

  private String viewnum;

  private boolean isclik = false;
  //超清
  private String superquality;
  //高清
  private String highquality;
  //标清
  private String stanquality;
  //普通
  private String commquality;
  //流畅
  private String easyquality;
  //缩略图
  private String thumbnail;
  //视频文件名(阿里云)
  private String filenamemark;
  //流媒体id(阿里云)
  private String mediaid;

  public boolean isclik() {
    return isclik;
  }

  public String getEid() {
    return eid;
  }

  public void setEid(String eid) {
    this.eid = eid;
  }

  public String getFileSize() {
    return fileSize;
  }

  public void setFileSize(String fileSize) {
    this.fileSize = fileSize;
  }

  public void setIsclik(boolean isclik) {
    this.isclik = isclik;
  }

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

  public String getCourseImage() {
    return courseImage;
  }

  public void setCourseImage(String courseImage) {
    this.courseImage = courseImage;
  }

  public String getCoursePrice() {
    return coursePrice;
  }

  public void setCoursePrice(String coursePrice) {
    this.coursePrice = coursePrice;
  }

  public String getDocUrl() {
    return docUrl;
  }

  public void setDocUrl(String docUrl) {
    this.docUrl = docUrl;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public boolean isPricetype() {
    return pricetype;
  }

  public void setPricetype(boolean pricetype) {
    this.pricetype = pricetype;
  }

  public String getCourseId() {
    return courseId;
  }

  public void setCourseId(String courseId) {
    this.courseId = courseId;
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

  public String getSuperquality() {
    return superquality;
  }

  public void setSuperquality(String superquality) {
    this.superquality = superquality;
  }

  public String getHighquality() {
    return highquality;
  }

  public void setHighquality(String highquality) {
    this.highquality = highquality;
  }

  public String getStanquality() {
    return stanquality;
  }

  public void setStanquality(String stanquality) {
    this.stanquality = stanquality;
  }

  public String getCommquality() {
    return commquality;
  }

  public void setCommquality(String commquality) {
    this.commquality = commquality;
  }

  public String getEasyquality() {
    return easyquality;
  }

  public void setEasyquality(String easyquality) {
    this.easyquality = easyquality;
  }

  public String getThumbnail() {
    return thumbnail;
  }

  public void setThumbnail(String thumbnail) {
    this.thumbnail = thumbnail;
  }

  public String getFilenamemark() {
    return filenamemark;
  }

  public void setFilenamemark(String filenamemark) {
    this.filenamemark = filenamemark;
  }

  public String getMediaid() {
    return mediaid;
  }

  public void setMediaid(String mediaid) {
    this.mediaid = mediaid;
  }

  @Override
  public String toString() {
    return "CouseMuluInfo{" +
        "eid='" + eid + '\'' +
        ", fileSize='" + fileSize + '\'' +
        ", addtime='" + addtime + '\'' +
        ", collection='" + collection + '\'' +
        ", courseId='" + courseId + '\'' +
        ", courseImage='" + courseImage + '\'' +
        ", courseInfo='" + courseInfo + '\'' +
        ", courseName='" + courseName + '\'' +
        ", coursePrice='" + coursePrice + '\'' +
        ", coursedoc='" + coursedoc + '\'' +
        ", courseintro='" + courseintro + '\'' +
        ", coursevideo='" + coursevideo + '\'' +
        ", docUrl='" + docUrl + '\'' +
        ", imageUrl='" + imageUrl + '\'' +
        ", integral='" + integral + '\'' +
        ", pricetype=" + pricetype +
        ", riokin='" + riokin + '\'' +
        ", sectionname='" + sectionname + '\'' +
        ", sessionfile='" + sessionfile + '\'' +
        ", sessioninfo='" + sessioninfo + '\'' +
        ", sessiontime='" + sessiontime + '\'' +
        ", sessiontype='" + sessiontype + '\'' +
        ", sessonid='" + sessonid + '\'' +
        ", teacherid='" + teacherid + '\'' +
        ", tip='" + tip + '\'' +
        ", updatetime='" + updatetime + '\'' +
        ", videoUrl='" + videoUrl + '\'' +
        ", viewnum='" + viewnum + '\'' +
        ", isclik=" + isclik +
        ", superquality='" + superquality + '\'' +
        ", highquality='" + highquality + '\'' +
        ", stanquality='" + stanquality + '\'' +
        ", commquality='" + commquality + '\'' +
        ", easyquality='" + easyquality + '\'' +
        ", thumbnail='" + thumbnail + '\'' +
        ", filenamemark='" + filenamemark + '\'' +
        ", mediaid='" + mediaid + '\'' +
        '}';
  }
}
