package com.fips.huashun.widgets;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build.VERSION_CODES;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.TextView;
import com.fips.huashun.common.Constants;

/**
 * Created by kevin on 2017/2/20. 汉仪字体
 */
public class HyTextView extends TextView {

  public HyTextView(Context context) {
    super(context);
  }

  public HyTextView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public HyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @RequiresApi(api = VERSION_CODES.LOLLIPOP)
  public HyTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
  }

  public void setTypeface(Typeface tf, int style) {
     super.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/hanyi.ttf"));
    super.setTypeface(Constants.TF_HANYI_FONT);
  }
}
