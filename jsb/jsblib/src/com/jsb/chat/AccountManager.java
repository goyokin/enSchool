package com.jsb.chat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smackx.Form;
import org.jivesoftware.smackx.ReportedData;
import org.jivesoftware.smackx.ReportedData.Row;
import org.jivesoftware.smackx.ServiceDiscoveryManager;
import org.jivesoftware.smackx.search.UserSearchManager;

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
		
		String tmp = null;
		try {
			tmp = username.substring(0, username.indexOf('@'));
		} catch (Exception e) {
			Tracer.e(TAG, "fail to parse the uesr name", e);
			return false;
		}

		try {
			mCurrentConn.login(tmp, password);
		} catch (Exception e) {
			Tracer.e(TAG, "fail to login", e);
			mStatus = STAT_CONNECTED;
			return false;
		}
		mStatus = STAT_LOGGED_IN;
		Tracer.i(TAG, "user " + tmp + " logged in");
		
		mFullUserName = username;
		mPassword = password;
		mUserName = tmp;
		
		return true;
	}
	
	// Test code for registration
	/* public boolean newUser() {
		if (mCurrentConn == null)  
            return false;
        Registration reg = new Registration();  
        reg.setType(IQ.Type.SET);  
        reg.setTo(getConnection().getServiceName());  
        // no jid  
        reg.setUsername(account);  
        reg.setPassword(password);  
        // addAttribute cannot be null.  
        reg.addAttribute("android", "geolo_createUser_android");  
        PacketFilter filter = new AndFilter(new PacketIDFilter(  
                reg.getPacketID()), new PacketTypeFilter(IQ.class));  
        PacketCollector collector = getConnection().createPacketCollector(  
                filter);  
        getConnection().sendPacket(reg);  
        IQ result = (IQ) collector.nextResult(SmackConfiguration  
                .getPacketReplyTimeout());    
        collector.cancel();  
        if (result == null) {  
            Log.e("regist", "No response from server.");  
            return "0";  
        } else if (result.getType() == IQ.Type.RESULT) {  
            Log.v("regist", "regist success.");  
            return "1";  
        } else { // if (result.getType() == IQ.Type.ERROR)  
            if (result.getError().toString().equalsIgnoreCase("conflict(409)")) {  
                Log.e("regist", "IQ.Type.ERROR: "  
                        + result.getError().toString());  
                return "2";  
            } else {  
                Log.e("regist", "IQ.Type.ERROR: "  
                        + result.getError().toString());  
                return "3";  
            }  
        }  
	} */

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
	
	public boolean deleteAccount() {  
        try {
            mCurrentConn.getAccountManager().deleteAccount();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
	
	public List<HashMap<String, String>> searchUsers(String userName) {  
        HashMap<String, String> user = null;
        List<HashMap<String, String>> results = new ArrayList<HashMap<String, String>>();  
        try {  
            new ServiceDiscoveryManager(getConnection());  
  
            UserSearchManager usm = new UserSearchManager(getConnection());  
  
            Form searchForm = usm.getSearchForm(getConnection()  
                    .getServiceName());  
            Form answerForm = searchForm.createAnswerForm();  
            answerForm.setAnswer("userAccount", true);  
            answerForm.setAnswer("userPhote", userName);  
            ReportedData data = usm.getSearchResults(answerForm, "search"  
                    + getConnection().getServiceName());  
  
            Iterator<Row> it = data.getRows();  
            Row row = null;  
            while (it.hasNext()) {  
                user = new HashMap<String, String>();  
                row = it.next();  
                user.put("userAccount", row.getValues("userAccount").next()  
                        .toString());  
                user.put("userPhote", row.getValues("userPhote").next()  
                        .toString());  
                results.add(user);  
            }  
        } catch (Exception e) {  
            Tracer.e(TAG, "fail to find user");  
        }  
        return results;  
    }
}
