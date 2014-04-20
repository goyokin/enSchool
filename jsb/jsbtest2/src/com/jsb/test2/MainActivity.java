package com.jsb.test2;

import com.jsb.simple.JSBImpl;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends JSBImpl {
	@Override
	protected String getUrl() {
		return "file:///android_asset/index.html";
	}
}
