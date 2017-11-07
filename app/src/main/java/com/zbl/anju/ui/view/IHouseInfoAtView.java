package com.zbl.anju.ui.view;

import android.widget.ImageView;

import com.youth.banner.Banner;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerSimple;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * 房屋信息Activity视图接口
 * Created by James on 2017/7/9.
 */

public interface IHouseInfoAtView {
//	JCVideoPlayerStandard getJcVideoPlayer();
	JCVideoPlayerStandard getJcVideoPlayer();
	Banner getBanner();
	ImageView getIvIndoorPhotos();
}
