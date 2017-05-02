package com.fips.huashun.ui.fragment;


import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fips.huashun.ApplicationEx;
import com.fips.huashun.R;
import com.fips.huashun.ui.utils.SystemStatusManager;
import com.fips.huashun.widgets.LoadingDialog;



/**
 * Created by lgpeng on 2016/2/17 0017.
 */
public abstract class BaseFragment extends Fragment {

    private LoadingDialog loadingDialog;
    public View MainContentView;
    /**
     * 保存上一次点击时间
     */
    private SparseArray<Long> lastClickTimes;

    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        loadingDialog = new LoadingDialog(getActivity());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new SystemStatusManager(getActivity()).setTranslucentStatus(R.color.transparent);
        lastClickTimes = new SparseArray<Long>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        MainContentView = getContentView();
        return MainContentView;
    }



    public abstract View getContentView();

    @Override
    public void onDestroy() {
        super.onDestroy();
        lastClickTimes = null;
    }

    /**
     * 检查是否可执行点击操作
     * 防重复点击
     *
     * @return 返回true则可执行
     */
    protected boolean checkClick(int id) {
        Long lastTime = lastClickTimes.get(id);
        Long thisTime = System.currentTimeMillis();
        lastClickTimes.put(id, thisTime);
        return !(lastTime != null && thisTime - lastTime < 800);
    }

    public void showLoadingDialog() {
        loadingDialog.show(getString(R.string.loading));
    }

    public void showLoadingDialog(String content) {
        loadingDialog.show(content);
    }

    public void dimissLoadingDialog() {
        loadingDialog.dismiss();
    }

    public boolean checkSdkVersion(){
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }
    public int getColor(int col) {
        return ApplicationEx.getInstance().getResources().getColor(col);
    }

}
