package com.zbl.anju.util;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.view.animation.LinearInterpolator;

/**
 * Created by James on 2017/6/14.
 */

public class AnimUtil {
	private static AnimUtil mInstance;

	private ValueAnimator valueAnimator;
	private UpdateListener updateListener;
	private EndListener endListener;
	private long duration;
	private float start;
	private float end;
	private LinearInterpolator interpolator;

	private AnimUtil() {
		duration = 1000; //默认动画时常1s
		start = 0.0f;
		end = 1.0f;
		interpolator = new LinearInterpolator();// 匀速的插值器
	}

	public static AnimUtil getInstance() {
		if (mInstance == null) {
			synchronized (AnimUtil.class) {
				if (mInstance == null) {
					return new AnimUtil();
				}
			}
		}
		return mInstance;
	}


	public void setDuration(int timeLength) {
		duration = timeLength;
	}

	public void setValueAnimator(float start, float end, long duration) {

		this.start = start;
		this.end = end;
		this.duration = duration;

	}

	public void setInterpolator(LinearInterpolator interpolator) {
		this.interpolator = interpolator;
	}

	public void startAnimator() {
		if (valueAnimator != null) {
			valueAnimator = null;
		}
		valueAnimator = ValueAnimator.ofFloat(start, end);
		valueAnimator.setDuration(duration);
		valueAnimator.setInterpolator(interpolator);
		valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator valueAnimator) {

				if (updateListener == null) {
					return;
				}

				float cur = (float) valueAnimator.getAnimatedValue();
				updateListener.progress(cur);
			}
		});
		valueAnimator.addListener(new Animator.AnimatorListener() {
			@Override
			public void onAnimationStart(Animator animator) {
			}

			@Override
			public void onAnimationEnd(Animator animator) {
				if (endListener == null) {
					return;
				}
				endListener.endUpdate(animator);
			}

			@Override
			public void onAnimationCancel(Animator animator) {
			}

			@Override
			public void onAnimationRepeat(Animator animator) {
			}
		});
		valueAnimator.start();
	}

	public void addUpdateListener(UpdateListener updateListener) {

		this.updateListener = updateListener;
	}

	public void addEndListner(EndListener endListener) {
		this.endListener = endListener;
	}

	public interface EndListener {
		void endUpdate(Animator animator);
	}

	public interface UpdateListener {

		void progress(float progress);
	}

}
