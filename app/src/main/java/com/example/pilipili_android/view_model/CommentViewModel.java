package com.example.pilipili_android.view_model;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.pilipili_android.bean.localbean.CommentItemBean;
import com.example.pilipili_android.bean.netbean.CommentDetailsReturn;
import com.example.pilipili_android.bean.netbean.CommentListReturn;
import com.example.pilipili_android.bean.netbean.CommentReturn;
import com.example.pilipili_android.bean.netbean.GetUserBackgroundOrAvatarReturn;
import com.example.pilipili_android.bean.netbean.NetRequestResult;
import com.example.pilipili_android.bean.netbean.UserOpenDetailReturn;
import com.example.pilipili_android.inteface.OnNetRequestListener;
import com.example.pilipili_android.model.CommentDataSource;
import com.example.pilipili_android.model.UserDataSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class CommentViewModel extends AndroidViewModel {

    private CommentDataSource dataSource = new CommentDataSource();
    private UserDataSource userDataSource = new UserDataSource();
    private Context context;
    private MutableLiveData<List<CommentItemBean>> commentList = new MutableLiveData<>();
    private MutableLiveData<HashMap<Integer, List<CommentItemBean>>> replayList = new MutableLiveData<>();

    public CommentViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
        commentList.setValue(new ArrayList<>());
        replayList.setValue(new HashMap<>());
    }

    public MutableLiveData<List<CommentItemBean>> getCommentList() {
        return commentList;
    }

    public MutableLiveData<HashMap<Integer, List<CommentItemBean>>> getReplayList() {
        return replayList;
    }

    public void comment(int pv, String comment) {
        dataSource.comment(pv, comment, UserBaseDetail.getToken(context), new OnNetRequestListener() {
            @Override
            public void onSuccess(NetRequestResult netRequestResult) {
                int id = ((CommentReturn)netRequestResult.getData()).getData().getId();
                dataSource.getCommentDetails(id, new OnNetRequestListener() {
                    @Override
                    public void onSuccess(NetRequestResult netRequestResult) {
                        CommentDetailsReturn comment = (CommentDetailsReturn) netRequestResult.getData();
                        List<CommentItemBean> list = new ArrayList<>(Objects.requireNonNull(commentList.getValue()));
                        CommentItemBean itemBean = new CommentItemBean();
                        itemBean.setComment(comment.getData());
                        list.add(0, itemBean);
                        commentList.postValue(list);
                    }

                    @Override
                    public void onSuccess() {
                        // ignore
                    }

                    @Override
                    public void onFail() {
                        // ignore
                    }

                    @Override
                    public void onFail(String errorMessage) {
                        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onSuccess() {
                // ignore
            }

            @Override
            public void onFail() {
                // ignore
            }

            @Override
            public void onFail(String errorMessage) {
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void replay(int id, String comment) {
        dataSource.replay(id, comment, UserBaseDetail.getToken(context), new OnNetRequestListener() {
            @Override
            public void onSuccess(NetRequestResult netRequestResult) {
                int replayId = ((CommentReturn)netRequestResult.getData()).getData().getId();
                getCommentDetails(replayId, true, id);
            }

            @Override
            public void onSuccess() {
                // ignore
            }

            @Override
            public void onFail() {
                // ignore
            }

            @Override
            public void onFail(String errorMessage) {
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getCommentList(int pv, int type) {
        dataSource.getCommentList(pv, type, new OnNetRequestListener() {
            @Override
            public void onSuccess(NetRequestResult netRequestResult) {
                CommentListReturn listReturn = (CommentListReturn) netRequestResult.getData();
                List<CommentItemBean> list = Objects.requireNonNull(commentList.getValue());
                if (list != null) {
                    list.clear();
                }
                for (int i : listReturn.getData().getAll_comments()) {
                    getCommentDetails(i);
                    getReplayListPreview(i);
                }
            }

            @Override
            public void onSuccess() {
                // ignore
            }

            @Override
            public void onFail() {
                // ignore
            }

            @Override
            public void onFail(String errorMessage) {
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getReplayListPreview(int id) {
        dataSource.getReplayList(id, new OnNetRequestListener() {
            @Override
            public void onSuccess(NetRequestResult netRequestResult) {
                CommentListReturn listReturn = (CommentListReturn) netRequestResult.getData();
                List<CommentItemBean> list = Objects.requireNonNull(replayList.getValue()).get(id);
                if (list != null) {
                    list.clear();
                }
                for (int i = 0 ; i < 3 && i < listReturn.getData().getAll_comments().size(); i++) {
                    getCommentDetails(i, true, id);
                }
            }

            @Override
            public void onSuccess() {
                // ignore
            }

            @Override
            public void onFail() {
                // ignore
            }

            @Override
            public void onFail(String errorMessage) {
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getReplayList(int id) {
        dataSource.getReplayList(id, new OnNetRequestListener() {
            @Override
            public void onSuccess(NetRequestResult netRequestResult) {
                CommentListReturn listReturn = (CommentListReturn) netRequestResult.getData();
                List<CommentItemBean> list = Objects.requireNonNull(replayList.getValue()).get(id);
                if (list != null) {
                    list.clear();
                }
                for (int i : listReturn.getData().getAll_comments()) {
                    getCommentDetails(i, true, id);
                }
            }

            @Override
            public void onSuccess() {
                // ignore
            }

            @Override
            public void onFail() {
                // ignore
            }

            @Override
            public void onFail(String errorMessage) {
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getCommentDetails(int id) {
        getCommentDetails(id, false, -1);
    }

    private void getCommentDetails(int id, boolean isReplay, int parentId) {
        dataSource.getCommentDetails(id, new OnNetRequestListener() {
            @Override
            public void onSuccess(NetRequestResult netRequestResult) {
                if (isReplay) {
                    CommentDetailsReturn comment = (CommentDetailsReturn) netRequestResult.getData();
                    List<CommentItemBean> list = Objects.requireNonNull(replayList.getValue()).get(parentId);
                    if (list == null) list = new ArrayList<>();
                    CommentItemBean itemBean = new CommentItemBean();
                    itemBean.setComment(comment.getData());
                    List<CommentItemBean> finalList = list;
                    userDataSource.getUserOpenDetail(comment.getData().getAuthor(), new OnNetRequestListener() {
                        @Override
                        public void onSuccess(NetRequestResult netRequestResult) {
                            UserOpenDetailReturn userOpenDetailReturn = (UserOpenDetailReturn) netRequestResult.getData();
                            itemBean.setUser(userOpenDetailReturn.getData());
                            userDataSource.getUserAvatar(comment.getData().getAuthor(), new OnNetRequestListener() {
                                @Override
                                public void onSuccess(NetRequestResult netRequestResult) {
                                    GetUserBackgroundOrAvatarReturn avatarReturn = (GetUserBackgroundOrAvatarReturn) netRequestResult.getData();
                                    itemBean.setAvatar(avatarReturn.getData());
                                    finalList.add(itemBean);
                                    HashMap<Integer, List<CommentItemBean>> map = new HashMap<>(Objects.requireNonNull(replayList.getValue()));
                                    map.put(parentId, finalList);
                                    replayList.postValue(map);
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

                } else {
                    CommentDetailsReturn comment = (CommentDetailsReturn) netRequestResult.getData();
                    List<CommentItemBean> list = new ArrayList<>(Objects.requireNonNull(commentList.getValue()));
                    CommentItemBean itemBean = new CommentItemBean();
                    itemBean.setComment(comment.getData());
                    userDataSource.getUserOpenDetail(comment.getData().getAuthor(), new OnNetRequestListener() {
                        @Override
                        public void onSuccess(NetRequestResult netRequestResult) {
                            UserOpenDetailReturn userOpenDetailReturn = (UserOpenDetailReturn) netRequestResult.getData();
                            itemBean.setUser(userOpenDetailReturn.getData());
                            userDataSource.getUserAvatar(comment.getData().getAuthor(), new OnNetRequestListener() {
                                @Override
                                public void onSuccess(NetRequestResult netRequestResult) {
                                    GetUserBackgroundOrAvatarReturn avatarReturn = (GetUserBackgroundOrAvatarReturn) netRequestResult.getData();
                                    itemBean.setAvatar(avatarReturn.getData());
                                    list.add(itemBean);
                                    commentList.postValue(list);
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
            }

            @Override
            public void onSuccess() {
                // ignore
            }

            @Override
            public void onFail() {
                // ignore
            }

            @Override
            public void onFail(String errorMessage) {
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void likeComment(int id) {
        dataSource.likeComment(id, new OnNetRequestListener() {
            @Override
            public void onSuccess(NetRequestResult netRequestResult) {
                // ignore
            }

            @Override
            public void onSuccess() {
                Toast.makeText(context, "点赞成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFail() {
                // ignore
            }

            @Override
            public void onFail(String errorMessage) {
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void unlikeComment(int id) {
        dataSource.unlikeComment(id, new OnNetRequestListener() {
            @Override
            public void onSuccess(NetRequestResult netRequestResult) {
                // ignore
            }

            @Override
            public void onSuccess() {
                Toast.makeText(context, "取消点赞成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFail() {
                // ignore
            }

            @Override
            public void onFail(String errorMessage) {
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
