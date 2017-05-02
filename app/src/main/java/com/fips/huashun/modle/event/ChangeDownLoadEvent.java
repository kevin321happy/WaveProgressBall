package com.fips.huashun.modle.event;

/** 
 * description: 改变下载任务
 * autour: Kevin
 * company:锦绣氘(武汉)科技有限公司
 * date: 2017/4/29 10:06 
 * update: 2017/4/29
 * version: 1.21
 * 站在峰顶 看世界
 * 落在谷底 思人生
*/
public class ChangeDownLoadEvent {

  private String pid; //下载任务标识
  private String localPath;//本地文件的路径
  private int state;//下载的状态

  public ChangeDownLoadEvent(String pid) {
    this.pid = pid;
  }

  public ChangeDownLoadEvent() {
  }

  public String getPid() {
    return pid;
  }

  public void setPid(String pid) {
    this.pid = pid;
  }

  public String getLocalPath() {
    return localPath;
  }

  public void setLocalPath(String localPath) {
    this.localPath = localPath;
  }

  public int getState() {
    return state;
  }

  public void setState(int state) {
    this.state = state;
  }
}
