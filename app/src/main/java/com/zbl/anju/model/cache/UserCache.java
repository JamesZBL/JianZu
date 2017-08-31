package com.zbl.anju.model.cache;


import com.zbl.anju.app.AppConst;
import com.zbl.anju.util.SPUtils;
import com.zbl.anju.util.UIUtils;

/**
 * @author James
 * @描述 用户缓存
 */
public class UserCache {

	public static String getId() {
		return SPUtils.getInstance(UIUtils.getContext()).getString(AppConst.User.ID, "");
	}

	public static String getPhone() {
		return SPUtils.getInstance(UIUtils.getContext()).getString(AppConst.User.PHONE, "");
	}

	public static String getPwd() {
		return SPUtils.getInstance(UIUtils.getContext()).getString(AppConst.User.PWD, "");
	}

	/**
	 * 对用户登录信息进行缓存
	 */
	public static void save(String id, String account, String pwd) {
		SPUtils.getInstance(UIUtils.getContext()).putString(AppConst.User.ID, id);
		SPUtils.getInstance(UIUtils.getContext()).putString(AppConst.User.PHONE, account);
		SPUtils.getInstance(UIUtils.getContext()).putString(AppConst.User.PWD, pwd);
	}

	/**
	 * 清除用户登录数据
	 */
	public static void clear() {
		SPUtils.getInstance(UIUtils.getContext()).remove(AppConst.User.ID);
		SPUtils.getInstance(UIUtils.getContext()).remove(AppConst.User.PHONE);
		SPUtils.getInstance(UIUtils.getContext()).remove(AppConst.User.PWD);

	}

}
