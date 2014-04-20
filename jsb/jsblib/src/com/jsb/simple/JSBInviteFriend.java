package com.jsb.simple;

import org.json.JSONObject;

import android.content.Context;

import com.jsb.chat.GroupManager;
import com.jsb.debug.Tracer;

public class JSBInviteFriend extends IJSBInternal {
	
	public JSBInviteFriend(JSBImpl jsb) {
		super(jsb);
	}

	private final static String TAG = "JSBInviteFriend";
	private final static String PARAM_FRIEND = "friend";
	private final static String PARAM_GROUP = "group";
	private final static String PARAM_REASON = "reason";
	private GroupManager mGroupMgr = null;

	@Override
	public void onPageFinished() {
		
	}

	@Override
	public void notify(Context context, JSONObject param, String onSuccess,
					   String onError, String onProgress) {
		Tracer.d(TAG, "JSBInviteFriend: notify get called");
		
		String group = null;
		try {
			group = param.getString(PARAM_GROUP);
		} catch (Exception e) {
			Tracer.e(TAG, "group is not set");
			callback(onError, null);
			return;
		}
		
		String friend = null;
		try {
			friend = param.getString(PARAM_FRIEND);
		} catch (Exception e) {
			Tracer.e(TAG, "friend is not set");
			callback(onError, null);
			return;
		}
		
		String reason = "";
		try {
			friend = param.getString(PARAM_REASON);
		} catch (Exception e) {
		}
		
		if (mGroupMgr == null) {
			mGroupMgr = GroupManager.getInstance(context);
		}
		
		boolean ret = false;
		if (mGroupMgr != null) {
			ret = mGroupMgr.inviateFriend(group, friend, reason);
		}
		
		if (ret) {
			callback(onSuccess, null);
		} else {
			callback(onError, null);
		}
	}
}
