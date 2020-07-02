package com.example.pilipili_android.view_model;

import android.app.Application;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.pilipili_android.bean.localbean.SignBean;
import com.example.pilipili_android.bean.netbean.BuyCoinReturn;
import com.example.pilipili_android.bean.netbean.BuyVIPReturn;
import com.example.pilipili_android.bean.netbean.GetOSSUrlReturn;
import com.example.pilipili_android.bean.netbean.IsFollowedReturn;
import com.example.pilipili_android.bean.netbean.ListFollowReturn;
import com.example.pilipili_android.bean.netbean.LoginSend;
import com.example.pilipili_android.bean.netbean.NetRequestResult;
import com.example.pilipili_android.bean.localbean.SpaceActivityBean;
import com.example.pilipili_android.bean.netbean.RenameReturn;
import com.example.pilipili_android.bean.netbean.UserDetailReturn;
import com.example.pilipili_android.bean.netbean.UserOpenDetailReturn;
import com.example.pilipili_android.constant.SPConstant;
import com.example.pilipili_android.inteface.OnNetRequestListener;
import com.example.pilipili_android.model.UserDataSource;
import com.example.pilipili_android.util.AliyunOSSUtil;
import com.example.pilipili_android.util.EncryptUtil;
import com.example.pilipili_android.util.SPUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class UserViewModel extends AndroidViewModel {

    private Context context;

    private UserDataSource userDataSource;

    private MutableLiveData<Boolean> isValid = new MutableLiveData<>();
    private MutableLiveData<Boolean> isSuccessLogin = new MutableLiveData<>();
    private MutableLiveData<LoginSend> loginInfo = new MutableLiveData<>();
    private MutableLiveData<UserDetailReturn> userDetail = new MutableLiveData<>();
    private MutableLiveData<Boolean> isSuccessBuyCoin = new MutableLiveData<>();
    private MutableLiveData<Boolean> isSuccessBuyVip = new MutableLiveData<>();
    private MutableLiveData<SpaceActivityBean> spaceActivityBean = new MutableLiveData<>();
    private MutableLiveData<String> spaceBackgroundUrl = new MutableLiveData<>();
    private MutableLiveData<String> spaceAvatarUrl = new MutableLiveData<>();
    private MutableLiveData<Boolean> isSetGenderSuccess = new MutableLiveData<>();//此变量false为男，true为女，值只要发生改变即代表变性成功
    private MutableLiveData<String> newUsername = new MutableLiveData<>();
    private MutableLiveData<SignBean> newSign = new MutableLiveData<>();
    private MutableLiveData<Boolean> isFollowed = new MutableLiveData<>();
    private MutableLiveData<List<ListFollowReturn.DataBean.ListBean>> followedList = new MutableLiveData<>();
    private MutableLiveData<List<ListFollowReturn.DataBean.ListBean>> fanList = new MutableLiveData<>();

    public UserViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
        userDataSource = new UserDataSource();
        followedList.setValue(new ArrayList<>());
        fanList.setValue(new ArrayList<>());
    }

    public boolean verifyLocalToken(){
        String localToken = UserBaseDetail.getToken(context);
        if(localToken == null || localToken.equals("")){
            isValid.postValue(false);
            return false;
        } else {
            userDataSource.verifyToken(localToken, new OnNetRequestListener() {
                @Override
                public void onSuccess(NetRequestResult netRequestResult) {
                }

                @Override
                public void onSuccess() {
                    isValid.postValue(true);
                }

                @Override
                public void onFail() {
                    isValid.postValue(false);
                }

                @Override
                public void onFail(String errorMessage) {
                    isValid.postValue(true);//没有网也让用户进MainActivity
                    Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
                }
            });
            return true;
        }
    }

    public void login(String email, String password) {
        if(email.trim().equals("")){
            Toast.makeText(context, "请输入邮箱", Toast.LENGTH_SHORT).show();
        } else if (password.trim().equals("")) {
            Toast.makeText(context, "请输入密码", Toast.LENGTH_SHORT).show();
        } else {
            userDataSource.login(email, password, new OnNetRequestListener() {
                @Override
                public void onSuccess(NetRequestResult netRequestResult) {
                    putToken((String)netRequestResult.getData());
                    userDataSource.getUserDetail(UserBaseDetail.getToken(context), new OnNetRequestListener() {
                        @Override
                        public void onSuccess(NetRequestResult netRequestResult) {
                            UserDetailReturn userDetailReturn = (UserDetailReturn)netRequestResult.getData();
                            putCoin(userDetailReturn.getData().getCoins());
                            putUID(userDetailReturn.getData().getId());
                            putUsername(userDetailReturn.getData().getUsername());
                            putFollowerCount(userDetailReturn.getData().getFans_count());
                            putFollowingCount(userDetailReturn.getData().getFollowings_count());
                            putGender(userDetailReturn.getData().isGender());
                            putVIPDeadline(userDetailReturn.getData().getVip());
                            userDataSource.getUserAvatar(UserBaseDetail.getUID(context), new OnNetRequestListener() {
                                @Override
                                public void onSuccess(NetRequestResult netRequestResult) {
                                    GetOSSUrlReturn getOSSUrlReturn = (GetOSSUrlReturn) netRequestResult.getData();
                                    if(getOSSUrlReturn.getData().getFile() == null) {
                                        isSuccessLogin.setValue(true);
                                        return;
                                    }
                                    String url = AliyunOSSUtil.getImageUrl(context, getOSSUrlReturn.getData().getGuest_key(),
                                            getOSSUrlReturn.getData().getGuest_secret(),
                                            getOSSUrlReturn.getData().getSecurity_token(),
                                            getOSSUrlReturn.getData().getFile());

                                    CustomTarget<Drawable> customTarget = new CustomTarget<Drawable>() {
                                        @Override
                                        public void onResourceReady(@NonNull Drawable resource, Transition<? super Drawable> transition) {
                                            putAvatar(url);
                                            isSuccessLogin.setValue(true);
                                        }

                                        @Override
                                        public void onLoadCleared(@Nullable Drawable placeholder) {

                                        }
                                    };

                                    Glide.with(context).load(url).into(customTarget);
                                }

                                @Override
                                public void onSuccess() {

                                }

                                @Override
                                public void onFail() {

                                }

                                @Override
                                public void onFail(String errorMessage) {
                                    isSuccessLogin.setValue(false);
                                    Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
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
                            isSuccessLogin.setValue(false);
                            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
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
                    isSuccessLogin.setValue(false);
                    Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void register(String email, String username, String password) {
        if(username.trim().equals("")){
            Toast.makeText(context, "昵称不能为空哦~", Toast.LENGTH_SHORT).show();
        } else if(email.trim().equals("")) {
            Toast.makeText(context, "邮箱不能为空哦~", Toast.LENGTH_SHORT).show();
        } else if(password.trim().equals("")) {
            Toast.makeText(context, "密码不能为空哦~", Toast.LENGTH_SHORT).show();
        } else if(!EncryptUtil.isUsernameValid(username)) {
            Toast.makeText(context, "昵称无效哦~", Toast.LENGTH_SHORT).show();
        } else if (!EncryptUtil.isEmailValid(email)) {
            Toast.makeText(context, "邮箱无效哦~", Toast.LENGTH_SHORT).show();
        } else if (!EncryptUtil.isPasswordValid(password)) {
            Toast.makeText(context, "密码无效哦~", Toast.LENGTH_SHORT).show();
        } else {
            userDataSource.register(email, username, password, new OnNetRequestListener() {
                @Override
                public void onSuccess(NetRequestResult netRequestResult) {

                }

                @Override
                public void onSuccess() {
                    LoginSend loginSend = new LoginSend();
                    loginSend.setEmail(email);
                    loginSend.setPassword(password);
                    loginInfo.setValue(loginSend);
                    Toast.makeText(context, "注册成功!", Toast.LENGTH_SHORT).show();
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

    public void getUserDetailInfo() {
        userDataSource.getUserDetail(UserBaseDetail.getToken(context), new OnNetRequestListener() {
            @Override
            public void onSuccess(NetRequestResult netRequestResult) {
                UserDetailReturn userDetailReturn = (UserDetailReturn)netRequestResult.getData();
                putCoin(userDetailReturn.getData().getCoins());
                putUID(userDetailReturn.getData().getId());
                putUsername(userDetailReturn.getData().getUsername());
                putFollowerCount(userDetailReturn.getData().getFans_count());
                putFollowingCount(userDetailReturn.getData().getFollowings_count());
                putGender(userDetailReturn.getData().isGender());
                putVIPDeadline(userDetailReturn.getData().getVip());
                userDataSource.getUserAvatar(UserBaseDetail.getUID(context), new OnNetRequestListener() {
                    @Override
                    public void onSuccess(NetRequestResult netRequestResult) {
                        GetOSSUrlReturn getOSSUrlReturn = (GetOSSUrlReturn) netRequestResult.getData();
                        if(getOSSUrlReturn.getData().getFile() == null) {
                            isSuccessLogin.setValue(true);
                            return;
                        }
                        String url = AliyunOSSUtil.getImageUrl(context, getOSSUrlReturn.getData().getGuest_key(),
                                getOSSUrlReturn.getData().getGuest_secret(),
                                getOSSUrlReturn.getData().getSecurity_token(),
                                getOSSUrlReturn.getData().getFile());
                        CustomTarget<Drawable> customTarget = new CustomTarget<Drawable>() {
                            @Override
                            public void onResourceReady(@NonNull Drawable resource, Transition<? super Drawable> transition) {
                                putAvatar(url);
                                isSuccessLogin.setValue(true);
                            }

                            @Override
                            public void onLoadCleared(@Nullable Drawable placeholder) {

                            }
                        };

                        Glide.with(context).load(url).into(customTarget);
                    }

                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onFail() {

                    }

                    @Override
                    public void onFail(String errorMessage) {
                        isSuccessLogin.setValue(false);
                        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
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
                isSuccessLogin.setValue(false);
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void buyCoin(int howMany) {
        userDataSource.buyCoin(UserBaseDetail.getToken(context), howMany, new OnNetRequestListener() {
            @Override
            public void onSuccess(NetRequestResult netRequestResult) {
                putCoin(((BuyCoinReturn)netRequestResult.getData()).getData().getCoins());
                isSuccessBuyCoin.setValue(true);
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

    public void buyVip(int vip, int coins) {
        userDataSource.buyVip(UserBaseDetail.getToken(context), vip, coins, new OnNetRequestListener() {
            @Override
            public void onSuccess(NetRequestResult netRequestResult) {
                putCoin(UserBaseDetail.getCoin(context) - coins);
                putVIPDeadline(((BuyVIPReturn)netRequestResult.getData()).getData().getVip());
                isSuccessBuyVip.setValue(true);
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

    public void getUserSpaceData(int UID){
        userDataSource.getUserBackground(UID, new OnNetRequestListener() {
            @Override
            public void onSuccess(NetRequestResult netRequestResult) {
                GetOSSUrlReturn getOSSUrlReturn = (GetOSSUrlReturn) netRequestResult.getData();
                if(getOSSUrlReturn.getData().getFile() == null) {
                    spaceBackgroundUrl.setValue("");
                    return;
                }
                String url = AliyunOSSUtil.getImageUrl(context, getOSSUrlReturn.getData().getGuest_key(),
                        getOSSUrlReturn.getData().getGuest_secret(),
                        getOSSUrlReturn.getData().getSecurity_token(),
                        getOSSUrlReturn.getData().getFile());
                spaceBackgroundUrl.setValue(url);
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
        userDataSource.getUserAvatar(UID, new OnNetRequestListener() {
            @Override
            public void onSuccess(NetRequestResult netRequestResult) {
                GetOSSUrlReturn getOSSUrlReturn = (GetOSSUrlReturn) netRequestResult.getData();
                if(getOSSUrlReturn.getData().getFile() == null) {
                    spaceAvatarUrl.setValue("");
                    return;
                }
                String url = AliyunOSSUtil.getImageUrl(context, getOSSUrlReturn.getData().getGuest_key(),
                        getOSSUrlReturn.getData().getGuest_secret(),
                        getOSSUrlReturn.getData().getSecurity_token(),
                        getOSSUrlReturn.getData().getFile());
                spaceAvatarUrl.setValue(url);
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
        userDataSource.getUserOpenDetail(UID, new OnNetRequestListener() {
            @Override
            public void onSuccess(NetRequestResult netRequestResult) {
                UserOpenDetailReturn userOpenDetailReturn = (UserOpenDetailReturn) netRequestResult.getData();
                SpaceActivityBean spaceActivityBean1 = new SpaceActivityBean();
                spaceActivityBean1.setLike(userOpenDetailReturn.getData().getLikes() + "\n获赞");
                spaceActivityBean1.setSign(userOpenDetailReturn.getData().getSign());
                spaceActivityBean1.setFollower(userOpenDetailReturn.getData().getFans_count() + "\n粉丝");
                spaceActivityBean1.setFollowing(userOpenDetailReturn.getData().getFollowings_count() + "\n关注");
                spaceActivityBean1.setUID("uid:" + userOpenDetailReturn.getData().getId());
                spaceActivityBean1.setUsername(userOpenDetailReturn.getData().getUsername());
                spaceActivityBean1.setGender(userOpenDetailReturn.getData().isGender());
                spaceActivityBean1.setVip((userOpenDetailReturn).getData().getVip() != null);
                spaceActivityBean.setValue(spaceActivityBean1);
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

    public void uploadUserBackground(String path) {
        File file = new File(path);
        userDataSource.uploadUserBackground(UserBaseDetail.getToken(context), file, new OnNetRequestListener() {
            @Override
            public void onSuccess(NetRequestResult netRequestResult) {

            }

            @Override
            public void onSuccess() {
                spaceBackgroundUrl.setValue(path);
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

    public void uploadUserAvatar(String path) {
        File file = new File(path);
        userDataSource.uploadUserAvatar(UserBaseDetail.getToken(context), file, new OnNetRequestListener() {
            @Override
            public void onSuccess(NetRequestResult netRequestResult) {

            }

            @Override
            public void onSuccess() {
                spaceAvatarUrl.setValue(path);
                userDataSource.getUserAvatar(UserBaseDetail.getUID(context), new OnNetRequestListener() {
                    @Override
                    public void onSuccess(NetRequestResult netRequestResult) {
                        GetOSSUrlReturn getOSSUrlReturn = (GetOSSUrlReturn) netRequestResult.getData();
                        if(getOSSUrlReturn.getData().getFile() == null) {
                            return;
                        }
                        String url = AliyunOSSUtil.getImageUrl(context, getOSSUrlReturn.getData().getGuest_key(),
                                getOSSUrlReturn.getData().getGuest_secret(),
                                getOSSUrlReturn.getData().getSecurity_token(),
                                getOSSUrlReturn.getData().getFile());
                        CustomTarget<Drawable> customTarget = new CustomTarget<Drawable>() {
                            @Override
                            public void onResourceReady(@NonNull Drawable resource, Transition<? super Drawable> transition) {
                                putAvatar(url);
                            }

                            @Override
                            public void onLoadCleared(@Nullable Drawable placeholder) {

                            }
                        };

                        Glide.with(context).load(url).into(customTarget);
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

            @Override
            public void onFail() {

            }

            @Override
            public void onFail(String errorMessage) {
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setGender(boolean gender) {
        userDataSource.setGender(UserBaseDetail.getToken(context), gender, new OnNetRequestListener() {
            @Override
            public void onSuccess(NetRequestResult netRequestResult) {

            }

            @Override
            public void onSuccess() {
                isSetGenderSuccess.setValue(gender);
                putGender(gender);
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

    public void rename(String username) {
        userDataSource.rename(UserBaseDetail.getToken(context), username, new OnNetRequestListener() {
            @Override
            public void onSuccess(NetRequestResult netRequestResult) {
                RenameReturn renameReturn = (RenameReturn) netRequestResult.getData();
                putCoin(renameReturn.getData().getCoins());
                putUsername(username);
                EventBus.getDefault().post(username);
                newUsername.setValue(username);
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

    public void uploadSign(String sign, String signEdit) {
        userDataSource.uploadSign(UserBaseDetail.getToken(context), sign, new OnNetRequestListener() {
            @Override
            public void onSuccess(NetRequestResult netRequestResult) {

            }

            @Override
            public void onSuccess() {
                SignBean signBean = new SignBean();
                signBean.setSign(sign);
                signBean.setSignEdit(signEdit);
                newSign.setValue(signBean);
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

    public void follow(ListFollowReturn.DataBean.ListBean bean) {
        userDataSource.follow(UserBaseDetail.getToken(context), bean.getId(), new OnNetRequestListener() {
            @Override
            public void onSuccess(NetRequestResult netRequestResult) {
                bean.setIs_followed(true);
                followedList.setValue(followedList.getValue());
                fanList.setValue(fanList.getValue());
                putFollowingCount(getFollowingCount() + 1);
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

    public void unFollow(ListFollowReturn.DataBean.ListBean bean) {
        userDataSource.unFollow(UserBaseDetail.getToken(context), bean.getId(), new OnNetRequestListener() {
            @Override
            public void onSuccess(NetRequestResult netRequestResult) {
                bean.setIs_followed(false);
                followedList.setValue(followedList.getValue());
                fanList.setValue(fanList.getValue());
                putFollowingCount(getFollowingCount() - 1);
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

    public void follow(int id) {
        userDataSource.follow(UserBaseDetail.getToken(context), id, new OnNetRequestListener() {
            @Override
            public void onSuccess(NetRequestResult netRequestResult) {
                isFollowed.setValue(true);
                putFollowingCount(getFollowingCount() + 1);
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

    public void unFollow(int id) {
        userDataSource.unFollow(UserBaseDetail.getToken(context), id, new OnNetRequestListener() {
            @Override
            public void onSuccess(NetRequestResult netRequestResult) {
                isFollowed.setValue(false);
                putFollowingCount(getFollowingCount() - 1);
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

    public void isFollowed(int id) {
        userDataSource.isFollowed(UserBaseDetail.getToken(context), id, new OnNetRequestListener() {
            @Override
            public void onSuccess(NetRequestResult netRequestResult) {
                IsFollowedReturn isFollowedReturn = (IsFollowedReturn) netRequestResult.getData();
                isFollowed.setValue(isFollowedReturn.getData().isIs_followed());
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

    public void listFollowings(int id) {
        userDataSource.listFollowings(UserBaseDetail.getToken(context), id, new OnNetRequestListener() {
            @Override
            public void onSuccess(NetRequestResult netRequestResult) {
                ListFollowReturn listFollowReturn = (ListFollowReturn) netRequestResult.getData();
                ArrayList<ListFollowReturn.DataBean.ListBean> list = new ArrayList<>(listFollowReturn.getData().getList());
                followedList.postValue(list);
                for (ListFollowReturn.DataBean.ListBean bean : list) {
                    userDataSource.getUserAvatar(bean.getId(), new OnNetRequestListener() {
                        @Override
                        public void onSuccess(NetRequestResult netRequestResult) {
                            GetOSSUrlReturn getOSSUrlReturn = (GetOSSUrlReturn) netRequestResult.getData();
                            if(getOSSUrlReturn.getData().getFile() == null) {
                                return;
                            }
                            String url = AliyunOSSUtil.getImageUrl(context, getOSSUrlReturn.getData().getGuest_key(),
                                    getOSSUrlReturn.getData().getGuest_secret(),
                                    getOSSUrlReturn.getData().getSecurity_token(),
                                    getOSSUrlReturn.getData().getFile());
                            bean.setAvatar(url);
                            followedList.postValue(followedList.getValue());
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

            }

            @Override
            public void onFail() {

            }

            @Override
            public void onFail(String errorMessage) {

            }
        });
    }

    public void listFan(int id) {
        userDataSource.listFan(UserBaseDetail.getToken(context), id, new OnNetRequestListener() {
            @Override
            public void onSuccess(NetRequestResult netRequestResult) {
                ListFollowReturn listFollowReturn = (ListFollowReturn) netRequestResult.getData();
                ArrayList<ListFollowReturn.DataBean.ListBean> list = new ArrayList<>(listFollowReturn.getData().getList());
                fanList.postValue(list);
                for (ListFollowReturn.DataBean.ListBean bean : list) {
                    userDataSource.getUserAvatar(bean.getId(), new OnNetRequestListener() {
                        @Override
                        public void onSuccess(NetRequestResult netRequestResult) {
                            GetOSSUrlReturn getOSSUrlReturn = (GetOSSUrlReturn) netRequestResult.getData();
                            if(getOSSUrlReturn.getData().getFile() == null) {
                                return;
                            }
                            String url = AliyunOSSUtil.getImageUrl(context, getOSSUrlReturn.getData().getGuest_key(),
                                    getOSSUrlReturn.getData().getGuest_secret(),
                                    getOSSUrlReturn.getData().getSecurity_token(),
                                    getOSSUrlReturn.getData().getFile());
                            bean.setAvatar(url);
                            fanList.postValue(fanList.getValue());
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

            }

            @Override
            public void onFail() {

            }

            @Override
            public void onFail(String errorMessage) {

            }
        });
    }

    private void putUsername(String username) {
        SPUtil.put(context, SPConstant.USERNAME, username);
    }

    private void putCoin(int coin) {
        SPUtil.put(context, SPConstant.COIN, coin);
    }

    private void putUID(int uid) {
        SPUtil.put(context, SPConstant.UID, uid);
    }

    private void putToken(String token) {
        SPUtil.put(context, SPConstant.TOKEN, token);
    }

    private void putFollowerCount(int followerCount) {
        SPUtil.put(context, SPConstant.FOLLOWER, followerCount);
    }

    private int getFollowerCount() {
        return (int) SPUtil.get(context, SPConstant.FOLLOWER, 0);
    }

    private void putFollowingCount(int followerCount) {
        SPUtil.put(context, SPConstant.FOLLOWING, followerCount);
    }

    private int getFollowingCount() {
        return (int) SPUtil.get(context, SPConstant.FOLLOWING, 0);
    }

    //男：false 女：true
    private void putGender(Boolean genderBoolean) {
        SPUtil.put(context, SPConstant.GENDER, genderBoolean);
    }

    private void putVIPDeadline(String ddl){
        if(ddl == null) {
            SPUtil.put(context, SPConstant.VIP_DDL, "");
        } else {
            SPUtil.put(context, SPConstant.VIP_DDL, ddl);
        }
    }

    private void putAvatar(String url) {
        String filename = EncryptUtil.getGlide4_SafeKey(url);
        SPUtil.put(context, SPConstant.AVATAR, filename);
    }

    public Context getContext() {
        return context;
    }

    public MutableLiveData<SpaceActivityBean> getSpaceActivityBean() {
        return spaceActivityBean;
    }

    public MutableLiveData<Boolean> getIsSuccessBuyCoin() {
        return isSuccessBuyCoin;
    }

    public MutableLiveData<UserDetailReturn> getUserDetail() {
        return userDetail;
    }

    public MutableLiveData<Boolean> getIsValid() {
        return isValid;
    }

    public MutableLiveData<Boolean> getIsSuccessLogin() {
        return isSuccessLogin;
    }

    public MutableLiveData<LoginSend> getLoginInfo() {
        return loginInfo;
    }

    public MutableLiveData<String> getSpaceBackgroundUrl() {
        return spaceBackgroundUrl;
    }

    public MutableLiveData<String> getSpaceAvatarUrl() {
        return spaceAvatarUrl;
    }

    public MutableLiveData<Boolean> getIsSetGenderSuccess() {
        return isSetGenderSuccess;
    }

    public MutableLiveData<String> getNewUsername() {
        return newUsername;
    }

    public MutableLiveData<SignBean> getNewSign() {
        return newSign;
    }

    public MutableLiveData<Boolean> getIsSuccessBuyVip() {
        return isSuccessBuyVip;
    }

    public MutableLiveData<Boolean> getIsFollowed() {
        return isFollowed;
    }

    public MutableLiveData<List<ListFollowReturn.DataBean.ListBean>> getFollowedList() {
        return followedList;
    }

    public MutableLiveData<List<ListFollowReturn.DataBean.ListBean>> getFanList() {
        return fanList;
    }
}
