package com.zbl.anju.ui.activity;

import android.widget.Button;

import com.zbl.anju.R;
import com.zbl.anju.ui.base.BaseActivity;
import com.zbl.anju.ui.base.BasePresenter;

import butterknife.Bind;

/**
 * 成功
 * Created by James on 17-9-4.
 */

public class ResultOkActivity extends BaseActivity {

	@Bind(R.id.btn_done)
	Button btnDone;

	@Override
	protected BasePresenter createPresenter() {
		return null;
	}

	@Override
	protected int provideContentViewId() {
		return R.layout.activity_sell_ok;
	}

	@Override
	public void initListener() {
		super.initListener();
		btnDone.setOnClickListener(v->{
			finish();
		});
	}

	@Override
	public void initView() {
		super.initView();
		setToolbarTitle("发布成功");
	}
}
