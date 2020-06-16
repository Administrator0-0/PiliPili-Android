package com.example.pilipili_android.model;

import android.content.Context;
import android.util.Log;

import com.example.pilipili_android.bean.BuyCoinReturn;
import com.example.pilipili_android.bean.CommonReturn;
import com.example.pilipili_android.bean.CommonSend;
import com.example.pilipili_android.bean.FollowUnFollowReturn;
import com.example.pilipili_android.bean.LoginReturn;
import com.example.pilipili_android.bean.LoginSend;
import com.example.pilipili_android.bean.NetRequestResult;
import com.example.pilipili_android.bean.RegisterSend;
import com.example.pilipili_android.bean.RenameReturn;
import com.example.pilipili_android.bean.RenameSend;
import com.example.pilipili_android.bean.UserDetailReturn;
import com.example.pilipili_android.bean.UserFollowDetailReturn;
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

public class UserDataSource {

    RetrofitService retrofitService;

    public UserDataSource (){
        retrofitService = RetrofitUtil.getInstance();
    }

    public void register(String email, String userName, String password, OnNetRequestListener onNetRequestListener) {
        RegisterSend registerSend = new RegisterSend();
        registerSend.setEmail(email);
        registerSend.setUsername(userName);
        registerSend.setPassword(password);

        Gson gson = new Gson();
        String registerSendJson = gson.toJson(registerSend);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), registerSendJson);
        Call<CommonReturn> call = retrofitService.register(body);

        call.enqueue(new Callback<CommonReturn>() {
            @Override
            public void onResponse(Call<CommonReturn> call, Response<CommonReturn> response) {
                CommonReturn commonReturn = response.body();
                if(commonReturn == null) {
                    onNetRequestListener.onFail("注册错误");
                    return;
                }
                if(commonReturn.getCode() == 200) {
                    onNetRequestListener.onSuccess();
                } else {
                    onNetRequestListener.onFail(commonReturn.getMessage());
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
        Call<CommonReturn> call = retrofitService.verifyToken(ciphertext);
        call.enqueue(new Callback<CommonReturn>() {
            @Override
            public void onResponse(Call<CommonReturn> call, Response<CommonReturn> response) {
                CommonReturn commonReturn = response.body();
                if(commonReturn == null) {
                    onNetRequestListener.onFail();
                    return;
                }
                if(commonReturn.getCode() == 200) {
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
        Call<LoginReturn> call = retrofitService.login(body);

        call.enqueue(new Callback<LoginReturn>() {
            @Override
            public void onResponse(Call<LoginReturn> call, Response<LoginReturn> response) {
                LoginReturn loginReturn = response.body();
                if(loginReturn == null) {
                    onNetRequestListener.onFail("登录错误");
                    return;
                }
                if(loginReturn.getCode() == 200) {
                    onNetRequestListener.onSuccess(new NetRequestResult<>(loginReturn));
                } else {
                    onNetRequestListener.onFail(Objects.requireNonNull(loginReturn).getMessage());
                }
            }

            @Override
            public void onFailure(Call<LoginReturn> call, Throwable t) {
                onNetRequestListener.onFail("网络不稳定，请检查网络");
            }
        });
    }

    public void getUserDetail(String token, OnNetRequestListener onNetRequestListener) {
        String ciphertext = EncryptUtil.getVerificationToken(token);
        Call<UserDetailReturn> call = retrofitService.getUserDetail(ciphertext);
        call.enqueue(new Callback<UserDetailReturn>() {
            @Override
            public void onResponse(Call<UserDetailReturn> call, Response<UserDetailReturn> response) {
                UserDetailReturn userDetailReturn = response.body();
                if(userDetailReturn == null) {
                    onNetRequestListener.onFail("获取用户信息错误");
                    return;
                }
                if(userDetailReturn.getCode() == 200) {
                    onNetRequestListener.onSuccess(new NetRequestResult<>(userDetailReturn));
                } else {
                    onNetRequestListener.onFail(Objects.requireNonNull(userDetailReturn).getMessage());
                }
            }

            @Override
            public void onFailure(Call<UserDetailReturn> call, Throwable t) {
                onNetRequestListener.onFail("网络不稳定，请检查网络");
            }
        });
    }

    public void getUserFollowDetail(String UID, OnNetRequestListener onNetRequestListener) {
        Call<UserFollowDetailReturn> call = retrofitService.getUserFollowDetail(UID);
        call.enqueue(new Callback<UserFollowDetailReturn>() {
            @Override
            public void onResponse(Call<UserFollowDetailReturn> call, Response<UserFollowDetailReturn> response) {
                UserFollowDetailReturn userFollowDetailReturn = response.body();
                if(userFollowDetailReturn == null) {
                    onNetRequestListener.onFail("获取用户关注信息错误");
                    return;
                }
                if(userFollowDetailReturn.getCode() == 200) {
                    onNetRequestListener.onSuccess(new NetRequestResult<>(userFollowDetailReturn));
                } else {
                    onNetRequestListener.onFail(Objects.requireNonNull(userFollowDetailReturn).getMessage());
                }
            }

            @Override
            public void onFailure(Call<UserFollowDetailReturn> call, Throwable t) {
                onNetRequestListener.onFail("网络不稳定，请检查网络");
            }
        });
    }

    public void buyCoin(String token, int howMany, OnNetRequestListener onNetRequestListener) {
        CommonSend<Integer> commonSend = new CommonSend<>();
        commonSend.setData(howMany);
        Gson gson = new Gson();
        String commonSendJson = gson.toJson(commonSend);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), commonSendJson);
        String ciphertext = EncryptUtil.getVerificationToken(token);
        Call<BuyCoinReturn> call = retrofitService.buyCoin(ciphertext, body);

        call.enqueue(new Callback<BuyCoinReturn>() {
            @Override
            public void onResponse(Call<BuyCoinReturn> call, Response<BuyCoinReturn> response) {
                BuyCoinReturn buyCoinReturn = response.body();
                if(buyCoinReturn == null) {
                    onNetRequestListener.onFail("买币错误");
                    return;
                }
                if(buyCoinReturn.getCode() == 200) {
                    onNetRequestListener.onSuccess(new NetRequestResult<>(buyCoinReturn));
                } else {
                    onNetRequestListener.onFail(Objects.requireNonNull(buyCoinReturn).getMessage());
                }
            }

            @Override
            public void onFailure(Call<BuyCoinReturn> call, Throwable t) {
                onNetRequestListener.onFail("网络不稳定，请检查网络");
            }
        });
    }

    public void rename(String token, String username, OnNetRequestListener onNetRequestListener) {
        RenameSend renameSend = new RenameSend();
        renameSend.setUsername(username);
        renameSend.setCost(6);//改一次名字花费多少硬币
        Gson gson = new Gson();
        String renameSendJson = gson.toJson(renameSend);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), renameSendJson);
        String ciphertext = EncryptUtil.getVerificationToken(token);
        Call<RenameReturn> call = retrofitService.rename(ciphertext, body);

        call.enqueue(new Callback<RenameReturn>() {
            @Override
            public void onResponse(Call<RenameReturn> call, Response<RenameReturn> response) {
                RenameReturn renameReturn = response.body();
                if(renameReturn == null) {
                    onNetRequestListener.onFail("改名错误");
                    return;
                }
                if(renameReturn.getCode() == 200) {
                    onNetRequestListener.onSuccess(new NetRequestResult<>(renameReturn));
                } else {
                    onNetRequestListener.onFail(Objects.requireNonNull(renameReturn).getMessage());
                }
            }

            @Override
            public void onFailure(Call<RenameReturn> call, Throwable t) {
                onNetRequestListener.onFail("网络不稳定，请检查网络");
            }
        });
    }

    public void follow(String token, String id, OnNetRequestListener onNetRequestListener){
        CommonSend<String> commonSend = new CommonSend<>();
        commonSend.setData(id);
        Gson gson = new Gson();
        String commonSendJson = gson.toJson(commonSend);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), commonSendJson);
        String ciphertext = EncryptUtil.getVerificationToken(token);
        Call<FollowUnFollowReturn> call = retrofitService.follow(ciphertext, body);

        call.enqueue(new Callback<FollowUnFollowReturn>() {
            @Override
            public void onResponse(Call<FollowUnFollowReturn> call, Response<FollowUnFollowReturn> response) {
                FollowUnFollowReturn followUnFollowReturn = response.body();
                if(followUnFollowReturn == null) {
                    onNetRequestListener.onFail("关注错误");
                    return;
                }
                if(followUnFollowReturn.getCode() == 200) {
                    onNetRequestListener.onSuccess(new NetRequestResult<>(followUnFollowReturn));
                } else {
                    onNetRequestListener.onFail(Objects.requireNonNull(followUnFollowReturn).getMessage());
                }
            }

            @Override
            public void onFailure(Call<FollowUnFollowReturn> call, Throwable t) {
                onNetRequestListener.onFail("网络不稳定，请检查网络");
            }
        });
    }

    public void unFollow(String token, String id, OnNetRequestListener onNetRequestListener){
        CommonSend<String> commonSend = new CommonSend<>();
        commonSend.setData(id);
        Gson gson = new Gson();
        String commonSendJson = gson.toJson(commonSend);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), commonSendJson);
        String ciphertext = EncryptUtil.getVerificationToken(token);
        Call<FollowUnFollowReturn> call = retrofitService.unFollow(ciphertext, body);

        call.enqueue(new Callback<FollowUnFollowReturn>() {
            @Override
            public void onResponse(Call<FollowUnFollowReturn> call, Response<FollowUnFollowReturn> response) {
                FollowUnFollowReturn followUnFollowReturn = response.body();
                if(followUnFollowReturn == null) {
                    onNetRequestListener.onFail("取消关注错误");
                    return;
                }
                if(followUnFollowReturn.getCode() == 200) {
                    onNetRequestListener.onSuccess(new NetRequestResult<>(followUnFollowReturn));
                } else {
                    onNetRequestListener.onFail(Objects.requireNonNull(followUnFollowReturn).getMessage());
                }
            }

            @Override
            public void onFailure(Call<FollowUnFollowReturn> call, Throwable t) {
                onNetRequestListener.onFail("网络不稳定，请检查网络");
            }
        });
    }
}
