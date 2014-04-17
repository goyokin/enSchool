package com.jsb.chat;

import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import android.content.Context;

import com.jsb.debug.Tracer;

public class ChatReceiver implements PacketListener {
	private final static String TAG = "ChatReceiver";
	private static ChatReceiver mInstance = null;
	private Context mContext = null;
	private MessageManager mMessageMgr = null;
	
	private ChatReceiver(Context context) {
		mContext = context;
		mMessageMgr = new MessageManager(mContext);
	}
	
	public static ChatReceiver getInstance(Context context) {
		if (mInstance == null) {
			mInstance = new ChatReceiver(context);
		}
		
		return mInstance;
	}

	@Override
	public void processPacket(Packet packet) {
		Tracer.d(TAG, "packet received : " + packet);
		
		if (packet instanceof Message) {
			final Message msg = (Message) packet;
			mMessageMgr.add(msg);
		} else {
			Tracer.d(TAG, "received non-messages");
		}
	}
}
