package com.fips.huashun.widgets;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fips.huashun.R;


public class MyShopVideoLayout extends LinearLayout{

	private TextView  textTitle;


	private ImageView imageView;

	private String url;
	public MyShopVideoLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.categry_item, this);
		textTitle=(TextView)findViewById(R.id.tv_text);



		imageView=(ImageView)findViewById(R.id.iv_image);

	}
	public MyShopVideoLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.categry_item, this);
		textTitle=(TextView)findViewById(R.id.tv_text);
		imageView=(ImageView)findViewById(R.id.iv_image);

	}

	/**
	 * 为TextView1设置显示的文字
	 */ 
	public void setTextTitleText(String text) {
		textTitle.setText(text); 
	} 
	/**
	 * 为TextView1设置显示的文字
	 */ 


	public void setStringUrl(String url){
		this.url = url;
	}

	public String getStringUrl(){
		return this.url;

	}
	public TextView getTextTitle() {
		return textTitle;
	}
	public void setTextTitle(TextView textView1) {
		this.textTitle = textView1;
	}


	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	public ImageView getImageView() {
		return imageView;
	}
	public void setImageView(ImageView imageView) {
		this.imageView = imageView;
	}

	public void setImageResource(int resId) { 
		imageView.setImageResource(resId); 
	} 

}
