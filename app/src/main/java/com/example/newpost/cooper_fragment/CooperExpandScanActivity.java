package com.example.newpost.cooper_fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.newpost.R;
import com.example.newpost.utils.StatusBarUtil;

/**
 * 作者: qgl
 * 创建日期：2020/11/12
 * 描述: 合作方拓展扫码
 */
public class CooperExpandScanActivity extends Activity implements View.OnClickListener {

    private LinearLayout iv_back;
    private ImageView cooperexpandscan_ig_btn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.statusBarLightMode_white(this);
        setContentView(R.layout.cooperexpandscan_activity);
        iv_back = findViewById(R.id.iv_back);
        cooperexpandscan_ig_btn = findViewById(R.id.cooperexpandscan_ig_btn);
        iv_back.setOnClickListener(this);
        cooperexpandscan_ig_btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.cooperexpandscan_ig_btn:
                startActivity(new Intent(CooperExpandScanActivity.this,CooperExpandActivity.class));
                break;
        }
    }
}
