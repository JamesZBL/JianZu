package com.zbl.anju.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.ViewDragHelper;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.flyco.animation.BaseAnimatorSet;
import com.flyco.animation.BounceEnter.BounceTopEnter;
import com.flyco.animation.SlideExit.SlideBottomExit;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.NormalDialog;
import com.muddzdev.styleabletoastlibrary.StyleableToast;
import com.zbl.anju.R;
import com.zbl.anju.app.AppConst;
import com.zbl.anju.app.MyApp;
import com.zbl.anju.app.base.BaseApp;

import java.lang.reflect.Field;

import me.shaohui.bottomdialog.BottomDialog;

import static com.zhy.autolayout.utils.ScreenUtils.getStatusBarHeight;


/**
 * @author James
 * @描述 和ui相关的工具类
 */
public class UIUtils {

	public static Toast mToast;
	public static StyleableToast mStyleableToast;
	public static int screenWidth;
	public static int screenHeight;
	public static int screenMin;// 宽高中，小的一边
	public static int screenMax;// 宽高中，较大的值
	public static float density;
	public static float scaleDensity;
	public static float xdpi;
	public static float ydpi;
	public static int densityDpi;
	public static int statusbarheight;
	public static int navbarheight;


	/**
	 * 显示toast
	 *
	 * @param msg 消息内容
	 */
	public static void showToast(String msg) {
		showToast(msg, Toast.LENGTH_SHORT);
	}


	/**
	 * 显示一定时间长度的toast
	 *
	 * @param msg      消息内容
	 * @param duration 显示时长
	 */
	public static void showToast(String msg, int duration) {
		/*if (mToast == null) {
			mToast = Toast.makeText(getContext(), "", duration);
		}
		mToast.setText(msg);
		mToast.show();*/

		if (mStyleableToast == null) {
			mStyleableToast = StyleableToast.makeText(getContext(), msg, duration, R.style.styleable_toast);
		} else {
			mStyleableToast.setText(msg);
		}
		mStyleableToast.show();
	}

	/**
	 * 可自定义位置的 toast
	 *
	 * @param msg
	 * @param duration
	 * @param gravity
	 */
	public static void showToast(String msg, int duration, int gravity) {
		if (mToast == null) {
			mToast = Toast.makeText(getContext(), "", duration);
		}
		mToast.setText(msg);
		mToast.setGravity(gravity, 0, 0);
		mToast.show();
	}

	/**
	 * 用于在线程中执行弹土司操作
	 */
	public static void showToastSafely(final String msg) {
		UIUtils.getMainThreadHandler().post(new Runnable() {

			@Override
			public void run() {
				if (mToast == null) {
					mToast = Toast.makeText(getContext(), "", Toast.LENGTH_SHORT);
				}
				mToast.setText(msg);
				mToast.show();
			}
		});
	}


	/**
	 * 得到上下文
	 *
	 * @return
	 */
	public static Context getContext() {
		return BaseApp.getContext();
	}

	/**
	 * 得到resources对象
	 *
	 * @return
	 */
	public static Resources getResource() {
		return getContext().getResources();
	}

	/**
	 * 得到string.xml中的字符串
	 *
	 * @param resId
	 * @return
	 */
	public static String getString(int resId) {
		return getResource().getString(resId);
	}

	/**
	 * 得到string.xml中的字符串，带点位符
	 *
	 * @return
	 */
	public static String getString(int id, Object... formatArgs) {
		return getResource().getString(id, formatArgs);
	}

	/**
	 * 得到string.xml中和字符串数组
	 *
	 * @param resId
	 * @return
	 */
	public static String[] getStringArr(int resId) {
		return getResource().getStringArray(resId);
	}

	/**
	 * 得到colors.xml中的颜色
	 *
	 * @param colorId
	 * @return
	 */
	public static int getColor(int colorId) {
		return getResource().getColor(colorId);
	}

	/**
	 * 得到应用程序的包名
	 *
	 * @return
	 */
	public static String getPackageName() {
		return getContext().getPackageName();
	}

	/**
	 * 得到主线程Handler
	 *
	 * @return
	 */
	public static Handler getMainThreadHandler() {
		return MyApp.getMainHandler();
	}

	/**
	 * 得到主线程id
	 *
	 * @return
	 */
	public static long getMainThreadId() {
		return MyApp.getMainThreadId();
	}

	/**
	 * 安全的执行一个任务
	 *
	 * @param task
	 */
	public static void postTaskSafely(Runnable task) {
		int curThreadId = android.os.Process.myTid();
		// 如果当前线程是主线程
		if (curThreadId == getMainThreadId()) {
			task.run();
		} else {
			// 如果当前线程不是主线程
			getMainThreadHandler().post(task);
		}
	}

	/**
	 * 延迟执行任务
	 *
	 * @param task
	 * @param delayMillis
	 */
	public static void postTaskDelay(Runnable task, int delayMillis) {
		getMainThreadHandler().postDelayed(task, delayMillis);
	}

	/**
	 * 移除任务
	 */
	public static void removeTask(Runnable task) {
		getMainThreadHandler().removeCallbacks(task);
	}

	/**
	 * dip-->px
	 */
	public static int dip2Px(int dip) {
		// px/dip = density;
		// density = dpi/160
		// 320*480 density = 1 1px = 1dp
		// 1280*720 density = 2 2px = 1dp

		float density = getResource().getDisplayMetrics().density;
		int px = (int) (dip * density + 0.5f);
		return px;
	}

	/**
	 * px-->dip
	 */
	public static int px2dip(int px) {

		float density = getResource().getDisplayMetrics().density;
		int dip = (int) (px / density + 0.5f);
		return dip;
	}

