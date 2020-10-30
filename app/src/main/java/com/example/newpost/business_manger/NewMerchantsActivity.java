package com.example.newpost.business_manger;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.newpost.R;
import com.example.newpost.base.BaseActivity;

/**
 * 作者: qgl
 * 创建日期：2020/10/30
 * 描述: 新增商户
 */
public class NewMerchantsActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_back;
    private RelativeLayout new_merchants_small;
    @Override
    protected int getLayoutId() {
        return R.layout.newmerchantsactivity;
    }

    @Override
    protected void initView() {
        iv_back = findViewById(R.id.iv_back);
        new_merchants_small = findViewById(R.id.new_merchants_small);
        iv_back.setOnClickListener(this);
        new_merchants_small.setOnClickListener(this);
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
            case R.id.new_merchants_small:
                startActivity(new Intent(NewMerchantsActivity.this,Merchants_DatailActivity.class));
                break;

        }
    }
}
