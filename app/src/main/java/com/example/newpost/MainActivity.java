package com.example.newpost;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.example.newpost.base.BaseActivity;
import com.example.newpost.fragment.Fragment1;
import com.example.newpost.fragment.Fragment2;
import com.example.newpost.fragment.Fragment3;
public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {
    private FrameLayout content;
    private RadioButton mainRb1;
    private RadioButton mainRb2;
    private RadioButton mainRb3;
    private RadioGroup rgFooter;
    private FragmentTransaction transaction;
    private long mPressedTime = 0;
    private Fragment1 fragment1;
    private Fragment2 fragment2;
    private Fragment3 fragment3;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        content = findViewById(R.id.content);
        mainRb1 = findViewById(R.id.main_rb1);
        mainRb2 = findViewById(R.id.main_rb2);
        mainRb3 = findViewById(R.id.main_rb3);
        rgFooter = findViewById(R.id.rg_footer);
        rgFooter.setOnCheckedChangeListener(this);
        rgFooter.check(R.id.main_rb1);
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
                    fragment1 = new Fragment1().newInstance("");
                    transaction.add(R.id.content, fragment1);
                } else {
                    transaction.show(fragment1);
                }
                break;
            case R.id.main_rb2:

                if (fragment2 == null) {
                    fragment2 = new Fragment2().newInstance("");
                    transaction.add(R.id.content, fragment2);
                } else {
                    transaction.show(fragment2);
                }
                break;
            case R.id.main_rb3:

                if (fragment3 == null) {
                    fragment3 = new Fragment3().newInstance("");
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
            exitApp(this);
        }
    }
}
