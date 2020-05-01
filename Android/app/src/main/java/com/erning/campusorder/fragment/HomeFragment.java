package com.erning.campusorder.fragment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.erning.campusorder.R;
import com.erning.campusorder.activity.OrderActivity;
import com.erning.campusorder.activity.ProductDetailActivity;
import com.erning.campusorder.entity.Produce;
import com.erning.campusorder.presenter.HomePresenter;
import com.erning.campusorder.util.PresenterFragment;
import com.erning.common.adapter.BaseListAdapter;
import com.facebook.drawee.view.SimpleDraweeView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeFragment extends PresenterFragment<HomePresenter> {
    private Adapter adapter = new Adapter();

    @BindView(R.id.img_title_back) ImageView img_back;
    @BindView(R.id.text_title) TextView text_title;
    @BindView(R.id.refresh_home) SmartRefreshLayout refresh_home;
    @BindView(R.id.list_home) ListView list_home;
    @BindView(R.id.text_home_money) TextView text_money;

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

        list_home.setAdapter(adapter);
        presenter.init();
        // 加载更多数据
        refresh_home.setOnLoadMoreListener(refreshLayout -> presenter.loadMore());
        // 下拉刷新
        refresh_home.setOnRefreshListener(refreshLayout -> presenter.refresh());
        list_home.setOnItemClickListener((parent, view, position, id) -> {
            Produce produce = adapter.getItem(position);
            Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
            intent.putExtra("item",produce);
            startActivity(intent);
        });
    }

    public void setData(List<Produce> products) {
        List<Produce> oldData = adapter.getData();
        if (oldData != null){
            // 保证刷新数据时已选的商品数量不丢失
            for (Produce produce:oldData){
                for (Produce p:products){
                    if (produce.getId() == p.getId()){
                        // 同一个商品，数量挪过去
                        p.setCount(produce.getCount());
                    }
                }
            }
        }
        refresh_home.finishLoadMore();
        refresh_home.finishRefresh();
        adapter.setData(products);
    }

    public void addData(List<Produce> products) {
        refresh_home.finishLoadMore();
        refresh_home.finishRefresh();
        adapter.addData(products);
    }

    public void orderSuccess(int id){
        Intent intent = new Intent(getActivity(), OrderActivity.class);
        intent.putExtra("id",id);
        startActivity(intent);
    }

    public void noMore() {
        refresh_home.setNoMoreData(true);
    }

    @OnClick(R.id.btn_home_order)
    public void order(){
        presenter.order(adapter.getData());
    }

    public void error() {
        refresh_home.finishLoadMore();
        refresh_home.finishRefresh();
    }

    class Adapter extends BaseListAdapter<Produce>{
        private View.OnClickListener onClickAdd = v -> {
            // 获取当前点击的是哪一项
            Produce item = (Produce) v.getTag();
            item.setCount(item.getCount()+1);
            // 数据改变了，刷新界面
            notifyDataSetChanged();
            // 计算价格
            calculator();
        };
        private View.OnClickListener onClickSub = v -> {
            Produce item = (Produce) v.getTag();
            item.setCount(item.getCount()-1);
            notifyDataSetChanged();
        };

        Adapter() {
            super(new ArrayList<>());
        }

        private void calculator(){
            float total = 0;
            for (Produce produce:dataList){
                total += produce.getCount()*Double.parseDouble(produce.getPrice());
            }
            text_money.setText("￥ "+total);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView==null ? LayoutInflater.from(getActivity()).inflate(R.layout.item_product,parent,false) : convertView;
            SimpleDraweeView image = view.findViewById(R.id.img_item_product);
            TextView name = view.findViewById(R.id.text_item_product_name);
            TextView desc = view.findViewById(R.id.text_item_product_desc);
            ImageView add = view.findViewById(R.id.img_item_product_add);
            TextView count = view.findViewById(R.id.text_item_product_count);
            TextView price = view.findViewById(R.id.text_item_product_price);
            ImageView sub = view.findViewById(R.id.img_item_product_sub);
            Produce item = getItem(position);

            image.setImageURI(item.getImg());
            name.setText(item.getName());
            desc.setText(item.getDescription());
            count.setText(""+item.getCount());
            price.setText("￥ "+item.getPrice());
            add.setTag(item);
            add.setOnClickListener(onClickAdd);
            sub.setTag(item);
            sub.setOnClickListener(onClickSub);
            return view;
        }
    }
}
