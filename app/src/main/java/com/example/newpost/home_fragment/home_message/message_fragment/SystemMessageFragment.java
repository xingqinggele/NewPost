package com.example.newpost.home_fragment.home_message.message_fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.newpost.R;
import com.example.newpost.adapter.WriteReportAdapter;
import com.example.newpost.ceshi.Bean;
import com.example.newpost.home_fragment.home_message.MessageActivity;
import com.example.newpost.home_fragment.home_message.MessageDetelisActivity;
import com.example.newpost.home_fragment.home_message.adapter.SystemMeAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者: qgl
 * 创建日期：2020/11/13
 * 描述:系统消息
 */
public class SystemMessageFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    private SystemMeAdapter systemMeAdapter;
    private SwipeRefreshLayout system_message_swipe;
    private RecyclerView system_message_list_view;
    private List<Bean> mData = new ArrayList<>();
    private int mCount = 1;
    private Bean ceshiBean;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.systemmessage_fragment, container, false);
        initView(view);
        return view;
    }

    public void initView(View v){
        system_message_swipe = v.findViewById(R.id.system_message_swipe);
        system_message_list_view = v.findViewById(R.id.system_message_list_view);
        initList();
        initData();
    }

    public void initList(){
        system_message_swipe.setOnRefreshListener(this);
        systemMeAdapter = new SystemMeAdapter(R.layout.system_message_list_item,mData);
        systemMeAdapter.openLoadAnimation();
        systemMeAdapter.setEnableLoadMore(true);
        systemMeAdapter.setOnLoadMoreListener(this,system_message_list_view);
        systemMeAdapter.setEmptyView(LayoutInflater.from(getActivity()).inflate(R.layout.list_empty, null));
        system_message_list_view.setLayoutManager(new LinearLayoutManager(getActivity()));
        system_message_list_view.setAdapter(systemMeAdapter);
        systemMeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //点击Item事件
                Intent intent = new Intent(getActivity(), MessageDetelisActivity.class);
                intent.putExtra("ID",position+"");
                startActivity(intent);
            }
        });

    }

    @Override
    public void onRefresh() {
        system_message_swipe.setRefreshing(true);
        setRefresh();
        initData();
    }

    private void setRefresh() {
        mData.clear();
        mCount = 1;
    }

    @Override
    public void onLoadMoreRequested() {
        mCount = mCount + 1;
        initData();
    }



    public void initData() {
        system_message_swipe.setRefreshing(false);
        for (int i = 0;i<10;i++){
            ceshiBean = new Bean();
            ceshiBean.setName(i+"王**");
            ceshiBean.setPrice(i+1);
            ceshiBean.setPass(i+1+"元");
            mData.add(ceshiBean);
        }

        ((MessageActivity)getActivity()).setListSize(mData.size(),0);
        if (mData.size()<12){
            systemMeAdapter.loadMoreEnd();
        }else {
            systemMeAdapter.loadMoreComplete();
        }
    }
}
