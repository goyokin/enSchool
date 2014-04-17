package com.jsb.simple;

import java.lang.ref.WeakReference;

import org.json.JSONObject;

import android.content.Context;

import com.jsb.debug.Tracer;

public abstract class IJSBInternal {
	
	private final static String TAG = "IJSBInternal";
	private WeakReference<JSBImpl> mJsb = null;

	public abstract void notify(Context context, JSONObject param, String onSuccess, String onError, String onProgress);
	public abstract void onPageFinished();

	// empty init not allowed
	protected IJSBInternal() {
	}

	public IJSBInternal(JSBImpl jsb) {
		mJsb = new WeakReference<JSBImpl>(jsb);
	}
	
	protected void callback(String method, String param) {
		if (method == null || method.length() <= 0)
			return;
		try {
			JSBImpl inst = mJsb.get();
			if (inst != null) {
				inst.callJSMethod(method, param);
			}
		} catch (Exception e) {
			Tracer.e(TAG, "fail to call JSB");
		}
	}
}
