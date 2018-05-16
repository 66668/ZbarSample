package com.sfs.zbar.adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * 
 * @author practicing
 *
 */
public class MyLinearLayout extends LinearLayout {
	private int mlastX = 0;
	private final int MAX_WIDTH = 70;//向左滑动的距离 
	private Context mContext;
	private Scroller mScroller;
	private OnScrollListener mScrollListener;

	public static interface OnScrollListener {
		public void OnScroll(MyLinearLayout view);
	}

	public MyLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		mScroller = new Scroller(context, new LinearInterpolator(context, null));
	}

	public void disPatchTouchEvent(MotionEvent event) {
		int maxLength = dipToPx(mContext, MAX_WIDTH);

		int x = (int) event.getX();
		int y = (int) event.getY();

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: {
		}
			break;
		case MotionEvent.ACTION_MOVE: {
			int scrollX = this.getScrollX();
			int newScrollX = scrollX + mlastX - x;
			
			if (Math.abs(mlastX - x) > 30) {
				if (newScrollX < 0) {
					newScrollX = 0;
				} else if (newScrollX > maxLength) {
					newScrollX = maxLength;
				}
				this.scrollTo(newScrollX, 0);
			}

		}
			break;
		case MotionEvent.ACTION_UP: {
			int scrollX = this.getScrollX();
			int newScrollX = scrollX + mlastX - x;
			if (scrollX > maxLength / 2) {
				newScrollX = maxLength;
				mScrollListener.OnScroll(this);
			} else {
				newScrollX = 0;
			}
			mScroller.startScroll(scrollX, 0, newScrollX - scrollX, 0);
			invalidate();

		}
			break;
		}
		mlastX = x;
	}

	public void setOnScrollListener(OnScrollListener scrollListener) {
		mScrollListener = scrollListener;
	}

	public void smoothScrollTo(int destX, int destY) {
		int scrollX = getScrollX();
		int delta = destX - scrollX;
		mScroller.startScroll(scrollX, 0, delta, 0);
		invalidate();
	}

	@Override
	public void computeScroll() {
		if (mScroller.computeScrollOffset()) {
			this.scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
		}
		invalidate();
	}

	private int dipToPx(Context context, int dip) {
		return (int) (dip * context.getResources().getDisplayMetrics().density + 0.5f);
	}
}
