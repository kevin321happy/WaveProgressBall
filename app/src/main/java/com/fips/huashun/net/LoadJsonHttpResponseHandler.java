package com.fips.huashun.net;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import com.fips.huashun.widgets.LoadingDialog;
import com.loopj.android.http.JsonHttpResponseHandler;
import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

public class LoadJsonHttpResponseHandler extends JsonHttpResponseHandler {

	private LoadDatahandler mHandler;
	private  static boolean isShow = true;
	private  static Context mContext;
	private  static LoadingDialog mPostingdialog =null;
	private static String msg = "数据处理中...";
	public LoadJsonHttpResponseHandler(Context mContext, LoadDatahandler mHandler) {
		this.mHandler = mHandler;
		LoadJsonHttpResponseHandler.mContext = mContext;
	}

	public LoadJsonHttpResponseHandler(Context mContext, boolean isShow, LoadDatahandler mHandler) {
		LoadJsonHttpResponseHandler.mContext = mContext;
		this.mHandler = mHandler;
		LoadJsonHttpResponseHandler.isShow = isShow;
	}

	public LoadJsonHttpResponseHandler(Context mContext, boolean isShow, String msg, LoadDatahandler mHandler) {
		LoadJsonHttpResponseHandler.mContext = mContext;
		this.mHandler = mHandler;
		LoadJsonHttpResponseHandler.isShow = isShow;
		LoadJsonHttpResponseHandler.msg = msg;
	}

	@Override
	public void onStart() {
		super.onStart();
		mHandler.onStart();
	}

	@Override
	public void onFinish() {
		super.onFinish();
		mHandler.onFinish();
	}


	public void onFailure(String error, String errorMessage) {
		if (errorMessage != null) {
			mHandler.onFailure(error, errorMessage);
		}
	}

//	@Override
//	public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//		super.onFailure(statusCode, headers, responseString, throwable);
//		mHandler.onFailure("", "");
//		dismissLoadingDialog();
//		ToastUtil.getInstant().show("网络连接异常");
//	}

//	@Override
//	public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
//		super.onFailure(statusCode, headers, throwable, errorResponse);
//		mHandler.onFailure("", "");
//		dismissLoadingDialog();
//		ToastUtil.getInstant().show("网络连接异常");
//	}

	public LoadJsonHttpResponseHandler() {
		super();
	}

	@Override
	public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
		super.onFailure(statusCode, headers, throwable, errorResponse);
		mHandler.onFailure("", "");
		dismissLoadingDialog();
	//	ToastUtil.getInstant().show("网络连接异常");

	}
//	@Override
//	public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
//		super.onSuccess(statusCode, headers, response);
//		Log.e("HuaShun",response.toString());
//		dismissLoadingDialog();
//		try {
////			String msg = response.get("msg").toString();
////			String suc =response.get("suc").toString();
////			if (!TextUtils.isEmpty(msg)){
////				ToastUtil.getInstant().show(msg);
////			}
//
//			mHandler.onSuccess(response.toString());
//
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
//	}

	//	@Override
//	public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
//		super.onFailure(statusCode, headers, throwable, errorResponse);
//		mHandler.onFailure("", "");
//		dismissLoadingDialog();
//		ToastUtil.getInstant().show("网络连接异常");
//	}
//
//	@Override
//	public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//		mHandler.onFailure("", "");
//		dismissLoadingDialog();
//		ToastUtil.getInstant().show("网络连接异常");
//	}

	@Override
	public void onSuccess(int statusCode, Header[] arg1, JSONObject response) {
		super.onSuccess(statusCode, arg1, response);
		Log.e("HuaShun",response.toString());
		dismissLoadingDialog();
		try {
//			String msg = response.get("msg").toString();
			String suc =response.get("suc").toString();
//			if (!TextUtils.isEmpty(msg)){
//				ToastUtil.getInstant().show(msg);
//			}
			mHandler.onSuccess(response);

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void dismissLoadingDialog() {
		if (isShow) {
			if (mPostingdialog == null ) {
				return;
			}
			mPostingdialog.dismiss();
			mPostingdialog = null;
		}
	}
	private  static void  showPostingdialog(){
		if (isShow) {
			if (mPostingdialog == null) {
				mPostingdialog = new LoadingDialog((Activity) mContext);
			}
			mPostingdialog.show();
		}
	}
}
