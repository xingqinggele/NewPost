package com.example.newpost.home_fragment.home_merchants.merchantstransfer;

import android.app.Dialog;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newpost.R;
import com.example.newpost.base.BaseActivity;
import com.example.newpost.dialog.MyDialog;
import com.example.newpost.home_fragment.home_merchants.merchantstransfer.adapter.ManyRecyclerAdapter;
import com.example.newpost.home_fragment.home_merchants.merchantstransfer.adapter.RecyclerViewItemDecoration;
import com.example.newpost.home_fragment.home_merchants.merchantstransfer.bean.MerchantsTransferBean;
import com.example.newpost.home_fragment.home_merchants.merchantstransfer.bean.MermachineBean;
import com.example.newpost.net.HttpRequest;
import com.example.newpost.net.OkHttpException;
import com.example.newpost.net.RequestParams;
import com.example.newpost.net.ResponseCallback;
import com.example.newpost.utils.SPUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者: qgl
 * 创建日期：2020/11/25
 * 描述:终端划拨
 */
public class MerchantsTransferActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    private RecyclerViewItemDecoration recyclerViewItemDecoration;
    private ManyRecyclerAdapter manyRecyclerAdapter;
    private RecyclerView recyclerView_Many_view_show;
    private CheckBox check_box;
    private boolean isType = false;
    private TextView tv;
    private LinearLayout iv_back;
    private Button bt_sub;
    private String createBy = "";
    private SwipeRefreshLayout swipe_refresh_layout;
    /*****/
    private List<MermachineBean> beanList = new ArrayList<>();
    private List<MermachineBean> beanList_size = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.merchantstransferactivity;
    }

    @Override
    protected void initView() {
        recyclerViewItemDecoration = new RecyclerViewItemDecoration(this, 1);
        postData();
    }

    @Override
    protected void initData() {
        createBy = getIntent().getStringExtra("createBy");
        iv_back = findViewById(R.id.iv_back);
        bt_sub = findViewById(R.id.bt_sub);
        iv_back.setOnClickListener(this);
        bt_sub.setOnClickListener(this);
        tv = (TextView) findViewById(R.id.merchants_transfer_number);
        check_box = findViewById(R.id.check_box);
        recyclerView_Many_view_show = (RecyclerView) findViewById(R.id.listview);
        swipe_refresh_layout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipe_refresh_layout.setOnRefreshListener(this);
        recyclerView_Many_view_show.addItemDecoration(recyclerViewItemDecoration);
        recyclerView_Many_view_show.setLayoutManager(new LinearLayoutManager(this));

        check_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (!beanList.isEmpty()) {
                        if (isType) {
                            manyRecyclerAdapter.setAllSelect();
                            isType = false;
                            tv.setText("总计:"+0+"条");

                        } else {
                            isType = true;
                            manyRecyclerAdapter.getAllSelect();
                            tv.setText("总计:"+beanList.size()+"条");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void setResponseState() {
        runOnUiThread(new Runnable() {
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
                            check_box.setText("全选");
                            check_box.setChecked(false);
                        } else if (len > 0 & len < lenght) {
                            isType = false;
                            check_box.setText("全选");
                            check_box.setChecked(false);

                        } else if (len == lenght) {
                            isType = true;
                            check_box.setText("取消");
                            check_box.setChecked(true);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                finish();
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
                        Toast.makeText(MerchantsTransferActivity.this,"请选择要划拨的机器",Toast.LENGTH_LONG).show();
                    }else {
                        Log.e("aaaaa","选中的值是"+sb.toString());

                        Log.e("list_size",beanList_size+"");
                        showDialog();
                    }

                break;
        }
    }

    private void showDialog() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_merchants_transfer, null);
        TextView textView = view.findViewById(R.id.dialog_tv1);
        TextView dialog_cancel = view.findViewById(R.id.dialog_cancel);
        TextView dialog_determine = view.findViewById(R.id.dialog_determine);
        textView.setText("共"+beanList_size.size()+"台,可划拨"+beanList_size.size()+"台");
        Dialog dialog = new MyDialog(MerchantsTransferActivity.this, true, true, (float) 0.7).setNewView(view);
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
     * 提交划拨
     */
    public void SubMit(){
        RequestParams params = new RequestParams();
            params.put("toId",createBy);
            HttpRequest.updPosListTo(params, SPUtils.get(MerchantsTransferActivity.this, "Token", "-1").toString(),beanList_size, new ResponseCallback() {
                @Override
                public void onSuccess(Object responseObj) {

                    onRefresh();
                }

                @Override
                public void onFailure(OkHttpException failuer) {
                    Failuer(failuer.getEcode(),failuer.getEmsg());
                }
            });



    }

    /**
     * 获取终端机器列表
     */
    public void postData(){
        RequestParams params = new RequestParams();
        params.put("appUserId","0");
        HttpRequest.getPosList(params, SPUtils.get(MerchantsTransferActivity.this, "Token", "-1").toString(), new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                swipe_refresh_layout.setRefreshing(false);

                Gson gson = new GsonBuilder().serializeNulls().create();
                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    List<MermachineBean> memberList = gson.fromJson(result.getJSONArray("data").toString(),
                            new TypeToken<List<MermachineBean>>() {
                            }.getType());
                    beanList.addAll(memberList);
                    manyRecyclerAdapter = new ManyRecyclerAdapter(beanList, MerchantsTransferActivity.this);
                    recyclerView_Many_view_show.setAdapter(manyRecyclerAdapter);
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
        tv.setText("总计:"+"0"+"台");
        beanList.clear();
        beanList_size.clear();
        postData();
    }


}
