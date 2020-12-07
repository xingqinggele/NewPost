package com.example.newpost.cooper_fragment;

import android.app.Dialog;
import android.content.Intent;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.newpost.R;
import com.example.newpost.base.BaseActivity;
import com.example.newpost.dialog.MyDialog;
import com.example.newpost.net.HttpRequest;
import com.example.newpost.net.OkHttpException;
import com.example.newpost.net.RequestParams;
import com.example.newpost.net.ResponseCallback;
import com.example.newpost.utils.SPUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;

/**
 * 作者: qgl
 * 创建日期：2020/11/12
 * 描述: 合作方拓展
 */
public class CooperExpandActivity extends BaseActivity implements View.OnClickListener {
    private String pftId = ""; //修改ID


    private LinearLayout iv_back;
    private ImageView cooperexpand_tab1;
    private ImageView cooperexpand_tab2;
    private ImageView cooperexpand_btn1;
    private ImageView cooperexpand_btn2;
//    修改按钮状态
    private boolean Modify = true;
//    拓展按钮状态
    private boolean Expand = true;
//    完成按钮状态
    private boolean Complete = true;
    // 关闭按钮状态
    private boolean Shut = true;
    private TextView cooper_me_tv1;
    private TextView cooper_me_tv2;
    private TextView cooper_me_tv3;
    private TextView cooper_me_tv4;
    private TextView cooper_me_tv5;
    private TextView cooper_me_tv6;
    private TextView cooper_me_tv7;
    private TextView cooper_me_tv8;
    private TextView cooper_me_tv9;
    private TextView cooper_me_tv_hezuo;

    private TextView cooper_fx_tv1;
    private TextView cooper_fx_tv2;
    private TextView cooper_fx_tv3;
    private TextView cooper_fx_tv4;
    private TextView cooper_fx_tv5;
    private TextView cooper_fx_tv6;
    private TextView cooper_fx_tv7;
    private TextView cooper_fx_tv8;

    private TextView cooper_me_liner_tv1;
    private TextView cooper_me_liner_tv11;
    private TextView cooper_me_liner_tv2;
    private TextView cooper_me_liner_tv22;
    private TextView cooper_me_liner_tv3;
    private TextView cooper_me_liner_tv33;
    private TextView cooper_me_liner_tv4;
    private TextView cooper_me_liner_tv44;
    private TextView cooper_me_liner_tv5;
    private TextView cooper_me_liner_tv55;
    private TextView cooper_me_liner_tv6;
    private TextView cooper_me_liner_tv66;
    private TextView cooper_me_liner_tv7;
    private TextView cooper_me_liner_tv77;
    private TextView cooper_me_liner_tv8;
    private TextView cooper_me_liner_tv88;
    private TextView cooper_me_liner_tv9;
    private TextView cooper_me_liner_tv99;



    private TextView cooper_fx_liner_tv1;
    private TextView cooper_fx_liner_tv11;
    private TextView cooper_fx_liner_tv2;
    private TextView cooper_fx_liner_tv22;
    private TextView cooper_fx_liner_tv3;
    private TextView cooper_fx_liner_tv33;
    private TextView cooper_fx_liner_tv4;
    private TextView cooper_fx_liner_tv44;
    private TextView cooper_fx_liner_tv5;
    private TextView cooper_fx_liner_tv55;
    private TextView cooper_fx_liner_tv6;
    private TextView cooper_fx_liner_tv66;
    private TextView cooper_fx_liner_tv7;
    private TextView cooper_fx_liner_tv77;
    private TextView cooper_fx_liner_tv8;
    private TextView cooper_fx_liner_tv88;






    private LinearLayout cooper_me_liner_ln1;
    private LinearLayout cooper_me_liner_ln2;
    private LinearLayout cooper_me_liner_ln3;
    private LinearLayout cooper_me_liner_ln4;
    private LinearLayout cooper_me_liner_ln5;
    private LinearLayout cooper_me_liner_ln6;
    private LinearLayout cooper_me_liner_ln7;
    private LinearLayout cooper_me_liner_ln8;
    private LinearLayout cooper_me_liner_ln9;

    private LinearLayout cooper_fx_liner_ln1;
    private LinearLayout cooper_fx_liner_ln2;
    private LinearLayout cooper_fx_liner_ln3;
    private LinearLayout cooper_fx_liner_ln4;
    private LinearLayout cooper_fx_liner_ln5;
    private LinearLayout cooper_fx_liner_ln6;
    private LinearLayout cooper_fx_liner_ln7;
    private LinearLayout cooper_fx_liner_ln8;




    private LinearLayout fx_liner;
    private LinearLayout fr_liner;



    @Override
    protected int getLayoutId() {
        return R.layout.cooperexpandactivity;
    }

