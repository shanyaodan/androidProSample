package com.example.sampleandroid.util;

import android.util.Log;

public class L {

	public static boolean mode_for_release = true;

	public static void v(Object tag, Object type, Object msg) {
		if (!mode_for_release) {
			String des = String.format("[%s][%s]%s",
					null == tag ? "" : tag.toString(),
					null == type ? "" : type.toString(),
					null == msg ? "" : msg.toString());
			Log.v(null == tag ? "" : tag.toString(), des);
		}
	}

	public static void i(Object tag, Object type, Object msg) {
		if (!mode_for_release) {
			String des = String.format("[%s][%s]%s",
					null == tag ? "" : tag.toString(),
					null == type ? "" : type.toString(),
					null == msg ? "" : msg.toString());
			Log.v(null == tag ? "" : tag.toString(), des);
		}
	}

	public static void i(Object tag, Object msg) {
		if (!mode_for_release) {
			String des = String.format("[%s][%s]%s",
					null == tag ? "" : tag.toString(),

					null == msg ? "" : msg.toString());
			Log.v(null == tag ? "" : tag.toString(), des);
		}
	}

	public static void e(Object tag, Object type, Object msg) {
		if (!mode_for_release) {
			String des = String.format("[%s][%s]%s",
					null == tag ? "" : tag.toString(),
					null == type ? "" : type.toString(),
					null == msg ? "" : msg.toString());
			Log.e(null == tag ? "" : tag.toString(), des);
		}
	}

}
