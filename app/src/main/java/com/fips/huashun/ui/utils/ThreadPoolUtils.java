package com.fips.huashun.ui.utils;

import static android.os.AsyncTask.execute;

import com.fips.huashun.ApplicationEx;
import java.util.LinkedList;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import okhttp3.OkHttpClient;

/**
 *  Created by kevin on 2017/2/23.
 *  线程池+队列管理工具
 */
public class ThreadPoolUtils {
  private static final int POOLNUM = 3;// 线程池大小
  private volatile static int count = 0;// 工作线程数
  private static ThreadPoolExecutor THREADPOOL = (ThreadPoolExecutor) Executors.newFixedThreadPool(POOLNUM);// 线程池
  private static LinkedList<Task> TASKQUEUE = new LinkedList<>();// 等待队列

  /**
   * 执行任务
   * @param task
   * @return 是否执行，否：放入等待队列
   */
  public static boolean execute(Task task) {
    boolean isExecute = false;
    if (count < POOLNUM) {
      count++;
      isExecute = true;
      THREADPOOL.execute(task);
    } else {
      // 排队(添加任务到队列的尾部)
      TASKQUEUE.addLast(task);
    }
    return isExecute;
  }

  /**
   * 删除等待任务
   * @param id
   * @return
   */
  public static boolean cancelWaitTask(long id){
    boolean isCancel=false;

    Task target = null;
    for (Task item : TASKQUEUE) {
      if (item.id == id) {
        target = item;
        break;
      }
    }
    if (target != null) {
      TASKQUEUE.remove(target);
      isCancel=true;
    }
    return isCancel;
  }

  /**
   * 线程池中执行的任务
   */
  public static abstract class Task implements Runnable {
    // 使用id去标识任务，当存在排队等待的任务时可以依据id进行删除操作。
    public long id;

    @Override
    public void run() {
      // 耗时工作
      work();
      // 完成任务后通知主线程，线程池有一个空余线程,并试图从等待队列中获取下载任务
      ApplicationEx.getHandler().post(new Runnable() {
        @Override
        public void run() {
          count--;
          Task first = TASKQUEUE.pollFirst();//获取第一个并从等待队列中移除
          if (first != null) {
            execute(first);
          }
        }
      });
    }
    protected abstract void work();
  }
}
