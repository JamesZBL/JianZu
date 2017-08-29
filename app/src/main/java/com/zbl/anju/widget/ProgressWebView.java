package com.zbl.anju.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.webkit.WebView;
import android.widget.ProgressBar;

/**
 * @author James
 * @描述 带进度条的WebView
 */
public class ProgressWebView extends WebView {


	private String htmltitle;//html tile
	private ProgressBar mProgressBar;


	public String getHtmltitle() {
		return htmltitle;
	}

	public ProgressWebView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mProgressBar = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
		mProgressBar.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 8, 0, 0));

		Drawable drawable = context.getResources().getDrawable(com.zbl.anju.R.drawable.progressbar_webview);
		mProgressBar.setProgressDrawable(drawable);
		addView(mProgressBar);
		setWebChromeClient(new WebChromeClient());
		//是否可以缩放
		getSettings().setSupportZoom(false);
//        getSettings().setBuiltInZoomControls(true);
	}

	public class WebChromeClient extends android.webkit.WebChromeClient {
		@Override
		public void onProgressChanged(WebView view, int newProgress) {
			if (newProgress == 100) {
				mProgressBar.setVisibility(GONE);
			} else {
				if (mProgressBar.getVisibility() == GONE)
					mProgressBar.setVisibility(VISIBLE);
				mProgressBar.setProgress(newProgress);
				try {
					htmltitle = view.getTitle().trim();
				} catch (NullPointerException e) {
					e.printStackTrace();
				}
			}
			super.onProgressChanged(view, newProgress);
		}
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		LayoutParams lp = (LayoutParams) mProgressBar.getLayoutParams();
		lp.x = l;
		lp.y = t;
		mProgressBar.setLayoutParams(lp);
		super.onScrollChanged(l, t, oldl, oldt);
	}
}
