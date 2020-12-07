package com.example.newpost.home_fragment.home_merchants.newmerchants;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.example.newpost.R;
import com.example.newpost.base.BaseActivity;
import com.example.newpost.bean.IdCardInfo;
import com.example.newpost.bean.JsonBean;
import com.example.newpost.home_fragment.home_merchants.newmerchants.bean.MerchantsDetailBean;
import com.example.newpost.home_fragment.home_merchants.newmerchants.state.MerchantsDetailSubActivity;
import com.example.newpost.net.HttpRequest;
import com.example.newpost.net.OkHttpException;
import com.example.newpost.net.RequestParams;
import com.example.newpost.net.ResponseCallback;
import com.example.newpost.utils.GetJsonDataUtil;
import com.example.newpost.utils.ImageConvertUtil;
import com.example.newpost.utils.SPUtils;
import com.example.newpost.utils.Utility;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
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
import java.util.Date;
import java.util.List;

import static com.example.newpost.utils.checkID.validateCard;

/**
 * 作者: qgl
 * 创建日期：2020/10/31
 * 描述:新增企业商户1
 */
public class MerchantsBigDetailActivity11 extends BaseActivity implements View.OnClickListener {
    private String MerchantsBig_IDCard_IS = ""; //身份证正面
    private String MerchantsBig_IDCard_IS_TYPE = "1"; //身份证类型
    private String MerchantsBig_IDCard_NO = ""; //身份证反面
    private String MerchantsBig_IDCard_NO_TYPE = "1"; //身份证类型
    private String MerchantsBig_Bank = ""; //银行卡照片
    private String MerchantsBig_Bank_TYPE = "1"; //银行卡类型
    private String MerchantsBig_Shouquan = ""; //授权书照片
    private String MerchantsBig_Shouquan_TYPE = "1"; //授权书类型
    private boolean userType = true; // 用户进入状态，ture 新增，false 修改或查看
    private TextView merchant_detail_audit;
    private String mctScope = "1"; // 营业类型ID
    private String MerchantsBig_License = ""; // 营业执照照片
    private String MerchantsBig_LicenseType = "1"; // 营业执照类型
    private String MerchantsBig_handheld_License = ""; // 手持营业执照照片
    private String MerchantsBig_handheld_LicenseType = "1"; // 手持营业执照类型
    private String MerchantsBig_IMG1 = "";
    private String MerchantsBig_IMG1_TYPE = "1";
    private String MerchantsBig_IMG2 = "";
    private String MerchantsBig_IMG2_TYPE = "1";
    private String MerchantsBig_IMG3 = "";
    private String MerchantsBig_IMG3_TYPE = "1";
    private String MerchantsBig_IMG4 = "";
    private String MerchantsBig_IMG4_TYPE = "1";
    private String MCT_ID = ""; //详情ID
    private String mctSettleType = "0";//结算类型
    private String mctSettleLegal = "0";//是否法人
    private String mctBankRegion = "";//开户地区
    private String mctBankNum = "";//开户账号
    private String mctBankType = "";//开户行
    private String mctBankType_ID = "";//开户行ID
    private String mctBankUser = "";//开户人姓名
    private String mctBankPhone = "";//开户人预留手机号

    private String mctPermitType = "";//营业执照类型
    private String mctPermitNum = "";//营业执照编号
    private String mctPermitAging = "";//营业执照有效期
    private String mctPhoneLo = "";//营业执照手机号


    private LinearLayout iv_back;
    private Button merchant_big_detail_submit;
    private TextView merchant_big_detail_address;
    private EditText merchant_big_detail_id_name;
    private EditText merchant_big_detail_id_number;
    private EditText merchant_big_detail_name;
    private TextView merchant_big_detail_card_year;
    private TextView merchant_big_detail_scope;
    private EditText merchant_big_detail_detail_address;

    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    private List<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private Thread thread;
    private static boolean isLoaded = false;
    //身份证正面
    private SimpleDraweeView merchant_big_detail_card_is;
    // 身份证反面
    private SimpleDraweeView merchant_big_detail_card_the;
    // 腾讯云ORC是否启用状态
    private boolean OrcType = true;  // true 启用，false不启用
    private static final int Id_POSITIVE = 00201;   // 身份证正面
    private static final int Id_REVERSE = 00202;    // 身份证反面
    private String secretId;  // 腾讯秘钥ID
    private String secretKey; // 腾讯秘钥key
    private String IdName; //身份证名字
    private String IdNumber; // 身份证号码
    private String IdValidDate; //身份证有效期

