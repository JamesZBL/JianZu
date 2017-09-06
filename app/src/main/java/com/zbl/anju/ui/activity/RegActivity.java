package com.zbl.anju.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.widget.Button;

import com.zbl.anju.R;
import com.zbl.anju.ui.base.BaseActivity;
import com.zbl.anju.ui.base.BasePresenter;

import butterknife.Bind;

/**
 * 注册
 * Created by James on 17-9-4.
 */

public class RegActivity extends BaseActivity {

	@Bind(R.id.btn_submit_reg)
	Button mBtnSubmitReg;

	@Override
	protected BasePresenter createPresenter() {
		return null;
	}

	@Override
	protected int provideContentViewId() {
		return R.layout.activity_user_reg;
	}

	@Override
	public void initListener() {
		super.initListener();
		mBtnSubmitReg.setOnClickListener(v -> {
			showWaitingDialog("正在提交");
			new Handler().postDelayed(() -> {
				hideWaitingDialog();
				Intent i = new Intent(this, ResultOkActivity.class);
				i.putExtra("msg","认证成功");
				startActivity(i);
				finish();
			}, 1000);
		});
	}

	@Override
	public void initView() {
		super.initView();
		setToolbarTitle("实名认证");
	}
}
