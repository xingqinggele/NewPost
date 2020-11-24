package com.example.newpost.home_fragment.home_merchants.newmerchants;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.example.newpost.R;
import com.example.newpost.base.BaseActivity;
import com.example.newpost.cos.CosServiceFactory;
import com.example.newpost.net.HttpRequest;
import com.example.newpost.net.OkHttpException;
import com.example.newpost.net.RequestParams;
import com.example.newpost.net.ResponseCallback;
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
public class MerchantsBigDetailActivity3 extends BaseActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
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
    private ImageView merchant_big_detail3_license;
    private ImageView merchant_big_detail2_bank_card;
    private EditText merchant_big_detail3_code;
    private ImageView merchant_big_detail3_im1;
    private ImageView merchant_big_detail3_im2;
    private ImageView merchant_big_detail3_im3;
    private ImageView merchant_big_detail3_im4;
    private Button merchant_big_detail3_submit;
    private RadioGroup merchant_big_detail3_radio_group1;

    //已经选择图片
    private List<LocalMedia> selectList = new ArrayList<>();
    //照片选择最大值
    private int maxSelectNum = 1;
    private String url1 = "";
    private String url2 = "";
    private String url3 = "";
    private String url4 = "";
    private String url5 = "";
    private String url6 = "";
    // 网络地址
    private String dete3_url1 = "";
    private String dete3_url2 = "";
    private String dete3_url3 = "";
    private String dete3_url4 = "";
    private String dete3_url5 = "";
    private String dete3_url6 = "";
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
    private String Big1_shopIdis;
    private String Big1_shopIdno;
    private String Big1_shopUserName;
    private String Big1_shopUserNumber;
    private String Big1_shopYear;
    private String Big1_bank_address;
    private String Big1_bank_Type;
    private String Big1_bank_number;
    private String Big1_bank_name;
    private String Big1_bank_phone;
    private String Big1_bank_car;
    private String Big1_bank_shouquan;
    private String CodeTypes;
    private String PersonTypes;


    @Override
    protected int getLayoutId() {
        return R.layout.merchants_big_detailactivity3;
    }

    @Override
    protected void initView() {
        secretId = SPUtils.get(MerchantsBigDetailActivity3.this, "secretId", "-1").toString();
        secretKey = SPUtils.get(MerchantsBigDetailActivity3.this, "secretKey", "-1").toString();
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
        Big1_shopName = getIntent().getStringExtra("Big1_shopName");
        Big1_shopScope = getIntent().getStringExtra("Big1_shopScope");
        Big1_shopAddress = getIntent().getStringExtra("Big1_shopAddress");
        Big1_shopDetailAddress = getIntent().getStringExtra("Big1_shopDetailAddress");
        Big1_shopIdis = getIntent().getStringExtra("Big1_shopIdis");
        Big1_shopIdno = getIntent().getStringExtra("Big1_shopIdno");
        Big1_shopUserName = getIntent().getStringExtra("Big1_shopUserName");
        Big1_shopUserNumber = getIntent().getStringExtra("Big1_shopUserNumber");
        Big1_shopYear = getIntent().getStringExtra("Big1_shopYear");
        Big1_bank_address = getIntent().getStringExtra("Big1_bank_address");
        Big1_bank_Type = getIntent().getStringExtra("Big1_bank_Type");
        Big1_bank_number = getIntent().getStringExtra("Big1_bank_number");
        Big1_bank_name = getIntent().getStringExtra("Big1_bank_name");
        Big1_bank_phone = getIntent().getStringExtra("Big1_bank_phone");
        Big1_bank_car = getIntent().getStringExtra("Big1_bank_car");
        Big1_bank_shouquan = getIntent().getStringExtra("Big1_bank_shouquan");
        CodeTypes = getIntent().getStringExtra("CodeTypes");
        PersonTypes = getIntent().getStringExtra("PersonTypes");
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
                Log.e("啊啊啊", url1 + "\n" + url2 + "\n" + url3 + "\n" + url4 + "\n" + url5 + "\n" + url6);
                folderName = TimeUtils.getNowTime() + "/" + "463001198502255128";
                upload(folderName, url1);
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
                        } else {
                            merchant_big_detail3_license.setImageResource(R.mipmap.merchant_detail3_license);
                        }
                        url1 = selectList.get(0).getCompressPath();

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
                        } else {
                            merchant_big_detail2_bank_card.setImageResource(R.mipmap.merchant_detail3_license);
                        }
                        url2 = selectList.get(0).getCompressPath();

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
                        } else {
                            merchant_big_detail3_im1.setImageResource(R.mipmap.merchant_big_detail3_photo1);
                        }
                        url3 = selectList.get(0).getCompressPath();

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
                        } else {
                            merchant_big_detail3_im2.setImageResource(R.mipmap.merchant_big_detail3_photo1);
                        }
                        url4 = selectList.get(0).getCompressPath();

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
                        } else {
                            merchant_big_detail3_im3.setImageResource(R.mipmap.merchant_big_detail3_photo1);
                        }
                        url5 = selectList.get(0).getCompressPath();

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
                        } else {
                            merchant_big_detail3_im4.setImageResource(R.mipmap.merchant_big_detail3_photo1);
                        }
                        url6 = selectList.get(0).getCompressPath();

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


    /**
     * 上传图片,对象存储
     */
    private void upload(String newfolderName, String url) {
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
                    dete3_url1 = result.accessUrl;
                    upload1(folderName, url2);
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

    private void upload1(String newfolderName, String url) {
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
                    dete3_url2 = result.accessUrl;
                    upload2(folderName, url3);
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

    private void upload2(String newfolderName, String url) {
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
                    dete3_url3 = result.accessUrl;
                    upload3(folderName, url4);
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

    private void upload3(String newfolderName, String url) {
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
                    dete3_url4 = result.accessUrl;
                    upload4(folderName, url5);
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

    private void upload4(String newfolderName, String url) {
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
                    dete3_url5 = result.accessUrl;
                    upload5(folderName, url6);
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

    private void upload5(String newfolderName, String url) {
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
                    dete3_url6 = result.accessUrl;
                    subMit();
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

    public void subMit(){
        RequestParams params = new RequestParams();
        params.put("mctType","1");
        params.put("mctName",Big1_shopName);
        params.put("mctScope",Big1_shopScope);
        params.put("mctCity",Big1_shopAddress);
        params.put("mctAddress",Big1_shopDetailAddress);
        params.put("mctCardFileY",Big1_shopIdis);
        params.put("mctCardFileN",Big1_shopIdno);
        params.put("mctUserName",Big1_shopUserName);
        params.put("mctCartNum",Big1_shopUserNumber);
        params.put("mctCartAging",Big1_shopYear);
        params.put("mctSettleType",CodeTypes);
        params.put("mctSettleLegal",PersonTypes);
        params.put("mctBankRegion",Big1_bank_address);
        params.put("mctBankNum",Big1_bank_number);
        params.put("mctBankType",Big1_bank_Type);
        params.put("mctBankUser",Big1_bank_name);
        params.put("mctBankPhone",Big1_bank_phone);
        params.put("mctBankFile",Big1_bank_car);
        if (!TextUtils.isEmpty(Big1_bank_shouquan)){
            params.put("mctPowerFile",Big1_bank_shouquan);
        }
        params.put("mctPermitType",YY_Type); // 营业执照类型
        params.put("mctPermitNum",merchant_big_detail3_account.getText().toString().trim()); // 营业执照编号
        params.put("mctPermitAging",merchant_big_detail2_region.getText().toString().trim()); // 营业执照有效期
        params.put("mctPermitFile",url1); //营业执照照片
        params.put("mctPermitFileHand",url2); //营业执照手持照片
        params.put("mctPlaceFile",url3+"#"+url4+"#"+url5+"#"+url6); // 营业场所照片（多张）
        params.put("mctPhoneLo",merchant_big_detail3_phone.getText().toString().trim()); // 验证码电话
        params.put("mctCodeLo",merchant_big_detail3_code.getText().toString().trim()); // 验证码
        HttpRequest.getSmallStay(params, SPUtils.get(MerchantsBigDetailActivity3.this, "Token", "-1").toString(), new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {



            }

            @Override
            public void onFailure(OkHttpException failuer) {
                if (failuer.getEcode() == 401) {
                    showToast("登录过期，请重新登录");
                    // 退出登录,清除本地数据
                    SPUtils.clear(mContext);
                    exitApp(MerchantsBigDetailActivity3.this);
                } else {
                    showToast(failuer.getEmsg());
                }
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
}
