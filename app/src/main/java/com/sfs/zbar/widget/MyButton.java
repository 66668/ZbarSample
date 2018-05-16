// Copyright (C) 2012-2013 UUZZ All rights reserved
package com.sfs.zbar.widget;

import android.content.Context;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.sfs.zbar.R;


/**
 * 项目名称：Lottery1_3_0 类名：MyButton.java 类描述：自定义button
 * 
 * @author 杨禹恒 实现的主要功能：防止button重复点击 版本：1.3.0 修改时间：2013-10-15
 */
public class MyButton extends android.support.v7.widget.AppCompatButton {
	private long lastClickTime;
	//第一行决定红色,第二行决定绿色,第三行决定蓝色，第四行决定了透明度，第五列是颜色的偏移量
	private int textColor;
	private Drawable drawable;
	private Context mContext;

	public final float[] BT_SELECTED = new float[] { 
			1, 0, 0, 0, -50,
			0, 1, 0, 0, -50,
			0, 0, 1, 0, -50,
			0, 0, 0, 1, 0 };
	public final float[] BT_NOT_SELECTED = new float[] { 
			1, 0, 0, 0, 0,
			0, 1, 0, 0, 0,
			0, 0, 1, 0, 0,
			0, 0, 0, 1, 0 };
	
	public MyButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext=context;
		textColor=getCurrentTextColor();
		drawable=getBackground();
		if(!isEnabled()){
			setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.login_btn_enable_background));
		}
		
	}

	public MyButton(Context context) {
		super(context);
		mContext=context;
		textColor=getCurrentTextColor();
		drawable=getBackground();
		if(!isEnabled()){
			setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.login_btn_enable_background));
		}
	}
	
	/* (non-Javadoc)
	 * @see android.widget.TextView#setEnabled(boolean)
	 */
	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		if(!enabled){
			setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.login_btn_enable_background));
		}else{
			setBackgroundDrawable(drawable);
			getBackground().setColorFilter(
					new ColorMatrixColorFilter(BT_NOT_SELECTED));
			setBackgroundDrawable(getBackground());
			setTextColor(textColor);
		}
	}
	
	

	@Override
	public void setOnClickListener(OnClickListener l) {
		// TODO Auto-generated method stub
		super.setOnClickListener(l);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		if(!isEnabled()){
			return false;
		}
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			getBackground().setColorFilter(
					new ColorMatrixColorFilter(BT_SELECTED));
			setBackgroundDrawable(getBackground());
			getParent().requestDisallowInterceptTouchEvent(true);// 通知父类不要处理这次Touch事件，我要自己处理
			break;
		case MotionEvent.ACTION_UP:
			if (isFastDoubleClick()) {
				event.setAction(MotionEvent.ACTION_CANCEL);
			}
			getBackground().setColorFilter(
					new ColorMatrixColorFilter(BT_NOT_SELECTED));
			setBackgroundDrawable(getBackground());
			break;
		}
		return super.onTouchEvent(event);
	}

	public boolean isFastDoubleClick() {
		long time = System.currentTimeMillis();
		long timeD = time - lastClickTime;
		if (0 < timeD && timeD < 1000) {
			return true;
		}
		lastClickTime = time;
		return false;
	}

}
