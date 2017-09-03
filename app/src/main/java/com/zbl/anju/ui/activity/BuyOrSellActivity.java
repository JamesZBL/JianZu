package com.zbl.anju.ui.activity;

import com.zbl.anju.R;
import com.zbl.anju.ui.base.BaseActivity;
import com.zbl.anju.ui.base.BasePresenter;
import com.zhy.autolayout.AutoRelativeLayout;

import butterknife.Bind;

/**
 * 买和卖
 * Created by James on 17-9-3.
 */

public class BuyOrSellActivity extends BaseActivity {

	@Bind(R.id.all_i_wanna_buy)
	AutoRelativeLayout allIWannaBuy;

	@Override
	protected BasePresenter createPresenter() {
		return null;
	}

	@Override
	protected int provideContentViewId() {
		return R.layout.activity_buy_or_sell;
	}

	@Override
	public void initView() {
		super.initView();
		setToolbarTitle("二手买卖");
	}

	@Override
	public void initListener() {
		super.initListener();
		/* 我要买 */
		allIWannaBuy.setOnClickListener(v->{
			jumpToActivityAndClearTask(BuyClassifyActivity.class);
			finish();
		});
	}
}
