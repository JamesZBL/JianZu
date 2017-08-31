package com.zbl.anju.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import butterknife.ButterKnife;

/**
 * 启动画面基类
 * Created by James on 2017/6/6.
 */

public abstract class BaseSplashActivity extends BaseActivity {


	/**
	 * 视图初始化
	 */
	public void initView() {
		setContentView(provideContentViewId());
		ButterKnife.bind(this);
	}

	/**
	 * 初始化监听器
	 */
	public abstract void initListener();

	/**
	 * 数据初始化
	 */
	public abstract void initData();

	/**
	 * 获得视图资源id
	 *
	 * @return 视图资源id
	 */
	protected abstract int provideContentViewId();

}
