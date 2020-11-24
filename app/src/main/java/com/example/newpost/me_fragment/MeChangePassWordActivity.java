package com.example.newpost.me_fragment;

import android.content.Intent;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newpost.R;
import com.example.newpost.base.BaseActivity;
import com.example.newpost.net.HttpRequest;
import com.example.newpost.net.OkHttpException;
import com.example.newpost.net.RequestParams;
import com.example.newpost.net.ResponseCallback;
import com.example.newpost.useractivity.LoginActivity;
import com.example.newpost.useractivity.RegisterActivity;
import com.example.newpost.useractivity.RetrievePassActivity;
import com.example.newpost.utils.SPUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 作者: qgl
 * 创建日期：2020/11/2
 * 描述:修改密码
 */
public class MeChangePassWordActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout iv_back;  // 返回键
    private ImageView me_password_eys; // 可视按钮
    private EditText me_password_original_password, me_password_new_password, me_password_confirm_password; //原密码，新密码，确认密码
    private TextView me_password_retrieve; // 忘记密码
    private Button me_password_submit; // 提交
    private boolean eys = true; // true 不可视, false 不可视

    @Override
    protected int getLayoutId() {
        return R.layout.changepassword_activity;
    }


    @Override
    protected void initView() {
        iv_back = findViewById(R.id.iv_back);
        me_password_eys = findViewById(R.id.me_password_eys);
        me_password_original_password = findViewById(R.id.me_password_original_password);
        me_password_new_password = findViewById(R.id.me_password_new_password);
        me_password_confirm_password = findViewById(R.id.me_password_confirm_password);
        me_password_retrieve = findViewById(R.id.me_password_retrieve);
        me_password_submit = findViewById(R.id.me_password_submit);
        iv_back.setOnClickListener(this);
        me_password_eys.setOnClickListener(this);
        me_password_retrieve.setOnClickListener(this);
        me_password_submit.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                //返回键
                finish();
                break;
            case R.id.me_password_eys:
                // 是否可视
                if (eys) {
                    eys = false;
                    me_password_eys.setImageDrawable(getDrawable(R.mipmap.me_change_password_eys1));
                    me_password_original_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    eys = true;
                    me_password_eys.setImageDrawable(getDrawable(R.mipmap.me_change_password_eys));
                    me_password_original_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                break;
            case R.id.me_password_retrieve:
                //忘记密码
                startActivity(new Intent(MeChangePassWordActivity.this, RetrievePassActivity.class));
                break;
            case R.id.me_password_submit:
                // 提交

                if (TextUtils.isEmpty(me_password_original_password.getText().toString().trim())) {
                    Toast.makeText(MeChangePassWordActivity.this, "请输入原密码", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(me_password_new_password.getText().toString().trim())) {
                    Toast.makeText(MeChangePassWordActivity.this, "请输入新密码", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(me_password_confirm_password.getText().toString().trim())) {
                    Toast.makeText(MeChangePassWordActivity.this, "请输入确认密码", Toast.LENGTH_LONG).show();
                    return;
                }
                if (!me_password_new_password.getText().toString().trim().equals(me_password_confirm_password.getText().toString().trim())) {
                    Toast.makeText(MeChangePassWordActivity.this, "两次输入密码不一致", Toast.LENGTH_LONG).show();
                    return;
                }
                if (SPUtils.contains(MeChangePassWordActivity.this, "userName")) {
                    Submit(SPUtils.get(MeChangePassWordActivity.this, "userName", "-1").toString(), me_password_original_password.getText().toString().trim(), me_password_new_password.getText().toString().trim());
                } else {
                    showToast("请先登录");
                    exitApp(mContext);
                }
                break;
        }
    }

    /**
     * 提交数据
     */
    public void Submit(String userName, String passWord, String passWord_new) {
        // 开启加载框
        loadDialog.show();
        RequestParams params = new RequestParams();
        params.put("username", userName);
        params.put("password", passWord);
        params.put("passwordnew", passWord_new);
        HttpRequest.getUpdPassword(params, SPUtils.get(MeChangePassWordActivity.this, "Token", "-1").toString(), new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                loadDialog.dismiss();
                showToast("修改密码成功,请重新登录");
                // 退出登录,清除本地数据
                SPUtils.clear(mContext);
                exitApp(MeChangePassWordActivity.this);
            }

            @Override
            public void onFailure(OkHttpException failuer) {
                loadDialog.dismiss();
                if (failuer.getEcode() == 401) {
                    showToast("登录过期，请重新登录");
                    // 退出登录,清除本地数据
                    SPUtils.clear(mContext);
                    exitApp(MeChangePassWordActivity.this);
                } else {
                    showToast(failuer.getEmsg());
                }
            }
        });
    }
}
