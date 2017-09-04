package com.zbl.anju.ui.activity;

import com.lqr.recyclerview.LQRRecyclerView;
import com.zbl.anju.R;
import com.zbl.anju.ui.base.BaseActivity;
import com.zbl.anju.ui.presenter.HouseListHezuAtPresenter;
import com.zbl.anju.ui.view.IHouseLIistHezuAtView;

import butterknife.Bind;

/**
 * 房屋列表
 * Created by James on 17-9-1.
 */

public class HouseListHezuActiity extends BaseActivity<IHouseLIistHezuAtView, HouseListHezuAtPresenter> implements IHouseLIistHezuAtView {



	@Bind(R.id.rv_house_list)
	LQRRecyclerView rvHouseInfo;            //房源recyclerView


	@Override
	protected HouseListHezuAtPresenter createPresenter() {
		return new HouseListHezuAtPresenter(this);
	}

	@Override
	public void initView() {
		super.initView();
		setToolbarTitle("合租");
	}

	@Override
	public void initData() {
		super.initData();
		mPresenter.initData();
	}

	@Override
	protected int provideContentViewId() {
		return R.layout.activity_house_list_hezu;
	}


	@Override
	public void initListener() {
		super.initListener();

	}


	@Override
	public LQRRecyclerView getRvHouses() {
		return rvHouseInfo;
	}
}
