package com.example.newpost.home_fragment.home_lecture_hall;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.newpost.R;
import com.example.newpost.base.BaseActivity;
import com.example.newpost.home_fragment.home_lecture_hall.adapter.LecturehallAdapter;

import com.example.newpost.home_fragment.home_terminal.merchantstransfer.bean.MerchantsTransferBean;
import com.example.newpost.utils.GlideImageLoader;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者: qgl
 * 创建日期：2020/11/28
 * 描述:实战讲堂
 */
public class LecturehallActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
    private SwipeRefreshLayout swipe_refresh_layout;
    private RecyclerView listview;
    private Banner banner;
    private LinearLayout iv_back;
    private LecturehallAdapter mAdapter;
    private List<MerchantsTransferBean> mData = new ArrayList<>();
    private MerchantsTransferBean bean;
    @Override
    protected int getLayoutId() {
        return R.layout.lecturehallactivity;
    }

    @Override
    protected void initView() {
        banner = findViewById(R.id.banner);
        swipe_refresh_layout = findViewById(R.id.swipe_refresh_layout);
        listview = findViewById(R.id.listview);
        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        initList();


    }

    @Override
    protected void initData() {
        List<String> list=new ArrayList<>();
        //网络文件
        list.add("https://cykj-1303987307.cos.ap-beijing.myqcloud.com/banner/banner.png");
        list.add("https://cykj-1303987307.cos.ap-beijing.myqcloud.com/banner/banner.png");
        banner.setImages(list)
                .setImageLoader(new GlideImageLoader())
                .start();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
        }
    }

    private void initList() {
        swipe_refresh_layout.setOnRefreshListener(this);
        mAdapter = new LecturehallAdapter(mData);
        mAdapter.openLoadAnimation();
        mAdapter.setEnableLoadMore(false);
        mAdapter.setOnLoadMoreListener(this,listview);
        mAdapter.setEmptyView(LayoutInflater.from(this).inflate(R.layout.list_empty, null));
        listview.setLayoutManager(new LinearLayoutManager(this));
        listview.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(LecturehallActivity.this,LecturehallDetailsActivity.class);
                intent.putExtra("ID",position+"");
                startActivity(intent);
            }
        });
        postData();

    }

    @Override
    public void onRefresh() {
        swipe_refresh_layout.setRefreshing(true);
        mData.clear();
        postData();
    }

    @Override
    public void onLoadMoreRequested() {

    }

    public void postData(){
        swipe_refresh_layout.setRefreshing(false);
        for (int i = 0;i < 10;i++){
            bean = new MerchantsTransferBean();
            bean.setAppUserId(i+"");
            mData.add(bean);
        }
        mData.addAll(mData);
        mAdapter.loadMoreEnd();
        mAdapter.notifyDataSetChanged();
    }
}
