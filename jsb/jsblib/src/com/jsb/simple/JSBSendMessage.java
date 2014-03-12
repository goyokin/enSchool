package com.jsb.simple;

import org.json.JSONObject;

import com.jsb.chat.ChatManager;
import com.jsb.debug.Tracer;

public class JSBSendMessage extends IJSBInternal {

	public JSBSendMessage(JSBImpl jsb) {
		super(jsb);
	}
	
	private final static String TAG = "JSBSendMessage";
	private final static String PARAM_MESSAGE = "msg";
	private final static String PARAM_TARGET = "target";
	private ChatManager mChatMgr = null;

	@Override
	public void onPageFinished() {
		
	}

	@Override
	public void notify(JSONObject param, String onSuccess,
						String onError, String onProgress) {
		String target = null;
		try {
			target = param.getString(PARAM_TARGET);
		} catch (Exception e) {
			Tracer.w(TAG, "fail to get target for chat", e);
			return;
		}

		String msg = null;
		try {
			msg = param.getString(PARAM_MESSAGE);
		} catch (Exception e) {
			Tracer.w(TAG, "fail to get msg", e);
			return;
		}
		
		boolean success = false;
		if (mChatMgr == null)
			mChatMgr = ChatManager.getInstance();
		if (mChatMgr != null) {
			success = mChatMgr.sendMessage(target, msg);
		}
		
		if (success) {
			callback(onSuccess, null);
		} else {
			callback(onError, null);
		}
	}
}