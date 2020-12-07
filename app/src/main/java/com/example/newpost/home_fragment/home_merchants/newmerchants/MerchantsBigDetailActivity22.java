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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
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
import java.util.List;

/**
 * 作者: qgl
 * 创建日期：2020/11/2
 * 描述:新增企业商户2
 */
public class MerchantsBigDetailActivity22 extends BaseActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {
    private String MerchantsBig_IDCard_IS_TYPE = ""; //身份证类型
    private String MerchantsBig_IDCard_NO_TYPE = ""; //身份证类型
    private String MerchantsBig_Bank = ""; //银行卡照片
    private String MerchantsBig_Bank_TYPE = "1"; //银行卡类型
    private String MerchantsBig_Shouquan = ""; //授权书照片
    private String MerchantsBig_Shouquan_TYPE = "1"; //授权书类型
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
    private String mctPermitType = "";//营业执照类型
    private String mctPermitNum = "";//营业执照编号
    private String mctPermitAging = "";//营业执照有效期
    private String mctPhoneLo = "";//营业执照手机号
    private String MCT_ID = ""; //详情ID
    private String UserType = "0"; // 新增，修改状态


    private String CodeTypes = "0";   // 对公，对私
    private boolean PersonType = true;   // 是,否法人
    private String PersonTypes = "0";   // 是,否法人
    private String Big1_shopName = "";
    private String Big1_shopScope = "";
    private String Big1_shopAddress = "";
    private String Big1_shopDetailAddress = "";
    private String Big1_shopIdis = "";
    private String Big1_shopIdno = "";
    private String Big1_shopUserName = "";
    private String Big1_shopUserNumber = "";
    private String Big1_shopYear = "";
    private String mctBankType = "1"; // 开户行ID


    private static final int MSG_SHOU_QUAN_SHU = 0x0001; //授权书
    //省市区选择
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    private Thread thread;
    //选择开户城市
    private static boolean isLoaded = false;
    private List<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();


    private RadioGroup merchant_big_detail2_radio_group2;
    private RadioGroup merchant_big_detail2_radio_group1;

    private RelativeLayout merchant_big_detail2_type;
    private LinearLayout merchant_big_detail2_line3;
    private LinearLayout iv_back;
    private TextView merchant_big_detail2_region;
    private Button merchant_big_detail2_submit;

    private SimpleDraweeView merchant_big_detail2_bank_card; // 身份证照片
    private SimpleDraweeView merchant_big_detail2_author; //授权书

    private boolean isOrc = true;  //腾讯云识别银行卡是否允许
    //已经选择图片
    private List<LocalMedia> selectList = new ArrayList<>();
    //照片选择最大值
    private int maxSelectNum = 1;
    private TextView merchant_big_detail2_bank;
    private EditText merchant_big_detail2_account;
    private EditText merchant_big_detail2_kh_name;
    private EditText merchant_big_detail2_kh_phone;
    //营业范围数据配置
    private List<Merchants2DetailBean> mdBean = new ArrayList<>();
    // 选择控件
    private OptionsPickerView reasonPicker;


    private RadioButton merchants_big_detail2_radio_male, merchants_big_detail2_radio_private, merchants_big_detail2_radio_yes, merchants_big_detail2_radio_no;

