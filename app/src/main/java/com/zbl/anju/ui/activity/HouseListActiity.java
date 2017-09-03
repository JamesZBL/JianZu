package com.zbl.anju.ui.activity;

import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.lqr.recyclerview.LQRRecyclerView;
import com.zbl.anju.R;
import com.zbl.anju.ui.base.BaseActivity;
import com.zbl.anju.ui.presenter.HouseListAtPresenter;
import com.zbl.anju.ui.view.IHouseLIistAtView;
import com.zbl.anju.util.UIUtils;
import com.zhy.autolayout.AutoLinearLayout;

import butterknife.Bind;

/**
 * 房屋列表
 * Created by James on 17-9-1.
 */

public class HouseListActiity extends BaseActivity<IHouseLIistAtView, HouseListAtPresenter> implements IHouseLIistAtView {

	@Bind(R.id.all_house_list_tab_house_type)
	AutoLinearLayout allTabHouseType;       //户型toggle
	@Bind(R.id.all_house_list_tab_prices)
	AutoLinearLayout allTabPrices;          //价格toggle
	@Bind(R.id.all_menu_house_types)
	AutoLinearLayout allMenuHouseTypes;     //户型菜单
	@Bind(R.id.all_menu_prices)
	AutoLinearLayout allMenuPrices;         //价格菜单
	@Bind(R.id.lv_menu_house_types)
	ListView lvMenuHouseTypes;              //户型listView
	@Bind(R.id.lv_menu_prices)
	ListView lvMenuPrices;                  //价格listView
	@Bind(R.id.iv_tab_tri_type)
	ImageView iv_tri_0;                     //户型tri
	@Bind(R.id.iv_tab_tri_price)
	ImageView iv_tri_1;                     //价格tri

	@Bind(R.id.v_type)
	View v_0;                               //遮罩
	@Bind(R.id.v_price)
	View v_1;                               //遮罩

	@Bind(R.id.rv_house_list)
	LQRRecyclerView rvHouseInfo;            //房源recyclerView

	private boolean isDropOpened = false;
	private int openIndex = -1;

	@Override
	protected HouseListAtPresenter createPresenter() {
		return new HouseListAtPresenter(this);
	}

	@Override
	public void initView() {
		super.initView();
		mPresenter.initDropDownMenu();
	}

	@Override
	public void initData() {
		super.initData();
		mPresenter.initData();
	}

	@Override
	protected int provideContentViewId() {
		return R.layout.activity_house_list;
	}


	@Override
	public void initListener() {
		super.initListener();
		allTabHouseType.setOnClickListener(v -> {
			if (openIndex == 0 && allMenuHouseTypes.getVisibility() == View.VISIBLE) {
				closeAllDrop();
				return;
			}
			openDropDown(0);
		});
		allTabPrices.setOnClickListener(v -> {
			if (openIndex == 1 && allMenuPrices.getVisibility() == View.VISIBLE) {
				closeAllDrop();
				return;
			}
			openDropDown(1);
		});
		lvMenuHouseTypes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				mPresenter.setTypeIndex(i);
				mPresenter.reloadHouses();
				closeAllDrop();
			}
		});
		lvMenuPrices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				mPresenter.setPriceIndex(i);
				mPresenter.reloadHouses();
				closeAllDrop();
			}
		});

		v_0.setOnClickListener(v -> {
			closeAllDrop();
		});
		v_1.setOnClickListener(v -> {
			closeAllDrop();
		});
	}

	@Override
	public ListView getListViewHouseTypes() {
		return lvMenuHouseTypes;
	}

	@Override
	public ListView getListViewPrices() {
		return lvMenuPrices;
	}

	@Override
	public LQRRecyclerView getRvHouses() {
		return rvHouseInfo;
	}

	/**
	 * 关闭 所有下拉菜单
	 */
	private void closeAllDrop() {
		clearTabTri();
		if (allMenuHouseTypes.getVisibility() == View.VISIBLE) {
			UIUtils.startAnimation(allMenuHouseTypes, R.anim.anim_drop_up_menu);
			allMenuHouseTypes.setVisibility(View.GONE);
		}
		if (allMenuPrices.getVisibility() == View.VISIBLE) {
			UIUtils.startAnimation(allMenuPrices, R.anim.anim_drop_up_menu);
			allMenuPrices.setVisibility(View.GONE);
		}
		isDropOpened = false;
	}

	/**
	 * 复位tab上的三角
	 */
	private void clearTabTri() {
		iv_tri_0.setImageDrawable(getResources().getDrawable(R.drawable.ic_down_tri));
		iv_tri_1.setImageDrawable(getResources().getDrawable(R.drawable.ic_down_tri));
	}


	/**
	 * 设置tab三角
	 */
	private void setTri(int index) {
		clearTabTri();
		switch (index) {
			case 0: {
				iv_tri_0.setImageDrawable(getResources().getDrawable(R.drawable.ic_up_tri));
				break;
			}
			case 1: {
				iv_tri_1.setImageDrawable(getResources().getDrawable(R.drawable.ic_up_tri));
				break;
			}
		}
	}

	public void openDropDown(int i) {

		//每次开启之前先关闭
		closeAllDrop();
		switch (i) {
			case 0: {
				allMenuHouseTypes.setVisibility(View.VISIBLE);
				UIUtils.startAnimation(allMenuHouseTypes, R.anim.anim_drop_down_menu);
				openIndex = 0;
				setTri(0);
				break;
			}
			case 1: {
				allMenuPrices.setVisibility(View.VISIBLE);
				UIUtils.startAnimation(allMenuPrices, R.anim.anim_drop_down_menu);
				openIndex = 1;
				setTri(1);
				break;
			}
		}
		this.isDropOpened = true;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK)
			if (isDropOpened) {
				closeAllDrop();
				return true;
			}
		return super.onKeyDown(keyCode, event);
	}
}
