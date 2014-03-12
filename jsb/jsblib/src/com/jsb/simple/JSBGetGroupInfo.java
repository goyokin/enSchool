package com.jsb.simple;

import org.json.JSONObject;

import com.jsb.debug.Tracer;

public class JSBGetGroupInfo extends IJSBInternal {
	
	public JSBGetGroupInfo(JSBImpl jsb) {
		super(jsb);
	}

	private final static String TAG = "JSBGetGroupInfo";

	@Override
	public void onPageFinished() {
		
	}

	@Override
	public void notify(JSONObject param, String onSuccess,
			String onError, String onProgress) {
		Tracer.d(TAG, "JSBGetGroupInfo: notify get called");
	}
}
