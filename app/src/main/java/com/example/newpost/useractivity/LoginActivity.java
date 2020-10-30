package com.example.newpost.useractivity;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.newpost.MainActivity;
import com.example.newpost.R;
import com.example.newpost.base.BaseActivity;
import com.example.newpost.net.HttpRequest;
import com.example.newpost.net.OkHttpException;
import com.example.newpost.net.RequestParams;
import com.example.newpost.net.ResponseCallback;
import com.gyf.barlibrary.ImmersionBar;


/**
 * 作者: qgl
 * 创建日期：2020/10/21
 * 描述:登录界面
 */
public class LoginActivity extends BaseActivity {
//    @BindView(R.id.et_userName)
//    EditText etUserName;
//    @BindView(R.id.et_pass)
//    EditText etPass;
//    @BindView(R.id.bt_login)
//    Button btLogin;
    private ImmersionBar mImmersionBar;//状态栏沉浸
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected int getLayoutId() {
        statusBarConfig().init();
        return R.layout.login_activity;
    }
    /**
     * 初始化沉浸式状态栏
     */
    private ImmersionBar statusBarConfig() {
        //在BaseActivity里初始化
        mImmersionBar = ImmersionBar.with(this).fitsSystemWindows(true).statusBarColor(R.color.white)
                .statusBarDarkFont(true)    //默认状态栏字体颜色为黑色
                .keyboardEnable(false, WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
                        | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);  //解决软键盘与底部输入框冲突问题，默认为false，还有一个重载方法，可以指定软键盘mode
        //必须设置View树布局变化监听，否则软键盘无法顶上去，还有模式必须是SOFT_INPUT_ADJUST_PAN
        getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(this);
        return mImmersionBar;
    }
    @Override
    protected void initView() {

    }
    @Override
    protected void initData() {

    }
//    @OnClick(R.id.bt_login)
//    public void onViewClicked() {
//        if (TextUtils.isEmpty(etUserName.getText().toString().trim())){
//            showToast("请输入账户");
//            return;
//        }
//        if (TextUtils.isEmpty(etPass.getText().toString().trim())){
//            showToast("请输入密码");
//            return;
//        }
//        getLogin();
//        startActivity(new Intent(LoginActivity.this, MainActivity.class));
//    }
    /**
     * 登录方法
     */
    public void getLogin(){
        RequestParams params = new RequestParams();
//        params.put("user",etUserName.getText().toString().trim());
//        params.put("pass",etPass.getText().toString().trim());
        HttpRequest.getLogin(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {

            }

            @Override
            public void onFailure(OkHttpException failuer) {

            }
        });

    }


}
