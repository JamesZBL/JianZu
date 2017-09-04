package com.zbl.anju.ui.activity;

import com.zbl.anju.R;
import com.zbl.anju.ui.base.BaseActivity;
import com.zbl.anju.ui.base.BasePresenter;

/**
 * 注册
 * Created by James on 17-9-4.
 */

public class RegActivity extends BaseActivity {
	@Override
	protected BasePresenter createPresenter() {
		return null;
	}

	@Override
	protected int provideContentViewId() {
		return R.layout.activity_user_reg;
	}

	@Override
	public void initView() {
		super.initView();
		setToolbarTitle("实名认证");
	}
}
