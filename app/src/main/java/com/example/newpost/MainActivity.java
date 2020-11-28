package com.example.newpost;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.example.newpost.base.BaseActivity;
import com.example.newpost.fragment.HomeFragment;
import com.example.newpost.fragment.CooperFragment;
import com.example.newpost.fragment.MeFragment;
import com.example.newpost.me_fragment.MeChangePassWordActivity;
import com.example.newpost.net.HttpRequest;
import com.example.newpost.net.OkHttpException;
import com.example.newpost.net.RequestParams;
import com.example.newpost.net.ResponseCallback;
import com.example.newpost.useractivity.LoginActivity;
import com.example.newpost.utils.SPUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {
    private FrameLayout content;
    private RadioButton mainRb1;
    private RadioButton mainRb2;
    private RadioButton mainRb3;
    private RadioGroup rgFooter;
    private FragmentTransaction transaction;
    private long mPressedTime = 0;
    private HomeFragment fragment1;
    private CooperFragment fragment2;
    private MeFragment fragment3;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        getTenXunKey();
        content = findViewById(R.id.content);
        mainRb1 = findViewById(R.id.main_rb1);
        mainRb2 = findViewById(R.id.main_rb2);
        mainRb3 = findViewById(R.id.main_rb3);
        rgFooter = findViewById(R.id.rg_footer);
        rgFooter.setOnCheckedChangeListener(this);
        rgFooter.check(R.id.main_rb1);
        String a = "{\"toId\": \"201112162546e60f79063c4f288ef05f7a6cd9752e\",\"list\": \"[{posId:\\\"123\\\",version:\\\"0\\\"}]\"}";
        String str=a.toString().substring(a.toString().length()-1);
        Log.e("2222----","....."+str);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        hideAllFragment(transaction);
        switch (checkedId){
            case R.id.main_rb1:

                if (fragment1 == null) {
                    fragment1 = new HomeFragment().newInstance("");
                    transaction.add(R.id.content, fragment1);
                } else {
                    transaction.show(fragment1);
                }
                break;
            case R.id.main_rb2:

                if (fragment2 == null) {
                    fragment2 = new CooperFragment().newInstance("");
                    transaction.add(R.id.content, fragment2);
                } else {
                    transaction.show(fragment2);
                }
                break;
            case R.id.main_rb3:

                if (fragment3 == null) {
                    fragment3 = new MeFragment().newInstance("");
                    transaction.add(R.id.content, fragment3);
                } else {
                    transaction.show(fragment3);
                }
                break;
        }
        transaction.commit();
    }

    private void hideAllFragment(FragmentTransaction transaction) {
        if (fragment1 != null) this.transaction.hide(fragment1);
        if (fragment2!=null) this.transaction.hide(fragment2);
        if (fragment3!=null) this.transaction.hide(fragment3);
    }

    /**
     * 退出软件
     */
    @Override
    public void onBackPressed() {
        long mNowTime = System.currentTimeMillis();//获取第一次按键时间
        if ((mNowTime - mPressedTime) > 2000) {//比较两次按键时间差
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mPressedTime = mNowTime;
        } else {//退出程序
            this.finish();
        }
    }

    /**
     * 获取腾讯key存储到本地
     */
    public void getTenXunKey(){
        RequestParams params = new RequestParams();
        HttpRequest.getTenXunKey(params, SPUtils.get(MainActivity.this, "Token", "-1").toString(), new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    JSONObject data = new JSONObject(result.getJSONObject("data").toString());
                    if (data.getString("SecretKey") !=null && !data.getString("SecretKey").isEmpty()){
                        SPUtils.put(MainActivity.this, "secretId", data.getString("SecretId"));
                        SPUtils.put(MainActivity.this, "secretKey", data.getString("SecretKey"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(OkHttpException failuer) {
                if (failuer.getEcode() == 401) {
                    showToast("登录过期，请重新登录");
                    // 退出登录,清除本地数据
                    SPUtils.clear(mContext);
                    exitApp(MainActivity.this);
                } else {
                    showToast(failuer.getEmsg());
                }
            }
        });



    }







}
