package com.example.pilipili_android.model;

import android.content.Context;

import com.example.pilipili_android.bean.CommonReturn;
import com.example.pilipili_android.bean.LoginSend;
import com.example.pilipili_android.bean.NetRequestResult;
import com.example.pilipili_android.bean.RegisterSend;
import com.example.pilipili_android.inteface.OnNetRequestListener;
import com.example.pilipili_android.inteface.RetrofitService;
import com.example.pilipili_android.util.EncryptUtil;
import com.example.pilipili_android.util.RetrofitUtil;
import com.google.gson.Gson;

import java.util.Objects;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginDataSource {

    public void register(String email, String userName, String password, OnNetRequestListener onNetRequestListener) {
        RegisterSend registerSend = new RegisterSend();
        registerSend.setEmail(email);
        registerSend.setUsername(userName);
        registerSend.setPassword(password);

        Gson gson = new Gson();
        String registerSendJson = gson.toJson(registerSend);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), registerSendJson);
        Call<CommonReturn> call = RetrofitUtil.getInstance().register(body);

        call.enqueue(new Callback<CommonReturn>() {
            @Override
            public void onResponse(Call<CommonReturn> call, Response<CommonReturn> response) {
                CommonReturn commonReturn = response.body();
                if(commonReturn != null && commonReturn.getCode() == 200) {
                    onNetRequestListener.onSuccess();
                } else {
                    onNetRequestListener.onFail(Objects.requireNonNull(commonReturn).getMsg());
                }
            }

            @Override
            public void onFailure(Call<CommonReturn> call, Throwable t) {
                onNetRequestListener.onFail("网络不稳定，请检查网络");
            }
        });
    }

    public void verifyToken(String token, OnNetRequestListener onNetRequestListener) {
        String ciphertext = EncryptUtil.getVerificationToken(token);
        Call<CommonReturn> call = RetrofitUtil.getInstance().verifyToken(ciphertext);
        call.enqueue(new Callback<CommonReturn>() {
            @Override
            public void onResponse(Call<CommonReturn> call, Response<CommonReturn> response) {
                CommonReturn commonReturn = response.body();
                if(commonReturn != null && commonReturn.getCode() == 200) {
                    onNetRequestListener.onSuccess();
                } else {
                    onNetRequestListener.onFail();
                }
            }

            @Override
            public void onFailure(Call<CommonReturn> call, Throwable t) {
                onNetRequestListener.onFail("网络不稳定，请检查网络");
            }
        });
    }

    public void login(String email, String password, OnNetRequestListener onNetRequestListener) {
        LoginSend loginSend = new LoginSend();
        loginSend.setEmail(email);
        loginSend.setPassword(password);

        Gson gson = new Gson();
        String loginSendJson = gson.toJson(loginSend);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), loginSendJson);
        Call<CommonReturn> call = RetrofitUtil.getInstance().register(body);

        call.enqueue(new Callback<CommonReturn>() {
            @Override
            public void onResponse(Call<CommonReturn> call, Response<CommonReturn> response) {
                CommonReturn commonReturn = response.body();
                if(commonReturn != null && commonReturn.getCode() == 200) {
                    onNetRequestListener.onSuccess(new NetRequestResult<>(commonReturn));
                } else {
                    onNetRequestListener.onFail(Objects.requireNonNull(commonReturn).getMsg());
                }
            }

            @Override
            public void onFailure(Call<CommonReturn> call, Throwable t) {
                onNetRequestListener.onFail("网络不稳定，请检查网络");
            }
        });
    }
}
