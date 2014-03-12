package com.jsb.simple;

import org.json.JSONObject;

import com.jsb.debug.Tracer;

public class JSBChangeGroup extends IJSBInternal {
	
	public JSBChangeGroup(JSBImpl jsb) {
		super(jsb);
	}

	private final static String TAG = "JSBChangeGroup";

	@Override
	public void onPageFinished() {
		
	}

	@Override
	public void notify(JSONObject param, String onSuccess,
			String onError, String onProgress) {
		Tracer.d(TAG, "JSBChangeGroup: notify get called");
	}
}
