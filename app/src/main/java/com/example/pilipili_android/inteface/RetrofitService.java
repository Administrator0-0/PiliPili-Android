package com.example.pilipili_android.inteface;

import com.example.pilipili_android.bean.BuyCoinReturn;
import com.example.pilipili_android.bean.CommonReturn;
import com.example.pilipili_android.bean.FollowUnFollowReturn;
import com.example.pilipili_android.bean.LoginReturn;
import com.example.pilipili_android.bean.RenameReturn;
import com.example.pilipili_android.bean.UserDetailReturn;
import com.example.pilipili_android.bean.UserFollowDetailReturn;
import com.example.pilipili_android.constant.UrlConstant;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RetrofitService {

    /**
     * 注册
     *
     */
    @POST(UrlConstant.REGISTER)
    Call<CommonReturn> register(@Body RequestBody jsonObject);

    /**
     * 验证token
     *
     */
    @GET(UrlConstant.VERIFY_TOKEN)
    Call<CommonReturn> verifyToken(@Header("Authorization") String token);

    /**
     * 登陆
     *
     */
    @POST(UrlConstant.LOGIN)
    Call<LoginReturn> login(@Body RequestBody jsonObject);

    /**
     * 获取用户信息
     *
     */
    @GET(UrlConstant.DETAIL)
    Call<UserDetailReturn> getUserDetail(@Header("Authorization") String token);

    /**
     * 获取用户关注信息
     *
     */
    @GET(UrlConstant.FOLLOW_DETAIL_HEAD + "{id}" + UrlConstant.FOLLOW_DETAIL_TAIL)
    Call<UserFollowDetailReturn> getUserFollowDetail(@Path("id") String id);

    /**
     * 用户买币
     *
     */
    @PUT(UrlConstant.BUY_COIN)
    Call<BuyCoinReturn> buyCoin(@Header("Authorization") String token, @Body RequestBody jsonObject);

    /**
     * 用户改名
     *
     */
    @PUT(UrlConstant.RENAME)
    Call<RenameReturn> rename(@Header("Authorization") String token, @Body RequestBody jsonObject);

    /**
     * 关注
     *
     */
    @PUT(UrlConstant.FOLLOW)
    Call<FollowUnFollowReturn> follow(@Header("Authorization") String token, @Body RequestBody jsonObject);

    /**
     * 取消关注
     *
     */
    @PUT(UrlConstant.UNFOLLOW)
    Call<FollowUnFollowReturn> unFollow(@Header("Authorization") String token, @Body RequestBody jsonObject);
}
