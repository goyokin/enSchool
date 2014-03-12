package com.jsb.simple;

import org.json.JSONObject;

import com.jsb.debug.Tracer;

public class JSBStartCamera extends IJSBInternal {
	
	public JSBStartCamera(JSBImpl jsb) {
		super(jsb);
	}

	private final static String TAG = "JSBStartCamera";

	@Override
	public void onPageFinished() {
		
	}

	@Override
	public void notify(JSONObject param, String onSuccess,
			String onError, String onProgress) {
		Tracer.d(TAG, "JSBStartCamera:notify get called");
	}
}
