package com.erning.campusorder.activity;

import android.widget.EditText;
import android.widget.TextView;

import com.erning.campusorder.R;
import com.erning.campusorder.presenter.ForgetPresenter;
import com.erning.campusorder.util.PresenterActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class ForgetActivity extends PresenterActivity<ForgetPresenter> {
    @BindView(R.id.text_title) TextView title;
    @BindView(R.id.edit_forget_phone) EditText edit_phone;
    @BindView(R.id.edit_forget_code) EditText edit_code;
    @BindView(R.id.edit_forget_pwd) EditText edit_pwd;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_forget;
    }

    @Override
    protected ForgetPresenter initPresenter() {
        return new ForgetPresenter(this);
    }

    @OnClick(R.id.img_title_back)
    void back(){
        finish();
    }
    @OnClick(R.id.btn_forget_getCode)
    void getCode(){
        toast("123456");
    }
    @OnClick(R.id.btn_forget_done)
    void done(){
        String phone = edit_phone.getText().toString();
        String code = edit_code.getText().toString();
        String pwd = edit_pwd.getText().toString();

        presenter.forget(phone,code,pwd);
    }

    @Override
    protected void initControls() {
        super.initControls();

        title.setText("找回密码");
    }

    public void forgetSuccess() {
        toast("密码修改成功!");
        finish();
    }
}