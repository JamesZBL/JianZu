package com.zbl.anju.app;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.DisplayMetrics;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.umeng.analytics.MobclickAgent;
import com.uuch.adlibrary.utils.DisplayUtil;
import com.zbl.anju.app.base.BaseApp;
import com.zbl.anju.model.data.GroupNotificationMessageData;
import com.zbl.anju.umeng.UmengTestUtil;
import com.zbl.anju.util.SPUtils;

import org.json.JSONArray;
import org.json.JSONObject;


/**
 * @author James
 * @描述 BaseApp的拓展，用于设置其他第三方的初始化
 */
public class MyApp extends BaseApp {

	@Override
	public void onCreate() {
		super.onCreate();
		//注册声明周期方法
		initActivityLifeCycleCallbacks();
//		//初始化极光推送
//		initJPush();
		/*****************************************************************************************/
		/**
		 * 友盟调试模式
		 * 仅在测试期间调用
		 * 发布之前需删掉
		 */
		//// TODO: 2017/6/20 发布之前删掉此段
		MobclickAgent.setDebugMode(true);
		UmengTestUtil.getDeviceInfo(getContext());
		initFresco();
		/*****************************************************************************************/
	}

	/**
	 * 初始化 Fresco
	 *  adDialog用到
	 */
	private void initFresco() {
		Fresco.initialize(this);
		DisplayMetrics dm = getResources().getDisplayMetrics();
		DisplayUtil.density = dm.density;
		DisplayUtil.densityDPI = dm.densityDpi;
		DisplayUtil.screenWidthPx = dm.widthPixels;
		DisplayUtil.screenhightPx = dm.heightPixels;
		DisplayUtil.screenWidthDip = DisplayUtil.px2dip(getApplicationContext(), dm.widthPixels);
		DisplayUtil.screenHightDip = DisplayUtil.px2dip(getApplicationContext(), dm.heightPixels);
	}


	private GroupNotificationMessageData jsonToBean(String data) {
		GroupNotificationMessageData dataEntity = new GroupNotificationMessageData();
		try {
			JSONObject jsonObject = new JSONObject(data);
			if (jsonObject.has("operatorNickname")) {
				dataEntity.setOperatorNickname(jsonObject.getString("operatorNickname"));
			}
			if (jsonObject.has("targetGroupName")) {
				dataEntity.setTargetGroupName(jsonObject.getString("targetGroupName"));
			}
			if (jsonObject.has("timestamp")) {
				dataEntity.setTimestamp(jsonObject.getLong("timestamp"));
			}
			if (jsonObject.has("targetUserIds")) {
				JSONArray jsonArray = jsonObject.getJSONArray("targetUserIds");
				for (int i = 0; i < jsonArray.length(); i++) {
					dataEntity.getTargetUserIds().add(jsonArray.getString(i));
				}
			}
			if (jsonObject.has("targetUserDisplayNames")) {
				JSONArray jsonArray = jsonObject.getJSONArray("targetUserDisplayNames");
				for (int i = 0; i < jsonArray.length(); i++) {
					dataEntity.getTargetUserDisplayNames().add(jsonArray.getString(i));
				}
			}
			if (jsonObject.has("oldCreatorId")) {
				dataEntity.setOldCreatorId(jsonObject.getString("oldCreatorId"));
			}
			if (jsonObject.has("oldCreatorName")) {
				dataEntity.setOldCreatorName(jsonObject.getString("oldCreatorName"));
			}
			if (jsonObject.has("newCreatorId")) {
				dataEntity.setNewCreatorId(jsonObject.getString("newCreatorId"));
			}
			if (jsonObject.has("newCreatorName")) {
				dataEntity.setNewCreatorName(jsonObject.getString("newCreatorName"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataEntity;
	}


	public static String getCurProcessName(Context context) {

		int pid = android.os.Process.myPid();

		ActivityManager activityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);

		for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
				.getRunningAppProcesses()) {

			if (appProcess.pid == pid) {
				return appProcess.processName;
			}
		}
		return null;
	}

	public static boolean isFirstLaunchThisVersion(Context context, Class clazz) {
		return SPUtils.getInstance(context).getBoolean(AppConst.FIRST_LAUNCH + getVersionCode(context) + clazz.getName(), true);
	}


	private void initActivityLifeCycleCallbacks() {
		registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {

			@Override
			public void onActivityStopped(Activity activity) {

			}

			@Override
			public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

			}

			/**
			 * 从后台切换到前台
			 * @param activity
			 */
			@Override
			public void onActivityStarted(Activity activity) {

			}

			@Override
			public void onActivityPaused(Activity activity) {
				//友盟Session统计
				MobclickAgent.onPause(activity);
			}

			@Override
			public void onActivityResumed(Activity activity) {
				//友盟Session统计
				MobclickAgent.onResume(activity);
			}


			@Override
			public void onActivityDestroyed(Activity activity) {
				//友盟Session统计
				MobclickAgent.onKillProcess(activity);
			}

			@Override
			public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

			}
		});
	}

	/**
	 * 获取当前版本号
	 *
	 * @param context
	 * @return
	 */
	public static String getVersionCode(Context context) {
		PackageManager packageManager = context.getPackageManager();
		PackageInfo packageInfo;
		String versionCode = "";
		try {
			packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
			versionCode = packageInfo.versionCode + "";
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return versionCode;
	}
}
