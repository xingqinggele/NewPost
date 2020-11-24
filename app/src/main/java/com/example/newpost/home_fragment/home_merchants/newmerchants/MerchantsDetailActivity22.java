package com.example.newpost.home_fragment.home_merchants.newmerchants;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.example.newpost.R;
import com.example.newpost.base.BaseActivity;
import com.example.newpost.bean.BankCardInfo;
import com.example.newpost.bean.JsonBean;
import com.example.newpost.cos.CosServiceFactory;
import com.example.newpost.home_fragment.home_merchants.newmerchants.bean.MerChantinformationBean;
import com.example.newpost.home_fragment.home_merchants.newmerchants.bean.Merchants2DetailBean;
import com.example.newpost.net.HttpRequest;
import com.example.newpost.net.OkHttpException;
import com.example.newpost.net.RequestParams;
import com.example.newpost.net.ResponseCallback;
import com.example.newpost.utils.CustomConfigUtil;
import com.example.newpost.utils.GetJsonDataUtil;
import com.example.newpost.utils.ImageConvertUtil;
import com.example.newpost.utils.SPUtils;
import com.example.newpost.utils.TimeUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.tencent.cos.xml.CosXmlService;
import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.exception.CosXmlServiceException;
import com.tencent.cos.xml.listener.CosXmlProgressListener;
import com.tencent.cos.xml.listener.CosXmlResultListener;
import com.tencent.cos.xml.model.CosXmlRequest;
import com.tencent.cos.xml.model.CosXmlResult;
import com.tencent.cos.xml.transfer.COSXMLUploadTask;
import com.tencent.cos.xml.transfer.TransferConfig;
import com.tencent.cos.xml.transfer.TransferManager;
import com.tencent.cos.xml.transfer.TransferState;
import com.tencent.cos.xml.transfer.TransferStateListener;
import com.tencent.ocr.sdk.common.ISDKKitResultListener;
import com.tencent.ocr.sdk.common.OcrModeType;
import com.tencent.ocr.sdk.common.OcrSDKConfig;
import com.tencent.ocr.sdk.common.OcrSDKKit;
import com.tencent.ocr.sdk.common.OcrType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者: qgl
 * 创建日期：2020/10/31
 * 描述:新增小微商户填写信息2
 */
public class MerchantsDetailActivity22 extends BaseActivity implements View.OnClickListener {

    private boolean userType = true;  // 用户进入状态，ture 新增，false 修改或查看
    private String ID_Card_positive_Type = "1"; // 1 网络地址 2 修改了本地图片
    private String ID_Card_reverse_Type = "1";
    private String ID_Card_handheld_Type = "1";
    private String Bank_URL = ""; // 银行卡照片网络地址
    private String Bank_Type = "1"; // 1 网络地址 2 修改了本地图片
    private String MCT_ID = ""; //


    //省市区选择
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    private Thread thread;
    //选择开户城市
    private static boolean isLoaded = false;
    private List<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();

    private LinearLayout iv_back; // 返回键
    private SimpleDraweeView merchant_detail2_bank; // 银行卡照片
    private Bitmap retBitmap;
    private EditText merchant_detail2_number;  // 银行卡号码
    private EditText merchants_detail2_reserved_phone;  // 银行预留手机号
    private EditText merchants_detail2_phone;  // 商户手机号
    private TextView merchant_detail_address;  // 开户行
    private TextView merchant_detail_city;     // 开户城市
    private Button merchant_detail_submit;     // 开户城市
    private EditText merchant_detail2_et_bg;     // 验证码
    private boolean isOrc = false;  //腾讯云识别银行卡是否允许
    //已经选择图片
    private List<LocalMedia> selectList = new ArrayList<>();
    //照片选择最大值
    private int maxSelectNum = 1;
    //银行范围
    private List<Merchants2DetailBean> mdBean = new ArrayList<>();
    private OptionsPickerView reasonPicker;

