package com.jsb.chat;

import com.jsb.debug.Tracer;

public class ChatManager {
	private final static String TAG = "ChatManager";
	private static ChatManager mInstance = null;
	
	private ChatManager() {
	}
	
	public static ChatManager getInstance() {
		if (mInstance == null) {
			mInstance = new ChatManager();
		}
		
		return mInstance;
	}

	public boolean sendMessage(String target, String msg) {
		Tracer.d(TAG, "send msg " + msg + " to " + target);
		return true;
	}
}
