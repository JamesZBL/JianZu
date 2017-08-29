package com.zbl.anju.util;

import android.app.Activity;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.FrameLayout;

/**
 * 解决全屏状态（包括设置了 Translucent Status 后输入框被软键盘遮挡的问题
 * Created by James on 2017/6/17.
 */

public class EdittextFit {


	public static void assistActivity(Activity activity) {
		new EdittextFit(activity);
	}

	private Activity mContext;
	private View mChildOfContent;
	private int usableHeightPrevious;
	private FrameLayout.LayoutParams frameLayoutParams;

	private EdittextFit(Activity activity) {
		this.mContext = activity;
		FrameLayout content = (FrameLayout) activity.findViewById(android.R.id.content);
		mChildOfContent = content.getChildAt(0);
		mChildOfContent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
			public void onGlobalLayout() {
				possiblyResizeChildOfContent();
			}
		});
		frameLayoutParams = (FrameLayout.LayoutParams) mChildOfContent.getLayoutParams();
	}

	private void possiblyResizeChildOfContent() {
		int usableHeightNow = computeUsableHeight();
		if (usableHeightNow != usableHeightPrevious) {
			int usableHeightSansKeyboard = mChildOfContent.getRootView().getHeight();
			int heightDifference = usableHeightSansKeyboard - usableHeightNow;
			if (heightDifference > (usableHeightSansKeyboard / 4)) {
				// keyboard probably just became visible
				if (mContext.getWindow().hasFeature(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)) {
					frameLayoutParams.height = usableHeightSansKeyboard - heightDifference + UIUtils.getStatusbarheight(mContext);
				} else {
					frameLayoutParams.height = usableHeightSansKeyboard - heightDifference;
				}
			} else {
				// keyboard probably just became hidden
				frameLayoutParams.height = usableHeightSansKeyboard;
			}
			mChildOfContent.requestLayout();
			usableHeightPrevious = usableHeightNow;
		}
	}

	private int computeUsableHeight() {
		Rect r = new Rect();
		mChildOfContent.getWindowVisibleDisplayFrame(r);
		return (r.bottom - r.top);
	}

}
