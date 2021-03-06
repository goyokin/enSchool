package com.jsb.simple;

import org.json.JSONObject;

import android.content.Context;

import com.jsb.chat.AccountManager;
import com.jsb.debug.Tracer;

public class JSBSubscribe extends IJSBInternal {
	
	public JSBSubscribe(JSBImpl jsb) {
		super(jsb);
	}

	private final static String TAG = "JSBSubscribe";
	private final static String PARAM_USER_NAME = "username";
	private final static String PARAM_PASSWORD = "password";
	private String mUsername = null;
	private String mPassword = null;
	private AccountManager mAccountMgr = null;

	@Override
	public void onPageFinished() {
		
	}

	@Override
	public void notify(Context context, JSONObject param, String onSuccess,
					   String onError, String onProgress) {
		Tracer.d(TAG, "JSBSubscribe:notify get called");
		
		try {
			mUsername = param.getString(PARAM_USER_NAME);
		} catch (Exception e) {
			Tracer.w(TAG, "fail to get username", e);
		}
		
		try {
			mPassword = param.getString(PARAM_PASSWORD);
		} catch (Exception e) {
			Tracer.w(TAG, "fail to get username", e);
		}
		
		boolean success = false;
		if (mUsername != null) {
			if (mAccountMgr != null && mAccountMgr.isUserConnected(mUsername)) {
				callback(onSuccess, null);
				return;
			}
			
			mAccountMgr = AccountManager.getInstance(context);
			if (mAccountMgr.connect(mUsername, mPassword)) {
				success = true;
			}
		}
		
		if (success) {
			callback(onSuccess, null);
		} else {
			callback(onError, null);
		}
	}
}
