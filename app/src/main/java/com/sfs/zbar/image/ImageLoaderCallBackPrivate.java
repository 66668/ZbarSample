package com.sfs.zbar.image;

import android.graphics.Bitmap;

/**
 * Project:ImageLoaderLib <br>
 * Class:ImageLoaderCallBackPrivate <br>
 * Description:内部使用回调<br>
 * Created by 杨禹恒 on 2014年9月23日. <br>
 * Version:1.0.0 <br>
 * Update:2014年9月23日 <br>
 */
public interface ImageLoaderCallBackPrivate {
	/**
	 * Description:图片加载成功<br>
	 * Created by 杨禹恒 on 2014年9月22日.<br>
	 * @param bitmap<br>
	 */
	public void onLoaderSuccess(Bitmap bitmap, ImageLoaderInfo imagLoaderInfo);
	/**
	 * Description:图片加载失败<br>
	 * Created by 杨禹恒 on 2014年9月22日.<br>
	 * @param errorCode DisplayImageErrorCode<br>
	 */
	public void onLoaderFailure(int errorCode);
}
