package com.example.newpost.home_fragment.home_merchants.newmerchants.state;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.newpost.MainActivity;
import com.example.newpost.R;
import com.example.newpost.base.BaseActivity;
import com.example.newpost.home_fragment.home_merchants.newmerchants.MerchantsBigDetailActivity11;
import com.example.newpost.home_fragment.home_merchants.newmerchants.MerchantsDetailActivity11;
import com.example.newpost.home_fragment.home_merchants.newmerchants.NewMerchantsActivity;

/**
 * 作者: qgl
 * 创建日期：2020/11/21
 * 描述:审核失败成功界面
 */
public class MerchantsDetailFailureActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout iv_back;
    private Button merchats_detail_failure_btn;
    private String type = "0"; // 身份信息 0 小微 1商户
    private String parent_class = "0"; // 父类 0 选择页 1提交页

    @Override
    protected int getLayoutId() {
        return R.layout.merchantsdetailfailureactivity;
    }

    @Override
    protected void initView() {
        iv_back = findViewById(R.id.iv_back);
        merchats_detail_failure_btn = findViewById(R.id.merchats_detail_failure_btn);
        iv_back.setOnClickListener(this);
        merchats_detail_failure_btn.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        type = getIntent().getStringExtra("type");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                    finish();
                break;
            case R.id.merchats_detail_failure_btn:
                if (type.equals("0")) {
                    startActivity(new Intent(MerchantsDetailFailureActivity.this, MerchantsDetailActivity11.class));
                } else {
                    startActivity(new Intent(MerchantsDetailFailureActivity.this, MerchantsBigDetailActivity11.class));
                }
                finish();
                break;


        }
    }
}
