package com.jsb.chat;

import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.XMPPConnection;

import android.content.Context;

import com.jsb.debug.Tracer;
import com.jsb.utils.Config;

public class AccountManager {
	
	private final static String TAG = "AccountManager";
	private static AccountManager mInstance = null;
	private Connection mCurrentConn = null;
	private Context mContext = null;
	
	private final static int STAT_NOT_LOGIN = 0;
	private final static int STAT_LOGGED_IN = 1;
	private final static int STAT_CONNECTED = 2;
	
	private int mStatus = STAT_NOT_LOGIN;
	
	private String mFullUserName = null;
	private String mPassword = null;
	private String mHost = null;
	private String mUserName = null;
	private String mResource = null;
	
	private AccountManager(Context context) {
		mContext = context;
	}
	
	public static AccountManager getInstance(Context context) {
		if (mInstance == null) {
			mInstance = new AccountManager(context);
		}
		
		return mInstance;
	}

	public boolean connect(String username, String password) {
		Tracer.d(TAG, "connect to " + username);
		
		String host = null;
		String resource = null;
		
		if (mCurrentConn == null || !mCurrentConn.isConnected()) {
			try {
				host = username.substring(username.indexOf('@') + 1, username.indexOf('/'));
			} catch (Exception e) {
				Tracer.e(TAG, "fail to get host from user name", e);
				host = null;
			}
			try {
				if (host == null) {
					host = Config.getInstance(mContext).getString(Config.KEY_HOST_NAME, Config.DEF_HOST);
				}
				mCurrentConn = new XMPPConnection(host);
			} catch (Exception e) {
				Tracer.e(TAG, "fail to new xmpp connection");
				mStatus = STAT_NOT_LOGIN;
				return false;
			}
			
			mHost = host;
			
			try {
				resource = username.substring(username.indexOf('/') + 1);
			} catch (Exception e) {
				Tracer.e(TAG, "fail to parse resource name", e);
				resource = null;
			}
			
			if (resource == null) {
				resource = Config.getInstance(mContext).getString(Config.KEY_RES_NAME, Config.DEF_RES);
			}
			
			mResource = resource;
			
			try {
				mCurrentConn.connect();
			} catch (Exception e) {
				mCurrentConn = null; // reset status
				Tracer.e(TAG, "fail to connect to server", e);
				mStatus = STAT_NOT_LOGIN;
				return false;
			}
			
			try {
				mCurrentConn.addPacketListener(ChatReceiver.getInstance(mContext), null);
			} catch (Exception e) {
				Tracer.e(TAG, "fail to set listener", e);
				mCurrentConn.disconnect();
				mCurrentConn = null; // reset status
				mStatus = STAT_NOT_LOGIN;
				return false;
			}
			
			mStatus = STAT_CONNECTED;
		}
		
		try {
			mCurrentConn.login(username, password);
		} catch (Exception e) {
			Tracer.e(TAG, "fail to login");
			mStatus = STAT_CONNECTED;
			return false;
		}
		mStatus = STAT_LOGGED_IN;
		
		mFullUserName = username;
		mPassword = password;
		try {
			mUserName = username.substring(0, username.indexOf('@'));
		} catch (Exception e) {
			Tracer.w(TAG, "fail to parse the uesr name");
		}
		
		return true;
	}

	public boolean isUserConnected(String username) {
		return (mStatus == STAT_LOGGED_IN && username.compareTo(mFullUserName) == 0);
	}
	
	public boolean isUserConnected() {
		return (mStatus == STAT_LOGGED_IN);
	}
	
	public Connection getConnection() {
		return mCurrentConn;
	}
	
	public String getUser() {
		return mUserName;
	}
	
	public String getFullUserName() {
		return mFullUserName;
	}
	
	public String getHost() {
		return mHost;
	}
	
	public String getPassword() {
		return mPassword;
	}
	
	public String getResource() {
		return mResource;
	}
}