    @Override
    protected void initView() {
        getProfit();
        cooperexpand_tab1 = findViewById(R.id.cooperexpand_tab1);
        cooperexpand_tab2 = findViewById(R.id.cooperexpand_tab2);
        cooperexpand_btn1 = findViewById(R.id.cooperexpand_btn1);
        cooperexpand_btn2 = findViewById(R.id.cooperexpand_btn2);

        cooper_me_tv1 = findViewById(R.id.cooper_me_tv1);
        cooper_me_tv2 = findViewById(R.id.cooper_me_tv2);
        cooper_me_tv3 = findViewById(R.id.cooper_me_tv3);
        cooper_me_tv4 = findViewById(R.id.cooper_me_tv4);
        cooper_me_tv5 = findViewById(R.id.cooper_me_tv5);
        cooper_me_tv6 = findViewById(R.id.cooper_me_tv6);
        cooper_me_tv7 = findViewById(R.id.cooper_me_tv7);
        cooper_me_tv8 = findViewById(R.id.cooper_me_tv8);
        cooper_me_tv9 = findViewById(R.id.cooper_me_tv9);
        cooper_me_tv_hezuo = findViewById(R.id.cooper_me_tv_hezuo);

        cooper_fx_tv1 = findViewById(R.id.cooper_fx_tv1);
        cooper_fx_tv2 = findViewById(R.id.cooper_fx_tv2);
        cooper_fx_tv3 = findViewById(R.id.cooper_fx_tv3);
        cooper_fx_tv4 = findViewById(R.id.cooper_fx_tv4);
        cooper_fx_tv5 = findViewById(R.id.cooper_fx_tv5);
        cooper_fx_tv6 = findViewById(R.id.cooper_fx_tv6);
        cooper_fx_tv7 = findViewById(R.id.cooper_fx_tv7);
        cooper_fx_tv8 = findViewById(R.id.cooper_fx_tv8);

        cooper_me_liner_tv1 = findViewById(R.id.cooper_me_liner_tv1);
        cooper_me_liner_tv11 = findViewById(R.id.cooper_me_liner_tv11);
        cooper_me_liner_tv2 = findViewById(R.id.cooper_me_liner_tv2);
        cooper_me_liner_tv22 = findViewById(R.id.cooper_me_liner_tv22);
        cooper_me_liner_tv3 = findViewById(R.id.cooper_me_liner_tv3);
        cooper_me_liner_tv33 = findViewById(R.id.cooper_me_liner_tv33);
        cooper_me_liner_tv4 = findViewById(R.id.cooper_me_liner_tv4);
        cooper_me_liner_tv44 = findViewById(R.id.cooper_me_liner_tv44);
        cooper_me_liner_tv5 = findViewById(R.id.cooper_me_liner_tv5);
        cooper_me_liner_tv55 = findViewById(R.id.cooper_me_liner_tv55);
        cooper_me_liner_tv6 = findViewById(R.id.cooper_me_liner_tv6);
        cooper_me_liner_tv66 = findViewById(R.id.cooper_me_liner_tv66);
        cooper_me_liner_tv7 = findViewById(R.id.cooper_me_liner_tv7);
        cooper_me_liner_tv77 = findViewById(R.id.cooper_me_liner_tv77);
        cooper_me_liner_tv8 = findViewById(R.id.cooper_me_liner_tv8);
        cooper_me_liner_tv88 = findViewById(R.id.cooper_me_liner_tv88);
        cooper_me_liner_tv9 = findViewById(R.id.cooper_me_liner_tv9);
        cooper_me_liner_tv99 = findViewById(R.id.cooper_me_liner_tv99);

        cooper_fx_liner_tv1 = findViewById(R.id.cooper_fx_liner_tv1);
        cooper_fx_liner_tv11 = findViewById(R.id.cooper_fx_liner_tv11);
        cooper_fx_liner_tv2 = findViewById(R.id.cooper_fx_liner_tv2);
        cooper_fx_liner_tv22 = findViewById(R.id.cooper_fx_liner_tv22);
        cooper_fx_liner_tv3 = findViewById(R.id.cooper_fx_liner_tv3);
        cooper_fx_liner_tv33 = findViewById(R.id.cooper_fx_liner_tv33);
        cooper_fx_liner_tv4 = findViewById(R.id.cooper_fx_liner_tv4);
        cooper_fx_liner_tv44 = findViewById(R.id.cooper_fx_liner_tv44);
        cooper_fx_liner_tv5 = findViewById(R.id.cooper_fx_liner_tv5);
        cooper_fx_liner_tv55 = findViewById(R.id.cooper_fx_liner_tv55);
        cooper_fx_liner_tv6 = findViewById(R.id.cooper_fx_liner_tv6);
        cooper_fx_liner_tv66 = findViewById(R.id.cooper_fx_liner_tv66);
        cooper_fx_liner_tv7 = findViewById(R.id.cooper_fx_liner_tv7);
        cooper_fx_liner_tv77 = findViewById(R.id.cooper_fx_liner_tv77);
        cooper_fx_liner_tv8 = findViewById(R.id.cooper_fx_liner_tv8);
        cooper_fx_liner_tv88 = findViewById(R.id.cooper_fx_liner_tv88);


        fx_liner = findViewById(R.id.fx_liner);
        fr_liner = findViewById(R.id.fr_liner);




        cooper_me_liner_ln1 = findViewById(R.id.cooper_me_liner_ln1);
        cooper_me_liner_ln2 = findViewById(R.id.cooper_me_liner_ln2);
        cooper_me_liner_ln3 = findViewById(R.id.cooper_me_liner_ln3);
        cooper_me_liner_ln4 = findViewById(R.id.cooper_me_liner_ln4);
        cooper_me_liner_ln5 = findViewById(R.id.cooper_me_liner_ln5);
        cooper_me_liner_ln6 = findViewById(R.id.cooper_me_liner_ln6);
        cooper_me_liner_ln7 = findViewById(R.id.cooper_me_liner_ln7);
        cooper_me_liner_ln8 = findViewById(R.id.cooper_me_liner_ln8);
        cooper_me_liner_ln9 = findViewById(R.id.cooper_me_liner_ln9);
        cooper_me_liner_ln1.setOnClickListener(this);
        cooper_me_liner_ln2.setOnClickListener(this);
        cooper_me_liner_ln3.setOnClickListener(this);
        cooper_me_liner_ln4.setOnClickListener(this);
        cooper_me_liner_ln5.setOnClickListener(this);
        cooper_me_liner_ln6.setOnClickListener(this);
        cooper_me_liner_ln7.setOnClickListener(this);
        cooper_me_liner_ln8.setOnClickListener(this);
        cooper_me_liner_ln9.setOnClickListener(this);

        cooper_fx_liner_ln1 = findViewById(R.id.cooper_fx_liner_ln1);
        cooper_fx_liner_ln2 = findViewById(R.id.cooper_fx_liner_ln2);
        cooper_fx_liner_ln3 = findViewById(R.id.cooper_fx_liner_ln3);
        cooper_fx_liner_ln4 = findViewById(R.id.cooper_fx_liner_ln4);
        cooper_fx_liner_ln5 = findViewById(R.id.cooper_fx_liner_ln5);
        cooper_fx_liner_ln6 = findViewById(R.id.cooper_fx_liner_ln6);
        cooper_fx_liner_ln7 = findViewById(R.id.cooper_fx_liner_ln7);
        cooper_fx_liner_ln8 = findViewById(R.id.cooper_fx_liner_ln8);
        cooper_fx_liner_ln1.setOnClickListener(this);
        cooper_fx_liner_ln2.setOnClickListener(this);
        cooper_fx_liner_ln3.setOnClickListener(this);
        cooper_fx_liner_ln4.setOnClickListener(this);
        cooper_fx_liner_ln5.setOnClickListener(this);
        cooper_fx_liner_ln6.setOnClickListener(this);
        cooper_fx_liner_ln7.setOnClickListener(this);
        cooper_fx_liner_ln8.setOnClickListener(this);



        iv_back = findViewById(R.id.iv_back);
        cooperexpand_tab1.setOnClickListener(this);
        cooperexpand_tab2.setOnClickListener(this);
        cooperexpand_btn1.setOnClickListener(this);
        cooperexpand_btn2.setOnClickListener(this);
        iv_back.setOnClickListener(this);

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
            case R.id.cooperexpand_tab1:
                cooperexpand_tab1.setImageDrawable(getResources().getDrawable(R.mipmap.cooperexpand_tab01));
                cooperexpand_tab2.setImageDrawable(getResources().getDrawable(R.mipmap.cooperexpand_tab02));
                fr_liner.setVisibility(View.VISIBLE);
                fx_liner.setVisibility(View.GONE);
                break;
            case R.id.cooperexpand_tab2:
                cooperexpand_tab1.setImageDrawable(getResources().getDrawable(R.mipmap.cooperexpand_tab03));
                cooperexpand_tab2.setImageDrawable(getResources().getDrawable(R.mipmap.cooperexpand_tab04));
                fr_liner.setVisibility(View.GONE);
                fx_liner.setVisibility(View.VISIBLE);
                break;
            case R.id.cooperexpand_btn1:
                if (Modify){
                    Modify = false;
                    Expand = false;
                    cooperexpand_btn1.setImageDrawable(getResources().getDrawable(R.mipmap.cooperexpand_btn03));
                    cooperexpand_btn2.setImageDrawable(getResources().getDrawable(R.mipmap.cooperexpand_btn04));
                    cooper_me_liner_ln1.setVisibility(View.VISIBLE);
                    cooper_me_liner_ln2.setVisibility(View.VISIBLE);
                    cooper_me_liner_ln3.setVisibility(View.VISIBLE);
                    cooper_me_liner_ln4.setVisibility(View.VISIBLE);
                    cooper_me_liner_ln5.setVisibility(View.VISIBLE);
                    cooper_me_liner_ln6.setVisibility(View.VISIBLE);
                    cooper_me_liner_ln7.setVisibility(View.VISIBLE);
                    cooper_me_liner_ln8.setVisibility(View.VISIBLE);
                    cooper_me_liner_ln9.setVisibility(View.VISIBLE);
                    cooper_me_tv_hezuo.setVisibility(View.VISIBLE);


                    cooper_fx_liner_ln1.setVisibility(View.VISIBLE);
                    cooper_fx_liner_ln2.setVisibility(View.VISIBLE);
                    cooper_fx_liner_ln3.setVisibility(View.VISIBLE);
                    cooper_fx_liner_ln4.setVisibility(View.VISIBLE);
                    cooper_fx_liner_ln5.setVisibility(View.VISIBLE);
                    cooper_fx_liner_ln6.setVisibility(View.VISIBLE);
                    cooper_fx_liner_ln7.setVisibility(View.VISIBLE);
                    cooper_fx_liner_ln8.setVisibility(View.VISIBLE);


                }else {
                    // 关闭
                    cooperexpand_btn1.setImageDrawable(getResources().getDrawable(R.mipmap.cooperexpand_btn01));
                    cooperexpand_btn2.setImageDrawable(getResources().getDrawable(R.mipmap.cooperexpand_btn02));
                    Modify = true;
                    Expand = true;


                    cooper_me_liner_ln1.setVisibility(View.GONE);
                    cooper_me_liner_ln2.setVisibility(View.GONE);
                    cooper_me_liner_ln3.setVisibility(View.GONE);
                    cooper_me_liner_ln4.setVisibility(View.GONE);
                    cooper_me_liner_ln5.setVisibility(View.GONE);
                    cooper_me_liner_ln6.setVisibility(View.GONE);
                    cooper_me_liner_ln7.setVisibility(View.GONE);
                    cooper_me_liner_ln8.setVisibility(View.GONE);
                    cooper_me_liner_ln9.setVisibility(View.GONE);
                    cooper_me_tv_hezuo.setVisibility(View.GONE);

                    cooper_fx_liner_ln1.setVisibility(View.GONE);
                    cooper_fx_liner_ln2.setVisibility(View.GONE);
                    cooper_fx_liner_ln3.setVisibility(View.GONE);
                    cooper_fx_liner_ln4.setVisibility(View.GONE);
                    cooper_fx_liner_ln5.setVisibility(View.GONE);
                    cooper_fx_liner_ln6.setVisibility(View.GONE);
                    cooper_fx_liner_ln7.setVisibility(View.GONE);
                    cooper_fx_liner_ln8.setVisibility(View.GONE);
                }

                break;
            case R.id.cooperexpand_btn2:
                if (Expand){
                    //分享
                }else {
                    //完成
                    SubMint();
                }

                break;
            case R.id.cooper_me_liner_ln1:
                showDialog1("借记卡成本","1");
                break;
            case R.id.cooper_me_liner_ln2:
                showDialog1("借记卡封顶值","2");
                break;
            case R.id.cooper_me_liner_ln3:
                showDialog1("贷记卡成本","3");

                break;
            case R.id.cooper_me_liner_ln4:
                showDialog1("扫码费率成本","4");

                break;
            case R.id.cooper_me_liner_ln5:
                showDialog1("扫码D0成本","5");

                break;
            case R.id.cooper_me_liner_ln6:
                showDialog1("云闪付小额优惠成本","6");

                break;
            case R.id.cooper_me_liner_ln7:
                showDialog1("刷卡分润比例","7");

                break;
            case R.id.cooper_me_liner_ln8:
                showDialog1("扫码分润比例","8");

                break;
            case R.id.cooper_me_liner_ln9:
                showDialog1("分润服务费","9");
                break;
            case R.id.cooper_fx_liner_ln1:

                showDialog1("传统POS返现","10");

                break;
            case R.id.cooper_fx_liner_ln2:

                showDialog1("20年传统POS押金版(299元)活动","11");

                break;
            case R.id.cooper_fx_liner_ln3:

                showDialog1("20年传统POS押金版(99元)活动","12");

                break;
            case R.id.cooper_fx_liner_ln4:

                showDialog1("电签押金版首次 激活返现","13");

                break;
            case R.id.cooper_fx_liner_ln5:

                showDialog1("电签押金版非首次激活返现","14");

                break;
            case R.id.cooper_fx_liner_ln6:

                showDialog1("电签非押金版返现","15");

                break;
            case R.id.cooper_fx_liner_ln7:

                showDialog1("返现比例","16");

                break;
            case R.id.cooper_fx_liner_ln8:

                showDialog1("返现服务费","17");
                break;

        }
    }

   public void getProfit(){
       RequestParams params = new RequestParams();
       params.put("appUserId","0");
       HttpRequest.getProfit(params, SPUtils.get(CooperExpandActivity.this, "Token", "-1").toString(), new ResponseCallback() {
           @Override
           public void onSuccess(Object responseObj) {
               try {
                   JSONObject result = new JSONObject(responseObj.toString());
                   if (TextUtils.equals(result.getString("data"),"null")){
                       showToast("出现位置错误请联系管理员！");

                   }else{
                       JSONObject data = new JSONObject(result.getJSONObject("data").toString());
                       JSONObject profit = new JSONObject(data.getJSONObject("profit").toString());
                       if (TextUtils.equals(data.getString("profitSet"),"null")){
                           showToast("未设置");
                       }else {
                           JSONObject profitSet = new JSONObject(data.getJSONObject("profitSet").toString());
                           pftId = profitSet.getString("pftId");
                           //分润
                           cooper_me_liner_tv1.setText(profitSet.getString("pftDebitCost"));
                           cooper_me_liner_tv11.setText(profit.getString("pftDebitCost")+"-"+profitSet.getString("pftDebitCost"));
                           cooper_me_liner_tv2.setText(profitSet.getString("pftDebitTop"));
                           cooper_me_liner_tv22.setText(profit.getString("pftDebitTop")+"-"+profitSet.getString("pftDebitTop"));
                           cooper_me_liner_tv3.setText(profitSet.getString("pftCreditCost"));
                           cooper_me_liner_tv33.setText(profit.getString("pftCreditCost")+"-"+profitSet.getString("pftCreditCost"));
                           cooper_me_liner_tv4.setText(profitSet.getString("pftScanRateCost"));
                           cooper_me_liner_tv44.setText(profit.getString("pftScanRateCost")+"-"+profitSet.getString("pftScanRateCost"));
                           cooper_me_liner_tv5.setText(profitSet.getString("pftScanD0Cost"));
                           cooper_me_liner_tv55.setText(profit.getString("pftScanD0Cost")+"-"+profitSet.getString("pftScanD0Cost"));
                           cooper_me_liner_tv6.setText(profitSet.getString("pftCloudCost"));
                           cooper_me_liner_tv66.setText(profit.getString("pftCloudCost")+"-"+profitSet.getString("pftCloudCost"));
                           cooper_me_liner_tv7.setText(profitSet.getString("pftBrushProfit"));
                           cooper_me_liner_tv77.setText(profit.getString("pftBrushProfit")+"-"+profitSet.getString("pftBrushProfit"));
                           cooper_me_liner_tv8.setText(profitSet.getString("pftScanProfit"));
                           cooper_me_liner_tv88.setText(profit.getString("pftScanProfit")+"-"+profitSet.getString("pftScanProfit"));
                           cooper_me_liner_tv9.setText(profitSet.getString("pftServiceFee"));
                           cooper_me_liner_tv99.setText(profit.getString("pftServiceFee")+"-"+profitSet.getString("pftServiceFee"));

                           cooper_fx_liner_tv1.setText(profitSet.getString("backTradition"));
                           cooper_fx_liner_tv11.setText(profit.getString("backTradition")+"-"+profitSet.getString("backTradition"));
                           cooper_fx_liner_tv2.setText(profitSet.getString("backTraditionDepositT"));
                           cooper_fx_liner_tv22.setText(profit.getString("backTraditionDepositT")+"-"+profitSet.getString("backTraditionDepositT"));
                           cooper_fx_liner_tv3.setText(profitSet.getString("backTraditionDepositN"));
                           cooper_fx_liner_tv33.setText(profit.getString("backTraditionDepositN")+"-"+profitSet.getString("backTraditionDepositN"));
                           cooper_fx_liner_tv4.setText(profitSet.getString("backEvisaDepositFirst"));
                           cooper_fx_liner_tv44.setText(profit.getString("backEvisaDepositFirst")+"-"+profitSet.getString("backEvisaDepositFirst"));
                           cooper_fx_liner_tv5.setText(profitSet.getString("backEvisaDepositNofirst"));
                           cooper_fx_liner_tv55.setText(profit.getString("backEvisaDepositNofirst")+"-"+profitSet.getString("backEvisaDepositNofirst"));
                           cooper_fx_liner_tv6.setText(profitSet.getString("backEvisaNodeposit"));
                           cooper_fx_liner_tv66.setText(profit.getString("backEvisaNodeposit")+"-"+profitSet.getString("backEvisaNodeposit"));
                           cooper_fx_liner_tv7.setText(profitSet.getString("backRatio"));
                           cooper_fx_liner_tv77.setText(profit.getString("backRatio")+"-"+profitSet.getString("backRatio"));
                           cooper_fx_liner_tv8.setText(profitSet.getString("backFee"));
                           cooper_fx_liner_tv88.setText(profit.getString("backFee")+"-"+profitSet.getString("backFee"));
                       }
                       //分润
                       cooper_me_tv1.setText(profit.getString("pftDebitCost"));
                       cooper_me_tv2.setText(profit.getString("pftDebitTop"));
                       cooper_me_tv3.setText(profit.getString("pftCreditCost"));
                       cooper_me_tv4.setText(profit.getString("pftScanRateCost"));
                       cooper_me_tv5.setText(profit.getString("pftScanD0Cost"));
                       cooper_me_tv6.setText(profit.getString("pftCloudCost"));
                       cooper_me_tv7.setText(profit.getString("pftBrushProfit"));
                       cooper_me_tv8.setText(profit.getString("pftScanProfit"));
                       cooper_me_tv9.setText(profit.getString("pftServiceFee"));
                        //返现
                       cooper_fx_tv1.setText(profit.getString("backTradition"));
                       cooper_fx_tv2.setText(profit.getString("backTraditionDepositT"));
                       cooper_fx_tv3.setText(profit.getString("backTraditionDepositN"));
                       cooper_fx_tv4.setText(profit.getString("backEvisaDepositFirst"));
                       cooper_fx_tv5.setText(profit.getString("backEvisaDepositNofirst"));
                       cooper_fx_tv6.setText(profit.getString("backEvisaNodeposit"));
                       cooper_fx_tv7.setText(profit.getString("backRatio"));
                       cooper_fx_tv8.setText(profit.getString("backFee"));



                   }
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


    // 外勤弹框
    private void showDialog1(String title,String type) {
        View view = LayoutInflater.from(CooperExpandActivity.this).inflate(R.layout.dialog_waiqin, null);
        TextView dialog_cancel = view.findViewById(R.id.dialog_cancel);
        TextView dialog_determine = view.findViewById(R.id.dialog_determine);
        TextView dialog_title = view.findViewById(R.id.dialog_title);
        EditText dialog_ed_content = view.findViewById(R.id.dialog_ed_content);
        dialog_ed_content.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        TextView dialog_tishi = view.findViewById(R.id.dialog_tishi);

        Dialog dialog = new MyDialog(CooperExpandActivity.this, true, true, (float) 0.8).setNewView(view);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        dialog_title.setText(title);

        dialog_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog_determine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (type.equals("1")){
                    BigDecimal bd=new BigDecimal(cooper_me_tv1.getText().toString().trim());
                    BigDecimal bd1=new BigDecimal(dialog_ed_content.getText().toString().trim());
                    if(bd.compareTo(bd1) > -1){
                        dialog_tishi.setVisibility(View.GONE);
                        dialog.dismiss();
                        cooper_me_liner_tv1.setText(dialog_ed_content.getText().toString().trim());
                        cooper_me_liner_tv11.setText(cooper_me_tv1.getText().toString().trim()+"-"+dialog_ed_content.getText().toString().trim());
                    }else {

                        dialog_tishi.setVisibility(View.VISIBLE);

                    }

                }
                if (type.equals("2")){
                    BigDecimal bd=new BigDecimal(cooper_me_tv2.getText().toString().trim());
                    BigDecimal bd1=new BigDecimal(dialog_ed_content.getText().toString().trim());
                    if(bd.compareTo(bd1) > -1){
                        dialog_tishi.setVisibility(View.GONE);
                        dialog.dismiss();
                        cooper_me_liner_tv2.setText(dialog_ed_content.getText().toString().trim());
                        cooper_me_liner_tv22.setText(cooper_me_tv2.getText().toString().trim()+"-"+dialog_ed_content.getText().toString().trim());
                    }else {

                        dialog_tishi.setVisibility(View.VISIBLE);

                    }

                }
                if (type.equals("3")){
                    BigDecimal bd=new BigDecimal(cooper_me_tv3.getText().toString().trim());
                    BigDecimal bd1=new BigDecimal(dialog_ed_content.getText().toString().trim());
                    if(bd.compareTo(bd1) > -1){
                        dialog_tishi.setVisibility(View.GONE);
                        dialog.dismiss();
                        cooper_me_liner_tv3.setText(dialog_ed_content.getText().toString().trim());
                        cooper_me_liner_tv33.setText(cooper_me_tv3.getText().toString().trim()+"-"+dialog_ed_content.getText().toString().trim());
                    }else {

                        dialog_tishi.setVisibility(View.VISIBLE);

                    }

                }
                if (type.equals("4")){
                    BigDecimal bd=new BigDecimal(cooper_me_tv4.getText().toString().trim());
                    BigDecimal bd1=new BigDecimal(dialog_ed_content.getText().toString().trim());
                    if(bd.compareTo(bd1) > -1){
                        dialog_tishi.setVisibility(View.GONE);
                        dialog.dismiss();
                        cooper_me_liner_tv4.setText(dialog_ed_content.getText().toString().trim());
                        cooper_me_liner_tv44.setText(cooper_me_tv4.getText().toString().trim()+"-"+dialog_ed_content.getText().toString().trim());
                    }else {

                        dialog_tishi.setVisibility(View.VISIBLE);

                    }

                }
                if (type.equals("5")){
                    BigDecimal bd=new BigDecimal(cooper_me_tv5.getText().toString().trim());
                    BigDecimal bd1=new BigDecimal(dialog_ed_content.getText().toString().trim());
                    if(bd.compareTo(bd1) > -1){

                        dialog_tishi.setVisibility(View.GONE);
                        dialog.dismiss();
                        cooper_me_liner_tv5.setText(dialog_ed_content.getText().toString().trim());
                        cooper_me_liner_tv55.setText(cooper_me_tv5.getText().toString().trim()+"-"+dialog_ed_content.getText().toString().trim());

                    }else {
                        dialog_tishi.setVisibility(View.VISIBLE);

                    }

                }
                if (type.equals("6")){
                    BigDecimal bd=new BigDecimal(cooper_me_tv6.getText().toString().trim());
                    BigDecimal bd1=new BigDecimal(dialog_ed_content.getText().toString().trim());
                    if(bd.compareTo(bd1) > -1){
                        dialog_tishi.setVisibility(View.GONE);
                        dialog.dismiss();
                        cooper_me_liner_tv6.setText(dialog_ed_content.getText().toString().trim());
                        cooper_me_liner_tv66.setText(cooper_me_tv6.getText().toString().trim()+"-"+dialog_ed_content.getText().toString().trim());

                    }else {
                        dialog_tishi.setVisibility(View.VISIBLE);

                    }

                }
                if (type.equals("7")){
                    BigDecimal bd=new BigDecimal(cooper_me_tv7.getText().toString().trim());
                    BigDecimal bd1=new BigDecimal(dialog_ed_content.getText().toString().trim());
                    if(bd.compareTo(bd1) > -1){
                        dialog_tishi.setVisibility(View.GONE);
                        dialog.dismiss();
                        cooper_me_liner_tv7.setText(dialog_ed_content.getText().toString().trim());
                        cooper_me_liner_tv77.setText(cooper_me_tv7.getText().toString().trim()+"-"+dialog_ed_content.getText().toString().trim());

                    }else {

                        dialog_tishi.setVisibility(View.VISIBLE);

                    }

                }
                if (type.equals("8")){
                    BigDecimal bd=new BigDecimal(cooper_me_tv8.getText().toString().trim());
                    BigDecimal bd1=new BigDecimal(dialog_ed_content.getText().toString().trim());
                    if(bd.compareTo(bd1) > -1){
                        dialog_tishi.setVisibility(View.GONE);
                        dialog.dismiss();
                        cooper_me_liner_tv8.setText(dialog_ed_content.getText().toString().trim());
                        cooper_me_liner_tv88.setText(cooper_me_tv8.getText().toString().trim()+"-"+dialog_ed_content.getText().toString().trim());
                    }else {

                        dialog_tishi.setVisibility(View.VISIBLE);

                    }

                }
                if (type.equals("9")){
                    BigDecimal bd=new BigDecimal(cooper_me_tv9.getText().toString().trim());
                    BigDecimal bd1=new BigDecimal(dialog_ed_content.getText().toString().trim());
                    if(bd.compareTo(bd1) > -1){
                        dialog_tishi.setVisibility(View.GONE);
                        dialog.dismiss();
                        cooper_me_liner_tv9.setText(dialog_ed_content.getText().toString().trim());
                        cooper_me_liner_tv99.setText(cooper_me_tv9.getText().toString().trim()+"-"+dialog_ed_content.getText().toString().trim());
                    }else {


                        dialog_tishi.setVisibility(View.VISIBLE);

                    }

                }

                if (type.equals("10")){
                    BigDecimal bd=new BigDecimal(cooper_fx_tv1.getText().toString().trim());
                    BigDecimal bd1=new BigDecimal(dialog_ed_content.getText().toString().trim());
                    if(bd.compareTo(bd1) > -1){
                        dialog_tishi.setVisibility(View.GONE);
                        dialog.dismiss();
                        cooper_fx_liner_tv1.setText(dialog_ed_content.getText().toString().trim());
                        cooper_fx_liner_tv11.setText(cooper_fx_tv1.getText().toString().trim()+"-"+dialog_ed_content.getText().toString().trim());
                    }else {
                        dialog_tishi.setVisibility(View.VISIBLE);
                    }

                }
                if (type.equals("11")){
                    BigDecimal bd=new BigDecimal(cooper_fx_tv2.getText().toString().trim());
                    BigDecimal bd1=new BigDecimal(dialog_ed_content.getText().toString().trim());
                    if(bd.compareTo(bd1) > -1){
                        dialog_tishi.setVisibility(View.GONE);
                        dialog.dismiss();
                        cooper_fx_liner_tv2.setText(dialog_ed_content.getText().toString().trim());
                        cooper_fx_liner_tv22.setText(cooper_fx_tv2.getText().toString().trim()+"-"+dialog_ed_content.getText().toString().trim());
                    }else {

                        dialog_tishi.setVisibility(View.VISIBLE);

                    }

                }
                if (type.equals("12")){
                    BigDecimal bd=new BigDecimal(cooper_fx_tv3.getText().toString().trim());
                    BigDecimal bd1=new BigDecimal(dialog_ed_content.getText().toString().trim());
                    if(bd.compareTo(bd1) > -1){
                        dialog_tishi.setVisibility(View.GONE);
                        dialog.dismiss();
                        cooper_fx_liner_tv3.setText(dialog_ed_content.getText().toString().trim());
                        cooper_fx_liner_tv33.setText(cooper_fx_tv3.getText().toString().trim()+"-"+dialog_ed_content.getText().toString().trim());
                    }else {

                        dialog_tishi.setVisibility(View.VISIBLE);

                    }

                }
                if (type.equals("13")){
                    BigDecimal bd=new BigDecimal(cooper_fx_tv4.getText().toString().trim());
                    BigDecimal bd1=new BigDecimal(dialog_ed_content.getText().toString().trim());
                    if(bd.compareTo(bd1) > -1){
                        dialog_tishi.setVisibility(View.GONE);
                        dialog.dismiss();
                        cooper_fx_liner_tv4.setText(dialog_ed_content.getText().toString().trim());
                        cooper_fx_liner_tv44.setText(cooper_fx_tv4.getText().toString().trim()+"-"+dialog_ed_content.getText().toString().trim());
                    }else {

                        dialog_tishi.setVisibility(View.VISIBLE);

                    }

                }

                if (type.equals("14")){
                    BigDecimal bd=new BigDecimal(cooper_fx_tv5.getText().toString().trim());
                    BigDecimal bd1=new BigDecimal(dialog_ed_content.getText().toString().trim());
                    if(bd.compareTo(bd1) > -1){
                        dialog_tishi.setVisibility(View.GONE);
                        dialog.dismiss();
                        cooper_fx_liner_tv5.setText(dialog_ed_content.getText().toString().trim());
                        cooper_fx_liner_tv55.setText(cooper_fx_tv5.getText().toString().trim()+"-"+dialog_ed_content.getText().toString().trim());
                    }else {

                        dialog_tishi.setVisibility(View.VISIBLE);

                    }

                }

                if (type.equals("15")){
                    BigDecimal bd=new BigDecimal(cooper_fx_tv6.getText().toString().trim());
                    BigDecimal bd1=new BigDecimal(dialog_ed_content.getText().toString().trim());
                    if(bd.compareTo(bd1) > -1){
                        dialog_tishi.setVisibility(View.GONE);
                        dialog.dismiss();
                        cooper_fx_liner_tv6.setText(dialog_ed_content.getText().toString().trim());
                        cooper_fx_liner_tv66.setText(cooper_fx_tv6.getText().toString().trim()+"-"+dialog_ed_content.getText().toString().trim());
                    }else {

                        dialog_tishi.setVisibility(View.VISIBLE);

                    }

                }

                if (type.equals("16")){
                    BigDecimal bd=new BigDecimal(cooper_fx_tv7.getText().toString().trim());
                    BigDecimal bd1=new BigDecimal(dialog_ed_content.getText().toString().trim());
                    if(bd.compareTo(bd1) > -1){
                        dialog_tishi.setVisibility(View.GONE);
                        dialog.dismiss();
                        cooper_fx_liner_tv7.setText(dialog_ed_content.getText().toString().trim());
                        cooper_fx_liner_tv77.setText(cooper_fx_tv7.getText().toString().trim()+"-"+dialog_ed_content.getText().toString().trim());
                    }else {

                        dialog_tishi.setVisibility(View.VISIBLE);

                    }

                }

                if (type.equals("17")){
                    BigDecimal bd=new BigDecimal(cooper_fx_tv8.getText().toString().trim());
                    BigDecimal bd1=new BigDecimal(dialog_ed_content.getText().toString().trim());
                    if(bd.compareTo(bd1) > -1){
                        dialog_tishi.setVisibility(View.GONE);
                        dialog.dismiss();
                        cooper_fx_liner_tv8.setText(dialog_ed_content.getText().toString().trim());
                        cooper_fx_liner_tv88.setText(cooper_fx_tv8.getText().toString().trim()+"-"+dialog_ed_content.getText().toString().trim());
                    }else {

                        dialog_tishi.setVisibility(View.VISIBLE);

                    }

                }



            }
        });
    }


    public void SubMint(){
        RequestParams params = new RequestParams();
        if (!pftId.equals("")){
            params.put("pftId",pftId);
        }
        params.put("pftDebitCost",cooper_me_liner_tv1.getText().toString().trim());
        params.put("pftDebitTop",cooper_me_liner_tv2.getText().toString().trim());
        params.put("pftCreditCost",cooper_me_liner_tv3.getText().toString().trim());
        params.put("pftScanRateCost",cooper_me_liner_tv4.getText().toString().trim());
        params.put("pftScanD0Cost",cooper_me_liner_tv5.getText().toString().trim());
        params.put("pftCloudCost",cooper_me_liner_tv6.getText().toString().trim());
        params.put("pftBrushProfit",cooper_me_liner_tv7.getText().toString().trim());
        params.put("pftScanProfit",cooper_me_liner_tv8.getText().toString().trim());
        params.put("pftServiceFee",cooper_me_liner_tv9.getText().toString().trim());
        params.put("backTradition",cooper_fx_liner_tv1.getText().toString().trim());
        params.put("backTraditionDepositT",cooper_fx_liner_tv2.getText().toString().trim());
        params.put("backTraditionDepositN",cooper_fx_liner_tv3.getText().toString().trim());
        params.put("backEvisaDepositFirst",cooper_fx_liner_tv4.getText().toString().trim());
        params.put("backEvisaDepositNofirst",cooper_fx_liner_tv5.getText().toString().trim());
        params.put("backEvisaNodeposit",cooper_fx_liner_tv6.getText().toString().trim());
        params.put("backRatio",cooper_fx_liner_tv7.getText().toString().trim());
        params.put("backFee",cooper_fx_liner_tv8.getText().toString().trim());
        HttpRequest.getAddProfitSet(params, SPUtils.get(CooperExpandActivity.this, "Token", "-1").toString(), new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                startActivity(new Intent(CooperExpandActivity.this, CooperExpandScanActivity.class));

            }

            @Override
            public void onFailure(OkHttpException failuer) {
                Log.e("aa","11111111111");
                Log.e("aa",failuer.getEmsg());
                Failuer(failuer.getEcode(),failuer.getEmsg());
            }
        });



    }


}
