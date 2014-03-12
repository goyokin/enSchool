package com.jsb.chat;

import org.json.JSONObject;

import com.jsb.debug.Tracer;

public class GroupManager {
	private final static String TAG = "GroupManager";
	private static GroupManager mInstance = null;
	
	private GroupManager() {
	}
	
	public static GroupManager getInstance() {
		if (mInstance == null) {
			mInstance = new GroupManager();
		}
		
		return mInstance;
	}

	public boolean addUser(String group, String user) {
		Tracer.d(TAG, "add " + user + " to group " + group);
		return true;
	}
	
	public boolean removeUser(String group, String user) {
		Tracer.d(TAG, "remove " + user + " from group " + group);
		return true;
	}
	
	public boolean updateGroup(String group, String groupInfo) {
		Tracer.d(TAG, "update group " + group);
		return true;
	}
	
	public boolean removeGroup(String group) {
		Tracer.d(TAG, "remove group " + group);
		return true;
	}
	
	public String getUser(String user) {
		Tracer.d(TAG, "get user info for " + user);
		return "";
	}
	
	public String getGroup(String group) {
		Tracer.d(TAG, "get group info for " + group);
		return "";
	}
	
	public String getGroupList() {
		Tracer.d(TAG, "get group list");
		return "";
	}
}
