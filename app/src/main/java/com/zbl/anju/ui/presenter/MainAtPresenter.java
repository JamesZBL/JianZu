package com.zbl.anju.ui.presenter;

import android.view.View;

import com.bumptech.glide.Glide;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;
import com.tencent.mapsdk.raster.model.BitmapDescriptorFactory;
import com.tencent.mapsdk.raster.model.LatLng;
import com.tencent.mapsdk.raster.model.Marker;
import com.tencent.mapsdk.raster.model.MarkerOptions;
import com.uuch.adlibrary.AdConstant;
import com.uuch.adlibrary.AdManager;
import com.uuch.adlibrary.bean.AdInfo;
import com.uuch.adlibrary.transformer.DepthPageTransformer;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.zbl.anju.R;
import com.zbl.anju.app.AppConst;
import com.zbl.anju.db.DBManager;
import com.zbl.anju.ui.base.BaseActivity;
import com.zbl.anju.ui.base.BasePresenter;
import com.zbl.anju.ui.view.IMainAtView;
import com.zbl.anju.util.GlideImageLoader;
import com.zbl.anju.util.LogUtils;
import com.zbl.anju.util.UIUtils;

import java.util.ArrayList;
import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * 主页
 */
public class MainAtPresenter extends BasePresenter<IMainAtView> {

	private LatLng lalng = null;
	private boolean isFirstLocation = true;        //首次定位成功
	private View markerView;                       //当前位置标记
	private List<View> markerViewHouses;           //房屋标记集合
	private TencentLocationListener mLocationListener;//定位监听器
	private TencentLocationRequest mLocationRequest;//定位请求
	TencentLocationManager mLocationManager;       //定位管理器

	public MainAtPresenter(BaseActivity context) {
		super(context);
		initLocation();
	}

	/**
	 * 加载数据
	 * 视频
	 */
	public void initData() {
		initBanner();
		initJcPlayer();
	}

	/**
	 * 加载视频
	 */
	private void initJcPlayer() {
		//设置视频播放源
		getView().getJcPlayer().setUp("http://pgccdn.v.baidu.com/446673505_4133162716_20170806132327.mp4?authorization=bce-auth-v1%2Fc308a72e7b874edd9115e4614e1d62f6%2F2017-08-06T05%3A23%3A33Z%2F-1%2F%2Fe4fa01ec4352ddb38314a70c809264a6fc0e6894dd0638710acbf712a1c0a6f3&responseCacheControl=max-age%3D8640000&responseExpires=Tue%2C+14+Nov+2017+13%3A23%3A33+GMT&xcode=c64b1919385aa116dc50d92e5054a7741d93c9f3c454ab2f&time=1504139813&_=1504054789526"
				, JCVideoPlayerStandard.SCREEN_LAYOUT_LIST);
		//设置视频缩略图
		Glide.with(mContext)
				.load("http://pic3.58cdn.com.cn/anjuke_58/46bbe80f123d3425211a2b1e25397895")
				.into(getView().getJcPlayer().thumbImageView);
		JCVideoPlayer.setJcUserAction(new HouseInfoAtPresenter.MyUserActionStandard());
	}


	/**
	 * 设置轮播各个属性
	 */
	private void initBannerProperty() {
		//设置banner样式
//		mBannerAdvertisement.setBannerStyle(BannerConfig.);  //显示指示器，不显示标题
		Banner mBannerAdvertisement = getView().getBannerAd();
		//设置图片加载器
		mBannerAdvertisement.setImageLoader(new GlideImageLoader());
		//设置图片集合
//		banner.setImages(images);  //在presenter中设置
		//设置banner动画效果
		mBannerAdvertisement.setBannerAnimation(Transformer.Default);
		//设置标题集合（当banner样式有显示title时）
//		mBanner.setBannerTitles(titles);
		//设置自动轮播，默认为true
		mBannerAdvertisement.isAutoPlay(true);
		//设置轮播时间
		mBannerAdvertisement.setDelayTime(AppConst.MAIN_AD_BANNER_INTERVAL);
		//设置指示器位置（当banner模式中有指示器时）
		mBannerAdvertisement.setIndicatorGravity(BannerConfig.CENTER);
		//banner设置方法全部调用完毕时最后调用
//		mBanner.start();            //在presenter中设置
	}

