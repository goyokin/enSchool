package com.jsb.chat;

import java.util.Iterator;
import java.util.LinkedList;

import org.jivesoftware.smack.packet.Message;

import com.jsb.debug.Tracer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class MessageManager implements Iterable<ChatMessage> {
	private final static String TAG = "MessageManager";
	private Context mContext = null;
	private MessageStorage mStorage = null;
	
	private Object mDbLock = new Object();
	
	private class MessageStorage extends SQLiteOpenHelper {
		public static final String TABLE_NAME = "messages";
        public static final String COLUMN_NAME_TEXT = "text"; 
        public static final String COLUMN_NAME_TIME = "timestamp";
        public static final String COLUMN_NAME_READ = "read";

		private static final String TEXT_TYPE = " TEXT";
		private static final String COMMA_SEP = ",";
		private static final String SQL_CREATE_ENTRIES =
		    "CREATE TABLE " + TABLE_NAME + " (" +
		    BaseColumns._ID + " INTEGER PRIMARY KEY," +
		    COLUMN_NAME_TEXT + TEXT_TYPE + COMMA_SEP +
		    COLUMN_NAME_TIME + " DATETIME DEFAULT CURRENT_TIMESTAMP " + COMMA_SEP + 
		    COLUMN_NAME_READ + " INTEGER" +
		    " )";

		private static final String SQL_DELETE_ENTRIES =
		    "DROP TABLE IF EXISTS " + TABLE_NAME;


	    public static final int DATABASE_VERSION = 1;
	    public static final String DATABASE_NAME = "jsb.message.db";

	    public MessageStorage(Context context) {
	        super(context, DATABASE_NAME, null, DATABASE_VERSION);
	    }
	    public void onCreate(SQLiteDatabase db) {
	        db.execSQL(SQL_CREATE_ENTRIES);
	    }
	    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	        db.execSQL(SQL_DELETE_ENTRIES);
	        onCreate(db);
	    }
	    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	        onUpgrade(db, oldVersion, newVersion);
	    }
	}
	
	public MessageManager(Context context) {
		mContext = context;
		mStorage = new MessageStorage(mContext);
	}
	
	public void add(Message msg) {
		SQLiteDatabase db = null;

		synchronized(mDbLock) {
			try {
				db = mStorage.getWritableDatabase();
				ContentValues values = new ContentValues();
				values.put(MessageStorage.COLUMN_NAME_TEXT, msg.getBody());
				db.beginTransaction();
				db.insert(MessageStorage.TABLE_NAME, null, values);
				db.endTransaction();
			} catch (Exception e) {
				Tracer.e(TAG, "add msg fail", e);
			} finally {
				try { db.close(); } catch (Exception e) {}
			}
		}
	}

	@Override
	public Iterator<ChatMessage> iterator() {
		LinkedList<ChatMessage> list = new LinkedList<ChatMessage>();
		list.clear();
		
		String text = null;
		int readFlag = ChatMessage.FLAG_NOT_READ;
		String timeStamp = null;
		SQLiteDatabase db = null;
		
		synchronized(mDbLock) {

			try {
				db = mStorage.getReadableDatabase();
				String[] projection = { BaseColumns._ID, MessageStorage.COLUMN_NAME_TEXT,
										MessageStorage.COLUMN_NAME_TIME,
										MessageStorage.COLUMN_NAME_READ };
		
				String sortOrder = MessageStorage.COLUMN_NAME_TIME + " DESC";
		
				Cursor c = db.query(MessageStorage.TABLE_NAME, projection, null, null,
									null, null, sortOrder);
				c.moveToFirst();
				int n = c.getCount(), id = 0;
				for (int i = 0; i < n; i++) {
					text = c.getString(c.getColumnIndexOrThrow(MessageStorage.COLUMN_NAME_TEXT));
					readFlag = c.getInt(c.getColumnIndexOrThrow(MessageStorage.COLUMN_NAME_READ));
					timeStamp = c.getString(c.getColumnIndexOrThrow(MessageStorage.COLUMN_NAME_TIME));
					id = c.getInt(c.getColumnIndexOrThrow(BaseColumns._ID));
					list.add(new ChatMessage(text, readFlag, timeStamp, id));
					c.moveToNext();
				}
			} catch (Exception e) {
				Tracer.e(TAG, "fail to get msg", e);
				return null;
			} finally {
				try { db.close(); } catch (Exception e) {}
			}
		}
		
		return list.iterator();
	}
}
