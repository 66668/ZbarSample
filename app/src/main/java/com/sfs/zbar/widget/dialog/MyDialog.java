package com.sfs.zbar.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sfs.zbar.R;


public class MyDialog extends Dialog implements
		View.OnClickListener {

	private onDialogCallBack monDialogCallBack;
	private Context mContext;
	private TextView mContent;
	private Button RightButton;
	private String mContentString = "";
	private boolean isSingleButton;
	private String mLeftButtonName = "确定";
	private String mRightButtonName = "取消";
	private boolean isSupportHtmlShow;
	private int mType;// 弹出框区分

	public MyDialog(Context context) {
		super(context, R.style.MyDialog);
		mContext = context;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		View lViewDialog = LayoutInflater.from(mContext).inflate(
				R.layout.view_dialog, null);
		this.setContentView(lViewDialog);

		Button leftButton = (Button) lViewDialog.findViewById(R.id.dialog_btn1);

		leftButton.setText(mLeftButtonName);

		RightButton = (Button) lViewDialog.findViewById(R.id.dialog_btn2);

		RightButton.setText(mRightButtonName);

		mContent = (TextView) lViewDialog.findViewById(R.id.dialog_text);

		leftButton.setOnClickListener(this);
		RightButton.setOnClickListener(this);
		if (isSupportHtmlShow) {
			mContent.setText(Html.fromHtml(mContentString));
		} else {
			mContent.setText(mContentString);
		}

		if (isSingleButton) {
			leftButton.setBackgroundResource(R.drawable.bottom_radio_pre);
			RightButton.setVisibility(View.GONE);
		} else {
			leftButton.setBackgroundResource(R.drawable.left_bottom_radio);
			RightButton.setBackgroundResource(R.drawable.right_bottom_radio);
			RightButton.setVisibility(View.VISIBLE);
		}
	}

	public interface onDialogCallBack {

		public void leftButton(int type);

		public void RightButton(int type);

	}

	public void setDialogCallBack(onDialogCallBack lonDialogCallBack) {
		monDialogCallBack = lonDialogCallBack;
	}

	/**
	 * 设置弹出框内容
	 * 
	 * @param lContent
	 *            弹出框文字
	 * @param isSigleButton
	 *            是否显示一个按钮 否则显示2个按钮
	 */
	public void setContent(String lContent, boolean isSigleButton,
			String leftName, String rightName, int type) {
		mContentString = lContent;
		this.isSingleButton = isSigleButton;

		if (leftName != null) {
			mLeftButtonName = leftName;
		}
		if (rightName != null) {
			mRightButtonName = rightName;
		}
		mType = type;
	}

	public void setHtmlContent(String lContent, boolean isSigleButton,
			String leftName, String rightName, int type) {
		mContentString = lContent;
		this.isSingleButton = isSigleButton;

		if (leftName != null) {
			mLeftButtonName = leftName;
		}
		if (rightName != null) {
			mRightButtonName = rightName;
		}
		isSupportHtmlShow = true;
		mType = type;
	}

	public void showDialog() {
		try {
			show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.dialog_btn1:
			monDialogCallBack.leftButton(mType);
			break;
		case R.id.dialog_btn2:
			monDialogCallBack.RightButton(mType);
			break;
		}
	}

}
