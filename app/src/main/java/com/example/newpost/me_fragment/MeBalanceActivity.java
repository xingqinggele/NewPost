package com.example.newpost.me_fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.newpost.R;
import com.example.newpost.base.BaseActivity;

/**
 * 作者: qgl
 * 创建日期：2020/11/5
 * 描述:我的余额
 */
public class MeBalanceActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout iv_back;
    private LinearLayout mebalance_withdrawal;
    private LinearLayout me_payment;

    @Override
    protected int getLayoutId() {
        return R.layout.mebalanceactivity;
    }

    @Override
    protected void initView() {
        iv_back = findViewById(R.id.iv_back);
        mebalance_withdrawal = findViewById(R.id.mebalance_withdrawal);
        me_payment = findViewById(R.id.me_payment);
        iv_back.setOnClickListener(this);
        mebalance_withdrawal.setOnClickListener(this);
        me_payment.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.mebalance_withdrawal:
                startActivity(new Intent(MeBalanceActivity.this, MeBalanceWitndActivity.class));
                break;
            case R.id.me_payment:
                startActivity(new Intent(MeBalanceActivity.this, MePaymentActivity.class));
                break;

        }
    }
}
