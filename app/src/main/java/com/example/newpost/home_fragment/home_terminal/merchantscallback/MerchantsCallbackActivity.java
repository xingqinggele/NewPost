package com.example.newpost.home_fragment.home_terminal.merchantscallback;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.newpost.R;
import com.example.newpost.adapter.MyViewPageAdapter;
import com.example.newpost.base.BaseActivity;
import com.example.newpost.home_fragment.home_message.message_fragment.PersonalMessageFragment;
import com.example.newpost.home_fragment.home_message.message_fragment.SystemMessageFragment;
import com.example.newpost.home_fragment.home_terminal.merchantscallback.adapter.ChooseGridViewAdapter;
import com.example.newpost.home_fragment.home_terminal.merchantscallback.fragment.ChooseFragment;
import com.example.newpost.home_fragment.home_terminal.merchantscallback.fragment.IntervalFragment;
import com.example.newpost.views.MyGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者: qgl
 * 创建日期：2020/11/30
 * 描述: 终端回调
 */
public class MerchantsCallbackActivity extends BaseActivity implements View.OnClickListener {
    private TabLayout my_tablayout;
    private ViewPager my_viewpager;
    private LinearLayout iv_back;
    ArrayList<String> titleDatas = new ArrayList<>();
    ;
    ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();
    private DrawerLayout drawer_layout;
    private ChooseGridViewAdapter madapter;
    private List<String> mList = new ArrayList<>();
    private MyGridView gvTest;
    private int selectorPosition = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.merchantscallbackactivity;
    }

    @Override
    protected void initView() {
        my_tablayout = findViewById(R.id.my_tablayout);
        my_viewpager = findViewById(R.id.my_viewpager);
        iv_back = findViewById(R.id.iv_back);
        drawer_layout = findViewById(R.id.drawer_layout);
        iv_back.setOnClickListener(this);
        gvTest = findViewById(R.id.my_grid1);
    }

    @Override
    protected void initData() {
        titleDatas.add("选择回调");
        titleDatas.add("区间回调");
        fragmentList.add(new ChooseFragment());
        fragmentList.add(new IntervalFragment());
        init();
        /***模拟数据****/
        mList.add("全部");
        mList.add("传统POS");
        mList.add("电签POS");
        madapter = new ChooseGridViewAdapter(MerchantsCallbackActivity.this, mList);
        gvTest.setAdapter(madapter);
        gvTest.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //把点击的position传递到adapter里面去
                madapter.changeState(i);
                selectorPosition = i;
            }
        });
    }

    private void init() {
        MyViewPageAdapter myViewPageAdapter = new MyViewPageAdapter(getSupportFragmentManager(), titleDatas, fragmentList);
        my_tablayout.setSelectedTabIndicator(0);
        my_viewpager.setAdapter(myViewPageAdapter);
        my_tablayout.setupWithViewPager(my_viewpager);
        my_tablayout.setTabsFromPagerAdapter(myViewPageAdapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }


    public void setListSize(String value) {
        // 点击了筛选
        Log.e("aaa", "点击了筛选");
        drawer_layout.openDrawer(GravityCompat.END);
    }
}
