package com.zbl.anju.ui.presenter;

import android.content.Intent;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lqr.adapter.LQRAdapterForRecyclerView;
import com.lqr.adapter.LQRViewHolderForRecyclerView;
import com.zbl.anju.R;
import com.zbl.anju.db.DBManager;
import com.zbl.anju.model.data.ErShouThing;
import com.zbl.anju.model.data.House;
import com.zbl.anju.ui.activity.HouseInfoActivity;
import com.zbl.anju.ui.base.BaseActivity;
import com.zbl.anju.ui.base.BasePresenter;
import com.zbl.anju.ui.view.IBuyClassifyAtView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 买分类
 * Created by James on 17-9-4.
 */

public class BuyClassifyAtPresenter extends BasePresenter<IBuyClassifyAtView> {

	private List<ErShouThing> mData = new ArrayList<>();
	private LQRAdapterForRecyclerView<ErShouThing> mAdapter;

	public BuyClassifyAtPresenter(BaseActivity context) {
		super(context);
	}

	/**
	 * 加载推荐
	 */
	public void initData() {
		loadData();
		setAdapter();
	}

	/**
	 * 设置适配器
	 */
	private void setAdapter() {
		mAdapter = new LQRAdapterForRecyclerView<ErShouThing>(mContext, mData, R.layout.item_ershou_info) {
			@Override
			public void convert(LQRViewHolderForRecyclerView helper, ErShouThing item, int position) {

				ImageView ivHeader = helper.getView(R.id.ivHeader);
					/* 加载缩略图 */
				Glide.with(mContext).load(item.getImgUrl()).centerCrop().into(ivHeader);

				helper.setText(R.id.tvDescTitle, item.getName())
						.setText(R.id.tv_release, item.getRerleaseTime())
						.setText(R.id.tv_price, item.getPrice())
						.setText(R.id.tvDistance, item.getDistance());

			}
		};


		getView().getRcTuijian().setAdapter(mAdapter);
	}

	/**
	 * 加载数据
	 */
	private void loadData() {
		List<ErShouThing> ershouList = DBManager.getInstance().getErshouTuijian();
		mData.clear();
		mData.addAll(ershouList);
	}

}
