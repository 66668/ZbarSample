package com.sfs.zbar.imgcache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.widget.ImageView;

import com.yanzhenjie.zbar.BuildConfig;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


public class ImageLocalFetcher extends ImageFetcher {

	private static final String TAG = "ImageLocalFetcher";

	public ImageLocalFetcher(Context context, int imageWidth) {
		super(context, imageWidth);
	}
	
	public ImageLocalFetcher(Context context, int imageWidth, int imageHeight) {
		super(context, imageWidth, imageHeight);
	}

	@Override
	public void loadImage(Object data, ImageView imageView) {
		if (data == null) {
            return;
        }

		String url = String.valueOf(data);
        BitmapDrawable value = null;

        if (mImageCache != null) {
			value = mImageCache.getBitmapFromMemCache(url);
        }

        if (value != null) {
            // Bitmap found in memory cache
        	if (mIsCircleBitmap) {
        		imageView.setImageBitmap(BitmapUtil.toCircleBmp(BitmapUtil.drawableToBitmap(value)));
        	}
        	else imageView.setImageDrawable(value);
        	return;
        } 
        
        if (cancelPotentialWork(data, imageView)) {
        	if (url.contains("http")) {
        		DiskLruCache diskLruCache = mImageCache.mDiskLruCache;
        		if (diskLruCache != null) {
        			DiskLruCache.Entry entry = diskLruCache.lruEntries.get(ImageCache.hashKeyForDisk(url));
        			if (entry == null || !entry.getCleanFile(0).exists()) {
        				requestFromNet(data, imageView);
        				return;
        			}
        		}
        	}
        	
        	final BitmapLocalWorkerTask task = new BitmapLocalWorkerTask(imageView);
        	final AsyncDrawable asyncDrawable =
        			new AsyncDrawable(mResources, mLoadingBitmap, task);
        	imageView.setImageDrawable(asyncDrawable);
        	task.executeOnExecutor(AsyncTask.DUAL_THREAD_EXECUTOR, data);
        }
	}

	private void requestFromNet(Object data, ImageView imageView) {
		final BitmapWorkerTask task = new BitmapWorkerTask(imageView);
		final AsyncDrawable asyncDrawable =
				new AsyncDrawable(mResources, mLoadingBitmap, task);
		imageView.setImageDrawable(asyncDrawable);
		task.executeOnExecutor(AsyncTask.DUAL_THREAD_EXECUTOR, data);
	}

	private class BitmapLocalWorkerTask extends BitmapWorkerTask {

        public BitmapLocalWorkerTask(ImageView imageView) {
			super(imageView);
		}

		/**
         * Background processing.
         */
        @Override
        protected BitmapDrawable doInBackground(Object... params) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "doInBackground - starting work");
            }

            data = params[0];
            final String dataString = String.valueOf(data);
            Bitmap bitmap = null;
            BitmapDrawable drawable = null;
            
            synchronized (mPauseWorkLock) {
                while (mPauseWork && !isCancelled()) {
                    try {
                        mPauseWorkLock.wait();
                    } catch (InterruptedException e) {}
                }
            }
            
            // If the image cache is available and this task has not been cancelled by another
            // thread and the ImageView that was originally bound to this task is still bound back
            // to this task and our "exit early" flag is not set then try and fetch the bitmap from
            // the cache
            if (mImageCache != null && !isCancelled() && getAttachedImageView() != null
            		&& !mExitTasksEarly) {
				bitmap = mImageCache.getBitmapFromDiskCache(dataString);
            }
            
            if (bitmap == null && !isCancelled() && getAttachedImageView() != null
            		&& !mExitTasksEarly) {
            	bitmap = processLocalBitmap(dataString);
            }
            
            if (bitmap != null) {
            	if (Utils.hasHoneycomb()) {
            		drawable = new BitmapDrawable(mResources, bitmap);
            	} else {
            		drawable = new RecyclingBitmapDrawable(mResources, bitmap);
            	}
            	
            	ImageCache imageCache = getImageCache();
            	if (imageCache != null) {
            		imageCache.addBitmapToCache(dataString, drawable);
            	}
            }

            return drawable;
        }
    }
	
	private Bitmap processLocalBitmap(String data) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "processBitmap - " + data);
        }

        Bitmap bitmap = null;
        FileInputStream fileInputStream = null;
        FileDescriptor fileDescriptor;
		try {
			fileInputStream = new FileInputStream(data);
			fileDescriptor = fileInputStream.getFD();
			if (fileDescriptor != null) {
				bitmap = decodeSampledBitmapFromDescriptor(fileDescriptor, mImageWidth,
						mImageHeight, getImageCache());
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		if (fileInputStream != null) {
			try {
				fileInputStream.close();
			} catch (IOException e) {}
		}
        return bitmap;
    }
}
