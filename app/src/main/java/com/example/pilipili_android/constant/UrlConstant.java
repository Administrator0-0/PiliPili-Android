package com.example.pilipili_android.constant;

public interface UrlConstant {
    /**
     * PiliPili主服务器
     */
    String PILIPILI_SERVER = "http://47.93.139.52:8000/";

    String PILIPILI_BUCKET = "https://oss-cn-beijing.aliyuncs.com";

    /**
     * 用户接口
     */
    String REGISTER = "user/register";

    String VERIFY_TOKEN = "user/verify";

    String LOGIN = "user/login";

    String DETAIL = "user/details";

    String FOLLOW_DETAIL_HEAD = "user/";

    String FOLLOW_DETAIL_TAIL = "/details";

    String BUY_COIN = "user/put-coin";

    String RENAME = "user/put-username";

    String FOLLOW = "user/fan";

    String UNFOLLOW = "user/un-fan";
}
