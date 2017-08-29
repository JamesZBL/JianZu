package com.zbl.anju.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.umeng.analytics.MobclickAgent;
import com.zbl.anju.util.LogUtils;

import butterknife.ButterKnife;

/**
 * 支持延迟加载的Fragment
 *
 * @param <V>
 * @param <T>
 */
public abstract class BaseFragment<V, T extends BasePresenter<V>> extends Fragment {

	protected T mPresenter;

	//延迟加载
	private boolean isPrepared;
	private boolean isFirstResume = true;
	private boolean isFirstVisible = true;
	private boolean isFirstInvisible = true;


	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//判断是否使用MVP模式
		mPresenter = createPresenter();
		if (mPresenter != null) {
			mPresenter.attachView((V) this);//因为之后所有的子类都要实现对应的View接口
		}
	}


	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		//子类不再需要设置布局ID，也不再需要使用ButterKnife.bind()
		View rootView = inflater.inflate(provideContentViewId(), container, false);
		ButterKnife.bind(this, rootView);
		initView(rootView);
		return rootView;
	}


	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initPrepare();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (mPresenter != null) {
			mPresenter.detachView();
		}
	}

	public void init() {
		LogUtils.d("fragment_init");
	}

	public void initView(View rootView) {
		LogUtils.d("fragment_initView");
	}

	public void initData() {
		LogUtils.d("fragment_initData");
	}

	public void initListener() {
		LogUtils.d("fragment_initListener");
	}


	//用于创建Presenter和判断是否使用MVP模式(由子类实现)
	protected abstract T createPresenter();

	//得到当前界面的布局文件id(由子类实现)
	protected abstract int provideContentViewId();


	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(getContext());       //统计时长
		if (isFirstResume) {
			isFirstResume = false;
			return;
		}
		if (getUserVisibleHint()) {
			onUserVisible();
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(getContext());
		if (getUserVisibleHint()) {
			onUserInvisible();
		}
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if (isVisibleToUser) {
			if (isFirstVisible) {
				isFirstVisible = false;
				initPrepare();
			} else {
				onUserVisible();
			}

		} else {
			if (isFirstInvisible) {
				isFirstInvisible = false;
				onFirstUserInvisible();
			} else {
				onUserInvisible();
			}
		}
	}

	public synchronized void initPrepare() {
		if (isPrepared) {
			onFirstUserVisible();
		} else {
			isPrepared = true;
		}
	}


	/**
	 * 第一次fragment可见（进行初始化工作）
	 */


	public void onFirstUserVisible() {
		init();
		initData();
		initListener();
	}

	/**
	 * fragment可见（切换回来或者onResume）
	 */


	public void onUserVisible() {

	}

	/**
	 * 第一次fragment不可见（不建议在此处理事件）
	 */


	public void onFirstUserInvisible() {


	}

	/**
	 * fragment不可见（切换掉或者onPause）
	 */


	public void onUserInvisible() {


	}
}
