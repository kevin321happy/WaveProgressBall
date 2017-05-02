package com.fips.huashun.modle.event;

/**
 * Created by kevin on 2017/3/2.
 * 邮箱：kevin321vip@126.com
 * 公司：锦绣氘(武汉科技有限公司)
 */
public class CheckPermissionEvent {
  private boolean checkperpermission;

  public CheckPermissionEvent(boolean checkperpermission) {
    this.checkperpermission = checkperpermission;
  }

  public CheckPermissionEvent() {
  }

  public boolean isCheckperpermission() {
    return checkperpermission;
  }

  public void setCheckperpermission(boolean checkperpermission) {
    this.checkperpermission = checkperpermission;
  }
}
