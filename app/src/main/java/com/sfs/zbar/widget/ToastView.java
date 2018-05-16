package com.sfs.zbar.widget;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.sfs.zbar.R;


public class ToastView extends FrameLayout{
	private TextView tv;

	public ToastView(Context context) {
		this(context, null);
	}
	
	public ToastView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	public ToastView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView();
	}
	
	private void initView(){
		inflate(getContext(), R.layout.ui_toast, this);
		tv = (TextView) findViewById(R.id.TextViewInfo);
	}
	
	public void setText(CharSequence text){
		tv.setText(text);
	}
}