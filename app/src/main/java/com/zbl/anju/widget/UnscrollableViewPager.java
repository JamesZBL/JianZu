package com.zbl.anju.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by James on 2017/6/15.
 */

public class UnscrollableViewPager extends ViewPager {
	public UnscrollableViewPager(Context context) {
		super(context);
	}

	public UnscrollableViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
		return false;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		return false;
	}
}
