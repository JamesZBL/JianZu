package com.zbl.anju.ui.view;

import android.widget.EditText;

import com.tencent.tencentmap.mapsdk.map.MapView;
import com.tencent.tencentmap.mapsdk.map.TencentMap;
import com.zbl.anju.widget.LinePathView;

/**
 * 手写Activity视图接口
 * Created by James on 2017/7/9.
 */

public interface IMainAtView {
	LinePathView getLinepathView();
	EditText getEdtName();
	EditText getEdtDescription();

	MapView getTenMapView();
	TencentMap getTenMap();
}
