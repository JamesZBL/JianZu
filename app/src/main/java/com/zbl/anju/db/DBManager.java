package com.zbl.anju.db;

import com.litesuits.common.utils.RandomUtil;
import com.tencent.mapsdk.raster.model.LatLng;
import com.zbl.anju.app.AppConst;
import com.zbl.anju.model.data.ErShouThing;
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

	/* house */
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
	private List<String> houseTypes = new ArrayList<>();

	/* ershouThing*/
	private List<String> ershouNames = new ArrayList<>();
	private List<String> ershouPrices = new ArrayList<>();
	private List<String> ershouDiscription = new ArrayList<>();
	private List<String> ershouImageUrl = new ArrayList<>();

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
	 * 随机获取一个房源 按条件
	 */
	public House getRandomSingleHouse(String houseTypeStr, String priceStr) {
		House houseItem = new House();

		int i1 = RandomUtil.getRandom(0, xiaoquName.size() - 1);
		houseItem.setXiaoquName(xiaoquName.get(i1));

		int i2 = RandomUtil.getRandom(0, distance.size() - 1);
		houseItem.setDistance(distance.get(i2));

		int i3 = RandomUtil.getRandom(0, releaseTime.size() - 1);
		houseItem.setReleaseTime(releaseTime.get(i3));

		int i4;
		switch (priceStr) {
			case "1000以下": {
				i4 = RandomUtil.getRandom(0, price0.size() - 1);
				houseItem.setPrice(price0.get(i4));
				break;
			}
			case "1000-1500": {
				i4 = RandomUtil.getRandom(0, price1.size() - 1);
				houseItem.setPrice(price1.get(i4));
				break;
			}
			case "1500-2000": {
				i4 = RandomUtil.getRandom(0, price2.size() - 1);
				houseItem.setPrice(price2.get(i4));
				break;
			}
			case "2000以上": {
				i4 = RandomUtil.getRandom(0, price3.size() - 1);
				houseItem.setPrice(price3.get(i4));
				break;
			}
		}

		int i5 = RandomUtil.getRandom(0, hostName.size() - 1);
		houseItem.setHostName(hostName.get(i5));

		int i6 = RandomUtil.getRandom(0, hostPhoneNum.size() - 1);
		houseItem.setHostPhoneNum(hostPhoneNum.get(i6));

		int i7 = RandomUtil.getRandom(0, urlVideoThumbnail.size() - 1);
		houseItem.setUrlVideoThumbnail(urlVideoThumbnail.get(i7));

		int i8 = RandomUtil.getRandom(0, canDao.size() - 1);
		houseItem.setCanDao(canDao.get(i8));

		int i9 = RandomUtil.getRandom(0, canAddFir.size() - 1);
		houseItem.setCanAddFir(canAddFir.get(i9));

		houseItem.setType(houseTypeStr);


		return houseItem;
	}

	/**
	 * 随机获取一个房源
	 *
	 * @return
	 */
	public House getRandomSingleHouse() {
		House houseItem = new House();

		int i1 = RandomUtil.getRandom(0, xiaoquName.size() - 1);
		houseItem.setXiaoquName(xiaoquName.get(i1));

		int i3 = RandomUtil.getRandom(0, releaseTime.size() - 1);
		houseItem.setReleaseTime(releaseTime.get(i3));

		int i4 = RandomUtil.getRandom(0, price2.size() - 1);
		houseItem.setPrice(price2.get(i4));


		int i5 = RandomUtil.getRandom(0, hostName.size() - 1);
		houseItem.setHostName(hostName.get(i5));

		int i6 = RandomUtil.getRandom(0, hostPhoneNum.size() - 1);
		houseItem.setHostPhoneNum(hostPhoneNum.get(i6));

		int i7 = RandomUtil.getRandom(0, urlVideoThumbnail.size() - 1);
		houseItem.setUrlVideoThumbnail(urlVideoThumbnail.get(i7));

		int i8 = RandomUtil.getRandom(0, canDao.size() - 1);
		houseItem.setCanDao(canDao.get(i8));

		int i9 = RandomUtil.getRandom(0, canAddFir.size() - 1);
		houseItem.setCanAddFir(canAddFir.get(i9));

		int i10 = RandomUtil.getRandom(0, houseTypes.size() - 1);
		houseItem.setType(houseTypes.get(i10));

		return houseItem;
	}

	/**
	 * 根据条件获取房源列表
	 *
	 * @param priceStr     价格
	 * @param houseTypeStr 户型
	 */
	public List<House> getHouseList(String houseTypeStr, String priceStr) {
		List<House> houseList = new ArrayList<>();
		for (int i = 0; i < AppConst.HOUSE_DEFAULT_HOUSE_LIST_NUM; i++) {
			House houseItem = getRandomSingleHouse(houseTypeStr, priceStr);
			houseList.add(houseItem);
		}

		return houseList;
	}

	/**
	 * 获取二手推荐
	 *
	 * @return
	 */
	public List<ErShouThing> getErshouTuijian() {
		List<ErShouThing> ershouList = new ArrayList<>();
		for (int i = 0; i < ershouNames.size(); i++) {
			ErShouThing ershouItem = new ErShouThing();
			ershouItem.setName(ershouNames.get(i));
			ershouItem.setPrice(ershouPrices.get(i));
			ershouItem.setImgUrl(ershouImageUrl.get(i));

			int i1 = RandomUtil.getRandom(0, releaseTime.size() - 1);
			ershouItem.setRerleaseTime(releaseTime.get(i1));

			int i2 = RandomUtil.getRandom(0, distance.size() - 1);
			ershouItem.setDistance(distance.get(i1));

			ershouList.add(ershouItem);
		}
		return ershouList;
	}

	/**
	 * 初始化假数据
	 */
	private void initFalseData() {
		/* 房屋户型 */
		{
			houseTypes.add("一室");
			houseTypes.add("两室");
			houseTypes.add("三室");
		}
		/* 小区名称 */
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
			xiaoquName.add("丽港·美度");
			xiaoquName.add("定福家园");
			xiaoquName.add("金地·格林小镇");
			xiaoquName.add("山水文园");
			xiaoquName.add("金晖家园");
			xiaoquName.add("月桂庄园");
			xiaoquName.add("华贸中心");
		}
		/* 距离 */
		{
			distance.add("据我100m");
			distance.add("据我150m");
			distance.add("据我300m");
			distance.add("据我370m");
			distance.add("据我500m");
			distance.add("据我750m");
			distance.add("据我800m");
			distance.add("据我1km");
			distance.add("据我1.2km");
			distance.add("据我1.3km");
			distance.add("据我1.5km");
			distance.add("据我1.6km");
			distance.add("据我1.7km");
			distance.add("据我1.8km");
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
		/* 发布时间 */
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
		/*价格 500-1000 */
		{
			price0.add("500元/月");
			price0.add("600元/月");
			price0.add("700元/月");
			price0.add("800元/月");
			price0.add("1000元/月");
		}
		/*价格 1000-1500 */
		{
			price1.add("1100元/月");
			price1.add("1200元/月");
			price1.add("1300元/月");
			price1.add("1400元/月");
			price1.add("1500元/月");
		}
		/*价格 1500-2000 */
		{
			price2.add("1600元/月");
			price2.add("1700元/月");
			price2.add("1800元/月");
			price2.add("1900元/月");
			price2.add("2000元/月");
		}
		/*价格 2000 以上 */
		{
			price3.add("2100元/月");
			price3.add("2200元/月");
			price3.add("2300元/月");
			price3.add("2400元/月");
			price3.add("2500元/月");
			price3.add("2800元/月");
		}
		/* 房主name */
		{
			hostName.add("李先生");
			hostName.add("李女士");
			hostName.add("王先生");
			hostName.add("王女士");
			hostName.add("张先生");
			hostName.add("张女士");
			hostName.add("赵先生");
			hostName.add("赵女士");
		}
		/* 房主phoneNum */
		{
			hostPhoneNum.add("133****8890");
			hostPhoneNum.add("135****7830");
			hostPhoneNum.add("139****2358");
			hostPhoneNum.add("151****1190");
			hostPhoneNum.add("155****8831");
			hostPhoneNum.add("159****3589");
			hostPhoneNum.add("183****9120");
		}
		/* 缩略图 */
		{
			urlVideoThumbnail.add("http://pic.58pic.com/58pic/13/80/06/68558PIC7Fn_1024.jpg");
			urlVideoThumbnail.add("http://pic.58pic.com/58pic/14/32/00/83A58PICDH5_1024.jpg");
			urlVideoThumbnail.add("http://pic.58pic.com/58pic/13/23/73/67y58PICzAc_1024.jpg");
			urlVideoThumbnail.add("http://pic.58pic.com/58pic/16/41/62/95I58PICRFd_1024.jpg");
			urlVideoThumbnail.add("http://pic56.nipic.com/file/20141213/15265552_175423699768_2.jpg");
			urlVideoThumbnail.add("http://pic.58pic.com/58pic/13/22/62/13V58PICAk2_1024.jpg");
			urlVideoThumbnail.add("http://pic.58pic.com/58pic/17/07/88/98y58PIChDF_1024.jpg");
			urlVideoThumbnail.add("http://pic.58pic.com/58pic/13/60/04/49K58PICjWN_1024.jpg");
			urlVideoThumbnail.add("http://img.taopic.com/uploads/allimg/110317/119-11031F4061430.jpg");
			urlVideoThumbnail.add("http://static.fuwo.com/upload/attachment/1604/15/93aa2aac02d311e6a06100163e00254c.jpeg");
			urlVideoThumbnail.add("http://img.zcool.cn/community/01615e58253824a84a0d304f3938e3.jpg@900w_1l_2o_100sh.jpg");
			urlVideoThumbnail.add("http://imgsrc.baidu.com/image/c0%3Dshijue1%2C0%2C0%2C294%2C40/sign=3a9efd80a3014c080d3620e66212687d/6f061d950a7b0208a84e4fc868d9f2d3572cc810.jpg");
			urlVideoThumbnail.add("http://www.nnjuya.com/imageRepository/a07bf833-097a-4f8d-b1be-8fc0c71b7db7.jpg");
			urlVideoThumbnail.add("http://pic.qiantucdn.com/58pic/18/23/94/12V58PICHbT_1024.jpg");
			urlVideoThumbnail.add("http://pic7.nipic.com/20100604/1912176_014540050840_2.jpg");
			urlVideoThumbnail.add("http://mpic.tiankong.com/c64/4e6/c644e61f9dffde34e857f470aa38d3fd/640.jpg");
			urlVideoThumbnail.add("http://imgsrc.baidu.com/image/c0%3Dshijue1%2C0%2C0%2C294%2C40/sign=40b96dcbbe8f8c54f7decd6c52404780/95eef01f3a292df50f160730b6315c6034a8733a.jpg");
			urlVideoThumbnail.add("http://www.tlnews.com.cn/fashion/content/attachement/jpg/site111/20151208/002564a41e2b17d073ac2e.jpg");
			urlVideoThumbnail.add("http://img.xtuan.com/upload/image/20140126/09500650594_w.jpg");
			urlVideoThumbnail.add("http://pic.58pic.com/58pic/17/11/30/89Q58PICun6_1024.jpg");
		}
		/* 可刀 */
		{
			canDao.add("可议价");
			canDao.add("不可议价");
		}
		/* 可添家具 */
		{
			canAddFir.add("可自行添置家具");
			canAddFir.add("不可自行添置家具");
		}


		/*------- erhou -------*/
		/* 名称 */
		{
			ershouNames.add("二手原装松下TH-P50ST30H");
			ershouNames.add("进口二手索尼KDL-46X4500王牌LED电视机");
			ershouNames.add("出售海尔双杠洗衣机7.0公斤基本全新的");

			ershouNames.add("海尔全自动洗衣机");
			ershouNames.add("09年容声BCD_183C冰箱（只现同城自提）");
			ershouNames.add("全新未拆封海尔Leader/统帅 BCD-206LSTPF三门家用节能电冰箱");

			ershouNames.add("ghd正品意大利版aura负离子电吹风");
			ershouNames.add("九阳榨汁机便宜转");
			ershouNames.add("处理布艺床一套带床垫");

			ershouNames.add("老榆木 柜子 床头柜 电视柜");
		}
		/* 价格 */
		{
			ershouPrices.add("¥3680.00");
			ershouPrices.add("¥2800.00");
			ershouPrices.add("¥950.00");

			ershouPrices.add("¥380.00");
			ershouPrices.add("¥300.00");
			ershouPrices.add("¥950.00");

			ershouPrices.add("¥90.00");
			ershouPrices.add("¥300.00");
			ershouPrices.add("¥300.00");

			ershouPrices.add("¥500.00");
		}
		/* 缩略图 */
		{
			ershouImageUrl.add("http://img1.cache.netease.com/catchpic/1/16/1668D219B94A7013BB77241FC8BC6380.JPG");
			ershouImageUrl.add("http://photocdn.sohu.com/20140808/Img403253747.jpg");
			ershouImageUrl.add("http://att2.citysbs.com/tiaozao/2012/09/10/23/middle_232322_16441347290602162_095d51bd6a8c5bc49f6b049dd6723080.jpg");
			ershouImageUrl.add("http://image5.suning.cn/uimg/shp/userItems/146885039746954447_1_1000x1000.jpg");
			ershouImageUrl.add("http://appimg.pba.cn/2016/08/31/3BD18C7D1ACCF9E24CC542D9D79F8FFB1472620981401.jpg");
			ershouImageUrl.add("http://att2.citysbs.com/tiaozao/2013/12/09/20/middle_2448x3264-205904_19251386593944910_17c182e82a82e4b44cb1df792bffa059.jpg");
			ershouImageUrl.add("http://img005.hc360.cn/hb/MTQ1NTk0MDQ5NDAwNjE4NzkyMjg0MDc=.jpg");
			ershouImageUrl.add("http://image3.suning.cn/uimg/shp/userItems/145403665063976893_1_1000x1000.jpg");
			ershouImageUrl.add("http://www.qhmb1.com/upfile/proimage/20135141956366382.jpg");

			ershouImageUrl.add("http://www.91spj.com/eb8ced3cb78ca086c25c8ce2bf449769");

		}
	}

	/**
	 * 按距离获取房源
	 * 3km以内的
	 *
	 * @return
	 */
	public List<House> getHouseListByDistance() {
		List<House> houseList = new ArrayList<>();
		//17
		for (int i = 0; i < 17; i++) {
			House houseItem = getRandomSingleHouse();
			houseItem.setDistance(distance.get(i));
			houseList.add(houseItem);
		}

		return houseList;
	}
}
