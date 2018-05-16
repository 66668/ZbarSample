package com.sfs.zbar.adapter;


import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

import com.sfs.zbar.bean.Delivery;

/**
 * @author practicing
 */
public class MyListView extends ListView {

    private MyLinearLayout mCurView;

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                //我们想知道当前点击了哪一行
                try {
                    int position = pointToPosition(x, y);
                    if (position != INVALID_POSITION) {
                        Delivery data = (Delivery) getItemAtPosition(position);
                        mCurView = data.rootView;
                    }
                } catch (Exception e) {

                }

            }
            break;
            default:
                break;
        }
        if (mCurView != null) {
            mCurView.disPatchTouchEvent(event);
        }
        return super.onTouchEvent(event);
    }
}
