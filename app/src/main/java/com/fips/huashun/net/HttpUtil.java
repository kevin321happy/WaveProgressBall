package com.fips.huashun.net;

import android.util.Log;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;


public class HttpUtil {
	//OkHttpClient client=new OkHttpClient();
	private static AsyncHttpClient client = new AsyncHttpClient();
	static {
		client.setTimeout(20000);
		//client.setSSLSocketFactory();
}
	public static void get(String urlString, AsyncHttpResponseHandler res) {
		client.get(urlString, res);
	}
	public static void get(String urlString, RequestParams params, AsyncHttpResponseHandler res) {
		client.get(urlString, params, res);
	}
	public static void post(String urlString, RequestParams params, JsonHttpResponseHandler res) {
//		Log.d("Registertivity","开始注册请求");
		client.post(urlString, params, res);
		Log.d("params",params+"");
	}

	public static void get(String urlString, JsonHttpResponseHandler res) {
		client.get(urlString, res);
	}

	public static void get(String urlString, RequestParams params, JsonHttpResponseHandler res) {
		client.get(urlString, params, res);
	}

	public static void get(String opt, String _json, JsonHttpResponseHandler res) {
//		RequestParams params = new RequestParams();
//		params.put("opt", opt);
//		params.put("_json", _json);
//		client.get(Config.ServerIP, params, res);
	}

	public static void get(String uString, BinaryHttpResponseHandler bHandler) {
		client.get(uString, bHandler);
	}
//	public static void post(String urlString, RequestParams params, JsonHttpResponseHandler res) {
//		client.post(urlString, params, res);
//	}
	public static AsyncHttpClient getClient() {
		return client;
	}

}