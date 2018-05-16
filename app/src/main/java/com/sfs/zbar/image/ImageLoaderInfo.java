package com.sfs.zbar.image;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import java.util.Date;

/**
 * Project:ImageLoaderLib <br>
 * Class:ImageLoaderInfo <br>
 * Description:<br>
 * Created by 杨禹恒 on 2014年9月22日. <br>
 * Version:1.0.0 <br>
 * Update:2014年9月22日 <br>
 */
class ImageLoaderInfo {
	private final int loaderSuccess = 0;
	private final int loaderFaild = 1;
	private final int loadDefault = 3;

	public String imageUrl;
	public View view;
	public DisplayImageOptions mOptions;
	public ImageLoaderCallBack imageLoaderCallBack;
	/**
	 * 0=CENTER_CROP   1=FIT_CENTER  2=MATRIX
	 */
	private int isOriginalSize;
	private boolean norOriginalSize;
	private long time = new Date().getTime();

	public ImageLoaderInfo(String imageUrl, View view,
			DisplayImageOptions mOptions,
			ImageLoaderCallBack imageLoaderCallBack, int isOriginalSize,boolean norOriginalSize) {
		this.isOriginalSize = isOriginalSize;
		this.norOriginalSize=norOriginalSize;
		this.imageUrl = imageUrl;
		view.setTag(this.imageUrl + time);
		this.view = view;
		this.mOptions = mOptions;
		if (this.mOptions == null) {
			this.mOptions = new DisplayImageOptions();
		}
		this.imageLoaderCallBack = imageLoaderCallBack;

		Message msg = Message.obtain();
		msg.what = loadDefault;
		handler.sendMessage(msg);
	}

	/**
	 * Description:填充图片<br>
	 * Created by 杨禹恒 on 2014年9月23日.<br>
	 * 
	 * @param image
	 * <br>
	 */
	public void fillImage(Bitmap image) {
		Message msg = Message.obtain();
		msg.what = loaderSuccess;
		msg.obj = image;
		handler.sendMessage(msg);
	}

	/**
	 * Description:获取图片失败<br>
	 * Created by 杨禹恒 on 2014年9月23日.<br>
	 * <br>
	 */
	public void getImageFaild() {
		Message msg = Message.obtain();
		msg.what = loaderFaild;
		handler.sendMessage(msg);
	}

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

			switch (msg.what) {
			case loaderSuccess:
				// LayoutParams layoutParams = view.getLayoutParams();
				// int height = ((Bitmap) msg.obj).getHeight();// 获取图片的高度.
				// int width = ((Bitmap) msg.obj).getWidth();// 获取图片的宽度
				// layoutParams.height = (height * view.getWidth()) / width;
				// layoutParams.width=view.getWidth();
				// view.setLayoutParams(layoutParams);
				if (view instanceof ImageView) {
					if (view.getTag() != null
							&& view.getTag().equals(imageUrl + time)) {

						((ImageView) view).setImageBitmap((Bitmap) msg.obj);
						switch(isOriginalSize){
						case 0:
							((ImageView) view).setScaleType(ScaleType.CENTER_CROP);
							break;
						case 1:
							((ImageView) view).setScaleType(ScaleType.FIT_CENTER);
							break;
						case 2:
							((ImageView) view).setScaleType(ScaleType.MATRIX);
							break;
						}
					}
				} else if (view instanceof ImageButton) {
					if (view.getTag() != null
							&& view.getTag().equals(imageUrl + time)) {
						((ImageButton) view).setImageBitmap((Bitmap) msg.obj);
					}
				} else {
					if (imageLoaderCallBack != null) { // URL格式错误
						imageLoaderCallBack
								.onLoaderFailure(DisplayImageErrorCode.DISPLAY_ERROR_WIDGET_TYPE);
					}
				}
				break;
			case loaderFaild: // 加载失败，生成默认图
				if (mOptions != null) {
					int iconResId = mOptions.getFailImageRs();
					if (view instanceof ImageView) {
						if (view.getTag() != null
								&& view.getTag().equals(imageUrl + time)) {
							// if (iconResId == 0) {
							// ((ImageView) view).setImageDrawable(null);
							// } else {
							((ImageView) view).setImageResource(iconResId);
							if(norOriginalSize){
								((ImageView) view).setScaleType(ScaleType.CENTER_INSIDE);
							}
							// }
						}
					} else if (view instanceof ImageButton) {
						if (view.getTag() != null
								&& view.getTag().equals(imageUrl + time)) {
							// if (iconResId == 0) {
							// ((ImageButton) view).setImageDrawable(null);
							// } else {
							((ImageButton) view).setImageResource(iconResId);
							if(norOriginalSize){
								((ImageButton) view).setScaleType(ScaleType.CENTER_INSIDE);
							}
							// }
						}
					} else {
					}
				}
				break;
			case loadDefault: // 默认图
				if (mOptions != null) {
					int iconResId = mOptions.getStubImageRs();
					if (view instanceof ImageView) {
						if (view.getTag() != null
								&& view.getTag().equals(imageUrl + time)) {
							if (iconResId == 0) {
								((ImageView) view).setImageDrawable(null);
							} else {
								((ImageView) view).setImageResource(iconResId);
							}
						}
						if(norOriginalSize){
							((ImageView) view).setScaleType(ScaleType.CENTER_INSIDE);
						}
					} else if (view instanceof ImageButton) {
						if (view.getTag() != null
								&& view.getTag().equals(imageUrl + time)) {
							if (iconResId == 0) {
								((ImageButton) view).setImageDrawable(null);
							} else {
								((ImageButton) view)
										.setImageResource(iconResId);
							}
						}
						if(norOriginalSize){
							((ImageButton) view).setScaleType(ScaleType.CENTER_INSIDE);
						}
					} else {
					}
					
				}
				break;
			}
		}
	};
}
