package com.zbl.anju.util;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;

/**
 * 视频工具
 * Created by James on 17-9-5.
 */

public class VideoUtils {

	/**
	 * 根据视频路径获取缩略图
	 * @param filePath
	 * @return
	 */
	public static Bitmap getVideoThumbnail(String filePath) {
		Bitmap bitmap = null;
		MediaMetadataRetriever retriever = new MediaMetadataRetriever();
		try {
			retriever.setDataSource(filePath);
			bitmap = retriever.getFrameAtTime();
		}
		catch(IllegalArgumentException e) {
			e.printStackTrace();
		}
		catch (RuntimeException e) {
			e.printStackTrace();
		}
		finally {
			try {
				retriever.release();
			}
			catch (RuntimeException e) {
				e.printStackTrace();
			}
		}
		return bitmap;
	}
}
