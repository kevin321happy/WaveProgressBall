package com.fips.huashun.modle.bean;

import com.shuyu.common.model.RecyclerBaseModel;
import java.util.List;

/**
 * Created by kevin on 2017/3/1.
 * 邮箱：kevin321vip@126.com
 * 公司：锦绣氘(武汉)科技有限公司
 */
//活动调研列表的model
public class ActivityResearchModel {
  /**
   * row : [{"enid":"8","title":"即可警太阳花虎头燕颔","type":"question","start_time":null,"end_time":null,"description":"对方风格化法国","show_result":"1","notice":"1","notice_detail":null,"dep":null,"is_pull_staff":"0","addtime":"2017-02-25 11:26:22","status":"2","pid":"408","is_anwser":0}]
   * suc : y
   */

  private String suc;
  /**
   * enid : 8
   * title : 即可警太阳花虎头燕颔
   * type : question
   * start_time : null
   * end_time : null
   * description : 对方风格化法国
   * show_result : 1
   * notice : 1
   * notice_detail : null
   * dep : null
   * is_pull_staff : 0
   * addtime : 2017-02-25 11:26:22
   * status : 2
   * pid : 408
   * is_anwser : 0
   */

  private List<ResearchInfo> row;

  public String getSuc() {
    return suc;
  }

  public void setSuc(String suc) {
    this.suc = suc;
  }

  public List<ResearchInfo> getRow() {
    return row;
  }

  public void setRow(List<ResearchInfo> row) {
    this.row = row;
  }

  public static class ResearchInfo  extends RecyclerBaseModel{

    private String enid;
    private String title;
    private String type;
    private Object start_time;
    private Object end_time;
    private String description;
    private String show_result;
    private String notice;
    private Object notice_detail;
    private Object dep;
    private String is_pull_staff;
    private String addtime;
    private String status;
    private String pid;
    private String is_anwser;

    public String getEnid() {
      return enid;
    }

    public void setEnid(String enid) {
      this.enid = enid;
    }

    public String getTitle() {
      return title;
    }

    public void setTitle(String title) {
      this.title = title;
    }

    public String getType() {
      return type;
    }

    public void setType(String type) {
      this.type = type;
    }

    public Object getStart_time() {
      return start_time;
    }

    public void setStart_time(Object start_time) {
      this.start_time = start_time;
    }

    public Object getEnd_time() {
      return end_time;
    }

    public void setEnd_time(Object end_time) {
      this.end_time = end_time;
    }

    public String getDescription() {
      return description;
    }

    public void setDescription(String description) {
      this.description = description;
    }

    public String getShow_result() {
      return show_result;
    }

    public void setShow_result(String show_result) {
      this.show_result = show_result;
    }

    public String getNotice() {
      return notice;
    }

    public void setNotice(String notice) {
      this.notice = notice;
    }

    public Object getNotice_detail() {
      return notice_detail;
    }

    public void setNotice_detail(Object notice_detail) {
      this.notice_detail = notice_detail;
    }

    public Object getDep() {
      return dep;
    }

    public void setDep(Object dep) {
      this.dep = dep;
    }

    public String getIs_pull_staff() {
      return is_pull_staff;
    }

    public void setIs_pull_staff(String is_pull_staff) {
      this.is_pull_staff = is_pull_staff;
    }

    public String getAddtime() {
      return addtime;
    }

    public void setAddtime(String addtime) {
      this.addtime = addtime;
    }

    public String getStatus() {
      return status;
    }

    public void setStatus(String status) {
      this.status = status;
    }

    public String getPid() {
      return pid;
    }

    public void setPid(String pid) {
      this.pid = pid;
    }

    public String getIs_anwser() {
      return is_anwser;
    }

    public void setIs_anwser(String is_anwser) {
      this.is_anwser = is_anwser;
    }
  }
}
