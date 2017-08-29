package com.zbl.anju.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextWatcher;

/**
 * 自定义带删除按钮的输入框
 *
 * @author James
 */

public class ClearEditText extends AppCompatEditText implements TextWatcher, View.OnFocusChangeListener {
	/**
	 * 左右两侧图片资源
	 */
	private Drawable left, right;
	/**
	 * 是否获取焦点，默认没有焦点
	 */
	private boolean hasFocus = false;
	/**
	 * 手指抬起时的X坐标
	 */
	private int xUp = 0;

	public ClearEditText(Context context) {
		this(context, null);
	}

	public ClearEditText(Context context, AttributeSet attrs) {
		this(context, attrs, android.R.attr.editTextStyle);
	}

	public ClearEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initWedgits();
	}

	/**
	 * 初始化各组件
	 */
	private void initWedgits() {
		try {
			// 获取drawableLeft图片，如果在布局文件中没有定义drawableLeft属性，则此值为空
			left = getCompoundDrawables()[0];
			// 获取drawableRight图片，如果在布局文件中没有定义drawableRight属性，则此值为空
			right = getCompoundDrawables()[2];
			initDatas();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 初始化数据
	 */
	private void initDatas() {
		try {
			// 第一次显示，隐藏删除图标
			setCompoundDrawablesWithIntrinsicBounds(left, null, null, null);
			addListeners();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 添加事件监听
	 */
	private void addListeners() {
		try {
			setOnFocusChangeListener(this);
			addTextChangedListener(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
	                              int after) {
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int after) {
		if (hasFocus) {
			if (TextUtils.isEmpty(s)) {
				// 如果为空，则不显示删除图标
				setCompoundDrawablesWithIntrinsicBounds(left, null, null, null);
			} else {
				// 如果非空，则要显示删除图标
				if (null == right) {
					right = getCompoundDrawables()[2];
				}
				setCompoundDrawablesWithIntrinsicBounds(left, null, right, null);
			}
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		try {
			switch (event.getAction()) {
				case MotionEvent.ACTION_UP:
					// 获取点击时手指抬起的X坐标
					xUp = (int) event.getX();
					// 当点击的坐标到当前输入框右侧的距离小于等于getCompoundPaddingRight()的距离时，则认为是点击了删除图标
					// getCompoundPaddingRight()的说明：Returns the right padding of the view, plus space for the right Drawable if any.
					if ((getWidth() - xUp) <= getCompoundPaddingRight()) {
						if (!TextUtils.isEmpty(getText().toString())) {
							setText("");
						}
					}
					break;
				default:
					break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.onTouchEvent(event);
	}

	@Override
	public void afterTextChanged(Editable s) {
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		try {
			this.hasFocus = hasFocus;
			if (!hasFocus) {
				setCompoundDrawablesWithIntrinsicBounds(left, null, null, null);//失去焦点时不显示叉号
			} else if (!TextUtils.isEmpty(this.getText().toString())) {
				setCompoundDrawablesWithIntrinsicBounds(left, null, right, null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
