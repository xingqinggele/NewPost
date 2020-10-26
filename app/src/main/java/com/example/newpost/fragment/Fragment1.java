package com.example.newpost.fragment;

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
import android.widget.TextView;
import android.widget.Toast;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.newpost.R;
import com.example.newpost.adapter.CeshiAdapter;
import com.example.newpost.bean.CeshiBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者: qgl
 * 创建日期：2020/10/21
 * 描述:fragment1
 */
public class Fragment1 extends Fragment implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener{
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.recycle)
    RecyclerView recycle;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    private List<CeshiBean> list = new ArrayList<>();
    private CeshiAdapter ceshiAdapter;
    private int mCount = 1;
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
        ButterKnife.bind(this, view);
        iniTview();
        return view;
    }
    //初始化控件
    public void iniTview(){
        title.setText("标题");
        swipe.setOnRefreshListener(this);
        ceshiAdapter = new CeshiAdapter(R.layout.ceshi_list_item, list);
        //打开加载动画
        ceshiAdapter.openLoadAnimation();
        //设置启用加载更多
        ceshiAdapter.setEnableLoadMore(true);
        //设置为加载更多监听器
        ceshiAdapter.setOnLoadMoreListener(this, recycle);
        // 设置视图
        ceshiAdapter.setEmptyView(LayoutInflater.from(getActivity()).inflate(R.layout.list_empty, null));
        recycle.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycle.setAdapter(ceshiAdapter);
        // 点击item事件
        ceshiAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(getActivity(),"点击了" + list.get(position).getTitle(), Toast.LENGTH_LONG).show();
            }
        });
        getData();
    }
    /**
     * 模拟数据
     */
    public void getData() {
        swipe.setRefreshing(false);
        for (int i = 1; i <= 12; i++) {
            CeshiBean bean = new CeshiBean();
            bean.setTitle(i + "标题");
            list.add(bean);
        }
        if (list.size() < 10) {
            ceshiAdapter.loadMoreEnd();
        } else {
            ceshiAdapter.loadMoreComplete();
        }
        ceshiAdapter.notifyDataSetChanged();
    }
    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        swipe.setRefreshing(true);
        setRefresh();
        getData();
    }
    /**
     * 上拉加载
     */
    @Override
    public void onLoadMoreRequested() {
//        mCount = mCount + 1;
//        initData();
        ceshiAdapter.loadMoreEnd();
    }
    /**
     * 处理事件
     */
    private void setRefresh() {
        list.clear();
        mCount = 1;
    }
}
