package com.example.pilipili_android.view_model;

import android.app.Application;
import android.content.Context;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.pilipili_android.bean.netbean.GetOSSUrlReturn;
import com.example.pilipili_android.bean.netbean.GetRelatedVideoReturn;
import com.example.pilipili_android.bean.netbean.GetVideoListReturn;
import com.example.pilipili_android.bean.netbean.NetRequestResult;
import com.example.pilipili_android.bean.netbean.UploadVideoOrCoverReturn;
import com.example.pilipili_android.bean.netbean.VideoDetailReturn;
import com.example.pilipili_android.constant.SPConstant;
import com.example.pilipili_android.inteface.OnNetRequestListener;
import com.example.pilipili_android.model.VideoDataSource;
import com.example.pilipili_android.util.AliyunOSSUtil;
import com.example.pilipili_android.util.SPUtil;

import java.util.ArrayList;
import java.util.List;

public class VideoViewModel extends AndroidViewModel {

    private Context context;
    private VideoDataSource videoDataSource;

    /**
     * 这是错误的MVVM
     */
    private VideoDetailReturn.DataBean dataBean;//用于VideoActivity中传递信息
    public void setDataBean(VideoDetailReturn.DataBean dataBean) { this.dataBean = dataBean; }
    public VideoDetailReturn.DataBean getDataBean() { return dataBean; }

    private MutableLiveData<UploadVideoOrCoverReturn> uploadVideoKeyInfo = new MutableLiveData<>();
    private MutableLiveData<UploadVideoOrCoverReturn> uploadVideoCoverKeyInfo = new MutableLiveData<>();
    private MutableLiveData<Boolean> isCompletePublish = new MutableLiveData<>();
    private MutableLiveData<String> videoUrl = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLikeSuccess = new MutableLiveData<>();
    private MutableLiveData<Boolean> isCancelLikeSuccess = new MutableLiveData<>();
    private MutableLiveData<Boolean> isStarSuccess = new MutableLiveData<>();
    private MutableLiveData<Boolean> isCancelStarSuccess = new MutableLiveData<>();
    private MutableLiveData<Integer> isRewardSuccess = new MutableLiveData<>();
    private MutableLiveData<List<GetRelatedVideoReturn.DataBean.VideoListBean>> videoRelatedBeans = new MutableLiveData<>();
    private MutableLiveData<List<VideoDetailReturn.DataBean>> hotVideoBeans = new MutableLiveData<>();
    private MutableLiveData<List<VideoDetailReturn.DataBean>> animeVideoBeans = new MutableLiveData<>();
    private MutableLiveData<List<VideoDetailReturn.DataBean>> recommendVideoBeans = new MutableLiveData<>();
    private MutableLiveData<VideoDetailReturn.DataBean> videoDetailBean = new MutableLiveData<>();

