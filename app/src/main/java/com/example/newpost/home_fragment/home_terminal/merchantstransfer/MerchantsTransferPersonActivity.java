package com.example.newpost.home_fragment.home_terminal.merchantstransfer;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.newpost.R;
import com.example.newpost.base.BaseActivity;
import com.example.newpost.home_fragment.home_terminal.merchantstransfer.adapter.MerchantsTransferPersonAdapter;
import com.example.newpost.home_fragment.home_terminal.merchantstransfer.bean.MerchantsTransferBean;
import com.example.newpost.net.HttpRequest;
import com.example.newpost.net.OkHttpException;
import com.example.newpost.net.RequestParams;
import com.example.newpost.net.ResponseCallback;
import com.example.newpost.net.Utils;
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
 * 创建日期：2020/11/26
 * 描述: 终端划拨选择人
 */
public class MerchantsTransferPersonActivity extends BaseActivity implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {
    private SwipeRefreshLayout swipe_refresh_layout;
    private RecyclerView listview;
    private MerchantsTransferPersonAdapter mAdapter;
    private List<MerchantsTransferBean> mData = new ArrayList<>();
    private TextView merchant_transfer_number;
    private LinearLayout iv_back;
    private EditText merchants_person_ed_search;
    @Override
    protected int getLayoutId() {
        return R.layout.merchantstransferpersonactivity;
    }

    @Override
    protected void initView() {

        swipe_refresh_layout = findViewById(R.id.swipe_refresh_layout);
        merchant_transfer_number = findViewById(R.id.merchant_transfer_number);
        listview = findViewById(R.id.listview);
        iv_back = findViewById(R.id.iv_back);
        merchants_person_ed_search = findViewById(R.id.merchants_person_ed_search);
        iv_back.setOnClickListener(this);
        search();
        initList();
    }

    private void initList() {
        swipe_refresh_layout.setOnRefreshListener(this);
        mAdapter = new MerchantsTransferPersonAdapter(mData);
        mAdapter.openLoadAnimation();
        mAdapter.setEnableLoadMore(false);
        mAdapter.setOnLoadMoreListener(this,listview);
        mAdapter.setEmptyView(LayoutInflater.from(this).inflate(R.layout.list_empty, null));
        listview.setLayoutManager(new LinearLayoutManager(this));
        listview.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(MerchantsTransferPersonActivity.this, MerchantsTransferActivity.class);
                intent.putExtra("createBy",mData.get(position).getAppUserId());
                intent.putExtra("userName",mData.get(position).getMctUserName());
                startActivity(intent);
            }
        });
        postData();

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onRefresh() {
        swipe_refresh_layout.setRefreshing(true);
        setRefresh();
        postData();
    }


    private void setRefresh() {
        mData.clear();
    }

    @Override
    public void onLoadMoreRequested() {

    }

    public void postData(){
        RequestParams params = new RequestParams();
        HttpRequest.getPosUserList(params, SPUtils.get(MerchantsTransferPersonActivity.this, "Token", "-1").toString(), new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                swipe_refresh_layout.setRefreshing(false);
                //需要转化为实体对象
                Gson gson = new GsonBuilder().serializeNulls().create();
                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    List<MerchantsTransferBean> memberList = gson.fromJson(result.getJSONArray("data").toString(),
                            new TypeToken<List<MerchantsTransferBean>>() {
                            }.getType());
                    mData.addAll(memberList);
                    merchant_transfer_number.setText("累计团队:"+mData.size()+"人");
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
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
        }
    }


    private void search() {
        merchants_person_ed_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // 当按了搜索之后关闭软键盘
                    Utils.hideKeyboard(merchants_person_ed_search);
                    Log.e("点击了","搜索");
                    return true;
                }

                return false;
            }
        });
    }
}
