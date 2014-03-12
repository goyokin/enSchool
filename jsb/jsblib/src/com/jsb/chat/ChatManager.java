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
	
	public String getFriendChatHistory(String friend, int pageIndex) {
		Tracer.d(TAG, "get chat history for " + friend + " on page " + pageIndex);
		return "";
	}
	
	public String getGroupChatHistory(String group, int pageIndex) {
		Tracer.d(TAG, "get chat history for " + group + " on page " + pageIndex);
		return "";
	}
	
	public boolean sendFriendFile(String friend, String filePath) {
		Tracer.d(TAG, "send " + friend + " " + filePath);
		return true;
	}
	
	public boolean sendGroupFile(String group, String filePath) {
		Tracer.d(TAG, "send " + group + " " + filePath);
		return true;
	}
	
	public boolean sendFriendAudio(String friend, String audio) {
		Tracer.d(TAG, "send " + friend + " " + audio);
		return true;
	}
	
	public boolean sendGroupAudio(String group, String audio) {
		Tracer.d(TAG, "send " + group + " " + audio);
		return true;
	}
}
