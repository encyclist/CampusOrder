package com.erning.campusorder.presenter;

import androidx.annotation.NonNull;

import com.erning.common.net.bean.Order;
import com.erning.common.net.bean.OrderProduct;
import com.erning.common.net.bean.Page;
import com.erning.common.net.bean.Produce;
import com.erning.campusorder.fragment.HomeFragment;
import com.erning.campusorder.util.CallBack;
import com.erning.campusorder.util.FragmentPresenter;
import com.erning.common.net.bean.JsonRst;

import java.util.ArrayList;
import java.util.List;

public class HomePresenter extends FragmentPresenter<HomeFragment> {
    private int page = 0;
    private int limit = 20;

    public HomePresenter(HomeFragment homeFragment) {
        super(homeFragment);
    }

    @Override
    public void init() {
        getData();
    }

    private void getData(){
        page++;
        service.getProduceList(new Page(page,limit)).enqueue(new CallBack<JsonRst>() {
            @Override
            protected void result(@NonNull JsonRst body) {
                List<Produce> products = body.getData().getJSONArray("list").toJavaList(Produce.class);
                if (page == 1){
                    getView().setData(products);
                }else{
                    getView().addData(products);
                }
                if (products.size() < limit){
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

    public void loadMore() {
        getData();
    }

    public void refresh() {
        page = 0;
        getData();
    }

    public void order(List<Produce> data) {
        if (data == null)
            return;

        float total = 0;
        List<OrderProduct> produces = new ArrayList<>();
        for (Produce produce:data){
            if (produce.getCount() > 0){
                // 找到所有数量不为0的餐品
                total += produce.getCount()*Double.parseDouble(produce.getPrice());
                produces.add(new OrderProduct(produce,produce.getCount()));
            }
        }
        if (produces.size() > 0){
            service.addOrder(new Order(Integer.parseInt(getUserId()),"0",total,produces)).enqueue(new CallBack<JsonRst>() {
                @Override
                protected void result(@NonNull JsonRst body) {
                    Order order = body.getData().toJavaObject(Order.class);
                    getView().orderSuccess(order.getId());
                }
            });
        }
    }
}
