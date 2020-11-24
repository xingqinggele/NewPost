package com.example.newpost.me_fragment;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.newpost.R;
import com.example.newpost.base.BaseActivity;

/**
 * 作者: qgl
 * 创建日期：2020/11/14
 * 描述:我的银行卡
 */
public class MeBankCradActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout iv_back;
    private Button me_bank_card_btn;
    @Override
    protected int getLayoutId() {
        return R.layout.mebankcradactivity;
    }

    @Override
    protected void initView() {
        iv_back = findViewById(R.id.iv_back);
        me_bank_card_btn = findViewById(R.id.me_bank_card_btn);
        iv_back.setOnClickListener(this);
        me_bank_card_btn.setOnClickListener(this);
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
            case R.id.me_bank_card_btn:
                startActivity(new Intent(MeBankCradActivity.this,MeBankCradModifyActivity.class));
                break;
        }
    }
}
