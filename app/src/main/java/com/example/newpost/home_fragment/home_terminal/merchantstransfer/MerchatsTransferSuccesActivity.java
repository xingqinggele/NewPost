package com.example.newpost.home_fragment.home_terminal.merchantstransfer;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.newpost.R;
import com.example.newpost.base.BaseActivity;

/**
 * 作者: qgl
 * 创建日期：2020/12/4
 * 描述: 划拨成功
 */
public class MerchatsTransferSuccesActivity extends BaseActivity implements View.OnClickListener {

    private TextView success_name;
    private TextView success_num;
    private Button success_btn;
    private LinearLayout iv_back;
    @Override
    protected int getLayoutId() {
        return R.layout.merchatstransfersuccesactivity;
    }

    @Override
    protected void initView() {
        success_name = findViewById(R.id.success_name);
        success_num = findViewById(R.id.success_num);
        success_btn = findViewById(R.id.success_btn);
        iv_back = findViewById(R.id.iv_back);
        success_btn.setOnClickListener(this);
        iv_back.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        success_name .setText(getIntent().getStringExtra("userName"));
        success_num .setText(getIntent().getStringExtra("machineNum")+"台");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.success_btn:
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
