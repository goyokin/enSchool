package com.jsb.simple;

import org.json.JSONObject;

import com.jsb.debug.Tracer;

public class JSBAddFriend extends IJSBInternal {
	
	public JSBAddFriend(JSBImpl jsb) {
		super(jsb);
	}

	private final static String TAG = "JSBAddFriend";

	@Override
	public void onPageFinished() {
		
	}

	@Override
	public void notify(JSONObject param, String onSuccess,
			String onError, String onProgress) {
		Tracer.d(TAG, "JSBAddFriend: notify get called");
	}
}
