package com.example.pilipili_android.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.pilipili_android.bean.netbean.BuyCoinReturn;
import com.example.pilipili_android.bean.netbean.BuyVIPReturn;
import com.example.pilipili_android.bean.netbean.BuyVIPSend;
import com.example.pilipili_android.bean.netbean.CommonReturn;
import com.example.pilipili_android.bean.netbean.CommonSend;
import com.example.pilipili_android.bean.netbean.FollowUnFollowReturn;
import com.example.pilipili_android.bean.netbean.GetOSSUrlReturn;
import com.example.pilipili_android.bean.netbean.IsFollowedReturn;
import com.example.pilipili_android.bean.netbean.ListFollowReturn;
import com.example.pilipili_android.bean.netbean.LoginReturn;
import com.example.pilipili_android.bean.netbean.LoginSend;
import com.example.pilipili_android.bean.netbean.NetRequestResult;
import com.example.pilipili_android.bean.netbean.RegisterSend;
import com.example.pilipili_android.bean.netbean.RenameReturn;
import com.example.pilipili_android.bean.netbean.RenameSend;
import com.example.pilipili_android.bean.netbean.SetGenderReturn;
import com.example.pilipili_android.bean.netbean.UploadSignReturn;
import com.example.pilipili_android.bean.netbean.UploadUserBackgroundReturn;
import com.example.pilipili_android.bean.netbean.UserDetailReturn;
import com.example.pilipili_android.bean.netbean.UserOpenDetailReturn;
import com.example.pilipili_android.bean.netbean.UserVideoReturn;
import com.example.pilipili_android.bean.netbean.VideoDetailReturn;
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
                onNetRequestListener.onFail(t.getMessage());
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
                onNetRequestListener.onFail(t.getMessage());
            }
        });
    }

    public void getUserOpenDetail(int UID, OnNetRequestListener onNetRequestListener) {
        Call<UserOpenDetailReturn> call = retrofitService.getUserOpenDetail(UID + "");
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

    public void buyVip(String token, int vip, int coins, OnNetRequestListener onNetRequestListener) {
        BuyVIPSend vipSend = new BuyVIPSend();
        vipSend.setVip(vip);
        vipSend.setCoins(coins);
        Gson gson = new Gson();
        String json = gson.toJson(vipSend);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);
        String ciphertext = EncryptUtil.getVerificationToken(token);
        Call<BuyVIPReturn> call = retrofitService.buyVip(ciphertext, body);
        call.enqueue(new Callback<BuyVIPReturn>() {
            @Override
            public void onResponse(Call<BuyVIPReturn> call, Response<BuyVIPReturn> response) {
                BuyVIPReturn buyVIPReturn = response.body();
                if(buyVIPReturn != null) {
                    if(buyVIPReturn.getCode() == 200) {
                        onNetRequestListener.onSuccess(new NetRequestResult<>(buyVIPReturn));
                    } else {
                        onNetRequestListener.onFail(buyVIPReturn.getMessage());
                    }
                } else {
                    onNetRequestListener.onFail("购买错误");
                }
            }

            @Override
            public void onFailure(Call<BuyVIPReturn> call, Throwable t) {
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

    public void isFollowed(String token, int id, OnNetRequestListener onNetRequestListener){
        String ciphertext = EncryptUtil.getVerificationToken(token);
        Call<IsFollowedReturn> call = retrofitService.isFollowed(ciphertext, "" + id);
        call.enqueue(new Callback<IsFollowedReturn>() {
            @Override
            public void onResponse(Call<IsFollowedReturn> call, Response<IsFollowedReturn> response) {
                IsFollowedReturn isFollowedReturn = response.body();
                if(isFollowedReturn == null) {
                    onNetRequestListener.onFail("获取是否关注错误");
                    return;
                }
                if(isFollowedReturn.getCode() == 200) {
                    onNetRequestListener.onSuccess(new NetRequestResult<>(isFollowedReturn));
                } else {
                    onNetRequestListener.onFail(Objects.requireNonNull(isFollowedReturn).getMessage());
                }
            }

            @Override
            public void onFailure(Call<IsFollowedReturn> call, Throwable t) {
                onNetRequestListener.onFail("网络不稳定，请检查网络");
            }
        });
    }

    public void listFollowings(String token, int id, OnNetRequestListener onNetRequestListener){
        String ciphertext = EncryptUtil.getVerificationToken(token);
        Call<ListFollowReturn> call = retrofitService.listFollowings(ciphertext, "" + id);
        call.enqueue(new Callback<ListFollowReturn>() {
            @Override
            public void onResponse(Call<ListFollowReturn> call, Response<ListFollowReturn> response) {
                ListFollowReturn listFollowReturn = response.body();
                if(listFollowReturn == null) {
                    onNetRequestListener.onFail("获取是关注列表错误");
                    return;
                }
                if(listFollowReturn.getCode() == 200) {
                    onNetRequestListener.onSuccess(new NetRequestResult<>(listFollowReturn));
                } else {
                    onNetRequestListener.onFail(Objects.requireNonNull(listFollowReturn).getMessage());
                }
            }

            @Override
            public void onFailure(Call<ListFollowReturn> call, Throwable t) {
                onNetRequestListener.onFail("网络不稳定，请检查网络");
            }
        });
    }

    public void listFan(String token, int id, OnNetRequestListener onNetRequestListener){
        String ciphertext = EncryptUtil.getVerificationToken(token);
        Call<ListFollowReturn> call = retrofitService.listFan(ciphertext, "" + id);
        call.enqueue(new Callback<ListFollowReturn>() {
            @Override
            public void onResponse(Call<ListFollowReturn> call, Response<ListFollowReturn> response) {
                ListFollowReturn listFollowReturn = response.body();
                if(listFollowReturn == null) {
                    onNetRequestListener.onFail("获取是粉丝列表错误");
                    return;
                }
                if(listFollowReturn.getCode() == 200) {
                    onNetRequestListener.onSuccess(new NetRequestResult<>(listFollowReturn));
                } else {
                    onNetRequestListener.onFail(Objects.requireNonNull(listFollowReturn).getMessage());
                }
            }

            @Override
            public void onFailure(Call<ListFollowReturn> call, Throwable t) {
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
                    onNetRequestListener.onSuccess();
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
                    onNetRequestListener.onSuccess();
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

    public void getUserBackground (int UID, OnNetRequestListener onNetRequestListener) {
        Call<GetOSSUrlReturn> call = retrofitService.getUserBackground(UID + "");
        call.enqueue(new Callback<GetOSSUrlReturn>() {
            @Override
            public void onResponse(Call<GetOSSUrlReturn> call, Response<GetOSSUrlReturn> response) {
                GetOSSUrlReturn getOSSUrlReturn = response.body();
                if(getOSSUrlReturn == null) {
                    onNetRequestListener.onFail("获取头图不能");
                    return;
                }
                if(getOSSUrlReturn.getCode() == 200) {
                    onNetRequestListener.onSuccess(new NetRequestResult<>(getOSSUrlReturn));
                } else {
                    onNetRequestListener.onFail(Objects.requireNonNull(getOSSUrlReturn).getMessage());
                }
            }

            @Override
            public void onFailure(Call<GetOSSUrlReturn> call, Throwable t) {
                onNetRequestListener.onFail("网络不稳定，请检查网络");
            }
        });
    }

    public void getUserAvatar (int UID, OnNetRequestListener onNetRequestListener) {
        Call<GetOSSUrlReturn> call = retrofitService.getUserAvatar(UID + "");
        call.enqueue(new Callback<GetOSSUrlReturn>() {
            @Override
            public void onResponse(Call<GetOSSUrlReturn> call, Response<GetOSSUrlReturn> response) {
                GetOSSUrlReturn getOSSUrlReturn = response.body();
                if(getOSSUrlReturn == null) {
                    onNetRequestListener.onFail("获取头像不能");
                    return;
                }
                if(getOSSUrlReturn.getCode() == 200) {
                    onNetRequestListener.onSuccess(new NetRequestResult<>(getOSSUrlReturn));
                } else {
                    onNetRequestListener.onFail(Objects.requireNonNull(getOSSUrlReturn).getMessage());
                }
            }

            @Override
            public void onFailure(Call<GetOSSUrlReturn> call, Throwable t) {
                onNetRequestListener.onFail("网络不稳定，请检查网络");
            }
        });
    }

    public void uploadUserBackground(String token, File background, OnNetRequestListener onNetRequestListener) {
        String ciphertext = EncryptUtil.getVerificationToken(token);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), background);
        //创建MultipartBody.Part，用于封装文件数据
        MultipartBody.Part requestImgPart =
                MultipartBody.Part.createFormData("content", background.getName(), requestBody);
        Call<UploadUserBackgroundReturn> call = retrofitService.uploadUserBackground(ciphertext, requestImgPart);
        call.enqueue(new Callback<UploadUserBackgroundReturn>() {
            @Override
            public void onResponse(@NonNull Call<UploadUserBackgroundReturn> call, @NonNull Response<UploadUserBackgroundReturn> response) {
                UploadUserBackgroundReturn uploadUserBackgroundReturn = response.body();
                if(uploadUserBackgroundReturn == null) {
                    onNetRequestListener.onFail("头图上传错误");
                    return;
                }
                if(uploadUserBackgroundReturn.getCode() == 200) {
                    onNetRequestListener.onSuccess();
                } else {
                    onNetRequestListener.onFail(Objects.requireNonNull(uploadUserBackgroundReturn).getMessage());
                }
            }

            @Override
            public void onFailure(@NonNull Call<UploadUserBackgroundReturn> call, @NonNull Throwable t) {
                onNetRequestListener.onFail("网络不稳定，请检查网络");
            }
        });

    }

    public void uploadUserAvatar(String token, File avatar, OnNetRequestListener onNetRequestListener) {
        String ciphertext = EncryptUtil.getVerificationToken(token);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), avatar);
        //创建MultipartBody.Part，用于封装文件数据
        MultipartBody.Part requestImgPart =
                MultipartBody.Part.createFormData("content", avatar.getName(), requestBody);
        Call<UploadUserBackgroundReturn> call = retrofitService.uploadUserAvatar(ciphertext, requestImgPart);
        call.enqueue(new Callback<UploadUserBackgroundReturn>() {
            @Override
            public void onResponse(@NonNull Call<UploadUserBackgroundReturn> call, @NonNull Response<UploadUserBackgroundReturn> response) {
                UploadUserBackgroundReturn uploadUserBackgroundReturn = response.body();
                if(uploadUserBackgroundReturn == null) {
                    onNetRequestListener.onFail("头像上传错误");
                    return;
                }
                if(uploadUserBackgroundReturn.getCode() == 200) {
                    onNetRequestListener.onSuccess();
                } else {
                    onNetRequestListener.onFail(Objects.requireNonNull(uploadUserBackgroundReturn).getMessage());
                }
            }

            @Override
            public void onFailure(@NonNull Call<UploadUserBackgroundReturn> call, @NonNull Throwable t) {
                onNetRequestListener.onFail("网络不稳定，请检查网络");
            }
        });
    }

    public void getUserVideo(int uid, OnNetRequestListener onNetRequestListener) {
        Call<UserVideoReturn> call = retrofitService.getUserVideoDetail(uid + "");
        call.enqueue(new Callback<UserVideoReturn>() {
            @Override
            public void onResponse(@NonNull Call<UserVideoReturn> call, @NonNull Response<UserVideoReturn> response) {
                UserVideoReturn userVideoReturn = response.body();
                if(userVideoReturn == null) {
                    onNetRequestListener.onFail("获取投稿视频错误");
                    return;
                }
                if(userVideoReturn.getCode() == 200) {
                    onNetRequestListener.onSuccess(new NetRequestResult<>(userVideoReturn.getData().getVideo_list()));
                } else {
                    onNetRequestListener.onFail(Objects.requireNonNull(userVideoReturn).getMessage());
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserVideoReturn> call, @NonNull Throwable t) {
                onNetRequestListener.onFail("网络不稳定，请检查网络");
            }
        });
    }

    public void getVideoDetail(String token, int pv, OnNetRequestListener onNetRequestListener) {
        String ciphertext = EncryptUtil.getVerificationToken(token);
        Call<VideoDetailReturn> call = retrofitService.getVideoDetail(ciphertext, pv + "");
        call.enqueue(new Callback<VideoDetailReturn>() {
            @Override
            public void onResponse(Call<VideoDetailReturn> call, Response<VideoDetailReturn> response) {
                VideoDetailReturn videoDetailReturn = response.body();
                if(videoDetailReturn == null) {
                    onNetRequestListener.onFail("获取视频详细信息错误");
                    return;
                }
                if(videoDetailReturn.getCode() == 200) {

                    onNetRequestListener.onSuccess(new NetRequestResult<>(videoDetailReturn.getData()));
                } else {
                    onNetRequestListener.onFail(Objects.requireNonNull(videoDetailReturn).getMessage());
                }
            }

            @Override
            public void onFailure(Call<VideoDetailReturn> call, Throwable t) {
                onNetRequestListener.onFail("请确保网络通畅~");
            }
        });
    }

}
