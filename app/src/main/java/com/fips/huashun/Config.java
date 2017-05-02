package com.fips.huashun;

import android.os.Environment;

public class Config {
	//测试
	public static String ServerIP = "http://61.183.247.103:8082/index.php/api";
	//正式
//	public static String ServerIP = "http://www.jiulide.com/";
	public static String ServerIP2 = "http://61.183.247.103:8082/mendian/index.php/api/b2c";

	public static final String SharePreferenceName = "ruiyun_app_jiulide";

	public static String priceMark = "¥";

	public static final String TEMP_SDCARD_DIR = Environment
			.getExternalStorageDirectory().getPath() + "/JLD/temp/";



}
