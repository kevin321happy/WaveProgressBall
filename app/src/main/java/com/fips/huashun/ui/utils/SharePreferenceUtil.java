package com.fips.huashun.ui.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePreferenceUtil {
	private static SharedPreferences mSharedPreferences;

	public SharePreferenceUtil(Context context, String file) {
		mSharedPreferences = context.getSharedPreferences(file, Context.MODE_PRIVATE);
	}

	/**
	 * String-?洢key?????
	 *
	 * @param key
	 */
	public void setStringValue(String key) {
		mSharedPreferences.edit().putString(key, "").commit();
	}

	/**
	 * String-?洢key?value
	 *
	 * @param key
	 * @param value
	 */
	public void setStringValue(String key, String value) {
		mSharedPreferences.edit().putString(key, value).commit();
	}

	/**
	 * String-???key???
	 *
	 * @param key
	 * @return
	 */
	public String getStringValue(String key) {
		return mSharedPreferences.getString(key, "");
	}

	/**
	 * String-???key???
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public String getStringValue(String key, String defaultValue) {
		return mSharedPreferences.getString(key, defaultValue);
	}

	/**
	 * Boolean
	 */
	public static void setBooleanValue(String key, Boolean value) {
		mSharedPreferences.edit().putBoolean(key, value).commit();
	}

	public static boolean getBooleanValue(String key, Boolean defaultValue) {
		return mSharedPreferences.getBoolean(key, defaultValue);
	}

	/**
	 * Long
	 */
	public void setLongValue(String key, long value) {
		mSharedPreferences.edit().putLong(key, value).commit();
	}

	public long getLongValue(String key) {
		return mSharedPreferences.getLong(key, 0L);
	}

	/**
	 * int
	 */
	public void setIntValue(String key, int value) {
		mSharedPreferences.edit().putInt(key, value).commit();
	}

	public int getIntValue(String key) {
		return mSharedPreferences.getInt(key, 0);
	}

	public int getIntValue(String key, int defaultValue) {
		return mSharedPreferences.getInt(key, defaultValue);
	}

	/**
	 * remove
	 */
	public void remove(String name) {
		mSharedPreferences.edit().remove(name).commit();
	}

	/**
	 * clear
	 */
	public void clear() {
		mSharedPreferences.edit().clear().commit();
	}


}
