package com.sfs.zbar.tools;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.sfs.zbar.widget.ToastView;


public class Toaster {

	private static Toast mToast;
	private static Context mCtx;
	private final static int DEFAULT_BOTTOM = -1;

	private Toaster() {
	}

	@SuppressLint("ShowToast")
	public synchronized static void init(Application ctx) {
		mCtx = ctx;
		initToast(ctx);
	}

	private static void initToast(Context ctx) {
		if (mToast == null) {
			mToast = new Toast(ctx);
			mToast.setView(new ToastView(ctx));
		}
	}

	public static void show(int resId) {
		initToast(mCtx);
		show(mCtx.getResources().getString(resId));
	}

	public static void show(final CharSequence msg) {
		if (TextUtils.isEmpty(msg))
			return;
		initToast(mCtx);
		((ToastView) (mToast.getView())).setText(msg);
		mToast.setDuration(Toast.LENGTH_SHORT);
		mToast.show();
	}

	private static void resetGravity(int gravity) {
		if (gravity != DEFAULT_BOTTOM) {
			mToast.setGravity(gravity, 0, 0);
		}
	}

	public static void showPost(final CharSequence msg) {
		initToast(mCtx);

		mToast.getView().post(new XuRunnable(msg));
	}

	public static void show(final CharSequence msg, int delay) {
		initToast(mCtx);
		mToast.getView().postDelayed(new XuRunnable(msg), delay);
	}

	public static void showWithGravity(int resId, int gravity) {
		initToast(mCtx);
		resetGravity(gravity);
		showWithGravity(mCtx.getString(resId), 0, gravity);
	}

	public static void showWithGravity(final CharSequence msg, int gravity) {
		if (TextUtils.isEmpty(msg))
			return;
		showWithGravity(msg, 0, gravity);
	}

	public static void showPostWithGravity(final CharSequence msg, int gravity) {
		initToast(mCtx);
		resetGravity(gravity);
		mToast.getView().post(new XuRunnable(msg));
	}

	public static void showWithGravity(final CharSequence msg, int delay,
			int gravity) {
		initToast(mCtx);
		resetGravity(gravity);
		mToast.getView().postDelayed(new XuRunnable(msg), delay);
	}

	private static class XuRunnable implements Runnable {

		private CharSequence _msg;

		public XuRunnable(CharSequence msg) {
			_msg = msg;
		}

		@Override
		public void run() {
			// mToast.cancel();
			((ToastView) (mToast.getView())).setText(_msg);
			mToast.setDuration(Toast.LENGTH_SHORT);
			mToast.show();
		}

	}
}
