package com.zbl.anju.ui.activity;

import android.content.Intent;
import android.widget.ImageView;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.tencent.open.utils.HttpUtils;
import com.tencent.tauth.IRequestListener;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.zbl.anju.R;
import com.zbl.anju.ui.base.BaseActivity;
import com.zbl.anju.ui.presenter.LoginAtPresenter;
import com.zbl.anju.ui.view.ILoginAtView;
import com.zbl.anju.widget.StateButton;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;

import butterknife.Bind;

/**
 * 登录界面
 *
 * @author James
 */
public class LoginActivity extends BaseActivity<ILoginAtView, LoginAtPresenter> implements ILoginAtView {

	@Bind(R.id.login_edt_username)
	MaterialEditText mEdtUsername;
	@Bind(R.id.login_edt_password)
	MaterialEditText mEdtPassword;
	@Bind(R.id.btn_login)
	StateButton mBtnLogin;

	@Bind(R.id.iv_qq_login)
	ImageView mIvQQLogin;
	@Bind(R.id.iv_wechat_login)
	ImageView mIvWechatLogin;
	@Bind(R.id.iv_alipay_login)
	ImageView mIvAlipayLogin;


	@Override
	protected int provideContentViewId() {
		return R.layout.activity_login_anju;
	}


	@Override
	public void initView() {
		super.initView();
	}


	@Override
	public void init() {
		super.init();
		registerBR();
	}

	@Override
	public void initData() {
		super.initData();
	}

	@Override
	public void initListener() {
		super.initListener();

		/* 登录 */
		mBtnLogin.setOnClickListener(v -> {
			mPresenter.login();
		});

		/* qq登录 */
		mIvQQLogin.setOnClickListener(v -> {
			mPresenter.loginByQQ();
		});
	}

	@Override
	protected LoginAtPresenter createPresenter() {
		return new LoginAtPresenter(this);
	}

	private void registerBR() {

	}

	/**
	 * toolbar不显示返回箭头按钮
	 *
	 * @return
	 */
	@Override
	protected boolean isToolbarCanBack() {
		return false;
	}


	@Override
	public void onFirstLaunchThisVersionDo() {
		super.onFirstLaunchThisVersionDo();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}

	@Override
	public MaterialEditText getEdtUsername() {
		return mEdtUsername;
	}

	@Override
	public MaterialEditText getEdtPassword() {
		return mEdtPassword;
	}

	@Override
	public StateButton getBtnLogin() {
		return mBtnLogin;
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Tencent.onActivityResultData(requestCode, resultCode, data, mPresenter.getIUiListener());
	}


}
