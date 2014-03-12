package com.jsb.simple;

import org.json.JSONObject;

import com.jsb.chat.GroupManager;
import com.jsb.debug.Tracer;

public class JSBGetGroupList extends IJSBInternal {
	
	public JSBGetGroupList(JSBImpl jsb) {
		super(jsb);
	}

	private final static String TAG = "JSBGetGroupList";
	private GroupManager mGroupMgr = null;

	@Override
	public void onPageFinished() {
		
	}

	@Override
	public void notify(JSONObject param, String onSuccess,
			String onError, String onProgress) {
		Tracer.d(TAG, "JSBGetGroupList: notify get called");
		
		if (mGroupMgr == null) {
			mGroupMgr = GroupManager.getInstance();
		}
		
		String groupInfo = null;
		if (mGroupMgr != null) {
			groupInfo = mGroupMgr.getGroupList();
		}
		
		if (groupInfo != null) {
			callback(onSuccess, groupInfo);
		} else {
			callback(onError, null);
		}
	}
}
