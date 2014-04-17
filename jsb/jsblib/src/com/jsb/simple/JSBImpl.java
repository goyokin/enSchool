package com.jsb.simple;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.jsb.debug.Tracer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class JSBImpl extends Activity implements IJSB {

	private final static String TAG = "JSBImpl";
	private WebView mWebView;
	private final static String MAIN_SCREEN_URL = "http://www.mypage.com";
	
	private Object mJSBObjCacheLock = new Object();
	private ArrayList<JSBObj> mJSBObjCache = new ArrayList<JSBObj>();
	
	@SuppressLint("SetJavaScriptEnabled")
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
				
		setContentView(R.layout.jsb_screen);

		mWebView = (WebView) findViewById(R.id.jsb_web_view);
		mWebView.setNetworkAvailable(true);
		mWebView.setAlwaysDrawnWithCacheEnabled(true);
		mWebView.getSettings().setLoadsImagesAutomatically(true);
		mWebView.getSettings().setBuiltInZoomControls(true);
		mWebView.getSettings().setSupportZoom(true);
		mWebView.setVerticalScrollBarEnabled(false);
		mWebView.setHorizontalScrollBarEnabled(false);
		mWebView.getSettings().setDatabaseEnabled(true);
		mWebView.getSettings().setDomStorageEnabled(true);
		mWebView.getSettings().setJavaScriptEnabled(true);
		
		if (android.os.Build.VERSION.SDK_INT > 6) {
			mWebView.getSettings().setLoadWithOverviewMode(true);
			mWebView.getSettings().setAppCacheEnabled(true);
		}

		if (android.os.Build.VERSION.SDK_INT > 4) {
			mWebView.setScrollbarFadingEnabled(false);
			mWebView.getSettings().setDatabaseEnabled(true);
		}

		mWebView.setWebChromeClient(new WebCromeClientImpl());
		mWebView.setWebViewClient(new WebViewClientImpl());
		
		mWebView.addJavascriptInterface(this, "JSBImpl");

		mWebView.loadUrl(getUrl());
		mWebView.setVisibility(View.VISIBLE);
	}
	
	protected String getUrl() {
		return MAIN_SCREEN_URL;
	}
	
	public class WebCromeClientImpl extends WebChromeClient {

		public void onProgressChanged(WebView view, int progress) {
		}

		public boolean onJsAlert(WebView view, String url, String message,
				JsResult result) {
			return false;
		}

		public void onReceivedTitle(WebView view, String title) {
		}
	}

	public class WebViewClientImpl extends WebViewClient {

		@Override
		public void onReceivedError(WebView view, int errorCode,
				String description, String failingUrl) {
		}

		public void onPageFinished(WebView view, String url) {
			JSBImpl.this.onPageFinished();
		}

		public void onReceivedSslError(WebView view, SslErrorHandler handler,
				SslError error) {
		}
	}
	
	public void callJSMethod(final String jsMethod, final String param) {
		JSBImpl.this.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				try {
					if (param != null && param.length() > 0)
						mWebView.loadUrl("javascript:" + jsMethod  + "('" + param + "');");
					else
						mWebView.loadUrl("javascript:" + jsMethod  + "();");
				} catch (Exception e) {
					Tracer.e(TAG, "fail to call " + jsMethod, e);
				}
			}
		});
	}
	
	public void callJSMethod(final String jsMethod) {
		
		callJSMethod(jsMethod, null);
	}

	@Override
	@android.webkit.JavascriptInterface
	public void notify(String jsonStr) {
		JSONObject jobj = null;
		try {
			jobj = new JSONObject(jsonStr);
		} catch (JSONException e) {
			Tracer.e(TAG, "fail to notify", e);
			return;
		}
		
		String clsName = null;
		try {
			clsName = jobj.getString(KEY_CLASS_NAME);
		} catch (JSONException e) {
			Tracer.e(TAG, "fail to get class name", e);
			return;
		}
		
		JSBObj jsbObj = jsbAdd(jobj, clsName);
		if (jsbObj != null) {
			jsbObj.call(jobj);
		}
	}
	
	private class JSBObj {
		public Class<?> mClass;
		private ArrayList<Method> mMethodTable = new ArrayList<Method>();
		private Object mObj = null;
		
		public JSBObj(JSONObject jsonObj) {
			
			String clsName = null;
			try {
				clsName = jsonObj.getString(KEY_CLASS_NAME);
			} catch (JSONException e) {
				Tracer.e(TAG, "fail to get class name", e);
				return;
			}
			
			Class<?> cls = null;
			try {
				cls = Class.forName(clsName);
			} catch (Exception e) {
				Tracer.e(TAG, "class not found" + clsName, e);
				return;
			}

			mClass = cls;
		}
		
		public void call(JSONObject jsonObj) {
			
			if (mClass == null)
				return;
			
			String method = null;
			try {
				method = jsonObj.getString(KEY_METHOD_NAME);
			} catch (Exception e) {
				Tracer.e(TAG, "fail to get method", e);
				return;
			}
			
			Method func = null;
			int i, numMethods = mMethodTable.size();
			for (i = 0; i < numMethods; i++) {
				func = mMethodTable.get(i); 
				if (func.getName().matches(method))
					break;
			}
			
			if (i >= numMethods) {
				try {
					// func(Context context, JSONObject param, String onSuccess, String onError, String onProgress)
					func = mClass.getMethod(method, Context.class, JSONObject.class, String.class,
											String.class, String.class);
				} catch (Exception e) {
					Tracer.e(TAG, "method not found" + mClass.getName() + ":" + method, e);
					return;
				}
			}
			
			JSONObject param = null;
			try {
				param = jsonObj.getJSONObject(KEY_PARAM);
			} catch (Exception e) {
				// Tracer.w(TAG, "fail to get param", e);
			}
			
			String onSuccess = null;
			try {
				onSuccess = jsonObj.getString(KEY_ON_SUCCESS);
			} catch (Exception e) {
				// Tracer.w(TAG, "fail to get onSuccess", e);
			}
			
			String onError = null;
			try {
				onError = jsonObj.getString(KEY_ON_ERROR);
			} catch (Exception e) {
				// Tracer.w(TAG, "fail to get onError", e);
			}
			
			String onProgress = null;
			try {
				onProgress = jsonObj.getString(KEY_ON_PROGRESS);
			} catch (Exception e) {
				// Tracer.w(TAG, "fail to get onProgress", e);
			}

			try {
				if (mObj == null) {
					Constructor<?> ctor = mClass.getConstructor(JSBImpl.class);
					mObj = ctor.newInstance(JSBImpl.this);
				}
				func.invoke(mObj, JSBImpl.this.getApplicationContext(), param, onSuccess, onError, onProgress);
			} catch (Exception e) {
				Tracer.e(TAG, "fail to call " + mClass.getSimpleName() + ":" + func.getName(), e);
			}
		}
	}
	
	private JSBObj jsbFind(String clsName) {
		synchronized(mJSBObjCacheLock) {
			for (int i = 0; i < mJSBObjCache.size(); i++) {
				JSBObj obj = mJSBObjCache.get(i); 
				if (obj.mClass.getName().matches(clsName)) {
					return obj; 
				}
			}
		}
		
		return null;
	}
	
	private JSBObj jsbAdd(JSONObject jsonObj, String clsName) {
		JSBObj jsbObj = null;

		try {
			jsbObj = jsbFind(clsName);
			if (jsbObj == null) {
				synchronized(mJSBObjCacheLock) {
					jsbObj = new JSBObj(jsonObj);
					if (jsbObj != null)
						mJSBObjCache.add(jsbObj);
				}
			}
		} catch (Exception e) {
		}
		
		return jsbObj;
	}

	@Override
	@android.webkit.JavascriptInterface
	public void onPageFinished() {
		synchronized(mJSBObjCacheLock) {
			for (int i = 0; i < mJSBObjCache.size(); i++) {
				JSBObj obj = mJSBObjCache.get(i);
				try { obj.call(new JSONObject("{\"method\":\"onPageFinished\",}")); } catch (Exception e) {}
			}
		}
	}
}
