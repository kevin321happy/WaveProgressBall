package com.fips.huashun.widgets;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build.VERSION_CODES;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by kevin on 2017/2/20. 华康字体
 */

public class HkTextView extends TextView {

  public HkTextView(Context context) {
    super(context);
  }

  public HkTextView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public HkTextView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }
  @RequiresApi(api = VERSION_CODES.LOLLIPOP)
  public HkTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
  }
  public void setTypeface(Typeface tf, int style) {
    if (style == Typeface.NORMAL) {//海报
      super.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/hkww.ttc"));
//     super.setTypeface(Constants.TC_HKWW_FONT);
    } else {
      super.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/hkhb.ttf"));
//      super.setTypeface(Constants.TF_HKHB_FONT);
    }
  }
}
