package com.jsb.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class Config {
	
	// private final static String TAG = "Config";
	private static Config mInstance = null;
	private Context mContext = null;
	private final static String PREFERENCE_NAME = "jsb_configures";
	
	public final static String KEY_HOST_NAME = "key_host_name";
	public final static String DEF_HOST = "127.0.0.1";
	public final static String KEY_RES_NAME = "key_res_name";
	public final static String DEF_RES = "smack-jsb";
	public final static String KEY_HIS_PG_SIZE = "key_pg_sz";
	public final static int DEF_HIS_PG_SIZE = 20;
	
	private Config(Context context) {
		mContext = context;
	}
	
	public static Config getInstance(Context context) {
		if (mInstance == null) {
			mInstance = new Config(context);
		}
		
		return mInstance;
	}
	
	public int getInt(String key, int defVal) {
		SharedPreferences sp = mContext.getSharedPreferences(PREFERENCE_NAME, 0);
		return sp.getInt(key, defVal);
	}
	
	public void setInt(String key, int val) {
		SharedPreferences sp = mContext.getSharedPreferences(PREFERENCE_NAME, 0);
		Editor edt = sp.edit();
		edt.putInt(key, val);
		edt.commit();
	}
	
	public long getLong(String key, long defVal) {
		SharedPreferences sp = mContext.getSharedPreferences(PREFERENCE_NAME, 0);
		return sp.getLong(key, defVal);
	}
	
	public void setLong(String key, long val) {
		SharedPreferences sp = mContext.getSharedPreferences(PREFERENCE_NAME, 0);
		Editor edt = sp.edit();
		edt.putLong(key, val);
		edt.commit();
	}
	
	public String getString(String key, String defVal) {
		SharedPreferences sp = mContext.getSharedPreferences(PREFERENCE_NAME, 0);
		return sp.getString(key, defVal);
	}
	
	public void setString(String key, String val) {
		SharedPreferences sp = mContext.getSharedPreferences(PREFERENCE_NAME, 0);
		Editor edt = sp.edit();
		edt.putString(key, val);
		edt.commit();
	}
}
