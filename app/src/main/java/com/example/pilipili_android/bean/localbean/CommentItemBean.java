package com.example.pilipili_android.bean.localbean;

import com.example.pilipili_android.bean.netbean.CommentDetailsReturn;
import com.example.pilipili_android.bean.netbean.GetOSSUrlReturn;
import com.example.pilipili_android.bean.netbean.UserOpenDetailReturn;

public class CommentItemBean {
    private CommentDetailsReturn.DataBean comment;
    private GetOSSUrlReturn.DataBean avatar;
    private UserOpenDetailReturn.DataBean user;

    public void setAvatar(GetOSSUrlReturn.DataBean avatar) {
        this.avatar = avatar;
    }

    public void setComment(CommentDetailsReturn.DataBean comment) {
        this.comment = comment;
    }

    public void setUser(UserOpenDetailReturn.DataBean user) {
        this.user = user;
    }

    public GetOSSUrlReturn.DataBean getAvatar() {
        return avatar;
    }

    public CommentDetailsReturn.DataBean getComment() {
        return comment;
    }

    public UserOpenDetailReturn.DataBean getUser() {
        return user;
    }
}
