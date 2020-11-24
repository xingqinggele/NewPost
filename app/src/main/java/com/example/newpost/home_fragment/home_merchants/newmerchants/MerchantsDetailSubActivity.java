package com.example.newpost.home_fragment.home_merchants.newmerchants;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.example.newpost.MainActivity;
import com.example.newpost.R;
import com.example.newpost.base.BaseActivity;
import com.example.newpost.useractivity.LoginActivity;

/**
 * 作者: qgl
 * 创建日期：2020/11/21
 * 描述:提交成功界面
 */
public class MerchantsDetailSubActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout iv_back;
    @Override
    protected int getLayoutId() {
        return R.layout.merchantsdetailsubactivity;
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
                for (Activity ac : activitys) {
                    if (ac != null) {
                        ac.finish();
                    }
                }
                startActivity(new Intent(mContext, MainActivity.class));
                break;
        }
    }
}
