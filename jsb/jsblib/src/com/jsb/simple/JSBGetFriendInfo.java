package com.jsb.simple;

import org.json.JSONObject;

import com.jsb.debug.Tracer;

public class JSBGetFriendInfo extends IJSBInternal {
	
	public JSBGetFriendInfo(JSBImpl jsb) {
		super(jsb);
	}

	private final static String TAG = "JSBGetFriendInfo";

	@Override
	public void onPageFinished() {
		
	}

	@Override
	public void notify(JSONObject param, String onSuccess,
			String onError, String onProgress) {
		Tracer.d(TAG, "JSBGetFriendInfo: notify get called");
	}
}
