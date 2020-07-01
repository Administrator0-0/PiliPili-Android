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
import com.example.pilipili_android.bean.netbean.GetOSSUrlReturn;
import com.example.pilipili_android.bean.netbean.NetRequestResult;
import com.example.pilipili_android.bean.netbean.ReplayListReturn;
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
                dataSource.getCommentDetails(id, UserBaseDetail.getToken(context), new OnNetRequestListener() {
                    @Override
                    public void onSuccess(NetRequestResult netRequestResult) {
                        CommentDetailsReturn comment = (CommentDetailsReturn) netRequestResult.getData();
                        getNewCommentDetails(comment.getData().getId());
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
                getCommentDetails(replayId, true, true, id);
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

    public void detailsReplay(int parentId, int id, String comment) {
        dataSource.replay(id, comment, UserBaseDetail.getToken(context), new OnNetRequestListener() {
            @Override
            public void onSuccess(NetRequestResult netRequestResult) {
                int replayId = ((CommentReturn)netRequestResult.getData()).getData().getId();
                getCommentDetails(replayId, true, true, parentId);
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
                ReplayListReturn listReturn = (ReplayListReturn) netRequestResult.getData();
                List<CommentItemBean> list = Objects.requireNonNull(replayList.getValue()).get(id);
                if (list != null) {
                    list.clear();
                } else {
                    Objects.requireNonNull(replayList.getValue()).put(id, new ArrayList<>());
                }
                for (int i = 0 ; i < 4 && i < listReturn.getData().getAll_replays().size(); i++) {
                    getCommentDetails(listReturn.getData().getAll_replays().get(i), true, false, id);
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
                ReplayListReturn listReturn = (ReplayListReturn) netRequestResult.getData();
                List<CommentItemBean> list = Objects.requireNonNull(replayList.getValue()).get(id);
                if (list != null) {
                    list.clear();
                }
                for (int i : listReturn.getData().getAll_replays()) {
                    getCommentDetails(i, true, false, id);
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

    public void getReplayListDFS(int id) {
        dataSource.getReplayListDFS(id, new OnNetRequestListener() {
            @Override
            public void onSuccess(NetRequestResult netRequestResult) {
                ReplayListReturn listReturn = (ReplayListReturn) netRequestResult.getData();
                List<CommentItemBean> list = Objects.requireNonNull(replayList.getValue()).get(id);
                if (list != null) {
                    list.clear();
                }
                for (int i : listReturn.getData().getAll_replays()) {
                    getCommentDetails(i, true, false, id);
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

    public void getNewCommentDetails(int id) {
        getCommentDetails(id, false, true, -1);
    }

    public void getCommentDetails(int id) {
        getCommentDetails(id, false, false, -1);
    }

    private void getCommentDetails(int id, boolean isReplay, boolean isNewComment, int parentId) {
        dataSource.getCommentDetails(id, UserBaseDetail.getToken(context), new OnNetRequestListener() {
            @Override
            public void onSuccess(NetRequestResult netRequestResult) {
                if (isReplay) {
                    CommentDetailsReturn comment = (CommentDetailsReturn) netRequestResult.getData();
                    List<CommentItemBean> list = Objects.requireNonNull(replayList.getValue()).get(parentId);
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
                                    GetOSSUrlReturn avatarReturn = (GetOSSUrlReturn) netRequestResult.getData();
                                    itemBean.setAvatar(avatarReturn.getData());
                                    if (list == null) Objects.requireNonNull(replayList.getValue()).put(parentId, new ArrayList<>());
                                    List<CommentItemBean> finallist = Objects.requireNonNull(replayList.getValue()).get(parentId);
                                    if (isNewComment) {
                                        finallist.add(0, itemBean);
                                    } else {
                                        finallist.add(itemBean);
                                    }
                                    HashMap<Integer, List<CommentItemBean>> map = Objects.requireNonNull(replayList.getValue());
                                    map.put(parentId, finallist);
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
                    List<CommentItemBean> list = Objects.requireNonNull(commentList.getValue());
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
                                    GetOSSUrlReturn avatarReturn = (GetOSSUrlReturn) netRequestResult.getData();
                                    itemBean.setAvatar(avatarReturn.getData());
                                    if (isNewComment) {
                                        list.add(0, itemBean);
                                    } else {
                                        list.add(itemBean);
                                    }
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

    public void likeComment(CommentItemBean itemBean, int parentId) {
        dataSource.likeComment(itemBean.getComment().getId(), UserBaseDetail.getToken(context), new OnNetRequestListener() {
            @Override
            public void onSuccess(NetRequestResult netRequestResult) {
                // ignore
            }

            @Override
            public void onSuccess() {
                itemBean.getComment().setLikes(itemBean.getComment().getLikes() + 1);
                itemBean.getComment().setIs_liked(true);
                if (itemBean.getComment().getReplay_to_author() == null) {
                    List<CommentItemBean> list = Objects.requireNonNull(commentList.getValue());
                    list.set(list.indexOf(itemBean), itemBean);
                    commentList.postValue(list);
                } else {
                    List<CommentItemBean> finallist = Objects.requireNonNull(replayList.getValue()).get(parentId);
                    HashMap<Integer, List<CommentItemBean>> map = Objects.requireNonNull(replayList.getValue());
                    map.put(parentId, finallist);
                    replayList.postValue(map);
                }
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

    public void unlikeComment(CommentItemBean itemBean, int parentId) {
        dataSource.unlikeComment(itemBean.getComment().getId(), UserBaseDetail.getToken(context), new OnNetRequestListener() {
            @Override
            public void onSuccess(NetRequestResult netRequestResult) {
                // ignore
            }

            @Override
            public void onSuccess() {
                itemBean.getComment().setLikes(itemBean.getComment().getLikes() - 1);
                itemBean.getComment().setIs_liked(false);
                if (itemBean.getComment().getReplay_to_author() == null) {
                    List<CommentItemBean> list = Objects.requireNonNull(commentList.getValue());
                    list.set(list.indexOf(itemBean), itemBean);
                    commentList.postValue(list);
                } else {
                    List<CommentItemBean> finallist = Objects.requireNonNull(replayList.getValue()).get(parentId);
                    HashMap<Integer, List<CommentItemBean>> map = Objects.requireNonNull(replayList.getValue());
                    map.put(parentId, finallist);
                    replayList.postValue(map);
                }
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
