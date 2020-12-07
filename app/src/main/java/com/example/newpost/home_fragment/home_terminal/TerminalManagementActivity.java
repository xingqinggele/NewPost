package com.example.newpost.home_fragment.home_terminal;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.newpost.R;
import com.example.newpost.base.BaseActivity;
import com.example.newpost.home_fragment.home_terminal.merchantscallback.MerchantsCallbackActivity;
import com.example.newpost.home_fragment.home_terminal.merchantsquery.MerchantsQuery;
import com.example.newpost.home_fragment.home_terminal.merchantstransfer.MerchantsTransferPersonActivity;
import com.example.newpost.net.HttpRequest;
import com.example.newpost.net.OkHttpException;
import com.example.newpost.net.RequestParams;
import com.example.newpost.net.ResponseCallback;
import com.example.newpost.utils.SPUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 作者: qgl
 * 创建日期：2020/11/3
 * 描述: 终端管理
 */
public class TerminalManagementActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout iv_back;
    private TextView terminal_manage_tv_number1;
    private TextView terminal_manage_tv_number2;
    private TextView terminal_manage_tv_number3;
    private RelativeLayout terminal_management_query;
    private RelativeLayout terminal_management_transfer;
    private RelativeLayout terminal_management_callback;
    @Override
    protected int getLayoutId() {
        return R.layout.terminalmanagementactivity;
    }

    @Override
    protected void initView() {
        iv_back = findViewById(R.id.iv_back);
        terminal_manage_tv_number1 = findViewById(R.id.terminal_manage_tv_number1);
        terminal_manage_tv_number2 = findViewById(R.id.terminal_manage_tv_number2);
        terminal_manage_tv_number3 = findViewById(R.id.terminal_manage_tv_number3);
        terminal_management_query = findViewById(R.id.terminal_management_query);
        terminal_management_transfer = findViewById(R.id.terminal_management_transfer);
        terminal_management_callback = findViewById(R.id.terminal_management_callback);
        terminal_management_query.setOnClickListener(this);
        terminal_management_transfer.setOnClickListener(this);
        terminal_management_callback.setOnClickListener(this);

        iv_back.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        getUserPos();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.terminal_management_transfer:
                //终端划拨
                startActivity(new Intent(TerminalManagementActivity.this, MerchantsTransferPersonActivity.class));
                break;
            case R.id.terminal_management_query:
                //终端查询
                startActivity(new Intent(TerminalManagementActivity.this, MerchantsQuery.class));
                break;
            case R.id.terminal_management_callback:
                // 终端回调
                startActivity(new Intent(TerminalManagementActivity.this, MerchantsCallbackActivity.class));

                break;
        }
    }

    public void getUserPos(){
        RequestParams params = new RequestParams();
        HttpRequest.getUserPos(params, SPUtils.get(TerminalManagementActivity.this, "Token", "-1").toString(), new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    if (!TextUtils.equals(result.getString("data"), "null")){
                        JSONObject data = new JSONObject(result.getJSONObject("data").toString());
                        terminal_manage_tv_number1.setText(data.getString("posNumTotal"));
                        terminal_manage_tv_number3.setText(data.getString("posNumActiva"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(OkHttpException failuer) {

            }
        });
    }
}
