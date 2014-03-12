package com.jsb.simple;

import org.json.JSONObject;

import com.jsb.debug.Tracer;

public class JSBRemoveFriend extends IJSBInternal {
	
	public JSBRemoveFriend(JSBImpl jsb) {
		super(jsb);
	}

	private final static String TAG = "JSBRemoveFriend";

	@Override
	public void onPageFinished() {
		
	}

	@Override
	public void notify(JSONObject param, String onSuccess,
			String onError, String onProgress) {
		Tracer.d(TAG, "JSBRemoveFriend: notify get called");
	}
}
