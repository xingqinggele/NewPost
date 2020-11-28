package com.example.newpost.home_fragment.home_merchants.merchantsquery;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.newpost.R;
import com.example.newpost.base.BaseActivity;
import com.example.newpost.home_fragment.home_merchants.merchantsquery.adapter.MerchantsQueryAdapter;
import com.example.newpost.home_fragment.home_merchants.merchantstransfer.MerchantsTransferActivity;
import com.example.newpost.home_fragment.home_merchants.merchantstransfer.MerchantsTransferPersonActivity;
import com.example.newpost.home_fragment.home_merchants.merchantstransfer.adapter.ManyRecyclerAdapter;
import com.example.newpost.home_fragment.home_merchants.merchantstransfer.adapter.MerchantsTransferPersonAdapter;
import com.example.newpost.home_fragment.home_merchants.merchantstransfer.bean.MermachineBean;
import com.example.newpost.net.HttpRequest;
import com.example.newpost.net.OkHttpException;
import com.example.newpost.net.RequestParams;
import com.example.newpost.net.ResponseCallback;
import com.example.newpost.utils.SPUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者: qgl
 * 创建日期：2020/11/5
 * 描述: 商户查询
 */
public class MerchantsQuery extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
    private LinearLayout iv_back;
    private LinearLayout merchantsquery_ln_screening;
    private DrawerLayout drawerLayout;
    private SwipeRefreshLayout swipe_refresh_layout;
    private RecyclerView listview;
    private MerchantsQueryAdapter mAdapter;
    private List<MermachineBean> mData = new ArrayList<>();
    @Override
    protected int getLayoutId() {
        return R.layout.merchantsquery;
    }

    @Override
    protected void initView() {
        iv_back = findViewById(R.id.iv_back);
        merchantsquery_ln_screening = findViewById(R.id.merchantsquery_ln_screening);
        swipe_refresh_layout = findViewById(R.id.swipe_refresh_layout);
        listview = findViewById(R.id.listview);
        iv_back.setOnClickListener(this);
        merchantsquery_ln_screening.setOnClickListener(this);
        initList();
    }

    @Override
    protected void initData() {

    }

    private void initList() {
        swipe_refresh_layout.setOnRefreshListener(this);
        mAdapter = new MerchantsQueryAdapter(mData);
        mAdapter.openLoadAnimation();
        mAdapter.setEnableLoadMore(false);
        mAdapter.setOnLoadMoreListener(this,listview);
        mAdapter.setEmptyView(LayoutInflater.from(this).inflate(R.layout.list_empty, null));
        listview.setLayoutManager(new LinearLayoutManager(this));
        listview.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
        postData();

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.merchantsquery_ln_screening:
                break;
        }
    }




    /*******请求终端信息**********/
    public void postData(){
        RequestParams params = new RequestParams();
        params.put("appUserId","0");
        HttpRequest.getPosList(params, SPUtils.get(MerchantsQuery.this, "Token", "-1").toString(), new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                swipe_refresh_layout.setRefreshing(false);
                Gson gson = new GsonBuilder().serializeNulls().create();
                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    List<MermachineBean> memberList = gson.fromJson(result.getJSONArray("data").toString(),
                            new TypeToken<List<MermachineBean>>() {
                            }.getType());
                    mData.addAll(memberList);
                    mAdapter.loadMoreEnd();
                    mAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(OkHttpException failuer) {
                Failuer(failuer.getEcode(),failuer.getEmsg());
            }
        });
    }

    @Override
    public void onRefresh() {
        swipe_refresh_layout.setRefreshing(true);
        setRefresh();
        postData();
    }

    @Override
    public void onLoadMoreRequested() {

    }


    private void setRefresh() {
        mData.clear();
    }
}
