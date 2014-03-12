package com.jsb.simple;

import java.io.File;

import org.json.JSONObject;

import com.jsb.chat.AccountManager;
import com.jsb.debug.Tracer;

public class JSBReadDir extends IJSBInternal {

	public JSBReadDir(JSBImpl jsb) {
		super(jsb);
	}
	
	private final static String TAG = "JSBReadDir";
	private final static String PARAM_DIR = "dir";
	private final static String DEFAULT_PATH = "/";

	@Override
	public void onPageFinished() {
		
	}

	@Override
	public void notify(JSONObject param, String onSuccess,
			String onError, String onProgress) {
		Tracer.d(TAG, "JSBReadDir:notify get called");

		String dir = null;
		try {
			dir = param.getString(PARAM_DIR);
		} catch (Exception e) {
		}
		
		String list = null;
		if (dir == null)
			dir = DEFAULT_PATH;
		
		File f = new File(dir);
		File[] files = f.listFiles();
		list = "{\"files\":[";
		for (File f1 : files) {
			list += "\"f\":\"" + f1.getName() + "\"";
		}
		list += "]}";
		
		callback(onSuccess, list);
	}
}