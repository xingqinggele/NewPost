package com.example.newpost.useractivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newpost.MainActivity;
import com.example.newpost.R;
import com.example.newpost.dialog.LoadingDialog;
import com.example.newpost.net.HttpRequest;
import com.example.newpost.net.OkHttpException;
import com.example.newpost.net.RequestParams;
import com.example.newpost.net.ResponseCallback;
import com.example.newpost.utils.SPUtils;
import com.example.newpost.utils.StatusBarUtil;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * 作者: qgl
 * 创建日期：2020/10/21
 * 描述:登录界面
 */
public class LoginActivity extends Activity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private EditText login_et_userName;
    private EditText login_et_passWord;
    private Button login_bt_login;
    private boolean eys = true;
    private boolean remember_pass = false;
    private ImageView login_password_eys;
    private CheckBox login_remember_password;
    protected LoadingDialog loadDialog;//加载等待弹窗
    private TextView login_tv_forgot_password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.statusBarLightMode_white(this);
        setContentView(R.layout.login_activity);
        initView();
        initData();
    }

    /**
     * 初始化控件
     */
    public void initView() {
        loadDialog = new LoadingDialog(this);
        login_et_userName = findViewById(R.id.login_et_userName);
        login_et_passWord = findViewById(R.id.login_et_passWord);
        login_bt_login = findViewById(R.id.login_bt_login);
        login_password_eys = findViewById(R.id.login_password_eys);
        login_remember_password = findViewById(R.id.login_remember_password);
        login_tv_forgot_password = findViewById(R.id.login_tv_forgot_password);
        login_remember_password.setOnCheckedChangeListener(this);
        login_bt_login.setOnClickListener(this);
        login_password_eys.setOnClickListener(this);
        login_tv_forgot_password.setOnClickListener(this);
    }

    /**
     * 数据配置
     */
    public void initData() {
        if (SPUtils.contains(LoginActivity.this, "userName")) {
            Log.e("本地存储", SPUtils.get(LoginActivity.this, "userName", "-1").toString() + SPUtils.get(LoginActivity.this, "passWord", "-1").toString());
            getLogin(SPUtils.get(LoginActivity.this, "userName", "-1").toString(), SPUtils.get(LoginActivity.this, "passWord", "-1").toString());
        }
    }

    /**
     * 登录方法
     */
    public void getLogin(String userName, String passWord) {
        // 开启加载框
        loadDialog.show();
        RequestParams params = new RequestParams();
        params.put("username", userName);
        params.put("password", passWord);
        HttpRequest.getLogin(params,"", new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                // 关闭加载框
                loadDialog.dismiss();
                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    String token = result.getString("token");
                    Log.e("token", token);
                    if (remember_pass) {
                        SPUtils.put(LoginActivity.this, "userName", userName);
                        SPUtils.put(LoginActivity.this, "passWord", passWord);

                    }
                    SPUtils.put(LoginActivity.this, "Token", token);
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(OkHttpException failuer) {
                // 关闭加载框
                loadDialog.dismiss();
                Toast.makeText(LoginActivity.this, "请求失败=" + failuer.getEmsg(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_bt_login:
                if (TextUtils.isEmpty(login_et_userName.getText().toString().trim())) {
                    Toast.makeText(LoginActivity.this, "请输入账户", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(login_et_passWord.getText().toString().trim())) {
                    Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_LONG).show();
                    return;
                }
                getLogin(login_et_userName.getText().toString().trim(), login_et_passWord.getText().toString().trim());
                break;
            case R.id.login_password_eys:
                if (eys) {
                    eys = false;
                    login_password_eys.setImageDrawable(getDrawable(R.mipmap.me_change_password_eys1));
                    login_et_passWord.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    eys = true;
                    login_password_eys.setImageDrawable(getDrawable(R.mipmap.me_change_password_eys));
                    login_et_passWord.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                break;
            case R.id.login_tv_forgot_password:
                startActivity(new Intent(LoginActivity.this,RetrievePassActivity.class));
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        remember_pass = b;
    }
}
