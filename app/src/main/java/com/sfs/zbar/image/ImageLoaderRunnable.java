package com.sfs.zbar.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

/**
 * Project:ImageLoaderLib <br>
 * Class:ImageLoaderRunnable <br>
 * Description:加载图片线程<br>
 * Created by 杨禹恒 on 2014年9月22日. <br>
 * Version:1.0.0 <br>
 * Update:2014年9月22日 <br>
 */
class ImageLoaderRunnable implements Runnable {
	private Context mContext;
	/**
	 * 加载信息
	 */
	private ImageLoaderInfo imageLoaderInfo;
	/**
	 * 图片文件缓存
	 */
	private ImageFileCache imageFileCache;

	/**
	 * 网络获取图片
	 */
	private ImageGetFromHttp imageFromHttp;

	/**
	 * 图片内存缓存
	 */
	private ImageMemoryCache imageMemoryCache;

	public ImageLoaderRunnable(Context context, ImageLoaderInfo imageLoaderInfo) {
		this.imageLoaderInfo = imageLoaderInfo;
		mContext = context;
		imageFileCache = ImageFileCache.getInstance(mContext);
		imageMemoryCache = ImageMemoryCache.getInstance(mContext);
		imageFromHttp = ImageGetFromHttp.getInstance(mContext);
	}

	@Override
	public void run() {
		if (imageLoaderInfo.imageUrl != null
				&& imageLoaderInfo.imageUrl.length() > 0) { // 优先判断URL合法性
			Bitmap image = imageMemoryCache
					.getBitmapFromCache(imageLoaderInfo.imageUrl);
			if (image != null) { // 如果内存缓存有数据
				Log.d("aaa","缓存数据");
				fillImage(image);
				if (imageLoaderInfo.imageLoaderCallBack != null) {
					imageLoaderInfo.imageLoaderCallBack.onLoaderSuccess(
							imageLoaderInfo.view, image,
							imageLoaderInfo.imageUrl);
				}
			} else { // 从磁盘文件获取图片
				image = imageFileCache.getImage(imageLoaderInfo.imageUrl);
				if (image != null) { // 如果文件缓存有数据
					Log.d("aaa","磁盘数据");
					if (imageLoaderInfo.mOptions != null) {
						if (imageLoaderInfo.mOptions.isCacheInMemory()) { // 是否可以内存缓存
							imageMemoryCache.addBitmapToCache(
									imageLoaderInfo.imageUrl, image);
						}
					}
					fillImage(image);
					if (imageLoaderInfo.imageLoaderCallBack != null) {
						imageLoaderInfo.imageLoaderCallBack.onLoaderSuccess(
								imageLoaderInfo.view, image,
								imageLoaderInfo.imageUrl);
					}
				} else { // 从网络获取图片
					Log.d("aaa","网络下载");
					image = imageFromHttp
							.downloadBitmap(imageLoaderInfo.imageUrl);
					if (image != null) { // 网络有数据
						if (imageLoaderInfo.mOptions != null) {
							if (imageLoaderInfo.mOptions.isCacheInMemory()) { // 是否可以内存缓存
								imageMemoryCache.addBitmapToCache(
										imageLoaderInfo.imageUrl, image);
							}
							if (imageLoaderInfo.mOptions.isCacheOnDisc()) { // 是否可以文件缓存
								imageFileCache.saveBitmap(image,
										imageLoaderInfo.imageUrl);
							}
						}
						fillImage(image);
						if (imageLoaderInfo.imageLoaderCallBack != null) {
							imageLoaderInfo.imageLoaderCallBack
									.onLoaderSuccess(imageLoaderInfo.view,
											image, imageLoaderInfo.imageUrl);
						}
					} else {
						if (imageLoaderInfo.imageLoaderCallBack != null) { // 文件未找到
							imageLoaderInfo.imageLoaderCallBack
									.onLoaderFailure(DisplayImageErrorCode.DISPLAY_ERROR_IMAGE_NOT_FOUND);
							imageLoaderInfo.getImageFaild();
						}
					}
				}
			}
		} else {
			if (imageLoaderInfo.imageLoaderCallBack != null) { // URL格式错误
				imageLoaderInfo.imageLoaderCallBack
						.onLoaderFailure(DisplayImageErrorCode.DISPLAY_ERROR_NET_URL_FORMAT);
			}
			imageLoaderInfo.getImageFaild();
		}
	}

	/**
	 * Description:填充图片<br>
	 * Created by 杨禹恒 on 2014年9月22日.<br>
	 * 
	 * @param image
	 * <br>
	 */
	public void fillImage(Bitmap image) {
		if (imageLoaderInfo.view != null) {
			imageLoaderInfo.fillImage(image);
		} else {
			if (imageLoaderInfo.imageLoaderCallBack != null) {
				imageLoaderInfo.imageLoaderCallBack
						.onLoaderFailure(DisplayImageErrorCode.DISPLAY_ERROR_WIDGET_NULL);
				imageLoaderInfo.getImageFaild();
			}
		}
	}
}
