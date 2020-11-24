package com.example.newpost.home_fragment.home_list;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.newpost.R;
import com.example.newpost.base.BaseActivity;
import com.example.newpost.bean.CeshiBean;
import com.example.newpost.home_fragment.home_list.adapter.EarningsrankingAdapter;
import com.gyf.barlibrary.ImmersionBar;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者: qgl
 * 创建日期：2020/11/7
 * 描述: 首页排行榜
 */
public class EarningsRankingActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
    private LinearLayout iv_back;
    private SwipeRefreshLayout earnings_ranking_swipe;
    private RecyclerView earnings_ranking_list_view;
    private List<CeshiBean> mData = new ArrayList<>();
    private CeshiBean ceshiBean;
    private EarningsrankingAdapter earningsListAdapter;
    private ImmersionBar mImmersionBar;//状态栏沉浸
    private int mCount = 1;
    @Override
    protected int getLayoutId() {
        statusBarConfig().init();
        return R.layout.earningsrankingactivity;
    }

    @Override
    protected void initView() {
        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        earnings_ranking_swipe = findViewById(R.id.earnings_ranking_swipe);
        earnings_ranking_list_view = findViewById(R.id.earnings_ranking_list_view);

        earnings_ranking_swipe.setOnRefreshListener(this);
        earningsListAdapter = new EarningsrankingAdapter(R.layout.earningsranking_list_item,mData);
        earningsListAdapter.openLoadAnimation();
        earningsListAdapter.setEnableLoadMore(true);
        earningsListAdapter.setOnLoadMoreListener(this,earnings_ranking_list_view);
        earningsListAdapter.setEmptyView(LayoutInflater.from(this).inflate(R.layout.list_empty, null));
        earnings_ranking_list_view.setLayoutManager(new LinearLayoutManager(this));
        earnings_ranking_list_view.setAdapter(earningsListAdapter);
        getData();
    }

    @Override
    protected void initData() {
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
        }
    }

    public void getData(){
        earnings_ranking_swipe.setRefreshing(false);
        for (int i = 0;i<10;i++){
            ceshiBean = new CeshiBean();
            ceshiBean.setName(i+"王**");
            ceshiBean.setType(i+1+"");
            mData.add(ceshiBean);
        }
        if (mData.size()<12){
            earningsListAdapter.loadMoreEnd();
        }else {
            earningsListAdapter.loadMoreComplete();
        }
    }

    @Override
    public void onRefresh() {
        earnings_ranking_swipe.setRefreshing(true);
        setRefresh();
        getData();
    }

    /**
     * 初始化沉浸式状态栏
     */
    private ImmersionBar statusBarConfig() {
        //在BaseActivity里初始化
        mImmersionBar = ImmersionBar.with(this).fitsSystemWindows(true).statusBarColor(R.color.earnings_ranking_tab)
                .statusBarDarkFont(statusBarDarkFont())    //默认状态栏字体颜色为黑色
                .keyboardEnable(false, WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
                        | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);  //解决软键盘与底部输入框冲突问题，默认为false，还有一个重载方法，可以指定软键盘mode
        //必须设置View树布局变化监听，否则软键盘无法顶上去，还有模式必须是SOFT_INPUT_ADJUST_PAN
        getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(this);
        return mImmersionBar;
    }

    @Override
    public void onLoadMoreRequested() {
        mCount = mCount + 1;
        getData();
    }

    private void setRefresh() {
        mData.clear();
        mCount = 1;
    }
}
