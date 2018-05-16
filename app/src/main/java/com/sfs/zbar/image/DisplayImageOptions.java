package com.sfs.zbar.image;

/**
 * Project:ImageLoaderLib <br>
 * Class:DisplayImageOptions <br>
 * Description:图片配置类<br>
 * Created by 杨禹恒 on 2014年9月22日. <br>
 * Version:1.0.0 <br>
 * Update:2014年9月22日 <br>
 */
public class DisplayImageOptions {
	private int stubImageRs; // 默认图片
	private int failImageRs; // 失败图片
	private boolean cacheInMemory = true; // 是否缓存到内存中，默认为true
	private boolean cacheOnDisc = true; // 是否缓存到磁盘中

	public int getStubImageRs() {
		return stubImageRs;
	}

	public int getFailImageRs() {
		return failImageRs;
	}

	public boolean isCacheInMemory() {
		return cacheInMemory;
	}

	public boolean isCacheOnDisc() {
		return cacheOnDisc;
	}

	/**
	 * Description:设置图片下载期间显示的图片 <br>
	 * Created by 杨禹恒 on 2014年9月22日.<br>
	 * 
	 * @param resource
	 * <br>
	 */
	public void showStubImage(int resource) {
		stubImageRs=resource;
	}

	/**
	 * Description:设置图片加载或解码过程中发生错误显示的图片<br>
	 * Created by 杨禹恒 on 2014年9月22日.<br>
	 * 
	 * @param resource
	 * <br>
	 */
	public void showImageOnFail(int resource) {
		failImageRs=resource;
	}

	/**
	 * Description:设置下载的图片是否缓存在内存中 <br>
	 * Created by 杨禹恒 on 2014年9月22日.<br>
	 * 
	 * @param scale
	 * <br>
	 */
	public void cacheInMemory(boolean scale) {
		cacheInMemory=scale;
	}

	/**
	 * Description:设置下载的图片是否缓存在SD卡中 <br>
	 * Created by 杨禹恒 on 2014年9月22日.<br>
	 * 
	 * @param scale
	 * <br>
	 */
	public void cacheOnDisc(boolean scale) {
		cacheOnDisc=scale;
	}
}
