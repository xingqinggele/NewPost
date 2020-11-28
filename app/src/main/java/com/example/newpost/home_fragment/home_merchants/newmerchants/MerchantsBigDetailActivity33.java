package com.example.newpost.home_fragment.home_merchants.newmerchants;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.example.newpost.R;
import com.example.newpost.base.BaseActivity;
import com.example.newpost.cos.CosServiceFactory;
import com.example.newpost.home_fragment.home_merchants.newmerchants.bean.MerChantinformationBean;
import com.example.newpost.home_fragment.home_merchants.newmerchants.state.MerchantsDetailSubActivity;
import com.example.newpost.net.HttpRequest;
import com.example.newpost.net.OkHttpException;
import com.example.newpost.net.RequestParams;
import com.example.newpost.net.ResponseCallback;
import com.example.newpost.utils.ImageConvertUtil;
import com.example.newpost.utils.SPUtils;
import com.example.newpost.utils.TimeUtils;
import com.example.newpost.utils.Utility;
import com.facebook.drawee.view.SimpleDraweeView;
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

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 作者: qgl
 * 创建日期：2020/11/2
 * 描述:新增企业商户3
 */
public class MerchantsBigDetailActivity33 extends BaseActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    private boolean userType = true;  // 用户进入状态，ture 新增，false 修改或查看
    private String userType1 = "0";  // 用户进入状态，ture 新增，false 修改或查看
    private String MerchantsBig_IDCard_IS = ""; //身份证正面
    private String MerchantsBig_IDCard_IS_TYPE = ""; //身份证类型
    private String MerchantsBig_IDCard_NO = ""; //身份证反面
    private String MerchantsBig_IDCard_NO_TYPE = ""; //身份证类型
    private String MerchantsBig_Bank = ""; //银行卡照片
    private String MerchantsBig_Bank_TYPE = ""; //银行卡类型
    private String MerchantsBig_Shouquan = ""; //授权书照片
    private String MerchantsBig_Shouquan_TYPE = ""; //授权书类型
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

    private String Intent_url1 = "";
    private String Intent_url2 = "";
    private String Intent_url3 = "";
    private String Intent_url4 = "";
    private String Intent_url5 = "";
    private String Intent_url6 = "";
    private String Intent_url7 = "";
    private String Intent_url8 = "";
    private String Intent_url9 = "";
    private String Intent_url10 = "";



    private static final int LOG_IM1 = 131;
    private static final int LOG_IM2 = 132;
    private static final int LOG_IM3 = 133;
    private static final int LOG_IM4 = 134;
    private static final int LOG_IM5 = 135;
    private static final int LOG_IM6 = 136;


    private LinearLayout iv_back;
    private String YY_Type = "0"; // 营业执照类型 0，企业 1，个人
    private EditText merchant_big_detail3_account;
    private EditText merchant_big_detail3_phone;
    private TextView merchant_big_detail2_region;
    private SimpleDraweeView merchant_big_detail3_license;
    private SimpleDraweeView merchant_big_detail2_bank_card;
    private EditText merchant_big_detail3_code;
    private SimpleDraweeView merchant_big_detail3_im1;
    private SimpleDraweeView merchant_big_detail3_im2;
    private SimpleDraweeView merchant_big_detail3_im3;
    private SimpleDraweeView merchant_big_detail3_im4;
    private Button merchant_big_detail3_submit;
    private RadioGroup merchant_big_detail3_radio_group1;

    //已经选择图片
    private List<LocalMedia> selectList = new ArrayList<>();
    //照片选择最大值
    private int maxSelectNum = 1;
    //对象存储，图片上传到腾讯云
    private String secretId;  // 腾讯秘钥ID
    private String secretKey; // 腾讯秘钥key
    private CosXmlService cosXmlService;
    private TransferManager transferManager;
    private COSXMLUploadTask cosxmlTask;
    private String folderName = "";
    private String bucketName = "cykj-1303987307";
    /**********所有页面数据接收**********/
    private String Big1_shopName;
    private String Big1_shopScope;
    private String Big1_shopAddress;
    private String Big1_shopDetailAddress;
    private String Big1_shopUserName;
    private String Big1_shopUserNumber;
    private String Big1_shopYear;
    private String Big1_bank_address;
    private String Big1_bank_Type;
    private String Big1_bank_number;
    private String Big1_bank_name;
    private String Big1_bank_phone;
    private String CodeTypes;
    private String PersonTypes;


    private RadioButton merchants_big_detail3_radio_male,merchants_big_detail3_radio_private;


    @Override
    protected int getLayoutId() {
        return R.layout.merchants_big_detailactivity3;
    }

    @Override
    protected void initView() {
        secretId = SPUtils.get(MerchantsBigDetailActivity33.this, "secretId", "-1").toString();
        secretKey = SPUtils.get(MerchantsBigDetailActivity33.this, "secretKey", "-1").toString();
        cosXmlService = CosServiceFactory.getCosXmlService(this, "ap-beijing", secretId, secretKey, true);
        TransferConfig transferConfig = new TransferConfig.Builder().build();
        transferManager = new TransferManager(cosXmlService, transferConfig);

        iv_back = findViewById(R.id.iv_back);
        merchant_big_detail3_account = findViewById(R.id.merchant_big_detail3_account);
        merchant_big_detail3_phone = findViewById(R.id.merchant_big_detail3_phone);
        merchant_big_detail2_region = findViewById(R.id.merchant_big_detail2_region);
        merchant_big_detail2_bank_card = findViewById(R.id.merchant_big_detail2_bank_card);
        merchant_big_detail3_code = findViewById(R.id.merchant_big_detail3_code);
        merchant_big_detail3_license = findViewById(R.id.merchant_big_detail3_license);
        merchant_big_detail3_im1 = findViewById(R.id.merchant_big_detail3_im1);
        merchant_big_detail3_im2 = findViewById(R.id.merchant_big_detail3_im2);
        merchant_big_detail3_im3 = findViewById(R.id.merchant_big_detail3_im3);
        merchant_big_detail3_im4 = findViewById(R.id.merchant_big_detail3_im4);
        merchant_big_detail3_submit = findViewById(R.id.merchant_big_detail3_submit);
        merchant_big_detail3_radio_group1 = findViewById(R.id.merchant_big_detail3_radio_group1);
        merchants_big_detail3_radio_male = findViewById(R.id.merchants_big_detail3_radio_male);
        merchants_big_detail3_radio_private = findViewById(R.id.merchants_big_detail3_radio_private);
        iv_back.setOnClickListener(this);
        merchant_big_detail2_region.setOnClickListener(this);
        merchant_big_detail2_bank_card.setOnClickListener(this);
        merchant_big_detail3_license.setOnClickListener(this);
        merchant_big_detail3_im1.setOnClickListener(this);
        merchant_big_detail3_im2.setOnClickListener(this);
        merchant_big_detail3_im3.setOnClickListener(this);
        merchant_big_detail3_im4.setOnClickListener(this);
        merchant_big_detail3_submit.setOnClickListener(this);
        merchant_big_detail3_radio_group1.setOnCheckedChangeListener(this);
    }

    @Override
    protected void initData() {
        MCT_ID = getIntent().getStringExtra("MCT_ID");
        Big1_shopName = getIntent().getStringExtra("Big1_shopName");
        Big1_shopScope = getIntent().getStringExtra("Big1_shopScope");
        Big1_shopAddress = getIntent().getStringExtra("Big1_shopAddress");
        Big1_shopDetailAddress = getIntent().getStringExtra("Big1_shopDetailAddress");
        Big1_shopUserName = getIntent().getStringExtra("Big1_shopUserName");
        Big1_shopUserNumber = getIntent().getStringExtra("Big1_shopUserNumber");
        Big1_shopYear = getIntent().getStringExtra("Big1_shopYear");
        Big1_bank_address = getIntent().getStringExtra("Big1_bank_address");
        Big1_bank_Type = getIntent().getStringExtra("Big1_bank_Type");
        Big1_bank_number = getIntent().getStringExtra("Big1_bank_number");
        Big1_bank_name = getIntent().getStringExtra("Big1_bank_name");
        Big1_bank_phone = getIntent().getStringExtra("Big1_bank_phone");
        CodeTypes = getIntent().getStringExtra("CodeTypes");
        PersonTypes = getIntent().getStringExtra("PersonTypes");
        //身份证正面
        MerchantsBig_IDCard_IS = getIntent().getStringExtra("Big1_shopIdis");
        MerchantsBig_IDCard_IS_TYPE = getIntent().getStringExtra("Big1_shopIdis_type");
        Intent_url1 = MerchantsBig_IDCard_IS;
        //身份证反面
        MerchantsBig_IDCard_NO = getIntent().getStringExtra("Big1_shopIdno");
        MerchantsBig_IDCard_NO_TYPE = getIntent().getStringExtra("Big1_shopIdno_type");
        Intent_url2 = MerchantsBig_IDCard_NO;
        //银行卡
        MerchantsBig_Bank = getIntent().getStringExtra("Big1_bank_car");
        MerchantsBig_Bank_TYPE = getIntent().getStringExtra("Big1_bank_car_type");
        Intent_url3 = MerchantsBig_Bank;
        //授权书
        MerchantsBig_Shouquan = getIntent().getStringExtra("Big1_bank_shouquan");
        MerchantsBig_Shouquan_TYPE = getIntent().getStringExtra("Big1_bank_shouquan_type")+"";
        Intent_url4 = MerchantsBig_Shouquan;

        //营业执照
        MerchantsBig_License = getIntent().getStringExtra("MerchantsBig_License");
        MerchantsBig_LicenseType = getIntent().getStringExtra("MerchantsBig_LicenseType");
        Intent_url5 = MerchantsBig_License;

        MerchantsBig_handheld_License = getIntent().getStringExtra("MerchantsBig_handheld_License");
        MerchantsBig_handheld_LicenseType = getIntent().getStringExtra("MerchantsBig_handheld_LicenseType");
        Intent_url6 = MerchantsBig_handheld_License;

        MerchantsBig_IMG1 = getIntent().getStringExtra("MerchantsBig_IMG1");
        MerchantsBig_IMG1_TYPE = getIntent().getStringExtra("MerchantsBig_IMG1_TYPE");
        Intent_url7 = MerchantsBig_IMG1;

        MerchantsBig_IMG2 = getIntent().getStringExtra("MerchantsBig_IMG2");
        MerchantsBig_IMG2_TYPE = getIntent().getStringExtra("MerchantsBig_IMG2_TYPE");
        Intent_url8 = MerchantsBig_IMG2;

        MerchantsBig_IMG3 = getIntent().getStringExtra("MerchantsBig_IMG3");
        MerchantsBig_IMG3_TYPE = getIntent().getStringExtra("MerchantsBig_IMG3_TYPE");
        Intent_url9 = MerchantsBig_IMG3;

        MerchantsBig_IMG4 = getIntent().getStringExtra("MerchantsBig_IMG4");
        MerchantsBig_IMG4_TYPE = getIntent().getStringExtra("MerchantsBig_IMG4_TYPE");
        Intent_url10 = MerchantsBig_IMG4;

        userType1 = getIntent().getStringExtra("UserType");
        if (userType1.equals("0")){
            userType = true;
        }else {
            userType = false;
        }

        YY_Type = getIntent().getStringExtra("mctPermitType");
        if (YY_Type.equals("0")){
            merchants_big_detail3_radio_male.setChecked(true);
        }else {
            merchants_big_detail3_radio_private.setChecked(true);
        }
        merchant_big_detail3_account.setText(getIntent().getStringExtra("mctPermitNum"));
        merchant_big_detail2_region.setText(getIntent().getStringExtra("mctPermitAging"));
        merchant_big_detail3_phone.setText(getIntent().getStringExtra("mctPhoneLo"));
        merchant_big_detail3_license.setImageURI(MerchantsBig_License);
        merchant_big_detail2_bank_card.setImageURI(MerchantsBig_handheld_License);
        merchant_big_detail3_im1.setImageURI(MerchantsBig_IMG1);
        merchant_big_detail3_im2.setImageURI(MerchantsBig_IMG2);
        merchant_big_detail3_im3.setImageURI(MerchantsBig_IMG3);
        merchant_big_detail3_im4.setImageURI(MerchantsBig_IMG4);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.merchant_big_detail3_license:
                // 使用自己的获取图片方法，身份证反面
                initSelectImage(LOG_IM1);
                break;
            case R.id.merchant_big_detail2_bank_card:
                // 使用自己的获取图片方法，身份证反面
                initSelectImage(LOG_IM2);
                break;
            case R.id.merchant_big_detail3_im1:
                // 使用自己的获取图片方法，身份证反面
                initSelectImage(LOG_IM3);
                break;
            case R.id.merchant_big_detail3_im2:
                // 使用自己的获取图片方法，身份证反面
                initSelectImage(LOG_IM4);
                break;
            case R.id.merchant_big_detail3_im3:
                // 使用自己的获取图片方法，身份证反面
                initSelectImage(LOG_IM5);
                break;
            case R.id.merchant_big_detail3_im4:
                // 使用自己的获取图片方法，身份证反面
                initSelectImage(LOG_IM6);
                break;
            case R.id.merchant_big_detail3_submit:
                if (TextUtils.isEmpty(MerchantsBig_License)) {
                    showToast("请上传营业执照");
                    return;
                }
                if (TextUtils.isEmpty(MerchantsBig_handheld_License)) {
                    showToast("请上传手持营业执照");
                    return;
                }

                if (TextUtils.isEmpty(MerchantsBig_IMG1)) {
                    showToast("请上传经营场所一");
                    return;
                }

                if (TextUtils.isEmpty(MerchantsBig_IMG2)) {
                    showToast("请上传经营场所二");
                    return;
                }

                if (TextUtils.isEmpty(MerchantsBig_IMG3)) {
                    showToast("请上传收款台照");
                    return;
                }

                if (TextUtils.isEmpty(MerchantsBig_IMG4)) {
                    showToast("请上传门头照");
                    return;
                }
                if (TextUtils.isEmpty(merchant_big_detail3_account.getText().toString().trim())) {
                    showToast("请输入营业执照编号");
                    return;
                }

                if (TextUtils.isEmpty(merchant_big_detail2_region.getText().toString().trim())) {
                    showToast("请选择有效期");
                    return;
                }

                if (TextUtils.isEmpty(merchant_big_detail3_phone.getText().toString().trim())) {
                    showToast("请输入手机号");
                    return;
                }
                if (TextUtils.isEmpty(merchant_big_detail3_code.getText().toString().trim())) {
                    showToast("请输入验证码");
                    return;
                }
                loadDialog.show();
                folderName = TimeUtils.getNowTime() + "/" + Big1_shopUserNumber;
                List<MerChantinformationBean> beans = new ArrayList<>();
                if (!MerchantsBig_IDCard_IS_TYPE.equals("1")) {
                    // 需要存储的，身份证正面
                    MerChantinformationBean bean = new MerChantinformationBean();
                    bean.setName("1");
                    bean.setUrl(MerchantsBig_IDCard_IS);
                    beans.add(bean);
                }
                if (!MerchantsBig_IDCard_NO_TYPE.equals("1")) {
                    // 需要存储的，身份证反面
                    MerChantinformationBean bean = new MerChantinformationBean();
                    bean.setName("2");
                    bean.setUrl(MerchantsBig_IDCard_NO);
                    beans.add(bean);
                }
                if (!MerchantsBig_Bank_TYPE.equals("1")) {
                    // 需要存储的，银行卡
                    MerChantinformationBean bean = new MerChantinformationBean();
                    bean.setName("3");
                    bean.setUrl(MerchantsBig_Bank);
                    beans.add(bean);
                }
                if (!MerchantsBig_LicenseType.equals("1")) {
                    // 需要存储的 营业执照
                    MerChantinformationBean bean = new MerChantinformationBean();
                    bean.setName("4");
                    bean.setUrl(MerchantsBig_License);
                    beans.add(bean);
                }
                if (!MerchantsBig_handheld_LicenseType.equals("1")) {
                    // 需要存储的 手持营业执照
                    MerChantinformationBean bean = new MerChantinformationBean();
                    bean.setName("5");
                    bean.setUrl(MerchantsBig_handheld_License);
                    beans.add(bean);
                }
                if (!MerchantsBig_IMG1_TYPE.equals("1")) {
                    // 需要存储的 经营场所一
                    MerChantinformationBean bean = new MerChantinformationBean();
                    bean.setName("6");
                    bean.setUrl(MerchantsBig_IMG1);
                    beans.add(bean);
                }
                if (!MerchantsBig_IMG2_TYPE.equals("1")) {
                    // 需要存储的 经营场所二
                    MerChantinformationBean bean = new MerChantinformationBean();
                    bean.setName("7");
                    bean.setUrl(MerchantsBig_IMG2);
                    beans.add(bean);
                }
                if (!MerchantsBig_IMG3_TYPE.equals("1")) {
                    // 需要存储的 经营场所三
                    MerChantinformationBean bean = new MerChantinformationBean();
                    bean.setName("8");
                    bean.setUrl(MerchantsBig_IMG3);
                    beans.add(bean);
                }
                if (!MerchantsBig_IMG4_TYPE.equals("1")) {
                    // 需要存储的 经营场所四
                    MerChantinformationBean bean = new MerChantinformationBean();
                    bean.setName("9");
                    bean.setUrl(MerchantsBig_IMG4);
                    beans.add(bean);
                }
                if (!MerchantsBig_Shouquan_TYPE.equals("null")) {
                    if (!MerchantsBig_Shouquan_TYPE.equals("1")) {
                        // 需要存储的 授权书
                        MerChantinformationBean bean = new MerChantinformationBean();
                        bean.setName("10");
                        bean.setUrl(MerchantsBig_Shouquan);
                        beans.add(bean);
                    }
                }
                if (userType){
                    if (beans.get(0).getName() == null) {
                        Log.e("------》", "没有需要上传的图片");
                        subMit();
                    } else {
                        Log.e("------》", "需要上传图片");
                        for (int i = 0; i < beans.size(); i++) {
                            upload(beans, folderName,userType);
                        }
                    }

                }else {
                    if (beans.size() == 0) {
                        Log.e("------》", "没有需要上传的图片");
                        Modify_SubMit();
                    } else {
                        upload(beans, folderName,userType);
                    }
                }

                // Log.e("啊啊啊", url1 + "\n" + url2 + "\n" + url3 + "\n" + url4 + "\n" + url5 + "\n" + url6);


                break;

            case R.id.merchant_big_detail2_region:
                selectTime();
                break;


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
                case LOG_IM1:
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
                            merchant_big_detail3_license.setImageBitmap(bitmap);
                            MerchantsBig_License = ImageConvertUtil.getFile(bitmap).getCanonicalPath();
                            MerchantsBig_LicenseType = "2";
                        } else {
                            merchant_big_detail3_license.setImageResource(R.mipmap.merchant_detail_bank_car);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case LOG_IM2:
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
                            MerchantsBig_handheld_License = ImageConvertUtil.getFile(bitmap).getCanonicalPath();
                            MerchantsBig_handheld_LicenseType = "2";
                        } else {
                            merchant_big_detail2_bank_card.setImageResource(R.mipmap.merchant_detail_bank_car);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case LOG_IM3:
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
                            merchant_big_detail3_im1.setImageBitmap(bitmap);
                            MerchantsBig_IMG1 = ImageConvertUtil.getFile(bitmap).getCanonicalPath();
                            MerchantsBig_IMG1_TYPE = "2";
                        } else {
                            merchant_big_detail3_im1.setImageResource(R.mipmap.merchant_detail_bank_car);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case LOG_IM4:
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
                            merchant_big_detail3_im2.setImageBitmap(bitmap);
                            MerchantsBig_IMG2 = ImageConvertUtil.getFile(bitmap).getCanonicalPath();
                            MerchantsBig_IMG2_TYPE = "2";
                        } else {
                            merchant_big_detail3_im2.setImageResource(R.mipmap.merchant_detail_bank_car);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case LOG_IM5:
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
                            merchant_big_detail3_im3.setImageBitmap(bitmap);
                            MerchantsBig_IMG3 = ImageConvertUtil.getFile(bitmap).getCanonicalPath();
                            MerchantsBig_IMG3_TYPE = "2";
                        } else {
                            merchant_big_detail3_im3.setImageResource(R.mipmap.merchant_detail_bank_car);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;
                case LOG_IM6:
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
                            merchant_big_detail3_im4.setImageBitmap(bitmap);
                            MerchantsBig_IMG4 = ImageConvertUtil.getFile(bitmap).getCanonicalPath();
                            MerchantsBig_IMG4_TYPE = "2";
                        } else {
                            merchant_big_detail3_im4.setImageResource(R.mipmap.merchant_detail_bank_car);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;


            }
        }
    }

    private void selectTime() {
        TimePickerView pvTime = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                //设置时间
                merchant_big_detail2_region.setText(Utility.getTime(date));
            }
        }).setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("年", "月", "日", "时", "分", "秒")
                .build();
        pvTime.show();
    }


    public void subMit() {
        RequestParams params = new RequestParams();
        params.put("mctType", "1");
        params.put("mctName", Big1_shopName);
        params.put("mctScope", Big1_shopScope);
        params.put("mctCity", Big1_shopAddress);
        params.put("mctAddress", Big1_shopDetailAddress);
        params.put("mctCardFileY", Intent_url1);
        params.put("mctCardFileN", Intent_url2);
        params.put("mctUserName", Big1_shopUserName);
        params.put("mctCartNum", Big1_shopUserNumber);
        params.put("mctCartAging", Big1_shopYear);
        params.put("mctSettleType", CodeTypes);
        params.put("mctSettleLegal", PersonTypes);
        params.put("mctBankRegion", Big1_bank_address);
        params.put("mctBankNum", Big1_bank_number);
        params.put("mctBankType", Big1_bank_Type);
        params.put("mctBankUser", Big1_bank_name);
        params.put("mctBankPhone", Big1_bank_phone);
        params.put("mctBankFile", Intent_url3);
        if (!PersonTypes.equals("0")) {
            params.put("mctPowerFile", Intent_url10);
        }
        params.put("mctPermitType", YY_Type); // 营业执照类型
        params.put("mctPermitNum", merchant_big_detail3_account.getText().toString().trim()); // 营业执照编号
        params.put("mctPermitAging", merchant_big_detail2_region.getText().toString().trim()); // 营业执照有效期
        params.put("mctPermitFile", Intent_url4); //营业执照照片
        params.put("mctPermitFileHand", Intent_url5); //营业执照手持照片
        params.put("mctPlaceFile", Intent_url6 + "#" + Intent_url7 + "#" + Intent_url8 + "#" + Intent_url9); // 营业场所照片（多张）
        params.put("mctPhoneLo", merchant_big_detail3_phone.getText().toString().trim()); // 验证码电话
        params.put("mctCodeLo", merchant_big_detail3_code.getText().toString().trim()); // 验证码
        HttpRequest.getSmallStay(params, SPUtils.get(MerchantsBigDetailActivity33.this, "Token", "-1").toString(), new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                loadDialog.dismiss();
                Intent intent = new Intent(MerchantsBigDetailActivity33.this, MerchantsDetailSubActivity.class);
                intent.putExtra("parent_class","1");
                startActivity(intent);
            }

            @Override
            public void onFailure(OkHttpException failuer) {
                loadDialog.dismiss();
                Failuer(failuer.getEcode(),failuer.getEmsg());
            }
        });

    }

    public void Modify_SubMit() {
        RequestParams params = new RequestParams();
        params.put("mctId", MCT_ID);  //ID
        params.put("mctType", "1");
        params.put("mctName", Big1_shopName);
        params.put("mctScope", Big1_shopScope);
        params.put("mctCity", Big1_shopAddress);
        params.put("mctAddress", Big1_shopDetailAddress);
        params.put("mctCardFileY", Intent_url1);
        params.put("mctCardFileN", Intent_url2);
        params.put("mctUserName", Big1_shopUserName);
        params.put("mctCartNum", Big1_shopUserNumber);
        params.put("mctCartAging", Big1_shopYear);
        params.put("mctSettleType", CodeTypes);
        params.put("mctSettleLegal", PersonTypes);
        params.put("mctBankRegion", Big1_bank_address);
        params.put("mctBankNum", Big1_bank_number);
        params.put("mctBankType", Big1_bank_Type);
        params.put("mctBankUser", Big1_bank_name);
        params.put("mctBankPhone", Big1_bank_phone);
        params.put("mctBankFile", Intent_url3);
        if (!PersonTypes.equals("0")) {
            params.put("mctPowerFile", Intent_url10);
        }else {
            params.put("mctPowerFile", "");
        }
        params.put("mctPermitType", YY_Type); // 营业执照类型
        params.put("mctPermitNum", merchant_big_detail3_account.getText().toString().trim()); // 营业执照编号
        params.put("mctPermitAging", merchant_big_detail2_region.getText().toString().trim()); // 营业执照有效期
        params.put("mctPermitFile", Intent_url4); //营业执照照片
        params.put("mctPermitFileHand", Intent_url5); //营业执照手持照片
        params.put("mctPlaceFile", Intent_url6 + "#" + Intent_url7 + "#" + Intent_url8 + "#" + Intent_url9); // 营业场所照片（多张）
        params.put("mctPhoneLo", merchant_big_detail3_phone.getText().toString().trim()); // 验证码电话
        params.put("mctCodeLo", merchant_big_detail3_code.getText().toString().trim()); // 验证码
        HttpRequest.getModifyMerchant(params, SPUtils.get(MerchantsBigDetailActivity33.this, "Token", "-1").toString(), new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                loadDialog.dismiss();
                Intent intent = new Intent(MerchantsBigDetailActivity33.this, MerchantsDetailSubActivity.class);
                intent.putExtra("parent_class","1");
                startActivity(intent);
            }

            @Override
            public void onFailure(OkHttpException failuer) {
                loadDialog.dismiss();
                Failuer(failuer.getEcode(),failuer.getEmsg());

            }
        });

    }


    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i) {
            case R.id.merchants_big_detail3_radio_male:
                YY_Type = "0";
                break;
            case R.id.merchants_big_detail3_radio_private:
                YY_Type = "1";
                break;
        }
    }

    private void upload(List<MerChantinformationBean>list,String newfolderName,boolean userType) {
        int i = 0;
        if (TextUtils.isEmpty(list.get(i).getUrl())) {
            return;
        }

        if (cosxmlTask == null) {
            File file = new File(list.get(i).getUrl());
            String cosPath;
            if (TextUtils.isEmpty(newfolderName)) {
                cosPath = file.getName();
            } else {
                cosPath = newfolderName + File.separator + file.getName();
            }
            cosxmlTask = transferManager.upload(bucketName, cosPath, list.get(i).getUrl(), null);
            Log.e("参数-------》", bucketName + "----" + cosPath + "---" + list.get(i).getUrl());

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
                    if (list.get(i).getName().equals("1")) {
                        Intent_url1 = cOSXMLUploadTaskResult.accessUrl;
                        Log.e("1", cOSXMLUploadTaskResult.accessUrl);
                    } else if (list.get(i).getName().equals("2")) {
                        Intent_url2 = cOSXMLUploadTaskResult.accessUrl;
                        Log.e("2", cOSXMLUploadTaskResult.accessUrl);
                    } else if (list.get(i).getName().equals("3")) {
                        Intent_url3 = cOSXMLUploadTaskResult.accessUrl;
                        Log.e("3", cOSXMLUploadTaskResult.accessUrl);
                    } else if (list.get(i).getName().equals("4")){
                        Intent_url4 = cOSXMLUploadTaskResult.accessUrl;
                        Log.e("4", cOSXMLUploadTaskResult.accessUrl);
                    }else if (list.get(i).getName().equals("5")){
                        Intent_url5 = cOSXMLUploadTaskResult.accessUrl;
                        Log.e("5", cOSXMLUploadTaskResult.accessUrl);
                    }
                    else if (list.get(i).getName().equals("6")){
                        Intent_url6 = cOSXMLUploadTaskResult.accessUrl;
                        Log.e("6", cOSXMLUploadTaskResult.accessUrl);
                    }
                    else if (list.get(i).getName().equals("7")){
                        Intent_url7 = cOSXMLUploadTaskResult.accessUrl;
                        Log.e("7", cOSXMLUploadTaskResult.accessUrl);
                    }
                    else if (list.get(i).getName().equals("8")){
                        Intent_url8 = cOSXMLUploadTaskResult.accessUrl;
                        Log.e("8", cOSXMLUploadTaskResult.accessUrl);
                    }
                    else if (list.get(i).getName().equals("9")){
                        Intent_url9 = cOSXMLUploadTaskResult.accessUrl;
                        Log.e("9", cOSXMLUploadTaskResult.accessUrl);
                    }
                    else if (list.get(i).getName().equals("10")){
                        Intent_url10 = cOSXMLUploadTaskResult.accessUrl;
                        Log.e("10", cOSXMLUploadTaskResult.accessUrl);
                    }
                    setResult(RESULT_OK);
                    list.remove(i);
                    Log.e("长度",list.size()+"");
                    if (list.size()<1){
                        Log.e("----------》","没有了去提交后台吧");
                        if (userType){
                            subMit();
                        }else {
                            Modify_SubMit();
                        }
                    }else {
                        upload(list,newfolderName,userType);
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

}
