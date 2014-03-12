package com.jsb.simple;

import org.json.JSONObject;

import com.jsb.chat.GroupManager;
import com.jsb.debug.Tracer;

public class JSBRemoveGroup extends IJSBInternal {
	
	public JSBRemoveGroup(JSBImpl jsb) {
		super(jsb);
	}

	private final static String TAG = "JSBRemoveGroup";
	private final static String PARAM_GROUP = "group";
	private GroupManager mGroupMgr = null;

	@Override
	public void onPageFinished() {
		
	}

	@Override
	public void notify(JSONObject param, String onSuccess,
			String onError, String onProgress) {
		Tracer.d(TAG, "JSBRemoveGroup: notify get called");
		
		String group = null;
		try {
			group = param.getString(PARAM_GROUP);
		} catch (Exception e) {
			Tracer.e(TAG, "group is not set");
			callback(onError, null);
			return;
		}
		
		if (mGroupMgr == null) {
			mGroupMgr = GroupManager.getInstance();
		}
		
		boolean success = false;
		if (mGroupMgr != null) {
			success = mGroupMgr.removeGroup(group);
		}
		
		if (success) {
			callback(onSuccess, null);
		} else {
			callback(onError, null);
		}
	}
}
