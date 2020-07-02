package com.example.pilipili_android.constant;

import android.annotation.SuppressLint;

public interface UrlConstant {
    /**
     * PiliPili主服务器
     */
    String PILIPILI_SERVER = "http://47.93.139.52:8000/";

    String PILIPILI_ENDPOINT = "https://oss-cn-beijing.aliyuncs.com";

    String PILIPILI_BUCKET = "pilipili-bucket";

    /**
     * 本地路径
     */
    @SuppressLint("SdCardPath")
    String GLIDE_CACHE = "/data/data/com.example.pilipili_android/cache/image_manager_disk_cache";

    @SuppressLint("SdCardPath")
    String CROP_CACHE = "/data/data/com.example.pilipili_android/cache/image_cache_crop";

    @SuppressLint("SdCardPath")
    String COMPRESS_CACHE = "/data/data/com.example.pilipili_android/cache/image_cache_compress";

    /**
     * 用户接口
     */
    String REGISTER = "user/register";

    String VERIFY_TOKEN = "user/verify";

    String LOGIN = "user/login";

    String DETAIL = "user/details";

    String OPEN_DETAIL_HEAD = "user/uid";

    String OPEN_DETAIL_TAIL = "/details";

    String BUY_COIN = "user/put-coin";

    String RENAME = "user/put-username";

    String FOLLOW = "user/fan";

    String UNFOLLOW = "user/un-fan";

    String UPLOAD_SIGN = "user/upload-sign";

    String SET_GENDER = "user/put-gender";

    String UPLOAD_AVATAR = "user/upload-avatar";

    String UPLOAD_BACKGROUND = "user/upload-background";

    String GET_AVATAR_HEAD = "user/uid";

    String GET_AVATAR_TAIL = "/get-avatar";

    String GET_BACKGROUND_HEAD = "user/uid";

    String GET_BACKGROUND_TAIL = "/get-background";

    String BUY_VIP = "user/put-vip";

    String IS_FOLLOWED = "/is-followed";

    String LIST_FAN = "/list-fans";

    String LIST_FOLLOWINGS = "/list-followings";

    /**
     * 视频接口
     */

    String UPLOAD_VIDEO = "video/upload-new";

    String UPLOAD_COVER = "video/cover-new";

    String CONFIRM_UPLOAD = "video/save-new";

    String CANCEL_UPLOAD = "video/cancel-new";

    String GET_RECOMMEND_VIDEO = "video/list-video";

    String GET_VIDEO_DETAIL_HEAD = "video/pv";

    String GET_VIDEO_DETAIL_TAIL = "/details";

    String GET_VIDEO_HEAD = "video/pv";

    String GET_VIDEO_TAIL = "/video";


    String GET_VIDEO_PREFIX = "video/pv";

    String POST_VIDEO_COMMENT = "/comment";

    String GET_VIDEO_COMMENT = "/get-comment";

    String GET_COMMENT_PREFIX = "comment/comment";

    String POST_COMMENT_REPLAY = "/replay";

    String GET_COMMENT_REPLAY = "/get-replay";

    String GET_COMMENT_REPLAY_DFS = "/get-replay-dfs";

    String LIKE_COMMENT = "/like";

    String UNLIKE_COMMENT = "/unlike";

    String COMMENT_DETAILS = "/details";

    String POST_DANMUKU = "/danmuku";

    String GET_DANMUKU = "/get-danmuku";

}
