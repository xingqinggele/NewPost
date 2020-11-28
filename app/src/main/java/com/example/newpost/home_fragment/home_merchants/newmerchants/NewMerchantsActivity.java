package com.example.newpost.home_fragment.home_merchants.newmerchants;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.newpost.R;
import com.example.newpost.base.BaseActivity;
import com.example.newpost.home_fragment.home_merchants.newmerchants.state.MerchantsDetailFailureActivity;
import com.example.newpost.home_fragment.home_merchants.newmerchants.state.MerchantsDetailSubActivity;
import com.example.newpost.net.HttpRequest;
import com.example.newpost.net.OkHttpException;
import com.example.newpost.net.RequestParams;
import com.example.newpost.net.ResponseCallback;
import com.example.newpost.utils.SPUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 作者: qgl
 * 创建日期：2020/10/30
 * 描述: 新增商户
 */
public class NewMerchantsActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout iv_back;
    private RelativeLayout new_merchants_relative_small;
    private RelativeLayout new_merchants_relative_big;
    private String mctType = "0"; // 0小微 1 企业
    private String mctAuditState = ""; //审核状态

    @Override
    protected int getLayoutId() {
        return R.layout.newmerchantsactivity;
    }

    @Override
    protected void initView() {
        iv_back = findViewById(R.id.iv_back);
        new_merchants_relative_small = findViewById(R.id.new_merchants_relative_small);
        new_merchants_relative_big = findViewById(R.id.new_merchants_relative_big);
        iv_back.setOnClickListener(this);
        new_merchants_relative_small.setOnClickListener(this);
        new_merchants_relative_big.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        postData();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.new_merchants_relative_small:
                //如果 mctType为空证明没有入驻信息
                if (mctType.equals("")) {
                    // 直接进入小微入驻
                    startActivity(new Intent(NewMerchantsActivity.this, MerchantsDetailActivity11.class));
                } else {
                    // mctType = 0 已经入驻小微了
                    if (mctType.equals("0")) {
                        if (mctAuditState.equals("")) {
                            startActivity(new Intent(NewMerchantsActivity.this, MerchantsDetailActivity11.class));
                        } else {
                            judge(mctAuditState, true);
                        }
                    } else {
                        showToast("您已存在企业商户，不可在注册小微！");
                    }

                }
                break;
            case R.id.new_merchants_relative_big:
                //如果 mctType为空证明没有入驻信息
                if (mctType.equals("")) {
                    // 直接进入商户入驻
                    startActivity(new Intent(NewMerchantsActivity.this, MerchantsBigDetailActivity11.class));
                } else {
                    // mctType = 1 已经入驻商家了
                    if (mctType.equals("1")) {
                        if (mctAuditState.equals("")) {
                            startActivity(new Intent(NewMerchantsActivity.this, MerchantsBigDetailActivity11.class));
                        } else {
                            judge(mctAuditState, false);
                        }

                    } else {
                        showToast("您已存在小微，不可在注册企业商户！");
                    }
                }
                break;

        }
    }

    /***************请求接口判断身份，审核状态*********/
    public void postData() {
        {
            RequestParams params = new RequestParams();
            HttpRequest.getMerchant(params, SPUtils.get(NewMerchantsActivity.this, "Token", "-1").toString(), new ResponseCallback() {
                @Override
                public void onSuccess(Object responseObj) {
                    try {
                        JSONObject result = new JSONObject(responseObj.toString());
                        if (TextUtils.equals(result.getString("data"), "null")) {
                            Log.e("aaaaaaaa", "aaaaaaa");
                            // 新增用户
                            mctType = "";
                        } else {
                            // 修改或者是查看
                            Log.e("bbbbbbbbbbb", "aaaaaaa");
                            JSONObject data = new JSONObject(result.getJSONObject("data").toString());
                            mctType = data.getString("mctType");
                            mctAuditState = data.getString("mctAuditState");
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(OkHttpException failuer) {
                    mctType = "3";
                    Failuer(failuer.getEcode(), failuer.getEmsg());
                }
            });


        }
    }

    /**
     * 判断审核状态
     *
     * @param value
     */
    public void judge(String value, boolean type) {
        if (value.equals("1")) {
            //审核中不让查看
            Intent intent = new Intent(NewMerchantsActivity.this, MerchantsDetailSubActivity.class);
            intent.putExtra("parent_class", "0");
            startActivity(intent);
        } else if (value.equals("2")) {
            //审核成功，直接进入
            if (type) {
                startActivity(new Intent(NewMerchantsActivity.this, MerchantsDetailActivity11.class));
            } else {
                startActivity(new Intent(NewMerchantsActivity.this, MerchantsBigDetailActivity11.class));
            }
        } else {
            //审核失败
            Intent intent = new Intent(NewMerchantsActivity.this, MerchantsDetailFailureActivity.class);

            if (type) {
                intent.putExtra("type", "0");
            } else {
                intent.putExtra("type", "1");
            }
            startActivity(intent);
        }
    }
}
