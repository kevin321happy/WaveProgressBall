package com.fips.huashun.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.ListView;

public class MyListView extends ListView {
	Context context;
	public MyListView(Context context) {
		this(context, null);
		this.context = context;
	}

	public MyListView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		this.context = context;
	}

	public MyListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if(ev.getAction()==MotionEvent.ACTION_MOVE){
			// getParent().requestDisallowInterceptTouchEvent(false);
			return false;
		}
		return super.dispatchTouchEvent(ev);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		Display display= ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		int   screenHeight = display.getHeight();
		if(heightMode==MeasureSpec.UNSPECIFIED){
			heightMeasureSpec = MeasureSpec.makeMeasureSpec(screenHeight,MeasureSpec.AT_MOST);
		}
		super.onMeasure(widthMeasureSpec,heightMeasureSpec);
	}
}