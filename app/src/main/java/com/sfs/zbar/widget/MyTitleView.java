package com.sfs.zbar.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sfs.zbar.R;


/**
 * 标题栏
 *
 * @author troy
 */
public class MyTitleView extends LinearLayout {

    private Context mContext;
    private Button mBackBtn;// 返回按钮
    private TextView mContent;// 标题文字
    private Button mMessageBtn;// 右边按钮
    private OnTitleCallBack mOnTitleCallBack;// 右边按钮点击回调
    private View view;
    private RelativeLayout mBackGround;
    public static final int STYLE_MESSAGE = 1;
    public static final int STYLE_MESSAGEWITHRED = 2;
    public static final int STYLE_ADD = 3;
    public static final int STYLE_CANERAM = 4;
    public static final int STYLE_DANHAO = 5;
    public static final int STYLE_SEARCH = 6;
    public static final int STYLE_WANCHENG = 7;
    public static final int STYLE_SHOUDONGSHURU = 8;
    public static final int STYLE_VOICEINPUT = 9;
    public static final int STYLE_NULL = 10;
    public static final int STYLE_CAMERA = 11;
    public static final int STYLE_SCAN = 12;
    public static final int STYLE_BLUE = 13;

    private boolean isRedStyle;

    public MyTitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
        // TODO Auto-generated constructor stub
    }

    public MyTitleView(Context context) {
        super(context);
        mContext = context;
        init();
        // TODO Auto-generated constructor stub
    }

    private void init() {
        view = LayoutInflater.from(mContext).inflate(R.layout.topbar_title, this);
        mBackBtn = (Button) view.findViewById(R.id.title_back);
        mContent = (TextView) view.findViewById(R.id.title_content);
        mMessageBtn = (Button) view.findViewById(R.id.title_message);
        mBackGround = (RelativeLayout) view.findViewById(R.id.title_backgroung);
    }

    public void setSubTitle(String title) {
        TextView subTitle = (TextView) findViewById(R.id.titlesub_content);
        subTitle.setVisibility(View.VISIBLE);
        subTitle.setText(title);
    }

    public interface OnTitleCallBack {
        public void RightOnClick();

        public void LeftOnClick();
    }

    public void setBlackStyle() {
        mBackGround.setBackgroundColor(mContext.getResources().getColor(R.color.black));
        mContent.setTextColor(mContext.getResources().getColor(R.color.white));
        isRedStyle = true;
    }

    @SuppressWarnings("deprecation")
    public void setRightButton(int style) {
        switch (style) {
            case STYLE_ADD:
                mMessageBtn.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.add_img));
                break;
            case STYLE_MESSAGE:
                mMessageBtn.setBackgroundDrawable(mContext.getResources()
                        .getDrawable(R.drawable.com_btn_tianjia_1_normal));
                break;

            case STYLE_CAMERA:
                mMessageBtn.setBackgroundDrawable(mContext.getResources()
                        .getDrawable(R.drawable.zhongbaovamera));
                break;

            case STYLE_MESSAGEWITHRED:
                mMessageBtn.setBackgroundDrawable(mContext.getResources()
                        .getDrawable(R.drawable.com_btn_xiaoxi_normal));
                break;
            case STYLE_CANERAM:
                mMessageBtn.setBackgroundColor(Color.TRANSPARENT);
                mMessageBtn.setText("重新拍照");
                mMessageBtn.setTextColor(Color.WHITE);
                break;
            case STYLE_DANHAO:
                mMessageBtn.setBackgroundColor(Color.TRANSPARENT);
                mMessageBtn.setText("单号查询");
                mMessageBtn.setTextColor(Color.WHITE);
                break;
            case STYLE_SEARCH:
                mMessageBtn.setBackgroundColor(Color.TRANSPARENT);
                mMessageBtn.setText("搜索");
                mMessageBtn.setTextColor(Color.WHITE);
                break;
            case STYLE_WANCHENG:
                mMessageBtn.setBackgroundColor(Color.TRANSPARENT);
                mMessageBtn.setText("完成");
                mMessageBtn.setTextColor(Color.WHITE);
                break;
            case STYLE_SHOUDONGSHURU:
                mMessageBtn.setBackgroundColor(Color.TRANSPARENT);
                mMessageBtn.setText("手动输入");
                mMessageBtn.setTextColor(Color.WHITE);
                break;
            case STYLE_VOICEINPUT:
                mMessageBtn.setBackgroundColor(Color.TRANSPARENT);
                mMessageBtn.setText("语音输入");
                mMessageBtn.setTextColor(Color.WHITE);
                break;
            case STYLE_SCAN://扫描
                mMessageBtn.setBackgroundDrawable(mContext.getResources()
                        .getDrawable(R.drawable.btn_sm));
                mMessageBtn.setText("");
                break;
//            case STYLE_BLUE://蓝牙
//                mMessageBtn.setBackgroundDrawable(mContext.getResources()
//                        .getDrawable(R.drawable.btn_hw));
//                mMessageBtn.setText("");
//                break;


            case STYLE_NULL:
                break;
        }
    }

    public void setContent(String title, boolean isLeftVisible,
                           boolean isRightVisible, OnTitleCallBack lOnTitleCallBack) {
        mOnTitleCallBack = lOnTitleCallBack;
        mContent.setText(title);
        if (isLeftVisible) {
            mBackBtn.setVisibility(View.VISIBLE);
        } else {
            mBackBtn.setVisibility(View.GONE);
        }

        if (isRightVisible) {
            mMessageBtn.setVisibility(View.VISIBLE);
        } else {
            mMessageBtn.setVisibility(View.GONE);
        }
        mBackBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if (mContext instanceof Activity) {
                    if (mOnTitleCallBack != null) {
                        mOnTitleCallBack.LeftOnClick();
                    } else {
                        Activity mActivity = ((Activity) mContext);
                        mActivity.finish();
                    }
                }
            }
        });

        mMessageBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (mOnTitleCallBack != null) {
                    mOnTitleCallBack.RightOnClick();
                }

            }
        });

    }

}
