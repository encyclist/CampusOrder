package com.erning.campusorder.fragment;

import android.content.Intent;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.erning.campusorder.Application;
import com.erning.campusorder.R;
import com.erning.campusorder.activity.ForgetActivity;
import com.erning.campusorder.activity.ResetActivity;
import com.erning.campusorder.entity.User;
import com.erning.campusorder.presenter.MyPresenter;
import com.erning.campusorder.util.PresenterFragment;

import butterknife.BindView;
import butterknife.OnClick;

public class MyFragment extends PresenterFragment<MyPresenter> {
    @BindView(R.id.text_my_name) TextView txt_phone;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_my;
    }

    @Override
    protected MyPresenter initPresenter() {
        return new MyPresenter(this);
    }

    @Override
    protected void initData() {
        presenter.init();
    }

    public void showUserInfo(User user) {
        txt_phone.setText(user.getPhone());
    }

    @OnClick(R.id.relative_my_clear)
    public void clear(){
        new AlertDialog.Builder(getActivity())
                .setTitle("陛下三思")
                .setMessage("清空缓存会导致加载变慢，继续吗？")
                .setPositiveButton("确定", (dialog, which) -> toast("缓存已清空"))
                .setNegativeButton("取消",null)
                .show();
    }

    @OnClick(R.id.relative_my_logout)
    public void logout(){
        new AlertDialog.Builder(getActivity())
                .setTitle("陛下三思")
                .setMessage("退出登录后要重新登陆，确定吗？")
                .setPositiveButton("确定", (dialog, which) -> Application.logout())
                .setNegativeButton("取消",null)
                .show();
    }

    @OnClick(R.id.relative_my_pwd)
    public void reset(){
        Intent intent = new Intent(getActivity(), ForgetActivity.class);
        intent.putExtra("action","reset");
        startActivity(intent);
    }
}