    private String secretId;  // 腾讯秘钥ID
    private String secretKey; // 腾讯秘钥key
    @Override
    protected int getLayoutId() {
        return R.layout.merchants_big_detailactivity2;
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

    /**
     * 初始化
     */
    @Override
    protected void initView() {
        secretId = SPUtils.get(MerchantsBigDetailActivity22.this, "secretId", "-1").toString();
        secretKey = SPUtils.get(MerchantsBigDetailActivity22.this, "secretKey", "-1").toString();
        merchant_big_detail2_radio_group1 = findViewById(R.id.merchant_big_detail2_radio_group1);
        merchant_big_detail2_kh_name = findViewById(R.id.merchant_big_detail2_kh_name);
        merchant_big_detail2_radio_group2 = findViewById(R.id.merchant_big_detail2_radio_group2);
        merchant_big_detail2_type = findViewById(R.id.merchant_big_detail2_type);
        merchant_big_detail2_line3 = findViewById(R.id.merchant_big_detail2_line3);
        merchant_big_detail2_bank_card = findViewById(R.id.merchant_big_detail2_bank_card);
        merchant_big_detail2_bank = findViewById(R.id.merchant_big_detail2_bank);
        merchant_big_detail2_region = findViewById(R.id.merchant_big_detail2_region);
        merchant_big_detail2_kh_phone = findViewById(R.id.merchant_big_detail2_kh_phone);
        merchant_big_detail2_author = findViewById(R.id.merchant_big_detail2_author);
        merchant_big_detail2_radio_group1.setOnCheckedChangeListener(this);
        merchant_big_detail2_radio_group2.setOnCheckedChangeListener(this);
        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        merchant_big_detail2_bank.setOnClickListener(this);
        merchant_big_detail2_submit = findViewById(R.id.merchant_big_detail2_submit);
        merchant_big_detail2_account = findViewById(R.id.merchant_big_detail2_account);
        merchant_big_detail2_submit.setOnClickListener(this);
        merchant_big_detail2_bank_card.setOnClickListener(this);
        merchant_big_detail2_region.setOnClickListener(this);
        merchant_big_detail2_author.setOnClickListener(this);
        merchants_big_detail2_radio_male = findViewById(R.id.merchants_big_detail2_radio_male);
        merchants_big_detail2_radio_private = findViewById(R.id.merchants_big_detail2_radio_private);
        merchants_big_detail2_radio_yes = findViewById(R.id.merchants_big_detail2_radio_yes);
        merchants_big_detail2_radio_no = findViewById(R.id.merchants_big_detail2_radio_no);

    }

    /**
     * 数据处理
     */
    @Override
    protected void initData() {
        mHandler.sendEmptyMessage(MSG_LOAD_DATA);

        Big1_shopName = getIntent().getStringExtra("big_shopName");
        Big1_shopScope = getIntent().getStringExtra("big_shopScope");
        Big1_shopAddress = getIntent().getStringExtra("big_shopAddress");
        Big1_shopDetailAddress = getIntent().getStringExtra("big_shopDetailAddress");
        Big1_shopIdis = getIntent().getStringExtra("big_shopIdis");
        Big1_shopIdno = getIntent().getStringExtra("big_shopIdno");
        Big1_shopUserName = getIntent().getStringExtra("big_shopUserName");
        Big1_shopUserNumber = getIntent().getStringExtra("big_shopUserNumber");
        Big1_shopYear = getIntent().getStringExtra("big_shopYear");
        MerchantsBig_IDCard_IS_TYPE = getIntent().getStringExtra("big_shopIdno_type");
        MerchantsBig_IDCard_NO_TYPE = getIntent().getStringExtra("big_shopIdno_type");
        UserType = getIntent().getStringExtra("userType");
        if (UserType.equals("1")) {
            MCT_ID = getIntent().getStringExtra("MCT_ID");
            MerchantsBig_Bank = getIntent().getStringExtra("MerchantsBig_Bank");
            MerchantsBig_Bank_TYPE = getIntent().getStringExtra("MerchantsBig_Bank_TYPE");
            MerchantsBig_Shouquan = getIntent().getStringExtra("MerchantsBig_Shouquan");
            MerchantsBig_Shouquan_TYPE = getIntent().getStringExtra("MerchantsBig_Shouquan_TYPE");
            MerchantsBig_License = getIntent().getStringExtra("MerchantsBig_License");
            MerchantsBig_LicenseType = getIntent().getStringExtra("MerchantsBig_LicenseType");
            MerchantsBig_handheld_License = getIntent().getStringExtra("MerchantsBig_handheld_License");
            MerchantsBig_handheld_LicenseType = getIntent().getStringExtra("MerchantsBig_handheld_LicenseType");
            MerchantsBig_IMG1 = getIntent().getStringExtra("MerchantsBig_IMG1");
            MerchantsBig_IMG1_TYPE = getIntent().getStringExtra("MerchantsBig_IMG1_TYPE");
            MerchantsBig_IMG2 = getIntent().getStringExtra("MerchantsBig_IMG2");
            MerchantsBig_IMG2_TYPE = getIntent().getStringExtra("MerchantsBig_IMG2_TYPE");
            MerchantsBig_IMG3 = getIntent().getStringExtra("MerchantsBig_IMG3");
            MerchantsBig_IMG3_TYPE = getIntent().getStringExtra("MerchantsBig_IMG3_TYPE");
            MerchantsBig_IMG4 = getIntent().getStringExtra("MerchantsBig_IMG4");
            MerchantsBig_IMG4_TYPE = getIntent().getStringExtra("MerchantsBig_IMG4_TYPE");
            CodeTypes = getIntent().getStringExtra("mctSettleType");
            PersonTypes = getIntent().getStringExtra("mctSettleLegal");
            if (CodeTypes.equals("0")) {
                merchants_big_detail2_radio_male.setChecked(true);
            } else {
                merchants_big_detail2_radio_private.setChecked(true);
            }
            if (PersonTypes.equals("0")) {
                merchants_big_detail2_radio_yes.setChecked(true);
            } else {
                merchants_big_detail2_radio_no.setChecked(true);
            }
            merchant_big_detail2_region.setText(getIntent().getStringExtra("mctBankRegion"));
            merchant_big_detail2_account.setText(getIntent().getStringExtra("mctBankNum"));
            merchant_big_detail2_bank.setText(getIntent().getStringExtra("mctBankType"));
            merchant_big_detail2_kh_name.setText(getIntent().getStringExtra("mctBankUser"));
            merchant_big_detail2_kh_phone.setText(getIntent().getStringExtra("mctBankPhone"));
            mctBankType = getIntent().getStringExtra("mctBankType_ID");
            merchant_big_detail2_bank_card.setImageURI(MerchantsBig_Bank);
            merchant_big_detail2_author.setImageURI(MerchantsBig_Shouquan);


            mctPermitType = getIntent().getStringExtra("mctPermitType");
            mctPermitNum = getIntent().getStringExtra("mctPermitNum");
            mctPermitAging = getIntent().getStringExtra("mctPermitAging");
            mctPhoneLo = getIntent().getStringExtra("mctPhoneLo");
        }

        getBankAndPlace();
    }

    /**
     * RadioGroup点击事件
     *
     * @param radioGroup
     * @param i
     */
    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i) {
            case R.id.merchants_big_detail2_radio_male:
                CodeTypes = "0";
                merchant_big_detail2_type.setVisibility(View.VISIBLE);
                break;
            case R.id.merchants_big_detail2_radio_private:
                CodeTypes = "1";
                merchant_big_detail2_type.setVisibility(View.GONE);
                break;
            case R.id.merchants_big_detail2_radio_yes:
                PersonType = true;
                PersonTypes = "0";
                merchant_big_detail2_line3.setVisibility(View.GONE);
                break;
            case R.id.merchants_big_detail2_radio_no:
                PersonType = false;
                PersonTypes = "1";
                merchant_big_detail2_line3.setVisibility(View.VISIBLE);
                break;
        }
    }

    /**
     * 点击事件
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;

            case R.id.merchant_big_detail2_bank_card:
                initSdk(secretId, secretKey);
                if (isOrc) {
                    OcrSDKKit.getInstance().startProcessOcr(MerchantsBigDetailActivity22.this, OcrType.BankCardOCR,
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
            case R.id.merchant_big_detail2_submit:
                if (TextUtils.isEmpty(MerchantsBig_Bank)) {
                    showToast("请上传银行卡照片");
                    return;
                }
                if (!PersonType) {
                    if (TextUtils.isEmpty(MerchantsBig_Shouquan)) {
                        showToast("请上传授权书");
                        return;
                    }
                }
                if (TextUtils.isEmpty(merchant_big_detail2_region.getText().toString().trim())) {
                    showToast("请输入开户地区");
                }
                if (TextUtils.isEmpty(merchant_big_detail2_bank.getText().toString().trim())) {
                    showToast("请输入开户行");
                }
                if (TextUtils.isEmpty(merchant_big_detail2_account.getText().toString().trim())) {
                    showToast("请输入开户账号");
                }

                if (TextUtils.isEmpty(merchant_big_detail2_kh_name.getText().toString().trim())) {
                    showToast("请输入姓名");
                }
                if (TextUtils.isEmpty(merchant_big_detail2_kh_phone.getText().toString().trim())) {
                    showToast("请输入预留手机号");
                }

                getIntents(PersonType);


                break;
            case R.id.merchant_big_detail2_bank:
                initReason();
                reasonPicker.show();
                break;
            case R.id.merchant_big_detail2_region:
                if (isLoaded) {
                    showPickerView();
                } else {
                    Toast.makeText(MerchantsBigDetailActivity22.this, "Please waiting until the data is parsed", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.merchant_big_detail2_author:
                // 使用自己的获取图片方法，授权书
                initSelectImage(MSG_SHOU_QUAN_SHU);
                break;
        }
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

    /**
     * 获取银行卡信息
     *
     * @param response
     * @param srcBase64Image
     */
    public void getdata(String response, String srcBase64Image) {
        if (!srcBase64Image.isEmpty()) {
            Bitmap bitmap = ImageConvertUtil.base64ToBitmap(srcBase64Image);
            try {
                if (bitmap != null)
                    merchant_big_detail2_bank_card.setImageBitmap(bitmap);
                MerchantsBig_Bank = ImageConvertUtil.getFile(bitmap).getCanonicalPath();
                MerchantsBig_Bank_TYPE = "2";
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (!response.isEmpty()) {
            final BankCardInfo bankCardInfo = new Gson().fromJson(response, BankCardInfo.class);
            Log.e("银行卡", bankCardInfo.getCardNo() +mdBean.size()+ "----" + bankCardInfo.getBankInfo());
            for (int i = 0;i < mdBean.size();i++){
                if (mdBean.get(i).getBankName().equals(bankCardInfo.getBankInfo().substring(0,bankCardInfo.getBankInfo().length()-10))){
                    mctBankType = mdBean.get(i).getBankId();
                    Log.e("银行ID",mdBean.get(i).getBankId());
                }
                else {
                    Log.e("不一样",mdBean.get(i).getBankId());
                }
            }

            merchant_big_detail2_account.setText(bankCardInfo.getCardNo());
            merchant_big_detail2_bank.setText(bankCardInfo.getBankInfo().substring(0,bankCardInfo.getBankInfo().length()-10));
        } else {
            //Toast.makeText(this, "result is empty", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 回调事件
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
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
                            merchant_big_detail2_bank_card.setImageBitmap(bitmap);
                            MerchantsBig_Bank = ImageConvertUtil.getFile(bitmap).getCanonicalPath();
                            MerchantsBig_Bank_TYPE = "2";
                        } else {
                            merchant_big_detail2_bank_card.setImageResource(R.mipmap.merchant_detail_bank_car);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;
                case MSG_SHOU_QUAN_SHU:
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
                            merchant_big_detail2_author.setImageBitmap(bitmap);
                            MerchantsBig_Shouquan = ImageConvertUtil.getFile(bitmap).getCanonicalPath();
                            MerchantsBig_Shouquan_TYPE = "2";

                        } else {
                            merchant_big_detail2_author.setImageResource(R.mipmap.merchant_big_details2_author);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
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
                merchant_big_detail2_bank.setText(name.get(options1));
                mctBankType = mdBean.get(options1).getBankId();
            }
        }).setTitleText("营业范围").setContentTextSize(22).setTitleSize(22).setSubCalSize(21).build();
        reasonPicker.setPicker(name);
    }

    /**
     * 获取开户行
     */
    public void getBankAndPlace() {
        RequestParams params = new RequestParams();
        HttpRequest.getBankAndPlace(params, SPUtils.get(MerchantsBigDetailActivity22.this, "Token", "-1").toString(), new ResponseCallback() {
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
                    exitApp(MerchantsBigDetailActivity22.this);
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


    //跳转公共方法
    public void getIntents(boolean type) {
        Intent intent = new Intent(MerchantsBigDetailActivity22.this, MerchantsBigDetailActivity33.class);
        intent.putExtra("UserType", UserType);
        intent.putExtra("Big1_shopName", Big1_shopName);
        intent.putExtra("Big1_shopScope", Big1_shopScope);
        intent.putExtra("Big1_shopAddress", Big1_shopAddress);
        intent.putExtra("Big1_shopDetailAddress", Big1_shopDetailAddress);
        intent.putExtra("Big1_shopIdis", Big1_shopIdis);
        intent.putExtra("Big1_shopIdis_type", MerchantsBig_IDCard_IS_TYPE);
        intent.putExtra("Big1_shopIdno", Big1_shopIdno);
        intent.putExtra("Big1_shopIdno_type", MerchantsBig_IDCard_NO_TYPE);
        intent.putExtra("Big1_shopUserName", Big1_shopUserName);
        intent.putExtra("Big1_shopUserNumber", Big1_shopUserNumber);
        intent.putExtra("Big1_shopYear", Big1_shopYear);
        intent.putExtra("Big1_bank_address", merchant_big_detail2_region.getText().toString().trim());
        intent.putExtra("Big1_bank_Type", mctBankType);
        intent.putExtra("Big1_bank_number", merchant_big_detail2_account.getText().toString().trim());
        intent.putExtra("Big1_bank_name", merchant_big_detail2_kh_name.getText().toString().trim());
        intent.putExtra("Big1_bank_phone", merchant_big_detail2_kh_phone.getText().toString().trim());
        intent.putExtra("Big1_bank_car", MerchantsBig_Bank);
        intent.putExtra("Big1_bank_car_type", MerchantsBig_Bank_TYPE);
        intent.putExtra("CodeTypes", CodeTypes);
        intent.putExtra("PersonTypes", PersonTypes);
        intent.putExtra("MCT_ID", MCT_ID);
        if (!type) {
            intent.putExtra("Big1_bank_shouquan", MerchantsBig_Shouquan);
            intent.putExtra("Big1_bank_shouquan_type", MerchantsBig_Shouquan_TYPE);
        }
        intent.putExtra("mctPermitType", mctPermitType);
        intent.putExtra("mctPermitNum", mctPermitNum);
        intent.putExtra("mctPermitAging", mctPermitAging);
        intent.putExtra("mctPhoneLo", mctPhoneLo);
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

        startActivity(intent);
    }

    /************************获取全国地区数据解析*******************/
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
                merchant_big_detail2_region.setText(tx);
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

    /************** 请求腾讯开关***************/
    public void getTengXunType(){
        RequestParams params = new RequestParams();
        HttpRequest.getTengXunType(params, SPUtils.get(MerchantsBigDetailActivity22.this, "Token", "-1").toString(), new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    if (TextUtils.equals(result.getString("data"), "null")) {
                        //空的话默认不能使用
                        isOrc = false;
                    }else {
                        JSONObject data = new JSONObject(result.getJSONObject("data").toString());
                        String bankCare = data.getString("bankCard");
                        if (bankCare.equals("1")){
                            isOrc = true;
                        }else {
                            isOrc = false;
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(OkHttpException failuer) {
                isOrc = false;
                Failuer(failuer.getEcode(), failuer.getEmsg());
            }
        });
    }
}
