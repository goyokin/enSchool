package com.jsb.simple;

import org.json.JSONObject;

import android.content.Context;

import com.jsb.chat.GroupManager;
import com.jsb.debug.Tracer;

public class JSBGetGroupInfo extends IJSBInternal {
	
	public JSBGetGroupInfo(JSBImpl jsb) {
		super(jsb);
	}

	private final static String TAG = "JSBGetGroupInfo";
	private final static String PARAM_GROUP = "group";
	private GroupManager mGroupMgr = null;

	@Override
	public void onPageFinished() {
		
	}

	@Override
	public void notify(Context context, JSONObject param, String onSuccess,
					   String onError, String onProgress) {
		Tracer.d(TAG, "JSBGetGroupInfo: notify get called");
		
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
		
		String groupInfo = null;
		if (mGroupMgr != null) {
			groupInfo = mGroupMgr.getGroup(group);
		}
		
		if (groupInfo != null) {
			callback(onSuccess, groupInfo);
		} else {
			callback(onError, null);
		}
	}
}
