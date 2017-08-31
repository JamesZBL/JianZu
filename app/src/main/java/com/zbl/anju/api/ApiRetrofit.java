package com.zbl.anju.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zbl.anju.api.base.BaseApiRetrofit;
import com.zbl.anju.model.request.AddBookRequest;
import com.zbl.anju.model.request.AddGroupMemberRequest;
import com.zbl.anju.model.request.AddToBlackListRequest;
import com.zbl.anju.model.request.AgreeFriendsRequest;
import com.zbl.anju.model.request.ChangePasswordRequest;
import com.zbl.anju.model.request.CheckPhoneRequest;
import com.zbl.anju.model.request.CreateGroupRequest;
import com.zbl.anju.model.request.DeleteFriendRequest;
import com.zbl.anju.model.request.DeleteGroupMemberRequest;
import com.zbl.anju.model.request.DismissGroupRequest;
import com.zbl.anju.model.request.FriendInvitationRequest;
import com.zbl.anju.model.request.JoinGroupRequest;
import com.zbl.anju.model.request.LoginRequest;
import com.zbl.anju.model.request.QuitGroupRequest;
import com.zbl.anju.model.request.RegisterRequest;
import com.zbl.anju.model.request.RemoveFromBlacklistRequest;
import com.zbl.anju.model.request.RestPasswordRequest;
import com.zbl.anju.model.request.SendCodeRequest;
import com.zbl.anju.model.request.SetFriendDisplayNameRequest;
import com.zbl.anju.model.request.SetGroupDisplayNameRequest;
import com.zbl.anju.model.request.SetGroupNameRequest;
import com.zbl.anju.model.request.SetGroupPortraitRequest;
import com.zbl.anju.model.request.SetNameRequest;
import com.zbl.anju.model.request.SetPortraitRequest;
import com.zbl.anju.model.request.UpdateBookRequest;
import com.zbl.anju.model.request.VerifyCodeRequest;
import com.zbl.anju.model.response.AddBookResponse;
import com.zbl.anju.model.response.AddGroupMemberResponse;
import com.zbl.anju.model.response.AddToBlackListResponse;
import com.zbl.anju.model.response.AgreeFriendsResponse;
import com.zbl.anju.model.response.ChangePasswordResponse;
import com.zbl.anju.model.response.CheckPhoneResponse;
import com.zbl.anju.model.response.CreateGroupResponse;
import com.zbl.anju.model.response.DefaultConversationResponse;
import com.zbl.anju.model.response.DeleteBookByIdResponse;
import com.zbl.anju.model.response.DeleteFriendResponse;
import com.zbl.anju.model.response.DeleteGroupMemberResponse;
import com.zbl.anju.model.response.FriendInvitationResponse;
import com.zbl.anju.model.response.GetBlackListResponse;
import com.zbl.anju.model.response.GetBooksResponse;
import com.zbl.anju.model.response.GetFriendInfoByIDResponse;
import com.zbl.anju.model.response.GetGroupInfoResponse;
import com.zbl.anju.model.response.GetGroupMemberResponse;
import com.zbl.anju.model.response.GetGroupResponse;
import com.zbl.anju.model.response.GetTokenResponse;
import com.zbl.anju.model.response.GetUserInfoByIdResponse;
import com.zbl.anju.model.response.GetUserInfoByPhoneResponse;
import com.zbl.anju.model.response.GetUserInfosResponse;
import com.zbl.anju.model.response.JoinGroupResponse;
import com.zbl.anju.model.response.LoginResponse;
import com.zbl.anju.model.response.LoginSimpleResponse;
import com.zbl.anju.model.response.QiNiuTokenResponse;
import com.zbl.anju.model.response.QuitGroupResponse;
import com.zbl.anju.model.response.RegisterResponse;
import com.zbl.anju.model.response.RemoveFromBlackListResponse;
import com.zbl.anju.model.response.RestPasswordResponse;
import com.zbl.anju.model.response.SendCodeResponse;
import com.zbl.anju.model.response.SetFriendDisplayNameResponse;
import com.zbl.anju.model.response.SetGroupDisplayNameResponse;
import com.zbl.anju.model.response.SetGroupNameResponse;
import com.zbl.anju.model.response.SetGroupPortraitResponse;
import com.zbl.anju.model.response.SetNameResponse;
import com.zbl.anju.model.response.SetPortraitResponse;
import com.zbl.anju.model.response.SyncTotalDataResponse;
import com.zbl.anju.model.response.UpdateBookResponse;
import com.zbl.anju.model.response.UploadSigResponse;
import com.zbl.anju.model.response.UserRelationshipResponse;
import com.zbl.anju.model.response.VerifyCodeResponse;
import com.zbl.anju.model.response.VersionResponse;

import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * @author James
 * @描述 使用Retrofit对网络请求进行配置
 */
public class ApiRetrofit extends BaseApiRetrofit {

	public MyApi mApi;
	public BookApi bookApi;
	public SignatureApi signatureApi;
	public AnjuApi anjuApi;
	public static ApiRetrofit mInstance;

