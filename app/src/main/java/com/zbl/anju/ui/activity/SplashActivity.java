package com.zbl.anju.ui.activity;

import android.os.Handler;

import com.zbl.anju.R;
import com.zbl.anju.api.ApiRetrofit;
import com.zbl.anju.app.AppConst;
import com.zbl.anju.model.cache.UserCache;
import com.zbl.anju.ui.base.BasePresenter;
import com.zbl.anju.ui.base.BaseSplashActivity;
import com.zbl.anju.util.LogUtils;
import com.zbl.anju.util.StringUtils;
import com.zbl.anju.util.UIUtils;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author James
 * @描述 启动页
 */
public class SplashActivity extends BaseSplashActivity {


	private boolean isPwdValid = false;

	@Override
	public void initData() {
		login();
	}

	/**
	 * 验证用户身份
	 */
	public void login() {
		String password = UserCache.getPwd();
		if (StringUtils.isEmpty(password)) {
			startLoginAct();
			return;
		}
		ApiRetrofit.getInstance().loginSimple("", "", password)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(loginResponse -> {
					int code = loginResponse.getCode();
					if (code == 200) {
						//验证通过
						/**/
						/**/
						/**/
						startMainAct();
					} else {
						//验证失败
						UIUtils.showToast(UIUtils.getString(R.string.id_invalidate_please_login_again));
						startLoginAct();
					}
				}, this::loginError);
	}

	/**
	 * 启动主页
	 */
	protected void startMainAct() {
		new Handler().postDelayed(() -> {
			jumpToActivityAndClearTask(MainActivity.class);
			finish();
		}, AppConst.SPLASH_DELAY);

	}

	/**
	 * 启动登录
	 */
	protected void startLoginAct() {
		jumpToActivityAndClearTask(LoginActivity.class);
		finish();
	}


	@Override
	public void initView() {
		super.initView();
	}


	@Override
	protected int provideContentViewId() {
		return R.layout.activity_splash;
	}


	@Override
	public void initListener() {

	}

	@Override
	protected BasePresenter createPresenter() {
		return null;
	}


	/**
	 * 登录失败（网络连接问题）
	 */
	private void loginError(Throwable throwable) {
		LogUtils.e(throwable.getLocalizedMessage());
		UIUtils.showToast(UIUtils.getString(R.string.please_check_net));
		jumpToActivityAndClearTask(LoginActivity.class);   //跳转到登录
		finish();
	}
}
