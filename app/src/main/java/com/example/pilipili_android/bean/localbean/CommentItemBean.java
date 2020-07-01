package com.example.pilipili_android.bean.localbean;

import com.example.pilipili_android.bean.netbean.CommentDetailsReturn;
import com.example.pilipili_android.bean.netbean.CommentListReturn;
import com.example.pilipili_android.bean.netbean.GetOSSUrlReturn;
import com.example.pilipili_android.bean.netbean.UserOpenDetailReturn;

public class CommentItemBean {
    private CommentListReturn.DataBean.AllCommentsBean comment;
    private GetOSSUrlReturn.DataBean avatar;

    public void setAvatar(GetOSSUrlReturn.DataBean avatar) {
        this.avatar = avatar;
    }

    public void setComment(CommentListReturn.DataBean.AllCommentsBean comment) {
        this.comment = comment;
    }


    public GetOSSUrlReturn.DataBean getAvatar() {
        return avatar;
    }

    public CommentListReturn.DataBean.AllCommentsBean getComment() {
        return comment;
    }

}
