package com.erning.campusorder.presenter;

import androidx.annotation.NonNull;

import com.erning.campusorder.activity.ForgetActivity;
import com.erning.campusorder.util.ActivityPresenter;
import com.erning.campusorder.util.CallBack;
import com.erning.common.net.bean.result.JsonRst;

public class ForgetPresenter extends ActivityPresenter<ForgetActivity> {
    public ForgetPresenter(ForgetActivity forgetActivity) {
        super(forgetActivity);
    }

    @Override
    public void init() {

    }

    public void forget(String phone, String code, String pwd) {
        if(phone.isEmpty() || code.isEmpty() || pwd.isEmpty()){
            getView().showError("任何信息都不能为空!");
            return;
        }

        service.forget(phone,code,pwd).enqueue(new CallBack<JsonRst>() {
            @Override
            protected void result(@NonNull JsonRst body) {
                getView().forgetSuccess();
            }
        });
    }
}
