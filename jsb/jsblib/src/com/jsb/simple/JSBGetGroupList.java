package com.jsb.simple;

import org.json.JSONObject;

import com.jsb.debug.Tracer;

public class JSBGetGroupList extends IJSBInternal {
	
	public JSBGetGroupList(JSBImpl jsb) {
		super(jsb);
	}

	private final static String TAG = "JSBGetGroupList";

	@Override
	public void onPageFinished() {
		
	}

	@Override
	public void notify(JSONObject param, String onSuccess,
			String onError, String onProgress) {
		Tracer.d(TAG, "JSBGetGroupList: notify get called");
	}
}
