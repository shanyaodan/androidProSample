package com.example.sampleandroid;

import android.app.Application;
import android.content.Context;

import com.example.sampleandroid.exception.CrashHandler;

public class App extends Application {

	public static Context sContext;

	@Override
	public void onCreate() {
		super.onCreate();
		sContext = getApplicationContext();
		initData();
		// 监听奔溃

		CrashHandler.getInstance().init(this);
		// 获取屏幕宽高
		init();
	}

	public static Context getContext() {
		return sContext;
	}

	/**
	 * 初始化
	 */
	private void init() {

	}

	private void initData() {

	}

}
