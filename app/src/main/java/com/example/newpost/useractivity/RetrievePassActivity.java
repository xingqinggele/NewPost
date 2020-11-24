package com.example.newpost.useractivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.example.newpost.dialog.LoadingDialog;
import com.example.newpost.net.HttpRequest;
import com.example.newpost.net.OkHttpException;
import com.example.newpost.net.RequestParams;
import com.example.newpost.net.ResponseCallback;
import com.example.newpost.utils.CountDownTimerUtils;
import com.example.newpost.utils.SPUtils;
import com.example.newpost.utils.StatusBarUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.newpost.utils.Utility.isChinaPhoneLegal;

/**
 * 作者: qgl
 * 创建日期：2020/11/13
 * 描述:找回密码
 */
public class RetrievePassActivity extends Activity implements View.OnClickListener {
    private LinearLayout iv_back;
    private EditText retrieve_pass_et_userName; // 手机号
    private EditText retrieve_pass_et_code; // 验证码
    private EditText retrieve_pass_et_password; // 新密码
    private EditText retrieve_pass_et_password1; // 再次输入密码
    private Button retrieve_pass_btn; // 提交修改
    private TextView retrieve_pass_tv_mCode; // 倒计时控件
    private String uuid = "";
    protected LoadingDialog loadDialog;//加载等待弹窗
    public static List<Activity> activitys;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.statusBarLightMode_white(this);
        setContentView(R.layout.retrievepassactivity);
        initView();
    }

    public void initView(){
        if (activitys == null) {
            activitys = new ArrayList<Activity>();
        }
        activitys.add(this);
        loadDialog = new LoadingDialog(this);
        iv_back = findViewById(R.id.iv_back);
        retrieve_pass_et_userName = findViewById(R.id.retrieve_pass_et_userName);
        retrieve_pass_et_code = findViewById(R.id.retrieve_pass_et_code);
        retrieve_pass_et_password = findViewById(R.id.retrieve_pass_et_password);
        retrieve_pass_et_password1 = findViewById(R.id.retrieve_pass_et_password1);
        retrieve_pass_btn = findViewById(R.id.retrieve_pass_btn);
        retrieve_pass_tv_mCode = findViewById(R.id.retrieve_pass_tv_mCode);
        iv_back.setOnClickListener(this);
        retrieve_pass_btn.setOnClickListener(this);
        retrieve_pass_tv_mCode.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.retrieve_pass_tv_mCode:
                if (isChinaPhoneLegal(retrieve_pass_et_userName.getText().toString().trim())) {
                      // 开始倒计时 60秒，间隔1秒
                    CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(retrieve_pass_tv_mCode, 60000, 1000);
                    mCountDownTimerUtils.start();
                    //发送短信
                    getPhoneCode();
                } else {
                    Toast.makeText(RetrievePassActivity.this, "请输入正确的手机号", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.retrieve_pass_btn:
                if (!isChinaPhoneLegal(retrieve_pass_et_userName.getText().toString().trim())) {
                    Toast.makeText(RetrievePassActivity.this, "请输入正确的手机号", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(retrieve_pass_et_code.getText().toString().trim())) {
                    Toast.makeText(RetrievePassActivity.this, "请输入验证码", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(retrieve_pass_et_password.getText().toString().trim())) {
                    Toast.makeText(RetrievePassActivity.this, "请输入新密码", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(retrieve_pass_et_password1.getText().toString().trim())) {
                    Toast.makeText(RetrievePassActivity.this, "请再次输入密码", Toast.LENGTH_LONG).show();
                    return;
                }
                if (!retrieve_pass_et_password.getText().toString().trim().equals(retrieve_pass_et_password1.getText().toString().trim())){
                    Toast.makeText(RetrievePassActivity.this, "两次输入密码不一致", Toast.LENGTH_LONG).show();
                    return;
                }
                getRetrieve(retrieve_pass_et_code.getText().toString().trim(),retrieve_pass_et_userName.getText().toString().trim(),retrieve_pass_et_password.getText().toString().trim(),uuid);
                break;
        }
    }


    /**
     * 获取手机验证码
     */
    public void getPhoneCode() {
        RequestParams params = new RequestParams();
        params.put("username", retrieve_pass_et_userName.getText().toString().trim());
        HttpRequest.getRegister_Code(params, "", new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    uuid = result.getString("data");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(OkHttpException failuer) {
            }
        });

    }


    public void getRetrieve(String code,String userName,String passWord,String uuid){
        loadDialog.show();
        RequestParams params = new RequestParams();
        params.put("username",userName);
        params.put("passwordnew",passWord);
        params.put("code",code);
        params.put("uuid",uuid);
        HttpRequest.getRetrievePassword(params, "", new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                loadDialog.dismiss();
                Toast.makeText(RetrievePassActivity.this,"密码修改成功",Toast.LENGTH_LONG).show();
                // 退出登录,清除本地数据
                SPUtils.clear(RetrievePassActivity.this);
                exitApp(RetrievePassActivity.this);
            }

            @Override
            public void onFailure(OkHttpException failuer) {
                loadDialog.dismiss();
            }
        });

    }

    /**
     * 退出应用
     * @param context
     */
    public void exitApp(Context context) {// 循环结束当前所有Activity
        for (Activity ac : activitys) {
            if (ac != null) {
                ac.finish();
            }
        }
        startActivity(new Intent(RetrievePassActivity.this, LoginActivity.class));
    }
}
