package com.zbl.anju.ui.activity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.support.v7.widget.ViewUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.Toast;

import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.zbl.anju.R;
import com.zbl.anju.app.AppConst;
import com.zbl.anju.manager.BroadcastManager;
import com.zbl.anju.ui.base.BaseActivity;
import com.zbl.anju.ui.presenter.HouseInfoAtPresenter;
import com.zbl.anju.ui.view.IHouseInfoAtView;
import com.zbl.anju.util.GlideImageLoader;
import com.zbl.anju.util.PopupWindowUtils;
import com.zbl.anju.util.StringUtils;
import com.zbl.anju.util.UIUtils;
import com.zhy.autolayout.AutoLinearLayout;

import butterknife.Bind;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerSimple;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import kr.co.namee.permissiongen.PermissionGen;
import me.shaohui.bottomdialog.BottomDialog;

/**
 * 房屋信息界面
 *
 * @author James
 */
public class HouseInfoActivity extends BaseActivity<IHouseInfoAtView, HouseInfoAtPresenter> implements IHouseInfoAtView {

	@Bind(R.id.ibToolbarMore)
	AutoLinearLayout mIbToolbarMore;//三点菜单按钮

	@Bind(R.id.houseinfo_video_player)
	JCVideoPlayerStandard mHouseInfoVideoPlayer;  //视频播放器
	@Bind(R.id.houseinfo_banner)
	Banner mBanner;     //轮播图
	@Bind(R.id.scv_houseinfo)
	ScrollView mScrollView;


	EditText mEdtSigBtmDlgName;
	EditText mEdtSigBtmDlgDescription;

	BottomDialog mBottomDialog;

	@Override
	protected HouseInfoAtPresenter createPresenter() {
		return new HouseInfoAtPresenter(this);
	}

	@Override
	protected int provideContentViewId() {
		return R.layout.activity_house_info;
	}


	@Override
	public void initView() {
		super.initView();
		mIbToolbarMore.setVisibility(View.VISIBLE);
		initBanner();
		initJcVideoPlayer();
	}


	@Override
	public void init() {
		super.init();
		registerBR();
	}

	@Override
	public void initData() {
		super.initData();
		//加载数据
		mPresenter.initData();
		mPresenter.initBundle(getIntent().getExtras());
	}

