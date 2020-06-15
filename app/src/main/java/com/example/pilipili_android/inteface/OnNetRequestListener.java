package com.example.pilipili_android.inteface;

import com.example.pilipili_android.bean.NetRequestResult;

public interface OnNetRequestListener {

    void onSuccess(NetRequestResult netRequestResult);

    void onSuccess();

    void onFail(int errorCode);

    void onFail();

    void onFail(String errorMessage);
}
