package com.example.pilipili_android.util;

import android.content.Context;
import android.util.Log;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.example.pilipili_android.constant.UrlConstant;

import static com.example.pilipili_android.constant.UrlConstant.PILIPILI_BUCKET;

public class AliyunOSSUtil {

    public static OSS getOSS(Context context, String accessKey, String secretKey, String securityToken) {
        OSSCredentialProvider  ossCredentialProvider = new OSSStsTokenCredentialProvider(accessKey, secretKey, securityToken);
        // 配置类如果不设置，会有默认配置。
        ClientConfiguration clientConfiguration = new ClientConfiguration();
        clientConfiguration.setConnectionTimeout(5 * 1000); // 连接超时，默认15秒。
        clientConfiguration.setSocketTimeout(5 * 1000); // socket超时，默认15秒。
        clientConfiguration.setMaxConcurrentRequest(15); // 最大并发请求数，默认5个。
//        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次。
        return new OSSClient(context, UrlConstant.PILIPILI_ENDPOINT, ossCredentialProvider, clientConfiguration);
    }

    /**
     * 获取图片Url
     * @param context 整个应用的上下文，用ViewModel中的
     * @return 图片url
     */
    public static String getImageUrl(Context context, String accessKey, String secretKey, String securityToken, String fileName) {
        return getOSS(context, accessKey, secretKey, securityToken).presignPublicObjectURL(PILIPILI_BUCKET, fileName);

    }

    public static OSSAsyncTask uploadFile(Context context, String accessKey, String secretKey, String securityToken, String fileName, String path) {
        OSS oss = getOSS(context, accessKey, secretKey, securityToken);

        //构造上传请求
        PutObjectRequest put = new PutObjectRequest(PILIPILI_BUCKET, fileName, path);

        //进度回调
        put.setProgressCallback((request, currentSize, totalSize) -> {
            Log.d("PutObject", "currentSize: " + currentSize + " totalSize: " + totalSize);
        });

        OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                Log.d("PutObject", "UploadSuccess");
                Log.d("ETag", result.getETag());
                Log.d("RequestId", result.getRequestId());
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                // 请求异常。
                if (clientExcepion != null) {
                    // 本地异常，如网络异常等。
                    clientExcepion.printStackTrace();
                }
                if (serviceException != null) {
                    // 服务异常。
                    Log.e("ErrorCode", serviceException.getErrorCode());
                    Log.e("RequestId", serviceException.getRequestId());
                    Log.e("HostId", serviceException.getHostId());
                    Log.e("RawMessage", serviceException.getRawMessage());
                }
            }
        });
        return task;
    }

}

