package com.example.newpost.home_fragment.home_message;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.newpost.R;
import com.example.newpost.base.BaseActivity;
import com.example.newpost.adapter.MyViewPageAdapter;
import com.example.newpost.home_fragment.home_message.message_fragment.PersonalMessageFragment;
import com.example.newpost.home_fragment.home_message.message_fragment.SystemMessageFragment;


import java.util.ArrayList;
import java.util.List;

/**
 * 作者: qgl
 * 创建日期：2020/11/13
 * 描述:消息界面
 */
public class MessageActivity extends BaseActivity implements View.OnClickListener {
    private TabLayout my_tablayout;
    private ViewPager my_viewpager;
    private LinearLayout iv_back;
    ArrayList<String> titleDatas   = new ArrayList<>();;
    ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();
    private List<TextView> numberList = new ArrayList<>();
    @Override
    protected int getLayoutId() {
        return R.layout.messageactivity;
    }

    @Override
    protected void initView() {
        my_tablayout = findViewById(R.id.my_tablayout);
        my_viewpager = findViewById(R.id.my_viewpager);
        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        titleDatas.add("系统消息");
        titleDatas.add("个人消息");
        fragmentList.add(new SystemMessageFragment());
        fragmentList.add(new PersonalMessageFragment());
        init();
    }

    private void init() {
        MyViewPageAdapter myViewPageAdapter = new MyViewPageAdapter(getSupportFragmentManager(), titleDatas, fragmentList);
        my_tablayout.setSelectedTabIndicator(0);
        my_viewpager.setAdapter(myViewPageAdapter);
        my_tablayout.setupWithViewPager(my_viewpager);
        my_tablayout.setTabsFromPagerAdapter(myViewPageAdapter);

        //替换tab中原有的样式
        for (int i = 0; i < titleDatas.size(); i++) {
            my_tablayout.getTabAt(i).setCustomView(R.layout.item_message_tab);
            TextView title = my_tablayout.getTabAt(i).getCustomView().findViewById(R.id.tv_title);
            TextView number = my_tablayout.getTabAt(i).getCustomView().findViewById(R.id.number);
            //存储后方便赋值
            numberList.add(number);
            title.setText(titleDatas.get(i));
        }
    }

    public void setListSize(int size, int position) {
        numberList.get(position).setText(size + "");
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
