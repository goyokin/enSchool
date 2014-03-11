package com.jsb.simple;

import org.json.JSONObject;

public class JSBLogin implements IJSB {
	
	private final static String TAG = "JSBLogin";

	@Override
	public void notify(String jsonObj) {
		
	}

	@Override
	public void onPageFinished() {
		
	}

	@Override
	public void notify(JSONObject jsonObj, String param, String onSuccess,
			String onError, String onProgress) {
		Tracer.d(TAG, "JSBLogin:notify get called");
	}
}
