package com.zbl.anju.ui.presenter;

import com.zbl.anju.R;
import com.zbl.anju.api.ApiRetrofit;
import com.zbl.anju.model.cache.UserCache;
import com.zbl.anju.model.exception.ServerException;
import com.zbl.anju.ui.activity.MainActivity;
import com.zbl.anju.ui.base.BaseActivity;
import com.zbl.anju.ui.base.BasePresenter;
import com.zbl.anju.ui.view.ILoginAtView;
import com.zbl.anju.util.LogUtils;
import com.zbl.anju.util.UIUtils;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginAtPresenter extends BasePresenter<ILoginAtView> {

	public LoginAtPresenter(BaseActivity context) {
		super(context);
	}

	/**
	 * 登录
	 */
	public void login() {
		mContext.showWaitingDialog(UIUtils.getString(R.string.login_ing));
		String password = getView().getEdtPassword().getText().toString().trim();
		ApiRetrofit.getInstance().loginSimple("", "", password)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(loginResponse -> {
					int code = loginResponse.getCode();
					mContext.hideWaitingDialog();
					if (code == 200) {
						UserCache.save("", "", password);
						mContext.jumpToActivityAndClearTask(MainActivity.class);
						mContext.finish();
					} else {
						UIUtils.showToast(UIUtils.getString(R.string.login_error));
					}
				}, this::loginError);
	}

	private void loginError(Throwable throwable) {
		LogUtils.e(throwable.getLocalizedMessage());
		UIUtils.showToast(UIUtils.getString(R.string.please_check_net));
		mContext.hideWaitingDialog();
	}
}
