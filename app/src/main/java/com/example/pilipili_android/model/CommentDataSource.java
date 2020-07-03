package com.example.pilipili_android.model;

import com.example.pilipili_android.bean.netbean.CommentDetailsReturn;
import com.example.pilipili_android.bean.netbean.CommentListReturn;
import com.example.pilipili_android.bean.netbean.CommentReturn;
import com.example.pilipili_android.bean.netbean.CommentSend;
import com.example.pilipili_android.bean.netbean.CommonReturn;
import com.example.pilipili_android.bean.netbean.NetRequestResult;
import com.example.pilipili_android.bean.netbean.ReplayListReturn;
import com.example.pilipili_android.inteface.OnNetRequestListener;
import com.example.pilipili_android.inteface.RetrofitService;
import com.example.pilipili_android.util.EncryptUtil;
import com.example.pilipili_android.util.RetrofitUtil;
import com.google.gson.Gson;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentDataSource {

    private RetrofitService retrofitService;

    public CommentDataSource (){
        retrofitService = RetrofitUtil.getInstance();
    }

    public void comment(int pv, String comment, String token, OnNetRequestListener onNetRequestListener) {
        String ciphertext = EncryptUtil.getVerificationToken(token);
        CommentSend send = new CommentSend();
        send.setComment(comment);
        Gson gson = new Gson();
        String sendJson = gson.toJson(send);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), sendJson);
        Call<CommentReturn> call = retrofitService.postComment("" + pv, ciphertext, body);
        call.enqueue(new Callback<CommentReturn>() {
            @Override
            public void onResponse(Call<CommentReturn> call, Response<CommentReturn> response) {
                CommentReturn commentReturn = response.body();
                if(commentReturn != null) {
                    if(commentReturn.getCode() == 200) {
                        onNetRequestListener.onSuccess(new NetRequestResult<>(commentReturn));
                    } else {
                        onNetRequestListener.onFail(commentReturn.getMessage());
                    }
                } else {
                    onNetRequestListener.onFail("评论错误");
                }
            }
            @Override
            public void onFailure(Call<CommentReturn> call, Throwable t) {
                onNetRequestListener.onFail("网络不稳定，请检查网络");
            }
        });
    }

    public void getCommentList(int pv, int type, String token, OnNetRequestListener onNetRequestListener) {
        String ciphertext = EncryptUtil.getVerificationToken(token);
        Call<CommentListReturn> call = retrofitService.getCommentList("" + pv, "" + type, ciphertext);
        call.enqueue(new Callback<CommentListReturn>() {
            @Override
            public void onResponse(Call<CommentListReturn> call, Response<CommentListReturn> response) {
                CommentListReturn commentListReturn = response.body();
                if(commentListReturn != null) {
                    if(commentListReturn.getCode() == 200) {
                        onNetRequestListener.onSuccess(new NetRequestResult<>(commentListReturn));
                    } else {
                        onNetRequestListener.onFail(commentListReturn.getMessage());
                    }
                } else {
                    onNetRequestListener.onFail("获取评论错误");
                }
            }
            @Override
            public void onFailure(Call<CommentListReturn> call, Throwable t) {
                onNetRequestListener.onFail("网络不稳定，请检查网络");
            }
        });
    }

    public void replay(int id, String comment, String token, OnNetRequestListener onNetRequestListener) {
        String ciphertext = EncryptUtil.getVerificationToken(token);
        CommentSend send = new CommentSend();
        send.setComment(comment);
        Gson gson = new Gson();
        String sendJson = gson.toJson(send);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), sendJson);
        Call<CommentReturn> call = retrofitService.postReplay("" + id, ciphertext, body);
        call.enqueue(new Callback<CommentReturn>() {
            @Override
            public void onResponse(Call<CommentReturn> call, Response<CommentReturn> response) {
                CommentReturn commentReturn = response.body();
                if(commentReturn != null) {
                    if(commentReturn.getCode() == 200) {
                        onNetRequestListener.onSuccess(new NetRequestResult<>(commentReturn));
                    } else {
                        onNetRequestListener.onFail(commentReturn.getMessage());
                    }
                } else {
                    onNetRequestListener.onFail("回复错误");
                }
            }
            @Override
            public void onFailure(Call<CommentReturn> call, Throwable t) {
                onNetRequestListener.onFail("网络不稳定，请检查网络");
            }
        });
    }

    public void delete(int id, String token, OnNetRequestListener onNetRequestListener) {
        String ciphertext = EncryptUtil.getVerificationToken(token);
        Call<CommonReturn> call = retrofitService.deleteComment("" + id, ciphertext);
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
                    onNetRequestListener.onFail("删除错误");
                }
            }
            @Override
            public void onFailure(Call<CommonReturn> call, Throwable t) {
                onNetRequestListener.onFail("网络不稳定，请检查网络");
            }
        });
    }

    public void getReplayList(int id, String token, OnNetRequestListener onNetRequestListener) {
        String ciphertext = EncryptUtil.getVerificationToken(token);
        Call<CommentListReturn> call = retrofitService.getReplayList("" + id, ciphertext);
        call.enqueue(new Callback<CommentListReturn>() {
            @Override
            public void onResponse(Call<CommentListReturn> call, Response<CommentListReturn> response) {
                CommentListReturn replayListReturn = response.body();
                if(replayListReturn != null) {
                    if(replayListReturn.getCode() == 200) {
                        onNetRequestListener.onSuccess(new NetRequestResult<>(replayListReturn));
                    } else {
                        onNetRequestListener.onFail(replayListReturn.getMessage());
                    }
                } else {
                    onNetRequestListener.onFail("获取回复错误");
                }
            }
            @Override
            public void onFailure(Call<CommentListReturn> call, Throwable t) {
                onNetRequestListener.onFail("网络不稳定，请检查网络");
            }
        });
    }

    public void getReplayListDFS(int id, String token, OnNetRequestListener onNetRequestListener) {
        String ciphertext = EncryptUtil.getVerificationToken(token);
        Call<CommentListReturn> call = retrofitService.getReplayListDFS("" + id, ciphertext);
        call.enqueue(new Callback<CommentListReturn>() {
            @Override
            public void onResponse(Call<CommentListReturn> call, Response<CommentListReturn> response) {
                CommentListReturn replayListReturn = response.body();
                if(replayListReturn != null) {
                    if(replayListReturn.getCode() == 200) {
                        onNetRequestListener.onSuccess(new NetRequestResult<>(replayListReturn));
                    } else {
                        onNetRequestListener.onFail(replayListReturn.getMessage());
                    }
                } else {
                    onNetRequestListener.onFail("获取回复错误");
                }
            }
            @Override
            public void onFailure(Call<CommentListReturn> call, Throwable t) {
                onNetRequestListener.onFail("网络不稳定，请检查网络");
            }
        });
    }

    public void likeComment(int id, String token, OnNetRequestListener onNetRequestListener) {
        String ciphertext = EncryptUtil.getVerificationToken(token);
        Call<CommonReturn> call = retrofitService.likeComment("" + id, ciphertext);
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
                    onNetRequestListener.onFail("点赞错误");
                }
            }
            @Override
            public void onFailure(Call<CommonReturn> call, Throwable t) {
                onNetRequestListener.onFail("网络不稳定，请检查网络");
            }
        });
    }

    public void unlikeComment(int id, String token, OnNetRequestListener onNetRequestListener) {
        String ciphertext = EncryptUtil.getVerificationToken(token);
        Call<CommonReturn> call = retrofitService.unlikeComment("" + id, ciphertext);
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
                    onNetRequestListener.onFail("取消点赞错误");
                }
            }
            @Override
            public void onFailure(Call<CommonReturn> call, Throwable t) {
                onNetRequestListener.onFail("网络不稳定，请检查网络");
            }
        });
    }

    public void getCommentDetails(int id, String token, OnNetRequestListener onNetRequestListener) {
        String ciphertext = EncryptUtil.getVerificationToken(token);
        Call<CommentDetailsReturn> call = retrofitService.getCommentDetails("" + id, ciphertext);
        call.enqueue(new Callback<CommentDetailsReturn>() {
            @Override
            public void onResponse(Call<CommentDetailsReturn> call, Response<CommentDetailsReturn> response) {
                CommentDetailsReturn commentDetailsReturn = response.body();
                if(commentDetailsReturn != null) {
                    if(commentDetailsReturn.getCode() == 200) {
                        onNetRequestListener.onSuccess(new NetRequestResult<>(commentDetailsReturn));
                    } else {
                        onNetRequestListener.onFail(commentDetailsReturn.getMessage());
                    }
                } else {
                    onNetRequestListener.onFail("获取评论信息错误");
                }
            }
            @Override
            public void onFailure(Call<CommentDetailsReturn> call, Throwable t) {
                onNetRequestListener.onFail("网络不稳定，请检查网络");
            }
        });
    }
}
