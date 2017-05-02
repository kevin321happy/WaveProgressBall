package com.fips.huashun.modle.bean;

import com.shuyu.common.model.RecyclerBaseModel;
import java.util.List;

/**
 * Created by kevin on 2017/2/28.
 * 邮箱：kevin321vip@126.com
 * 公司：锦绣氘(武汉科技有限公司)
 */
//pk榜数据模型
public class PkListModel  {

  /**
   * data : [{"img_path":"http://v1.52qmct.com/pic/upload/head/20_20161226035250.jpg","name":"文强","praise":"289","score":"5.0","tag_name":"火影","userid":"20"},{"img_path":"http://v1.52qmct.com/pic/upload/head/20_20161226035250.jpg","name":"文强","praise":"289","score":"5.0","tag_name":"火影","userid":"20"},{"dep_name":"部长","img_path":"http://v1.52qmct.com/pic/upload/head/22_20161205091213.jpg","name":"吴卫","praise":"139","score":"5.0","tag_name":"哎呦喂","userid":"22"},{"dep_name":"部长","img_path":"http://v1.52qmct.com/pic/upload/head/22_20161205091213.jpg","name":"吴卫","praise":"139","score":"5.0","tag_name":"哎呦喂","userid":"22"},{"img_path":"http://v1.52qmct.com/pic/upload/head/20_20161226035250.jpg","name":"文强","praise":"289","score":"5.0","tag_name":"火影","userid":"20"},{"dep_name":"部长","img_path":"http://v1.52qmct.com/pic/upload/head/22_20161205091213.jpg","name":"吴卫","praise":"139","score":"5.0","tag_name":"哎呦喂","userid":"22"},{"img_path":"http://v1.52qmct.com/pic/upload/head/20_20161226035250.jpg","name":"文强","praise":"289","score":"5.0","tag_name":"火影","userid":"20"},{"img_path":"http://v1.52qmct.com/pic/upload/head/20_20161226035250.jpg","name":"文强","praise":"289","score":"4.0","tag_name":"火影","userid":"20"},{"img_path":"http://v1.52qmct.com/pic/upload/head/20_20161226035250.jpg","name":"文强","praise":"289","score":"4.0","tag_name":"火影","userid":"20"}]
   * msg : success
   * suc : y
   */

  private String msg;
  private String suc;
  /**
   * img_path : http://v1.52qmct.com/pic/upload/head/20_20161226035250.jpg
   * name : 文强
   * praise : 289
   * score : 5.0
   * tag_name : 火影
   * userid : 20
   */

  private List<RankInfoItem> data;

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

  public List<RankInfoItem> getData() {
    return data;
  }

  public void setData(List<RankInfoItem> data) {
    this.data = data;
  }

  public static class RankInfoItem extends RecyclerBaseModel {

    private String img_path;
    private String name;
    private String praise;
    private String score;
    private String tag_name;
    private String userid;
    private String dep_name;
    private boolean islike;

    public boolean islike() {
      return islike;
    }

    public void setIslike(boolean islike) {
      this.islike = islike;
    }

    public String getDep_name() {
      return dep_name;
    }

    public void setDep_name(String dep_name) {
      this.dep_name = dep_name;
    }

    public String getImg_path() {
      return img_path;
    }

    public void setImg_path(String img_path) {
      this.img_path = img_path;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getPraise() {
      return praise;
    }

    public void setPraise(String praise) {
      this.praise = praise;
    }

    public String getScore() {
      return score;
    }

    public void setScore(String score) {
      this.score = score;
    }

    public String getTag_name() {
      return tag_name;
    }

    public void setTag_name(String tag_name) {
      this.tag_name = tag_name;
    }

    public String getUserid() {
      return userid;
    }

    public void setUserid(String userid) {
      this.userid = userid;
    }
  }
}
