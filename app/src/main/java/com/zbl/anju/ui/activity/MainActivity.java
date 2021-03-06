package com.zbl.anju.ui.activity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gigamole.navigationtabstrip.NavigationTabStrip;
import com.ikimuhendis.ldrawer.ActionBarDrawerToggle;
import com.ikimuhendis.ldrawer.DrawerArrowDrawable;
import com.tencent.mapsdk.raster.model.LatLng;
import com.tencent.tencentmap.mapsdk.map.MapView;
import com.tencent.tencentmap.mapsdk.map.TencentMap;
import com.zaaach.citypicker.CityPickerActivity;
import com.zbl.anju.R;
import com.zbl.anju.app.AppConst;
import com.zbl.anju.db.model.TagHolder;
import com.zbl.anju.manager.BroadcastManager;
import com.zbl.anju.model.cache.UserCache;
import com.zbl.anju.ui.base.BaseActivity;
import com.zbl.anju.ui.presenter.MainAtPresenter;
import com.zbl.anju.ui.view.IMainAtView;
import com.zbl.anju.util.UIUtils;
import com.zbl.anju.widget.LinePathView;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import butterknife.Bind;
import de.hdodenhof.circleimageview.CircleImageView;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import kr.co.namee.permissiongen.PermissionGen;
import me.shaohui.bottomdialog.BottomDialog;

/**
 * 地图界面
 *
 * @author James
 */
public class MainActivity extends BaseActivity<IMainAtView, MainAtPresenter> implements IMainAtView {

	@Bind(R.id.dwl_main)
	DrawerLayout mDrawerLayout;
	@Bind(R.id.all_main_left)
	AutoLinearLayout mAllMainLeft;
	@Bind(R.id.rll_design_bottom_sheet)
	AutoLinearLayout mRllBottomSheet;
	@Bind(R.id.iv_bottom_sheet_pointer)
	ImageView mIvBottomSheetPointer;
	@Bind(R.id.arl_bottom_sheet_bar)
	AutoRelativeLayout mArlBottomSheetBar;


	/* 顶部 */
	@Bind(R.id.ivToolbarLeftMenu)
	AutoLinearLayout mIbToolbarLeftMenu;
	@Bind(R.id.ibToolbarMsg)
	AutoLinearLayout mIbToolbarMsg;      //消息按钮
	@Bind(R.id.line_include_toolbar_white)
	View mToolbarLine;
	@Bind(R.id.navi_main_top)
	NavigationTabStrip mNavigationTop;   //顶部导航栏
	@Bind(R.id.all_main_toolbar_city_pick)
	AutoLinearLayout mAllToolBarCityPick;//顶部城市选择按钮
	@Bind(R.id.tv_main_toobar_city_name)
	TextView mTvToolbarCityName;         //顶部城市名称文字

//	@Bind(R.id.main_anju_guanggao_banner)
//	Banner mBannerAdvertisement;        //广告轮播图

	/* 地图相关 */
	@Bind(R.id.house_map_view)
	MapView mTenMapView;                //地图（仅视图）
	@Bind(R.id.btn_main_loc)
	Button mBtnMainLoc;                 //缩放当前位置
	@Bind(R.id.btn_main_full)
	Button mBtnMainFull;                //全屏

	/* 主页宫格菜单 */
	@Bind(R.id.all_grids)
	AutoLinearLayout mAllMainGrid;      //宫格
	@Bind(R.id.arl_left_menu_1)
	AutoRelativeLayout mArlLeftMenu1;   //整租
	@Bind(R.id.arl_left_menu_2)
	AutoRelativeLayout mArlLeftMenu2;   //合租
	@Bind(R.id.arl_left_menu_6)
	AutoRelativeLayout mArlLeftMenu6;   //二手
	@Bind(R.id.arl_left_menu_9)
	AutoRelativeLayout mArlLeftMenu9;   //房源发布

	/* 左侧菜单项目 */
	@Bind(R.id.iv_header_left_menu)
	CircleImageView iv_left_header;     //头像

	BottomSheetBehavior mBottomSheetBehavior;

