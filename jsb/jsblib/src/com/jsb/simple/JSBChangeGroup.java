package com.jsb.simple;

import org.json.JSONObject;

import android.content.Context;

import com.jsb.chat.GroupManager;
import com.jsb.debug.Tracer;

public class JSBChangeGroup extends IJSBInternal {
	
	public JSBChangeGroup(JSBImpl jsb) {
		super(jsb);
	}

	private final static String TAG = "JSBChangeGroup";
	private final static String PARAM_GROUP_INFO = "groupinfo";
	private final static String PARAM_GROUP = "group";
	private GroupManager mGroupMgr = null;

	@Override
	public void onPageFinished() {
		
	}

	@Override
	public void notify(Context context, JSONObject param, String onSuccess,
					   String onError, String onProgress) {
		Tracer.d(TAG, "JSBChangeGroup: notify get called");
		
		String groupInfo = null;
		try {
			groupInfo = param.getString(PARAM_GROUP_INFO);
		} catch (Exception e) {
			Tracer.e(TAG, "group info is not set");
			callback(onError, null);
			return;
		}
		
		String group = null;
		try {
			group = param.getString(PARAM_GROUP);
		} catch (Exception e) {
			Tracer.e(TAG, "group is not set");
			callback(onError, null);
			return;
		}
		
		if (mGroupMgr == null) {
			mGroupMgr = GroupManager.getInstance(context);
		}
		
		boolean success = false;
		if (mGroupMgr != null) {
			success = mGroupMgr.updateGroup(group, groupInfo);
		}
		
		if (success) {
			callback(onSuccess, null);
		} else {
			callback(onError, null);
		}
	}
}
