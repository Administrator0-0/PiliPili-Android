package com.example.pilipili_android.model;

import com.example.pilipili_android.bean.netbean.CommonReturn;
import com.example.pilipili_android.bean.netbean.DanmukuListReturn;
import com.example.pilipili_android.bean.netbean.DanmukuSend;
import com.example.pilipili_android.bean.netbean.NetRequestResult;
import com.example.pilipili_android.inteface.OnNetRequestListener;
import com.example.pilipili_android.inteface.RetrofitService;
import com.example.pilipili_android.util.EncryptUtil;
import com.example.pilipili_android.util.RetrofitUtil;
import com.google.gson.Gson;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanmukuDataSource {

    private RetrofitService retrofitService;

    public DanmukuDataSource() {
        retrofitService = RetrofitUtil.getInstance();
    }

    public void postDanmuku(int pv, DanmukuSend danmuku, String token, OnNetRequestListener onNetRequestListener) {
        String ciphertext = EncryptUtil.getVerificationToken(token);
        Gson gson = new Gson();
        String json = gson.toJson(danmuku);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);
        Call<CommonReturn> call = retrofitService.postDanmuku("" + pv, ciphertext, body);
        call.enqueue(new Callback<CommonReturn>() {
            @Override
            public void onResponse(Call<CommonReturn> call, Response<CommonReturn> response) {
                CommonReturn commonReturn = response.body();
                if(commonReturn != null) {
                    if(commonReturn.getCode() == 200) {
                        onNetRequestListener.onSuccess();
                    } else {
                        onNetRequestListener.onFail(commonReturn.getMessage());
                    }
                } else {
                    onNetRequestListener.onFail("发布弹幕错误");
                }
            }

            @Override
            public void onFailure(Call<CommonReturn> call, Throwable t) {
                onNetRequestListener.onFail("网络不稳定，请检查网络");
            }
        });
    }

    public void getDanmuku(int pv, OnNetRequestListener onNetRequestListener) {
        Call<DanmukuListReturn> call = retrofitService.getDanmuku("" + pv);
        call.enqueue(new Callback<DanmukuListReturn>() {
            @Override
            public void onResponse(Call<DanmukuListReturn> call, Response<DanmukuListReturn> response) {
                DanmukuListReturn danmukuListReturn = response.body();
                if(danmukuListReturn != null) {
                    if(danmukuListReturn.getCode() == 200) {
                        onNetRequestListener.onSuccess(new NetRequestResult<>(danmukuListReturn));
                    } else {
                        onNetRequestListener.onFail(danmukuListReturn.getMessage());
                    }
                } else {
                    onNetRequestListener.onFail("发布弹幕错误");
                }
            }

            @Override
            public void onFailure(Call<DanmukuListReturn> call, Throwable t) {
                onNetRequestListener.onFail("网络不稳定，请检查网络");
            }
        });
    }
}
