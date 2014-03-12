package com.jsb.simple;

import org.json.JSONObject;

import com.jsb.chat.ChatManager;
import com.jsb.debug.Tracer;

public class JSBSendAudio extends IJSBInternal {

	public JSBSendAudio(JSBImpl jsb) {
		super(jsb);
	}
	
	private final static String TAG = "JSBSendAudio";
	private final static String PARAM_AUDIO_NAME = "audioname";
	private final static String PARAM_GROUP = "group";
	private final static String PARAM_FRIEND = "friend";
	private ChatManager mChatMgr = null;

	@Override
	public void onPageFinished() {
		
	}

	@Override
	public void notify(JSONObject param, String onSuccess,
			String onError, String onProgress) {
		Tracer.d(TAG, "JSBSendAudio: notify get called");
		
		String audio = null;
		try {
			audio = param.getString(PARAM_AUDIO_NAME);
		} catch (Exception e) {
			Tracer.e(TAG, "audio is not set");
			callback(onError, null);
			return;
		}
		
		String group = null;
		try {
			group = param.getString(PARAM_GROUP);
		} catch (Exception e) {
		}
		
		String friend = null;
		try {
			friend = param.getString(PARAM_FRIEND);
		} catch (Exception e) {
		}
		
		if (mChatMgr == null) {
			mChatMgr = ChatManager.getInstance();
		}
		
		boolean success = false;
		if (mChatMgr != null) {
			if (friend != null) {
				success = mChatMgr.sendFriendAudio(friend, audio);
			} else if (group != null) {
				success = mChatMgr.sendGroupAudio(group, audio);
			}
		}
		
		if (success) {
			callback(onSuccess, null);
		} else {
			callback(onError, null);
		}
	}
}