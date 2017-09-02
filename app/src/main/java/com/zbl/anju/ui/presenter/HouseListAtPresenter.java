package com.zbl.anju.ui.presenter;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.zbl.anju.R;
import com.zbl.anju.ui.base.BaseActivity;
import com.zbl.anju.ui.base.BasePresenter;
import com.zbl.anju.ui.view.IHouseLIistAtView;

import java.util.List;

/**
 *
 * Created by James on 17-9-1.
 */

public class HouseListAtPresenter extends BasePresenter<IHouseLIistAtView> {

	private ListView mListViewHouseTypes;
	private ListView mListViewPrices;

	private List<String> data;

	private String headers[] = {"户型", "价格"};

	private String mHouseTypes[] = {"一室", "两室", "三室", "合租"};
	private String mPrices[] = {"1000以下", "1000-1500", "1500-2000",  "2000以上"};

	private int typeIndex= 0;       //当前选中的户型
	private int priceIndex= 0;      //当前选中的价格

	public void setTypeIndex(int typeIndex) {
		this.typeIndex = typeIndex;
	}

	public void setPriceIndex(int priceIndex) {
		this.priceIndex = priceIndex;
	}

	public HouseListAtPresenter(BaseActivity context) {
		super(context);
	}


	/**
	 * 初始化两个listview
	 */
	public void initDropDownMenu() {
		mListViewHouseTypes = getView().getListViewHouseTypes();
		mListViewHouseTypes.setAdapter( new ArrayAdapter<>( mContext, R.layout.simple_list_item_single_choice , mHouseTypes ));
		mListViewHouseTypes.setItemChecked(0,true);


		mListViewPrices = getView().getListViewPrices();
		mListViewPrices.setAdapter( new ArrayAdapter<>( mContext, R.layout.simple_list_item_single_choice , mPrices ));
		mListViewPrices.setItemChecked(0,true);


	}

	public void loadHouses(){

	}
}
