package com.erning.campusorder.presenter;

import androidx.annotation.NonNull;

import com.erning.campusorder.entity.Produce;
import com.erning.campusorder.fragment.HomeFragment;
import com.erning.campusorder.util.CallBack;
import com.erning.campusorder.util.FragmentPresenter;
import com.erning.common.net.bean.result.JsonRst;

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
        service.getProduceList(page,limit).enqueue(new CallBack<JsonRst>() {
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
        List<Produce> produces = new ArrayList<>();
        for (Produce produce:data){
            total += produce.getCount()*Double.parseDouble(produce.getPrice());
            if (produce.getCount() > 0){
                // 找到所有数量不为0的餐品
                produces.add(produce);
            }
        }
        if (produces.size() > 0){
            // TODO 下单 getView().orderSuccess(id)
        }
    }
}
