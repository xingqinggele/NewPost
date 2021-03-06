 package com.example.newpost.home_fragment.home_merchants;

import android.content.Intent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.newpost.R;
import com.example.newpost.base.BaseActivity;
import com.example.newpost.home_fragment.home_terminal.merchantsquery.MerchantsQuery;
import com.example.newpost.home_fragment.home_merchants.newmerchants.NewMerchantsActivity;
import com.gyf.barlibrary.ImmersionBar;

/**
 * 作者: qgl
 * 创建日期：2020/10/30
 * 描述: 商户管理
 */
public class MerchantsActivity extends BaseActivity implements View.OnClickListener {
    private ImmersionBar mImmersionBar;//状态栏沉浸
    //新增商户
    private RelativeLayout merchant_relative_new;
    //商户查询
    private RelativeLayout merchant_relative_query;
    private LinearLayout iv_back;
    @Override
    protected int getLayoutId() {
        statusBarConfig().init();
        return R.layout.merchantsactivity;
    }

    @Override
    protected void initView() {
        merchant_relative_new = findViewById(R.id.merchant_relative_new);
        merchant_relative_query = findViewById(R.id.merchant_relative_query);
        merchant_relative_new.setOnClickListener(this);
        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        merchant_relative_query.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    /**
     * 初始化沉浸式状态栏
     */
    private ImmersionBar statusBarConfig() {
        //在BaseActivity里初始化
        mImmersionBar = ImmersionBar.with(this).fitsSystemWindows(true).statusBarColor(R.color.home_merchant)
                .statusBarDarkFont(statusBarDarkFont())    //默认状态栏字体颜色为黑色
                .keyboardEnable(false, WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
                        | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);  //解决软键盘与底部输入框冲突问题，默认为false，还有一个重载方法，可以指定软键盘mode
        //必须设置View树布局变化监听，否则软键盘无法顶上去，还有模式必须是SOFT_INPUT_ADJUST_PAN
        getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(this);
        return mImmersionBar;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.merchant_relative_new:
                startActivity(new Intent(MerchantsActivity.this, NewMerchantsActivity.class));
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.merchant_relative_query:
                startActivity(new Intent(MerchantsActivity.this, MerchantsQuery.class));

                break;
        }
    }
}
