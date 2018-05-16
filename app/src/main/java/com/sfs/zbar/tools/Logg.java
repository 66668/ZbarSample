package com.sfs.zbar.tools;


import android.util.Log;

public class Logg {

	private static final String TAG = "JSON";

	private Logg() {
	}

	public static void e(String msg) {
		e(TAG, msg);
	}

	public static void e(String tag, String msg) {
		if (BuildConfig.DEBUG)
			Log.e(TAG, msg);
	}

	public static void v(String msg) {
		v(TAG, msg);
	}

	public static void v(String tag, String msg) {
		if (BuildConfig.DEBUG)
			Log.v(tag, msg);
	}

	public static void d(String msg) {
		d(TAG, msg);
	}

	public static void d(String tag, String msg) {
		if (BuildConfig.DEBUG)
			Log.d(tag, msg);
	}
}
