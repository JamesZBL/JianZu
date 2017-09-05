package com.zbl.anju.ui.activity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.zbl.anju.R;
import com.zbl.anju.ui.base.BaseActivity;
import com.zbl.anju.ui.base.BasePresenter;

import butterknife.Bind;

/**
 * 发布房源
 * Created by James on 17-9-4.
 */

public class HouseReleaseActivity extends BaseActivity {

	@Bind(R.id.et_sell_title)
	EditText mEdtTitle;
	@Bind(R.id.et_sell_description)
	EditText mEdtDescription;
	@Bind(R.id.iv_house_release_video_thumbnail)
	ImageView ivThumbnail;
	@Bind(R.id.iv_house_add_video)
	ImageView ivPlus;
	@Bind(R.id.btn_sell_release)
	Button btnRelease;

	private static final int REQUEST_CODE = 0x00000012;

	@Override
	protected BasePresenter createPresenter() {
		return null;
	}

	@Override
	protected int provideContentViewId() {
		return R.layout.activity_house_release;
	}

	@Override
	public void initView() {
		super.initView();
		setToolbarTitle("房源发布");
	}

	@Override
	public void initListener() {
		super.initListener();
		btnRelease.setOnClickListener(v -> {
			showWaitingDialog("发布中");
			new Handler().postDelayed(() -> {
				hideWaitingDialog();
				jumpToActivityAndClearTask(ResultOkActivity.class);
				finish();
			}, 3000);
		});
		ivPlus.setOnClickListener(v -> {
			//选取视频
			selectVideo();
		});
	}


	/**
	 * 选取视频
	 */
	private void selectVideo() {
		Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(i, REQUEST_CODE);
	}

	/**
	 * 接受返回结果
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && null != data) {
			Uri selectedVideo = data.getData();
			String[] filePathColumn = {MediaStore.Video.Media.DATA};

			Cursor cursor = getContentResolver().query(selectedVideo,
					filePathColumn, null, null, null);
			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String videoPath = cursor.getString(columnIndex);
			cursor.close();
			setImage(videoPath);        //设置缩略图
		}
	}

	/**
	 * 根据视频url设置缩略图
	 * @param videoPath
	 */
	private void setImage(String videoPath) {
		ivThumbnail.setVisibility(View.VISIBLE);
		ivPlus.setVisibility(View.GONE);
	}
}
