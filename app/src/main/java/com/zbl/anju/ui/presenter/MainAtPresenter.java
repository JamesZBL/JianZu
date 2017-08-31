package com.zbl.anju.ui.presenter;

import android.view.View;
import android.view.animation.AnimationUtils;

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
import com.zbl.anju.R;
import com.zbl.anju.app.AppConst;
import com.zbl.anju.db.DBManager;
import com.zbl.anju.ui.base.BaseActivity;
import com.zbl.anju.ui.base.BasePresenter;
import com.zbl.anju.ui.view.IMainAtView;
import com.zbl.anju.util.LogUtils;
import com.zbl.anju.util.UIUtils;

import java.util.ArrayList;
import java.util.List;

public class MainAtPresenter extends BasePresenter<IMainAtView> {

	protected boolean isFirstLocation = true;        //首次定位成功
	private LatLng lalng = null;
	protected View markerView;                       //当前位置标记
	protected List<View> markerViewHouses;           //房屋标记集合

	public MainAtPresenter(BaseActivity context) {
		super(context);
		initLocation();
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


	/**
	 * 设置地图中心点为设备当前位置
	 */
	public synchronized void setMapCenter() {
		if (lalng != null) {
			getView().getTenMap().setCenter(lalng);
			getView().getTenMap().setZoom(AppConst.MAP_LOC_SUCCESS_ZOOM_LEVEL);
		}
	}


	/**
	 * 根据坐标推荐房源
	 */
	public void loadHouses(LatLng centerLatLng) {
		if (lalng == null) {
			return;
		}
		if (markerViewHouses == null) {
			markerViewHouses = new ArrayList<>();
		}

		List<LatLng> getHousesLatLng = DBManager.getInstance().getHousesLocsByLatLng(lalng, 3, AppConst.HOUSE_DEFAULT_HOUSE_NUM, 1);

		if (centerLatLng != null) {

			//指定中心点了,将中心点放到指定的点上
			getView().getTenMap().setCenter(centerLatLng);

		} else {

			//未指定中心点（当前位置）
			getView().getTenMap().setCenter(lalng);

		}

		//缩放到3公里级别
		getView().getTenMap().setZoom(AppConst.MAP_3_KM_ZOOM_LEVEL);

		//遍历房屋标记集合，逐个清除标记
		for (View markerViewHouse : markerViewHouses
				) {
			markerViewHouse.setVisibility(View.GONE);
		}
		//清空集合
		markerViewHouses.clear();

		//逐个添加房屋标记
		for (LatLng latLng : getHousesLatLng
				) {
			//获得标记View
			View markerView = addMarker(true, latLng, R.drawable.ic_house_here, "Hi,I'm a tag.").getMarkerView();
			//将View存到房屋标记的集合中
			markerViewHouses.add(markerView);
		}
	}

	/**
	 * 添加当前位置标记
	 *
	 * @return 添加后的标记
	 */
	public Marker addCurrentLocMarker() {
		if (lalng == null) {
			return null;
		}

		return addMarker(false, lalng, R.drawable.ic_im_here, null);
	}

	/**
	 * 添加标记
	 *
	 * @param isAnimation 动画
	 * @param latLng      经纬度
	 * @param resId       图标
	 * @return 添加后的标记
	 */
	public Marker addMarker(boolean isAnimation, LatLng latLng, int resId, Object tag) {
		Marker marker = getView().getTenMap().addMarker(new MarkerOptions()
				.position(latLng)
				.icon(BitmapDescriptorFactory.fromResource(resId))
				.draggable(false)
				.tag(tag)
		);
		if (isAnimation) {
			View markerView = marker.getMarkerView();
			//// TODO: 17-8-31 修改动画
			UIUtils.startAnimation(markerView, R.anim.map_marker_low_to_high);
		}

		return marker;
	}


	/**
	 * 定位监听器
	 */
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
					try {
						/* 首次定位 */
						TencentMap tenMap = getView().getTenMap();
						tenMap.setCenter(lalng);                                //设置中心点
						tenMap.setZoom(AppConst.MAP_LOC_SUCCESS_ZOOM_LEVEL);    //设置放大级别
						/*添加设备所在位置标记*/
						markerView = addCurrentLocMarker().getMarkerView();
						/*缩放到3公里级别并推荐房源*/
						tenMap.setZoom(AppConst.MAP_3_KM_ZOOM_LEVEL);
						loadHouses(lalng);          //当前位置房源

						isFirstLocation = false;
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					/*不是首次定位（位置有可能发生变化）*/
					if (markerView != null) {
						markerView.setVisibility(View.GONE);    //隐藏掉当前标记
					}
					/*添加新标记*/
					markerView = addCurrentLocMarker().getMarkerView();
				}

				String address = tencentLocation.getAddress();


				LogUtils.d(
						"--------------定位成功--------------\n经度："
								+ latitude
								+ "，纬度："
								+ longitude + "\n"
								+ "地址："
								+ address);

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