	/**
	 * 初始化广告轮播图
	 */
	private void initBanner() {
		//设置轮播图的各个属性
		initBannerProperty();
		//设置轮播图
		List<String> imageList = new ArrayList<>();
		imageList.add("http://pic.58pic.com/58pic/13/80/06/68558PIC7Fn_1024.jpg");
		imageList.add("http://pic.58pic.com/58pic/14/32/00/83A58PICDH5_1024.jpg");
		imageList.add("http://pic.58pic.com/58pic/13/23/73/67y58PICzAc_1024.jpg");
		imageList.add("http://pic.58pic.com/58pic/16/41/62/95I58PICRFd_1024.jpg");

		imageList.add("http://pic56.nipic.com/file/20141213/15265552_175423699768_2.jpg");
		imageList.add("http://pic.58pic.com/58pic/13/22/62/13V58PICAk2_1024.jpg");
		imageList.add("http://pic.58pic.com/58pic/17/07/88/98y58PIChDF_1024.jpg");
		imageList.add("http://pic.58pic.com/58pic/13/60/04/49K58PICjWN_1024.jpg");

		//设置图片来源
		getView().getBannerAd().setImages(imageList);
		//开始加载图片
		getView().getBannerAd().start();
	}


	/**
	 * 定位
	 */
	public void initLocation() {
		mLocationListener = new MyTencentLocationListener();
		mLocationRequest = TencentLocationRequest.create();
		mLocationRequest.setInterval(AppConst.LOCATION_DEFAULT_INTERVAL);    //设置定位周期
		mLocationRequest.setRequestLevel(TencentLocationRequest.REQUEST_LEVEL_NAME);  //设置定位精准度
		mLocationManager = TencentLocationManager.getInstance(mContext);
		int error = mLocationManager.requestLocationUpdates(mLocationRequest, mLocationListener);

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
	 * 移除定位监听器
	 */
	public void removeLocationListener() {
		if (null != mLocationManager && null != mLocationListener) {
			mLocationManager.removeUpdates(mLocationListener);
		}
	}


	/**
	 * 设置地图中心点为设备当前位置
	 */
	public synchronized void setMapCenter() {
		if (lalng != null) {
			setCenter(lalng);
			setZoom(AppConst.MAP_LOC_SUCCESS_ZOOM_LEVEL);
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
			setCenter(centerLatLng);

		} else {

			//未指定中心点（当前位置）
			setCenter(lalng);

		}

		//缩放到3公里级别
		setZoom(AppConst.MAP_3_KM_ZOOM_LEVEL);

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
			UIUtils.startAnimation(markerView, R.anim.map_marker_low_to_high);
		}

		return marker;
	}

	/**
	 * 设置缩放比（通用）
	 */
	private void setZoom(int zoomLevel) {
		getView().getTenMap().setZoom(zoomLevel);
	}

	/**
	 * 设置中心点（通用）
	 */
	private void setCenter(LatLng latLng) {
		getView().getTenMap().setCenter(latLng);
	}

	/**
	 * 弹ad
	 */
	public void showAd(){
		List<AdInfo> advList = new ArrayList<>();
		AdInfo adInfo = new AdInfo();
		adInfo.setActivityImg(AppConst.URL_AD_IMG);
		advList.add(adInfo);
		AdManager adManager = new AdManager(mContext, advList);
		adManager.setOverScreen(true)
				.setPageTransformer(new DepthPageTransformer());

		adManager.showAdDialog(AdConstant.ANIM_DOWN_TO_UP);
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
						setCenter(lalng);                                //设置中心点
						setZoom(AppConst.MAP_LOC_SUCCESS_ZOOM_LEVEL);    //设置放大级别
						/*添加设备所在位置标记*/
						markerView = addCurrentLocMarker().getMarkerView();
						/*缩放到3公里级别并推荐房源*/
						setZoom(AppConst.MAP_3_KM_ZOOM_LEVEL);
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
