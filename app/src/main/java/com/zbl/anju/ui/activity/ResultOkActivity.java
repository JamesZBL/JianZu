package com.zbl.anju.ui.activity;

import android.widget.Button;
import android.widget.TextView;

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
	@Bind(R.id.tv_ok_msg)
	TextView tvOkMsg;

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
		btnDone.setOnClickListener(v -> {
			finish();
		});
	}

	@Override
	public void initView() {
		super.initView();
		setToolbarTitle("发布成功");
	}

	@Override
	public void initData() {
		super.initData();
		try {
			String msg = getIntent().getExtras().getString("msg");
			setMsg(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setMsg(String msg) {
		tvOkMsg.setText(msg);
		setToolbarTitle(msg);
	}
}
