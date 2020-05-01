package com.erning.campusorder.presenter;

import androidx.annotation.NonNull;

import com.erning.campusorder.activity.LoginActivity;
import com.erning.campusorder.util.ActivityPresenter;
import com.erning.campusorder.util.CallBack;
import com.erning.common.net.bean.result.JsonRst;

public class LoginPresenter extends ActivityPresenter<LoginActivity> {
    public LoginPresenter(LoginActivity loginActivity) {
        super(loginActivity);
    }

    @Override
    public void init() {

    }

    public void login(String phone, String password) {
        if(phone.isEmpty() || password.isEmpty()){
            getView().showError("用户名或密码不能为空!");
            return;
        }

        service.login(phone, password).enqueue(new CallBack<JsonRst>() {
            @Override
            protected void result(@NonNull JsonRst body) {
                String id = body.getData().getString("id");
                String tel = body.getData().getString("tel");
                getView().loginSuccess(id,tel);
            }
        });
    }

    public void register(String phone, String password, String name) {
        if(phone.isEmpty() || password.isEmpty() || name.isEmpty()){
            getView().showError("任何信息都不能为空!");
            return;
        }
        if(!password.equals(name)){
            getView().showError("两次输入的密码不一致!");
            return;
        }

        service.register(phone, password).enqueue(new CallBack<JsonRst>() {
            @Override
            protected void result(@NonNull JsonRst body) {
                String id = body.getData().getString("id");
                String tel = body.getData().getString("tel");
                getView().loginSuccess(id,tel);
            }
        });
    }
}