    //已经选择图片
    private List<LocalMedia> selectList = new ArrayList<>();
    //照片选择最大值
    private int maxSelectNum = 1;
    //营业范围数据配置
    private List<MerchantsDetailBean> mdBean = new ArrayList<>();
    // 选择控件
    private OptionsPickerView reasonPicker;


    @Override
    protected int getLayoutId() {
        return R.layout.merchants_big_datailactivity;
    }

    @Override
    protected void initView() {
        getMerchant();

        secretId = SPUtils.get(MerchantsBigDetailActivity11.this, "secretId", "-1").toString();
        secretKey = SPUtils.get(MerchantsBigDetailActivity11.this, "secretKey", "-1").toString();
        iv_back = findViewById(R.id.iv_back);
        merchant_big_detail_submit = findViewById(R.id.merchant_big_detail_submit);
        merchant_detail_audit = findViewById(R.id.merchant_detail_audit);
        iv_back.setOnClickListener(this);
        merchant_big_detail_submit.setOnClickListener(this);
        merchant_big_detail_address = findViewById(R.id.merchant_big_detail_address);
        merchant_big_detail_card_is = findViewById(R.id.merchant_big_detail_card_is);
        merchant_big_detail_card_the = findViewById(R.id.merchant_big_detail_card_the);
        merchant_big_detail_id_name = findViewById(R.id.merchant_big_detail_id_name);
        merchant_big_detail_id_number = findViewById(R.id.merchant_big_detail_id_number);
        merchant_big_detail_card_year = findViewById(R.id.merchant_big_detail_card_year);
        merchant_big_detail_scope = findViewById(R.id.merchant_big_detail_scope);
        merchant_big_detail_name = findViewById(R.id.merchant_big_detail_name);
        merchant_big_detail_detail_address = findViewById(R.id.merchant_big_detail_detail_address);
        merchant_big_detail_address.setOnClickListener(this);
        merchant_big_detail_card_is.setOnClickListener(this);
        merchant_big_detail_card_the.setOnClickListener(this);
        merchant_big_detail_card_year.setOnClickListener(this);
        merchant_big_detail_scope.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        mHandler.sendEmptyMessage(MSG_LOAD_DATA);
        getBankAndPlace();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.merchant_big_detail_submit:
                if (TextUtils.isEmpty(MerchantsBig_IDCard_IS)) {
                    showToast("请上传身份证正面照片");
                    return;
                }
                if (TextUtils.isEmpty(MerchantsBig_IDCard_NO)) {
                    showToast("请上传身份证反面照片");
                    return;
                }
                if (TextUtils.isEmpty(merchant_big_detail_name.getText().toString().trim())) {
                    showToast("请输入商户全称");
                    return;
                }
                if (TextUtils.isEmpty(mctScope)) {
                    showToast("请选择营业范围");
                    return;
                }
                if (TextUtils.isEmpty(merchant_big_detail_address.getText().toString().trim())) {
                    showToast("请选择商铺归属地");
                    return;
                }
                if (TextUtils.isEmpty(merchant_big_detail_detail_address.getText().toString().trim())) {
                    showToast("请输入商铺详细地址");
                    return;
                }
                if (TextUtils.isEmpty(merchant_big_detail_id_name.getText().toString().trim())) {
                    showToast("请输入姓名");
                    return;
                }
                if (TextUtils.isEmpty(merchant_big_detail_id_number.getText().toString().trim())) {
                    showToast("请输入身份证号");
                    return;
                }
                if (TextUtils.isEmpty(merchant_big_detail_card_year.getText().toString().trim())) {
                    showToast("请输入身份证有效期");
                    return;
                }
                if (!validateCard(merchant_big_detail_id_number.getText().toString().trim())) {
                    showToast("请选输入正确的身份证号");
                    return;
                }

                Intent intent = new Intent(MerchantsBigDetailActivity11.this, MerchantsBigDetailActivity22.class);
                intent.putExtra("big_shopName", merchant_big_detail_name.getText().toString().trim());
                intent.putExtra("big_shopScope", mctScope);
                intent.putExtra("big_shopAddress", merchant_big_detail_address.getText().toString().trim());
                intent.putExtra("big_shopDetailAddress", merchant_big_detail_detail_address.getText().toString().trim());
                intent.putExtra("big_shopIdis", MerchantsBig_IDCard_IS);
                intent.putExtra("big_shopIdno", MerchantsBig_IDCard_NO);
                intent.putExtra("big_shopIdno_type", MerchantsBig_IDCard_IS_TYPE);
                intent.putExtra("big_shopIdno_type", MerchantsBig_IDCard_NO_TYPE);
                intent.putExtra("big_shopUserName", merchant_big_detail_id_name.getText().toString().trim());
                intent.putExtra("big_shopUserNumber", merchant_big_detail_id_number.getText().toString().trim());
                intent.putExtra("big_shopYear", merchant_big_detail_card_year.getText().toString().trim());
                if (userType) {
                    intent.putExtra("userType", "0");
                } else {
                    intent.putExtra("userType", "1");
                    intent.putExtra("MerchantsBig_Bank", MerchantsBig_Bank);
                    intent.putExtra("MerchantsBig_Bank_TYPE", MerchantsBig_Bank_TYPE);
                    intent.putExtra("MerchantsBig_Shouquan", MerchantsBig_Shouquan);
                    intent.putExtra("MerchantsBig_Shouquan_TYPE", MerchantsBig_Shouquan_TYPE);
                    intent.putExtra("MerchantsBig_License", MerchantsBig_License);
                    intent.putExtra("MerchantsBig_LicenseType", MerchantsBig_LicenseType);
                    intent.putExtra("MerchantsBig_handheld_License", MerchantsBig_handheld_License);
                    intent.putExtra("MerchantsBig_handheld_LicenseType", MerchantsBig_handheld_LicenseType);
                    intent.putExtra("MerchantsBig_IMG1", MerchantsBig_IMG1);
                    intent.putExtra("MerchantsBig_IMG1_TYPE", MerchantsBig_IMG1_TYPE);
                    intent.putExtra("MerchantsBig_IMG2", MerchantsBig_IMG2);
                    intent.putExtra("MerchantsBig_IMG2_TYPE", MerchantsBig_IMG2_TYPE);
                    intent.putExtra("MerchantsBig_IMG3", MerchantsBig_IMG3);
                    intent.putExtra("MerchantsBig_IMG3_TYPE", MerchantsBig_IMG3_TYPE);
                    intent.putExtra("MerchantsBig_IMG4", MerchantsBig_IMG4);
                    intent.putExtra("MerchantsBig_IMG4_TYPE", MerchantsBig_IMG4_TYPE);

                    intent.putExtra("MCT_ID", MCT_ID);
                    intent.putExtra("mctSettleType", mctSettleType);
                    intent.putExtra("mctSettleLegal", mctSettleLegal);
                    intent.putExtra("mctBankRegion", mctBankRegion);
                    intent.putExtra("mctBankNum", mctBankNum);
                    intent.putExtra("mctBankType", mctBankType);
                    intent.putExtra("mctBankType_ID", mctBankType_ID);
                    intent.putExtra("mctBankUser", mctBankUser);
                    intent.putExtra("mctBankPhone", mctBankPhone);
                    intent.putExtra("mctPermitType", mctPermitType);
                    intent.putExtra("mctPermitNum", mctPermitNum);
                    intent.putExtra("mctPermitAging", mctPermitAging);
                    intent.putExtra("mctPhoneLo", mctPhoneLo);
                }
                startActivity(intent);
                break;
            case R.id.merchant_big_detail_address:
                if (isLoaded) {
                    showPickerView();
                } else {
                    Toast.makeText(MerchantsBigDetailActivity11.this, "Please waiting until the data is parsed", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.merchant_big_detail_card_is:
                initSdk(secretId, secretKey);
                if (OrcType) {
                    //弹出界面
                    OcrSDKKit.getInstance().startProcessOcr(MerchantsBigDetailActivity11.this, OcrType.IDCardOCR_FRONT, null,
                            new ISDKKitResultListener() {
                                @Override
                                public void onProcessSucceed(String response, String srcBase64Image, String requestId) {
                                    IdCardInfo tempIdCardInfo = new Gson().fromJson(response, IdCardInfo.class);
                                    Log.e("response", tempIdCardInfo.getRequestId());
                                    Bitmap bitmap = ImageConvertUtil.base64ToBitmap(srcBase64Image);
                                    try {
                                        if (bitmap != null)
                                            merchant_big_detail_card_is.setImageBitmap(bitmap);
                                        MerchantsBig_IDCard_IS = ImageConvertUtil.getFile(bitmap).getCanonicalPath();
                                        MerchantsBig_IDCard_IS_TYPE = "2";
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    Log.e("姓名" + tempIdCardInfo.getName(), "身份证号码" + tempIdCardInfo.getIdNum());
                                    IdName = tempIdCardInfo.getName();
                                    IdNumber = tempIdCardInfo.getIdNum();
                                    setResultListData();
                                }

                                @Override
                                public void onProcessFailed(String errorCode, String message, String requestId) {
                                    popTip(errorCode, message);
                                    Log.e("111requestId", requestId);
                                    IdName = "";
                                    IdNumber = "";
                                }
                            });
                } else {
                    // 使用自己的获取图片方法，身份证正面
                    initSelectImage(Id_POSITIVE);
                }
                break;
            case R.id.merchant_big_detail_card_the:
                if (OrcType) {
                    initSdk(secretId, secretKey);
                    //身份证反面
                    OcrSDKKit.getInstance().startProcessOcr(MerchantsBigDetailActivity11.this, OcrType.IDCardOCR_BACK, null,
                            new ISDKKitResultListener() {
                                @Override
                                public void onProcessSucceed(String response, String srcBase64Image, String requestId) {
                                    IdCardInfo tempIdCardInfo = new Gson().fromJson(response, IdCardInfo.class);
                                    Log.e("response", tempIdCardInfo.getRequestId());
                                    Bitmap bitmap = ImageConvertUtil.base64ToBitmap(srcBase64Image);
                                    try {
                                        if (bitmap != null)
                                            merchant_big_detail_card_the.setImageBitmap(bitmap);
                                        MerchantsBig_IDCard_NO = ImageConvertUtil.getFile(bitmap).getCanonicalPath();
                                        MerchantsBig_IDCard_NO_TYPE = "2";
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    merchant_big_detail_card_the.setImageBitmap(bitmap);
                                    IdValidDate = tempIdCardInfo.getValidDate();
                                    setResultListData();
                                }

                                @Override
                                public void onProcessFailed(String errorCode, String message, String requestId) {
                                    popTip(errorCode, message);
                                    Log.e("222222", requestId);
                                    IdValidDate = "";
                                }
                            });
                } else {
                    // 使用自己的获取图片方法，身份证反面
                    initSelectImage(Id_REVERSE);
                }
                break;
            case R.id.merchant_big_detail_scope:
                initReason();
                reasonPicker.show();
                break;
            case R.id.merchant_big_detail_card_year:
                selectTime();
                break;

        }
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_DATA:
                    if (thread == null) {//如果已创建就不再重新创建子线程了

                        Toast.makeText(MerchantsBigDetailActivity11.this, "Begin Parse Data", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(MerchantsBigDetailActivity11.this, "Parse Succeed", Toast.LENGTH_SHORT).show();
                    isLoaded = true;
                    break;

                case MSG_LOAD_FAILED:
                    Toast.makeText(MerchantsBigDetailActivity11.this, "Parse Failed", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };


    private void initReason() {
        List<String> name = new ArrayList<>();
        for (int i = 0; i < mdBean.size(); i++) {
            name.add(mdBean.get(i).getPlaceName());
        }
        reasonPicker = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                merchant_big_detail_scope.setText(name.get(options1));
                mctScope = mdBean.get(options1).getPlaceId();
            }
        }).setTitleText("营业范围").setContentTextSize(22).setTitleSize(22).setSubCalSize(21).build();
        reasonPicker.setPicker(name);
    }

    private void selectTime() {
        TimePickerView pvTime = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                //设置时间
                merchant_big_detail_card_year.setText(Utility.getTime(date));
            }
        }).setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("年", "月", "日", "时", "分", "秒")
                .build();
        pvTime.show();
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

                String opt3tx = options2Items.size() > 0
                        && options3Items.get(options1).size() > 0
                        && options3Items.get(options1).get(options2).size() > 0 ?
                        options3Items.get(options1).get(options2).get(options3) : "";

                String tx = opt1tx + opt2tx + opt3tx;
                merchant_big_detail_address.setText(tx);
            }
        })
                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();

        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
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

            /**
             * 添加地区数据
             */
            options3Items.add(province_AreaList);
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

    /**
     * 腾讯卡片识别初始化
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

    // 配置识别出来的数据
    private void setResultListData() {
        if (IdName != null && !IdName.isEmpty()) {
            merchant_big_detail_id_name.setText(IdName);
            merchant_big_detail_id_number.setText(IdNumber);
        }
        if (IdValidDate != null && !IdValidDate.isEmpty()) {
            merchant_big_detail_card_year.setText(IdValidDate.substring(11));
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
//                .setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
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
                case Id_POSITIVE:
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
                            merchant_big_detail_card_is.setImageBitmap(bitmap);
                            MerchantsBig_IDCard_IS = ImageConvertUtil.getFile(bitmap).getCanonicalPath();
                            MerchantsBig_IDCard_IS_TYPE = "2";
                        } else {
                            merchant_big_detail_card_is.setImageResource(R.mipmap.merchant_detail_car1);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case Id_REVERSE:
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
                            merchant_big_detail_card_the.setImageBitmap(bitmap);
                            MerchantsBig_IDCard_NO = ImageConvertUtil.getFile(bitmap).getCanonicalPath();
                            MerchantsBig_IDCard_NO_TYPE = "2";
                        } else {
                            merchant_big_detail_card_the.setImageResource(R.mipmap.merchant_detail_car2);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }

    /**
     * 获取营业范围
     */
    public void getBankAndPlace() {
        RequestParams params = new RequestParams();
        HttpRequest.getBankAndPlace(params, SPUtils.get(MerchantsBigDetailActivity11.this, "Token", "-1").toString(), new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                //需要转化为实体对象
                Gson gson = new GsonBuilder().serializeNulls().create();
                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    JSONObject data = new JSONObject(result.getJSONObject("data").toString());
                    mdBean = gson.fromJson(data.getJSONArray("placeList").toString(),
                            new TypeToken<List<MerchantsDetailBean>>() {
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
                    exitApp(MerchantsBigDetailActivity11.this);
                } else {
                    showToast(failuer.getEmsg());
                }
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OcrSDKKit.getInstance().release();
    }


    /*********开始--->查询是否入住过******/
    /**
     * 查询商户信息
     */
    public void getMerchant() {
        RequestParams params = new RequestParams();
        HttpRequest.getMerchant(params, SPUtils.get(MerchantsBigDetailActivity11.this, "Token", "-1").toString(), new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    if (TextUtils.equals(result.getString("data"), "null")) {
                        Log.e("aaaaaaaa", "aaaaaaa");
                        // 新增用户
                        userType = true;
                    } else {
                        // 修改或者是查看
                        Log.e("bbbbbbbbbbb", "aaaaaaa");
                        userType = false;
                        JSONObject data = new JSONObject(result.getJSONObject("data").toString());
                        String mctAuditState = data.getString("mctAuditState");
//                        if (mctAuditState.equals("1")) {
//                            // 审核中不让查看
//                            merchant_detail_audit.setVisibility(View.GONE);
//                            startActivity(new Intent(MerchantsBigDetailActivity11.this, MerchantsDetailSubActivity.class));
//                            finish();
//                        }else if (mctAuditState.equals("2")) {
//                            // 审核成功可以查看，开始赋值
//                            merchant_detail_audit.setVisibility(View.GONE);
//                        } else {
//                            // 审核驳回可以查看，开始赋值
//                            merchant_detail_audit.setVisibility(View.VISIBLE);
//                        }

                        MerchantsBig_IDCard_IS = data.getString("mctCardFileY");
                        MerchantsBig_IDCard_IS_TYPE = "1";
                        MerchantsBig_IDCard_NO = data.getString("mctCardFileN");
                        MerchantsBig_IDCard_NO_TYPE = "1";
                        MerchantsBig_Bank = data.getString("mctBankFile");
                        MerchantsBig_Bank_TYPE = "1";
                        MerchantsBig_Shouquan = data.getString("mctPowerFile");
                        MerchantsBig_Shouquan_TYPE = "1";
                        MerchantsBig_License = data.getString("mctPermitFile");
                        MerchantsBig_LicenseType = "1";
                        MerchantsBig_handheld_License = data.getString("mctPermitFileHand");
                        MerchantsBig_handheld_LicenseType = "1";
                        if (!data.getString("mctPlaceFile").equals("###")) {
                            String[] split = data.getString("mctPlaceFile").split("#");
                            MerchantsBig_IMG1 = split[0];
                            MerchantsBig_IMG1_TYPE = "1";
                            MerchantsBig_IMG2 = split[1];
                            MerchantsBig_IMG2_TYPE = "1";
                            MerchantsBig_IMG3 = split[2];
                            MerchantsBig_IMG3_TYPE = "1";
                            MerchantsBig_IMG4 = split[3];
                            MerchantsBig_IMG4_TYPE = "1";
                        }
                        merchant_big_detail_name.setText(data.getString("mctName"));
                        MCT_ID = data.getString("mctId");
                        merchant_big_detail_scope.setText(data.getString("placeName"));
                        merchant_big_detail_address.setText(data.getString("mctCity"));
                        merchant_big_detail_detail_address.setText(data.getString("mctAddress"));
                        mctScope = data.getString("mctScope");
                        merchant_big_detail_card_is.setImageURI(MerchantsBig_IDCard_IS);
                        merchant_big_detail_card_the.setImageURI(MerchantsBig_IDCard_NO);
                        merchant_big_detail_id_name.setText(data.getString("mctUserName"));
                        merchant_big_detail_id_number.setText(data.getString("mctCartNum"));
                        merchant_big_detail_card_year.setText(data.getString("mctCartAging"));
                        //第二页的数据
                        mctSettleType = data.getString("mctSettleType");
                        mctSettleLegal = data.getString("mctSettleLegal");
                        mctBankRegion = data.getString("mctBankRegion");
                        mctBankNum = data.getString("mctBankNum");
                        mctBankType = data.getString("bankName");
                        mctBankType_ID = data.getString("mctBankType");
                        mctBankUser = data.getString("mctBankUser");
                        mctBankPhone = data.getString("mctBankPhone");
                        //第三页数据
                        mctPermitType = data.getString("mctPermitType");
                        mctPermitNum = data.getString("mctPermitNum");
                        mctPermitAging = data.getString("mctPermitAging");
                        mctPhoneLo = data.getString("mctPhoneLo");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(OkHttpException failuer) {
                Failuer(failuer.getEcode(), failuer.getEmsg());
                showToast("接口请求失败了");
            }
        });


    }


    /************** 请求腾讯开关***************/
    public void getTengXunType(){
        RequestParams params = new RequestParams();
        HttpRequest.getTengXunType(params, SPUtils.get(MerchantsBigDetailActivity11.this, "Token", "-1").toString(), new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    if (TextUtils.equals(result.getString("data"), "null")) {
                        //空的话默认不能使用
                        OrcType = false;
                    }else {
                        JSONObject data = new JSONObject(result.getJSONObject("data").toString());
                        String idCard = data.getString("idCard");
                        if (idCard.equals("1")){
                            OrcType = true;
                        }else {
                            OrcType = false;
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(OkHttpException failuer) {
                OrcType = false;
                Failuer(failuer.getEcode(), failuer.getEmsg());
            }
        });
    }
}
