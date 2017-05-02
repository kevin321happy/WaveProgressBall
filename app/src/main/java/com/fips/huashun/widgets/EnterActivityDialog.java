package com.fips.huashun.widgets;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fips.huashun.R;
import com.fips.huashun.ui.utils.AnimationLoader;
import com.fips.huashun.ui.utils.DisplayUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by kevin on 2017/1/9.
 * 自定义参加活动的dialog
 */
public class EnterActivityDialog extends Dialog {
    @Bind(R.id.ll_top)
    LinearLayout mLlTop;
    @Bind(R.id.iv_dismiss)
    ImageView mIvDismiss;
    @Bind(R.id.iv_logo)
    ImageView mIvLogo;
    @Bind(R.id.ll_buttom_title)
    TextView mLlButtomTitle;
    @Bind(R.id.ll_buttom_content)
    TextView mLlButtomContent;
    @Bind(R.id.btnPositive)
    Button mBtnPositive;

    private Animation mInanimation;
    private Animation mOutanimation;
    private View mDialogView;
    private OnPositiveListener mOnPositiveListener;
    private boolean isShowAnimation;
    private CharSequence mTitleText, mContentText, mPositiveText;


    public EnterActivityDialog(Context context) {
        this(context, 0);
    }

    public EnterActivityDialog(Context context, int themeResId) {
        super(context, R.style.color_dialog);
        init();
    }

    //初始化进出动画
    private void init() {
        mInanimation = AnimationLoader.getInAnimation(getContext());
        mOutanimation = AnimationLoader.getOutAnimation(getContext());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initListener();
    }

    //初始化界面
    private void initView() {
        View contentView = View.inflate(getContext(), R.layout.enter_activity_dialog, null);
        setContentView(contentView);
        ButterKnife.bind(this);
        resizeDialog();//调整对话框位置
        mDialogView = getWindow().getDecorView().findViewById(android.R.id.content);
        //设置数据
        mLlButtomTitle.setText(mTitleText);
        mLlButtomContent.setText(mContentText);
        mBtnPositive.setText(mPositiveText);
    }

    //初始化确定/取消按钮的监听
    private void initListener() {
        mBtnPositive.setOnClickListener(mPositiveListener);
        mIvDismiss.setOnClickListener(mDismissListener);
        initAnimListener();
    }

    private void callDismiss() {
        super.dismiss();
    }

    //初始化动画的监听
    private void initAnimListener() {
        mOutanimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                mDialogView.post(new Runnable() {
                    @Override
                    public void run() {
                        callDismiss();
                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    //当点击了确定
    private View.OnClickListener mPositiveListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mOnPositiveListener != null) {
                mOnPositiveListener.onClick(EnterActivityDialog.this);
                dismiss();
            }
        }
    };
    //点击取消
    private View.OnClickListener mDismissListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(getContext(), "chacha", Toast.LENGTH_SHORT).show();
            dismiss();
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        startWithAnimation(true);
    }

    @Override
    public void dismiss() {
        outWithAnimation(true);
    }

    //进场动画
    private void startWithAnimation(boolean isShowAnimation) {
        if (isShowAnimation) {
            mDialogView.startAnimation(mInanimation);
        }
    }

    //出场动画
    private void outWithAnimation(boolean isShowAnimation) {
        if (isShowAnimation) {
            mDialogView.startAnimation(mOutanimation);
        } else {
            super.dismiss();
        }
    }

    //调整对话框的位置
    private void resizeDialog() {
        Window window = getWindow();
        window.setGravity(Gravity.CENTER_VERTICAL);
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = (int) (DisplayUtil.getScreenSize(getContext()).x * 0.8);
        attributes.height = (int) (DisplayUtil.getScreenSize(getContext()).y * 0.4);
        getWindow().setAttributes(attributes);
    }

    private Runnable mDismissRunnable = new Runnable() {
        @Override
        public void run() {
            dismiss();
        }
    };

    //设置标题/内容
    public void setDialogTitle(String title) {
        mLlButtomTitle.setText("" + title);

    }

    public void setDialogContent(String content) {
        mLlButtomContent.setText(content);

    }

    //设置进出的动画
    public EnterActivityDialog setAnimationIn(AnimationSet animIn) {
        mInanimation = animIn;
        return this;
    }

    public EnterActivityDialog setAnimationOut(AnimationSet animOut) {
        mOutanimation = animOut;
        initAnimListener();
        return this;
    }

    //重写父类的方法
    @Override
    public void setTitle(CharSequence title) {
        mTitleText = title;
    }

    @Override
    public void setTitle(int titleId) {
        setTitle(getContext().getText(titleId));
    }

    public EnterActivityDialog setContentText(CharSequence text) {
        mContentText = text;
        return this;
    }

    public EnterActivityDialog setContentText(int textId) {
        return setContentText(getContext().getText(textId));
    }

    public EnterActivityDialog setPositiveListener(CharSequence text, OnPositiveListener l) {
        mPositiveText = text;
        mOnPositiveListener = l;
        return this;
    }

    public EnterActivityDialog setPositiveListener(int textId, OnPositiveListener l) {
        return setPositiveListener(getContext().getText(textId), l);
    }

    public CharSequence getContentText() {
        return mContentText;
    }

    public CharSequence getTitleText() {
        return mTitleText;
    }

    public CharSequence getPositiveText() {
        return mPositiveText;
    }

    public interface OnPositiveListener {
        void onClick(EnterActivityDialog dialog);
    }
}
