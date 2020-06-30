package com.example.pilipili_android.view_model;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.pilipili_android.bean.netbean.DanmukuListReturn;
import com.example.pilipili_android.bean.netbean.DanmukuSend;
import com.example.pilipili_android.bean.netbean.NetRequestResult;
import com.example.pilipili_android.inteface.OnNetRequestListener;
import com.example.pilipili_android.model.DanmukuDataSource;

public class DanmukuViewModel extends AndroidViewModel {

    private Context context;
    private DanmukuDataSource dataSource;
    private MutableLiveData<DanmukuListReturn.DataBean> danmukuList = new MutableLiveData<>();

    public DanmukuViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
        dataSource = new DanmukuDataSource();
        danmukuList.setValue(new DanmukuListReturn.DataBean());
    }

    public MutableLiveData<DanmukuListReturn.DataBean> getDanmukuList() {
        return danmukuList;
    }

    public void postDanmuku(int pv, DanmukuSend danmuku) {
        dataSource.postDanmuku(pv, danmuku, UserBaseDetail.getToken(context), new OnNetRequestListener() {
            @Override
            public void onSuccess(NetRequestResult netRequestResult) {
                // ignore
            }

            @Override
            public void onSuccess() {
                danmukuList.postValue(danmukuList.getValue());
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

    public void getDanmuku(int pv) {
        dataSource.getDanmuku(pv, new OnNetRequestListener() {
            @Override
            public void onSuccess(NetRequestResult netRequestResult) {
                DanmukuListReturn danmukuListReturn = (DanmukuListReturn) netRequestResult.getData();
                danmukuList.postValue(danmukuListReturn.getData());
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


}
