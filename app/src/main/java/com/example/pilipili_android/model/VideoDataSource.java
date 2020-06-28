package com.example.pilipili_android.model;

import android.util.Log;

import com.example.pilipili_android.bean.netbean.CommonReturn;
import com.example.pilipili_android.bean.netbean.CommonSend;
import com.example.pilipili_android.bean.netbean.ConfirmUploadSend;
import com.example.pilipili_android.bean.netbean.NetRequestResult;
import com.example.pilipili_android.bean.netbean.RenameReturn;
import com.example.pilipili_android.bean.netbean.RenameSend;
import com.example.pilipili_android.bean.netbean.UploadUserBackgroundReturn;
import com.example.pilipili_android.bean.netbean.UploadVideoOrCoverReturn;
import com.example.pilipili_android.inteface.OnNetRequestListener;
import com.example.pilipili_android.inteface.RetrofitService;
import com.example.pilipili_android.util.EncryptUtil;
import com.example.pilipili_android.util.RetrofitUtil;
import com.google.gson.Gson;

import java.io.File;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoDataSource {

    private RetrofitService retrofitService;

    public VideoDataSource (){
        retrofitService = RetrofitUtil.getInstance();
    }

    public void uploadVideo(String token, String fileName, OnNetRequestListener onNetRequestListener) {
        CommonSend<String> commonSend = new CommonSend<>();
        commonSend.setData(fileName);
        Gson gson = new Gson();
        String commonSendJson = gson.toJson(commonSend);
        commonSendJson = commonSendJson.replace("data", "filename");
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), commonSendJson);
        String ciphertext = EncryptUtil.getVerificationToken(token);
        Call<UploadVideoOrCoverReturn> call = retrofitService.uploadVideo(ciphertext, body);

        call.enqueue(new Callback<UploadVideoOrCoverReturn>() {
            @Override
            public void onResponse(Call<UploadVideoOrCoverReturn> call, Response<UploadVideoOrCoverReturn> response) {
                UploadVideoOrCoverReturn uploadVideoOrCoverReturn = response.body();
                if(uploadVideoOrCoverReturn == null) {
                    onNetRequestListener.onFail("");
                    return;
                }
                if(uploadVideoOrCoverReturn.getCode() == 200) {
                    onNetRequestListener.onSuccess(new NetRequestResult<>(uploadVideoOrCoverReturn));
                } else {

                    onNetRequestListener.onFail(Objects.requireNonNull(uploadVideoOrCoverReturn).getMessage());
                }
            }

            @Override
            public void onFailure(Call<UploadVideoOrCoverReturn> call, Throwable t) {
                onNetRequestListener.onFail("请确保网络通畅~");
            }
        });
    }

    public void uploadCover(String token, String fileName, OnNetRequestListener onNetRequestListener) {
        CommonSend<String> commonSend = new CommonSend<>();
        commonSend.setData(fileName);
        Gson gson = new Gson();
        String commonSendJson = gson.toJson(commonSend);
        commonSendJson = commonSendJson.replace("data", "filename");
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), commonSendJson);
        String ciphertext = EncryptUtil.getVerificationToken(token);
        Call<UploadVideoOrCoverReturn> call = retrofitService.uploadVideoCover(ciphertext, body);

        call.enqueue(new Callback<UploadVideoOrCoverReturn>() {
            @Override
            public void onResponse(Call<UploadVideoOrCoverReturn> call, Response<UploadVideoOrCoverReturn> response) {
                UploadVideoOrCoverReturn uploadVideoOrCoverReturn = response.body();
                if(uploadVideoOrCoverReturn == null) {
                    onNetRequestListener.onFail("");
                    return;
                }
                if(uploadVideoOrCoverReturn.getCode() == 200) {
                    onNetRequestListener.onSuccess(new NetRequestResult<>(uploadVideoOrCoverReturn));
                } else {
                    onNetRequestListener.onFail(Objects.requireNonNull(uploadVideoOrCoverReturn).getMessage());
                }
            }

            @Override
            public void onFailure(Call<UploadVideoOrCoverReturn> call, Throwable t) {
                onNetRequestListener.onFail("请确保网络通畅~");
            }
        });
    }

    public void confirmUpload(String token, String title, String sign, int type, String duration, OnNetRequestListener onNetRequestListener) {
        ConfirmUploadSend confirmUploadSend = new ConfirmUploadSend();
        confirmUploadSend.setTitle(title);
        confirmUploadSend.setSign(sign);
        confirmUploadSend.setType(type);
        confirmUploadSend.setDuration(duration);
        Gson gson = new Gson();
        String confirmUploadSendJson = gson.toJson(confirmUploadSend);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), confirmUploadSendJson);
        String ciphertext = EncryptUtil.getVerificationToken(token);
        Call<CommonReturn> call = retrofitService.confirmUpload(ciphertext, body);

        call.enqueue(new Callback<CommonReturn>() {
            @Override
            public void onResponse(Call<CommonReturn> call, Response<CommonReturn> response) {
                CommonReturn commonReturn = response.body();
                if(commonReturn == null) {
                    onNetRequestListener.onFail("确认发布错误");
                    return;
                }
                if(commonReturn.getCode() == 200) {
                    Log.d("aaa2", "aaaa");
                    onNetRequestListener.onSuccess();
                } else {
                    onNetRequestListener.onFail(Objects.requireNonNull(commonReturn).getMessage());
                }
            }

            @Override
            public void onFailure(Call<CommonReturn> call, Throwable t) {
                onNetRequestListener.onFail("请确保网络通畅~");
            }
        });
    }

    public void cancelUpload(String token, boolean isOperateBucket) {
        CommonSend<Boolean> commonSend = new CommonSend<>();
        commonSend.setData(isOperateBucket);
        Gson gson = new Gson();
        String commonSendJson = gson.toJson(commonSend);
        commonSendJson = commonSendJson.replace("data", "goto_bucket");
        String ciphertext = EncryptUtil.getVerificationToken(token);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), commonSendJson);
        Call<CommonReturn> call = retrofitService.cancelUpload(ciphertext, body);
        call.enqueue(new Callback<CommonReturn>() {
            @Override
            public void onResponse(Call<CommonReturn> call, Response<CommonReturn> response) {

            }

            @Override
            public void onFailure(Call<CommonReturn> call, Throwable t) {

            }
        });
    }
}
