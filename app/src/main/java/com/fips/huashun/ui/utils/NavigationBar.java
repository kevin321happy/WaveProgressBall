package com.fips.huashun.ui.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fips.huashun.R;


public class NavigationBar extends RelativeLayout {

	public static final int LEFT_VIEW = 1;
	public static final int MIDDLE_VIEW = 2;
	public static final int RIGHT_VIEW = 3;

	public interface NavigationListener {
		void onButtonClick(int button);
	}

	private NavigationListener mListener = null;

	private ImageButton mLeftImgBtn;
	private TextView mLeftTextView;
	private TextView mTitleView;
	private ImageButton mRightImgBtn;
	private TextView mRightTextView;
	private RelativeLayout mBar_main_layout;

	public NavigationBar(Context context) {
		super(context);
		init();
	}

	public NavigationBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.navigation_bar, this, true);

		mBar_main_layout = (RelativeLayout) findViewById(R.id.bar_main_layout);
		mLeftImgBtn = (ImageButton) findViewById(R.id.img_btn_navigation_left);
		mLeftTextView = (TextView) findViewById(R.id.tv_navigation_left);
		mTitleView = (TextView) findViewById(R.id.tv_navigation_title);
		mRightImgBtn = (ImageButton) findViewById(R.id.img_btn_navigation_right);
		mRightTextView = (TextView) findViewById(R.id.tv_navigation_right);

		mLeftImgBtn.setOnClickListener(buttonListener);
		mLeftTextView.setOnClickListener(buttonListener);
		mRightImgBtn.setOnClickListener(buttonListener);
		mRightTextView.setOnClickListener(buttonListener);
	}

	public void setBarBackgroundColor(int color) {
		mBar_main_layout.setBackgroundColor(color);
	}

	public void setListener(NavigationListener listener) {
		this.mListener = listener;
	}

	OnClickListener buttonListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if (v.getId() == R.id.img_btn_navigation_left || v.getId() == R.id.tv_navigation_left) {
				if (mListener != null) {
					mListener.onButtonClick(LEFT_VIEW);
				} else {
					// ((Activity) getContext()).onBackPressed();
				}
			}
			if (v.getId() == R.id.img_btn_navigation_right || v.getId() == R.id.tv_navigation_right) {
				if (mListener != null) {
					mListener.onButtonClick(RIGHT_VIEW);
				}
			}
		}
	};

	public void setLeftImage(int resId) {
		mLeftTextView.setVisibility(View.INVISIBLE);
		mLeftImgBtn.setVisibility(View.VISIBLE);
		mLeftImgBtn.setImageResource(resId);
	}

	public void setLeftBack() {
		mLeftTextView.setVisibility(View.INVISIBLE);
		mLeftImgBtn.setVisibility(View.VISIBLE);
		mLeftImgBtn.setImageResource(R.drawable.ico_back);
	}

	public void setLeftText(String string) {
		mLeftImgBtn.setVisibility(View.INVISIBLE);
		mLeftTextView.setVisibility(View.VISIBLE);
		mLeftTextView.setText(string);
	}

	public void setLeftIconText(String string) {
		// mLeftImgBtn.setVisibility(View.INVISIBLE);
		// mLeftTextView.setCompoundDrawablesWithIntrinsicBounds(null, null,
		// getResources().getDrawable(R.drawable.ico_club_arrow_down), null);
		// mLeftTextView.setVisibility(View.VISIBLE);
		// mLeftTextView.setText(string);
	}

	public void setHideLeftButton() {
		mLeftImgBtn.setVisibility(View.INVISIBLE);
	}

	public void setTitle(String string) {
		mTitleView.setText(string);
	}

	public void setTitle(int strResId) {
		mTitleView.setText(strResId);
	}

	public void setRightText(String string) {
		mRightImgBtn.setVisibility(View.INVISIBLE);
		mRightTextView.setVisibility(View.VISIBLE);
		mRightTextView.setText(string);
	}
	
	public void setHiedRightText() {
		mRightImgBtn.setVisibility(View.INVISIBLE);
		mRightTextView.setVisibility(View.INVISIBLE);
	}

	public String getRightText() {
		return mRightTextView.getText().toString();
	}

	public void setRightButton(int resId) {
		mRightTextView.setVisibility(View.INVISIBLE);
		mRightImgBtn.setVisibility(View.VISIBLE);
		mRightImgBtn.setImageResource(resId);
	}

}
