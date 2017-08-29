package com.zbl.anju.util;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.zbl.anju.R;
import com.zbl.anju.app.AppConst;

/**
 * 通知
 * Created by James on 2017/1/23.
 */

public class NotificationUtil {

	/**
	 * 显示通知（通知栏）
	 * 可伴随振动和声音
	 *
	 * @param context
	 * @param title
	 * @param msg
	 * @param intent
	 */
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	public static void showNotification(Context context, String title, String msg, Intent intent) {

//		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
//		boolean ifNotify = preferences.getBoolean("notifications_new_message", true);
//		if (!ifNotify) return;
//		String ringtone = preferences.getString("notifications_new_message_ringtone", "");
//		boolean ifVibrate = preferences.getBoolean("notifications_new_message_vibrate", true);
//		String led = preferences.getString("notifications_new_message_led", "");


		//获取设置
		boolean shouldNoti = SPUtils.getInstance(context).getBoolean(AppConst.SP_STR_NOTIFY_IF_NOTIFY, false);
		boolean shouldSound = SPUtils.getInstance(context).getBoolean(AppConst.SP_STR_NOTIFY_IF_SOUND, false);
		boolean shouldVibrate = SPUtils.getInstance(context).getBoolean(AppConst.SP_STR_NOTIFY_IF_VIBRATE, false);
		boolean shouldDetail = SPUtils.getInstance(context).getBoolean(AppConst.SP_STR_NOTIFY_IF_DETAIL, false);
		if (!shouldNoti) return;


		NotificationManager mgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		// 创建一个启动其他Activity的Intent
//		Intent intent = new Intent(context, );
		PendingIntent pi = PendingIntent.getActivity(
				context, 0, intent, 0);
		Notification.Builder builder = new Notification.Builder(context)
				// 设置打开该通知，该通知自动消失
				.setAutoCancel(true)
				// 设置显示在状态栏的通知提示信息
				.setTicker("您有一条新消息")
				// 设置通知的图标
				.setSmallIcon(R.mipmap.ic_launcher)
				// 设置通知内容的标题
				.setContentTitle(title)
				// 设置通知内容
				.setContentText(msg)
				// 设置使用系统默认的声音、默认LED灯
				.setDefaults(Notification.DEFAULT_SOUND)
				// 设置时间
				.setWhen(System.currentTimeMillis())
				// 设改通知将要启动程序的Intent
				.setContentIntent(pi);

//		if (ringtone != "") {
//			builder.setSound(Uri.parse(ringtone));
//		}
		if (shouldVibrate) {
			builder.setVibrate(new long[]{200, 100, 200, 100, 200, 100});
		}
		if (!shouldSound) {
			builder.setSound(null);
		}
		if (!shouldDetail) {
			builder.setContentTitle("");
			builder.setSubText("");
		}
//		if (led != "" && led != "no") {
//			if (led == "blue") {
//				builder.setLights(Color.BLUE, 1000, 300);
//			} else if (led == "green") {
//				builder.setLights(Color.GREEN, 1000, 300);
//			} else if (led == "yellow") {
//				builder.setLights(Color.YELLOW, 1000, 300);
//			}
//		}

		Notification notify = builder.build();

		// 发送通知
		mgr.notify(AppConst.NOTIFICATION_ID, notify);
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	public static void cancelAllNotification(Context context) {
		NotificationManager mgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		mgr.cancelAll();
	}
}