	@Bind(R.id.main_jc_player)
	JCVideoPlayerStandard mJcPlayer;

	/* 关于遮罩层 */
	@Bind(R.id.ll_mask_activity_main)
	LinearLayout mLlGuideMask;              //引导遮罩层
	@Bind(R.id.id_all_activity_main_center)
	AutoLinearLayout mAllActivityMainCenter;//主布局

	TencentMap mTenMap;                 //地图实例
	DrawerArrowDrawable mDrawerArrow;
	ActionBarDrawerToggle mDrawerToggle;

	EditText mEdtSigBtmDlgName;
	EditText mEdtSigBtmDlgDescription;

	BottomDialog mBottomDialog;        //底部弹窗
	private boolean hasLaunchAd = false;//是否已经显示ad
	private boolean hasFullMap = false;//是否已经全屏显示地图

	/* 重写方法 activity生命周期方法实现对 地图 MapView 的舍命周期管理 */

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
		super.onCreate(savedInstanceState, persistentState);
		/*地图*/
		mTenMapView.onCreate(savedInstanceState);
	}

	@Override
	protected void onDestroy() {
		/*地图*/
		mTenMapView.onDestroy();
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		/*地图*/
		mTenMapView.onPause();
		JCVideoPlayer.releaseAllVideos();  //暂停播放视频
		super.onPause();
	}

	@Override
	protected void onResume() {
		/*弹ad*/
		if (!hasLaunchAd) {
			this.hasLaunchAd = true;
			showAd();
		}
		/*重新定位*/
		//// TODO: 17-9-1 need test
		mPresenter.initLocation();

		/*地图*/
		mTenMapView.onResume();
		super.onResume();
	}


	@Override
	protected void onStop() {
		/*删除定位监听器*/
		//// TODO: 17-9-1 删除定位监听器 need test
		mPresenter.removeLocationListener();
		/*地图*/
		mTenMapView.onStop();
		super.onStop();
	}

	@Override
	public void onBackPressed() {
		if (hasFullMap) {
			cancelMapFullScreen();
			return;
		}
		if (JCVideoPlayer.backPress()) {
			return;
		}
		super.onBackPressed();
	}

	@Override
	protected MainAtPresenter createPresenter() {
		return new MainAtPresenter(this);
	}

	@Override
	protected int provideContentViewId() {
		return R.layout.activity_main_anju;
	}


	@Override
	public void initView() {
		super.initView();
		initThisStatusBar();
		//设置toolbar 细线不可见
//		mToolbarLine.setVisibility(View.GONE);
		//设置toolbar按钮可见
		mIbToolbarMsg.setVisibility(View.VISIBLE);
		mIbToolbarLeftMenu.setVisibility(View.VISIBLE);
		initNavTop();   //初始化顶部导航栏
		initTenMap();   //初始化地图
		initDrawer();   //初始化侧边菜单
		initBottomSheet();   //初始化底部滑动菜单
		initJcVideoPlayer(); //初始化视频播放器
		setGuideMaskVisible(true);//显示遮罩层
	}


	@Override
	public void init() {
		super.init();
		registerBR();
	}

	@Override
	public void initData() {
		super.initData();
		mPresenter.initData();
	}

	@Override
	public void initListener() {
		super.initListener();

		/* 顶部导航条 */
		mNavigationTop.setOnTabStripSelectedIndexListener(new NavigationTabStrip.OnTabStripSelectedIndexListener() {
			@Override
			public void onStartTabSelected(String s, int i) {

			}

			@Override
			public void onEndTabSelected(String s, int i) {
				mPresenter.loadHouses(null);
			}
		});

		/* toolbar左侧菜单按钮 */
		mIbToolbarLeftMenu.setOnClickListener(v -> {
			openDrawer();
		});

		/* toolbar右侧消息按钮 */
		mIbToolbarMsg.setOnClickListener(v -> {
		});

		/* 定位按钮 */
		mBtnMainLoc.setOnClickListener(v -> {
			mPresenter.setMapCenter();
		});

		/* 主页宫格菜单 内容 */
		mArlLeftMenu1.setOnClickListener(v -> {
			//房屋列表整租
			jumpToActivity(HouseListActiity.class);
		});
		mArlLeftMenu2.setOnClickListener(v -> {
			//合租
			jumpToActivity(HouseListHezuActiity.class);
		});
		mArlLeftMenu6.setOnClickListener(v -> {
			//二手
			jumpToActivity(BuyOrSellActivity.class);
		});
		mArlLeftMenu9.setOnClickListener(v -> {
			//房源发布
			jumpToActivity(HouseTypeActivity.class);
		});

		/* 顶部 城市选择按钮 */
		mAllToolBarCityPick.setOnClickListener(v -> {
			//启动城市选择activity
			//启动
			startActivityForResult(new Intent(MainActivity.this, CityPickerActivity.class),
					AppConst.REQ_CITY_PICK);
		});

		/*地图全屏按钮*/
		mBtnMainFull.setOnClickListener(v -> {
			if (!hasFullMap) {
				makeMapFullScreen();
			} else {
				cancelMapFullScreen();
			}
		});

		/* 头像 暂时实现点击头像清除User缓存 */
		iv_left_header.setOnClickListener(v -> {
			UserCache.clear();
			showToast("测试通过");
		});

		/* 遮罩层 ，点击则消失 */
		mLlGuideMask.setOnClickListener(v -> {
			setGuideMaskVisible(false);
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == AppConst.REQ_CITY_PICK && resultCode == RESULT_OK) {
			//成功选择城市
			if (data != null) {
				String city = data.getStringExtra(CityPickerActivity.KEY_PICKED_CITY);
				mTvToolbarCityName.setText(city);                       //设置城市文字
			}
		}
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


	@Override
	protected boolean isToolbarCanBack() {
		return false;
	}

	/**
	 * 初始化状态栏
	 */
	private void initThisStatusBar() {
		if (Build.VERSION.SDK_INT >= 19) {
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			mAppBar.setPadding(0, UIUtils.getStatusbarheight(this), 0, 0);
			if (Build.VERSION.SDK_INT >= 21) {
				getWindow().setStatusBarColor(UIUtils.getColor(R.color.colorPrimary));
				getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
			}

			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
				getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
				getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
			}
		}
	}

	@Override
	public LinePathView getLinepathView() {
		return null;
	}

	@Override
	public EditText getEdtName() {
		return mEdtSigBtmDlgName;
	}

	@Override
	public EditText getEdtDescription() {
		return mEdtSigBtmDlgDescription;
	}

	@Override
	public MapView getTenMapView() {
		return mTenMapView;
	}

	@Override
	public TencentMap getTenMap() {
		return mTenMap;
	}

	@Override
	public NavigationTabStrip getNavigationTabStrip() {
		return mNavigationTop;
	}

//	@Override
//	public Banner getBannerAd() {
//		return mBannerAdvertisement;
//	}

	@Override
	public JCVideoPlayerStandard getJcPlayer() {
		return mJcPlayer;
	}

	/**
	 * 初始化视频播放器
	 */
	private void initJcVideoPlayer() {
		mJcPlayer.thumbImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
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
//						Manifest.permission.RECORD_AUDIO,
						Manifest.permission.WAKE_LOCK,
//						Manifest.permission.CAMERA,
						//存储空间
						Manifest.permission.WRITE_EXTERNAL_STORAGE,
						Manifest.permission.WRITE_SETTINGS
				)
				.request();
	}

	/**
	 * 初始化地图
	 */
	private void initTenMap() {
		//获取地图实例
		mTenMap = mTenMapView.getMap();
		if (mTenMap != null) {
			//对地图进行设置
			//设置地图中心点
			mTenMap.setCenter(new LatLng(AppConst.MAP_DEFAULT_LATITUDE, AppConst.MAP_DEFAULT_LONGITUDE));
			//设置缩放级别
			mTenMap.setZoom(AppConst.MAP_DEFAULT_ZOOM_LEVEL);
			//设置点击监听
			mTenMap.setOnMarkerClickListener(marker -> {
				Object tag = marker.getTag();
				if (tag != null) {
					Bundle bundle = new Bundle();
					bundle.putSerializable("tagHolder", new TagHolder(tag));
					jumpToActivity(new Intent(this, HouseInfoActivity.class).putExtras(bundle));
				}
				return false;
			});
			//点击地图
			mTenMap.setOnMapClickListener(latLng -> {
				jumpToActivity(HouseListActiity.class);
			});
		}
	}

	/**
	 * 初始化侧边菜单
	 */
	private void initDrawer() {
		mDrawerArrow = new DrawerArrowDrawable(this) {
			@Override
			public boolean isLayoutRtl() {
				return false;
			}
		};
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				mDrawerArrow, R.string.app_name,
				R.string.app_name) {

			public void onDrawerClosed(View view) {
				super.onDrawerClosed(view);
				invalidateOptionsMenu();
				UIUtils.showToast("关闭");
			}

			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				invalidateOptionsMenu();
				UIUtils.showToast("打开");

			}
		};

		UIUtils.setDrawerLeftEdgeSize(this, mDrawerLayout, 0);


	}

	/**
	 * 初始化底部滑动菜单
	 */
	private void initBottomSheet() {
		mBottomSheetBehavior = BottomSheetBehavior.from(mRllBottomSheet);
		//实现按三角按钮开关底部菜单
		mArlBottomSheetBar.setOnClickListener(v -> {
			mBottomSheetBehavior.setState(
					mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED ?
							BottomSheetBehavior.STATE_COLLAPSED
							: BottomSheetBehavior.STATE_EXPANDED);
		});
		//设置展开折叠事件监听
		mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
			//状态改变
			@Override
			public void onStateChanged(@NonNull View bottomSheet, int newState) {
				if (newState == BottomSheetBehavior.STATE_EXPANDED) {
					//展开了，设置三角指向下方
					mIvBottomSheetPointer.setImageDrawable(getResources().getDrawable(R.drawable.ic_down_tri));
				} else if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
					mIvBottomSheetPointer.setImageDrawable(getResources().getDrawable(R.drawable.ic_up_tri));
				}
			}

			//滑动位置改变
			@Override
			public void onSlide(@NonNull View bottomSheet, float slideOffset) {

			}
		});
	}

	/**
	 * 初始化顶部导航栏
	 */
	private void initNavTop() {
		mNavigationTop.setTabIndex(0);      //指向首位
	}

	/**
	 * 打开侧边栏
	 */
	private void openDrawer() {
		mDrawerLayout.openDrawer(Gravity.LEFT);
	}

	/**
	 * 关闭侧边栏
	 */
	private void closeDrawer() {
		mDrawerLayout.closeDrawers();
	}

	/**
	 * 弹 ad
	 */
	private void showAd() {
		mPresenter.showAd();
	}

	/**
	 * 地图全屏
	 */
	private void makeMapFullScreen() {
		mAllMainGrid.setVisibility(View.GONE);
		mJcPlayer.setVisibility(View.GONE);
//		mBannerAdvertisement.setVisibility(View.GONE);
		mAppBar.setVisibility(View.GONE);
		//设置为全屏模式
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		hasFullMap = true;
	}

	/**
	 * 取消地图全屏
	 */
	private void cancelMapFullScreen() {
		mAllMainGrid.setVisibility(View.VISIBLE);
		mJcPlayer.setVisibility(View.VISIBLE);
//		mBannerAdvertisement.setVisibility(View.VISIBLE);
		mAppBar.setVisibility(View.VISIBLE);
		//取消全屏状态
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		hasFullMap = false;
	}

	@Override
	public void onFirstLaunchThisVersionDo() {
		super.onFirstLaunchThisVersionDo();
		initPermission();
	}

	/**
	 * 显示引导遮罩
	 */
	public void setGuideMaskVisible(boolean show) {
		if (show) {
			//隐藏主布局
			mAllActivityMainCenter.setVisibility(View.GONE);
			//显示遮罩层布局
			mLlGuideMask.setVisibility(View.VISIBLE);
		} else {
			//显示主布局
			mAllActivityMainCenter.setVisibility(View.VISIBLE);
			//隐藏遮罩层布局
			mLlGuideMask.setVisibility(View.GONE);
		}
	}
}
