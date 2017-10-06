package com.zbl.anju.sdk;

import com.tencent.tauth.Tencent;
import com.zbl.anju.app.AppConst;
import com.zbl.anju.app.MyApp;

/**
 * Tencent
 * Created by James on 17-9-11.
 */

public class MyTecncent {

	private static Tencent mInstance;

	private MyTecncent() {
	}

	public static Tencent getInstance() {
		if (mInstance == null) {
			synchronized (RongCloudSDK.class) {
				if (mInstance == null) {
					mInstance = Tencent.createInstance(AppConst.MY_TENCENT_APP_ID, MyApp.getContext());
				}
			}
		}
		return mInstance;
	}
}
