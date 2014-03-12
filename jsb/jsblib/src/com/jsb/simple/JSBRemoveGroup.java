package com.jsb.simple;

import org.json.JSONObject;

import com.jsb.debug.Tracer;

public class JSBRemoveGroup extends IJSBInternal {
	
	public JSBRemoveGroup(JSBImpl jsb) {
		super(jsb);
	}

	private final static String TAG = "JSBRemoveGroup";

	@Override
	public void onPageFinished() {
		
	}

	@Override
	public void notify(JSONObject param, String onSuccess,
			String onError, String onProgress) {
		Tracer.d(TAG, "JSBRemoveGroup: notify get called");
	}
}
