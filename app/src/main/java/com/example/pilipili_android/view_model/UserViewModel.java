package com.example.pilipili_android.view_model;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.pilipili_android.bean.BuyCoinReturn;
import com.example.pilipili_android.bean.LoginReturn;
import com.example.pilipili_android.bean.LoginSend;
import com.example.pilipili_android.bean.NetRequestResult;
import com.example.pilipili_android.bean.UserDetailReturn;
import com.example.pilipili_android.constant.SPConstant;
import com.example.pilipili_android.inteface.OnNetRequestListener;
import com.example.pilipili_android.model.UserDataSource;
import com.example.pilipili_android.util.EncryptUtil;
import com.example.pilipili_android.util.SPUtil;

import java.util.Objects;

public class UserViewModel extends AndroidViewModel {

    private Context context;

    private UserDataSource userDataSource;

    private MutableLiveData<Boolean> isValid = new MutableLiveData<>();
    private MutableLiveData<Boolean> isSuccessLogin = new MutableLiveData<>();
    private MutableLiveData<LoginSend> loginInfo = new MutableLiveData<>();
    private MutableLiveData<UserDetailReturn> userDetail = new MutableLiveData<>();
    private MutableLiveData<Boolean> isSuccessBuyCoin = new MutableLiveData<>();

    public MutableLiveData<LoginSend> getLoginInfo() {
        return loginInfo;
    }

    public UserViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
        userDataSource = new UserDataSource();
    }

    public MutableLiveData<Boolean> getIsValid() {
        return isValid;
    }

    public MutableLiveData<Boolean> getIsSuccessLogin() {
        return isSuccessLogin;
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
                            isSuccessLogin.setValue(true);
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
                    isSuccessLogin.setValue(false);
                    Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void register(String email, String username, String password) {
        if(username.trim().equals("")){
            Toast.makeText(context, "用户名不能为空", Toast.LENGTH_SHORT).show();
        } else if(email.trim().equals("")) {
            Toast.makeText(context, "邮箱不能为空", Toast.LENGTH_SHORT).show();
        } else if(password.trim().equals("")) {
            Toast.makeText(context, "密码不能为空", Toast.LENGTH_SHORT).show();
        } else if(!EncryptUtil.isUsernameValid(username)) {
            Toast.makeText(context, "用户名无效", Toast.LENGTH_SHORT).show();
        } else if (!EncryptUtil.isEmailValid(email)) {
            Toast.makeText(context, "邮箱无效", Toast.LENGTH_SHORT).show();
        } else if (!EncryptUtil.isPasswordValid(password)) {
            Toast.makeText(context, "密码无效", Toast.LENGTH_SHORT).show();
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

    //不对外提供详细信息，全部由SP从本地获取
    public void getUserDetailInfo() {
        userDataSource.getUserDetail(UserBaseDetail.getToken(context), new OnNetRequestListener() {
            @Override
            public void onSuccess(NetRequestResult netRequestResult) {
                UserDetailReturn userDetailReturn = (UserDetailReturn)netRequestResult.getData();
                userDetail.setValue(userDetailReturn);
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

    public MutableLiveData<Boolean> getIsSuccessBuyCoin() {
        return isSuccessBuyCoin;
    }

    public MutableLiveData<UserDetailReturn> getUserDetail() {
        return userDetail;
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

    private void putFollowingCount(int followerCount) {
        SPUtil.put(context, SPConstant.FOLLOWING, followerCount);
    }

    public Context getContext() {
        return context;
    }
}
