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
                int id = ((CommentReturn) netRequestResult.getData()).getData().getId();
                dataSource.getCommentDetails(id, UserBaseDetail.getToken(context), new OnNetRequestListener() {
                    @Override
                    public void onSuccess(NetRequestResult netRequestResult) {
                        CommentDetailsReturn comment = (CommentDetailsReturn) netRequestResult.getData();
                        getNewCommentDetails(comment.getData().getId(), false, true, -1);
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
                int replayId = ((CommentReturn) netRequestResult.getData()).getData().getId();
                getNewCommentDetails(replayId, true, true, id);
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
                int replayId = ((CommentReturn) netRequestResult.getData()).getData().getId();
                getNewCommentDetails(replayId, true, true, parentId);
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
        dataSource.getCommentList(pv, type, UserBaseDetail.getToken(context), new OnNetRequestListener() {
            @Override
            public void onSuccess(NetRequestResult netRequestResult) {
                CommentListReturn listReturn = (CommentListReturn) netRequestResult.getData();
                List<CommentItemBean> list = Objects.requireNonNull(commentList.getValue());
                if (list != null) {
                    list.clear();
                }
                for (CommentListReturn.DataBean.AllCommentsBean i : listReturn.getData().getAll_comments()) {
                    getCommentDetails(i);
                    getReplayListPreview(i.getId());
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
        dataSource.getReplayList(id, UserBaseDetail.getToken(context), new OnNetRequestListener() {
            @Override
            public void onSuccess(NetRequestResult netRequestResult) {
                CommentListReturn listReturn = (CommentListReturn) netRequestResult.getData();
                List<CommentItemBean> list = Objects.requireNonNull(replayList.getValue()).get(id);
                if (list != null) {
                    list.clear();
                } else {
                    Objects.requireNonNull(replayList.getValue()).put(id, new ArrayList<>());
                }
                for (int i = 0; i < 4 && i < listReturn.getData().getAll_comments().size(); i++) {
                    getCommentDetails(listReturn.getData().getAll_comments().get(i), true, false, id);
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
        dataSource.getReplayListDFS(id, UserBaseDetail.getToken(context), new OnNetRequestListener() {
            @Override
            public void onSuccess(NetRequestResult netRequestResult) {
                CommentListReturn listReturn = (CommentListReturn) netRequestResult.getData();
                List<CommentItemBean> list = Objects.requireNonNull(replayList.getValue()).get(id);
                if (list != null) {
                    list.clear();
                }
                for (CommentListReturn.DataBean.AllCommentsBean i : listReturn.getData().getAll_comments()) {
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

    public void getNewCommentDetails(int id, boolean isReplay, boolean isNewComment, int parentId) {
        dataSource.getCommentDetails(id, UserBaseDetail.getToken(context), new OnNetRequestListener() {
            @Override
            public void onSuccess(NetRequestResult netRequestResult) {
                CommentDetailsReturn detailsReturn = (CommentDetailsReturn) netRequestResult.getData();
                CommentListReturn.DataBean.AllCommentsBean bean = new CommentListReturn.DataBean.AllCommentsBean();
                bean.setAuthor(detailsReturn.getData().getAuthor());
                bean.setAuthor_name(detailsReturn.getData().getAuthor_name());
                bean.setContent(detailsReturn.getData().getContent());
                bean.setId(detailsReturn.getData().getId());
                bean.setIs_liked(false);
                bean.setLikes(0);
                bean.setReplay_id(detailsReturn.getData().getReplay_id());
                bean.setReplay_to_author(detailsReturn.getData().getReplay_to_author());
                bean.setReplay_to_author_name(detailsReturn.getData().getReplay_to_author_name());
                bean.setTime(detailsReturn.getData().getTime());
                getCommentDetails(bean, isReplay, isNewComment, parentId);
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

    public void getCommentDetails(CommentListReturn.DataBean.AllCommentsBean bean) {
        getCommentDetails(bean, false, false, -1);
    }

    private void getCommentDetails(CommentListReturn.DataBean.AllCommentsBean bean, boolean isReplay, boolean isNewComment, int parentId) {
        if (isReplay) {
            List<CommentItemBean> list = Objects.requireNonNull(replayList.getValue()).get(parentId);
            CommentItemBean itemBean = new CommentItemBean();
            itemBean.setComment(bean);
            if (list == null)
                Objects.requireNonNull(replayList.getValue()).put(parentId, new ArrayList<>());
            List<CommentItemBean> finallist = Objects.requireNonNull(replayList.getValue()).get(parentId);
            if (isNewComment) {
                finallist.add(0, itemBean);
            } else {
                finallist.add(itemBean);
            }
            HashMap<Integer, List<CommentItemBean>> map = Objects.requireNonNull(replayList.getValue());
            map.put(parentId, finallist);
            replayList.postValue(map);

            userDataSource.getUserAvatar(bean.getAuthor(), new OnNetRequestListener() {
                @Override
                public void onSuccess(NetRequestResult netRequestResult) {
                    GetOSSUrlReturn avatarReturn = (GetOSSUrlReturn) netRequestResult.getData();
                    itemBean.setAvatar(avatarReturn.getData());
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
        } else {
            List<CommentItemBean> list = Objects.requireNonNull(commentList.getValue());
            CommentItemBean itemBean = new CommentItemBean();
            itemBean.setComment(bean);
            if (isNewComment) {
                list.add(0, itemBean);
            } else {
                list.add(itemBean);
            }
            commentList.postValue(list);
            userDataSource.getUserAvatar(bean.getAuthor(), new OnNetRequestListener() {
                @Override
                public void onSuccess(NetRequestResult netRequestResult) {
                    GetOSSUrlReturn avatarReturn = (GetOSSUrlReturn) netRequestResult.getData();
                    itemBean.setAvatar(avatarReturn.getData());
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
