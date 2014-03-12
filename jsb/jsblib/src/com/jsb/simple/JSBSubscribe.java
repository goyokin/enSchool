package com.jsb.simple;

import org.json.JSONObject;

import com.jsb.debug.Tracer;

public class JSBSubscribe extends IJSBInternal {
	
	public JSBSubscribe(JSBImpl jsb) {
		super(jsb);
	}

	private final static String TAG = "JSBSubscribe";

	@Override
	public void onPageFinished() {
		
	}

	@Override
	public void notify(JSONObject param, String onSuccess,
			String onError, String onProgress) {
		Tracer.d(TAG, "JSBSubscribe:notify get called");
		Tracer.d(TAG, "JSBSubscribe:call back to javascript");
		callback(onSuccess, null);
	}
}