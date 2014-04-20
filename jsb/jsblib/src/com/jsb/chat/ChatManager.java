package com.jsb.chat;

import java.util.ArrayList;

import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.packet.Message;
import android.content.Context;

import com.jsb.debug.Tracer;

public class ChatManager {
	private final static String TAG = "ChatManager";
	private static ChatManager mInstance = null;
	private Context mContext = null;
	
	private ChatManager(Context context) {
		mContext = context;
	}
	
	public static ChatManager getInstance(Context context) {
		if (mInstance == null) {
			mInstance = new ChatManager(context);
		}
		
		return mInstance;
	}

	public boolean sendMessage(String target, String msg) {
		
		AccountManager am = AccountManager.getInstance(mContext);
		if (!am.isUserConnected()) {
			Tracer.e(TAG, "user has not connected yet");
			return false;
		}
		Connection conn = am.getConnection();
		if (conn == null) {
			Tracer.e(TAG, "connection not found");
			// TODO: should we reconnect here
			return false;
		}
		
		GroupManager gm = GroupManager.getInstance(mContext);
		GroupManager.GroupRecord r = gm.getGroup(target);
		
		try {
			msg = msg.replaceAll("[\\u0001-\\u0008\\u000B-\\u001F]", "");
			if (r == null) { // send to a firend 
				Message message = new Message();
				message.setBody(msg);
				message.setType(Message.Type.chat);
				// assuming talking within the same host
				message.setTo(target + "@" + am.getHost() + "/" + am.getResource());
				message.setFrom(am.getFullUserName());
				conn.sendPacket(message);
			} else {
				r.mMuc.sendMessage(msg);
			}
			Tracer.d(TAG, "sent msg " + msg + " to " + target);
		} catch (Exception e) {
			Tracer.e(TAG, "fail to send message", e);
			return false;
		}
		return true;
	}
	
	public String getFriendChatHistory(String friend, int pageIndex, int pageSize) {
		Tracer.d(TAG, "get chat history for " + friend + " on page " + pageIndex);
		return "";
	}
	
	public String getGroupChatHistory(String group, int pageIndex, int pageSize) {
		Tracer.d(TAG, "get chat history for " + group + " on page " + pageIndex);
		
		GroupManager gm = GroupManager.getInstance(mContext);
		ArrayList<Message> history = gm.getChatHistory(group);
		
		if (history != null) {
			String messages = "";
			
			for (int i = 0; i < history.size(); i++) {
				messages += history.get(i).getBody() + ":";
			}
			
			Tracer.d(TAG, "messages = " + messages);
			
			return messages;
		}
		Tracer.d(TAG, "messages = ");
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
