package com.jsb.chat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterGroup;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.util.StringUtils;
import org.jivesoftware.smackx.Form;
import org.jivesoftware.smackx.FormField;
import org.jivesoftware.smackx.muc.DiscussionHistory;
import org.jivesoftware.smackx.muc.InvitationRejectionListener;
import org.jivesoftware.smackx.muc.MultiUserChat;
import android.content.Context;
import com.jsb.debug.Tracer;

public class GroupManager implements InvitationRejectionListener, PacketListener {
	private final static String TAG = "GroupManager";
	private static GroupManager mInstance = null;
	private Context mContext = null;
	
	public class GroupRecord {
		public MultiUserChat mMuc = null;
		public String mGroupName = null;
		
		public GroupRecord(String groupName, MultiUserChat muc) {
			mMuc = muc;
			mGroupName = groupName;
		}
	}
	
	ArrayList<GroupRecord> mMyGroups = new ArrayList<GroupRecord>(); 
	
	private GroupManager(Context context) {
		mContext = context;
	}
	
	public static GroupManager getInstance(Context context) {
		if (mInstance == null) {
			mInstance = new GroupManager(context);
		}
		
		return mInstance;
	}
	
	public boolean addGroup(String groupName) {
		
		Iterator<GroupRecord> i = mMyGroups.iterator();
		while (i.hasNext()) {
			GroupRecord r = i.next();
			if (r.mGroupName.compareTo(groupName) == 0) {
				return true;
			}
		}

		AccountManager am = AccountManager.getInstance(mContext);
        if (am == null) {
        	Tracer.e(TAG, "connection not created");
            return false;
        }
        
        Connection conn = am.getConnection();
        if (conn == null) {
        	Tracer.e(TAG, "connection not created");
            return false;
        }
        try {  
        	conn.getRoster().createGroup(groupName);  
            Tracer.v(TAG, "create " + groupName + " success");
            
            // create default room for new group
            MultiUserChat muc = createRoom(groupName, "");
            Tracer.v(TAG, "muc = " + muc);
            muc.sendMessage("Welcome to " + groupName);
            muc.addInvitationRejectionListener(this);
            muc.addMessageListener(this);
            muc.addParticipantListener(this);
            mMyGroups.add(new GroupRecord(groupName, muc));
            
            return true;
        } catch (Exception e) {
            Tracer.e(TAG, "fail to create group", e);
            return false;  
        }
	}

