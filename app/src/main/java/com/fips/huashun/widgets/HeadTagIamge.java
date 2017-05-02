package com.fips.huashun.widgets;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.fips.huashun.R;
import com.fips.huashun.ui.utils.DisplayUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by kevin on 2017/3/4. 邮箱：kevin321vip@126.com 公司：锦绣氘(武汉科技有限公司) 头像标签的IamgeView
 */

public class HeadTagIamge extends RelativeLayout {

  private CircleImageView mIv_icon;
  private TextView mTv_lable;
  private int mMeasuredWidth;
  private int mMeasuredHeight;
  private Context mContext;

  public HeadTagIamge(Context context) {
    this(context, null);
  }

  public HeadTagIamge(Context context, AttributeSet attrs) {
    super(context, attrs);
    this.mContext = context;
    addView(View.inflate(context, R.layout.head_image_item, null));
    mIv_icon = (CircleImageView) findViewById(R.id.iv_head_icon);
    mTv_lable = (TextView) findViewById(R.id.tv_label);

    Typeface typeFace = Typeface.createFromAsset(mContext.getAssets(), "fonts/hanyi.ttf");
    mTv_lable.setTypeface(typeFace);
  }

  public HeadTagIamge(Context context, AttributeSet attrs, int defStyleAttr) {
    this(context, null);
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    mMeasuredWidth = this.getMeasuredWidth();
    mMeasuredHeight = this.getMeasuredHeight();
    setChildSize();
  }

  //设置图标是否可见
  public void setLableVisible(boolean b) {
    mTv_lable.setVisibility(b ? VISIBLE : GONE);
  }

  //加载头像
  public void loadHeadIamge(Context context, String url) {
    ImageLoader.getInstance().displayImage(url, mIv_icon);
//    Glide.with(context).load(url).placeholder(R.drawable.user_head_default).into(mIv_icon);
//    Glide.with(context)
//        .load(url)
//        .error(R.drawable.user_head_default)
//        .into(mIv_icon);
  }

  //设置标签的文字
  public void setLableText(String text) {
    mTv_lable.setText(text);
  }

  //设置子控件的大小
  private void setChildSize() {
    //头像的参数设置
    ViewGroup.LayoutParams iv_icon_params = mIv_icon.getLayoutParams();
    iv_icon_params.width = (int) (mMeasuredWidth * 0.9);
    iv_icon_params.height = (int) (mMeasuredWidth * 0.9);
    mIv_icon.setLayoutParams(iv_icon_params);
    ViewGroup.LayoutParams tv_lable_params = mTv_lable.getLayoutParams();
    tv_lable_params.width = mMeasuredWidth;
    tv_lable_params.height = (int) (mMeasuredHeight * 0.5);
    mTv_lable.setLayoutParams(tv_lable_params);
  }

  //设置标签的文字的大小
  public void setLableSize(int lableSize) {
    mTv_lable.setTextSize(DisplayUtil.px2dp(mContext, lableSize * 2));
  }

  //设置头像的描边颜色
  public void setBorderColor(int borderColor) {
    mIv_icon.setBorderColor(borderColor);
  }

  //设置描边的宽度
  public void setBorderWidth(int BorderWidth) {
    mIv_icon.setBorderWidth(BorderWidth);
  }
}
