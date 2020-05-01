package com.erning.campusorder.presenter;

import androidx.annotation.NonNull;

import com.erning.campusorder.activity.OrderDetailActivity;
import com.erning.campusorder.util.ActivityPresenter;
import com.erning.campusorder.util.CallBack;
import com.erning.common.net.bean.JsonRst;
import com.erning.common.net.bean.Order;

public class OrderDetailPresenter extends ActivityPresenter<OrderDetailActivity> {
    public OrderDetailPresenter(OrderDetailActivity orderDetailActivity) {
        super(orderDetailActivity);
    }

    @Override
    public void init() {

    }

    public void getOrderInfo(int orderId) {
        service.selectOrderById(orderId).enqueue(new CallBack<JsonRst>() {
            @Override
            protected void result(@NonNull JsonRst body) {
                Order order = body.getData().toJavaObject(Order.class);
                getView().showData(order);
            }
        });
    }

    public void deleteOrder(int id) {
        service.deleteOrder(id).enqueue(new CallBack<JsonRst>() {
            @Override
            protected void result(@NonNull JsonRst body) {
                getView().deleteSuccess();
            }
        });
    }

    public void pay(int id) {
        service.payOrder(id).enqueue(new CallBack<JsonRst>() {
            @Override
            protected void result(@NonNull JsonRst body) {
                getView().paySuccess();
                getOrderInfo(id);
            }
        });
    }
}
