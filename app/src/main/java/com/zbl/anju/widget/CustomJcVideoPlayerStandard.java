package com.zbl.anju.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * 自定义视频播放器
 * Created by James on 17-8-27.
 */

public class CustomJcVideoPlayerStandard extends JCVideoPlayerStandard {
	public CustomJcVideoPlayerStandard(Context context) {
		super(context);
	}

	public CustomJcVideoPlayerStandard(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public void init(Context context) {
		super.init(context);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		int i = v.getId();
		if (i == fm.jiecao.jcvideoplayer_lib.R.id.fullscreen) {
			if (currentScreen == SCREEN_WINDOW_FULLSCREEN) {
				//click quit fullscreen
			} else {
				//click goto fullscreen
			}
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		return super.onTouch(v, event);
	}

	@Override
	public void startVideo() {
		super.startVideo();
	}

	/**
	 * onPrepared
	 */
	@Override
	public void onVideoRendingStart() {
		super.onVideoRendingStart();
	}

	@Override
	public void onStateNormal() {
		super.onStateNormal();
	}

	@Override
	public void onStatePreparing() {
		super.onStatePreparing();
	}

	@Override
	public void onStatePlaying() {
		super.onStatePlaying();
	}

	@Override
	public void onStatePause() {
		super.onStatePause();
	}

	@Override
	public void onStatePlaybackBufferingStart() {
		super.onStatePlaybackBufferingStart();
	}

	@Override
	public void onStateError() {
		super.onStateError();
	}

	@Override
	public void onStateAutoComplete() {
		super.onStateAutoComplete();
	}

	@Override
	public void onInfo(int what, int extra) {
		super.onInfo(what, extra);
	}

	@Override
	public void onError(int what, int extra) {
		super.onError(what, extra);
	}

	@Override
	public void startWindowFullscreen() {
		super.startWindowFullscreen();
	}

	@Override
	public void startWindowTiny() {
		super.startWindowTiny();
	}
}
