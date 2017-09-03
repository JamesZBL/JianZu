package com.zbl.anju.app;


import com.zbl.anju.util.FileUtils;
import com.zbl.anju.util.LogUtils;

/**
 * @author James
 * @描述 全局常量
 */
public class AppConst {

	public static final String TAG = "James";
	public static final int DEBUGLEVEL = LogUtils.LEVEL_ALL;//日志输出级别

	public static final String REGION = "86";

	public static final String FIRST_LAUNCH = "first_launch_app";

	/*-----------------------请求码-------------------------*/
	public static final int REQ_CITY_PICK = 10014;


	/*-----------------------  UI  -------------------------*/
	//启动画面延迟时间（完成初始化后的延迟时间，防止出现启动主Activity时黑屏一段时间）
	public static final long SPLASH_DELAY = 500;
	public static final int MAIN_AD_BANNER_INTERVAL = 4000; //主页广告轮播间隔(毫秒)
	public static final int BANNER_DELAY = 5000;  //轮播图延迟
	public static final int SCROLL_LISTEN = 100;  //滑动监听
	public static final float SCRN_DIM_AMOUNT = 0.7f;
	public static final float SCRN_DIM_AMOUNT_DIM = 0.3f;


	/* ----------------------地图-----------------------------*/
	public static final int MAP_DEFAULT_ZOOM_LEVEL = 12;    //默认缩放级别
	public static final int MAP_LOC_SUCCESS_ZOOM_LEVEL = 18;    //定位成功后的缩放级别
	public static final int MAP_3_KM_ZOOM_LEVEL = 14;    //可以显示周围3公里的缩放级别
	public static final double MAP_DEFAULT_LATITUDE = 38.042864;    //默认中心点纬度
	public static final double MAP_DEFAULT_LONGITUDE = 114.512215;    //默认中心点经度
	public static final long LOCATION_DEFAULT_INTERVAL = 1000;   //定位周期/毫秒

	/* ----------------------房屋-----------------------------*/
	public static final int HOUSE_DEFAULT_HOUSE_NUM = 15;    //默认推荐房源数量
	public static final int HOUSE_DEFAULT_HOUSE_LIST_NUM = 50;    //默认推荐房源数量





	/**/
	/**/
	/**/
	/**/
	/**/
	/**/
	/**/
	/**/
	/**/
	/**/
	/**/
	/**/
	/**/
	/**/
	/**/
	/**/
	/**/
	/**/

	/*================== 广播Action begin ==================*/
	//声明周期
	public static final String LIFE_RESUME = "app_life_resume";
	//全局数据获取
	public static final String FETCH_COMPLETE = "fetch_complete";
	//好友
	public static final String UPDATE_FRIEND = "update_friend";
	public static final String UPDATE_RED_DOT = "update_red_dot";
	//群组
	public static final String UPDATE_GROUP_NAME = "update_group_name";
	public static final String GROUP_LIST_UPDATE = "group_list_update";
	public static final String UPDATE_GROUP = "update_group";
	public static final String UPDATE_GROUP_MEMBER = "update_group_member";
	public static final String GROUP_DISMISS = "group_dismiss";
	//个人信息
	public static final String CHANGE_INFO_FOR_ME = "change_info_for_me";
	public static final String CHANGE_INFO_FOR_CHANGE_NAME = "change_info_for_change_name";
	public static final String CHANGE_INFO_FOR_USER_INFO = "change_info_for_user_info";
	//会话
	public static final String UPDATE_CONVERSATIONS = "update_conversations";
	public static final String UPDATE_CURRENT_SESSION = "update_current_session";
	public static final String UPDATE_CURRENT_SESSION_NAME = "update_current_session_name";
	public static final String REFRESH_CURRENT_SESSION = "refresh_current_session";
	public static final String CLOSE_CURRENT_SESSION = "close_current_session";
	/*================== 广播Action end ==================*/


	public static final class User {
		public static final String ID = "id";
		public static final String PHONE = "phone";
		//        public static final String ACCOUNT = "account";
		public static final String TOKEN = "token";
		public static final String PWD = "password";
	}


	public static final class WeChatUrl {
		public static final String HELP_FEED_BACK = "https://kf.qq.com/touch/product/wechat_app.html?scene_id=kf338&code=001ls8gj1IuCnz0kiUfj15uIfj1ls8ga&state=123";
		public static final String JD = "https://m.jd.com";
		public static final String TB = "https://www.tmall.com";
		public static final String GAME = "http://h.4399.com/android/?301";
	}

	public static final class QrCodeCommon {
		public static final String ADD = "add:";//加好友
		public static final String JOIN = "join:";//入群
	}

