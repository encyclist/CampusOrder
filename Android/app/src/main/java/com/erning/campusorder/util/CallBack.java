package com.erning.campusorder.util;

import android.util.Log;

import androidx.annotation.NonNull;

import com.erning.campusorder.Application;
import com.erning.common.net.bean.result.BaseResultModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class CallBack<T extends BaseResultModel> implements Callback<T> {
    @Override
    public void onFailure(Call<T> call, Throwable t) {
        if(call.isCanceled()){
            return;
        }
        Log.e("CallBack","网络请求失败"+t.getMessage());
        try {
            error(-1, "Network error");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if(response.isSuccessful()) {
            try{
                if (response.body().code == 0) {
                    result(response.body());
                }else {
                    error(response.body().code, response.body().msg);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            try {
                Log.e("onResponse","Code:"+response.code() + "message:"+response.message() + "body:"+response.errorBody().string());
            } catch (Exception e) {
                Log.e("onResponse","Code:"+response.code() + "message:"+response.message());
            }finally {
                try {
                    error(-1,"Network error");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    protected abstract void result(@NonNull T body);
    protected void error(int code, @NonNull String message){
        Application.showToast(message);
    };
}
