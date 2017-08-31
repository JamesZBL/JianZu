package com.zbl.anju.api;


import com.zbl.anju.model.response.LoginSimpleResponse;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * @author James
 */

public interface AnjuApi {

	String IP_ADD_PORT = "47.93.187.44:8080";
	String BASE_URL = "http://" + IP_ADD_PORT + "/booksystem/";

//	String IP_ADD_PORT = "192.168.1.116:8080";                      //本地
//	String BASE_URL = "http://" + IP_ADD_PORT + "/library/";        //本地


	@POST("anju/password")
	Observable<LoginSimpleResponse> loginSimple(@Body RequestBody body);
}
