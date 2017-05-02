package com.fips.huashun.widgets;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.fips.huashun.R;
import com.fips.huashun.ui.utils.DisplayUtil;
/**
 * Created by kevin on 2017/2/1.
 * 互动空间评论对话框
 */
public class CommonDialog extends Dialog implements View.OnClickListener {

    private NumberPicker mNumberPickerleft;
    private NumberPicker mNumberPickerright;
    private TextView mCacel;
    private TextView mConfirm;
    private int leftValue = 0;
    private int rightvalue = 0;
    private OnMarkClickListener mOnMarkClickListener;

    public void setOnMarkClickListener(OnMarkClickListener onMarkClickListener) {
        mOnMarkClickListener = onMarkClickListener;
    }

    //接口提供set方法
    public void setOnValueChoosedListener(OnValueChoosedListener onValueChoosedListener) {
        mOnValueChoosedListener = onValueChoosedListener;
    }

    private OnValueChoosedListener mOnValueChoosedListener;


    public CommonDialog(Context context) {
        this(context, 0);
    }

    public CommonDialog(Context context, int themeResId) {
        super(context, R.style.color_dialog);
    }

    protected CommonDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initNumberPickerListener();
    }

    //数字选择器的选择监听
    private void initNumberPickerListener() {
        //数值改变的监听
        mNumberPickerleft.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                leftValue = newVal;
                if (leftValue == 5) {
                    mNumberPickerright.setMaxValue(0);
                    mNumberPickerright.setMinValue(0);
                } else if (leftValue == 0) {
                    mNumberPickerright.setMaxValue(9);
                    mNumberPickerright.setMinValue(1);
                } else {
                    mNumberPickerright.setMaxValue(9);
                    mNumberPickerright.setMinValue(0);
                }
            }
            //Log.i("test", "老的值： " + oldVal + "新的值： " + newVal);
        });
        mNumberPickerright.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                rightvalue = newVal;
            }
        });
    }

    private void initView() {
        View contentView = View.inflate(getContext(), R.layout.teacher_grade_item, null);
        setContentView(contentView);
        mNumberPickerleft = (NumberPicker) contentView.findViewById(R.id.number_picker_left);
        mNumberPickerright = (NumberPicker) contentView.findViewById(R.id.number_picker_right);
        initNumberPickerStyle();
        mCacel = (TextView) contentView.findViewById(R.id.cancel);
        mConfirm = (TextView) contentView.findViewById(R.id.confirm);
        mCacel.setOnClickListener(this);
        mConfirm.setOnClickListener(this);
        resizeDialog();
    }

    //初始化选择器的样式
    private void initNumberPickerStyle() {
        //分割线的颜色
        mNumberPickerleft.setDividerColorResource(R.color.enterprise_text_color);
        mNumberPickerright.setDividerColorResource(R.color.enterprise_text_color);
        //  mNumberPickerleft.setDividerDistance(DisplayUtil.px2dp(getContext(),150));
        //控件字体颜色
        mNumberPickerleft.setTextColorResource(R.color.enterprise_act__defaulttext);
        mNumberPickerright.setTextColorResource(R.color.enterprise_act__defaulttext);
        //控件字体的大小
        mNumberPickerleft.setTextSize(R.dimen.space_20);
        mNumberPickerright.setTextSize(R.dimen.space_20);
    }
    private void resizeDialog() {
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = (int) (DisplayUtil.getScreenSize(getContext()).x * 0.9);
        getWindow().setAttributes(params);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel:
                dismiss();
                break;
            case R.id.confirm:
                if (mOnValueChoosedListener != null) {
                    mOnValueChoosedListener.onValueChoosed(leftValue, rightvalue);
                }
                if (mOnMarkClickListener != null) {
                    mOnMarkClickListener.onmarkChoosed(leftValue, rightvalue);
                }
                Toast.makeText(getContext(), "当前的评分为： " + leftValue + "." + rightvalue, Toast.LENGTH_SHORT).show();
                dismiss();
                break;
        }
    }

    public interface OnValueChoosedListener {
        void onValueChoosed(int leftvalue, int rightvalue);
    }

    //点击评分的接口
    public interface OnMarkClickListener {
        void onmarkChoosed(int leftvalue, int rightvalue);
    }
}