    private String Merchants_name;
    private String Merchants_number;
    private String Merchants_year;
    private String Merchants_adress;
    private String Merchants_deteils_adress;
    private String Merchants_type;
    private String idCard_is;
    private String idCard_no;
    private String idCard_take;
    private String secretId;  // 腾讯秘钥ID
    private String secretKey; // 腾讯秘钥key
    //对象存储，图片上传到腾讯云
    private CosXmlService cosXmlService;
    private TransferManager transferManager;
    private COSXMLUploadTask cosxmlTask;
    private String folderName = "";
    private String bucketName = "cykj-1303987307";
    private String url1;
    private String mctBankType = "";  // 开户行ID

    @Override
    protected int getLayoutId() {
        return R.layout.merchants_datailactivity2;


    }


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_DATA:
                    if (thread == null) {//如果已创建就不再重新创建子线程了

                        // Toast.makeText(MerchantsDetailActivity2.this, "Begin Parse Data", Toast.LENGTH_SHORT).show();
                        thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // 子线程中解析省市区数据
                                initJsonData();
                            }
                        });
                        thread.start();
                    }
                    break;

                case MSG_LOAD_SUCCESS:
                    //    Toast.makeText(MerchantsDetailActivity2.this, "Parse Succeed", Toast.LENGTH_SHORT).show();
                    isLoaded = true;
                    break;

                case MSG_LOAD_FAILED:
                    //  Toast.makeText(MerchantsDetailActivity2.this, "Parse Failed", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void initView() {


        secretId = SPUtils.get(MerchantsDetailActivity22.this, "secretId", "-1").toString();
        secretKey = SPUtils.get(MerchantsDetailActivity22.this, "secretKey", "-1").toString();
        cosXmlService = CosServiceFactory.getCosXmlService(this, "ap-beijing", secretId, secretKey, true);
        TransferConfig transferConfig = new TransferConfig.Builder().build();
        transferManager = new TransferManager(cosXmlService, transferConfig);
        iv_back = findViewById(R.id.iv_back);
        merchant_detail2_et_bg = findViewById(R.id.merchant_detail2_et_bg);
        merchant_detail2_bank = findViewById(R.id.merchant_detail2_bank);
        merchant_detail2_number = findViewById(R.id.merchant_detail2_number);
        merchant_detail_address = findViewById(R.id.merchant_detail_address);
        merchants_detail2_reserved_phone = findViewById(R.id.merchants_detail2_reserved_phone);
        merchant_detail_city = findViewById(R.id.merchant_detail_city);
        merchant_detail_submit = findViewById(R.id.merchant_detail_submit);
        merchants_detail2_phone = findViewById(R.id.merchants_detail2_phone);
        iv_back.setOnClickListener(this);
        merchant_detail2_bank.setOnClickListener(this);
        merchant_detail_city.setOnClickListener(this);
        merchant_detail_address.setOnClickListener(this);
        merchant_detail_submit.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        mHandler.sendEmptyMessage(MSG_LOAD_DATA);
        Merchants_name = getIntent().getStringExtra("userName");
        Merchants_number = getIntent().getStringExtra("idCard_number");
        Merchants_year = getIntent().getStringExtra("idCard_year");
        Merchants_adress = getIntent().getStringExtra("address");
        Merchants_deteils_adress = getIntent().getStringExtra("address_detail");
        Merchants_type = getIntent().getStringExtra("detail_type");
        idCard_is = getIntent().getStringExtra("idCardIs_url");
        idCard_no = getIntent().getStringExtra("idCardNo_url");
        idCard_take = getIntent().getStringExtra("idCardTask_url");
        ID_Card_positive_Type = getIntent().getStringExtra("idCardIs_type");
        ID_Card_reverse_Type = getIntent().getStringExtra("idCardNo_type");
        ID_Card_handheld_Type = getIntent().getStringExtra("idCardTask_type");
        shouLog("一" + ID_Card_positive_Type, "二" + ID_Card_reverse_Type + "三" + ID_Card_handheld_Type);
        if (getIntent().getStringExtra("userType").equals("1")) {
            userType = false;
            Bank_URL = getIntent().getStringExtra("bank_url");
            Bank_Type = "1";
            merchant_detail2_bank.setImageURI(Bank_URL);
            merchant_detail2_number.setText(getIntent().getStringExtra("bank_num"));
            merchant_detail_address.setText(getIntent().getStringExtra("bankName"));
            merchant_detail_city.setText(getIntent().getStringExtra("mctBankRegion"));
            merchants_detail2_reserved_phone.setText(getIntent().getStringExtra("mctBankPhone"));
            merchants_detail2_phone.setText(getIntent().getStringExtra("mctPhoneLo"));
            mctBankType = getIntent().getStringExtra("bankName_id");
            MCT_ID = getIntent().getStringExtra("MCT_ID");

        } else {
            userType = true;
        }

        getBankAndPlace();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.merchant_detail2_bank:
                initSdk(secretId, secretKey);
                if (isOrc) {
                    OcrSDKKit.getInstance().startProcessOcr(MerchantsDetailActivity22.this, OcrType.BankCardOCR,
                            CustomConfigUtil.getInstance().getCustomConfigUi(), new ISDKKitResultListener() {
                                @Override
                                public void onProcessSucceed(String response, String srcBase64Image, String requestId) {
                                    //回显银行卡信息
                                    getdata(response, srcBase64Image);

                                }

                                @Override
                                public void onProcessFailed(String errorCode, String message, String requestId) {
                                    popTip(errorCode, message);
                                    Log.e("requestId", requestId);
                                }
                            });
                    break;
                } else {
                    // 使用自己的获取图片方法，身份证正面
                    initSelectImage(PictureConfig.CHOOSE_REQUEST);
                }
                break;
            case R.id.merchant_detail_address:
                initReason();
                reasonPicker.show();
                break;
            case R.id.merchant_detail_city:
                if (isLoaded) {
                    showPickerView();
                } else {
                    Toast.makeText(MerchantsDetailActivity22.this, "Please waiting until the data is parsed", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.merchant_detail_submit:
                if (TextUtils.isEmpty(Bank_URL)) {
                    showToast("请上传银行卡照片");
                    return;
                }
                if (TextUtils.isEmpty(merchant_detail2_number.getText().toString().trim())) {
                    showToast("请输入结算卡号");
                    return;
                }
                if (TextUtils.isEmpty(mctBankType)) {
                    showToast("请选择开户行");
                    return;
                }

                if (TextUtils.isEmpty(merchant_detail_city.getText().toString().trim())) {
                    showToast("请选择开户城市");
                    return;
                }
                if (TextUtils.isEmpty(merchants_detail2_reserved_phone.getText().toString().trim())) {
                    showToast("请输入银行预留手机号");
                    return;
                }

                if (TextUtils.isEmpty(merchants_detail2_phone.getText().toString().trim())) {
                    showToast("请输入商户手机号");
                    return;
                }

                if (TextUtils.isEmpty(merchant_detail2_et_bg.getText().toString().trim())) {
                    showToast("请输入验证码");
                    return;
                }
                loadDialog.show();
                folderName = TimeUtils.getNowTime() + "/" + Merchants_number;
                List<MerChantinformationBean> beans = new ArrayList<>();
                MerChantinformationBean bean   = new MerChantinformationBean();
                if (!ID_Card_positive_Type.equals("1")) {
                    // 需要存储的
                    bean.setName("1");
                    bean.setUrl(idCard_is);
                    beans.add(bean);
                }
                if (!ID_Card_reverse_Type.equals("1")) {


                    bean.setName("2");
                    bean.setUrl(idCard_no);
                    beans.add(bean);
                }
                if (!ID_Card_handheld_Type.equals("1")) {

                    bean.setName("3");
                    bean.setUrl(idCard_take);
                    beans.add(bean);

                }
                if (!Bank_Type.equals("1")) {
                    bean.setName("4");
                    bean.setUrl(Bank_URL);
                    beans.add(bean);
                }

                //判断新增，修改
                if (userType){
                    if (beans.get(0).getName() == null) {
                        Log.e("------》", "没有需要上传的图片");
                        subMit();
                    } else {
                        for (int i = 0; i < beans.size(); i++) {
                            upload1(beans.get(i).getName(), folderName, beans.get(i).getUrl());
                        }
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                subMit();
                            }
                        }, 5000);
                    }

                }else {
                    if (beans.get(0).getName() == null) {
                        Log.e("------》", "没有需要上传的图片");
                        Modify_SubMit();
                    } else {
                        for (int i = 0; i < beans.size(); i++) {
                            upload1(beans.get(i).getName(), folderName, beans.get(i).getUrl());
                        }
//                        new Handler().postDelayed(new Runnable() {
//                            public void run() {
//                                Modify_SubMit();
//                            }
//                        }, 5000);
                    }
                }
                break;

        }
    }

    /**
     * 获取银行卡信息
     *
     * @param response
     * @param srcBase64Image
     */
    public void getdata(String response, String srcBase64Image) {

        try {
            if (!srcBase64Image.isEmpty()) {
                retBitmap = ImageConvertUtil.base64ToBitmap(srcBase64Image);
            }
            if (retBitmap != null) {
                merchant_detail2_bank.setImageBitmap(retBitmap);
                url1 = ImageConvertUtil.getFile(retBitmap).getCanonicalPath();
                Bank_Type = "2";
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!response.isEmpty()) {
            final BankCardInfo bankCardInfo = new Gson().fromJson(response, BankCardInfo.class);
            Log.e("银行卡", bankCardInfo.getCardNo() + "----" + bankCardInfo.getBankInfo());
            merchant_detail2_number.setText(bankCardInfo.getCardNo());
            merchant_detail_address.setText(bankCardInfo.getBankInfo());
        } else {
            //Toast.makeText(this, "result is empty", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 选取照片初始化
     */
    private void initSelectImage(int resultCode) {
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .maxSelectNum(maxSelectNum)// 最大图片选择数量
                .minSelectNum(0)// 最小选择数量
                .imageSpanCount(4)// 每行显示个数
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选
                .previewImage(true)// 是否可预览图片
                .isCamera(true)// 是否显示拍照按钮
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                //.imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
                .compress(true)// 是否压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .withAspectRatio(1, 1)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .selectionMedia(selectList)// 是否传入已选图片
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .forResult(resultCode);//结果回调onActivityResult code
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            selectList = new ArrayList<>();
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的
                    for (LocalMedia media : selectList) {
                        Log.i("图片-----》", new File(media.getPath()).length() + "");
                        Log.i("压缩图片-----》", new File(media.getCompressPath()).length() + "");
                    }
                    try {
                        Bitmap bitmap = BitmapFactory.decodeFile(selectList.get(0).getCompressPath());
                        if (bitmap != null) {
                            merchant_detail2_bank.setImageBitmap(bitmap);
                            Bank_Type = "2";
                            Bank_URL = ImageConvertUtil.getFile(bitmap).getCanonicalPath();
                        } else {
                            merchant_detail2_bank.setImageResource(R.mipmap.merchant_detail_bank_car);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;
            }
        }
    }

    /**
     * 获取开户行
     */
    public void getBankAndPlace() {
        RequestParams params = new RequestParams();
        HttpRequest.getBankAndPlace(params, SPUtils.get(MerchantsDetailActivity22.this, "Token", "-1").toString(), new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                //需要转化为实体对象
                Gson gson = new GsonBuilder().serializeNulls().create();
                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    JSONObject data = new JSONObject(result.getJSONObject("data").toString());
                    mdBean = gson.fromJson(data.getJSONArray("bankList").toString(),
                            new TypeToken<List<Merchants2DetailBean>>() {
                            }.getType());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(OkHttpException failuer) {
                if (failuer.getEcode() == 401) {
                    showToast("登录过期，请重新登录");
                    // 退出登录,清除本地数据
                    SPUtils.clear(mContext);
                    exitApp(MerchantsDetailActivity22.this);
                } else {
                    showToast(failuer.getEmsg());
                }
            }
        });


    }

    //配置银行范围的数据
    private void initReason() {
        List<String> name = new ArrayList<>();
        for (int i = 0; i < mdBean.size(); i++) {
            name.add(mdBean.get(i).getBankName());
        }
        reasonPicker = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                merchant_detail_address.setText(name.get(options1));
                mctBankType = mdBean.get(options1).getBankId();
            }
        }).setTitleText("营业范围").setContentTextSize(22).setTitleSize(22).setSubCalSize(21).build();
        reasonPicker.setPicker(name);
    }

    private void showPickerView() {// 弹出选择器
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String opt1tx = options1Items.size() > 0 ?
                        options1Items.get(options1).getPickerViewText() : "";

                String opt2tx = options2Items.size() > 0
                        && options2Items.get(options1).size() > 0 ?
                        options2Items.get(options1).get(options2) : "";
                String tx = opt1tx + opt2tx;
                merchant_detail_city.setText(tx);
            }
        })
                .setTitleText("选择开户城市")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();

        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        pvOptions.setPicker(options1Items, options2Items);//三级选择器
        pvOptions.show();
    }

    private void initJsonData() {//解析数据
        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = new GetJsonDataUtil().getJson(this, "province.json");//获取assets目录下的json文件数据
        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体
        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;
        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> cityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String cityName = jsonBean.get(i).getCityList().get(c).getName();
                cityList.add(cityName);//添加城市
                ArrayList<String> city_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                /*if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    city_AreaList.add("");
                } else {
                    city_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                }*/
                city_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                province_AreaList.add(city_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(cityList);

        }
        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);

    }

    public ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return detail;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OcrSDKKit.getInstance().release();
    }

    public void subMit() {
        RequestParams params = new RequestParams();
        params.put("mctType", "0");  //类型 0 小微，1 企业
        params.put("mctScope", Merchants_type);  // 营业类型 ID
        params.put("mctCity", Merchants_adress);  // 地区
        params.put("mctAddress", Merchants_deteils_adress); // 详细地址
        params.put("mctCardFileY", idCard_is); //身份证正面
        params.put("mctCardFileN", idCard_no); // 身份证反面
        params.put("mctCardFileHand", idCard_take); // 手持身份证
        params.put("mctUserName", Merchants_name); // 用户姓名
        params.put("mctCartNum", Merchants_number); // 身份证号码
        params.put("mctCartAging", Merchants_year); // 身份证有效期
        params.put("mctBankRegion", merchant_detail_city.getText().toString().trim()); //开户地区
        params.put("mctBankNum", merchant_detail2_number.getText().toString().trim()); // 银行卡号
        params.put("mctBankType", mctBankType); // 开户行ID
        params.put("mctBankPhone", merchants_detail2_reserved_phone.getText().toString().trim()); // 预留手机号
        params.put("mctBankFile", Bank_URL);  // 银行卡照片
        params.put("mctPhoneLo", merchants_detail2_phone.getText().toString().trim());  //验证码电话
        params.put("mctCodeLo", merchant_detail2_et_bg.getText().toString().trim());  //验证码
        HttpRequest.getSmallStay(params, SPUtils.get(MerchantsDetailActivity22.this, "Token", "-1").toString(), new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                loadDialog.dismiss();
                startActivity(new Intent(MerchantsDetailActivity22.this, MerchantsDetailSubActivity.class));
            }

            @Override
            public void onFailure(OkHttpException failuer) {
                loadDialog.dismiss();
                if (failuer.getEcode() == 401) {
                    showToast("登录过期，请重新登录");
                    // 退出登录,清除本地数据
                    SPUtils.clear(mContext);
                    exitApp(MerchantsDetailActivity22.this);
                } else {
                    showToast(failuer.getEmsg());
                }
            }
        });
    }

    /**
     * 腾讯对象存储初始化
     */
    private void initSdk(String secretId, String secretKey) {
        // 启动参数配置
        OcrType ocrType = OcrType.BankCardOCR; // 设置默认的业务识别，银行卡
        OcrSDKConfig configBuilder = OcrSDKConfig.newBuilder(secretId, secretKey, null)
                .OcrType(ocrType)
                .ModeType(OcrModeType.OCR_DETECT_MANUAL)
                .build();
        // 初始化SDK
        OcrSDKKit.getInstance().initWithConfig(this.getApplicationContext(), configBuilder);
    }
    /**
     * 上传图片,对象存储
     */
    private void upload1(String type, String newfolderName, String url) {
        if (TextUtils.isEmpty(url)) {
            return;
        }

        if (cosxmlTask == null) {
            File file = new File(url);
            String cosPath;
            if (TextUtils.isEmpty(newfolderName)) {
                cosPath = file.getName();
            } else {
                cosPath = newfolderName + File.separator + file.getName();
            }
            cosxmlTask = transferManager.upload(bucketName, cosPath, url, null);
            Log.e("参数-------》", bucketName + "----" + cosPath + "---" + url);

            cosxmlTask.setTransferStateListener(new TransferStateListener() {
                @Override
                public void onStateChanged(final TransferState state) {
                    // refreshUploadState(state);
                }
            });
            cosxmlTask.setCosXmlProgressListener(new CosXmlProgressListener() {
                @Override
                public void onProgress(final long complete, final long target) {
                    // refreshUploadProgress(complete, target);
                }
            });

            cosxmlTask.setCosXmlResultListener(new CosXmlResultListener() {
                @Override
                public void onSuccess(CosXmlRequest request, CosXmlResult result) {
                    COSXMLUploadTask.COSXMLUploadTaskResult cOSXMLUploadTaskResult = (COSXMLUploadTask.COSXMLUploadTaskResult) result;
                    cosxmlTask = null;
                    //  setResult(RESULT_OK);
                    Log.e("1111", "成功");
                    if (type.equals("1")) {
                        idCard_is = cOSXMLUploadTaskResult.accessUrl;
                        Log.e("身份证正面", cOSXMLUploadTaskResult.accessUrl);
                    } else if (type.equals("2")) {
                        idCard_no = cOSXMLUploadTaskResult.accessUrl;
                        Log.e("身份证反面", cOSXMLUploadTaskResult.accessUrl);
                    } else if (type.equals("3")) {
                        idCard_take = cOSXMLUploadTaskResult.accessUrl;
                        Log.e("手持身份证", cOSXMLUploadTaskResult.accessUrl);
                    } else {
                        Bank_URL = cOSXMLUploadTaskResult.accessUrl;
                        Log.e("银行卡", cOSXMLUploadTaskResult.accessUrl);
                    }

                }

                @Override
                public void onFail(CosXmlRequest request, CosXmlClientException exception, CosXmlServiceException serviceException) {
                    if (cosxmlTask.getTaskState() != TransferState.PAUSED) {
                        cosxmlTask = null;
                        Log.e("1111", "上传失败");

                    }
                    exception.printStackTrace();
                    serviceException.printStackTrace();
                }
            });

        }
    }

    //修改操作
    public void Modify_SubMit(){
            RequestParams params = new RequestParams();
            params.put("mctId", MCT_ID);  //ID
            params.put("mctType", "0");  //类型 0 小微，1 企业
            params.put("mctScope", Merchants_type);  // 营业类型 ID
            params.put("mctCity", Merchants_adress);  // 地区
            params.put("mctAddress", Merchants_deteils_adress); // 详细地址
            params.put("mctCardFileY", idCard_is); //身份证正面
            params.put("mctCardFileN", idCard_no); // 身份证反面
            params.put("mctCardFileHand", idCard_take); // 手持身份证
            params.put("mctUserName", Merchants_name); // 用户姓名
            params.put("mctCartNum", Merchants_number); // 身份证号码
            params.put("mctCartAging", Merchants_year); // 身份证有效期
            params.put("mctBankRegion", merchant_detail_city.getText().toString().trim()); //开户地区
            params.put("mctBankNum", merchant_detail2_number.getText().toString().trim()); // 银行卡号
            params.put("mctBankType", mctBankType); // 开户行ID
            params.put("mctBankPhone", merchants_detail2_reserved_phone.getText().toString().trim()); // 预留手机号
            params.put("mctBankFile", Bank_URL);  // 银行卡照片
            params.put("mctPhoneLo", merchants_detail2_phone.getText().toString().trim());  //验证码电话
            params.put("mctCodeLo", merchant_detail2_et_bg.getText().toString().trim());  //验证码
            HttpRequest.getModifyMerchant(params, SPUtils.get(MerchantsDetailActivity22.this, "Token", "-1").toString(), new ResponseCallback() {
                @Override
                public void onSuccess(Object responseObj) {
                    loadDialog.dismiss();
                    startActivity(new Intent(MerchantsDetailActivity22.this, MerchantsDetailSubActivity.class));
                }

                @Override
                public void onFailure(OkHttpException failuer) {
                    loadDialog.dismiss();
                    Failuer(failuer.getEcode(),failuer.getEmsg());

                }
            });
        }


}
