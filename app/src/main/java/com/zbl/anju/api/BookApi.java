package com.zbl.anju.api;


import com.zbl.anju.model.response.AddBookResponse;
import com.zbl.anju.model.response.DeleteBookByIdResponse;
import com.zbl.anju.model.response.GetBooksResponse;
import com.zbl.anju.model.response.UpdateBookResponse;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * ┏┓      ┏┓
 * 　　┏┛┻━━━━━━┛┗┓
 * 　　┃　　　　　 ┃
 * 　　┃　　 ━　　 ┃
 * 　　┃　┳┛   ┗┳ ┃
 * 　　┃　　　　　 ┃
 * 　　┃　　　┻　　┃
 * 　　┃　　　　　 ┃
 * 　　┗━┓　　　┏━┛
 * 　　　┃　　　┃  神兽保佑
 * 　　　┃　　　┃  代码无bug
 * 　　　┃　　　┗━━━┓
 * 　　　┃　　　　  ┣┓
 * 　　　┃　　　　 ┏┛
 * 　　　┗┓┓┏━━┳┓┏┛
 * 　　　 ┃┫┫  ┃┫┫
 * 　　　 ┗┻┛  ┗┻┛
 *
 * @author James
 * @apiNote server端api
 */

public interface BookApi {

	public static final String IP_ADD = "47.93.187.44:8080"; //阿里云主机 Tomcat6
	public static final String BASE_URL = "http://" + IP_ADD + "/booksystem/";


	//获取 books
	@GET("mobile/book_list")
	Observable<GetBooksResponse> getBooks();

	//删除 book
	@GET("mobile/book_delete/{bookId}")
	Observable<DeleteBookByIdResponse> deleteBook(@Path("bookId") long bookId);

	@POST("mobile/book_add")
	Observable<AddBookResponse> addBook(@Body RequestBody body);

	@POST("mobile/book_update")
	Observable<UpdateBookResponse> updateBook(@Body RequestBody body);
}
