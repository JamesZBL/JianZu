package com.zbl.anju.ui.presenter;

import android.view.animation.TranslateAnimation;

import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;
import com.tencent.mapsdk.raster.model.BitmapDescriptorFactory;
import com.tencent.mapsdk.raster.model.LatLng;
import com.tencent.mapsdk.raster.model.Marker;
import com.tencent.mapsdk.raster.model.MarkerOptions;
import com.tencent.tencentmap.mapsdk.map.TencentMap;
import com.tencent.tencentmap.mapsdk.raster.utils.animation.MarkerRotateAnimator;
import com.tencent.tencentmap.mapsdk.raster.utils.animation.MarkerTranslateAnimator;
import com.zbl.anju.R;
import com.zbl.anju.app.AppConst;
import com.zbl.anju.db.DBManager;
import com.zbl.anju.ui.base.BaseActivity;
import com.zbl.anju.ui.base.BasePresenter;
import com.zbl.anju.ui.view.IMainAtView;
import com.zbl.anju.util.LogUtils;

import java.util.List;

public class MainAtPresenter extends BasePresenter<IMainAtView> {

	protected boolean isFirstLocation = true;  //首次定位成功
	private LatLng lalng = null;

	public MainAtPresenter(BaseActivity context) {
		super(context);
	}


	/**
	 * 定位
	 */
	public void initLocation() {
		TencentLocationListener listener = new MyTencentLocationListener();
		TencentLocationRequest request = TencentLocationRequest.create();
		request.setInterval(AppConst.LOCATION_DEFAULT_INTERVAL);    //设置定位周期
		request.setRequestLevel(TencentLocationRequest.REQUEST_LEVEL_NAME);  //设置定位精准度
		TencentLocationManager locationManager = TencentLocationManager.getInstance(mContext);
		int error = locationManager.requestLocationUpdates(request, listener);

		switch (error) {
			case 0:
				LogUtils.d("--------------注册位置监听成功-------------");
				break;
			case 1:
				LogUtils.e("--------设备缺少使用腾讯定位SDK需要的基本条件--------");
				break;
			case 2:
				LogUtils.e("--------地图配置的 Key 不正确--------");
				break;
			case 3:
				LogUtils.e("--------自动加载libtencentloc.so失败-------");
				break;
		}
	}

	public void setMapCenter() {
		if (lalng != null) {
			getView().getTenMap().setCenter(lalng);
			getView().getTenMap().setZoom(AppConst.MAP_LOC_SUCCESS_ZOOM_LEVEL);
		}
	}


	/**
	 * 根据坐标推荐房源
	 */
	public void loadHouses() {
		if (lalng == null) {
			return;
		}

		List<LatLng> getHousesLatLng = DBManager.getInstance().getHousesLocsByLatLng(lalng, 3, AppConst.HOUSE_DEFAULT_HOUSE_NUM, 1);

		getView().getTenMap().setZoom(AppConst.MAP_3_KM_ZOOM_LEVEL);        //缩放到3公里级别
		getView().getTenMap().clearAllOverlays();       //清除所有标记
		addCurrentLocMarker();                          //添加位置标记

		//添加房屋标记
		for (LatLng latLng : getHousesLatLng
				) {
			addMarker(true, latLng, R.drawable.ic_house_here, "Hi,I'm a tag.");

		}
	}

	public void addCurrentLocMarker() {
		if (lalng == null) {
			return;
		}
		addMarker(false, lalng, R.drawable.ic_im_here, null);
	}

	/**
	 * 添加标记
	 *
	 * @param isAnimation 动画
	 * @param latLng      经纬度
	 * @param resId       图标
	 */
	public void addMarker(boolean isAnimation, LatLng latLng, int resId, Object tag) {
		Marker marker = getView().getTenMap().addMarker(new MarkerOptions()
				.position(latLng)
				.icon(BitmapDescriptorFactory.fromResource(resId))
				.draggable(true)
				.tag(tag)
		);
		if (isAnimation) {
			MarkerRotateAnimator rotateAnimator = new MarkerRotateAnimator(marker, 500, 0, 360);
			rotateAnimator.startAnimation();
		}
	}


	class MyTencentLocationListener implements TencentLocationListener {

		/**************************************** 定位回调 *****************************************/
		@Override
		public void onLocationChanged(TencentLocation tencentLocation, int error, String s) {
			if (TencentLocation.ERROR_OK == error) {
				// 定位成功
				double latitude = tencentLocation.getLatitude();
				double longitude = tencentLocation.getLongitude();
				lalng = new LatLng(latitude, longitude);

				if (isFirstLocation) {
					/* 首次定位 */
					TencentMap tenMap = getView().getTenMap();
					tenMap.setCenter(lalng);                                //设置中心点
					tenMap.setZoom(AppConst.MAP_LOC_SUCCESS_ZOOM_LEVEL);    //设置放大级别
					/*添加设备所在位置标记*/
					addCurrentLocMarker();
					/*缩放到3公里级别并推荐房源*/
					tenMap.setZoom(AppConst.MAP_3_KM_ZOOM_LEVEL);
					loadHouses();
				}

				String address = tencentLocation.getAddress();

				isFirstLocation = false;
				LogUtils.d("--------------定位成功--------------\n经度：" + latitude + "，纬度：" + longitude + "\n" + "地址：" + address);

			} else {
				// 定位失败
				LogUtils.e("--------------定位失败-------------");
			}
		}

		@Override
		public void onStatusUpdate(String s, int i, String s1) {

		}
		/**************************************** 定位回调 结束  ***********************************/
	}
}
