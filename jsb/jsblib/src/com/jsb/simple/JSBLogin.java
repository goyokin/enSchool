package com.jsb.simple;

import org.json.JSONObject;

public class JSBLogin implements IJSBInternal {
	
	private final static String TAG = "JSBLogin";

	@Override
	public void onPageFinished() {
		
	}

	@Override
	public void notify(JSONObject param, String onSuccess,
			String onError, String onProgress) {
		Tracer.d(TAG, "JSBLogin:notify get called");
	}
}
