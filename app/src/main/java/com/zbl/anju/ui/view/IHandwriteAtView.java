package com.zbl.anju.ui.view;

import android.widget.EditText;

import com.zbl.anju.widget.LinePathView;

/**
 * 手写Activity视图接口
 * Created by James on 2017/7/9.
 */

public interface IHandwriteAtView {
	LinePathView getLinepathView();
	EditText getEdtName();
	EditText getEdtDescription();
}
