package com.zbl.anju.api;


import com.zbl.anju.model.response.UploadSigResponse;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * @author James
 */

public interface SignatureApi {

		public static final String IP_ADD_PORT = "47.93.187.44:8080";
//	public static final String IP_ADD_PORT = "192.168.1.116:8080"; //本地
		public static final String BASE_URL = "http://" + IP_ADD_PORT + "/booksystem/";
//	public static final String BASE_URL = "http://" + IP_ADD_PORT + "/library/"; //本地


	/**
	 * "img" 为图片参数名
	 *
	 * @param maps 参数
	 * @param img  图片
	 * @return
	 */
	@Multipart
	@POST("signature/upload")
	Observable<UploadSigResponse> upload(@QueryMap Map<String, Object> maps, @Part("img\"; filename=\"img.jpg\"") RequestBody img);
}
