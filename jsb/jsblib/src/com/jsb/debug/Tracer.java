package com.jsb.debug;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import android.os.Environment;
import android.util.Log;

public class Tracer {

	private final static String LOG_FILE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/jsblog.txt";
	private final static int MAX_LOG_FILE_SIZE = 1024 * 1024 * 16;
	private final static boolean DEBUG_FILE = true;
	private final static boolean DEBUG_CONSOLE = true;

    public static final int VERBOSE = Log.VERBOSE;
    public static final int DEBUG = Log.DEBUG;
    public static final int INFO = Log.INFO;
    public static final int WARN = Log.WARN;
    public static final int ERROR = Log.ERROR;
    
    private static void log(String log) {
    	File f = new File(LOG_FILE_PATH);
    	if (f.length() >= MAX_LOG_FILE_SIZE) {
    		f.delete();
    	}
    	try {
	    	FileOutputStream fo = new FileOutputStream(LOG_FILE_PATH, true);
	    	fo.write((log + "\r\n").getBytes());
	    	fo.close();
    	} catch (Exception e) {
    	}
    }
    
    private static void loge(Throwable tr) {
    	PrintWriter pw = null;
		try {
			pw = new PrintWriter(new FileWriter(LOG_FILE_PATH, true));
		} catch (IOException e) {
		}
		
		if (pw != null) {
			pw.write(tr.toString());
			pw.flush();
			
			pw.close();
		}
    }

    public static void v (String tag, String msg) {
    	if (DEBUG_CONSOLE) {
    		Log.v(tag, msg);
    	}
    	
    	if (DEBUG_FILE) {
    		log("[" + tag + "]" + msg);
    	}
    }

    public static void v (String tag, String msg, Throwable tr) {
    	if (DEBUG_CONSOLE) {
    		Log.v(tag, msg, tr);
    	}
    	
    	if (DEBUG_FILE) {
    		log("[" + tag + "]" + msg);
    		loge(tr);
    	}
    }

    public static void d (String tag, String msg) {
    	if (DEBUG_CONSOLE) {
    		Log.d(tag, msg);
    	}
    	
    	if (DEBUG_FILE) {
    		log("[" + tag + "]" + msg);
    	}
    }

    public static void d (String tag, String msg, Throwable tr) {
    	if (DEBUG_CONSOLE) {
    		Log.d(tag, msg, tr);
    	}
    	
    	if (DEBUG_FILE) {
    		log("[" + tag + "]" + msg);
    		loge(tr);
    	}
    }

    public static void i (String tag, String msg) {
    	if (DEBUG_CONSOLE) {
    		Log.i(tag, msg);
    	}
    	
    	if (DEBUG_FILE) {
    		log("[" + tag + "]" + msg);
    	}
    }

    public static void i (String tag, String msg, Throwable tr) {
    	if (DEBUG_CONSOLE) {
    		Log.i(tag, msg, tr);
    	}
    	
    	if (DEBUG_FILE) {
    		log("[" + tag + "]" + msg);
    		loge(tr);
    	}
    }

    public static void w (String tag, String msg) {
    	if (DEBUG_CONSOLE) {
    		Log.w(tag, msg);
    	}
    	
    	if (DEBUG_FILE) {
    		log("[" + tag + "]" + msg);
    	}
    }

    public static void w (String tag, String msg, Throwable tr) {
    	if (DEBUG_CONSOLE) {
    		Log.w(tag, msg, tr);
    	}
    	
    	if (DEBUG_FILE) {
    		log("[" + tag + "]" + msg);
    		loge(tr);
    	}
    }

    public static void e (String tag, String msg) {
    	if (DEBUG_CONSOLE) {
    		Log.e(tag, msg);
    	}
    	
    	if (DEBUG_FILE) {
    		log("[" + tag + "]" + msg);
    	}
    }

    public static void e (String tag, String msg, Throwable tr) {
    	if (DEBUG_CONSOLE) {
    		Log.e(tag, msg, tr);
    	}
    	
    	if (DEBUG_FILE) {
    		log("[" + tag + "]" + msg);
    		loge(tr);
    	}
    }
}
