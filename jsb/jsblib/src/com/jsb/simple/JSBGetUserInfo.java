package com.jsb.simple;

import org.json.JSONObject;

import com.jsb.debug.Tracer;

public class JSBGetUserInfo extends IJSBInternal {
	
	public JSBGetUserInfo(JSBImpl jsb) {
		super(jsb);
	}

	private final static String TAG = "JSBGetUserInfo";

	@Override
	public void onPageFinished() {
		
	}

	@Override
	public void notify(JSONObject param, String onSuccess,
			String onError, String onProgress) {
		Tracer.d(TAG, "JSBGetUserInfo: notify get called");
	}
}
