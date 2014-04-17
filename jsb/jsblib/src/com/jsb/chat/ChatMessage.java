package com.jsb.chat;

public class ChatMessage {
	public final static int FLAG_READ = 0;
	public final static int FLAG_NOT_READ = 1;

	public String mText = null;
	public int mReadFlag = FLAG_READ;
	public String mTimeStamp = null;
	public int mId = 0;
	
	public ChatMessage(String text, int flag, String timeStamp, int id) {
		mText = text;
		mReadFlag = flag;
		mTimeStamp = timeStamp;
		mId = id;
	}
}
