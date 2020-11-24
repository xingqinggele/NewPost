package com.example.newpost.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.newpost.R;
import com.example.newpost.home_fragment.home_message.MessageActivity;
import com.example.newpost.me_fragment.MeAboutUsActivity;
import com.example.newpost.me_fragment.MeBalanceActivity;
import com.example.newpost.me_fragment.MeBankCradActivity;
import com.example.newpost.me_fragment.MeChangePassWordActivity;
import com.example.newpost.me_fragment.MeFeedBackActivity;
import com.example.newpost.me_fragment.MePersonalActivity;
import com.example.newpost.useractivity.LoginActivity;
import com.example.newpost.utils.DataCleanManager;
import com.example.newpost.utils.SPUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者: qgl
 * 创建日期：2020/10/21
 * 描述:我的
 */
public class MeFragment extends Fragment implements View.OnClickListener {

    private TextView me_clear_cache;
    private RelativeLayout me_relative_password;
    private RelativeLayout me_relative_personal;
    private RelativeLayout me_relative_opinion;
    private RelativeLayout me_relative_clean;
    private RelativeLayout me_relative_about_us;
    private RelativeLayout me_relative_out_login;
    private LinearLayout me_line_balance;
    private LinearLayout me_line_bankcard;
    private ImageView me_mess;
    private Context mContext;
    public static List<Activity> activitys;
    public static MeFragment newInstance(String requestJson) {
        MeFragment fragment = new MeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.me_fragment, container, false);
        initView(view);
        iniData();
        mContext = getActivity();
        if (activitys == null) {
            activitys = new ArrayList<Activity>();
        }
        activitys.add(getActivity());
        return view;
    }

    public void initView(View v) {
        me_clear_cache = v.findViewById(R.id.me_clear_cache);
        me_relative_password = v.findViewById(R.id.me_relative_password);
        me_relative_personal = v.findViewById(R.id.me_relative_personal);
        me_relative_opinion = v.findViewById(R.id.me_relative_opinion);
        me_relative_clean = v.findViewById(R.id.me_relative_clean);
        me_relative_about_us = v.findViewById(R.id.me_relative_about_us);
        me_line_balance = v.findViewById(R.id.me_line_balance);
        me_relative_out_login = v.findViewById(R.id.me_relative_out_login);
        me_mess = v.findViewById(R.id.me_mess);
        me_line_bankcard = v.findViewById(R.id.me_line_bankcard);
        me_relative_password.setOnClickListener(this);
        me_relative_personal.setOnClickListener(this);
        me_relative_opinion.setOnClickListener(this);
        me_relative_clean.setOnClickListener(this);
        me_relative_about_us.setOnClickListener(this);
        me_line_balance.setOnClickListener(this);
        me_relative_out_login.setOnClickListener(this);
        me_mess.setOnClickListener(this);
        me_line_bankcard.setOnClickListener(this);
    }
    // 数据配置
    public void iniData(){
        try {
            me_clear_cache.setText(DataCleanManager.getTotalCacheSize(getActivity())+"");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.me_relative_password:
                // 修改密码
                startActivity(new Intent(getActivity(), MeChangePassWordActivity.class));
                break;
            case R.id.me_relative_personal:
                //个人信息
                startActivity(new Intent(getActivity(), MePersonalActivity.class));
                break;
            case R.id.me_relative_opinion:
                // 意见反馈
                startActivity(new Intent(getActivity(), MeFeedBackActivity.class));
                break;
            case R.id.me_relative_clean:
                //清理缓存

                break;
            case R.id.me_relative_about_us:
                //关于我们
                startActivity(new Intent(getActivity(), MeAboutUsActivity.class));
                break;

            case R.id.me_line_balance:
                //我的余额
                startActivity(new Intent(getActivity(), MeBalanceActivity.class));

                break;
            case R.id.me_relative_out_login:
                // 退出登录,清除本地数据
                SPUtils.clear(mContext);
                exitApp(mContext);
                break;
            case R.id.me_mess:
                //消息列表
                startActivity(new Intent(getActivity(), MessageActivity.class));
                break;
            case R.id.me_line_bankcard:
//                我的银行卡
                startActivity(new Intent(getActivity(), MeBankCradActivity.class));
                break;
        }
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
}
