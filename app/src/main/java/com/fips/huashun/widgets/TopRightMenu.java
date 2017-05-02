package com.fips.huashun.widgets;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.fips.huashun.R;
import com.fips.huashun.modle.bean.TopRightMenuitem;
import com.fips.huashun.ui.adapter.TRMenuAdapter;
import com.fips.huashun.ui.utils.DisplayUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Author：Bro0cL on 2016/12/26.
 */
public class TopRightMenu {
    private Activity mContext;
    private PopupWindow mPopupWindow;
    private View content;
    private RecyclerView mRecyclerView;
    private List<TopRightMenuitem> mItemList;
    private boolean showIcon = true;
    private boolean dimBackgroup = true;
    private TRMenuAdapter mTrMenuAdapter;
    //popwindow 的高度
    private static final int DEFAULT_HEIGHT = 480;
    private static final int DEFAULT_WIDTH = 480;
    private int popHeight = DEFAULT_HEIGHT;
    private int popWidth = RecyclerView.LayoutParams.WRAP_CONTENT;
    private boolean needAnimationStyle = true;

    private static final int DEFAULT_ANIM_STYLE = R.style.TRM_ANIM_STYLE;
    private int animationStyle;
    private float alpha = 1.0f;
    public TopRightMenu(Activity context) {
        this.mContext = context;
        init();
    }
    //初始化
    private void init() {
        content = LayoutInflater.from(mContext).inflate(R.layout.trm_popup_menu, null);
        mRecyclerView = (RecyclerView) content.findViewById(R.id.trm_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        mItemList = new ArrayList<>();
        mTrMenuAdapter = new TRMenuAdapter(mContext, this, mItemList, showIcon);
        //mRecyclerView.setAdapter(mTrMenuAdapter);

    }

    private PopupWindow getPopupWindow() {
        mPopupWindow = new PopupWindow(mContext);
        mPopupWindow.setContentView(content);
        mPopupWindow.setHeight(popHeight);
        mPopupWindow.setWidth(popWidth);
        if (needAnimationStyle) {
            mPopupWindow.setAnimationStyle(animationStyle <= 0 ? DEFAULT_ANIM_STYLE : animationStyle);
            //mPopupWindow.setAnimationStyle(animationStyle);
        }
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable());
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (dimBackgroup) {
                    setBackgroundAlpha(1f, alpha, 240);
                }
            }
        });
        mTrMenuAdapter.setData(mItemList);
        mTrMenuAdapter.setShowIcon(showIcon);
        mRecyclerView.setAdapter(mTrMenuAdapter);
        return mPopupWindow;
        //popupWindow.showAtLocation();


    }

    //设置背景的透明度
    private void setBackgroundAlpha(float from, float to, int duration) {
        final WindowManager.LayoutParams lp = mContext.getWindow().getAttributes();
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(from, to);//透明度
        valueAnimator.setDuration(duration);//时间
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                lp.alpha = (float) valueAnimator.getAnimatedValue();
                mContext.getWindow().setAttributes(lp);
            }
        });
        valueAnimator.start();
    }

    //设置高度
    public TopRightMenu setHeight(int height) {
        if (height <= 0 && height != RecyclerView.LayoutParams.MATCH_PARENT
                && height != RecyclerView.LayoutParams.WRAP_CONTENT) {
            this.popHeight = DEFAULT_HEIGHT;
        } else if (height == -1) {
            this.popHeight = RecyclerView.LayoutParams.WRAP_CONTENT;
        } else {
            this.popHeight = height;
        }
        return this;
    }

    //设置宽度
    public TopRightMenu setWidth(int width) {
        if (width <= 0 && width != RecyclerView.LayoutParams.MATCH_PARENT
                && width != RecyclerView.LayoutParams.WRAP_CONTENT) {
            this.popWidth = DEFAULT_HEIGHT;
        } else if (width == -1) {
            this.popWidth = RecyclerView.LayoutParams.WRAP_CONTENT;
        } else {
            this.popWidth = width;
        }
        return this;
    }

    //是否显示菜单图标
    public TopRightMenu showIcon(boolean show) {
        this.showIcon = show;
        return this;
    }

    //添加菜单
    public TopRightMenu addMenuItem(TopRightMenuitem item) {
        mItemList.add(item);
        return this;
    }

    //添加多个菜单
    public TopRightMenu addMenuList(List<TopRightMenuitem> list) {
        mItemList.addAll(list);
        return this;
    }

    //是否让背景变暗
    public TopRightMenu dimBackground(boolean b) {
        this.dimBackgroup = b;
        return this;
    }

    //是否显示动画
    public TopRightMenu needAnimationStyle(boolean need) {
        this.needAnimationStyle = need;
        return this;
    }

    /**
     * 设置动画
     *
     * @param style
     * @return
     */
    public TopRightMenu setAnimationStyle(int style) {
        this.animationStyle = style;
        return this;
    }


    public TopRightMenu setOnMenuItemClickListener(OnMenuItemClickListener listener) {
        mTrMenuAdapter.setOnMenuItemClickListener(listener);
        return this;
    }

    public TopRightMenu showAsDropDown(View anchor, int xoff, int yoff) {
        if (mPopupWindow == null) {
            getPopupWindow();
        }
        if (!mPopupWindow.isShowing()) {
            mPopupWindow.showAsDropDown(anchor, DisplayUtil.px2dp(mContext,xoff), yoff);
            if (dimBackgroup) {
                setBackgroundAlpha(1f, alpha, 240);
            }
        }
        return this;
    }


    public void setPopWindowDiss() {
        if(mPopupWindow!=null&&mPopupWindow.isShowing()){
            mPopupWindow.dismiss();
        }
    }

    public interface OnMenuItemClickListener {
        void onMenuItemClick(int position);
    }
}


