package com.example.newpost.me_fragment;

import android.view.View;
import android.widget.LinearLayout;

import com.example.newpost.R;
import com.example.newpost.base.BaseActivity;

/**
 * 作者: qgl
 * 创建日期：2020/11/19
 * 描述: 提现界面
 */
public class MeBalanceWitndActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout iv_back;
    @Override
    protected int getLayoutId() {
        return R.layout.mebalancewitndactivity;
    }

    @Override
    protected void initView() {
        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
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
        }
    }
}
