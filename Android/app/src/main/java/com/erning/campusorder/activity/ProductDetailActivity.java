package com.erning.campusorder.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.erning.campusorder.R;
import com.erning.campusorder.entity.Produce;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ProductDetailActivity extends AppCompatActivity {
    private Unbinder unbinder;

    @BindView(R.id.text_title) TextView text_title;
    @BindView(R.id.img_productDetail) SimpleDraweeView img_img;
    @BindView(R.id.text_productDetail_desc) TextView text_desc;
    @BindView(R.id.text_productDetail_price) TextView text_price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        unbinder = ButterKnife.bind(this);

        Produce item = (Produce) getIntent().getSerializableExtra("item");

        text_title.setText(item.getName());
        img_img.setImageURI(item.getImg());
        text_desc.setText(item.getDescription());
        text_price.setText("￥ "+item.getPrice());
    }

    @OnClick(R.id.img_title_back)
    void back(){
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (unbinder != null){
            unbinder.unbind();
        }
    }
}
