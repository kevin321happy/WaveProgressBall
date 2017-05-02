package com.fips.huashun.widgets;

import java.io.Serializable;

public class Tag implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2684657309332033242L;
	
	private int backgroundResId;
	private int id;
	private boolean isChecked;
	private int leftDrawableResId;
	private int rightDrawableResId;
	private String title;
	private String Product_id;
	private String spec_value;
	private String spec_value_id;
	public void setSpec_value(String spec_value){
		this.spec_value = spec_value;
	}
	public String getSpec_value(){
		return this.spec_value;
	}
	public void setSpec_value_id(String spec_value_id){
		this.spec_value_id = spec_value_id;
	}
	public String getSpec_value_id(){
		return this.spec_value_id;
	}
	public String getProduct_id() {
		return Product_id;
	}

	public void setProduct_id(String product_id) {
		Product_id = product_id;
	}

	public Tag() {
		
	}

	public Tag(int paramInt, String paramString) {
		this.id = paramInt;
		this.title = paramString;
	}

	public int getBackgroundResId() {
		return this.backgroundResId;
	}

	public int getId() {
		return this.id;
	}

	public int getLeftDrawableResId() {
		return this.leftDrawableResId;
	}

	public int getRightDrawableResId() {
		return this.rightDrawableResId;
	}

	public String getTitle() {
		return this.title;
	}

	public boolean isChecked() {
		return this.isChecked;
	}

	public void setBackgroundResId(int paramInt) {
		this.backgroundResId = paramInt;
	}

	public void setChecked(boolean paramBoolean) {
		this.isChecked = paramBoolean;
	}

	public void setId(int paramInt) {
		this.id = paramInt;
	}

	public void setLeftDrawableResId(int paramInt) {
		this.leftDrawableResId = paramInt;
	}

	public void setRightDrawableResId(int paramInt) {
		this.rightDrawableResId = paramInt;
	}

	public void setTitle(String paramString) {
		this.title = paramString;
	}
}
