package com.zbl.anju.ui.activity;

import com.lqr.optionitemview.OptionItemView;
import com.zbl.anju.R;
import com.zbl.anju.ui.base.BaseActivity;
import com.zbl.anju.ui.base.BasePresenter;

import butterknife.Bind;

/**
 * 房屋类型选择
 * Created by James on 17-9-5.
 */

public class HouseTypeActivity extends BaseActivity {

	@Bind(R.id.oiv_house_type_zhengzu)
	OptionItemView mOivZhengzu;
	@Bind(R.id.oiv_house_type_hezu)
	OptionItemView mOivHezu;

	@Override
	protected BasePresenter createPresenter() {
		return null;
	}

	@Override
	protected int provideContentViewId() {
		return R.layout.activity_release_type;
	}

	@Override
	public void initListener() {
		super.initListener();
		mOivZhengzu.setOnClickListener(v->{
			jumpToActivity(HouseReleaseActivity.class);
		});
	}

	@Override
	public void initView() {
		super.initView();
		setToolbarTitle("房源类型");
	}
}
