package com.zbl.anju.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialcamera.MaterialCamera;
import com.bumptech.glide.Glide;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.NormalListDialog;
import com.zbl.anju.R;
import com.zbl.anju.ui.base.BaseActivity;
import com.zbl.anju.ui.base.BasePresenter;
import com.zbl.anju.util.UIUtils;
import com.zbl.anju.util.VideoUtils;
import com.zhy.autolayout.AutoLinearLayout;

import java.io.ByteArrayOutputStream;
import java.io.File;

import butterknife.Bind;

/**
 * 发布房源
 * Created by James on 17-9-4.
 */

public class HouseReleaseHezuActivity extends BaseActivity {

	@Bind(R.id.all_zhuangxiu)
	AutoLinearLayout mAllZhuangxiu;
	@Bind(R.id.all_chaoxiang)
	AutoLinearLayout mAllChaoxiang;
	@Bind(R.id.all_zhifu)
	AutoLinearLayout mAllZhifu;


	@Bind(R.id.edt_zhuangxiu)
	TextView edtZhuangXiu;
	@Bind(R.id.edt_chaoxiang)
	TextView edtChaoxiang;
	@Bind(R.id.edt_zhifu)
	TextView edtZhifu;
	@Bind(R.id.edt_house_release_chuzujianleixing)
	TextView edtleixing;
	@Bind(R.id.edt_xingbie)
	TextView edtxingbie;

	@Bind(R.id.iv_house_release_video_thumbnail)
	ImageView ivThumbnail;
	@Bind(R.id.iv_house_add_video)
	ImageView ivPlus;
	@Bind(R.id.btn_sell_release)
	Button btnRelease;

	private final static int CAMERA_RQ = 6970;

	@Override
	protected BasePresenter createPresenter() {
		return null;
	}

	@Override
	protected int provideContentViewId() {
		return R.layout.activity_house_hezu_release;
	}

	@Override
	public void initView() {
		super.initView();
		setToolbarTitle("合租发布");
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
		edtZhuangXiu.setOnClickListener(v -> {
			showListDialog(0);
		});
		edtChaoxiang.setOnClickListener(v -> {
			showListDialog(1);
		});
		edtZhifu.setOnClickListener(v -> {
			showListDialog(2);
		});
		edtleixing.setOnClickListener(v -> {
			showListDialog(3);
		});
		edtxingbie.setOnClickListener(v -> {
			showListDialog(4);
		});
	}


	/**
	 * 选取视频
	 */
	private void selectVideo() {
		File saveFolder = new File(Environment.getExternalStorageDirectory(), "AAAJianzu" + System.currentTimeMillis() + "");
		if (!saveFolder.mkdirs())
			throw new RuntimeException("Unable to create save directory, make sure WRITE_EXTERNAL_STORAGE permission is granted.");

		new MaterialCamera(this)                               // Constructor takes an Activity
				.allowRetry(true)                                  // Whether or not 'Retry' is visible during playback
				.autoSubmit(false)                                 // Whether or not user is allowed to playback videos after recording. This can affect other things, discussed in the next section.
				.saveDir(saveFolder)                               // The folder recorded videos are saved to
				.primaryColorAttr(R.attr.colorPrimary)             // The theme color used for the camera, defaults to colorPrimary of Activity in the constructor
				.showPortraitWarning(true)                         // Whether or not a warning is displayed if the user presses record in portrait orientation
				.defaultToFrontFacing(false)                       // Whether or not the camera will initially show the front facing camera
				.retryExits(false)                                 // If true, the 'Retry' button in the playback screen will exit the camera instead of going back to the recorder
				.restartTimerOnRetry(false)                        // If true, the countdown timer is reset to 0 when the user taps 'Retry' in playback
				.continueTimerInPlayback(false)                    // If true, the countdown timer will continue to go down during playback, rather than pausing.
				.videoEncodingBitRate(1024000)                     // Sets a custom bit rate for video recording.
				.audioEncodingBitRate(50000)                       // Sets a custom bit rate for audio recording.
				.videoFrameRate(24)                                // Sets a custom frame rate (FPS) for video recording.
				.qualityProfile(MaterialCamera.QUALITY_HIGH)       // Sets a quality profile, manually setting bit rates or frame rates with other settings will overwrite individual quality profile settings
				.videoPreferredHeight(720)                         // Sets a preferred height for the recorded video output.
				.videoPreferredAspect(4f / 3f)                     // Sets a preferred aspect ratio for the recorded video output.
				.maxAllowedFileSize(1024 * 1024 * 5)               // Sets a max file size of 5MB, recording will stop if file reaches this limit. Keep in mind, the FAT file system has a file size limit of 4GB.
				.iconRecord(R.drawable.mcam_action_capture)        // Sets a custom icon for the button used to start recording
				.iconStop(R.drawable.mcam_action_stop)             // Sets a custom icon for the button used to stop recording
				.iconFrontCamera(R.drawable.mcam_camera_front)     // Sets a custom icon for the button used to switch to the front camera
				.iconRearCamera(R.drawable.mcam_camera_rear)       // Sets a custom icon for the button used to switch to the rear camera
				.iconPlay(R.drawable.evp_action_play)              // Sets a custom icon used to start playback
				.iconPause(R.drawable.evp_action_pause)            // Sets a custom icon used to pause playback
				.iconRestart(R.drawable.evp_action_restart)        // Sets a custom icon used to restart playback
				.labelRetry(R.string.mcam_retry)                   // Sets a custom button label for the button used to retry recording, when available
				.labelConfirm(R.string.mcam_use_video)             // Sets a custom button label for the button used to confirm/submit a recording
				.autoRecordWithDelaySec(5)                         // The video camera will start recording automatically after a 5 second countdown. This disables switching between the front and back camera initially.
				.autoRecordWithDelayMs(5000)                       // Same as the above, expressed with milliseconds instead of seconds.
				.audioDisabled(false)                              // Set to true to record video without any audio.
				.start(CAMERA_RQ);                                 // Starts the camera activity, the result will be sent back to the current Activity
	}

