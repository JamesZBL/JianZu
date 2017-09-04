package com.zbl.anju.ui.presenter;

import android.content.Intent;
import android.widget.ArrayAdapter;
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
import com.zbl.anju.ui.view.IHouseLIistHezuAtView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 房屋列表
 * Created by James on 17-9-1.
 */

public class HouseListHezuAtPresenter extends BasePresenter<IHouseLIistHezuAtView> {


	private List<House> mData = new ArrayList<>();
	private LQRAdapterForRecyclerView<House> mAdapter;


	public HouseListHezuAtPresenter(BaseActivity context) {
		super(context);
	}


	/**
	 * 初次加载数据
	 */
	public void initData() {
		loadHouses();
	}

	/**
	 * 加载房源
	 */
	public void loadHouses() {
		loadData();
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
	private void loadData() {
		List<House> houseList = DBManager.getInstance().getHouseListByDistance();
		mData.clear();
		mData.addAll(houseList);
	}
}
