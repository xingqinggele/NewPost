package com.example.newpost.me_fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.newpost.R;
import com.example.newpost.base.BaseActivity;

/**
 * 作者: qgl
 * 创建日期：2020/11/3
 * 描述:意见反馈
 */
public class MeFeedBackActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout iv_back;
    @Override
    protected int getLayoutId() {
        return R.layout.mefeedbackactivity;
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
