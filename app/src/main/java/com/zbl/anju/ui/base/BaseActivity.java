package com.zbl.anju.ui.base;

import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.zbl.anju.R;
import com.zbl.anju.app.AppConst;
import com.zbl.anju.app.MyApp;
import com.zbl.anju.ui.activity.WebViewActivity;
import com.zbl.anju.util.EdittextFit;
import com.zbl.anju.util.LogUtils;
import com.zbl.anju.util.SPUtils;
import com.zbl.anju.util.UIUtils;
import com.zbl.anju.widget.CustomDialog;
import com.zhy.autolayout.AutoLinearLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.drakeet.materialdialog.MaterialDialog;

public abstract class BaseActivity<V, T extends BasePresenter<V>> extends AppCompatActivity {

	protected T mPresenter;
	private CustomDialog mDialogWaiting;
	private MaterialDialog mMaterialDialog;

	//以下是所有Activity中可能会出现的控件
	@Bind(R.id.appBarWhite)
	protected AppBarLayout mAppBar;
	//    @Bind(R.id.toolbar)
	//    protected Toolbar mToolbar;
	@Bind(R.id.flToolbar)
	public FrameLayout mToolbar;
	@Bind(R.id.ivToolbarNavigationArea)
	public AutoLinearLayout mToolbarNavigation;
	@Bind(R.id.tvToolbarTitle)
	public TextView mToolbarTitle;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyApp.activities.add(this);
		init();

		//判断是否使用MVP模式
		mPresenter = createPresenter();
		if (mPresenter != null) {
			mPresenter.attachView((V) this);//因为之后所有的子类都要实现对应的View接口
		}

		//子类不再需要设置布局ID，也不再需要使用ButterKnife.bind()
		setContentView(provideContentViewId());
		ButterKnife.bind(this);

		initStatusBar();
		setupAppBarAndToolbar();
//		initEdittextFit();  调用可导致布局错位

		//沉浸式状态栏
//		StatusBarUtil.setColor(this, UIUtils.getColor(R.color.colorPrimaryDark), 10);

