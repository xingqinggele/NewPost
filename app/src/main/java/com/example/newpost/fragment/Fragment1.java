package com.example.newpost.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.newpost.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 作者: qgl
 * 创建日期：2020/10/21
 * 描述:fragment1
 */
public class Fragment1 extends Fragment {
    Unbinder unbinder;
    //客服
    @BindView(R.id.main_home_img_cust)
    ImageView mainHomeImgCust;
    //消息
    @BindView(R.id.main_home_img_mess)
    ImageView mainHomeImgMess;
    //金额
    @BindView(R.id.main_home_text_mony)
    TextView mainHomeTextMony;
    //查看明细
    @BindView(R.id.main_home_txt_detail)
    TextView mainHomeTxtDetail;
    // 首页时间
    @BindView(R.id.main_home_txt_time)
    TextView mainHomeTxtTime;
    // 交易额，笔数
    @BindView(R.id.main_home_txt_trading_num)
    TextView mainHomeTxtTradingNum;
    // 分润
    @BindView(R.id.main_home_txt_profits_price)
    TextView mainHomeTxtProfitsPrice;
    // 返现
    @BindView(R.id.main_home_txt_back_price)
    TextView mainHomeTxtBackPrice;
    // 通告
    @BindView(R.id.main_home_txt_announcement)
    TextView mainHomeTxtAnnouncement;
    // 商户入驻
    @BindView(R.id.main_home_line_merchants)
    LinearLayout mainHomeLineMerchants;
    // 交易管理
    @BindView(R.id.main_home_line_trading)
    LinearLayout mainHomeLineTrading;
    // 收益管理
    @BindView(R.id.main_home_line_earnings)
    LinearLayout mainHomeLineEarnings;
    // 终端管理
    @BindView(R.id.main_home_line_terminal)
    LinearLayout mainHomeLineTerminal;
    // 体现
    @BindView(R.id.main_home_line_withdrawal)
    LinearLayout mainHomeLineWithdrawal;
    // 拓展合作方
    @BindView(R.id.main_home_line_cooper)
    LinearLayout mainHomeLineCooper;
    // 实战讲堂
    @BindView(R.id.main_home_line_hall)
    LinearLayout mainHomeLineHall;
    // 商户入驻
    @BindView(R.id.main_home_line_stayin)
    LinearLayout mainHomeLineStayin;

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
        unbinder = ButterKnife.bind(this, view);
        return view;
    }
    @OnClick({R.id.main_home_img_cust, R.id.main_home_img_mess,R.id.main_home_txt_detail,R.id.main_home_line_merchants,R.id.main_home_line_trading,R.id.main_home_line_earnings
    ,R.id.main_home_line_terminal,R.id.main_home_line_withdrawal,R.id.main_home_line_cooper,R.id.main_home_line_hall,R.id.main_home_line_stayin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.main_home_img_cust:
                break;
            case R.id.main_home_img_mess:
                break;
            case R.id.main_home_txt_detail:
                break;
            case R.id.main_home_line_merchants:
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
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
