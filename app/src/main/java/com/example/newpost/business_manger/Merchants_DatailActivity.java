package com.example.newpost.business_manger;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.newpost.R;
import com.example.newpost.base.BaseActivity;

/**
 * 作者: qgl
 * 创建日期：2020/10/30
 * 描述:新增小微商户填写信息
 */
public class Merchants_DatailActivity extends BaseActivity implements View.OnClickListener {
    //返回键
    private ImageView iv_back;
    // 商户名称
    private EditText merchant_deteil_sname;
    //商户类型
    private TextView merchant_deteil_type;
    // 商户地址
    private TextView merchant_deteil_adress;
    // 详细地址
    private TextView merchant_deteils_adress_deta;
    // 姓名
    private EditText merchant_deteil_uname;
    // 身份证号码
    private EditText merchant_deteil_idcrad;
    // 身份证有效期
    private EditText merchant_deteil_card_year;
    // 手机号
    private EditText merchant_deteil_phone;
    // 邮箱
    private EditText merchant_deteil_email;
    // 身份证正面
    private ImageView merchant_deteil_cardis;
    // 身份证反面
    private ImageView merchant_deteil_cardthe;
    // 店铺门头照
    private ImageView merchant_deteil_dp1;
    //店铺收银台照
    private ImageView merchant_deteil_dp2;
    //上传商铺内部照
    private ImageView merchant_deteil_dp3;
    // 提交，保存，跳转
    private Button merchant_deteil_submint;

    @Override
    protected int getLayoutId() {
        return R.layout.merchants_datailactivity;
    }

    @Override
    protected void initView() {
        iv_back = findViewById(R.id.iv_back);
        merchant_deteil_submint = findViewById(R.id.merchant_deteil_submint);
        merchant_deteil_type = findViewById(R.id.merchant_deteil_type);
        iv_back.setOnClickListener(this);
        merchant_deteil_submint.setOnClickListener(this);
        merchant_deteil_type.setOnClickListener(this);

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
            case R.id.merchant_deteil_submint:
                break;
            case R.id.merchant_deteil_type:
                break;
            case R.id.merchant_deteil_adress:
                break;
        }
    }
}
