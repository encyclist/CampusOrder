package com.erning.campusorder.fragment;

import com.erning.campusorder.R;
import com.erning.campusorder.presenter.MyPresenter;
import com.erning.campusorder.util.PresenterFragment;

public class MyFragment extends PresenterFragment<MyPresenter> {

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

    }
}
