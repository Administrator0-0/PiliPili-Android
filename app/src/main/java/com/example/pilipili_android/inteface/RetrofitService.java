package com.example.pilipili_android.inteface;

import com.example.pilipili_android.bean.netbean.BuyCoinReturn;
import com.example.pilipili_android.bean.netbean.CommonReturn;
import com.example.pilipili_android.bean.netbean.FollowUnFollowReturn;
import com.example.pilipili_android.bean.netbean.GetSpaceDataReturn;
import com.example.pilipili_android.bean.netbean.GetUserBackgroundReturn;
import com.example.pilipili_android.bean.netbean.LoginReturn;
import com.example.pilipili_android.bean.netbean.RenameReturn;
import com.example.pilipili_android.bean.netbean.SetGenderReturn;
import com.example.pilipili_android.bean.netbean.UploadSignReturn;
import com.example.pilipili_android.bean.netbean.UserDetailReturn;
import com.example.pilipili_android.bean.netbean.UserOpenDetailReturn;
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
    @GET(UrlConstant.OPEN_DETAIL_HEAD + "{id}" + UrlConstant.OPEN_DETAIL_TAIL)
    Call<UserOpenDetailReturn> getUserFollowDetail(@Path("id") String id);

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

    /**
     * 上传签名
     *
     */
    @POST(UrlConstant.UPLOAD_SIGN)
    Call<UploadSignReturn> uploadSign(@Header("Authorization") String token, @Body RequestBody jsonObject);

    /**
     * 修改性别
     *
     */
    @PUT(UrlConstant.SET_GENDER)
    Call<SetGenderReturn> setGender(@Header("Authorization") String token, @Body RequestBody jsonObject);

    /**
     * 获取用户空间信息
     *
     */
    @GET(UrlConstant.GET_SPACE_DATA)
    Call<GetSpaceDataReturn> getSpaceData(@Header("Authorization") String token);

    /**
     * 获取用户头图
     *
     */
    @GET(UrlConstant.GET_BACKGROUND_HEAD + "{id}" + UrlConstant.GET_BACKGROUND_TAIL)
    Call<GetUserBackgroundReturn> getUserBackground(@Path("id") String id);
}
