package com.erning.campusorder.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.erning.campusorder.R;
import com.erning.campusorder.presenter.OrderPresenter;
import com.erning.campusorder.util.PresenterFragment;

import butterknife.BindView;

public class OrderFragment extends PresenterFragment<OrderPresenter> {
    @BindView(R.id.img_title_back) ImageView img_back;
    @BindView(R.id.text_title) TextView text_title;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_order;
    }

    @Override
    protected void initData() {
        img_back.setVisibility(View.INVISIBLE);
        text_title.setText("我的订单");
    }

    @Override
    protected OrderPresenter initPresenter() {
        return new OrderPresenter(this);
    }
}
