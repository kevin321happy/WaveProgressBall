package com.fips.huashun.ui.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * ListView中嵌套gridview显示不正常的问题
 */
public class MultiLineGridView extends GridView {
	public MultiLineGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MultiLineGridView(Context context) {
		super(context);
	}

	public MultiLineGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}