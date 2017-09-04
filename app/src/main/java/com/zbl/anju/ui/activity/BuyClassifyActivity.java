package com.zbl.anju.ui.activity;

import com.lqr.recyclerview.LQRRecyclerView;
import com.zbl.anju.R;
import com.zbl.anju.ui.base.BaseActivity;
import com.zbl.anju.ui.base.BasePresenter;
import com.zbl.anju.ui.presenter.BuyClassifyAtPresenter;
import com.zbl.anju.ui.view.IBuyClassifyAtView;

import butterknife.Bind;

/**
 * 二手购买分类
 * Created by James on 17-9-3.
 */

public class BuyClassifyActivity extends BaseActivity<IBuyClassifyAtView, BuyClassifyAtPresenter> implements IBuyClassifyAtView {

	@Bind(R.id.rvJingPinTuijian)
	LQRRecyclerView rvTuijian;

	@Override
	protected BuyClassifyAtPresenter createPresenter() {
		return new BuyClassifyAtPresenter(this);
	}

	@Override
	protected int provideContentViewId() {
		return R.layout.activity_buy_classify;
	}

	@Override
	public void initData() {
		super.initData();
		mPresenter.initData();
	}

	@Override
	public void initView() {
		super.initView();
		setToolbarTitle("二手买卖");
	}

	@Override
	public LQRRecyclerView getRcTuijian() {
		return rvTuijian;
	}


}
