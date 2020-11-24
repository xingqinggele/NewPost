package com.example.newpost.base;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newpost.R;
import com.example.newpost.dialog.LoadingDialog;
import com.example.newpost.home_fragment.home_merchants.newmerchants.MerchantsDetailActivity11;
import com.example.newpost.useractivity.LoginActivity;
import com.example.newpost.utils.SPUtils;
import com.gyf.barlibrary.ImmersionBar;

import java.util.ArrayList;
import java.util.List;


/**
 * Created: qgl
 * 2020/10/19
 * 描述:基类Activity
 */
public abstract class BaseActivity extends FragmentActivity implements ViewTreeObserver.OnGlobalLayoutListener{
    protected Context mContext;
    public static List<Activity> activitys;
    private ImmersionBar mImmersionBar;//状态栏沉浸
    protected LoadingDialog loadDialog;//加载等待弹窗
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        statusBarConfig().init();
                if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if (ContextCompat.checkSelfPermission(BaseActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                //没有权限则申请权限
                ActivityCompat.requestPermissions(BaseActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
            }else {


            }
        }else {

        }
        if (getLayoutId() > 0) {
            setContentView(getLayoutId());
        }
        mContext = this;
        if (activitys == null) {
            activitys = new ArrayList<Activity>();
        }
        activitys.add(this);
        loadDialog = new LoadingDialog(this);
        initView();
        initData();
    }

    //引入布局
    protected abstract int getLayoutId();

    //初始化控件
    protected abstract void initView();

    //初始化数据
    protected abstract void initData();

    //Toast
    public void showToast(String text) {
        Toast.makeText(BaseActivity.this, text + "", Toast.LENGTH_SHORT).show();
    }

    //Log
    public void shouLog(String Interface, String text) {
        Log.e(Interface, text + "");
    }

    /**
     * 初始化沉浸式状态栏
     */
    private ImmersionBar statusBarConfig() {
            //在BaseActivity里初始化
            mImmersionBar = ImmersionBar.with(this).fitsSystemWindows(true).statusBarColor(R.color.colorPrimary)
                    .statusBarDarkFont(statusBarDarkFont())    //默认状态栏字体颜色为黑色
                    .keyboardEnable(false, WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
                            | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);  //解决软键盘与底部输入框冲突问题，默认为false，还有一个重载方法，可以指定软键盘mode
            //必须设置View树布局变化监听，否则软键盘无法顶上去，还有模式必须是SOFT_INPUT_ADJUST_PAN
            getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(this);
        return mImmersionBar;
    }
    /**
     * 获取状态栏字体颜色
     */
    public boolean statusBarDarkFont() {
        //返回false表示白色字体
        return false;
    }
    @Override
    public void onGlobalLayout() {

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
        startActivity(new Intent(mContext, LoginActivity.class));
    }

    /**
     * 腾讯对象存储错误码
     * @param msg
     * @param title
     */
    public void popTip(String msg, String title) {
        String msgShow = "错误码：" + msg;
        TextView titleTextView = new TextView(this);
        titleTextView.setText(title);
        titleTextView.setPadding(10, 10, 10, 10);
        titleTextView.setGravity(Gravity.CENTER);
        titleTextView.setTextSize(20);
        titleTextView.setTextColor(Color.rgb(0, 0, 0));

        TextView contentView = new TextView(this);
        contentView.setText(msgShow);
        contentView.setPadding(10, 10, 10, 10);
        contentView.setGravity(Gravity.CENTER);
        contentView.setTextSize(15);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCustomTitle(titleTextView);
        builder.setView(contentView);
        builder.setCancelable(true);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void Failuer(int code,String msg){
        if (code == 401) {
            showToast("登录过期，请重新登录");
            // 退出登录,清除本地数据
            SPUtils.clear(mContext);
            exitApp(mContext);
        } else {
            showToast(msg);
        }
    }

}
