package com.jsb.simple;

import org.json.JSONObject;

public interface IJSBInternal {

	public void notify(JSONObject param, String onSuccess, String onError, String onProgress);
	
	public void onPageFinished();
}
