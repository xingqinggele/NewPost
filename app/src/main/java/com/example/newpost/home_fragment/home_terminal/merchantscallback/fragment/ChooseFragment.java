package com.example.newpost.home_fragment.home_terminal.merchantscallback.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newpost.R;
import com.example.newpost.dialog.MyDialog;
import com.example.newpost.home_fragment.home_terminal.merchantscallback.MerchantsCallbackActivity;
import com.example.newpost.home_fragment.home_terminal.merchantscallback.adapter.ChooserRecyclerAdapter;
import com.example.newpost.home_fragment.home_terminal.merchantsquery.MerchantsQuery;
import com.example.newpost.home_fragment.home_terminal.merchantstransfer.MerchantsTransferActivity;
import com.example.newpost.home_fragment.home_terminal.merchantstransfer.MerchatsTransferSuccesActivity;
import com.example.newpost.home_fragment.home_terminal.merchantstransfer.adapter.RecyclerViewItemDecoration;
import com.example.newpost.home_fragment.home_terminal.merchantstransfer.bean.MermachineBean;
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
 * 创建日期：2020/11/30
 * 描述: 选择回调
 */
public class ChooseFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {
    private TextView tv1;
    private RecyclerView listview;
    private RecyclerViewItemDecoration recyclerViewItemDecoration;
    private ChooserRecyclerAdapter manyRecyclerAdapter;
    private CheckBox check_box;
    private boolean isType = false;
    private TextView tv;
    private TextView check_box_type;
    private TextView tv_number1;
    private Button bt_sub;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private  AppBarLayout appBarLayout ;
    /*****/
    private List<MermachineBean> beanList = new ArrayList<>();
    private List<MermachineBean> beanList_size = new ArrayList<>();
    protected Context mContext;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.choose_fragment, container, false);
        initView(view);
        mContext = getActivity();
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset >= 0) {
                    mSwipeRefreshLayout.setEnabled(true);
                } else {
                    mSwipeRefreshLayout.setEnabled(false);
                }
            }
        });
        return view;
    }


    public void initView(View view){
        recyclerViewItemDecoration = new RecyclerViewItemDecoration(getActivity(), 1);
        appBarLayout  = view.findViewById(R.id.appBarLayout);
        mSwipeRefreshLayout = view.findViewById(R.id.swipe_layout);
        listview =view.findViewById(R.id.listview);
        tv = view.findViewById(R.id.merchants_transfer_number);
        check_box = view.findViewById(R.id.check_box);
        tv1 = view.findViewById(R.id.tv1);
        tv_number1 = view.findViewById(R.id.tv_number1);
        bt_sub = view.findViewById(R.id.bt_sub);
        check_box_type = view.findViewById(R.id.check_box_type);
        tv1.setOnClickListener(this);
        bt_sub.setOnClickListener(this);
        check_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (!beanList.isEmpty()) {
                        if (isType) {
                            manyRecyclerAdapter.setAllSelect();
                            isType = false;
                            tv.setText("总计:"+0+"台");
                        } else {
                            isType = true;
                            manyRecyclerAdapter.getAllSelect();
                            tv.setText("总计:"+beanList.size()+"台");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        ini();
    }

    public void ini(){
        mSwipeRefreshLayout.setOnRefreshListener(this);
        listview.addItemDecoration(recyclerViewItemDecoration);
        listview.setLayoutManager(new LinearLayoutManager(getActivity()));
        postData();
    }

    /*************Adapter接口回调********************/
    ChooserRecyclerAdapter.OnAddClickListener onItemActionClick = new ChooserRecyclerAdapter.OnAddClickListener() {
        @Override
        public void onItemClick() {
            Log.e("啊","走了");
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        int len = 0;
                        int lenght = beanList.size();
                        if (lenght > 1) {
                            for (int i = 0; i < lenght; i++) {
                                if (manyRecyclerAdapter.ischeck.get(i, false)) {
                                    len = len + 1;
                                }
                            }
                            tv.setText("总计:"+len+"台");
                            if (len == 0) {
                                isType = false;
                                check_box_type.setText("全选");
                                check_box.setChecked(false);

                            } else if (len > 0 & len < lenght) {
                                isType = false;
                                check_box_type.setText("全选");
                                check_box.setChecked(false);

                            } else if (len == lenght) {
                                isType = true;
                                check_box_type.setText("取消");
                                check_box.setChecked(true);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };


    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        tv.setText("总计:"+"0"+"台");
        beanList.clear();
        beanList_size.clear();
        postData();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv1:
                ((MerchantsCallbackActivity)getActivity()).setListSize("Yes");
                break;
            case R.id.bt_sub:
                StringBuffer sb=new StringBuffer();
                beanList_size = new ArrayList<>();
                //获取选中的数据
                for (int i=0;i<beanList.size();i++){
                    if(manyRecyclerAdapter.ischeck.get(i, false)){
                        sb.append(beanList.get(i).getPosId().toString());
                        beanList_size.add(beanList.get(i));
                    }
                }
                if (sb.toString().equals("")){
                    Toast.makeText(getActivity(),"请选择要划拨的机器",Toast.LENGTH_LONG).show();
                }else {
                    Log.e("aaaaa","选中的值是"+sb.toString());
                    Log.e("list_size",beanList_size+"");
                    showDialog();
                }
                break;
        }
    }


    public void postData(){
        RequestParams params = new RequestParams();
        params.put("appUserId","1");
        HttpRequest.getPosList(params, SPUtils.get(getActivity(), "Token", "-1").toString(), new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                mSwipeRefreshLayout.setRefreshing(false);
                Gson gson = new GsonBuilder().serializeNulls().create();
                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    List<MermachineBean> memberList = gson.fromJson(result.getJSONArray("data").toString(),
                            new TypeToken<List<MermachineBean>>() {
                            }.getType());

                    beanList.addAll(memberList);
                    tv_number1.setText(beanList.size()+"");
                    manyRecyclerAdapter = new ChooserRecyclerAdapter(beanList, getActivity());
                    listview.setAdapter(manyRecyclerAdapter);
                    manyRecyclerAdapter.setOnAddClickListener(onItemActionClick);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(OkHttpException failuer) {

//                Failuer(failuer.getEcode(),failuer.getEmsg());
            }
        });

    }


    private void showDialog() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_merchants_transfer, null);
        TextView textView = view.findViewById(R.id.dialog_tv1);
        TextView dialog_cancel = view.findViewById(R.id.dialog_cancel);
        TextView dialog_determine = view.findViewById(R.id.dialog_determine);
        textView.setText("共"+beanList_size.size()+"台,可回调"+beanList_size.size()+"台");
        Dialog dialog = new MyDialog(getActivity(), true, true, (float) 0.7).setNewView(view);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        dialog_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog_determine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //提交
                dialog.dismiss();
                SubMit();
                Log.e("list",new Gson().toJson(beanList_size));

            }
        });
    }


    /**
     * 提交回调
     */
    public void SubMit(){
        RequestParams params = new RequestParams();
        params.put("toId","201112162546e60f79063c4f288ef05f7a6cd9752e");
        HttpRequest.updPosListFrom(params, SPUtils.get(getActivity(), "Token", "-1").toString(),beanList_size, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                Toast.makeText(getActivity(),"回调成功",Toast.LENGTH_LONG).show();
                onRefresh();
            }

            @Override
            public void onFailure(OkHttpException failuer) {
                //Failuer(failuer.getEcode(),failuer.getEmsg());
            }
        });



    }
}
