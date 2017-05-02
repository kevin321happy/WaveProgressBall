package com.fips.huashun.ui.utils;

import android.app.Activity;
import android.content.Context;

import java.util.Stack;

/**
 * activity堆栈式管理
 *
 * @author lgpeng
 * @date 2015年5月22日
 */
public class AppManager {

    private static Stack<Activity> activityStack;

    private static AppManager instance;

    private AppManager() {
    }

    /**
     * 单一实例
     */
    public static synchronized AppManager getAppManager() {
        if (instance == null) {
            instance = new AppManager();
        }
        return instance;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        //		Activity aa = getActivity(activity.getClass());
        //		if (aa != null)
        //		{
        //			//去除多余的实例
        //			finishActivity(aa);
        //		}
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
                //				break;
            }
        }
    }

    /**
     * 返回activity数量
     */
    public int getCount() {
        if (activityStack != null) {
            return activityStack.size();
        } else {
            return 0;
        }

    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                finishActivity(activityStack.get(i));
                break;
            }
        }
        activityStack.clear();
    }

    /**
     * 获取指定的Activity
     *
     * @author kymjs
     */
    public Activity getActivity(Class<?> cls) {
        if (activityStack != null)
            for (Activity activity : activityStack) {
                if (activity.getClass().equals(cls)) {
                    return activity;
                }
            }
        return null;
    }

    /**
     * 退出应用程序
     */
    public void AppExit(Context context) {
        try {
            finishAllActivity();
            // 杀死该应用进程
           android.os.Process.killProcess(android.os.Process.myPid());
//            System.exit(0);
        } catch (Exception e) {
        }
    }

}