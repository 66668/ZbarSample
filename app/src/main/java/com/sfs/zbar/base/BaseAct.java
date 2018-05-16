package com.sfs.zbar.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;

import com.sfs.zbar.R;
import com.sfs.zbar.image.DisplayImageOptions;
import com.sfs.zbar.imgcache.ImageCache.ImageCacheParams;
import com.sfs.zbar.imgcache.ImageFetcher;
import com.sfs.zbar.imgcache.Utils;
import com.sfs.zbar.tools.BuildConfig;
import com.sfs.zbar.tools.Logg;

/**
 * 实现了网络状态接口INetChangListener
 */
public abstract class BaseAct extends FragmentActivity /*
														 * implements
														 * INetChangListener
														 */{
	protected Context context;
	protected static final String TAG = "BaseAct";
	private static final boolean DEBUG = false;
	public static final String IMAGE_CACHE_DIR = "images";
	private static final String DELAY_UPGRADE_TIME = "delay_upgrade_time";
    private DisplayMetrics dm;
    public static float  mDensity;
	private int mImageWidth;
	private int mImageHeight;
	private int mLoadingImgResId;
	protected ImageFetcher mImageFetcher;
	protected DisplayImageOptions mDisplayImageOptions;

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		
		Logg.d("onSaveInstanceState");
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onStart() {
		Logg.d("onStart");
		super.onStart();
		mDisplayImageOptions = new DisplayImageOptions();
		mDisplayImageOptions.showImageOnFail(R.drawable.ic_launcher);
		mDisplayImageOptions.showStubImage(R.drawable.ic_launcher);
	}

	@Override
	protected void onStop() {
		Logg.d("onStop");
		// GetDataManager.getQueue().cancelAll(getTag());
		super.onStop();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Logg.d("onCreate");
		if (BuildConfig.DEBUG) {
			Utils.enableStrictMode();
		}
		// ActivityTaskManager.getInstance().putActivity(this.toString(),
		// this);
		context = this;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		
	      dm = new DisplayMetrics();
			// 获取屏幕信息
	       getWindowManager().getDefaultDisplay().getMetrics(dm);
			mDensity = dm.density;
	}

	protected void checkVersion() {
		// long delayTime = SharePreferUtil.getLong(DELAY_UPGRADE_TIME, 0);
		// if ((System.currentTimeMillis() - delayTime) >= INTERVAL_TIME) {
		// JsonObject jsonObject = new JsonObject();
		// jsonObject.addProperty("key", 1);
		// jsonObject.addProperty("edit", MyApplication.language);
		// VolleyHttpClient.get("Edition", jsonObject, UpgradeCheckVM.class, new
		// CallbackListener<UpgradeCheckVM>() {
		//
		// @Override
		// public void onResponseSuccess(int statusCode,
		// WebApiMessage message, UpgradeCheckVM result) {
		// String version = getVersionName(BaseAct.this);
		// if (TextUtils.isEmpty(result.downloadUrl)) {
		// showHintMsgDlg(result.content);
		// } else {
		// if (result.version != null &&
		// !version.equalsIgnoreCase(result.version)) {
		// showUpgradeDialog(result.content, result.downloadUrl);
		// }
		// }
		// }
		//
		// @Override
		// public void onResponseError(int statusCode,
		// WebApiMessage message, UpgradeCheckVM result) {
		//
		// }
		// }, getTag());
		// }

	}

	/**
	 * 获取软件版本号
	 * 
	 * @param context
	 * @return
	 */
	private String getVersionName(Context context) {
		String versionName = null;
		try {
			// 获取软件版本号，
			versionName = context.getPackageManager().getPackageInfo(
					getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return versionName;
	}

	private void showHintMsgDlg(String content) {
		// mHintDlg = DialogUtil.showNoBtnDialog(this, content);
		// mHintDlg.setOnCancelListener(new OnCancelListener() {
		//
		// @Override
		// public void onCancel(DialogInterface dialog) {
		// BaseAct.this.finish();
		// }
		// });
		// mHintDlg.setCanceledOnTouchOutside(false);
	}

	private void showUpgradeDialog(String content, final String downloadUrl) {
		// mUpgradeDlg = DialogUtil.showConfirmDialog(BaseAct.this, content,
		// getString(R.string.upgrade_delay),
		// getString(R.string.upgrade), new OnDialogClickListener() {
		//
		// @Override
		// public boolean onClick(View v) {
		// SharePreferUtil.putLong(DELAY_UPGRADE_TIME,
		// System.currentTimeMillis());
		// return false;
		// }
		// }, new OnDialogClickListener() {
		//
		// @Override
		// public boolean onClick(View v) {
		// Uri uri = Uri.parse(downloadUrl);
		// Intent downloadIntent = new Intent(Intent.ACTION_VIEW, uri);
		// startActivity(downloadIntent);
		// return false;
		// }
		// });
		// mUpgradeDlg.setCanceledOnTouchOutside(false);
	}

	public ImageFetcher getImageFetcher() {
		if (mImageFetcher == null)
			initImageFetcher();
		return mImageFetcher;
	}

	@Override
	protected void onResume() {
		Logg.d("onResume");
		super.onResume();
		if (mImageFetcher != null)
			mImageFetcher.setExitTasksEarly(false);
		chushihuaNet();
	}

	@Override
	protected void onPause() {
		Logg.d("onPause");
		super.onPause();
		if (mImageFetcher != null) {
			mImageFetcher.setExitTasksEarly(true);
			mImageFetcher.flushCache();
		}
		// if (connectionReceiver != null) {
		// unregisterReceiver(connectionReceiver);
		// }
	}

	@Override
	protected void onDestroy() {
		Logg.d("onDestroy");
		super.onDestroy();
		// 下一句会被保留
		// MyApplication.calcelRequest(getTag());
		if (mImageFetcher != null)
			mImageFetcher.closeCache();
	}

	protected void initImageFetcher() {
		ImageCacheParams cacheParams = new ImageCacheParams(this,
				setImageCacheDir());
		cacheParams.setMemCacheSizePercent(0.25f);
		mImageFetcher = new ImageFetcher(this, getImageFetcherWidth(),
				getImageFetcherHeight());
		mImageFetcher.setLoadingImage(getLoadingImgResId());
		mImageFetcher.addImageCache(getSupportFragmentManager(), cacheParams);
		mImageFetcher.setImageFadeIn(false);
	}

	public int getLoadingImgResId() {
		if (mLoadingImgResId == 0) {
			// 此处需要修改默认的图片，暂时为launcher
			mLoadingImgResId = R.color.transparent;
		}
		return mLoadingImgResId;
	}

	public void setLoadingImgResId(int resId) {
		if (resId != 0) {
			mLoadingImgResId = resId;
		}
	}

	public void setImageFetcherWidth(int width) {
		mImageWidth = width;
	}

	protected int getImageFetcherWidth() {
		if (mImageWidth == 0) {
			mImageWidth = getResources().getDimensionPixelSize(R.dimen.dp_60);
		}
		return mImageWidth;
	}

	public void setImageFetcherHeight(int height) {
		mImageHeight = height;
	}

	protected int getImageFetcherHeight() {
		if (mImageHeight == 0) {
			mImageHeight = getResources().getDimensionPixelSize(R.dimen.dp_60);
		}
		return mImageHeight;
	}

	public String setImageCacheDir() {
		return IMAGE_CACHE_DIR;
	}

	public void popBackStack() {
		getSupportFragmentManager().popBackStack();
	}

	public void replaceFragment(Fragment newFragment) {
		replaceFragmentById(newFragment, android.R.id.content, false);
	}

	public void replaceFragmentToStack(Fragment newFragment) {
		replaceFragmentById(newFragment, android.R.id.content, true);
	}

	public void replaceFragmentById(Fragment newFragment, int container,
                                    boolean addtobackstack) {
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(container, newFragment);
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		if (addtobackstack)
			ft.addToBackStack(null);
		ft.commit();
	}

	public void addFragmentToStack(Fragment newFragment) {
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.add(android.R.id.content, newFragment);
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		ft.addToBackStack(null);
		ft.commit();
	}

	public void addOrAttachFragment(Fragment frag, int containerId, int id) {
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		String name = makeFragmentName(containerId, id);
		Fragment fragment = fm.findFragmentByTag(name);
		if (fragment != null)
			ft.attach(fragment);
		else
			ft.add(containerId, frag, name);
		ft.commit();
	}

	public void detachFragmentById(int containerId, int id) {
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		String name = makeFragmentName(containerId, id);
		Fragment frag = fm.findFragmentByTag(name);
		if (frag != null) {
			ft.detach(frag);
			ft.commit();
		}
	}

	public static String makeFragmentName(int viewId, int id) {
		return "android:harryxu:" + viewId + ":" + id;
	}

	// /**
	// * 网络状态广播接收器
	// */
	// public BroadcastReceiver connectionReceiver = new BroadcastReceiver() {
	// @Override
	// public void onReceive(Context context, Intent intent) {
	// ConnectivityManager connectMgr = (ConnectivityManager) context
	// .getSystemService(Context.CONNECTIVITY_SERVICE);
	// NetworkInfo mobNetInfo =
	// connectMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
	// NetworkInfo wifiNetInfo =
	// connectMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
	//
	// if (!mobNetInfo.isConnected() && !wifiNetInfo.isConnected()) {
	// // CommonTools.notifyNetChange(false, false, false);
	// onNetStatue(false);
	// onWifiStatue(false);
	// onMobileStatue(false);
	// } else {
	// // CommonTools.notifyNetChange(true, wifiNetInfo.isConnected(),
	// // mobNetInfo.isConnected());
	// onNetStatue(true);
	// onWifiStatue(wifiNetInfo.isConnected());
	// onMobileStatue(mobNetInfo.isConnected());
	// }
	// }
	// };

	private void chushihuaNet() {

		// // 添加网络变化动作监听器类
		// IntentFilter intentFilter = new IntentFilter();
		// intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		// registerReceiver(connectionReceiver, intentFilter);
	}

	// @Override
	// public void onWifiStatue(boolean isConnect) {
	//
	// }

	// @Override
	// public void onMobileStatue(boolean isConnect) {
	// if (isConnect) {
	// // Toaster.show(getString(R.string.mobilewangluo));
	// CommonTools.isNetConnection = isConnect;
	// }
	// }

	// /**
	// * 使用说明：如果BaseAct子类需要在网络变化时做相应动作，可通过实现此方法。
	// */
	// @Override
	// public void onNetStatue(boolean isConnect) {
	// if (CommonTools.lastNetStatue == 0 && isConnect)
	// Toaster.show(getString(R.string.wangluolianjie));
	// else if (CommonTools.lastNetStatue == 1 && !isConnect)
	// Toaster.show(getString(R.string.wangluoduankai));
	// if (isConnect)
	// CommonTools.lastNetStatue = 1;
	// else
	// CommonTools.lastNetStatue = 0;
	// CommonTools.isNetConnection = isConnect;
	// }

	/**
	 * Activity跳转
	 */
	public void goActivity(Class<?> clazz) {
		Intent intent = new Intent(context, clazz);
		startActivity(intent);
	}

	/**
	 * Activity跳转
	 */
	public void goActivityForResult(Class<?> clazz, int requestCode) {
		Intent intent = new Intent(context, clazz);
		startActivityForResult(intent, requestCode);
	}




	public String getTag() {
		return this.getClass().getName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onRestoreInstanceState(android.os.Bundle)
	 */
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);
		// Intent intent = new Intent(GuoAoApplication.getInstance(),
		// LoadingActivity.class);
		// intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		// startActivity(intent);
		// ActivityTaskManager.getInstance().closeAllActivity();
		// android.os.Process.killProcess(android.os.Process.myPid());

	}

	public void hideSoftInputFromWindow() {
		InputMethodManager iMManager = (InputMethodManager) this
				.getSystemService(Activity.INPUT_METHOD_SERVICE);
		if (iMManager != null) {
			View currentFocus = this.getCurrentFocus();
			if (currentFocus != null) {
				iMManager.hideSoftInputFromWindow(
						currentFocus.getWindowToken(),
						InputMethodManager.HIDE_NOT_ALWAYS);
			}
		}
	}

}
