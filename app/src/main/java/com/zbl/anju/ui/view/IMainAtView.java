package com.zbl.anju.ui.view;

import android.widget.EditText;

import com.gigamole.navigationtabstrip.NavigationTabStrip;
import com.tencent.tencentmap.mapsdk.map.MapView;
import com.tencent.tencentmap.mapsdk.map.TencentMap;
import com.youth.banner.Banner;
import com.zbl.anju.widget.LinePathView;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

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

	NavigationTabStrip getNavigationTabStrip();
	Banner getBannerAd();
	JCVideoPlayerStandard getJcPlayer();
}
