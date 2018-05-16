package com.sfs.zbar.base;

import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewStub;
import android.widget.FrameLayout;

import com.sfs.zbar.R;


/**
 * @Description: 方便显示进度条的Activiy
 */
public class LoadingAct extends BaseAct {
	private ViewStub loadingViewStub;
	private View loadingView;

	@Override
	public void setContentView(int layoutResID) {
		ViewGroup view = (ViewGroup) LayoutInflater.from(this).inflate(
				layoutResID, null);
		super.setContentView(view);
		addLoadingViewStub();
	}

	@Override
	public void setContentView(View view, LayoutParams params) {
		super.setContentView(view, params);
		addLoadingViewStub();
	}


	@Override
	public void startActivity(Intent intent) {
		super.startActivity(intent);
	}

	@Override
	public void setContentView(View view) {
		super.setContentView(view);
		addLoadingViewStub();
	}

	private void addLoadingViewStub() {
		loadingViewStub = (ViewStub) View.inflate(this, R.layout.loading_viewstub, null);
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(-1, -1);
		addContentView(loadingViewStub, params);
	}

	private void inflateLoadingView() {
		if(loadingViewStub==null){
			return;
		}
		loadingView = loadingViewStub.inflate();
		loadingView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

			}
		});
	}

	/**
	 * @Description: 控制进度条显示、隐藏
	 * @param visible
	 *            true-显示 false-隐藏
	 * @return void
	 */
	public void setLoadingVisible(boolean visible, float alpha) {
		if (loadingView == null && visible) {
			inflateLoadingView();
		}
		if (loadingViewStub == null || loadingView == null) {
			Log.w("loadingactivity", "loadingViewStub is NULL, must called after setContentView");
			return;
		}
		if (visible && alpha != 1) {
			loadingView.setBackgroundColor(Color.argb((int) (0xff * alpha), 0,
					0, 0));
		}
		if (loadingView != null) {
			loadingView.setVisibility(visible ? View.VISIBLE : View.GONE);
		}
	}
}