	/**
	 * sp-->px
	 */
	public static int sp2px(int sp) {
		return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResource().getDisplayMetrics()) + 0.5f);
	}


	public static int getDisplayWidth() {
		if (screenWidth == 0) {
			GetInfo(UIUtils.getContext());
		}
		return screenWidth;
	}

	public static int getDisplayHeight() {
		if (screenHeight == 0) {
			GetInfo(UIUtils.getContext());
		}
		return screenHeight;
	}

	public static void GetInfo(Context context) {
		if (null == context) {
			return;
		}
		DisplayMetrics dm = context.getApplicationContext().getResources().getDisplayMetrics();
		screenWidth = dm.widthPixels;
		screenHeight = dm.heightPixels;
		screenMin = (screenWidth > screenHeight) ? screenHeight : screenWidth;
		screenMax = (screenWidth < screenHeight) ? screenHeight : screenWidth;
		density = dm.density;
		scaleDensity = dm.scaledDensity;
		xdpi = dm.xdpi;
		ydpi = dm.ydpi;
		densityDpi = dm.densityDpi;
		statusbarheight = getStatusBarHeight(context);
		navbarheight = getNavBarHeight(context);
	}

	public static int getNavBarHeight(Context context) {
		Resources resources = context.getResources();
		int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
		if (resourceId > 0) {
			return resources.getDimensionPixelSize(resourceId);
		}
		return 0;
	}

	public static int getStatusbarheight(Context context) {
		Resources resources = context.getResources();
		int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			return resources.getDimensionPixelSize(resourceId);
		}
		return 0;
	}

	/**
	 * 设置窗口透明度
	 * 弹出popwindow时改变背景，使背景变暗
	 *
	 * @param activity
	 * @param alpha
	 */
	public static void setWindowAlpha(Activity activity, float alpha) {
		Handler handler = new Handler();
		WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
		float alphaInit = lp.alpha;
		Window window = activity.getWindow();
		for (int i = 0; i < 200; i++) {
			int finalI = i;
			handler.postDelayed(() -> {
				lp.alpha = alphaInit - finalI * (alphaInit - alpha) / 200;
				window.setAttributes(lp);
			}, i);
		}
		lp.alpha = alpha;

	}

	/**
	 * 窗口过度动画
	 */

	public static void startWindowAnimation(Activity activity, int inAnimResId, int outAnimResId) {
		activity.overridePendingTransition(inAnimResId, outAnimResId);
	}

	/**
	 * 弹出底部Dialog
	 */
	public static BottomDialog showBottomDialog(FragmentManager fgmanager, int layoutId, BottomDialog.ViewListener listener) {
		BottomDialog dialog = BottomDialog.create(fgmanager)
				.setLayoutRes(layoutId)
				.setDimAmount(1 - AppConst.SCRN_DIM_AMOUNT_DIM)
				.setCancelOutside(false)// 点击外部区域是否关闭，默认true
				.setViewListener(listener);
		dialog.show();
		dialog.setCancelable(true);
		return dialog;
	}

	public static void focusEdt(EditText editText) {
		InputMethodManager inputManager = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		new Handler().postDelayed(() -> {
			inputManager.showSoftInput(editText, 0);
		}, 500);
	}


	/**
	 * 抽屉滑动范围控制
	 *
	 * @param activity
	 * @param drawerLayout
	 * @param displayWidthPercentage 占全屏的份额0~1
	 */
	public static void setDrawerLeftEdgeSize(Activity activity, DrawerLayout drawerLayout, float displayWidthPercentage) {
		if (activity == null || drawerLayout == null)
			return;
		try {
			// find ViewDragHelper and set it accessible
			Field leftDraggerField = drawerLayout.getClass().getDeclaredField("mLeftDragger");
			leftDraggerField.setAccessible(true);
			ViewDragHelper leftDragger = (ViewDragHelper) leftDraggerField.get(drawerLayout);
			// find edgesize and set is accessible
			Field edgeSizeField = leftDragger.getClass().getDeclaredField("mEdgeSize");
			edgeSizeField.setAccessible(true);
			int edgeSize = edgeSizeField.getInt(leftDragger);
			// set new edgesize
			// Point displaySize = new Point();
			DisplayMetrics dm = new DisplayMetrics();
			activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
			edgeSizeField.setInt(leftDragger, Math.max(edgeSize, (int) (dm.widthPixels * displayWidthPercentage)));
		} catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 为指定View加载动画
	 *
	 * @param v     视图
	 * @param resId 动画资源
	 */
	public static void startAnimation(View v, int resId) {
		Animation animation = AnimationUtils.loadAnimation(getContext(), resId);
		v.startAnimation(animation);
	}

	/**
	 * 显示对称对话框
	 *
	 * @param content
	 */
	public static NormalDialog getNormalDialogStyleTwo(String content,Activity activity) {
		BaseAnimatorSet mBasIn = new BounceTopEnter();
		BaseAnimatorSet mBasOut = new SlideBottomExit();

		final NormalDialog dialog = new NormalDialog(activity);
		dialog.content(content)//
				.style(NormalDialog.STYLE_TWO)//
				.titleTextSize(23)//
				.showAnim(mBasIn)//
				.dismissAnim(mBasOut);
		return dialog;
	}

	public static NormalDialog getNormalDialogStyleOne(String content,Activity activity) {
		BaseAnimatorSet mBasIn = new BounceTopEnter();
		BaseAnimatorSet mBasOut = new SlideBottomExit();

		final NormalDialog dialog = new NormalDialog(activity);
		dialog.content(content)//
				.style(NormalDialog.STYLE_TWO)//
				.btnNum(1)
				.titleTextSize(23)//
				.showAnim(mBasIn)//
				.dismissAnim(mBasOut);
		return dialog;
	}
}


