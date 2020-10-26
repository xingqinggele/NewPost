package com.example.newpost.useractivity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.newpost.R;
import com.example.newpost.base.BaseActivity;
import com.example.newpost.net.HttpRequest;
import com.example.newpost.net.OkHttpException;
import com.example.newpost.net.RequestParams;
import com.example.newpost.net.ResponseCallback;
import com.example.newpost.utils.CountDownTimerUtils;

import butterknife.BindView;
import butterknife.OnClick;

import static com.example.newpost.utils.Utility.isChinaPhoneLegal;

/**
 * 作者: qgl
 * 创建日期：2020/10/23
 * 描述:注册界面
 */
public class RegisterActivity extends BaseActivity {
    @BindView(R.id.regis_code)
    EditText regisCode;//验证码
    @BindView(R.id.regis_phone)
    EditText regisPhone;//手机号
    @BindView(R.id.regis_btn)
    Button regisBtn; // 注册按钮
    @BindView(R.id.mTextView)
    TextView mTextView;

    @Override
    protected int getLayoutId() {
        return R.layout.register_activity;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.regis_btn, R.id.mTextView})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mTextView:
                if (isChinaPhoneLegal(regisPhone.getText().toString().trim())) {
                    // 开始倒计时 60秒，间隔1秒
                    CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(mTextView, 60000, 1000);
                    mCountDownTimerUtils.start();
                    //发送短信
                    getPhoneCode();
                } else {
                    showToast("请输入正确的手机号");
                }
                break;
            case R.id.regis_btn:
                if (!isChinaPhoneLegal(regisPhone.getText().toString().trim())) {
                    showToast("请输入正确的手机号");
                    return;
                }
                if (TextUtils.isEmpty(regisCode.getText().toString().trim())) {
                    showToast("请输入验证码");
                    return;
                }
                getRegister();
                break;
        }
    }

    /**
     * 获取手机验证码
     */
    public void getPhoneCode() {

    }

    /**
     * 注册方法
     */
    public void getRegister() {
        RequestParams params = new RequestParams();
        params.put("phone", regisPhone.getText().toString().trim());
        params.put("code", regisCode.getText().toString().trim());
        HttpRequest.getRegister(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {

            }

            @Override
            public void onFailure(OkHttpException failuer) {
                showToast("请求失败=" + failuer.getEmsg());
            }
        });
    }

}