	//语音存放位置
	public static final String AUDIO_SAVE_DIR = FileUtils.getDir("audio");
	public static final int DEFAULT_MAX_AUDIO_RECORD_TIME_SECOND = 120;
	//视频存放位置
	public static final String VIDEO_SAVE_DIR = FileUtils.getDir("video");
	//照片存放位置
	public static final String PHOTO_SAVE_DIR = FileUtils.getDir("photo");
	//头像保存位置
	public static final String HEADER_SAVE_DIR = FileUtils.getDir("header");

	/*H5*/
	public static final String WEB_GAME_MODE = "web_game_mode";
	public static final String ASSET_URL = "file:///android_asset/";
	public static final String HTTP_URL = "http://";
	public static final String HTTPS_URL = "https://";
	public static final String H5_USER_PROTOCOL = ASSET_URL + "h5/userProtocol.html";
	public static final String H5_RONGCLOUD = HTTP_URL + "rongcloud.cn";
	public static final String H5_GAME = ASSET_URL + "h5_games/index.html";
	public static final String H5_TEN_CLOUND = HTTPS_URL + "www.qcloud.com/";

	public static final String H5_SCORE_RESULT_SEARCH = HTTP_URL + "http://112.124.54.19/Score/index.html?identity=7A41BA2A32ADAD55854C203C924D9E21";
	public static final String H5_CLASSROOM_SEARCH = HTTP_URL + "112.124.56.216:8080/Super/emptyClass/index.html?identity=7A41BA2A32ADAD55854C203C924D9E21";
	public static final String H5_SUPER_GROUP = HTTP_URL + "club.super.cn/m/home.jsp?identity=7A41BA2A32ADAD55854C203C924D9E21";
	public static final String H5_TRAIN_BUS_TICKETS = HTTP_URL + "m.tieyou.com/";
	public static final String H5_AIR_TICKETS = HTTPS_URL + "touch.qunar.com/h5/flight/";
	public static final String H5_SCHOOL_RECUIT = HTTP_URL + "luna.58.com/m/autotemplate?city=sjz&temname=job_common&utm_source=link&spm=u-LlVFdJga1luDubj.mzp_zpdl";
	public static final String H5_HOUSE_RENT = HTTP_URL + "luna.58.com/m/sjz/zufang.shtml?utm_source=link&spm=u-LlVFdJga1luDubj.mzf_zftc&-15=20";
	public static final String H5_ENTERTAINMANIT_CLASS = HTTP_URL + "www.youku.com";

	/*开发者*/
	public static final String DEVELOPER_QQ = "1146556298";
	public static final String QQ_SCHEME_URL = "mqqwpa://im/chat?chat_type=wpa&uin=";

	/*消息提醒*/
	public static int NOTIFICATION_ID = 2000;
	public static final String SP_STR_NOTIFY_IF_NOTIFY = "sp_user_should_notify";
	public static final String SP_STR_NOTIFY_IF_SOUND = "sp_user_should_sound";
	public static final String SP_STR_NOTIFY_IF_VIBRATE = "sp_user_should_vibrate";
	public static final String SP_STR_NOTIFY_IF_DETAIL = "sp_user_should_detail";

	/* book */
	public static final String H5_BOOK_SEARCH_URL = HTTPS_URL + "so.m.jd.com/ware/search.action?keyword=";

	/*****************************************广播*******************************************/
	/*book*/
	public static final String UPDATE_BOOK_INFO = "update_bookk_info";
	public static final String TYPE_EDIT_BOOK_INFO = "edit_bookk_info";
	public static final String TYPE_ADD_BOOK_INFO = "add_bookk_info";
	public static final String TYPE_HANDLE_BOOK = "type_handle_book";
	public static final String BOOK_NAME = "book_name";
	public static final String BOOK_NUM = "book_number";
	public static final String BOOK_ID = "book_id";
	public static final int ADD_BOOK_INFO_REQ_CODE = 10011;
	public static final int EDIT_BOOK_INFO_REQ_CODE = 10012;

	/* signature */
	public static final String FILE_WRITE_OK = "file_write_success";
	public static final String FILE_WRITE_ERROR = "file_write_error";

	/* signature 广播*/
	public static final String SIGNATURE_UPLOAD_STARTED = "signature_upload_started";
	public static final String SIGNATURE_UPLOAD_SUCCESS = "signature_upload_success";
	public static final String SIGNATURE_UPLOAD_FAILED = "signature_upload_failed";

	public static final int INIT_PERMISSION_REQ_CODE = 10013;
}
