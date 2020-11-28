package com.example.newpost.cooper_fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newpost.R;
import com.example.newpost.net.HttpRequest;
import com.example.newpost.net.OkHttpException;
import com.example.newpost.net.RequestParams;
import com.example.newpost.net.ResponseCallback;
import com.example.newpost.utils.EncodingUtils;
import com.example.newpost.utils.SPUtils;
import com.example.newpost.utils.StatusBarUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 作者: qgl
 * 创建日期：2020/11/12
 * 描述: 合作方拓展扫码
 */
public class CooperExpandScanActivity extends Activity implements View.OnClickListener {

    private LinearLayout iv_back;
    private ImageView cooperexpandscan_ig_btn,or_code;
    private String pftId;
    private String appUserShare;
    private TextView cooperexpandscan_tv_code;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.statusBarLightMode_white(this);
        setContentView(R.layout.cooperexpandscan_activity);
        getUserShare();

        iv_back = findViewById(R.id.iv_back);
        cooperexpandscan_ig_btn = findViewById(R.id.cooperexpandscan_ig_btn);
        or_code = findViewById(R.id.or_code);
        iv_back.setOnClickListener(this);
        cooperexpandscan_ig_btn.setOnClickListener(this);
        cooperexpandscan_tv_code = findViewById(R.id.cooperexpandscan_tv_code);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.cooperexpandscan_ig_btn:
//                startActivity(new Intent(CooperExpandScanActivity.this,CooperExpandActivity.class));
                finish();
                break;
        }
    }


    public void getUserShare(){
        RequestParams params = new RequestParams();
        HttpRequest.getUserShare(params, SPUtils.get(CooperExpandScanActivity.this, "Token", "-1").toString(), new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    JSONObject data = new JSONObject(result.getJSONObject("data").toString());
                    pftId = data.getString("pftId");
                    appUserShare = data.getString("appUserShare");
                    cooperexpandscan_tv_code.setText(appUserShare);
                    qrCode1();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(OkHttpException failuer) {

            }
        });
    }


    /**
     * 生成不带Logo的二维码点击事件
     */
    public void qrCode1(){
        if ("".equals(pftId+","+appUserShare)){
            return;
        }
        //生成二维码
        Bitmap codeBitmap = EncodingUtils.createQRCode(pftId+","+appUserShare,500,500,null);
        or_code.setImageBitmap(codeBitmap);//显示二维码
    }

    /**
     * 生成带Logo的二维码
     * @param view
     */
//    public void qrCode2 (View view){
//        if ("".equals(pftId+","+appUserShare)){
//            Toast.makeText(this, "请在输入框中输入内容", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        //获取logo资源,
//        //R.drawable.logo为logo图片
//        Bitmap logoBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.logo);
//        //生成二维码
//        Bitmap codeBitmap = EncodingUtils.createQRCode(pftId+","+appUserShare,500,500,logoBitmap);
//        or_code.setImageBitmap(codeBitmap);//显示二维码
//    }
}
