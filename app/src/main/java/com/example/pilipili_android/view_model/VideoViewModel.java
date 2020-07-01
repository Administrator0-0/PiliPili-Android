package com.example.pilipili_android.view_model;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.pilipili_android.bean.netbean.GetOSSUrlReturn;
import com.example.pilipili_android.bean.netbean.GetRecommendVideoListReturn;
import com.example.pilipili_android.bean.netbean.NetRequestResult;
import com.example.pilipili_android.bean.netbean.UploadVideoOrCoverReturn;
import com.example.pilipili_android.bean.netbean.VideoDetailReturn;
import com.example.pilipili_android.inteface.OnNetRequestListener;
import com.example.pilipili_android.model.VideoDataSource;
import com.example.pilipili_android.util.AliyunOSSUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class VideoViewModel extends AndroidViewModel {

    private Context context;
    private VideoDataSource videoDataSource;

    private List<VideoDetailReturn.DataBean> dataBeans = new ArrayList<>();//用于MainFragment中推荐刷出视频的保存
    public List<VideoDetailReturn.DataBean> getDataBeans() { return dataBeans; }

    private VideoDetailReturn.DataBean dataBean;//用于VideoActivity中传递信息
    public void setDataBean(VideoDetailReturn.DataBean dataBean) { this.dataBean = dataBean; }
    public VideoDetailReturn.DataBean getDataBean() { return dataBean; }

    private boolean isFirstIn = true;
    public boolean isFirstIn() { return isFirstIn; }
    public void setFirstIn(boolean firstIn) { isFirstIn = firstIn; }

    private MutableLiveData<UploadVideoOrCoverReturn> uploadVideoKeyInfo = new MutableLiveData<>();
    private MutableLiveData<UploadVideoOrCoverReturn> uploadVideoCoverKeyInfo = new MutableLiveData<>();
    private MutableLiveData<Boolean> isCompletePublish = new MutableLiveData<>();
    private MutableLiveData<Boolean> isFinishRefresh = new MutableLiveData<>();
    private MutableLiveData<String> videoUrl = new MutableLiveData<>();

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

    public void getVideo(int pv){
        videoDataSource.getVideo(UserBaseDetail.getToken(context), pv, new OnNetRequestListener() {
            @Override
            public void onSuccess(NetRequestResult netRequestResult) {
                GetOSSUrlReturn.DataBean dataBean = (GetOSSUrlReturn.DataBean)netRequestResult.getData();
                videoUrl.setValue(AliyunOSSUtil.getImageUrl(context, dataBean.getGuest_key(), dataBean.getGuest_secret(), dataBean.getSecurity_token(), dataBean.getFile()));
            }

            @Override
            public void onSuccess() {

            }

            @Override
            public void onFail() {

            }

            @Override
            public void onFail(String errorMessage) {

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

    public MutableLiveData<String> getVideoUrl() {
        return videoUrl;
    }
}
