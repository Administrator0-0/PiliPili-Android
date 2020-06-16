package com.example.pilipili_android.view_model;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.pilipili_android.bean.LoginReturn;
import com.example.pilipili_android.bean.LoginSend;
import com.example.pilipili_android.bean.NetRequestResult;
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
        String localToken = getToken();
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

    private String getToken() {
        return (String) SPUtil.get(context, SPConstant.TOKEN, "");
    }

    public void login(String email, String password) {
        if(email.trim().equals("")){
            Toast.makeText(context, "请输入用户名", Toast.LENGTH_SHORT).show();
        } else if (password.trim().equals("")) {
            Toast.makeText(context, "请输入密码", Toast.LENGTH_SHORT).show();
        } else {
            userDataSource.login(email, password, new OnNetRequestListener() {
                @Override
                public void onSuccess(NetRequestResult netRequestResult) {
                    SPUtil.put(context, SPConstant.TOKEN, ((LoginReturn)netRequestResult.getData()).getData().getToken());
                    SPUtil.put(context, SPConstant.USERNAME, ((LoginReturn)netRequestResult.getData()).getData().getUsername());
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

}
