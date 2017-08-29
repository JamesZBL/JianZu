package com.zbl.anju.ui.activity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.gigamole.navigationtabstrip.NavigationTabStrip;
import com.ikimuhendis.ldrawer.ActionBarDrawerToggle;
import com.ikimuhendis.ldrawer.DrawerArrowDrawable;
import com.tencent.mapsdk.raster.model.LatLng;
import com.tencent.tencentmap.mapsdk.map.MapView;
import com.tencent.tencentmap.mapsdk.map.TencentMap;
import com.zbl.anju.R;
import com.zbl.anju.app.AppConst;
import com.zbl.anju.manager.BroadcastManager;
import com.zbl.anju.ui.base.BaseActivity;
import com.zbl.anju.ui.presenter.MainAtPresenter;
import com.zbl.anju.ui.view.IMainAtView;
import com.zbl.anju.util.PopupWindowUtils;
import com.zbl.anju.util.StringUtils;
import com.zbl.anju.util.UIUtils;
import com.zbl.anju.widget.LinePathView;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import butterknife.Bind;
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


	@Bind(R.id.ivToolbarLeftMenu)
	AutoLinearLayout mIbToolbarLeftMenu;
	@Bind(R.id.ibToolbarMore)
	AutoLinearLayout mIbToolbarMore;     //三点菜单按钮
	@Bind(R.id.ibToolbarMsg)
	AutoLinearLayout mIbToolbarMsg;      //消息按钮
	@Bind(R.id.line_include_toolbar_white)
	View mToolbarLine;
	@Bind(R.id.navi_main_top)
	NavigationTabStrip mNavigationTop;

	@Bind(R.id.house_map_view)
	MapView mTenMapView;                //地图（仅视图）
	@Bind(R.id.btn_main_loc)
	Button mBtnMainLoc;

	@Bind(R.id.btn_upload_signature)
	Button mBtnUploadSignature;
	@Bind(R.id.ll_sig_btm_save)
	AutoLinearLayout mLlBtmSave;
	@Bind(R.id.ll_sig_btm_clear)
	AutoLinearLayout mLlBtmClear;
	@Bind(R.id.ll_sig_btm_color)
	AutoLinearLayout mLlBtmColor;

	BottomSheetBehavior mBottomSheetBehavior;

	TencentMap mTenMap;                 //地图实例
	DrawerArrowDrawable mDrawerArrow;
	ActionBarDrawerToggle mDrawerToggle;

	EditText mEdtSigBtmDlgName;
	EditText mEdtSigBtmDlgDescription;

	BottomDialog mBottomDialog;


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
		super.onPause();
	}

	@Override
	protected void onResume() {
		/*地图*/
		mTenMapView.onResume();
		super.onResume();
	}

	@Override
	protected void onStop() {
		/*地图*/
		mTenMapView.onStop();
		super.onStop();
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
		//设置toolbar 细线不可见
		mToolbarLine.setVisibility(View.GONE);
		//设置toolbar按钮可见
		mIbToolbarMsg.setVisibility(View.VISIBLE);
		mIbToolbarLeftMenu.setVisibility(View.VISIBLE);
		initNavTop();   //初始化顶部导航栏
		initTenMap();   //初始化地图
		initDrawer();   //初始化侧边菜单
		initBottomSheet();   //初始化底部滑动菜单
	}


	@Override
	public void init() {
		super.init();
		registerBR();
	}

	@Override
	public void initData() {
		super.initData();
		mPresenter.initLocation();  //定位
	}

	@Override
	public void initListener() {
		super.initListener();
		//顶部导航
		mNavigationTop.setOnTabStripSelectedIndexListener(new NavigationTabStrip.OnTabStripSelectedIndexListener() {
			@Override
			public void onStartTabSelected(String s, int i) {

			}

			@Override
			public void onEndTabSelected(String s, int i) {
				mPresenter.loadHouses(i);
			}
		});


		//上传（底部按钮）
		mBtnUploadSignature.setOnClickListener(v1 -> {
			showNameDialog();
		});
		mLlBtmClear.setOnClickListener(v1 -> {
//			mLpvSignature.clear();
		});
		mLlBtmColor.setOnClickListener(v1 -> {
//			showColorDialog();
		});
		mLlBtmSave.setOnClickListener(v1 -> {

		});


		mIbToolbarMsg.setOnClickListener(v -> {
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
				jumpToActivity(HouseInfoActivity.class);
			});
			//
		});

		/*定位*/
		mBtnMainLoc.setOnClickListener(v -> {
			mPresenter.setMapCenter();
		});

		/*左侧菜单*/
		mIbToolbarLeftMenu.setOnClickListener(v -> {
			openDrawer();
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



	@Override
	protected boolean isToolbarCanBack() {
		return false;
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


	@Override
	public void onFirstLaunchThisVersionDo() {
		super.onFirstLaunchThisVersionDo();
		initPermission();
	}

}
