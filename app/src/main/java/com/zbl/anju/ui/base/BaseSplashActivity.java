package com.zbl.anju.ui.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * Created by James on 2017/6/6.
 */

public abstract class BaseSplashActivity extends AppCompatActivity {
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
	}

	/**
	 * 初始化
	 * 启动界面已经在style中指定windowBackground
	 */
	public abstract void init();

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

	public void jumpToActivity(Class activity) {
		Intent intent = new Intent(this, activity);
		startActivity(intent);
	}
}
