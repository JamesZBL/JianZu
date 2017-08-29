package com.zbl.anju.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.webkit.PermissionRequest;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.zbl.anju.R;
import com.zbl.anju.app.AppConst;
import com.zbl.anju.ui.base.BaseActivity;
import com.zbl.anju.ui.base.BasePresenter;
import com.zbl.anju.widget.ProgressWebView;

import butterknife.Bind;

/**
 * @author James
 * @描述 内置浏览器界面
 */
public class WebViewActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

	private Intent mIntent;
	private Bundle mExtras;
	private String mUrl;
	private String mTitle;

	private boolean isLoading = false;
	private boolean isGameMode = false;

	@Bind(com.zbl.anju.R.id.ibToolbarMore)
	ImageButton mIbToolbarMore;
	@Bind(com.zbl.anju.R.id.webview)
	public ProgressWebView mWebView;
	@Bind(R.id.ivToolbarNavigationClear)
	ImageButton mToolbarNavigationClear;
	@Bind(R.id.ivToolbarNavigation)
	ImageButton mToolbarNavigation;
	@Bind(R.id.ibToolbarProgress)
	ProgressBar mProgressBar;
	@Bind(R.id.web_swipe_layout)
	SwipeRefreshLayout mSwipeFresh;

	@Override
	public void init() {
		//得到url
		try {
			mIntent = getIntent();

			mExtras = mIntent.getExtras();
			if (mExtras == null) {
				finish();
				return;
			}
			isGameMode = mExtras.getBoolean(AppConst.WEB_GAME_MODE, false);
			mUrl = mExtras.getString("url");
			if (TextUtils.isEmpty(mUrl)) {
				finish();
				return;
			}
			mTitle = mExtras.getString("title");

		} catch (Exception e) {
			e.printStackTrace();
			finish();
			return;
		}
	}

	@Override
	public void initView() {
		initAppBar();
		initSwipeLaout();
		mIbToolbarMore.setVisibility(View.VISIBLE);
		mToolbarNavigation.setVisibility(View.GONE);
		mToolbarNavigationClear.setVisibility(View.VISIBLE);
		//设置webView
		WebSettings settings = mWebView.getSettings();
		settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
		settings.setSupportMultipleWindows(true);
		settings.setJavaScriptEnabled(true);
		settings.setSavePassword(false);
		settings.setJavaScriptCanOpenWindowsAutomatically(true);
		settings.setMinimumFontSize(settings.getMinimumLogicalFontSize() + 8);
		settings.setAllowFileAccess(false);
		settings.setTextSize(WebSettings.TextSize.NORMAL);
		mWebView.setVerticalScrollbarOverlay(true);
		mWebView.setWebViewClient(new MyWebViewClient());
//		mWebView.setWebChromeClient(new MyWebChromeClient());
		mWebView.loadUrl(mUrl);
		setToolbarTitle(TextUtils.isEmpty(mTitle) ? mWebView.getTitle() : mTitle);
	}

	private void initAppBar() {
		if (isGameMode) {
			mAppBar.setVisibility(View.GONE);
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		}
	}

	/**
	 * 初始化下拉刷新布局
	 */
	private void initSwipeLaout() {
		// 设置下拉圆圈上的颜色，蓝色、绿色、橙色、红色
		mSwipeFresh.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
				android.R.color.holo_orange_light, android.R.color.holo_red_light, android.R.color.holo_purple);
		if (isGameMode) {
			mSwipeFresh.setEnabled(false);
		}
	}

	@Override
	public void initListener() {
		mSwipeFresh.setOnRefreshListener(this);
		mToolbarNavigationClear.setOnClickListener(v -> finish());
	}

	@Override
	protected BasePresenter createPresenter() {
		return null;
	}

	@Override
	protected int provideContentViewId() {
		return com.zbl.anju.R.layout.activity_webview;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				finish();
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		isLoading = false;

		//如果当前浏览器可以后退，则后退上一个页面
		if (mWebView.canGoBack()) {
			mWebView.goBack();
		} else {
			finish();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mWebView != null) {
			mWebView.removeAllViews();
			try {
				mWebView.destroy();
			} catch (Exception e) {
				e.printStackTrace();
			}
			mWebView = null;
		}
	}

	/**
	 * 下拉刷新
	 */
	@Override
	public void onRefresh() {
		mWebView.reload();

	}

	private class MyWebViewClient extends WebViewClient {

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			//在自己浏览器中跳转
			mWebView.loadUrl(url);
			return true;
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			super.onPageStarted(view, url, favicon);
			isLoading = true;
			mProgressBar.setVisibility(View.VISIBLE);
			setToolbarTitle("加载中...");
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
			isLoading = false;
			mProgressBar.setVisibility(View.GONE);
			mSwipeFresh.setRefreshing(false);
			setToolbarTitle(mWebView.getHtmltitle());
		}

		@Override
		public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
			handler.proceed();  // 接受所有网站的证书
			super.onReceivedSslError(view, handler, error);
		}
	}

	private class MyWebChromeClient extends android.webkit.WebChromeClient {
		@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
		@Override
		public void onPermissionRequest(PermissionRequest request) {
			super.onPermissionRequest(request);
			request.grant(request.getResources());
		}
	}
}
