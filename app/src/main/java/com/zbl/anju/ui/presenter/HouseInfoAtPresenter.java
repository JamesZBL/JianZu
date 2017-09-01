package com.zbl.anju.ui.presenter;

import android.os.Bundle;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.zbl.anju.db.model.TagHolder;
import com.zbl.anju.ui.base.BaseActivity;
import com.zbl.anju.ui.base.BasePresenter;
import com.zbl.anju.ui.view.IHouseInfoAtView;
import com.zbl.anju.util.UIUtils;

import java.util.ArrayList;
import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCUserAction;
import fm.jiecao.jcvideoplayer_lib.JCUserActionStandard;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

public class HouseInfoAtPresenter extends BasePresenter<IHouseInfoAtView> {

	public HouseInfoAtPresenter(BaseActivity context) {
		super(context);
	}

	/**
	 * 加载数据
	 */
	public void initData() {
		//设置视频播放源
		getView().getJcVideoPlayer().setUp("http://pgccdn.v.baidu.com/446673505_4133162716_20170806132327.mp4?authorization=bce-auth-v1%2Fc308a72e7b874edd9115e4614e1d62f6%2F2017-08-06T05%3A23%3A33Z%2F-1%2F%2Fe4fa01ec4352ddb38314a70c809264a6fc0e6894dd0638710acbf712a1c0a6f3&responseCacheControl=max-age%3D8640000&responseExpires=Tue%2C+14+Nov+2017+13%3A23%3A33+GMT&xcode=c64b1919385aa116dc50d92e5054a7741d93c9f3c454ab2f&time=1504139813&_=1504054789526"
				, JCVideoPlayerStandard.SCREEN_LAYOUT_LIST);
		//设置视频缩略图
		Glide.with(mContext)
				.load("http://pic3.58cdn.com.cn/anjuke_58/46bbe80f123d3425211a2b1e25397895")
				.into(getView().getJcVideoPlayer().thumbImageView);
		JCVideoPlayer.setJcUserAction(new MyUserActionStandard());


		//设置轮播图
		List<String> imageList = new ArrayList<>();
		imageList.add("http://pic.58pic.com/58pic/13/80/06/68558PIC7Fn_1024.jpg");
		imageList.add("http://pic.58pic.com/58pic/14/32/00/83A58PICDH5_1024.jpg");
		imageList.add("http://pic.58pic.com/58pic/13/23/73/67y58PICzAc_1024.jpg");
		imageList.add("http://pic.58pic.com/58pic/16/41/62/95I58PICRFd_1024.jpg");

		imageList.add("http://pic56.nipic.com/file/20141213/15265552_175423699768_2.jpg");
		imageList.add("http://pic.58pic.com/58pic/13/22/62/13V58PICAk2_1024.jpg");
		imageList.add("http://pic.58pic.com/58pic/17/07/88/98y58PIChDF_1024.jpg");
		imageList.add("http://pic.58pic.com/58pic/13/60/04/49K58PICjWN_1024.jpg");

		//设置图片来源
		getView().getBanner().setImages(imageList);
		//开始加载图片
		getView().getBanner().start();
	}

	public void initBundle(Bundle extras) {
		try {
			TagHolder holder= (TagHolder) extras.getSerializable("tagHolder");
		} catch (Exception e) {
			e.printStackTrace();
		}
		UIUtils.showToast("initBundle");
	}


	static class MyUserActionStandard implements JCUserActionStandard {

		@Override
		public void onEvent(int type, String url, int screen, Object... objects) {
			switch (type) {
				case JCUserAction.ON_CLICK_START_ICON:
					Log.i("USER_EVENT", "ON_CLICK_START_ICON" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
					break;
				case JCUserAction.ON_CLICK_START_ERROR:
					Log.i("USER_EVENT", "ON_CLICK_START_ERROR" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
					break;
				case JCUserAction.ON_CLICK_START_AUTO_COMPLETE:
					Log.i("USER_EVENT", "ON_CLICK_START_AUTO_COMPLETE" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
					break;
				case JCUserAction.ON_CLICK_PAUSE:
					Log.i("USER_EVENT", "ON_CLICK_PAUSE" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
					break;
				case JCUserAction.ON_CLICK_RESUME:
					Log.i("USER_EVENT", "ON_CLICK_RESUME" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
					break;
				case JCUserAction.ON_SEEK_POSITION:
					Log.i("USER_EVENT", "ON_SEEK_POSITION" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
					break;
				case JCUserAction.ON_AUTO_COMPLETE:
					Log.i("USER_EVENT", "ON_AUTO_COMPLETE" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
					break;
				case JCUserAction.ON_ENTER_FULLSCREEN:
					Log.i("USER_EVENT", "ON_ENTER_FULLSCREEN" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
					break;
				case JCUserAction.ON_QUIT_FULLSCREEN:
					Log.i("USER_EVENT", "ON_QUIT_FULLSCREEN" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
					break;
				case JCUserAction.ON_ENTER_TINYSCREEN:
					Log.i("USER_EVENT", "ON_ENTER_TINYSCREEN" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
					break;
				case JCUserAction.ON_QUIT_TINYSCREEN:
					Log.i("USER_EVENT", "ON_QUIT_TINYSCREEN" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
					break;
				case JCUserAction.ON_TOUCH_SCREEN_SEEK_VOLUME:
					Log.i("USER_EVENT", "ON_TOUCH_SCREEN_SEEK_VOLUME" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
					break;
				case JCUserAction.ON_TOUCH_SCREEN_SEEK_POSITION:
					Log.i("USER_EVENT", "ON_TOUCH_SCREEN_SEEK_POSITION" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
					break;

				case JCUserActionStandard.ON_CLICK_START_THUMB:
					Log.i("USER_EVENT", "ON_CLICK_START_THUMB" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
					break;
				case JCUserActionStandard.ON_CLICK_BLANK:
					Log.i("USER_EVENT", "ON_CLICK_BLANK" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
					break;
				default:
					Log.i("USER_EVENT", "unknow");
					break;
			}
		}
	}
}
