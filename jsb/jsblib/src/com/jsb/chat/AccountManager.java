package com.jsb.chat;

import com.jsb.debug.Tracer;

public class AccountManager {
	
	private final static String TAG = "AccountManager";
	private static AccountManager mInstance = null;
	
	private AccountManager() {
	}
	
	public static AccountManager getInstance() {
		if (mInstance == null) {
			mInstance = new AccountManager();
		}
		
		return mInstance;
	}

	public boolean connect(String username, String password) {
		/*
		 * TODO: connect to backend
		 */
		Tracer.d(TAG, "connect to " + username);
		return true;
	}
	
	public boolean isUserConnected(String username) {
		/*
		 * TODO: maintain connecting status
		 */
		Tracer.d(TAG, "is " + username + " connected");
		return true;
	}
}