	@Override
	public void initListener() {
		super.initListener();

		/* 三点菜单按钮 */
		mIbToolbarMore.setOnClickListener(v -> {
			View menuView = View.inflate(this, R.layout.menu_sig, null);
			PopupWindow popupWindow = PopupWindowUtils.getPopupWindowAtLocation(menuView, getWindow().getDecorView(), Gravity.TOP | Gravity.RIGHT, UIUtils.dip2Px(5), mAppBar.getHeight() + UIUtils.getStatusbarheight(this), R.style.popwindow_top_right_anim_style, this);

			//菜单操作
			//保存到本地
			menuView.findViewById(R.id.tvSaveSig).setOnClickListener(v1 -> {
				popupWindow.dismiss();

			});
			//清除
			menuView.findViewById(R.id.tvClearSig).setOnClickListener(v1 -> {
				popupWindow.dismiss();
//				mLpvSignature.clear();

			});
			//上传
			menuView.findViewById(R.id.tvUploadSig).setOnClickListener(v1 -> {
				popupWindow.dismiss();
//				mPresenter.uploadSig();
				showNameDialog();
			});

			/* 滑动监听 */
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
				mScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
					@Override
					public void onScrollChange(View view, int x, int y, int oldX, int oldY) {
						Rect scrollRect = new Rect();
						mScrollView.getHitRect(scrollRect);

						if (mHouseInfoVideoPlayer.getLocalVisibleRect(scrollRect)) {

						} else {
							////子控件完全不在可视范围内
							JCVideoPlayer.releaseAllVideos();
						}
					}
				});
			}

		});
	}

	private void registerBR() {
		BroadcastManager.getInstance(this).register(AppConst.SIGNATURE_UPLOAD_STARTED, new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				showWaitingDialog("正在上传");
			}
		});
		BroadcastManager.getInstance(this).register(AppConst.SIGNATURE_UPLOAD_SUCCESS, new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				hideWaitingDialog();
				UIUtils.showToast("上传成功！", Toast.LENGTH_LONG, Gravity.CENTER_VERTICAL);
			}
		});
		BroadcastManager.getInstance(this).register(AppConst.SIGNATURE_UPLOAD_FAILED, new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				hideWaitingDialog();
				UIUtils.showToast("网络请求超时", Toast.LENGTH_LONG, Gravity.CENTER_VERTICAL);
			}
		});
	}

	/**
	 * toolbar不显示返回箭头按钮
	 *
	 * @return
	 */
	@Override
	protected boolean isToolbarCanBack() {
		return true;
	}

	/**
	 * 初始化视频播放器
	 */
	private void initJcVideoPlayer() {
		mHouseInfoVideoPlayer.thumbImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
	}


	/**
	 * 设置轮播各个属性
	 */
	private void initBanner() {
		//设置banner样式
		mBanner.setBannerStyle(BannerConfig.NUM_INDICATOR);  //显示指示器，不显示标题
		//设置图片加载器
		mBanner.setImageLoader(new GlideImageLoader());
		//设置图片集合
//		banner.setImages(images);  //在presenter中设置
		//设置banner动画效果
		mBanner.setBannerAnimation(Transformer.Default);
		//设置标题集合（当banner样式有显示title时）
//		mBanner.setBannerTitles(titles);
		//设置自动轮播，默认为true
		mBanner.isAutoPlay(true);
		//设置轮播时间
		mBanner.setDelayTime(Integer.MAX_VALUE);    //设置无限长时间，只使用滑动浏览的功能
		//设置指示器位置（当banner模式中有指示器时）
		mBanner.setIndicatorGravity(BannerConfig.CENTER);
		//banner设置方法全部调用完毕时最后调用
//		mBanner.start();            //在presenter中设置
	}

	/**
	 * 弹出姓名输入dialog
	 */
	private void showNameDialog() {
		mBottomDialog = UIUtils.showBottomDialog(getSupportFragmentManager(), R.layout.dialog_sig, v1 -> {
			mEdtSigBtmDlgName = (EditText) v1.findViewById(R.id.edt_sig_name);
			mEdtSigBtmDlgDescription = (EditText) v1.findViewById(R.id.edt_sig_description);

			v1.findViewById(R.id.btn_sig_dialog_submit).setOnClickListener(v2 -> {
				String name = mEdtSigBtmDlgName.getText().toString();
				if (!StringUtils.isEmpty(name) && !StringUtils.isBlank(name)) {
					mBottomDialog.dismiss();

				} else {
					UIUtils.showToast("请填写正确的姓名", Toast.LENGTH_SHORT, Gravity.CENTER_VERTICAL);
				}
			});
			UIUtils.focusEdt(mEdtSigBtmDlgName);
		});
	}

	/**
	 * 申请运行时权限
	 */
	private void initPermission() {
		PermissionGen.with(this)
				.addRequestCode(AppConst.INIT_PERMISSION_REQ_CODE)
				.permissions(
						//电话通讯录
						Manifest.permission.GET_ACCOUNTS,
						Manifest.permission.READ_PHONE_STATE,
						//位置
						Manifest.permission.ACCESS_FINE_LOCATION,
						Manifest.permission.ACCESS_COARSE_LOCATION,
						Manifest.permission.ACCESS_FINE_LOCATION,
						//相机、麦克风
						Manifest.permission.RECORD_AUDIO,
						Manifest.permission.WAKE_LOCK,
						Manifest.permission.CAMERA,
						//存储空间
						Manifest.permission.WRITE_EXTERNAL_STORAGE,
						Manifest.permission.WRITE_SETTINGS
				)
				.request();
	}

	@Override
	public void onFirstLaunchThisVersionDo() {
		super.onFirstLaunchThisVersionDo();
	}

	@Override
	protected void onPause() {
		super.onPause();
		JCVideoPlayer.releaseAllVideos();  //暂停播放视频
	}

	@Override
	public void onBackPressed() {
		if (JCVideoPlayer.backPress()) {
			return;
		}
		super.onBackPressed();
	}

	@Override
	public JCVideoPlayerStandard getJcVideoPlayer() {
		return mHouseInfoVideoPlayer;
	}

	@Override
	public Banner getBanner() {
		return mBanner;
	}
}
