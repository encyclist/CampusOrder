package com.erning.campusorder.activity;

import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.erning.campusorder.R;
import com.erning.campusorder.presenter.LoginPresenter;
import com.erning.campusorder.util.PresenterActivity;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends PresenterActivity<LoginPresenter> {
    private String action = "login";

    @BindView(R.id.edit_login_phone) EditText edit_phone;
    @BindView(R.id.edit_login_password) EditText edit_password;
    @BindView(R.id.edit_login_name) EditText edit_name;
    @BindView(R.id.view4) View view;
    @BindView(R.id.text_login_register) TextView text_change;
    @BindView(R.id.text_login_forget) TextView text_forget;
    @BindView(R.id.btn_login_login) QMUIRoundButton btn_login;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initControls() {
        super.initControls();
        QMUIStatusBarHelper.translucent(this);
    }

    @Override
    protected LoginPresenter initPresenter() {
        return new LoginPresenter(this);
    }

    @OnClick(R.id.btn_login_login)
    void login(){
        String phone = edit_phone.getText().toString();
        String password = edit_password.getText().toString();
        if ("login".equals(action)){
            presenter.login(phone,password);
        }else if ("register".equals(action)){
            String name = edit_name.getText().toString();
            presenter.register(phone,password,name);
        }
    }

    @OnClick(R.id.text_login_forget)
    void forget(){
        startActivity(ForgetActivity.class);
    }

    @OnClick(R.id.text_login_register)
    void register(){
        if ("login".equals(action)){
            action = "register";
            text_change.setText("已有帐号登录");
            btn_login.setText("注册");
            view.setVisibility(View.VISIBLE);
            edit_name.setVisibility(View.VISIBLE);
            text_forget.setVisibility(View.GONE);
        }else{
            action = "login";
            text_change.setText("注册新账号");
            btn_login.setText("登录");
            view.setVisibility(View.GONE);
            edit_name.setVisibility(View.GONE);
            text_forget.setVisibility(View.VISIBLE);
        }
    }

    public void loginSuccess(String id) {
        putSpString("id",id);
        startActivity(MainActivity.class);
        this.finish();
    }
}
