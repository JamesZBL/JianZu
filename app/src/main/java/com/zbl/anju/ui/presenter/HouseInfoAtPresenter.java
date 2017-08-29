package com.zbl.anju.ui.presenter;

import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.zbl.anju.ui.base.BaseActivity;
import com.zbl.anju.ui.base.BasePresenter;
import com.zbl.anju.ui.view.IHouseInfoAtView;

import java.io.IOException;
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

	public void initData(){
		getView().getJcVideoPlayer().setUp("http://2449.vod.myqcloud.com/2449_22ca37a6ea9011e5acaaf51d105342e3.f20.mp4"
				, JCVideoPlayerStandard.SCREEN_LAYOUT_LIST);   //设置视频播放源
		Glide.with(mContext)
				.load("http://img4.jiecaojingxuan.com/2016/11/23/1bb2ebbe-140d-4e2e-abd2-9e7e564f71ac.png@!640_360")
				.into(getView().getJcVideoPlayer().thumbImageView);
		JCVideoPlayer.setJcUserAction(new MyUserActionStandard());



		List<String> imageList=new ArrayList<>();
		imageList.add("http://pic.58pic.com/58pic/13/80/06/68558PIC7Fn_1024.jpg");
		imageList.add("http://pic.58pic.com/58pic/14/32/00/83A58PICDH5_1024.jpg");
		imageList.add("http://pic.58pic.com/58pic/13/23/73/67y58PICzAc_1024.jpg");
		imageList.add("http://pic.58pic.com/58pic/16/41/62/95I58PICRFd_1024.jpg");

		imageList.add("http://pic56.nipic.com/file/20141213/15265552_175423699768_2.jpg");
		imageList.add("http://pic.58pic.com/58pic/13/22/62/13V58PICAk2_1024.jpg");
		imageList.add("http://pic.58pic.com/58pic/17/07/88/98y58PIChDF_1024.jpg");
		imageList.add("http://pic.58pic.com/58pic/13/60/04/49K58PICjWN_1024.jpg");

		getView().getBanner().setImages(imageList); //设置图片来源
		getView().getBanner().start();   //开始加载图片
	}
















	class MyUserActionStandard implements JCUserActionStandard {

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
