package com.zbl.anju.ui.presenter;

import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.lqr.adapter.LQRAdapterForRecyclerView;
import com.lqr.adapter.LQRViewHolderForRecyclerView;
import com.zbl.anju.R;
import com.zbl.anju.db.DBManager;
import com.zbl.anju.model.data.House;
import com.zbl.anju.ui.activity.HouseInfoActivity;
import com.zbl.anju.ui.base.BaseActivity;
import com.zbl.anju.ui.base.BasePresenter;
import com.zbl.anju.ui.view.IHouseLIistAtView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 房屋列表
 * Created by James on 17-9-1.
 */

public class HouseListAtPresenter extends BasePresenter<IHouseLIistAtView> {

	private ListView mListViewHouseTypes;
	private ListView mListViewPrices;

	private List<String> data;

	private String headers[] = {"户型", "价格"};

	private String mHouseTypes[] = {"一室", "两室", "三室"};
	private String mPrices[] = {"1000以下", "1000-1500", "1500-2000", "2000以上"};

	private int typeIndex = 0;       //当前选中的户型
	private int priceIndex = 0;      //当前选中的价格

	private List<House> mData = new ArrayList<>();
	private LQRAdapterForRecyclerView<House> mAdapter;


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
		mListViewHouseTypes.setAdapter(new ArrayAdapter<>(mContext, R.layout.simple_list_item_single_choice, mHouseTypes));
		mListViewHouseTypes.setItemChecked(0, true);


		mListViewPrices = getView().getListViewPrices();
		mListViewPrices.setAdapter(new ArrayAdapter<>(mContext, R.layout.simple_list_item_single_choice, mPrices));
		mListViewPrices.setItemChecked(0, true);


	}

	/**
	 * 初次加载数据
	 */
	public void initData() {
		loadHouses("一室", "1000以下");
	}

	/**
	 * 加载房源
	 */
	public void loadHouses(String houseTypeStr, String priceStr) {
		loadData(houseTypeStr, priceStr);
		setAdapter();
	}

	/**
	 * 适配器
	 */
	private void setAdapter() {
		if (mAdapter == null) {
			mAdapter = new LQRAdapterForRecyclerView<House>(mContext, mData, R.layout.item_house_info) {
				@Override
				public void convert(LQRViewHolderForRecyclerView helper, House item, int position) {

					CircleImageView ivHeader = helper.getView(R.id.ivHeader);
					/* 加载缩略图 */
					Glide.with(mContext).load(item.getUrlVideoThumbnail()).into(ivHeader);

					helper.setText(R.id.tvDescTitle, item.getXiaoquName())
							.setText(R.id.tv_release, item.getReleaseTime())
							.setText(R.id.tv_price, item.getPrice())
							.setText(R.id.tvHouseType, item.getType())
							.setText(R.id.tvDistance, item.getDistance());

				}
			};
			mAdapter.setOnItemClickListener((helper, parent, itemView, position) -> {
				Intent intent = new Intent(mContext, HouseInfoActivity.class);
				House item = mData.get(position);
//				intent.putExtra("sessionId", item.getTargetId());

				mContext.jumpToActivity(intent);
			});

			getView().getRvHouses().setAdapter(mAdapter);
		}
	}

	/**
	 * 加载房源
	 */
	private void loadData(String type, String price) {
		List<House> houseList = DBManager.getInstance().getHouseList(type, price);
		mData.clear();
		mData.addAll(houseList);
	}

	public void reloadHouses() {
		int typeIndex = this.typeIndex;
		int priceIndex = this.priceIndex;
		String priceStr = "";
		String typeStr = "";
		switch (priceIndex) {
			case 0: {
				priceStr = "1000以下";
				break;
			}
			case 1: {
				priceStr = "1000-1500";
				break;
			}
			case 2: {
				priceStr = "1500-2000";
				break;
			}
			case 3: {
				priceStr = "2000以上";
				break;
			}
		}
		switch (typeIndex) {
			case 0: {
				typeStr = "一室";
				break;
			}
			case 1: {
				typeStr = "两室";
				break;
			}
			case 2: {
				typeStr = "三室";
				break;
			}
			case 3: {
				typeStr = "合租";
				break;
			}
		}

		getView().getRvHouses().removeAllViews();
		loadHouses(typeStr, priceStr);
	}
}
