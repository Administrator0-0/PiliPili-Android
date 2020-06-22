package com.example.pilipili_android.util;

import android.content.Context;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.example.pilipili_android.constant.UrlConstant;

import static com.example.pilipili_android.constant.UrlConstant.PILIPILI_BUCKET;

public class AliyunOSSUtil {

    /**
     * 获取图片Url
     * @param context 整个应用的上下文，用ViewModel中的
     * @return 图片url
     */
    public static String getImageUrl(Context context, String accessKey, String secretKey, String securityToken, String fileName) {
        OSS oss = getOSS(context, accessKey, secretKey, securityToken);
        String imageUrl = oss.presignPublicObjectURL(PILIPILI_BUCKET, fileName);
        oss.
    }

    private static OSS getOSS(Context context, String accessKey, String SecretKey, String SecurityToken) {
        OSSCredentialProvider  ossCredentialProvider = new OSSStsTokenCredentialProvider(accessKey, SecretKey, SecurityToken);
        // 配置类如果不设置，会有默认配置。
        ClientConfiguration clientConfiguration = new ClientConfiguration();
        clientConfiguration.setConnectionTimeout(5 * 1000); // 连接超时，默认15秒。
        clientConfiguration.setSocketTimeout(5 * 1000); // socket超时，默认15秒。
//        conf.setMaxConcurrentRequest(5); // 最大并发请求数，默认5个。
//        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次。
        return new OSSClient(context, UrlConstant.PILIPILI_ENDPOINT, ossCredentialProvider, clientConfiguration);
    }

}

