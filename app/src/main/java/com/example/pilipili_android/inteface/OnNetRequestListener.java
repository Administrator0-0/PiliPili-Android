package com.example.pilipili_android.inteface;

import com.example.pilipili_android.bean.netbean.NetRequestResult;

public interface OnNetRequestListener {

    void onSuccess(NetRequestResult netRequestResult);

    void onSuccess();

    void onFail();

    void onFail(String errorMessage);
}
