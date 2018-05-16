package com.sfs.zbar.image;

import android.graphics.Bitmap;
import android.view.View;

/**
 * Project:ImageLoaderLib <br>
 * Class:ImageLoaderCallBack <br>
 * Description:图片加载器回调<br>
 * Created by 杨禹恒 on 2014年9月22日. <br>
 * Version:1.0.0 <br>
 * Update:2014年9月22日 <br>
 */
public interface ImageLoaderCallBack {
	/**
	 * Description:图片加载成功<br>
	 * Created by 杨禹恒 on 2014年9月22日.<br>
	 * @param bitmap<br>
	 */
	public void onLoaderSuccess(View view, Bitmap bitmap, String url);
	/**
	 * Description:图片加载失败<br>
	 * Created by 杨禹恒 on 2014年9月22日.<br>
	 * @param errorCode DisplayImageErrorCode<br>
	 */
	public void onLoaderFailure(int errorCode);
}
