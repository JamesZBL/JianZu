package com.zbl.anju.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.donkingliang.imageselector.adapter.ImageAdapter;
import com.donkingliang.imageselector.entry.Image;
import com.donkingliang.imageselector.utils.ImageSelectorUtils;
import com.zbl.anju.R;
import com.zbl.anju.ui.base.BaseActivity;
import com.zbl.anju.ui.base.BasePresenter;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * 发布二手物品
 * Created by James on 17-9-4.
 */

public class SellReleaseActivity extends BaseActivity {

	@Bind(R.id.et_sell_title)
	EditText mEdtTitle;
	@Bind(R.id.et_sell_description)
	EditText mEdtDescription;
	@Bind(R.id.rv_sell_imgs)
	RecyclerView rvImages;
	@Bind(R.id.iv_sell_add_pic)
	ImageView ivAddImages;
	@Bind(R.id.btn_sell_release)
	Button btnRelease;

	private static final int REQUEST_CODE = 0x00000011;
	private ImageAdapter mAdapter;

	@Override
	protected BasePresenter createPresenter() {
		return null;
	}

	@Override
	protected int provideContentViewId() {
		return R.layout.activity_sell_release;
	}

	@Override
	public void initView() {
		super.initView();
		setToolbarTitle("宝贝发布");
		rvImages.setLayoutManager(new GridLayoutManager(this, 3));
		mAdapter = new ImageAdapter(this, 3, false);
		rvImages.setAdapter(mAdapter);
	}

	@Override
	public void initListener() {
		super.initListener();
		ivAddImages.setOnClickListener(v -> {
			//多选(最多9张)
			ImageSelectorUtils.openPhoto(this, REQUEST_CODE, false, 3);
		});
		btnRelease.setOnClickListener(v -> {
			showWaitingDialog("发布中");
			new Handler().postDelayed(() -> {
				hideWaitingDialog();
				jumpToActivityAndClearTask(ResultOkActivity.class);
				finish();
			}, 3000);
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_CODE && data != null) {
			ArrayList<String> images = data.getStringArrayListExtra(ImageSelectorUtils.SELECT_RESULT);
			ArrayList<Image> imageEntities = new ArrayList<>();
			for (String imgUrl : images
					) {
				Image img = new Image(imgUrl, 0, "");
				imageEntities.add(img);
			}
			mAdapter.refresh(imageEntities);
		}
	}
}
