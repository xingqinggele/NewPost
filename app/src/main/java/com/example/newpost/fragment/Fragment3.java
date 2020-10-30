package com.example.newpost.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.newpost.R;

import static com.example.newpost.utils.Utility.getLocalVersion;
import static com.example.newpost.utils.Utility.getVersionName;


/**
 * 作者: qgl
 * 创建日期：2020/10/21
 * 描述:fragment3
 */
public class Fragment3 extends Fragment implements View.OnClickListener {
    //客服
    private ImageView main_me_custo;
    //消息
    private ImageView main_me_messge;
    //设置
    private ImageView main_me_set;
    // 头像
    private ImageView main_me_logo;
    // 名字
    private TextView main_me_name;
    // 合作方
    private TextView main_me_cooper;
    // 余额
    private TextView main_me_balance;
    //绑定银行卡
    private TextView main_me_binding;
    //修改密码
    private RelativeLayout main_me_password;
    //意见反馈
    private RelativeLayout main_me_opinion;
    //联系我们
    private RelativeLayout main_me_about_us;
    //清理缓存
    private RelativeLayout main_me_clean;
    //检查更新
    private RelativeLayout main_me_version;
    // 退出登录
    private RelativeLayout main_me_out_login;
    //版本号
    private TextView main_me_version_code;

    public static Fragment3 newInstance(String requestJson) {
        Fragment3 fragment = new Fragment3();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment3, container, false);
        initView(view);
        initData();
        return view;
    }

    public void initView(View v) {
        main_me_version_code = v.findViewById(R.id.main_me_version_code);
        main_me_custo = v.findViewById(R.id.main_me_custo);
        main_me_messge = v.findViewById(R.id.main_me_messge);
        main_me_set = v.findViewById(R.id.main_me_set);
        main_me_logo = v.findViewById(R.id.main_me_logo);
        main_me_name = v.findViewById(R.id.main_me_name);
        main_me_cooper = v.findViewById(R.id.main_me_cooper);
        main_me_balance = v.findViewById(R.id.main_me_balance);
        main_me_binding = v.findViewById(R.id.main_me_binding);
        main_me_password = v.findViewById(R.id.main_me_password);
        main_me_opinion = v.findViewById(R.id.main_me_opinion);
        main_me_about_us = v.findViewById(R.id.main_me_about_us);
        main_me_clean = v.findViewById(R.id.main_me_clean);
        main_me_version = v.findViewById(R.id.main_me_version);
        main_me_out_login = v.findViewById(R.id.main_me_out_login);
        main_me_custo.setOnClickListener(this);
        main_me_messge.setOnClickListener(this);
        main_me_set.setOnClickListener(this);
        main_me_binding.setOnClickListener(this);
        main_me_password.setOnClickListener(this);
        main_me_opinion.setOnClickListener(this);
        main_me_about_us.setOnClickListener(this);
        main_me_clean.setOnClickListener(this);
        main_me_version.setOnClickListener(this);
        main_me_out_login.setOnClickListener(this);

    }

    public void initData(){
        main_me_version_code.setText(getVersionName(getActivity())+"");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_me_custo:
                Log.e("好使了","牛逼");
                break;
            case R.id.main_me_messge:
                break;
            case R.id.main_me_set:
                break;

            case R.id.main_me_binding:
                break;

            case R.id.main_me_password:
                break;

            case R.id.main_me_opinion:
                break;

            case R.id.main_me_about_us:
                break;

            case R.id.main_me_clean:
                break;

            case R.id.main_me_version:
                break;

            case R.id.main_me_out_login:
                break;


        }
    }
}
