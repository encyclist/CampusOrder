package com.erning.campusorder.activity;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.erning.campusorder.R;
import com.erning.campusorder.presenter.OrderDetailPresenter;
import com.erning.campusorder.util.PresenterActivity;
import com.erning.common.adapter.BaseListAdapter;
import com.erning.common.net.bean.Order;
import com.erning.common.net.bean.Produce;
import com.facebook.drawee.view.SimpleDraweeView;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class OrderDetailActivity extends PresenterActivity<OrderDetailPresenter> {
    private Adapter adapter = new Adapter();
    private int orderId = -1;

    @BindView(R.id.text_title) TextView text_title;
    @BindView(R.id.text_orderDetail_total) TextView text_total;
    @BindView(R.id.list_orderDetail) ListView list_list;
    @BindView(R.id.text_title_text) TextView text_cancel;
    @BindView(R.id.text_orderDetail_time) TextView text_time;
    @BindView(R.id.text_orderDetail_state) TextView text_state;
    @BindView(R.id.btn_orderDetail_pay) QMUIRoundButton btn_pay;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_order_detail;
    }

    @Override
    protected OrderDetailPresenter initPresenter() {
        return new OrderDetailPresenter(this);
    }

    @Override
    protected void initControls() {
        super.initControls();

        text_title.setText("订单详情");

        list_list.setAdapter(adapter);
        list_list.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(OrderDetailActivity.this,ProductDetailActivity.class);
            intent.putExtra("item",adapter.getItem(position));
            startActivity(intent);
        });

        orderId = getIntent().getIntExtra("id",-1);
        presenter.getOrderInfo(orderId);
    }

    @OnClick(R.id.img_title_back)
    void back(){
        finish();
    }

    @OnClick(R.id.btn_orderDetail_pay)
    void pay(){
        // 支付
        new AlertDialog.Builder(this)
                .setMessage("确认支付")
                .setMessage("现在进行支付吗？")
                .setPositiveButton("确定", (dialog, which) -> presenter.pay(orderId))
                .setNegativeButton("取消",null)
                .show();
    }

    public void showData(Order order) {
        text_total.setText("￥ "+order.getPrice());
        adapter.setData(order.getProducts());
        text_time.setText("下单时间：" + order.getCreate_time());
        text_state.setText("订单状态：" + ("0".equals(order.getState()) ? "待支付" : "已完成"));
        text_cancel.setText(order.getState().equals("0") ? "取消订单" : "删除订单");
        text_cancel.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(OrderDetailActivity.this);
            if (order.getState().equals("0")){
                // 未支付
                builder.setTitle("确定取消订单吗");
                builder.setMessage("你尚未支付订单，取消后需要重新下单，确定吗？");
            }else{
                builder.setTitle("确定删除订单吗");
                builder.setMessage("删除后不可找回，确定吗？");
            }
            builder.setPositiveButton("确定", (dialog, which) -> presenter.deleteOrder(orderId));
            builder.setNegativeButton("取消", null);
            builder.show();
        });
        if (!order.getState().equals("0")){
            btn_pay.setVisibility(View.INVISIBLE);
        }
    }

    public void deleteSuccess() {
        toast("成功！");
        finish();
    }

    public void paySuccess() {
        toast("订单支付成功");
    }

    class Adapter extends BaseListAdapter<Produce> {

        Adapter() {
            super(new ArrayList<>());
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView==null ? LayoutInflater.from(OrderDetailActivity.this).inflate(R.layout.item_order_detail,parent,false) : convertView;
            SimpleDraweeView image = view.findViewById(R.id.img_item_product);
            TextView name = view.findViewById(R.id.text_item_product_name);
            TextView desc = view.findViewById(R.id.text_item_product_desc);
            TextView price = view.findViewById(R.id.text_item_product_price);
            TextView count = view.findViewById(R.id.text_orderDetail_count);
            Produce item = getItem(position);

            image.setImageURI(item.getImg());
            name.setText(item.getName());
            desc.setText(item.getDescription());
            price.setText("￥ "+item.getPrice());
            count.setText("共 "+item.getCount()+" 份");
            return view;
        }
    }
}
