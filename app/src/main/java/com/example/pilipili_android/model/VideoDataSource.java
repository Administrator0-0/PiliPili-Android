package com.example.pilipili_android.model;

import android.util.Log;

import com.example.pilipili_android.bean.netbean.CommonReturn;
import com.example.pilipili_android.bean.netbean.CommonSend;
import com.example.pilipili_android.bean.netbean.ConfirmUploadSend;
import com.example.pilipili_android.bean.netbean.GetOSSUrlReturn;
import com.example.pilipili_android.bean.netbean.GetRelatedVideoReturn;
import com.example.pilipili_android.bean.netbean.GetVideoListReturn;
import com.example.pilipili_android.bean.netbean.NetRequestResult;
import com.example.pilipili_android.bean.netbean.RewardVideoReturn;
import com.example.pilipili_android.bean.netbean.UploadVideoOrCoverReturn;
import com.example.pilipili_android.bean.netbean.VideoDetailReturn;
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

    public void getRecommendVideoList(OnNetRequestListener onNetRequestListener) {
        Call<GetVideoListReturn> call = retrofitService.getRecommendVideoList();
        call.enqueue(new Callback<GetVideoListReturn>() {
            @Override
            public void onResponse(Call<GetVideoListReturn> call, Response<GetVideoListReturn> response) {
                GetVideoListReturn getVideoListReturn = response.body();
                if(getVideoListReturn == null) {
                    onNetRequestListener.onFail("拉取视频列表错误");
                    return;
                }
                if(getVideoListReturn.getCode() == 200) {
                    onNetRequestListener.onSuccess(new NetRequestResult<>(getVideoListReturn));
                } else {
                    onNetRequestListener.onFail(Objects.requireNonNull(getVideoListReturn).getMessage());
                }
            }

            @Override
            public void onFailure(Call<GetVideoListReturn> call, Throwable t) {
                onNetRequestListener.onFail("请确保网络通畅~");
            }
        });
    }

    public void getAnimeVideoList(OnNetRequestListener onNetRequestListener) {
        Call<GetVideoListReturn> call = retrofitService.getAnimeVideoList();
        call.enqueue(new Callback<GetVideoListReturn>() {
            @Override
            public void onResponse(Call<GetVideoListReturn> call, Response<GetVideoListReturn> response) {
                GetVideoListReturn getVideoListReturn = response.body();
                if(getVideoListReturn == null) {
                    onNetRequestListener.onFail("拉取视频列表错误");
                    return;
                }
                if(getVideoListReturn.getCode() == 200) {
                    onNetRequestListener.onSuccess(new NetRequestResult<>(getVideoListReturn));
                } else {
                    onNetRequestListener.onFail(Objects.requireNonNull(getVideoListReturn).getMessage());
                }
            }

            @Override
            public void onFailure(Call<GetVideoListReturn> call, Throwable t) {
                onNetRequestListener.onFail("请确保网络通畅~");
            }
        });
    }

    public void getRelatedVideoList(int pv, OnNetRequestListener onNetRequestListener) {
        Call<GetRelatedVideoReturn> call = retrofitService.getRelatedVideoList(pv + "");
        call.enqueue(new Callback<GetRelatedVideoReturn>() {
            @Override
            public void onResponse(Call<GetRelatedVideoReturn> call, Response<GetRelatedVideoReturn> response) {
                GetRelatedVideoReturn getRelatedVideoReturn = response.body();
                if(getRelatedVideoReturn == null) {
                    onNetRequestListener.onFail("拉取视频列表错误");
                    return;
                }
                if(getRelatedVideoReturn.getCode() == 200) {
                    onNetRequestListener.onSuccess(new NetRequestResult<>(getRelatedVideoReturn.getData().getVideo_list()));
                } else {
                    onNetRequestListener.onFail(Objects.requireNonNull(getRelatedVideoReturn).getMessage());
                }
            }

            @Override
            public void onFailure(Call<GetRelatedVideoReturn> call, Throwable t) {
                onNetRequestListener.onFail("请确保网络通畅~");
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

    public void getVideo(String token, int pv, OnNetRequestListener onNetRequestListener) {
        String ciphertext = EncryptUtil.getVerificationToken(token);
        Call<GetOSSUrlReturn> call = retrofitService.getVideo(ciphertext, pv + "");
        call.enqueue(new Callback<GetOSSUrlReturn>() {
            @Override
            public void onResponse(Call<GetOSSUrlReturn> call, Response<GetOSSUrlReturn> response) {
                GetOSSUrlReturn getOSSUrlReturn = response.body();
                if(getOSSUrlReturn == null) {
                    Log.d("VideoDataSource", "获取视频错误");
                    return;
                }
                if(getOSSUrlReturn.getCode() == 200) {
                    onNetRequestListener.onSuccess(new NetRequestResult<>(getOSSUrlReturn.getData()));
                } else {
                    onNetRequestListener.onFail(Objects.requireNonNull(getOSSUrlReturn).getMessage());
                }
            }

            @Override
            public void onFailure(Call<GetOSSUrlReturn> call, Throwable t) {
                onNetRequestListener.onFail("请确保网络通畅~");
            }
        });
    }

    public void likeVideo(String token, int pv, OnNetRequestListener onNetRequestListener) {
        String ciphertext = EncryptUtil.getVerificationToken(token);
        Call<CommonReturn> call = retrofitService.likeVideo(ciphertext, pv + "");
        call.enqueue(new Callback<CommonReturn>() {
            @Override
            public void onResponse(Call<CommonReturn> call, Response<CommonReturn> response) {
                CommonReturn commonReturn = response.body();
                if(commonReturn == null) {
                    onNetRequestListener.onFail("点赞错误");
                    return;
                }
                if(commonReturn.getCode() == 200) {
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

    public void cancelLikeVideo(String token, int pv, OnNetRequestListener onNetRequestListener) {
        String ciphertext = EncryptUtil.getVerificationToken(token);
        Call<CommonReturn> call = retrofitService.cancelLikeVideo(ciphertext, pv + "");
        call.enqueue(new Callback<CommonReturn>() {
            @Override
            public void onResponse(Call<CommonReturn> call, Response<CommonReturn> response) {
                CommonReturn commonReturn = response.body();
                if(commonReturn == null) {
                    onNetRequestListener.onFail("取消点赞错误");
                    return;
                }
                if(commonReturn.getCode() == 200) {
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

    public void starVideo(String token, int pv, OnNetRequestListener onNetRequestListener) {
        String ciphertext = EncryptUtil.getVerificationToken(token);
        Call<CommonReturn> call = retrofitService.starVideo(ciphertext, pv + "");
        call.enqueue(new Callback<CommonReturn>() {
            @Override
            public void onResponse(Call<CommonReturn> call, Response<CommonReturn> response) {
                CommonReturn commonReturn = response.body();
                if(commonReturn == null) {
                    onNetRequestListener.onFail("收藏错误");
                    return;
                }
                if(commonReturn.getCode() == 200) {
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

    public void cancelStarVideo(String token, int pv, OnNetRequestListener onNetRequestListener) {
        String ciphertext = EncryptUtil.getVerificationToken(token);
        Call<CommonReturn> call = retrofitService.cancelStarVideo(ciphertext, pv + "");
        call.enqueue(new Callback<CommonReturn>() {
            @Override
            public void onResponse(Call<CommonReturn> call, Response<CommonReturn> response) {
                CommonReturn commonReturn = response.body();
                if(commonReturn == null) {
                    onNetRequestListener.onFail("取消收藏错误");
                    return;
                }
                if(commonReturn.getCode() == 200) {
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

    public void rewardVideo(String token, int coin, int pv, OnNetRequestListener onNetRequestListener) {
        CommonSend<Integer> commonSend = new CommonSend<>();
        commonSend.setData(coin);
        Gson gson = new Gson();
        String commonSendJson = gson.toJson(commonSend);
        commonSendJson = commonSendJson.replace("data", "coins");
        String ciphertext = EncryptUtil.getVerificationToken(token);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), commonSendJson);
        Call<RewardVideoReturn> call = retrofitService.rewardVideo(ciphertext, pv + "", body);
        call.enqueue(new Callback<RewardVideoReturn>() {
            @Override
            public void onResponse(Call<RewardVideoReturn> call, Response<RewardVideoReturn> response) {
                RewardVideoReturn rewardVideoReturn = response.body();
                if(rewardVideoReturn == null) {
                    onNetRequestListener.onFail("投币错误");
                    return;
                }
                if(rewardVideoReturn.getCode() == 200) {
                    onNetRequestListener.onSuccess(new NetRequestResult<>(rewardVideoReturn.getData().getCoins()));
                } else {
                    onNetRequestListener.onFail(Objects.requireNonNull(rewardVideoReturn).getMessage());
                }
            }

            @Override
            public void onFailure(Call<RewardVideoReturn> call, Throwable t) {
                onNetRequestListener.onFail("请确保网络通畅~");
            }
        });
    }

}
