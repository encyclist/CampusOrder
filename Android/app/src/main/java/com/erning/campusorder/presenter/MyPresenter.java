package com.erning.campusorder.presenter;

import androidx.annotation.NonNull;

import com.erning.common.net.bean.User;
import com.erning.campusorder.fragment.MyFragment;
import com.erning.campusorder.util.CallBack;
import com.erning.campusorder.util.FragmentPresenter;
import com.erning.common.net.bean.JsonRst;

public class MyPresenter extends FragmentPresenter<MyFragment> {
    public MyPresenter(MyFragment myFragment) {
        super(myFragment);
    }

    @Override
    public void init() {
        service.getUserInfo(getUserId()).enqueue(new CallBack<JsonRst>() {
            @Override
            protected void result(@NonNull JsonRst body) {
                User user = body.getData().toJavaObject(User.class);
                getView().showUserInfo(user);
            }
        });
    }
}