	/**
	 * 接受返回结果
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == requestCode && resultCode == RESULT_OK && null != data) {
//			showWaitingDialog("视频处理中");
			// Received recording or error from MaterialCamera
			if (requestCode == CAMERA_RQ) {

				if (resultCode == RESULT_OK) {
					/*Toast.makeText(this, "Saved to: " + data.getDataString(), Toast.LENGTH_LONG).show();*/
					try {
						setImage(data.getDataString().replace("file:///", "/"));        //设置缩略图
					} catch (Exception e) {
						e.printStackTrace();
						hideWaitingDialog();
						UIUtils.showToast("加载视频出错");
					}
				} else if (data != null) {
					Exception e = (Exception) data.getSerializableExtra(MaterialCamera.ERROR_EXTRA);
					e.printStackTrace();
					Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
				}
			}


		}
	}

	/**
	 * 根据视频url设置缩略图
	 *
	 * @param videoPath
	 */
	private void setImage(String videoPath) {
		ivThumbnail.setVisibility(View.VISIBLE);
		ivPlus.setVisibility(View.GONE);
		Bitmap bitmap = VideoUtils.getVideoThumbnail(videoPath);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
		byte[] bytes = baos.toByteArray();
		Glide.with(this).load(bytes).into(ivThumbnail);
		hideWaitingDialog();

		ivThumbnail.setOnClickListener(v -> {
			selectVideo();          //点击视频重新选择
		});
	}

	/**
	 * 弹listDialog
	 */
	private void showListDialog(int i) {
		String[] zhuagnXiutems = {"毛坯", "简单装修", "中等装修", "精装修", "豪华装修"};
		String[] chaoxiangItems = {"北", "南", "西", "东", "西北", "西南", "东北", "东南"};
		String[] zhifuItems = {"押一付一","押一付二", "押一付三","押二付一","押二付二","押二付三", "半年付", "一年付","面议","半年付不押","半年付押一","年付不押","年付押一"};
		String[] leixingItems = {"主卧", "次卧", "隔断"};
		String[] xingbieItems = {"男女不限", "限男生", "限女生", "限夫妻"};
		String[] items = {};
		switch (i) {
			case 0: {
				items = zhuagnXiutems;
				break;
			}
			case 1: {
				items = chaoxiangItems;
				break;
			}
			case 2: {
				items = zhifuItems;
				break;
			}
			case 3: {
				items = leixingItems;
				break;
			}
			case 4: {
				items = xingbieItems;
				break;
			}
		}

		final NormalListDialog dialog = new NormalListDialog(this, items);
		dialog.title("请选择")//
				.layoutAnimation(null)
				.show(R.style.myDialogAnim);
		String[] finalItems = items;
		dialog.setOnOperItemClickL(new OnOperItemClickL() {
			@Override
			public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
				switch (i) {
					case 0: {
						edtZhuangXiu.setText(finalItems[position]);
						break;
					}
					case 1: {
						edtChaoxiang.setText(finalItems[position]);
						break;
					}
					case 2: {
						edtZhifu.setText(finalItems[position]);
						break;
					}
					case 3: {
						edtleixing.setText(finalItems[position]);
						break;
					}
					case 4: {
						edtxingbie.setText(finalItems[position]);
						break;
					}
				}

				dialog.dismiss();
			}
		});
	}
}
