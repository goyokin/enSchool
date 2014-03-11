package com.jsb.simple;

import org.json.JSONObject;

public class JSBSubscribe implements IJSB {
	
	private final static String TAG = "JSBSubscribe";

	@Override
	public void notify(String jsonObj) {
		
	}

	@Override
	public void onPageFinished() {
		
	}

	@Override
	public void notify(JSONObject jsonObj, String param, String onSuccess,
			String onError, String onProgress) {
		Tracer.d(TAG, "JSBSubscribe:notify get called");
	}
}
