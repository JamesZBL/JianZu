package com.zbl.anju.ui.presenter;

import android.Manifest;

import com.tencent.open.utils.HttpUtils;
import com.tencent.tauth.IRequestListener;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.zbl.anju.R;
import com.zbl.anju.api.ApiRetrofit;
import com.zbl.anju.app.AppConst;
import com.zbl.anju.model.cache.UserCache;
import com.zbl.anju.model.exception.ServerException;
import com.zbl.anju.sdk.MyTecncent;
import com.zbl.anju.ui.activity.LoginActivity;
import com.zbl.anju.ui.activity.MainActivity;
import com.zbl.anju.ui.base.BaseActivity;
import com.zbl.anju.ui.base.BasePresenter;
import com.zbl.anju.ui.view.ILoginAtView;
import com.zbl.anju.util.LogUtils;
import com.zbl.anju.util.UIUtils;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;

import kr.co.namee.permissiongen.PermissionGen;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginAtPresenter extends BasePresenter<ILoginAtView> {

	private Tencent mTencent;
	private BaseUiListener mUiListener;
	private BaseApiListener mApiListener;

	public LoginAtPresenter(BaseActivity context) {
		super(context);
		mTencent = MyTecncent.getInstance();
		mUiListener = new BaseUiListener();
		mApiListener = new BaseApiListener();
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

	/**
	 * qq登录
	 */
	public void loginByQQ() {
		initQQPermission();         //申请权限
		if (!mTencent.isSessionValid()) {
			mTencent.login(mContext, "all", mUiListener);
		}
	}

	/**
	 * 申请qq所需权限
	 */
	private void initQQPermission() {
		PermissionGen.with(mContext)
				.addRequestCode(AppConst.INIT_PERMISSION_REQ_CODE)
				.permissions(
						Manifest.permission.WRITE_APN_SETTINGS
				)
				.request();
	}

	/**
	 * 获取ui监听器
	 *
	 * @return
	 */
	public IUiListener getIUiListener() {
		return this.mUiListener;
	}


	class BaseUiListener implements IUiListener {


		@Override
		public void onComplete(Object o) {

		}

		@Override
		public void onError(UiError e) {
			showResult("onError:", "code:" + e.errorCode + ", msg:"
					+ e.errorMessage + ", detail:" + e.errorDetail);
		}

		@Override
		public void onCancel() {
			showResult("onCancel", "");
		}
	}


	/**
	 *
	 */
	class BaseApiListener implements IRequestListener {

		public void onUnknowException(Exception e, Object state) {
			// 出现未知错误时会触发此异常
		}

		@Override
		public void onComplete(JSONObject jsonObject) {

		}

		@Override
		public void onIOException(IOException e) {

		}

		@Override
		public void onMalformedURLException(MalformedURLException e) {

		}

		@Override
		public void onJSONException(JSONException e) {

		}

		@Override
		public void onConnectTimeoutException(ConnectTimeoutException e) {

		}

		@Override
		public void onSocketTimeoutException(SocketTimeoutException e) {

		}

		@Override
		public void onNetworkUnavailableException(HttpUtils.NetworkUnavailableException e) {

		}

		@Override
		public void onHttpStatusException(HttpUtils.HttpStatusException e) {

		}

		@Override
		public void onUnknowException(Exception e) {

		}
	}

	private void showResult(String str1, String str2) {
		mContext.showToast(str1 + "\n" + str2);
	}
}
