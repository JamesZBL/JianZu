package com.zbl.anju.ui.presenter;

import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;
import com.tencent.mapsdk.raster.model.BitmapDescriptorFactory;
import com.tencent.mapsdk.raster.model.LatLng;
import com.tencent.mapsdk.raster.model.Marker;
import com.tencent.mapsdk.raster.model.MarkerOptions;
import com.tencent.tencentmap.mapsdk.map.TencentMap;
import com.zbl.anju.R;
import com.zbl.anju.app.AppConst;
import com.zbl.anju.ui.base.BaseActivity;
import com.zbl.anju.ui.base.BasePresenter;
import com.zbl.anju.ui.view.IMainAtView;
import com.zbl.anju.util.LogUtils;

public class MainAtPresenter extends BasePresenter<IMainAtView> {

	public boolean isFirstLocation = true;  //首次定位成功
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
	 * 加载房屋数据
	 * @param i  户型索引
	 */
	public void loadHouses(int i) {

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
					/*添加标记*/
					Marker marker = tenMap.addMarker(new MarkerOptions()
							.position(lalng)
							.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_im_here))
							.draggable(true));
				}

				String address = tencentLocation.getAddress();

				isFirstLocation = false;
				LogUtils.d("--------------定位成功--------------\n经度：" + latitude + "，纬度：" + longitude + "\n"+"地址："+address);

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