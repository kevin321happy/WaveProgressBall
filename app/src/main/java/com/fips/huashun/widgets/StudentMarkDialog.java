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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fips.huashun.R;
import com.fips.huashun.ui.utils.AnimationLoader;
import com.fips.huashun.ui.utils.DisplayUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by kevin on 2017/2/16.
 * 学生评论的dialog
 */

public class StudentMarkDialog extends Dialog {
    @Bind(R.id.ll_top)
    LinearLayout mLlTop;
    @Bind(R.id.iv_dismiss)
    ImageView iv_dismiss;
    @Bind(R.id.iv_logo)
    ImageView mIvLogo;
    @Bind(R.id.iv_3)
    ImageView mIv3;
    @Bind(R.id.iv_2)
    ImageView mIv2;
    @Bind(R.id.iv_4)
    ImageView mIv4;
    @Bind(R.id.iv_5)
    ImageView mIv5;
    @Bind(R.id.iv_1)
    ImageView mIv1;
    @Bind(R.id.rl)
    RelativeLayout mRl;
    @Bind(R.id.ll_buttom_title)
    TextView mLlButtomTitle;
    @Bind(R.id.ll_buttom_content)
    TextView mLlButtomContent;
    @Bind(R.id.btnPositive)
    Button mBtnPositive;
    private Animation mInanimation;
    private Animation mOutanimation;
    private View mDialogView;
    private boolean isShowAnimation;
    private OnConfirmListener mOnConfirmListener;
    private int mark=0;
    private Context mContext;

    public void setOnConfirmListener(OnConfirmListener onConfirmListener) {
        mOnConfirmListener = onConfirmListener;
    }

    public StudentMarkDialog(Context context) {
        this(context, 0);
        this.mContext=context;
    }

    public StudentMarkDialog(Context context, int themeResId) {
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
        View contentView = View.inflate(getContext(), R.layout.student_mark_dialog, null);
        setContentView(contentView);
        ButterKnife.bind(this);
        resizeDialog();//调整对话框位置
        mDialogView = getWindow().getDecorView().findViewById(android.R.id.content);
    }

    //初始化确定/取消按钮的监听
    private void initListener() {
        initAnimListener();
       // mBtnPositive.setOnClickListener(mPositiveListener);
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

//    //当点击了确定
//    private View.OnClickListener mPositiveListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            if (mOnConfirmListener != null) {
//                mOnConfirmListener.onClick(mark);
//                dismiss();
//            }
//        }
//    };
    //点击取消
//    private View.OnClickListener mDismissListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            Toast.makeText(getContext(), "chacha", Toast.LENGTH_SHORT).show();
//            dismiss();
//        }
//    };

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


    //设置进出的动画
    public StudentMarkDialog setAnimationIn(AnimationSet animIn) {
        mInanimation = animIn;
        return this;
    }

    public StudentMarkDialog setAnimationOut(AnimationSet animOut) {
        mOutanimation = animOut;
        initAnimListener();
        return this;
    }

    @OnClick({R.id.iv_3, R.id.iv_2, R.id.iv_4, R.id.iv_5, R.id.iv_1, R.id.btnPositive,R.id.iv_dismiss})
    public void onClick(View view) {
        if(mOnConfirmListener==null){
            return;
        }
        switch (view.getId()) {
            case R.id.iv_3:
                mIv1.setImageResource(R.drawable.star_d);
                mIv2.setImageResource(R.drawable.star_d);
                mIv3.setImageResource(R.drawable.star_d);
                mIv4.setImageResource(R.drawable.star_e);
                mIv5.setImageResource(R.drawable.star_e);
                mark=3;
                break;
            case R.id.iv_2:
                mIv1.setImageResource(R.drawable.star_d);
                mIv2.setImageResource(R.drawable.star_d);
                mIv3.setImageResource(R.drawable.star_e);
                mIv4.setImageResource(R.drawable.star_e);
                mIv5.setImageResource(R.drawable.star_e);
                mark=2;
                break;
            case R.id.iv_4:
                mIv1.setImageResource(R.drawable.star_d);
                mIv2.setImageResource(R.drawable.star_d);
                mIv3.setImageResource(R.drawable.star_d);
                mIv4.setImageResource(R.drawable.star_d);
                mIv5.setImageResource(R.drawable.star_e);
                mark=4;
                break;
            case R.id.iv_5:
                mIv1.setImageResource(R.drawable.star_d);
                mIv2.setImageResource(R.drawable.star_d);
                mIv3.setImageResource(R.drawable.star_d);
                mIv4.setImageResource(R.drawable.star_d);
                mIv5.setImageResource(R.drawable.star_d);
                mark=5;
                break;
            case R.id.iv_1:
                mIv1.setImageResource(R.drawable.star_d);
                mIv2.setImageResource(R.drawable.star_e);
                mIv3.setImageResource(R.drawable.star_e);
                mIv4.setImageResource(R.drawable.star_e);
                mIv5.setImageResource(R.drawable.star_e);
                mark=1;
                break;
            case R.id.btnPositive:
                dismiss();
                mOnConfirmListener.onClick(mark);
                break;
            case R.id.iv_dismiss:
                dismiss();
                break;

        }
    }


    public interface OnConfirmListener {
        void onClick(int mark);
    }
}
