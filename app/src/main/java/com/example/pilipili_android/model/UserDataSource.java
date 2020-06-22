package com.example.pilipili_android.model;

import com.example.pilipili_android.bean.netbean.BuyCoinReturn;
import com.example.pilipili_android.bean.netbean.CommonReturn;
import com.example.pilipili_android.bean.netbean.CommonSend;
import com.example.pilipili_android.bean.netbean.FollowUnFollowReturn;
import com.example.pilipili_android.bean.netbean.GetSpaceDataReturn;
import com.example.pilipili_android.bean.netbean.LoginReturn;
import com.example.pilipili_android.bean.netbean.LoginSend;
import com.example.pilipili_android.bean.netbean.NetRequestResult;
import com.example.pilipili_android.bean.netbean.RegisterSend;
import com.example.pilipili_android.bean.netbean.RenameReturn;
import com.example.pilipili_android.bean.netbean.RenameSend;
import com.example.pilipili_android.bean.netbean.SetGenderReturn;
import com.example.pilipili_android.bean.netbean.UploadSignReturn;
import com.example.pilipili_android.bean.netbean.UserDetailReturn;
import com.example.pilipili_android.bean.netbean.UserOpenDetailReturn;
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

    private RetrofitService retrofitService;

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
                if(commonReturn != null) {
                    if(commonReturn.getCode() == 200) {
                        onNetRequestListener.onSuccess();
                    } else {
                        onNetRequestListener.onFail(commonReturn.getMessage());
                    }
                } else {
                    onNetRequestListener.onFail("注册错误");
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
        Call<LoginReturn> call = retrofitService.login(body);

        call.enqueue(new Callback<LoginReturn>() {
            @Override
            public void onResponse(Call<LoginReturn> call, Response<LoginReturn> response) {
                LoginReturn loginReturn = response.body();
                if(loginReturn != null) {
                    if(loginReturn.getCode() == 200) {
                        String token = loginReturn.getData().getToken();
                        onNetRequestListener.onSuccess(new NetRequestResult<>(token));
                    } else {
                        onNetRequestListener.onFail(loginReturn.getMessage());
                    }
                } else {
                    onNetRequestListener.onFail("登录错误");
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
                if(userDetailReturn != null) {
                    if(userDetailReturn.getCode() == 200){
                        onNetRequestListener.onSuccess(new NetRequestResult<>(userDetailReturn));
                    } else {
                        onNetRequestListener.onFail(userDetailReturn.getMessage());
                    }
                } else {
                    onNetRequestListener.onFail("拉取用户信息错误");
                }
            }

            @Override
            public void onFailure(Call<UserDetailReturn> call, Throwable t) {
                onNetRequestListener.onFail("网络不稳定，请检查网络");
            }
        });
    }

    public void getUserFollowDetail(String UID, OnNetRequestListener onNetRequestListener) {
        Call<UserOpenDetailReturn> call = retrofitService.getUserFollowDetail(UID);
        call.enqueue(new Callback<UserOpenDetailReturn>() {
            @Override
            public void onResponse(Call<UserOpenDetailReturn> call, Response<UserOpenDetailReturn> response) {
                UserOpenDetailReturn userOpenDetailReturn = response.body();
                if(userOpenDetailReturn == null) {
                    onNetRequestListener.onFail("拉取用户公开信息错误");
                    return;
                }
                if(userOpenDetailReturn.getCode() == 200) {
                    onNetRequestListener.onSuccess(new NetRequestResult<>(userOpenDetailReturn));
                } else {
                    onNetRequestListener.onFail(Objects.requireNonNull(userOpenDetailReturn).getMessage());
                }
            }

            @Override
            public void onFailure(Call<UserOpenDetailReturn> call, Throwable t) {
                onNetRequestListener.onFail("网络不稳定，请检查网络");
            }
        });
    }

    public void buyCoin(String token, int howMany, OnNetRequestListener onNetRequestListener) {
        CommonSend<Integer> commonSend = new CommonSend<>();
        commonSend.setData(howMany);
        Gson gson = new Gson();
        String commonSendJson = gson.toJson(commonSend);
        commonSendJson = commonSendJson.replace("data", "coins");
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), commonSendJson);
        String ciphertext = EncryptUtil.getVerificationToken(token);
        Call<BuyCoinReturn> call = retrofitService.buyCoin(ciphertext, body);

        call.enqueue(new Callback<BuyCoinReturn>() {
            @Override
            public void onResponse(Call<BuyCoinReturn> call, Response<BuyCoinReturn> response) {
                BuyCoinReturn buyCoinReturn = response.body();
                if(buyCoinReturn != null) {
                    if(buyCoinReturn.getCode() == 200) {
                        onNetRequestListener.onSuccess(new NetRequestResult<>(buyCoinReturn));
                    } else {
                        onNetRequestListener.onFail(buyCoinReturn.getMessage());
                    }
                } else {
                    onNetRequestListener.onFail("支付错误");
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

    public void follow(String token, int id, OnNetRequestListener onNetRequestListener){
        CommonSend<Integer> commonSend = new CommonSend<>();
        commonSend.setData(id);
        Gson gson = new Gson();
        String commonSendJson = gson.toJson(commonSend);
        commonSendJson = commonSendJson.replace("data", "id");
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

    public void unFollow(String token, int id, OnNetRequestListener onNetRequestListener){
        CommonSend<Integer> commonSend = new CommonSend<>();
        commonSend.setData(id);
        Gson gson = new Gson();
        String commonSendJson = gson.toJson(commonSend);
        commonSendJson = commonSendJson.replace("data", "id");
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

    public void uploadSign(String token, String sign, OnNetRequestListener onNetRequestListener) {
        CommonSend<String> commonSend = new CommonSend<>();
        commonSend.setData(sign);
        Gson gson = new Gson();
        String commonSendJson = gson.toJson(commonSend);
        commonSendJson = commonSendJson.replace("data", "sign");
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), commonSendJson);
        String ciphertext = EncryptUtil.getVerificationToken(token);
        Call<UploadSignReturn> call = retrofitService.uploadSign(ciphertext, body);

        call.enqueue(new Callback<UploadSignReturn>() {
            @Override
            public void onResponse(Call<UploadSignReturn> call, Response<UploadSignReturn> response) {
                UploadSignReturn uploadSignReturn = response.body();
                if(uploadSignReturn == null) {
                    onNetRequestListener.onFail("上传签名错误");
                    return;
                }
                if(uploadSignReturn.getCode() == 200) {
                    onNetRequestListener.onSuccess(new NetRequestResult<>(uploadSignReturn));
                } else {
                    onNetRequestListener.onFail(Objects.requireNonNull(uploadSignReturn).getMessage());
                }
            }

            @Override
            public void onFailure(Call<UploadSignReturn> call, Throwable t) {
                onNetRequestListener.onFail("网络不稳定，请检查网络");
            }
        });
    }

    public void setGender(String token, boolean genderBoolean, OnNetRequestListener onNetRequestListener) {
        CommonSend<Boolean> commonSend = new CommonSend<>();
        commonSend.setData(genderBoolean);
        Gson gson = new Gson();
        String commonSendJson = gson.toJson(commonSend);
        commonSendJson = commonSendJson.replace("data", "gender");
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), commonSendJson);
        String ciphertext = EncryptUtil.getVerificationToken(token);
        Call<SetGenderReturn> call = retrofitService.setGender(ciphertext, body);

        call.enqueue(new Callback<SetGenderReturn>() {
            @Override
            public void onResponse(Call<SetGenderReturn> call, Response<SetGenderReturn> response) {
                SetGenderReturn setGenderReturn = response.body();
                if(setGenderReturn == null) {
                    onNetRequestListener.onFail("变性失败");
                    return;
                }
                if(setGenderReturn.getCode() == 200) {
                    onNetRequestListener.onSuccess(new NetRequestResult<>(setGenderReturn));
                } else {
                    onNetRequestListener.onFail(Objects.requireNonNull(setGenderReturn).getMessage());
                }
            }

            @Override
            public void onFailure(Call<SetGenderReturn> call, Throwable t) {
                onNetRequestListener.onFail("网络不稳定，请检查网络");
            }
        });
    }

    public void getSpaceData(String token, OnNetRequestListener onNetRequestListener) {
        String ciphertext = EncryptUtil.getVerificationToken(token);
        Call<GetSpaceDataReturn> call = retrofitService.getSpaceData(ciphertext);
        call.enqueue(new Callback<GetSpaceDataReturn>() {
            @Override
            public void onResponse(Call<GetSpaceDataReturn> call, Response<GetSpaceDataReturn> response) {
                GetSpaceDataReturn getSpaceDataReturn = response.body();
                if(getSpaceDataReturn != null) {
                    if(getSpaceDataReturn.getCode() == 200){
                        onNetRequestListener.onSuccess(new NetRequestResult<>(getSpaceDataReturn));
                    } else {
                        onNetRequestListener.onFail(getSpaceDataReturn.getMessage());
                    }
                } else {
                    onNetRequestListener.onFail("获取空间数据失败");
                }
            }

            @Override
            public void onFailure(Call<GetSpaceDataReturn> call, Throwable t) {
                onNetRequestListener.onFail("网络不稳定，请检查网络");
            }
        });
    }

}
