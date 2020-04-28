package com.erning.campusorder.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.erning.campusorder.R;
import com.erning.campusorder.presenter.HomePresenter;
import com.erning.campusorder.util.PresenterFragment;

import butterknife.BindView;

public class HomeFragment extends PresenterFragment<HomePresenter> {
    @BindView(R.id.img_title_back) ImageView img_back;
    @BindView(R.id.text_title) TextView text_title;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_home;
    }

    @Override
    protected HomePresenter initPresenter() {
        return new HomePresenter(this);
    }

    @Override
    protected void initData() {
        img_back.setVisibility(View.INVISIBLE);
        text_title.setText("点餐");
    }
}
