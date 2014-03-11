package com.jsb.simple;

import org.json.JSONObject;

public interface IJSB {
	/**
	 * JSON format:
	 * {
	 * "class":"${class name}",
	 * "method":"${method name}",
	 * "param":"${parameters}",
	 * "onSuccess":"${js method URI, e.g. myscript.onSuccess}",
	 * "onError":"${js method URI, e.g. myscript.onError}",
	 * "onProgress":"${js method URI, e.g. myscript.onProgress}",
	 * }
	 */
	public final static String KEY_CLASS_NAME = "class";
	public final static String KEY_METHOD_NAME = "method";
	public final static String KEY_PARAM = "param";
	public final static String KEY_ON_SUCCESS = "onSuccess";
	public final static String KEY_ON_ERROR = "onError";
	public final static String KEY_ON_PROGRESS = "onProgress";
	
	public void notify(String jsonStr);
	public void notify(JSONObject jsonObj, String param, String onSuccess, String onError, String onProgress);
	
	public void onPageFinished();
}