		initView();
		initListener();
		initData();

	}

	@Override
	protected void onResume() {
		super.onResume();
		onFirstLaunchThisVersion();
	}


	/**
	 * 解决软键盘遮挡问题
	 */
	protected void initEdittextFit() {
		EdittextFit.assistActivity(this);
	}

	protected void initStatusBar() {
		if (Build.VERSION.SDK_INT >= 19) {
//			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//			mAppBar.setPadding(0, UIUtils.getStatusbarheight(this), 0, 0);
			if (Build.VERSION.SDK_INT >= 21) {
//				getWindow().setStatusBarColor(UIUtils.getColor(R.color.black));
//				getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
			}

//			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
//			{
//				getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//				getWindow().setStatusBarColor(getResources().getColor(R.color.black));
//			}
		}
	}

	/**
	 * 设置AppBar和Toolbar
	 */
	private void setupAppBarAndToolbar() {
		//如果该应用运行在android 5.0以上设备，设置标题栏的z轴高度
		if (mAppBar != null && Build.VERSION.SDK_INT >= 21) {
//			mAppBar.setElevation(0f);
		}

		//如果界面中有使用toolbar，则使用toolbar替代actionbar
		//默认不是使用NoActionBar主题，所以如果需要使用Toolbar，需要自定义NoActionBar主题后，在AndroidManifest.xml中对指定Activity设置theme
//        if (mToolbar != null) {
//            setSupportActionBar(mToolbar);
//            if (isToolbarCanBack()) {
//                ActionBar actionBar = getSupportActionBar();
//                if (actionBar != null) {
//                    actionBar.setDisplayHomeAsUpEnabled(true);
//                }
//            }
//        }

		mToolbarNavigation.setVisibility(isToolbarCanBack() ? View.VISIBLE : View.GONE);
		mToolbarNavigation.setOnClickListener(v -> onBackPressed());


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
	protected void onDestroy() {
		super.onDestroy();
		if (mPresenter != null) {
			mPresenter.detachView();
		}
	}

	//在setContentView()调用之前调用，可以设置WindowFeature(如：this.requestWindowFeature(Window.FEATURE_NO_TITLE);)
	public void init() {
	}

	public void initView() {
	}

	public void initData() {
	}

	public void initListener() {
	}

	//用于创建Presenter和判断是否使用MVP模式(由子类实现)
	protected abstract T createPresenter();

	//得到当前界面的布局文件id(由子类实现)
	protected abstract int provideContentViewId();

	//首次启动
	public void onFirstLaunchThisVersion() {
		if (MyApp.isFirstLaunchThisVersion(this, this.getClass())) {
			onFirstLaunchThisVersionDo();
			hasFirstLaunch(this.getClass());
		}
	}

	public void onFirstLaunchThisVersionDo() {

	}

	/**
	 * 是否让Toolbar有返回按钮(默认可以，一般一个应用中除了主界面，其他界面都是可以有返回按钮的)
	 */
	protected boolean isToolbarCanBack() {
		return true;
	}

	/**
	 * 显示等待提示框
	 */
	public Dialog showWaitingDialog(String tip) {
		hideWaitingDialog();
		View view = View.inflate(this, R.layout.dialog_waiting, null);
		if (!TextUtils.isEmpty(tip))
			((TextView) view.findViewById(R.id.tvTip)).setText(tip);
		mDialogWaiting = new CustomDialog(this, view, R.style.MyDialog);
		if (!isFinishing()) {
			mDialogWaiting.show();
			mDialogWaiting.setCancelable(false);
		}
		return mDialogWaiting;
	}

	/**
	 * 隐藏等待提示框
	 */
	public void hideWaitingDialog() {
		if (mDialogWaiting != null) {
			mDialogWaiting.dismiss();
			mDialogWaiting = null;
		}
	}

	/**
	 * 显示MaterialDialog
	 */
	public MaterialDialog showMaterialDialog(String title, String message, String positiveText, String negativeText, View.OnClickListener positiveButtonClickListener, View.OnClickListener negativeButtonClickListener) {
		hideMaterialDialog();
		mMaterialDialog = new MaterialDialog(this);
		if (!TextUtils.isEmpty(title)) {
			mMaterialDialog.setTitle(title);
		}
		if (!TextUtils.isEmpty(message)) {
			mMaterialDialog.setMessage(message);
		}
		if (!TextUtils.isEmpty(positiveText)) {
			mMaterialDialog.setPositiveButton(positiveText, positiveButtonClickListener);
		}
		if (!TextUtils.isEmpty(negativeText)) {
			mMaterialDialog.setNegativeButton(negativeText, negativeButtonClickListener);
		}
		mMaterialDialog.show();
		return mMaterialDialog;
	}

	public MaterialDialog showMaterialDialog(String message, String positiveText) {
		hideMaterialDialog();
		mMaterialDialog = new MaterialDialog(this);
		if (!TextUtils.isEmpty(message)) {
			mMaterialDialog.setMessage(message);
		}
		if (!TextUtils.isEmpty(positiveText)) {
			mMaterialDialog.setPositiveButton(positiveText, new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					mMaterialDialog.dismiss();
				}
			});
		}
		mMaterialDialog.show();
		return mMaterialDialog;
	}

	/**
	 * 隐藏MaterialDialog
	 */
	public void hideMaterialDialog() {
		if (mMaterialDialog != null) {
			mMaterialDialog.dismiss();
			mMaterialDialog = null;
		}
	}

	public void jumpToActivity(Intent intent) {
		startActivity(intent);
	}

	public void jumpToActivity(Class activity) {
		Intent intent = new Intent(this, activity);
		startActivity(intent);
	}

	public void jumpToWebViewActivity(String url) {
		Intent intent = new Intent(this, WebViewActivity.class);
		intent.putExtra("url", url);
		jumpToActivity(intent);
	}

	public void jumpToWebViewActivity(String url, boolean gameMode) {
		Intent intent = new Intent(this, WebViewActivity.class);
		intent.putExtra("url", url);
		if (gameMode) {
			intent.putExtra(AppConst.WEB_GAME_MODE, true);
		}
		jumpToActivity(intent);
	}


	public void jumpToActivityAndClearTask(Class activity) {
		Intent intent = new Intent(this, activity);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
		startActivity(intent);
		finish();
	}

	public void jumpToActivityAndClearTop(Class activity) {
		Intent intent = new Intent(this, activity);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}

	public void jumpToActivityByUrl(String url) {
		//如果没有安装这个应用会导致崩溃必须捕获异常
		try {
			startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void jumpToActivityAndClearHistory(Class activity) {
		Intent intent = new Intent(this, activity);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
		intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		new Handler().postDelayed(() -> {
			startActivity(intent);
			finish();
		}, AppConst.SPLASH_DELAY);


//        startActivity(intent);
//        finish();
	}

	/*------------------ toolbar的一些视图操作 ------------------*/
	public void setToolbarTitle(String title) {

		mToolbarTitle.setText(title);
	}

	public void setToolbarSubTitle(String subTitle) {

	}

	public void onLoadDataFinished() {
		LogUtils.d("load_data_finished");
	}

	protected boolean isSingleActivity() {
		ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		if (am.getRunningTasks(1).size() > 1) {
			return false;
		}
		return true;
	}

	public void hasFirstLaunch(Class clazz) {
		SPUtils.getInstance(this).putBoolean(AppConst.FIRST_LAUNCH + MyApp.getVersionCode(this) + clazz.getName(), false);
	}

}