	private ApiRetrofit() {
		super();
		Gson gson = new GsonBuilder()
				.setLenient()
				.create();

		/* anju */
		anjuApi = new Retrofit.Builder()
				.baseUrl(AnjuApi.BASE_URL)
				.client(getClient())
				.addConverterFactory(GsonConverterFactory.create(gson))
				.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
				.build()
				.create(AnjuApi.class);

		//在构造方法中完成对Retrofit接口的初始化
		mApi = new Retrofit.Builder()
				.baseUrl(MyApi.BASE_URL)
				.client(getClient())
				.addConverterFactory(GsonConverterFactory.create(gson))
				.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
				.build()
				.create(MyApi.class);

		bookApi = new Retrofit.Builder()
				.baseUrl(BookApi.BASE_URL)
				.client(getClient())
				.addConverterFactory(GsonConverterFactory.create(gson))
				.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
				.build()
				.create(BookApi.class);
		signatureApi = new Retrofit.Builder()
				.baseUrl(SignatureApi.BASE_URL)
				.client(getClient())
				.addConverterFactory(GsonConverterFactory.create(gson))
				.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
				.build()
				.create(SignatureApi.class);
	}

	public static ApiRetrofit getInstance() {
		if (mInstance == null) {
			synchronized (ApiRetrofit.class) {
				if (mInstance == null) {
					mInstance = new ApiRetrofit();
				}
			}
		}
		return mInstance;
	}

	private RequestBody getRequestBody(Object obj) {
		String route = new Gson().toJson(obj);
		RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), route);
		return body;
	}

	private RequestBody getMultipartRequestBody(byte[] buffer) {
		RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"), buffer);
		return body;
	}

	//简易登录
	public Observable<LoginSimpleResponse> loginSimple(String region, String phone, String password) {
		return anjuApi.loginSimple(getRequestBody(new LoginRequest(region, phone, password)));
	}















	/**/
	/**/
	/**/
	/**/
	/**/
	/**/
	/**/
	/**/
	/**/
	/**/
	/**/
	/**/
	/**/
	/**/
	/**/
	/**/
	/**/
	/**/
	/**/
	//登录
	public Observable<LoginResponse> login(String region, String phone, String password) {
		return mApi.login(getRequestBody(new LoginRequest(region, phone, password)));
	}

	//注册
	public Observable<CheckPhoneResponse> checkPhoneAvailable(String region, String phone) {
		return mApi.checkPhoneAvailable(getRequestBody(new CheckPhoneRequest(phone, region)));
	}

	public Observable<SendCodeResponse> sendCode(String region, String phone) {
		return mApi.sendCode(getRequestBody(new SendCodeRequest(region, phone)));
	}

	public Observable<VerifyCodeResponse> verifyCode(String region, String phone, String code) {
		return mApi.verifyCode(getRequestBody(new VerifyCodeRequest(region, phone, code)));
	}

	public Observable<RegisterResponse> register(String nickname, String password, String verification_token) {
		return mApi.register(getRequestBody(new RegisterRequest(nickname, password, verification_token)));
	}

	public Observable<GetTokenResponse> getToken() {
		return mApi.getToken();
	}

	//个人信息
	public Observable<SetNameResponse> setName(String nickName) {
		return mApi.setName(getRequestBody(new SetNameRequest(nickName)));
	}

	public Observable<SetPortraitResponse> setPortrait(String portraitUri) {
		return mApi.setPortrait(getRequestBody(new SetPortraitRequest(portraitUri)));
	}

	public Observable<ChangePasswordResponse> changePassword(String oldPassword, String newPassword) {
		return mApi.changePassword(getRequestBody(new ChangePasswordRequest(oldPassword, newPassword)));
	}

	/**
	 * @param password           密码，6 到 20 个字节，不能包含空格
	 * @param verification_token 调用 /user/verify_code 成功后返回的 activation_token
	 */
	public Observable<RestPasswordResponse> restPassword(String password, String verification_token) {
		return mApi.restPassword(getRequestBody(new RestPasswordRequest(password, verification_token)));
	}

	//查询
	public Observable<GetUserInfoByIdResponse> getUserInfoById(String userid) {
		return mApi.getUserInfoById(userid);
	}

	public Observable<GetUserInfoByPhoneResponse> getUserInfoFromPhone(String region, String phone) {
		return mApi.getUserInfoFromPhone(region, phone);
	}

	//好友
	public Observable<FriendInvitationResponse> sendFriendInvitation(String userid, String addFriendMessage) {
		return mApi.sendFriendInvitation(getRequestBody(new FriendInvitationRequest(userid, addFriendMessage)));
	}

	public Observable<UserRelationshipResponse> getAllUserRelationship() {
		return mApi.getAllUserRelationship();
	}

	public Observable<GetFriendInfoByIDResponse> getFriendInfoByID(String userid) {
		return mApi.getFriendInfoByID(userid);
	}

	public Observable<AgreeFriendsResponse> agreeFriends(String friendId) {
		return mApi.agreeFriends(getRequestBody(new AgreeFriendsRequest(friendId)));
	}

	public Observable<DeleteFriendResponse> deleteFriend(String friendId) {
		return mApi.deleteFriend(getRequestBody(new DeleteFriendRequest(friendId)));
	}

	public Observable<SetFriendDisplayNameResponse> setFriendDisplayName(String friendId, String displayName) {
		return mApi.setFriendDisplayName(getRequestBody(new SetFriendDisplayNameRequest(friendId, displayName)));
	}

	public Observable<GetBlackListResponse> getBlackList() {
		return mApi.getBlackList();
	}

	public Observable<AddToBlackListResponse> addToBlackList(String friendId) {
		return mApi.addToBlackList(getRequestBody(new AddToBlackListRequest(friendId)));
	}

	public Observable<RemoveFromBlackListResponse> removeFromBlackList(String friendId) {
		return mApi.removeFromBlackList(getRequestBody(new RemoveFromBlacklistRequest(friendId)));
	}


	//群组
	public Observable<CreateGroupResponse> createGroup(String name, List<String> memberIds) {
		return mApi.createGroup(getRequestBody(new CreateGroupRequest(name, memberIds)));
	}

	public Observable<SetGroupPortraitResponse> setGroupPortrait(String groupId, String portraitUri) {
		return mApi.setGroupPortrait(getRequestBody(new SetGroupPortraitRequest(groupId, portraitUri)));
	}

	public Observable<GetGroupResponse> getGroups() {
		return mApi.getGroups();
	}

	public Observable<GetGroupInfoResponse> getGroupInfo(String groupId) {
		return mApi.getGroupInfo(groupId);
	}

	public Observable<GetGroupMemberResponse> getGroupMember(String groupId) {
		return mApi.getGroupMember(groupId);
	}

	public Observable<AddGroupMemberResponse> addGroupMember(String groupId, List<String> memberIds) {
		return mApi.addGroupMember(getRequestBody(new AddGroupMemberRequest(groupId, memberIds)));
	}

	public Observable<DeleteGroupMemberResponse> deleGroupMember(String groupId, List<String> memberIds) {
		return mApi.deleGroupMember(getRequestBody(new DeleteGroupMemberRequest(groupId, memberIds)));
	}

	public Observable<SetGroupNameResponse> setGroupName(String groupId, String name) {
		return mApi.setGroupName(getRequestBody(new SetGroupNameRequest(groupId, name)));
	}

	public Observable<QuitGroupResponse> quitGroup(String groupId) {
		return mApi.quitGroup(getRequestBody(new QuitGroupRequest(groupId)));
	}

	public Observable<QuitGroupResponse> dissmissGroup(String groupId) {
		return mApi.dissmissGroup(getRequestBody(new DismissGroupRequest(groupId)));
	}
