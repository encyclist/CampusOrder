package com.erning.campusorder.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.erning.campusorder.R;
import com.erning.campusorder.entity.Produce;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ProductDetailActivity extends AppCompatActivity {
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        unbinder = ButterKnife.bind(this);

        Produce item = (Produce) getIntent().getSerializableExtra("item");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (unbinder != null){
            unbinder.unbind();
        }
    }
}
