package com.zbl.anju.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.zbl.anju.R;
import com.zbl.anju.app.AppConst;
import com.zbl.anju.ui.base.BaseSplashActivity;

import butterknife.Bind;

/**
 * @author James
 * @描述 启动页
 */
public class SplashActivity extends BaseSplashActivity {

	@Bind(R.id.rlButton)
	RelativeLayout mRlButton;
	@Bind(R.id.btnLogin)
	Button mBtnLogin;
	@Bind(R.id.btnRegister)
	Button mBtnRegister;

	private boolean istokenValid = false;


	@Override
	public synchronized void init() {

	}

	private void startMainAct(){
		new Handler().postDelayed(() -> {
			startActivity(new Intent(this, MainActivity.class));
			finish();
		}, AppConst.SPLASH_DELAY);
	}



	@Override
	public void initView() {
		super.initView();
//		StatusBarUtil.setColor(this, UIUtils.getColor(R.color.black));
		AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
		alphaAnimation.setDuration(1000);
		mRlButton.setVisibility(View.VISIBLE);
		mRlButton.startAnimation(alphaAnimation);
	}

	@Override
	public void initData() {

	}

	@Override
	protected int provideContentViewId() {
		return R.layout.activity_splash;
	}


	@Override
	public void initListener() {
		if (istokenValid) {
			getSupportFragmentManager();
			return;
		}
	}
}
