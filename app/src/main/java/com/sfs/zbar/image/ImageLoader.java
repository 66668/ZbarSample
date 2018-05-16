package com.sfs.zbar.image;

import android.content.Context;
import android.view.View;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Project:ImageLoaderLib <br>
 * Class:ImageLoader <br>
 * Description:图片加载器<br>
 * Created by 杨禹恒 on 2014年9月22日. <br>
 * Version:1.0.0 <br>
 * Update:2014年9月22日 <br>
 */
public class ImageLoader {

	private Context mContext;

	private ExecutorService loaderExecutor = Executors.newFixedThreadPool(10);

	private static ImageLoader instance;

	private ImageLoader(Context context) {
		this.mContext = context;
	}

	public static ImageLoader getInstance(Context context) {
		if (instance == null) {
			instance = new ImageLoader(context.getApplicationContext());
		}
		return instance;
	}

	/**
	 * Description:加载控件图片<br>
	 * Created by 杨禹恒 on 2014年9月22日.<br>
	 * 
	 * @param imageUrl
	 * @param imageView
	 * <br>
	 */
	public void displayImage(String imageUrl, View imageView) {
		displayImage(imageUrl, imageView, null);
	}

	/**
	 * Description:加载控件图片<br>
	 * Created by 杨禹恒 on 2014年9月22日.<br>
	 * 
	 * @param imageUrl
	 * @param imageView
	 * @param options
	 * <br>
	 */
	public void displayImage(String imageUrl, View imageView,
			DisplayImageOptions options) {
		displayImage(imageUrl, imageView, options, null, 0,false);
	}
	
	public void  displayImage(String imageUrl, View imageView,
			DisplayImageOptions options,int isOriginalSize){
		displayImage(imageUrl, imageView, options, null, isOriginalSize ,false);
	}
	public void  displayImage(String imageUrl, View imageView,
			DisplayImageOptions options,int isOriginalSize,boolean norOriginalSize){
		displayImage(imageUrl, imageView, options, null, isOriginalSize,norOriginalSize);
	}

	/**
	 * Description:加载控件图片<br>
	 * Created by 杨禹恒 on 2014年9月22日.<br>
	 * 
	 * @param imageUrl
	 * @param imageView
	 * @param options
	 * @param imageLoaderCallBack
	 * <br>
	 */
	public void displayImage(String imageUrl, View imageView,
			DisplayImageOptions options, ImageLoaderCallBack imageLoaderCallBack,int isOriginalSize,boolean norOriginalSize) {
		ImageLoaderInfo ili = new ImageLoaderInfo(imageUrl,imageView,options,imageLoaderCallBack,isOriginalSize,norOriginalSize);
		if(imageUrl!=null&&imageUrl.length()>0){
			ImageLoaderRunnable task = new ImageLoaderRunnable(mContext, ili);
			loaderExecutor.execute(task);
		}else{
			ili.getImageFaild();
		}
	}
}
