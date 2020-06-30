package com.example.pilipili_android.inteface;

import android.database.Observable;

import com.example.pilipili_android.bean.netbean.BuyCoinReturn;
import com.example.pilipili_android.bean.netbean.CommentDetailsReturn;
import com.example.pilipili_android.bean.netbean.CommentListReturn;
import com.example.pilipili_android.bean.netbean.CommentReturn;
import com.example.pilipili_android.bean.netbean.CommonReturn;
import com.example.pilipili_android.bean.netbean.DanmukuListReturn;
import com.example.pilipili_android.bean.netbean.FollowUnFollowReturn;
import com.example.pilipili_android.bean.netbean.GetRecommendVideoListReturn;
import com.example.pilipili_android.bean.netbean.GetUserBackgroundOrAvatarReturn;
import com.example.pilipili_android.bean.netbean.LoginReturn;
import com.example.pilipili_android.bean.netbean.RenameReturn;
import com.example.pilipili_android.bean.netbean.ReplayListReturn;
import com.example.pilipili_android.bean.netbean.SetGenderReturn;
import com.example.pilipili_android.bean.netbean.UploadSignReturn;
import com.example.pilipili_android.bean.netbean.UploadUserBackgroundReturn;
import com.example.pilipili_android.bean.netbean.UploadVideoOrCoverReturn;
import com.example.pilipili_android.bean.netbean.UserDetailReturn;
import com.example.pilipili_android.bean.netbean.UserOpenDetailReturn;
import com.example.pilipili_android.bean.netbean.VideoDetailReturn;
import com.example.pilipili_android.constant.UrlConstant;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
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
     * 获取用户公开信息
     *
     */
    @GET(UrlConstant.OPEN_DETAIL_HEAD + "{uid}" + UrlConstant.OPEN_DETAIL_TAIL)
    Call<UserOpenDetailReturn> getUserOpenDetail(@Path("uid") String id);

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
     * 获取用户头图
     *
     */
    @GET(UrlConstant.GET_BACKGROUND_HEAD + "{id}" + UrlConstant.GET_BACKGROUND_TAIL)
    Call<GetUserBackgroundOrAvatarReturn> getUserBackground(@Path("id") String id);

    /**
     * 获取用户头像
     *
     */
    @GET(UrlConstant.GET_AVATAR_HEAD + "{id}" + UrlConstant.GET_AVATAR_TAIL)
    Call<GetUserBackgroundOrAvatarReturn> getUserAvatar(@Path("id") String id);

    /**
     * 上传用户头图
     *
     */
    @Multipart
    @POST(UrlConstant.UPLOAD_BACKGROUND)
    Call<UploadUserBackgroundReturn> uploadUserBackground(@Header("Authorization") String token, @Part MultipartBody.Part background);

    /**
     * 上传用户头像
     *
     */
    @Multipart
    @POST(UrlConstant.UPLOAD_AVATAR)
    Call<UploadUserBackgroundReturn> uploadUserAvatar(@Header("Authorization") String token, @Part MultipartBody.Part avatar);

    /**
     * 上传视频
     *
     */
    @POST(UrlConstant.UPLOAD_VIDEO)
    Call<UploadVideoOrCoverReturn> uploadVideo(@Header("Authorization") String token, @Body RequestBody jsonObject);


    /**
     * 上传视频封面
     *
     */
    @POST(UrlConstant.UPLOAD_COVER)
    Call<UploadVideoOrCoverReturn> uploadVideoCover(@Header("Authorization") String token, @Body RequestBody jsonObject);

    /**
     * 发布
     *
     */
    @POST(UrlConstant.CONFIRM_UPLOAD)
    Call<CommonReturn> confirmUpload(@Header("Authorization") String token, @Body RequestBody jsonObject);

    /**
     * 取消发布
     *
     */
    @PUT(UrlConstant.CANCEL_UPLOAD)
    Call<CommonReturn> cancelUpload(@Header("Authorization") String token, @Body RequestBody jsonObject);

    /**
     * 评论视频
     *
     */
    @POST(UrlConstant.GET_VIDEO_PREFIX + "{id}" + UrlConstant.POST_VIDEO_COMMENT)
    Call<CommentReturn> postComment(@Path("id") String id, @Header("Authorization") String token, @Body RequestBody jsonObject);

    /**
     * 获取视频评论列表
     *
     */
    @GET(UrlConstant.GET_VIDEO_PREFIX + "{id}" + UrlConstant.GET_VIDEO_COMMENT + "/type{type}")
    Call<CommentListReturn> getCommentList(@Path("id") String id, @Path("type") String type);

    /**
     * 回复评论
     *
     */
    @POST(UrlConstant.GET_COMMENT_PREFIX + "{id}" + UrlConstant.POST_COMMENT_REPLAY)
    Call<CommentReturn> postReplay(@Path("id") String id, @Header("Authorization") String token, @Body RequestBody jsonObject);

    /**
     * 获取评论回复列表
     *
     */
    @GET(UrlConstant.GET_COMMENT_PREFIX + "{id}" + UrlConstant.GET_COMMENT_REPLAY)
    Call<ReplayListReturn> getReplayList(@Path("id") String id);

    /**
     * 获取评论回复列表 DFS
     *
     */
    @GET(UrlConstant.GET_COMMENT_PREFIX + "{id}" + UrlConstant.GET_COMMENT_REPLAY_DFS)
    Call<ReplayListReturn> getReplayListDFS(@Path("id") String id);

    /**
     * 点赞评论
     *
     */
    @PUT(UrlConstant.GET_COMMENT_PREFIX + "{id}" + UrlConstant.LIKE_COMMENT)
    Call<CommonReturn> likeComment(@Path("id") String id, @Header("Authorization") String token);

    /**
     * 取消点赞评论
     *
     */
    @PUT(UrlConstant.GET_COMMENT_PREFIX + "{id}" + UrlConstant.UNLIKE_COMMENT)
    Call<CommonReturn> unlikeComment(@Path("id") String id, @Header("Authorization") String token);

    /**
     * 获取评论详细信息
     *
     */
    @GET(UrlConstant.GET_COMMENT_PREFIX + "{id}" + UrlConstant.COMMENT_DETAILS)
    Call<CommentDetailsReturn> getCommentDetails(@Path("id") String id, @Header("Authorization") String token);

    /**
     * 获取推荐页视频列表
     *
     */
    @GET(UrlConstant.GET_RECOMMEND_VIDEO)
    Call<GetRecommendVideoListReturn> getRecommendVideoList();

    /**
     * 获取视频详细信息（包括封面）
     *
     */
    @GET(UrlConstant.GET_VIDEO_DETAIL_HEAD + "{pv}" + UrlConstant.GET_VIDEO_DETAIL_TAIL)
    Call<VideoDetailReturn> getVideoDetail(@Header("Authorization") String token, @Path("pv") String pv);
    /**
     * 发布弹幕
     *
     */
    @POST(UrlConstant.GET_VIDEO_PREFIX + "{id}" + UrlConstant.POST_DANMUKU)
    Call<CommonReturn> postDanmuku(@Path("id") String id, @Header("Authorization") String token, @Body RequestBody jsonObject);

    /**
     * 获取弹幕
     *
     */
    @GET(UrlConstant.GET_VIDEO_PREFIX + "{id}" + UrlConstant.GET_DANMUKU)
    Call<DanmukuListReturn> getDanmuku(@Path("id") String id);

}
