package com.fips.huashun.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;


public class MyHorizentalScrollView extends HorizontalScrollView {
	private float	mDownX;
	private float	mDownY;
	public MyHorizentalScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public MyHorizentalScrollView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev)
	{

		// 请求父容器不要拦截touch
		// true：请求不拦截
		// false:请求拦截

		//int currentItem = getCurrentItem();

		switch (ev.getAction())
		{
			case MotionEvent.ACTION_DOWN:
				mDownX = ev.getX();
				mDownY = ev.getY();
				//getParent().requestDisallowInterceptTouchEvent(true);
				break;
			case MotionEvent.ACTION_MOVE:
				float moveX = ev.getX();
				float moveY = ev.getY();

				// 从右往左滑动--> downX >= moveX
				// 从左往右滑动--> downX < moveX

				// 如果是水平滑动时

				if (Math.abs(moveX - mDownX) > Math.abs(moveY - mDownY))
				{
					// 水平滑动

					
						getParent().requestDisallowInterceptTouchEvent(true);
					
				}
				else
				{
					getParent().requestDisallowInterceptTouchEvent(false);
				}
				break;
			case MotionEvent.ACTION_UP:

				break;
			default:
				break;
		}

		return super.dispatchTouchEvent(ev);
	}
}