//    public Observable<DismissGroupResponse> dissmissGroup(String groupId) {
//        return mApi.dissmissGroup(getRequestBody(new DismissGroupRequest(groupId)));
//    }

	public Observable<SetGroupDisplayNameResponse> setGroupDisplayName(String groupId, String displayName) {
		return mApi.setGroupDisplayName(getRequestBody(new SetGroupDisplayNameRequest(groupId, displayName)));
	}

	public Observable<JoinGroupResponse> JoinGroup(String groupId) {
		return mApi.JoinGroup(getRequestBody(new JoinGroupRequest(groupId)));
	}

	public Observable<DefaultConversationResponse> getDefaultConversation() {
		return mApi.getDefaultConversation();
	}

	public Observable<GetUserInfosResponse> getUserInfos(List<String> ids) {
		StringBuilder sb = new StringBuilder();
		for (String s : ids) {
			sb.append("id=");
			sb.append(s);
			sb.append("&");
		}
		String stringRequest = sb.substring(0, sb.length() - 1);
		return mApi.getUserInfos(stringRequest);
	}

	//其他
	public Observable<QiNiuTokenResponse> getQiNiuToken() {
		return mApi.getQiNiuToken();
	}

	public Observable<VersionResponse> getClientVersion() {
		return mApi.getClientVersion();
	}

	public Observable<SyncTotalDataResponse> syncTotalData(String version) {
		return mApi.syncTotalData(version);
	}

	/*********************************************** book start ********************************************/
	public Observable<GetBooksResponse> getBooks() {
		return bookApi.getBooks();
	}

	public Observable<DeleteBookByIdResponse> deleteBookById(long bookId) {
		return bookApi.deleteBook(bookId);
	}

	public Observable<AddBookResponse> addBook(String bookName, int number) {
		return bookApi.addBook(getRequestBody(new AddBookRequest(bookName, number)));
	}

	public Observable<UpdateBookResponse> updateBook(long bookId, String bookName, int number) {
		return bookApi.updateBook(getRequestBody(new UpdateBookRequest(bookId, bookName, number)));
	}
	/*********************************************** book  end  ********************************************/

	/********************************************* signature start *****************************************/
	public Observable<UploadSigResponse> uploadSig(Map<String, Object> params, byte[] img) {
		return signatureApi.upload(params, getMultipartRequestBody(img));
	}
	/********************************************* signature end   *****************************************/
}
