package com.fips.huashun.holder.download;

/**
 * Created by kevin on 2017/2/23. 活动资料的下载转状态
 */

public interface ActDataState {
  // 未下载
  int DOWNLOAD_NOT = 1;
  // 下载完成
  int DOWNLOAD_COMPLETED = 2;
  // 下载等待
  int DOWNLOAD_WAIT = 3;
  // 下载暂停
  int DOWNLOAD_STOP = 4;
  // 下载中
  int DOWNLOADING = 5;
  // 下载过程中出现异常
  int DOWNLOAD_ERROR = -1;
  // 禁止下载
  int DOWNLOAD_BAN = 0;
}
