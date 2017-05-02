package com.fips.huashun.widgets;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.fips.huashun.R;


/**
 * @author sushuo
 * @date 2015年6月10日
 */
public class LoadingDialog {
    /**
     * 旋转动画的时间
     */
    static final int ROTATION_ANIMATION_DURATION = 1200;

    /**
     * 动画插值
     */
    static final Interpolator ANIMATION_INTERPOLATOR = new LinearInterpolator();

    Window window;

    Dialog d;

    private ImageView imageView;

    //	private Animation animation;

    private TextView textView;

    private Activity activity;

    @SuppressWarnings("deprecation")
    public LoadingDialog(Activity activity) {
        this.activity = activity;
        View view = View.inflate(activity, R.layout.common_loadingdialog, null);
        imageView = (ImageView) view.findViewById(R.id.imageView);
        textView = (TextView) view.findViewById(R.id.load_text);
//		textView.setText(str);
        //		animation = AnimationUtils.loadAnimation(activity, R.anim.retateleft);
        //		imageView.startAnimation(animation);

        //		float pivotValue = 0.5f; // SUPPRESS CHECKSTYLE
        //		float toDegree = 720.0f; // SUPPRESS CHECKSTYLE
        //		RotateAnimation mRotateAnimation =
        //			new RotateAnimation(0.0f, toDegree, Animation.RELATIVE_TO_SELF, pivotValue, Animation.RELATIVE_TO_SELF, pivotValue);
        //		mRotateAnimation.setFillAfter(true);
        //		mRotateAnimation.setInterpolator(ANIMATION_INTERPOLATOR);
        //		mRotateAnimation.setDuration(ROTATION_ANIMATION_DURATION);
        //		mRotateAnimation.setRepeatCount(Animation.INFINITE);
        //		mRotateAnimation.setRepeatMode(Animation.RESTART);
        //		imageView.startAnimation(mRotateAnimation);
        //马儿奔跑的动画
        AnimationDrawable ad = (AnimationDrawable) activity.getResources().getDrawable(R.drawable.anim_loading_progress_round);
        imageView.setBackgroundDrawable(ad);
        ad.start();

        d = new Dialog(activity, R.style.dialog);// 加入样式
        d.setCanceledOnTouchOutside(false);
        window = d.getWindow();
        window.setGravity(Gravity.CENTER);
        window.setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

    }

    public void show() {
        if (null != d && !d.isShowing()) {
            d.show();
        }
    }

    public void show(String content) {
        if (null != d && !d.isShowing()) {
            textView.setText(content);
            d.show();
        }
    }

    public void dismiss() {
        if (d != null) {
            d.dismiss();
        }
    }

    public void setCancel() {
        if (d != null) {
            d.setCancelable(false);
        }
    }

}
