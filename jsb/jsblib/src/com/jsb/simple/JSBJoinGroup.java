package com.jsb.simple;

import org.json.JSONObject;

import android.content.Context;

import com.jsb.chat.AccountManager;
import com.jsb.chat.GroupManager;
import com.jsb.debug.Tracer;

public class JSBJoinGroup extends JSBChangeGroup {
	
	public JSBJoinGroup(JSBImpl jsb) {
		super(jsb);
	}
	
	private final static String TAG = "JSBJoinGroup";
	private final static String PARAM_NICK_NAME = "nickName";
	private final static String PARAM_GROUP = "group";
	private GroupManager mGroupMgr = null;

	@Override
	public void onPageFinished() {
		
	}

	@Override
	public void notify(Context context, JSONObject param, String onSuccess,
					   String onError, String onProgress) {
		Tracer.d(TAG, "JSBAddFriend: notify get called");
		
		String group = null;
		try {
			group = param.getString(PARAM_GROUP);
		} catch (Exception e) {
			Tracer.e(TAG, "group is not set");
			callback(onError, null);
			return;
		}
		
		String nickName = null;
		try {
			nickName = param.getString(PARAM_NICK_NAME);
		} catch (Exception e) {
			// use user name instead if nick name is not set
			AccountManager am = AccountManager.getInstance(context);
			nickName = am.getUser();
		}
		
		if (mGroupMgr == null) {
			mGroupMgr = GroupManager.getInstance(context);
		}
		
		boolean success = false;
		if (mGroupMgr != null) {
			success = mGroupMgr.joinGroup(nickName, group);
		}
		
		if (success) {
			callback(onSuccess, null);
		} else {
			callback(onError, null);
		}
	}
}
