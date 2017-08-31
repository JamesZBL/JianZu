package com.zbl.anju.db;

import com.tencent.mapsdk.raster.model.LatLng;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author James
 * @描述 数据库管理器
 */
public class DBManager {

	private static DBManager mInstance;
	private static final double ABS_LOC_3_KM = 0.035835;    //3公里对应的经纬度变化

	public DBManager() {

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
	public List<LatLng> getHousesLocsByLatLng(LatLng centerLatLng, int areaKiloMeter, int house_num,int type) {
		List<LatLng> latLngList = new ArrayList<>();
		Random random = new Random();
		for (int i = 0; i < house_num; i++) {
			final double ranLat = (random.nextDouble() * 2 - 1) * ABS_LOC_3_KM + centerLatLng.getLatitude();  //随机纬度数
			final double ranLng = (random.nextDouble() * 2 - 1) * ABS_LOC_3_KM + centerLatLng.getLongitude();  //随机经度数

			latLngList.add(new LatLng(ranLat, ranLng));
		}


		return latLngList;
	}

}
