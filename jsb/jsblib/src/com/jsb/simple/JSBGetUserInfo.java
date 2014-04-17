package com.jsb.simple;

import org.json.JSONObject;

import android.content.Context;

import com.jsb.chat.GroupManager;
import com.jsb.debug.Tracer;

public class JSBGetUserInfo extends IJSBInternal {
	
	public JSBGetUserInfo(JSBImpl jsb) {
		super(jsb);
	}
	
	private final static String TAG = "JSBGetFriendInfo";
	private final static String PARAM_USER = "user";
	private GroupManager mGroupMgr = null;

	@Override
	public void onPageFinished() {
		
	}

	@Override
	public void notify(Context context, JSONObject param, String onSuccess,
					   String onError, String onProgress) {
		Tracer.d(TAG, "JSBGetFriendInfo: notify get called");
		
		String user = null;
		try {
			user = param.getString(PARAM_USER);
		} catch (Exception e) {
			Tracer.e(TAG, "user is not set");
			callback(onError, null);
			return;
		}
		
		if (mGroupMgr == null) {
			mGroupMgr = GroupManager.getInstance();
		}
		
		String userInfo = null;
		if (mGroupMgr != null) {
			userInfo = mGroupMgr.getUser(user);
		}
		
		if (userInfo != null) {
			callback(onSuccess, userInfo);
		} else {
			callback(onError, null);
		}
	}
}
