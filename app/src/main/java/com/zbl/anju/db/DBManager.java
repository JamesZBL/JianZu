package com.zbl.anju.db;

import com.tencent.mapsdk.raster.model.LatLng;
import com.zbl.anju.model.data.House;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author James
 * @描述 数据管理器
 */
public class DBManager {

	private static DBManager mInstance;
	private static final double ABS_LOC_3_KM = 0.035835;    //3公里对应的经纬度变化
	private static final String QINIU_HOST = "http://ovi01lb03.bkt.clouddn.com";    //七牛云host

	private List<String> xiaoquName = new ArrayList<>();
	private List<String> description = new ArrayList<>();
	private List<String> distance = new ArrayList<>();
	private List<String> releaseTime = new ArrayList<>();
	private List<String> price0 = new ArrayList<>();
	private List<String> price1 = new ArrayList<>();
	private List<String> price2 = new ArrayList<>();
	private List<String> price3 = new ArrayList<>();
	private List<String> hostName = new ArrayList<>();
	private List<String> hostPhoneNum = new ArrayList<>();
	private List<String> urlVideoThumbnail = new ArrayList<>();
	private List<String> canDao = new ArrayList<>();
	private List<String> canAddFir = new ArrayList<>();
	private List<String> latLng = new ArrayList<>();

	public DBManager() {
		initFalseData();
	}


	public static DBManager getInstance() {
		if (mInstance == null) {
			synchronized (DBManager.class) {
				if (mInstance == null) {
					mInstance = new DBManager();
				}
			}
		}
		return mInstance;
	}


	/**
	 * 根据地图中心点获取周围方圆一定距离内的房源位置
	 *
	 * @param centerLatLng  地图中心点
	 * @param areaKiloMeter 范围/公里
	 * @return 房源列表
	 */
	public List<LatLng> getHousesLocsByLatLng(LatLng centerLatLng, int areaKiloMeter, int house_num, int type) {
		List<LatLng> latLngList = new ArrayList<>();
		Random random = new Random();
		for (int i = 0; i < house_num; i++) {
			final double ranLat = (random.nextDouble() * 2 - 1) * ABS_LOC_3_KM + centerLatLng.getLatitude();  //随机纬度数
			final double ranLng = (random.nextDouble() * 2 - 1) * ABS_LOC_3_KM + centerLatLng.getLongitude();  //随机经度数

			latLngList.add(new LatLng(ranLat, ranLng));
		}


		return latLngList;
	}

	/**
	 * 随机获取一个房源
	 */
	public House getRandomSingleHouse() {
		return null;
	}


	/**
	 * 根据条件获取房源列表
	 *
	 * @param priceStr     价格
	 * @param houseTypeStr 户型
	 */
	public List<House> getHouseList(String priceStr, String houseTypeStr) {
		List<House> houseList = new ArrayList<>();


		return null;
	}

	/**
	 * 初始化假数据
	 */
	private void initFalseData() {
		/*小区名称*/
		{
			xiaoquName.add("华夏家园");
			xiaoquName.add("文庭雅苑");
			xiaoquName.add("美景天城");
			xiaoquName.add("中景濠庭");
			xiaoquName.add("枫蓝国际");
			xiaoquName.add("新康家园");
			xiaoquName.add("富泉花园");
			xiaoquName.add("博雅西园");
			xiaoquName.add("永泰园");
			xiaoquName.add("翡翠城");
			xiaoquName.add("世纪城·生活城");
			xiaoquName.add("世纪东方城");
			xiaoquName.add("纳帕溪谷");
			xiaoquName.add("光彩国际公寓");
		}
		/*距离*/
		{
			distance.add("据我100m");
			distance.add("据我300m");
			distance.add("据我500m");
			distance.add("据我800m");
			distance.add("据我1km");
			distance.add("据我1.3km");
			distance.add("据我1.5km");
			distance.add("据我1.7km");
			distance.add("据我1.9km");
			distance.add("据我2km");
			distance.add("据我3km");
			distance.add("据我5km");
			distance.add("据我7.5km");
			distance.add("据我8.3km");
			distance.add("据我9.7km");
			distance.add("据我11.2km");
			distance.add("据我15.9km");
		}
		/*发布时间*/
		{
			releaseTime.add("09-03 8:35");
			releaseTime.add("09-04 9:11");
			releaseTime.add("09-05 10:25");
			releaseTime.add("09-06 12:55");
			releaseTime.add("09-07 13:39");
			releaseTime.add("09-08 14:47");
			releaseTime.add("09-09 15:20");
			releaseTime.add("09-10 16:17");
			releaseTime.add("09-11 17:26");
			releaseTime.add("09-12 18:10");
			releaseTime.add("09-13 19:52");
			releaseTime.add("09-14 20:12");
		}
		/*价格*/
		{
			price0.add("700元/月");
			price0.add("800元/月");
			price0.add("800元/月");
			price0.add("800元/月");
			price0.add("800元/月");
		}
	}
}
