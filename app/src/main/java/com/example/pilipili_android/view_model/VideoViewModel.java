package com.example.pilipili_android.view_model;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.pilipili_android.bean.netbean.GetRecommendVideoListReturn;
import com.example.pilipili_android.bean.netbean.NetRequestResult;
import com.example.pilipili_android.bean.netbean.UploadVideoOrCoverReturn;
import com.example.pilipili_android.bean.netbean.VideoDetailReturn;
import com.example.pilipili_android.inteface.OnNetRequestListener;
import com.example.pilipili_android.model.VideoDataSource;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class VideoViewModel extends AndroidViewModel {

    private Context context;

    public List<VideoDetailReturn.DataBean> getDataBeans() {
        return dataBeans;
    }

    private List<VideoDetailReturn.DataBean> dataBeans = new ArrayList<>();
    private VideoDataSource videoDataSource;

    private MutableLiveData<UploadVideoOrCoverReturn> uploadVideoKeyInfo = new MutableLiveData<>();
    private MutableLiveData<UploadVideoOrCoverReturn> uploadVideoCoverKeyInfo = new MutableLiveData<>();
    private MutableLiveData<Boolean> isCompletePublish = new MutableLiveData<>();
    private MutableLiveData<Boolean> isFinishRefresh = new MutableLiveData<>();


    public VideoViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
        videoDataSource = new VideoDataSource();
    }

    public void uploadVideo(String videoName) {
        videoDataSource.uploadVideo(UserBaseDetail.getToken(context), videoName, new OnNetRequestListener() {
            @Override
            public void onSuccess(NetRequestResult netRequestResult) {
                uploadVideoKeyInfo.setValue((UploadVideoOrCoverReturn) netRequestResult.getData());
            }

            @Override
            public void onSuccess() {
            }

            @Override
            public void onFail() {

            }

            @Override
            public void onFail(String errorMessage) {
                Toast.makeText(context, "上传部署失败\n" + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void uploadVideoCover(String videoCoverName) {
        videoDataSource.uploadCover(UserBaseDetail.getToken(context), videoCoverName, new OnNetRequestListener() {
            @Override
            public void onSuccess(NetRequestResult netRequestResult) {
                uploadVideoCoverKeyInfo.setValue((UploadVideoOrCoverReturn) netRequestResult.getData());
            }

            @Override
            public void onSuccess() {

            }

            @Override
            public void onFail() {

            }

            @Override
            public void onFail(String errorMessage) {
                Toast.makeText(context, "上传部署失败\n" + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void confirmUploadVideo(String title, String sign, int type, String duration) {
        videoDataSource.confirmUpload(UserBaseDetail.getToken(context), title, sign, type, duration, new OnNetRequestListener() {
            @Override
            public void onSuccess(NetRequestResult netRequestResult) {

            }

            @Override
            public void onSuccess() {
                isCompletePublish.setValue(true);
            }

            @Override
            public void onFail() {

            }

            @Override
            public void onFail(String errorMessage) {
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void cancelUploadVideo(boolean isOperateBucket) {
        videoDataSource.cancelUpload(UserBaseDetail.getToken(context), isOperateBucket);
    }

    public void getRecommendVideoDetail() {
        videoDataSource.getRecommendVideoList(new OnNetRequestListener() {
            @Override
            public void onSuccess(NetRequestResult netRequestResult) {
                String token = UserBaseDetail.getToken(context);
                List<VideoDetailReturn.DataBean> dataBeans1 = new ArrayList<>();
                GetRecommendVideoListReturn getRecommendVideoListReturn = (GetRecommendVideoListReturn) netRequestResult.getData();
                for(int pv : getRecommendVideoListReturn.getData().getVideo_list()) {
                    videoDataSource.getVideoDetail(token, pv, new OnNetRequestListener() {
                        @Override
                        public void onSuccess(NetRequestResult netRequestResult) {
                            dataBeans1.add((VideoDetailReturn.DataBean)netRequestResult.getData());
                            if(dataBeans1.size() == 8){
                                dataBeans.addAll(0, dataBeans1);
                                isFinishRefresh.setValue(true);
                            }
                        }

                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onFail() {

                        }

                        @Override
                        public void onFail(String errorMessage) {
                            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onSuccess() {

            }

            @Override
            public void onFail() {

            }

            @Override
            public void onFail(String errorMessage) {
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }


    public MutableLiveData<UploadVideoOrCoverReturn> getUploadVideoKeyInfo() {
        return uploadVideoKeyInfo;
    }

    public MutableLiveData<UploadVideoOrCoverReturn> getUploadVideoCoverKeyInfo() {
        return uploadVideoCoverKeyInfo;
    }

    public MutableLiveData<Boolean> getIsCompletePublish() {
        return isCompletePublish;
    }

    public MutableLiveData<Boolean> getIsFinishRefresh() {
        return isFinishRefresh;
    }
}
