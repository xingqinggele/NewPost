package com.example.newpost.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.newpost.R;
import com.example.newpost.business_manger.MerchantsActivity;

/**
 * 作者: qgl
 * 创建日期：2020/10/21
 * 描述:fragment1
 */
public class Fragment1 extends Fragment implements View.OnClickListener {
    //客服
    private ImageView main_home_img_cust;
    // 消息
    private ImageView main_home_img_mess;
    //金额
    private TextView main_home_text_mony;
    // 查看明细
    private TextView main_home_txt_detail;
    //时间
    private TextView main_home_txt_time;
    // 交易笔数
    private TextView main_home_txt_trading_num;
    //分润
    private TextView main_home_txt_profits_price;
    // 返现
    private TextView main_home_txt_back_price;
    // 通告
    private TextView main_home_txt_announcement;
    //商户管理
    private LinearLayout main_home_line_merchants;
    //交易管理
    private LinearLayout main_home_line_trading;
    //收益管理
    private LinearLayout main_home_line_earnings;
    //终端管理
    private LinearLayout main_home_line_terminal;
    //提现
    private LinearLayout main_home_line_withdrawal;
    //拓展合作方
    private LinearLayout main_home_line_cooper;
    //实战讲堂
    private LinearLayout main_home_line_hall;
    //商户入网
    private LinearLayout main_home_line_stayin;

    public static Fragment1 newInstance(String requestJson) {
        Fragment1 fragment = new Fragment1();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1, container, false);
        initView(view);
        return view;
    }


    public void initView(View view) {
        main_home_img_cust = view.findViewById(R.id.main_home_img_cust);
        main_home_img_mess = view.findViewById(R.id.main_home_img_mess);
        main_home_text_mony = view.findViewById(R.id.main_home_text_mony);
        main_home_txt_detail = view.findViewById(R.id.main_home_txt_detail);
        main_home_txt_profits_price = view.findViewById(R.id.main_home_txt_profits_price);
        main_home_txt_back_price = view.findViewById(R.id.main_home_txt_back_price);
        main_home_txt_time = view.findViewById(R.id.main_home_txt_time);
        main_home_txt_trading_num = view.findViewById(R.id.main_home_txt_trading_num);
        main_home_txt_announcement = view.findViewById(R.id.main_home_txt_announcement);
        main_home_line_merchants = view.findViewById(R.id.main_home_line_merchants);
        main_home_line_trading = view.findViewById(R.id.main_home_line_trading);
        main_home_line_earnings = view.findViewById(R.id.main_home_line_earnings);
        main_home_line_terminal = view.findViewById(R.id.main_home_line_terminal);
        main_home_line_withdrawal = view.findViewById(R.id.main_home_line_withdrawal);
        main_home_line_cooper = view.findViewById(R.id.main_home_line_cooper);
        main_home_line_hall = view.findViewById(R.id.main_home_line_hall);
        main_home_line_stayin = view.findViewById(R.id.main_home_line_stayin);
        main_home_img_cust.setOnClickListener(this);
        main_home_img_mess.setOnClickListener(this);
        main_home_txt_detail.setOnClickListener(this);
        main_home_line_merchants.setOnClickListener(this);
        main_home_line_trading.setOnClickListener(this);
        main_home_line_earnings.setOnClickListener(this);
        main_home_line_terminal.setOnClickListener(this);
        main_home_line_withdrawal.setOnClickListener(this);
        main_home_line_cooper.setOnClickListener(this);
        main_home_line_hall.setOnClickListener(this);
        main_home_line_stayin.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_home_img_cust:

                break;
            case R.id.main_home_img_mess:

                break;
            case R.id.main_home_txt_detail:
                break;
            case R.id.main_home_line_merchants:
                startActivity(new Intent(getActivity(), MerchantsActivity.class));
                break;
            case R.id.main_home_line_trading:
                break;
            case R.id.main_home_line_earnings:
                break;
            case R.id.main_home_line_terminal:
                break;
            case R.id.main_home_line_withdrawal:
                break;
            case R.id.main_home_line_cooper:
                break;
            case R.id.main_home_line_hall:
                break;
            case R.id.main_home_line_stayin:
                break;


        }
    }
}
