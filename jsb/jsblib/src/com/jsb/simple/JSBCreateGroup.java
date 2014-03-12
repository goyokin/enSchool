package com.jsb.simple;

import org.json.JSONObject;

import com.jsb.debug.Tracer;

public class JSBCreateGroup extends IJSBInternal {
	
	public JSBCreateGroup(JSBImpl jsb) {
		super(jsb);
	}

	private final static String TAG = "JSBCreateGroup";

	@Override
	public void onPageFinished() {
		
	}

	@Override
	public void notify(JSONObject param, String onSuccess,
			String onError, String onProgress) {
		Tracer.d(TAG, "JSBCreateGroup: notify get called");
	}
}
