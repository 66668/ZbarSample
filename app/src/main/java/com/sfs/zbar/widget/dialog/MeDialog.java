package com.sfs.zbar.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.sfs.zbar.R;


public class MeDialog extends Dialog {
	
/**
 * 自定义dialog
 * 对话框
 */
	

	public MeDialog(Context context) {
		super(context);

	}

	public MeDialog(Context context, int theme) {
		super(context, theme);
		setContentView(R.layout.dialog_tips);
	}

	public MeDialog(Context context, boolean cancelable,
                    OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

}
