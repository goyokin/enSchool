package com.jsb.simple;

import org.json.JSONObject;

import android.content.Context;

import com.jsb.chat.ChatManager;
import com.jsb.debug.Tracer;
import com.jsb.utils.Config;

public class JSBChatHistory extends IJSBInternal {

	public JSBChatHistory(JSBImpl jsb) {
		super(jsb);
	}
	
	private final static String TAG = "JSBChatHistory";
	private final static String PARAM_FRIEND = "friend";
	private final static String PARAM_GROUP = "group";
	private final static String PARAM_PAGE_INDEX = "pageindex";
	private final static String PARAM_PAGE_SIZE = "pagesize";
	private ChatManager mChatMgr = null;

	@Override
	public void onPageFinished() {
		
	}

	@Override
	public void notify(Context context, JSONObject param, String onSuccess,
					   String onError, String onProgress) {
		String friend = null;
		try {
			friend = param.getString(PARAM_FRIEND);
		} catch (Exception e) {
			Tracer.e(TAG, "friend is not set");
		}
		
		String group = null;
		try {
			group = param.getString(PARAM_GROUP);
		} catch (Exception e) {
			if (friend == null) {
				Tracer.e(TAG, "neither friend nor group is not set");
				callback(onError, null);
				return;
			}
		}
		
		int pageIndex = 0;
		try {
			pageIndex = param.getInt(PARAM_PAGE_INDEX);
		} catch (Exception e) {
		}
		
		int pageSize = Config.getInstance(context).getInt(Config.KEY_HIS_PG_SIZE, Config.DEF_HIS_PG_SIZE);
		try {
			pageSize = param.getInt(PARAM_PAGE_SIZE);
		} catch (Exception e) {
		}
		
		String ch = null;
		if (mChatMgr == null)
			mChatMgr = ChatManager.getInstance(context);
		if (mChatMgr != null) {
			if (friend != null) {
				ch = mChatMgr.getFriendChatHistory(friend, pageIndex, pageSize);
			} else if (group != null) {
				ch = mChatMgr.getGroupChatHistory(group, pageIndex, pageSize);
			} else {
				callback(onError, null);
				return;
			}
		}
		
		if (ch != null) {
			callback(onSuccess, ch);
		} else {
			callback(onError, null);
		}
	}
}
