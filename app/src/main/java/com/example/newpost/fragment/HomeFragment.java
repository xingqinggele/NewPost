package com.example.newpost.fragment;

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

import com.example.newpost.R;
import com.example.newpost.cooper_fragment.CooperExpandActivity;
import com.example.newpost.home_fragment.home_earnings.EarningsManagementActivity;
import com.example.newpost.home_fragment.home_lecture_hall.LecturehallActivity;
import com.example.newpost.home_fragment.home_list.EarningsRankingActivity;
import com.example.newpost.home_fragment.home_merchants.MerchantsActivity;
import com.example.newpost.home_fragment.home_message.MessageActivity;
import com.example.newpost.home_fragment.home_team.HomeTeamActivity;
import com.example.newpost.home_fragment.home_terminal.TerminalManagementActivity;

/**
 * 作者: qgl
 * 创建日期：2020/10/21
 * 描述:主页
 */
public class HomeFragment extends Fragment implements View.OnClickListener {
    // 商户管理
    private LinearLayout home_line_merchants;
    //终端管理
    private LinearLayout home_line_terminal;
    // 收益管理
    private LinearLayout home_line_earnings;
    // 交易管理，测试的
    private LinearLayout home_line_trading;
    // 实战讲堂
    private LinearLayout home_line_hall;
    //我的团队
    private LinearLayout home_line_team;
    // 排行榜
    private LinearLayout home_line_list;
    // 合作方
    private LinearLayout home_line_cooper;
    // 消息按钮
    private ImageView home_img_mess;



    public static HomeFragment newInstance(String requestJson) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        initView(view);
        return view;
    }


    public void initView(View view) {
        home_line_merchants = view.findViewById(R.id.home_line_merchants);
        home_line_terminal = view.findViewById(R.id.home_line_terminal);
        home_line_earnings = view.findViewById(R.id.home_line_earnings);
        home_line_trading = view.findViewById(R.id.home_line_trading);
        home_img_mess = view.findViewById(R.id.home_img_mess);
        home_line_hall = view.findViewById(R.id.home_line_hall);
        home_line_team = view.findViewById(R.id.home_line_team);
        home_line_list = view.findViewById(R.id.home_line_list);
        home_line_cooper = view.findViewById(R.id.home_line_cooper);
        home_line_merchants.setOnClickListener(this);
        home_line_terminal.setOnClickListener(this);
        home_line_earnings.setOnClickListener(this);
        home_line_trading.setOnClickListener(this);
        home_img_mess.setOnClickListener(this);
        home_line_hall.setOnClickListener(this);
        home_line_team.setOnClickListener(this);
        home_line_list.setOnClickListener(this);
        home_line_cooper.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home_line_merchants:
                // 跳转商户管理页
                startActivity(new Intent(getActivity(), MerchantsActivity.class));
                break;
            case R.id.home_line_terminal:
                //终端管理
                startActivity(new Intent(getActivity(), TerminalManagementActivity.class));
                break;
            case R.id.home_line_earnings:
                // 收益管理
                startActivity(new Intent(getActivity(), EarningsManagementActivity.class));
                break;
            case R.id.home_line_trading:
                break;
            case R.id.home_img_mess:
                // 消息列表
                startActivity(new Intent(getActivity(), MessageActivity.class));
                break;
            case R.id.home_line_hall:
                //实战讲堂
                startActivity(new Intent(getActivity(), LecturehallActivity.class));
                break;
            case R.id.home_line_team:
                //我的团队
                startActivity(new Intent(getActivity(), HomeTeamActivity.class));
                break;
            case R.id.home_line_list:
                //排行榜
                startActivity(new Intent(getActivity(), EarningsRankingActivity.class));
                break;
            case R.id.home_line_cooper:
                //合作方
                startActivity(new Intent(getActivity(), CooperExpandActivity.class));
                break;


        }
    }
}
