package com.example.newpost.home_fragment.home_merchants.newmerchants;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.newpost.R;
import com.example.newpost.base.BaseActivity;

/**
 * 作者: qgl
 * 创建日期：2020/10/30
 * 描述: 新增商户
 */
public class NewMerchantsActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout iv_back;
    private RelativeLayout new_merchants_relative_small;
    private RelativeLayout new_merchants_relative_big;
    @Override
    protected int getLayoutId() {
        return R.layout.newmerchantsactivity;
    }

    @Override
    protected void initView() {
        iv_back = findViewById(R.id.iv_back);
        new_merchants_relative_small = findViewById(R.id.new_merchants_relative_small);
        new_merchants_relative_big = findViewById(R.id.new_merchants_relative_big);
        iv_back.setOnClickListener(this);
        new_merchants_relative_small.setOnClickListener(this);
        new_merchants_relative_big.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.new_merchants_relative_small:
                startActivity(new Intent(NewMerchantsActivity.this, MerchantsDetailActivity11.class));
                break;
            case R.id.new_merchants_relative_big:
                startActivity(new Intent(NewMerchantsActivity.this, MerchantsBigDetailActivity1.class));
                break;

        }
    }
}
