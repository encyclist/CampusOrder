package com.erning.campusorder.presenter;

import androidx.annotation.NonNull;

import com.erning.campusorder.fragment.OrderFragment;
import com.erning.campusorder.util.CallBack;
import com.erning.campusorder.util.FragmentPresenter;
import com.erning.common.net.bean.JsonRst;
import com.erning.common.net.bean.Order;

import java.util.List;

public class OrderPresenter extends FragmentPresenter<OrderFragment> {
    private int page = 0;
    private int limit = 20;

    public OrderPresenter(OrderFragment orderFragment) {
        super(orderFragment);
    }

    @Override
    public void init() {

    }

    public void getData(){
        page++;
        service.getOrderList(page,limit,Integer.parseInt(getUserId())).enqueue(new CallBack<JsonRst>() {
            @Override
            protected void result(@NonNull JsonRst body) {
                List<Order> orders = body.getData().getJSONArray("list").toJavaList(Order.class);
                if (page == 1){
                    getView().setData(orders);
                }else{
                    getView().addData(orders);
                }
                if (orders.size() < limit){
                    getView().noMore();
                }
            }
            @Override
            protected void error(int code, @NonNull String message) {
                super.error(code, message);
                page--;
                getView().error();
            }
        });
    }

    public void getNew(){
        page = 0;
        getData();
    }
}
