package com.jsb.simple;

import org.json.JSONObject;

public class JSBSubscribe implements IJSBInternal {
	
	private final static String TAG = "JSBSubscribe";

	@Override
	public void onPageFinished() {
		
	}

	@Override
	public void notify(JSONObject param, String onSuccess,
			String onError, String onProgress) {
		Tracer.d(TAG, "JSBSubscribe:notify get called");
	}
}
