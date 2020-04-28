package com.erning.campusorder.presenter;

import com.erning.campusorder.activity.LoginActivity;
import com.erning.campusorder.util.ActivityPresenter;

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
    }

    public void register(String phone, String password, String name) {
        if(phone.isEmpty() || password.isEmpty() || name.isEmpty()){
            getView().showError("任何信息都不能为空!");
            return;
        }
    }
}
