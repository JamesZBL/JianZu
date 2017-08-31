package com.zbl.anju.ui.activity;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.zbl.anju.R;
import com.zbl.anju.ui.base.BaseActivity;
import com.zbl.anju.ui.presenter.LoginAtPresenter;
import com.zbl.anju.ui.view.ILoginAtView;
import com.zbl.anju.widget.StateButton;

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
		mBtnLogin.setOnClickListener(v->{
			mPresenter.login();
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
}
