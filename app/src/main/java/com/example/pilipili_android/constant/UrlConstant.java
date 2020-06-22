package com.example.pilipili_android.constant;

public interface UrlConstant {
    /**
     * PiliPili主服务器
     */
    String PILIPILI_SERVER = "http://47.93.139.52:8000/";

    String PILIPILI_ENDPOINT = "https://oss-cn-beijing.aliyuncs.com";

    String PILIPILI_BUCKET = "pilipili-bucket";

    /**
     * 用户接口
     */
    String REGISTER = "user/register";

    String VERIFY_TOKEN = "user/verify";

    String LOGIN = "user/login";

    String DETAIL = "user/details";

    String OPEN_DETAIL_HEAD = "user/";

    String OPEN_DETAIL_TAIL = "/details";

    String BUY_COIN = "user/put-coin";

    String RENAME = "user/put-username";

    String FOLLOW = "user/fan";

    String UNFOLLOW = "user/un-fan";

    String UPLOAD_SIGN = "user/upload-sign";

    String SET_GENDER = "user/put-gender";

    String GET_SPACE_DATA = "user/get-space";

    String UPLOAD_AVATAR = "user/upload-avatar";

    String UPLOAD_BACKGROUND = "user/upload-background";

    String GET_AVATAR_HEAD = "user/uid";

    String GET_AVATAR_TAIL = "/get-avatar";

    String GET_BACKGROUND_HEAD = "user/uid";

    String GET_BACKGROUND_TAIL = "/get-background";

    String BUY_VIP = "user/put-vip";


}
