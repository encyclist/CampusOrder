package com.erning.campusorder.fragment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.erning.campusorder.R;
import com.erning.campusorder.activity.OrderDetailActivity;
import com.erning.campusorder.presenter.OrderPresenter;
import com.erning.campusorder.util.PresenterFragment;
import com.erning.common.adapter.BaseListAdapter;
import com.erning.common.net.bean.Order;
import com.erning.common.net.bean.Produce;
import com.erning.common.utils.MD5_Calc;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class OrderFragment extends PresenterFragment<OrderPresenter> {
    private Adapter adapter = new Adapter();

    @BindView(R.id.img_title_back) ImageView img_back;
    @BindView(R.id.text_title) TextView text_title;
    @BindView(R.id.list_order) ListView list_order;
    @BindView(R.id.refresh_order) SmartRefreshLayout refresh_order;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_order;
    }

    @Override
    protected void initData() {
        img_back.setVisibility(View.INVISIBLE);
        text_title.setText("我的订单");
        list_order.setAdapter(adapter);
        refresh_order.setOnLoadMoreListener(refreshLayout -> presenter.getData());
        refresh_order.setOnRefreshListener(refreshLayout -> presenter.getNew());
        list_order.setOnItemClickListener((parent, view, position, id) -> {
            Order order = adapter.getItem(position);
            Intent intent = new Intent(getActivity(), OrderDetailActivity.class);
            intent.putExtra("id",order.getId());
            startActivity(intent);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.getNew();
    }

    @Override
    protected OrderPresenter initPresenter() {
        return new OrderPresenter(this);
    }

    public void error() {
        refresh_order.finishLoadMore();
        refresh_order.finishRefresh();
    }

    public void noMore() {
        refresh_order.setNoMoreData(true);
    }

    public void setData(List<Order> orders) {
        adapter.setData(orders);
        error();
    }

    public void addData(List<Order> orders) {
        adapter.addData(orders);
        error();
    }

    class Adapter extends BaseListAdapter<Order>{

        Adapter() {
            super(new ArrayList<>());
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView==null ? LayoutInflater.from(getActivity()).inflate(R.layout.item_order,parent,false) : convertView;
            TextView state = view.findViewById(R.id.text_item_order_state);
            TextView time = view.findViewById(R.id.text_item_order_time);
            TextView products = view.findViewById(R.id.text_item_order_products);
            TextView no = view.findViewById(R.id.text_item_order_no);
            Order item = getItem(position);

            if ("0".equals(item.getState())){
                state.setText("待支付");
                state.setTextColor(ContextCompat.getColor(getActivity(),R.color.red));
            }else{
                state.setText("已完成");
                state.setTextColor(ContextCompat.getColor(getActivity(),R.color.colorPrimary));
            }
            time.setText("下单时间：" + item.getCreate_time());
            products.setText("总价：￥" + item.getPrice());
            no.setText("订单号：" + MD5_Calc.Md5(item.getId()+item.getCreate_time()));

            return view;
        }
    }
}
