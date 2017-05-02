package com.fips.huashun.modle.bean;

import com.shuyu.common.model.RecyclerBaseModel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by kevin on 2017/2/9. 留言的条目信息
 */
public class LeavaItemInfo extends RecyclerBaseModel implements Serializable {

  private String pid;
  private String filepath;
  private String uid;
  private String label;
  private String depname;
  private String addtime;
  private String words;
  private String etctimes;
  private String discuss;
  private String goods;
  private String isgoods;
  private String ismark;
  private String isdiscuss;
  private String iscryptonym;
  private String title;
  private List<FilesPathInfo> files;

  private String mark;//评分数
  private String pubname;
  private String jobname;//岗位
  private String ismanager;//是否管理员
  private String lectormark;//讲师评分
  private String notreads;
  private String isintransit;//转发
  private boolean isforward;//是否为转发

  //private FilesPathInfo ml


  public void setLabel(String label) {
    this.label = label;
  }

  public void setMark(String mark) {
    this.mark = mark;
  }


  public String getIsmanager() {
    return ismanager;
  }

  public void setIsmanager(String ismanager) {
    this.ismanager = ismanager;
  }

  public void setLectormark(String lectormark) {
    this.lectormark = lectormark;
  }

  public boolean isforward() {
    return isforward;
  }

  public void setIsforward(boolean isforward) {
    this.isforward = isforward;
  }


  public String getPid() {
    return pid;
  }

  public void setPid(String pid) {
    this.pid = pid;
  }

  public String getFilepath() {
    return filepath;
  }

  public void setFilepath(String filepath) {
    this.filepath = filepath;
  }

  public String getUid() {
    return uid;
  }

  public void setUid(String uid) {
    this.uid = uid;
  }

  public String getPubname() {
    return pubname;
  }

  public void setPubname(String pubname) {
    this.pubname = pubname;
  }

  public String getLabel() {
    return label;
  }

  public String getDepname() {
    return depname;
  }

  public void setDepname(String depname) {
    this.depname = depname;
  }

  public String getJobname() {
    return jobname;
  }

  public void setJobname(String jobname) {
    this.jobname = jobname;
  }

  public String getAddtime() {
    return addtime;
  }

  public void setAddtime(String addtime) {
    this.addtime = addtime;
  }

  public Object getLectormark() {
    return lectormark;
  }


  public String getWords() {
    return words;
  }

  public void setWords(String words) {
    this.words = words;
  }

  public String getEtctimes() {
    return etctimes;
  }

  public void setEtctimes(String etctimes) {
    this.etctimes = etctimes;
  }

  public String getDiscuss() {
    return discuss;
  }

  public void setDiscuss(String discuss) {
    this.discuss = discuss;
  }

  public String getNotreads() {
    return notreads;
  }

  public void setNotreads(String notreads) {
    this.notreads = notreads;
  }

  public String getGoods() {
    return goods;
  }

  public void setGoods(String goods) {
    this.goods = goods;
  }

  public String getIsgoods() {
    return isgoods;
  }

  public void setIsgoods(String isgoods) {
    this.isgoods = isgoods;
  }

  public String getMark() {
    return mark;
  }


  public String getIsmark() {
    return ismark;
  }

  public void setIsmark(String ismark) {
    this.ismark = ismark;
  }

  public String getIsdiscuss() {
    return isdiscuss;
  }

  public void setIsdiscuss(String isdiscuss) {
    this.isdiscuss = isdiscuss;
  }

  public String getIscryptonym() {
    return iscryptonym;
  }

  public void setIscryptonym(String iscryptonym) {
    this.iscryptonym = iscryptonym;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getIsintransit() {
    return isintransit;
  }

  public void setIsintransit(String isintransit) {
    this.isintransit = isintransit;
  }

  public List<FilesPathInfo> getFiles() {
    return files;
  }

  public void setFiles(List<FilesPathInfo> files) {
    this.files = files;
  }

}


