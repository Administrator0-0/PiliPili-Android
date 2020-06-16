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
import com.example.pilipili_android.bean.NetRequestResult;
import com.example.pilipili_android.constant.SPConstant;
import com.example.pilipili_android.inteface.OnNetRequestListener;
import com.example.pilipili_android.model.UserDataSource;
import com.example.pilipili_android.util.SPUtil;

import java.util.Objects;

public class UserViewModel extends AndroidViewModel {

    private Context context;

    private UserDataSource userDataSource;

    private MutableLiveData<Boolean> isValid = new MutableLiveData<>();
    private MutableLiveData<Boolean> isSuccessLogin = new MutableLiveData<>();

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

    // 检查邮箱合法
    private boolean isEmailValid(String email) {
        if (email == null) {
            return false;
        }
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    // 检查密码合法
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }
}