	public boolean addUser(String group, String user, String nickName) {
		AccountManager am = AccountManager.getInstance(mContext);
        if (am == null) {
        	Tracer.e(TAG, "connection not created");
            return false;
        }
        
        Connection conn = am.getConnection();
        if (conn == null) {
        	Tracer.e(TAG, "connection not created");
            return false;
        }
		
        try {  
            Presence subscription = new Presence(Presence.Type.subscribed);  
            subscription.setTo(user);  
            user += "@" + conn.getServiceName();  
            conn.sendPacket(subscription);  
            conn.getRoster().createEntry(user, nickName, new String[] { group });
        } catch (Exception e) {  
            Tracer.e(TAG, "fail to add " + user + " to group " + group, e);  
            return false;  
        } 
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
	
	public GroupRecord getGroup(String group) {
		Tracer.d(TAG, "get group info for " + group);
		
		Iterator<GroupRecord> i = mMyGroups.iterator();
		while (i.hasNext()) {
			GroupRecord r = i.next();
			if (r.mGroupName.compareTo(group) == 0) {
				return r;
			}
		}
		
		return null;
	}
	
	public String getGroupList() {
		List<RosterGroup> groups = getGroups();
		
		Iterator<RosterGroup> i = groups.iterator();
		
		String nameList = "";
		while (i.hasNext()) {
			RosterGroup g = i.next();
			nameList += g.getName() + ":";
		}
		
		return nameList;
	}
	
	public boolean joinGroup(String nickName, String groupName) {
		
		Iterator<GroupRecord> i = mMyGroups.iterator();
		while (i.hasNext()) {
			GroupRecord r = i.next();
			if (r.mGroupName.compareTo(groupName) == 0) {
				return true;
			}
		}
		
		MultiUserChat muc = joinMultiUserChat(nickName, groupName, "");
        Tracer.v(TAG, "muc = " + muc);
        if (muc == null) {
        	Tracer.d(TAG, "user is not authorized to join");
        	return false;
        }
        mMyGroups.add(new GroupRecord(groupName, muc));
        
        return true;
	}
	
	public MultiUserChat joinMultiUserChat(String user, String roomsName,  
            String password) {  
		AccountManager am = AccountManager.getInstance(mContext);
        if (am == null) {
        	Tracer.e(TAG, "connection not created");
            return null;
        }
        
        Connection conn = am.getConnection();
        if (conn == null) {
        	Tracer.e(TAG, "connection not created");
            return null;
        }  
        try {  
            MultiUserChat muc = new MultiUserChat(conn, roomsName + "@conference." + conn.getServiceName());  
            DiscussionHistory history = new DiscussionHistory();  
            history.setMaxChars(0);
            // history.setSince(new Date());
            muc.join(user, password, history,  
                    SmackConfiguration.getPacketReplyTimeout());  
            Tracer.i(TAG, "join success");  
            return muc;  
        } catch (Exception e) {
            Tracer.e(TAG, "fail to join", e);  
            return null;  
        }  
    }  
   
    public List<String> findMulitUser(MultiUserChat muc) {  
    	AccountManager am = AccountManager.getInstance(mContext);
        if (am == null) {
        	Tracer.e(TAG, "connection not created");
            return null;
        }
        
        Connection conn = am.getConnection();
        if (conn == null) {
        	Tracer.e(TAG, "connection not created");
            return null;
        }
        
        List<String> listUser = new ArrayList<String>();  
        Iterator<String> it = muc.getOccupants();  
        while (it.hasNext()) {  
            String name = StringUtils.parseResource(it.next());  
            listUser.add(name);  
        }  
        return listUser;  
    }
	
	private MultiUserChat createRoom(String name, String password) {
  
		AccountManager am = AccountManager.getInstance(mContext);
        if (am == null) {
        	Tracer.e(TAG, "connection not created");
            return null;
        }
        
        Connection conn = am.getConnection();
        if (conn == null) {
        	Tracer.e(TAG, "connection not created");
            return null;
        }

        MultiUserChat muc = null;  
        try {
            muc = new MultiUserChat(conn, name + "@conference." + conn.getServiceName());  
            muc.create(name);
            Form form = muc.getConfigurationForm();    
            Form submitForm = form.createAnswerForm();  
            for (Iterator<FormField> fields = form.getFields(); fields  
                    .hasNext();) {  
                FormField field = (FormField) fields.next();  
                if (!FormField.TYPE_HIDDEN.equals(field.getType())  
                        && field.getVariable() != null) { 
                    submitForm.setDefaultAnswer(field.getVariable());  
                }
            }
            List<String> owners = new ArrayList<String>();  
            owners.add(conn.getUser());
            submitForm.setAnswer("muc#roomconfig_roomowners", owners);  
            submitForm.setAnswer("muc#roomconfig_persistentroom", true);  
            submitForm.setAnswer("muc#roomconfig_membersonly", true);  
            submitForm.setAnswer("muc#roomconfig_allowinvites", true);  
            if (!password.equals("")) {  
                submitForm.setAnswer("muc#roomconfig_passwordprotectedroom", true);
                submitForm.setAnswer("muc#roomconfig_roomsecret", password); 
            }
            // submitForm.setAnswer("muc#roomconfig_whois", "anyone");    
            submitForm.setAnswer("muc#roomconfig_enablelogging", true);  
            submitForm.setAnswer("x-muc#roomconfig_reservednick", true);
            submitForm.setAnswer("x-muc#roomconfig_canchangenick", false);  
            submitForm.setAnswer("x-muc#roomconfig_registration", false);
            muc.sendConfigurationForm(submitForm);
            
            Tracer.d(TAG, "root create request sent");
        } catch (Exception e) {  
            Tracer.e(TAG, "fail to create room", e);  
            return null;  
        }  
        return muc;  
    }
	
	private List<RosterGroup> getGroups() {  
		AccountManager am = AccountManager.getInstance(mContext);
        if (am == null) {
        	Tracer.e(TAG, "connection not created");
            return null;
        }
        
        Connection conn = am.getConnection();
        if (conn == null) {
        	Tracer.e(TAG, "connection not created");
            return null;
        }
        List<RosterGroup> grouplist = new ArrayList<RosterGroup>();  
        Collection<RosterGroup> rosterGroup = conn.getRoster().getGroups();
        Iterator<RosterGroup> i = rosterGroup.iterator();
        while (i.hasNext()) {  
            grouplist.add(i.next());
        }  
        return grouplist;  
    }  

    public List<RosterEntry> getEntriesByGroup(String groupName) {  
    	AccountManager am = AccountManager.getInstance(mContext);
        if (am == null) {
        	Tracer.e(TAG, "connection not created");
            return null;
        }
        
        Connection conn = am.getConnection();
        if (conn == null) {
        	Tracer.e(TAG, "connection not created");
            return null;
        }
        List<RosterEntry> Entrieslist = new ArrayList<RosterEntry>();  
        RosterGroup rosterGroup = conn.getRoster().getGroup(  
                groupName);  
        Collection<RosterEntry> rosterEntry = rosterGroup.getEntries();  
        Iterator<RosterEntry> i = rosterEntry.iterator();  
        while (i.hasNext()) {  
            Entrieslist.add(i.next());  
        }  
        return Entrieslist;  
    }  
  
    public List<RosterEntry> getAllEntries() {  
    	AccountManager am = AccountManager.getInstance(mContext);
        if (am == null) {
        	Tracer.e(TAG, "connection not created");
            return null;
        }
        
        Connection conn = am.getConnection();
        if (conn == null) {
        	Tracer.e(TAG, "connection not created");
            return null;
        } 
        List<RosterEntry> Entrieslist = new ArrayList<RosterEntry>();  
        Collection<RosterEntry> rosterEntry = conn.getRoster().getEntries();  
        Iterator<RosterEntry> i = rosterEntry.iterator();  
        while (i.hasNext()) {  
            Entrieslist.add(i.next());  
        }  
        return Entrieslist;  
    }
    
    public ArrayList<Message> getChatHistory(String group) {
    	Iterator<GroupRecord> i = mMyGroups.iterator();
		while (i.hasNext()) {
			GroupRecord r = i.next();
			if (r.mGroupName.compareTo(group) == 0) {
				Tracer.d(TAG, "found group " + group);
				Message m = null;
				
				ArrayList<Message> history = new ArrayList<Message>();
				do {
					m = r.mMuc.pollMessage();
					if (m != null) {
						history.add(m);
					}
				} while (m != null);
				
				return history;
			}
		}
		
		return null;
    }

	@Override
	public void invitationDeclined(String invitee, String reason) {
		Tracer.d(TAG, "Request has been rejected by " + invitee);
	}

	@Override
	public void processPacket(Packet packet) {
		// TODO: not sure how this different from PacketListener for current connection
		Tracer.d(TAG, "processPacket get called in GroupManager");
	}
	
	public boolean inviateFriend(String group, String friend, String reason) {
		GroupRecord r = null;
		Iterator<GroupRecord> i = mMyGroups.iterator();
		while (i.hasNext()) {
			r = i.next();
			if (r.mGroupName.compareTo(group) == 0) {
				break;
			}
			r = null;
		}
		
		if (r != null) {
			try {
				r.mMuc.invite(friend, reason);
			} catch (Exception e) {
				Tracer.e(TAG, "fail to send invitation to " + friend);
				return false;
			}
			return true;
		}
		
		return false;
	}
}
