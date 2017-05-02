package com.fips.huashun.modle.event;

/**
 * Created by kevin on 2017/3/6. 邮箱：kevin321vip@126.com 公司：锦绣氘(武汉科技有限公司)
 *
 */
//取消下载
public class CacleDownloadEvent {
 boolean cacleAll;

  public CacleDownloadEvent(boolean cacleAll) {
    this.cacleAll = cacleAll;
  }

  public boolean isCacleAll() {
    return cacleAll;
  }

  public void setCacleAll(boolean cacleAll) {
    this.cacleAll = cacleAll;
  }
}
