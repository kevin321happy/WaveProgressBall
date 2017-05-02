package com.fips.huashun.holder.download;

import com.fips.huashun.ApplicationEx;
import com.fips.huashun.modle.dbbean.DataDownloadInfo;
import com.fips.huashun.ui.utils.ThreadPoolUtils;
import com.fips.huashun.ui.utils.ThreadUtils;


import java.util.ArrayList;
import java.util.HashMap;

/**
 * 下载过程中管理
 */
public class DownloadManager {
    public static HashMap<Long, DataDownloadInfo> DOWNLOAD_INFO_HASHMAP = new HashMap<Long, DataDownloadInfo>();

    private static DownloadManager instance = new DownloadManager();
    public static DownloadManager getInstance() {
        return instance;
    }

    // 会有多个界面一同更新，创建一个集合，将需要更新将的对象缓存起来。当界面更新时循环该集合，通知集合中元素数据更新了
    public static ArrayList<DownloadTaskObserve> OBSERVES = new ArrayList<>();
    public void removeObserve(DownloadTaskObserve observe) {
        OBSERVES.remove(observe);
    }
    public void addObserve(DownloadTaskObserve observe) {
        OBSERVES.add(observe);
    }

    public void notifyObserves(final long appId) {
        ApplicationEx.getHandler().post(new Runnable() {
            @Override
            public void run() {
                for (DownloadTaskObserve item : OBSERVES) {
                    item.update(appId);
                }
            }
        });
    }
    /**
     * 开始下载
     */
//    public void download(long id) {
//        DownloadTask task = new DownloadTask(id);
//        boolean execute = ThreadPoolUtils.execute(task);
//        if (!execute) {
//            // 等待下载
//            task.setState(ActDataState.DOWNLOAD_WAIT);
//        }
//    }

//    /**
//     * 取消下载任务
//     *
//     * @param id
//     */
//    public void deleteQueueTask(long id) {
//        boolean cancelWaitTask = ThreadPoolUtils.cancelWaitTask(id);
//        if (cancelWaitTask) {
//            DOWNLOAD_INFO_HASHMAP.get(id).state = State.DOWNLOAD_NOT;
//            notifyObserves(id);
//        }
//    }
//
//    public void setState(Long id, int state) {
//        DOWNLOAD_INFO_HASHMAP.get(id).state = state;
//    }

    /**
     * 处理点击事件
     * @param id
     */
//    public void handleClick(long id){
//        DownloadInfo downLoadInfo = DownloadManager.DOWNLOAD_INFO_HASHMAP.get(id);
//
//        if (downLoadInfo == null) {
//            return;
//        }
//        switch (downLoadInfo.state) {
//            case State.INSTALL_ALREADY:
//                // 已经安装
//                AppUtils.openApp(UIUtils.getContext(), downLoadInfo.packageName);
//                break;
//            case State.DOWNLOAD_COMPLETED:
//                // 已经下载完成,但未安装
//                AppUtils.installApp(UIUtils.getContext(), FileUtils.getApk("apk", downLoadInfo.packageName));
//                break;
//            case State.DOWNLOAD_STOP:
//                // 暂停
//            case State.DOWNLOAD_ERROR:
//                // 出错，重试
//            case State.DOWNLOAD_NOT:
//                // 未下载
//                DownloadManager.getInstance().download(downLoadInfo.appId);
//                break;
//            case State.DOWNLOAD_WAIT:
//                // 线程池已满，应用添加到等待队列，用户点击后取消下载
//                DownloadManager.getInstance().deleteQueueTask(downLoadInfo.appId);
//                break;
//            case State.DOWNLOADING:
//                // 未出现异常，显示下载进度，点击暂停
//                DownloadManager.getInstance().setState(downLoadInfo.appId, State.DOWNLOAD_STOP);
//                break;
//
//        }
//    }
}
