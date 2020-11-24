package com.example.newpost.useractivity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newpost.R;
import com.example.newpost.net.HttpRequest;
import com.example.newpost.net.OkHttpException;
import com.example.newpost.net.RequestParams;
import com.example.newpost.net.ResponseCallback;
import com.example.newpost.utils.CountDownTimerUtils;
import com.example.newpost.utils.StatusBarUtil;

import okhttp3.MediaType;

import static com.example.newpost.utils.Utility.isChinaPhoneLegal;

/**
 * 作者: qgl
 * 创建日期：2020/10/23
 * 描述:注册界面
 */
public class RegisterActivity extends Activity implements View.OnClickListener {
    //    手机号
    private EditText register_et_phone;
    //    验证码
    private EditText register_tv_code;
    //    获取验证码按钮
    private TextView register_tv_mCode;
    //    注册按钮
    private Button register_btn;
    //    已有帐户，去登陆
    private TextView register_tv_login;
    //    阅读协议
    private TextView register_tv_agreement;
//    密码
    private EditText register_et_password;
//    再次输入密码
    private EditText register_et_password1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.statusBarLightMode_white(this);
        setContentView(R.layout.register_activity);
        initView();
        initData();
    }

    public void initView() {
        register_tv_login = findViewById(R.id.register_tv_login);
        register_tv_agreement = findViewById(R.id.register_tv_agreement);
        register_et_phone = findViewById(R.id.register_et_phone);
        register_tv_code = findViewById(R.id.register_tv_code);
        register_tv_mCode = findViewById(R.id.register_tv_mCode);
        register_btn = findViewById(R.id.register_btn);
        register_et_password = findViewById(R.id.register_et_password);
        register_et_password1 = findViewById(R.id.register_et_password1);
        register_tv_mCode.setOnClickListener(this);
        register_btn.setOnClickListener(this);
        register_tv_login.setOnClickListener(this);
    }

    public void initData(){
        SpannableString spannableString = new SpannableString(register_tv_login.getText().toString());  //获取按钮上的文字
        ForegroundColorSpan span = new ForegroundColorSpan(Color.parseColor("#1673A3"));
        spannableString.setSpan(span, 4, 8, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);//设置颜色
        register_tv_login.setText(spannableString);
        SpannableString spannableString1 = new SpannableString(register_tv_agreement.getText().toString());  //获取按钮上的文字
        spannableString1.setSpan(span, 7, 15, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);//设置颜色
        register_tv_agreement.setText(spannableString1);

    }

    /**
     * 获取手机验证码
     */
    public void getPhoneCode() {
        RequestParams params = new RequestParams();
        params.put("username", register_et_phone.getText().toString().trim());
        HttpRequest.getRegister_Code(params, "", new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                Log.e("的撒旦",responseObj.toString());
            }

            @Override
            public void onFailure(OkHttpException failuer) {

            }
        });

    }

    /**
     * 注册方法
     */
    public void getRegister() {
        RequestParams params = new RequestParams();
        params.put("phone", register_et_phone.getText().toString().trim());
        params.put("code", register_tv_code.getText().toString().trim());
        HttpRequest.getRegister(params, "",new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {

            }

            @Override
            public void onFailure(OkHttpException failuer) {
                Toast.makeText(RegisterActivity.this, "请求失败=" + failuer.getEmsg(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_tv_mCode:
                if (isChinaPhoneLegal(register_et_phone.getText().toString().trim())) {
//                    // 开始倒计时 60秒，间隔1秒
                    CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(register_tv_mCode, 60000, 1000);
                    mCountDownTimerUtils.start();
                    //发送短信
                    getPhoneCode();
                } else {
                    Toast.makeText(RegisterActivity.this, "请输入正确的手机号", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.register_btn:
                if (!isChinaPhoneLegal(register_et_phone.getText().toString().trim())) {
                    Toast.makeText(RegisterActivity.this, "请输入正确的手机号", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(register_tv_code.getText().toString().trim())) {
                    Toast.makeText(RegisterActivity.this, "请输入验证码", Toast.LENGTH_LONG).show();
                    return;
                }
                 if (TextUtils.isEmpty(register_et_password.getText().toString().trim())) {
                    Toast.makeText(RegisterActivity.this, "请输入密码", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(register_et_password1.getText().toString().trim())) {
                    Toast.makeText(RegisterActivity.this, "请再次输入密码", Toast.LENGTH_LONG).show();
                    return;
                }
                if (!register_et_password.getText().toString().trim().equals(register_et_password1.getText().toString().trim())){
                    Toast.makeText(RegisterActivity.this, "两次输入密码不一致", Toast.LENGTH_LONG).show();
                    return;
                }
                getRegister();
                break;
            case R.id.register_tv_login:

                break;
        }
    }
}