    public VideoViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
        videoDataSource = new VideoDataSource();
        videoRelatedBeans.setValue(new ArrayList<>());
        hotVideoBeans.setValue(new ArrayList<>());
        animeVideoBeans.setValue(new ArrayList<>());
        recommendVideoBeans.setValue(new ArrayList<>());
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
                GetVideoListReturn getVideoListReturn = (GetVideoListReturn) netRequestResult.getData();
                for(int pv : getVideoListReturn.getData().getVideo_list()) {
                    videoDataSource.getVideoDetail(token, pv, new OnNetRequestListener() {
                        @Override
                        public void onSuccess(NetRequestResult netRequestResult) {
                            dataBeans1.add((VideoDetailReturn.DataBean)netRequestResult.getData());
                            if(dataBeans1.size() == getVideoListReturn.getData().getVideo_list().size()){
                                recommendVideoBeans.getValue().addAll(0, dataBeans1);
                                recommendVideoBeans.setValue(hotVideoBeans.getValue());
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

    //没有大数据支持，只能实现假热门，实际调用的接口还是推荐的接口
    public void getHotVideoDetail() {
        videoDataSource.getRecommendVideoList(new OnNetRequestListener() {
            @Override
            public void onSuccess(NetRequestResult netRequestResult) {
                String token = UserBaseDetail.getToken(context);
                List<VideoDetailReturn.DataBean> dataBeans1 = new ArrayList<>();
                GetVideoListReturn getVideoListReturn = (GetVideoListReturn) netRequestResult.getData();
                for(int pv : getVideoListReturn.getData().getVideo_list()) {
                    videoDataSource.getVideoDetail(token, pv, new OnNetRequestListener() {
                        @Override
                        public void onSuccess(NetRequestResult netRequestResult) {
                            dataBeans1.add((VideoDetailReturn.DataBean)netRequestResult.getData());
                            if(dataBeans1.size() == getVideoListReturn.getData().getVideo_list().size()) {
                                hotVideoBeans.getValue().addAll(0, dataBeans1);
                                hotVideoBeans.setValue(hotVideoBeans.getValue());
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

    public void getAnimeVideoDetail() {
        videoDataSource.getAnimeVideoList(new OnNetRequestListener() {
            @Override
            public void onSuccess(NetRequestResult netRequestResult) {
                String token = UserBaseDetail.getToken(context);
                List<VideoDetailReturn.DataBean> dataBeans1 = new ArrayList<>();
                GetVideoListReturn getVideoListReturn = (GetVideoListReturn) netRequestResult.getData();
                for(int pv : getVideoListReturn.getData().getVideo_list()) {
                    videoDataSource.getVideoDetail(token, pv, new OnNetRequestListener() {
                        @Override
                        public void onSuccess(NetRequestResult netRequestResult) {
                            dataBeans1.add((VideoDetailReturn.DataBean)netRequestResult.getData());
                            if(dataBeans1.size() == getVideoListReturn.getData().getVideo_list().size()) {
                                animeVideoBeans.getValue().addAll(0, dataBeans1);
                                animeVideoBeans.setValue(animeVideoBeans.getValue());
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

    public void likeVideo() {
        videoDataSource.likeVideo(UserBaseDetail.getToken(context), dataBean.getPv(), new OnNetRequestListener() {
            @Override
            public void onSuccess(NetRequestResult netRequestResult) {

            }

            @Override
            public void onSuccess() {
                isLikeSuccess.setValue(true);
                Toast.makeText(context, "点赞成功", Toast.LENGTH_SHORT).show();
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

    public void cancelLikeVideo() {
        videoDataSource.cancelLikeVideo(UserBaseDetail.getToken(context), dataBean.getPv(), new OnNetRequestListener() {
            @Override
            public void onSuccess(NetRequestResult netRequestResult) {

            }

            @Override
            public void onSuccess() {
                isCancelLikeSuccess.setValue(true);
                Toast.makeText(context, "取消点赞", Toast.LENGTH_SHORT).show();
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

    public void starVideo() {
        videoDataSource.starVideo(UserBaseDetail.getToken(context), dataBean.getPv(), new OnNetRequestListener() {
            @Override
            public void onSuccess(NetRequestResult netRequestResult) {

            }

            @Override
            public void onSuccess() {
                isStarSuccess.setValue(true);
                Toast.makeText(context, "收藏成功", Toast.LENGTH_SHORT).show();
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

    public void cancelStarVideo() {
        videoDataSource.cancelStarVideo(UserBaseDetail.getToken(context), dataBean.getPv(), new OnNetRequestListener() {
            @Override
            public void onSuccess(NetRequestResult netRequestResult) {

            }

            @Override
            public void onSuccess() {
                isCancelStarSuccess.setValue(true);
                Toast.makeText(context, "取消收藏", Toast.LENGTH_SHORT).show();
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

    public void rewardVideo(int coins) {
        videoDataSource.rewardVideo(UserBaseDetail.getToken(context), coins, dataBean.getPv(), new OnNetRequestListener() {
            @Override
            public void onSuccess(NetRequestResult netRequestResult) {
                isRewardSuccess.setValue(coins);
                Toast.makeText(context, "投币成功", Toast.LENGTH_SHORT).show();
                SPUtil.put(context, SPConstant.COIN, netRequestResult.getData());
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

    public void getVideoDetail(int pv) {
        videoDataSource.getVideoDetail(UserBaseDetail.getToken(context), pv, new OnNetRequestListener() {
            @Override
            public void onSuccess(NetRequestResult netRequestResult) {
                videoDetailBean.setValue((VideoDetailReturn.DataBean) netRequestResult.getData());
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

    public void getRelatedVideos(int pv) {
        videoDataSource.getRelatedVideoList(pv, new OnNetRequestListener() {
            @Override
            public void onSuccess(NetRequestResult netRequestResult) {
                List<GetRelatedVideoReturn.DataBean.VideoListBean> listBeans = new ArrayList<>();
                for(Object bean : (List<?>)netRequestResult.getData()){
                    listBeans.add((GetRelatedVideoReturn.DataBean.VideoListBean) bean);
                }
                videoRelatedBeans.setValue(listBeans);
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

    public MutableLiveData<String> getVideoUrl() {
        return videoUrl;
    }

    public MutableLiveData<Boolean> getIsLikeSuccess() {
        return isLikeSuccess;
    }

    public MutableLiveData<Boolean> getIsCancelLikeSuccess() {
        return isCancelLikeSuccess;
    }

    public MutableLiveData<Boolean> getIsStarSuccess() {
        return isStarSuccess;
    }

    public MutableLiveData<Boolean> getIsCancelStarSuccess() {
        return isCancelStarSuccess;
    }

    public MutableLiveData<Integer> getIsRewardSuccess() {
        return isRewardSuccess;
    }

    public MutableLiveData<List<GetRelatedVideoReturn.DataBean.VideoListBean>> getVideoRelatedBeans() {
        return videoRelatedBeans;
    }

    public MutableLiveData<List<VideoDetailReturn.DataBean>> getHotVideoBeans() {
        return hotVideoBeans;
    }

    public MutableLiveData<List<VideoDetailReturn.DataBean>> getAnimeVideoBeans() {
        return animeVideoBeans;
    }

    public MutableLiveData<List<VideoDetailReturn.DataBean>> getRecommendVideoBeans() {
        return recommendVideoBeans;
    }

    public MutableLiveData<VideoDetailReturn.DataBean> getVideoDetailBean() {
        return videoDetailBean;
    }
}
