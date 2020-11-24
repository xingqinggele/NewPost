package com.example.newpost.home_fragment.home_merchants.newmerchants;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Message;
import android.os.Handler;
import android.support.annotation.Nullable;
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
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.example.newpost.R;
import com.example.newpost.base.BaseActivity;
import com.example.newpost.bean.IdCardInfo;
import com.example.newpost.bean.JsonBean;
import com.example.newpost.cos.CosServiceFactory;
import com.example.newpost.home_fragment.home_merchants.newmerchants.bean.MerchantsDetailBean;
import com.example.newpost.net.HttpRequest;
import com.example.newpost.net.OkHttpException;
import com.example.newpost.net.RequestParams;
import com.example.newpost.net.ResponseCallback;
import com.example.newpost.utils.GetJsonDataUtil;
import com.example.newpost.utils.ImageConvertUtil;
import com.example.newpost.utils.SPUtils;
import com.example.newpost.utils.TimeUtils;
import com.example.newpost.utils.Utility;
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
import java.util.Date;
import java.util.List;

import static com.example.newpost.utils.checkID.validateCard;

/**
 * 作者: qgl
 * 创建日期：2020/10/30
 * 描述:新增小微商户填写信息1
 */
public class MerchantsDetailActivity1 extends BaseActivity implements View.OnClickListener {
    //省市区选择
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    // 城市选择，省
    private List<JsonBean> options1Items = new ArrayList<>();
    // 城市选择，市
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    // 城市选择，区
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    // 线程
    private Thread thread;
    // 是否导入json数据
    private static boolean isLoaded = false;

    // 腾讯云ORC是否启用状态
    private boolean OrcType = false;  // true 启用，false不启用
    private static final int Id_POSITIVE = 00201;   // 身份证正面
    private static final int Id_REVERSE = 00202;    // 身份证反面
    private static final int Id_HANDHELD = 00203;   // 手持身份证
    //返回键
    private LinearLayout iv_back;
    //身份证正面
    private SimpleDraweeView merchant_detail_card_is;
    //身份证反面
    private SimpleDraweeView merchant_detail_card_the;
    //手持身份证
    private SimpleDraweeView merchant_detail_take_card;
    //姓名
    private EditText merchant_detail_name;
    //身份证号码
    private EditText merchant_detail_card_number;
    //身份证有效期
    private TextView merchant_detail_card_year;
    //商铺地址
    private TextView merchant_detail_address;
    //营业范围
    private TextView merchant_detail_type;
    //商户详细地址
    private EditText merchant_details_address_detail;
    // 提交，保存，跳转
    private Button merchant_detail_submit;
    private String IdName; //身份证名字
    private String IdNumber; // 身份证号码
    private String IdValidDate; //身份证有效期
    private String secretId;  // 腾讯秘钥ID
    private String secretKey; // 腾讯秘钥key
    //营业范围数据配置
    private List<MerchantsDetailBean> mdBean = new ArrayList<>();
    // 选择控件
    private OptionsPickerView reasonPicker;
    //已经选择图片
    private List<LocalMedia> selectList = new ArrayList<>();
    //照片选择最大值
    private int maxSelectNum = 1;

    private Bitmap bitmap1;//身份证正面
    private Bitmap bitmap2;//身份证反面
    private Bitmap bitmap3;// 手持身份证
    private String url1;
    private String url2;
    private String url3;
    //对象存储，图片上传到腾讯云
    private CosXmlService cosXmlService;
    private TransferManager transferManager;
    private COSXMLUploadTask cosxmlTask;
    private String folderName = "";
    private String bucketName = "cykj-1303987307";
    private String idCardIs_url = "";//身份证正面
    private String idCardNo_url = ""; //身份证反面
    private String idCardTask_url = "";// 手持身份证
    private String mctScope = ""; // 营业类型ID

    private TextView merchant_detail_audit;

    @Override
    protected int getLayoutId() {
        return R.layout.merchants_datailactivity1;
    }

    @Override
    protected void initView() {
        getMerchant();


        secretId = SPUtils.get(MerchantsDetailActivity1.this, "secretId", "-1").toString();
        secretKey = SPUtils.get(MerchantsDetailActivity1.this, "secretKey", "-1").toString();
        cosXmlService = CosServiceFactory.getCosXmlService(this, "ap-beijing", secretId, secretKey, true);
        TransferConfig transferConfig = new TransferConfig.Builder().build();
        transferManager = new TransferManager(cosXmlService, transferConfig);
        // 初始化控件
        iv_back = findViewById(R.id.iv_back);
        merchant_detail_submit = findViewById(R.id.merchant_detail_submit);
        merchant_detail_address = findViewById(R.id.merchant_detail_address);
        merchant_detail_card_is = findViewById(R.id.merchant_detail_card_is);
        merchant_detail_card_the = findViewById(R.id.merchant_detail_card_the);
        merchant_detail_take_card = findViewById(R.id.merchant_detail_take_card);
        merchant_detail_name = findViewById(R.id.merchant_detail_name);
        merchant_detail_card_number = findViewById(R.id.merchant_detail_card_number);
        merchant_detail_card_year = findViewById(R.id.merchant_detail_card_year);
        merchant_detail_type = findViewById(R.id.merchant_detail_type);
        merchant_details_address_detail = findViewById(R.id.merchant_details_address_detail);
        // 点击事件
        iv_back.setOnClickListener(this);
        merchant_detail_submit.setOnClickListener(this);
        merchant_detail_address.setOnClickListener(this);
        merchant_detail_card_is.setOnClickListener(this);
        merchant_detail_card_the.setOnClickListener(this);
        merchant_detail_take_card.setOnClickListener(this);
        merchant_detail_card_year.setOnClickListener(this);
        merchant_detail_type.setOnClickListener(this);

        merchant_detail_audit = findViewById(R.id.merchant_detail_audit);

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
            //获取地址
            case R.id.merchant_detail_address:
                if (isLoaded) {
                    showPickerView();
                } else {
                    Toast.makeText(MerchantsDetailActivity1.this, "Please waiting until the data is parsed", Toast.LENGTH_SHORT).show();
                }
                break;
            // 获取身份证正面
            case R.id.merchant_detail_card_is:
                initSdk(secretId, secretKey);
                if (OrcType) {
                    //弹出界面
                    OcrSDKKit.getInstance().startProcessOcr(MerchantsDetailActivity1.this, OcrType.IDCardOCR_FRONT, null,
                            new ISDKKitResultListener() {
                                @Override
                                public void onProcessSucceed(String response, String srcBase64Image, String requestId) {
                                    IdCardInfo tempIdCardInfo = new Gson().fromJson(response, IdCardInfo.class);
                                    Log.e("response", tempIdCardInfo.getRequestId());
                                    Bitmap bitmap = ImageConvertUtil.base64ToBitmap(srcBase64Image);
                                    merchant_detail_card_is.setImageBitmap(bitmap);
                                    bitmap1 = bitmap;
                                    Log.e("姓名" + tempIdCardInfo.getName(), "身份证号码" + tempIdCardInfo.getIdNum());
                                    IdName = tempIdCardInfo.getName();
                                    IdNumber = tempIdCardInfo.getIdNum();
                                    setResultListData();
                                }

                                @Override
                                public void onProcessFailed(String errorCode, String message, String requestId) {
                                    popTip(errorCode, message);
                                    Log.e("requestId", requestId);
                                    IdName = "";
                                    IdNumber = "";
                                }
                            });
                } else {
                    // 使用自己的获取图片方法，身份证正面
                    initSelectImage(Id_POSITIVE);
                }
                break;
            // 获取身份证反面
            case R.id.merchant_detail_card_the:
                if (OrcType) {
                    initSdk(secretId, secretKey);
                    //身份证反面
                    OcrSDKKit.getInstance().startProcessOcr(MerchantsDetailActivity1.this, OcrType.IDCardOCR_BACK, null,
                            new ISDKKitResultListener() {
                                @Override
                                public void onProcessSucceed(String response, String srcBase64Image, String requestId) {
                                    IdCardInfo tempIdCardInfo = new Gson().fromJson(response, IdCardInfo.class);
                                    Log.e("response", tempIdCardInfo.getRequestId());
                                    Bitmap bitmap = ImageConvertUtil.base64ToBitmap(srcBase64Image);
                                    merchant_detail_card_the.setImageBitmap(bitmap);
                                    bitmap2 = bitmap;
                                    IdValidDate = tempIdCardInfo.getValidDate();
                                    setResultListData();
                                }

                                @Override
                                public void onProcessFailed(String errorCode, String message, String requestId) {
                                    popTip(errorCode, message);
                                    Log.e("11111requestId", requestId);
                                    IdValidDate = "";
                                }
                            });
                } else {
                    // 使用自己的获取图片方法，身份证反面
                    initSelectImage(Id_REVERSE);
                }
                break;
            // 获取手持身份证
            case R.id.merchant_detail_take_card:
                initSelectImage(Id_HANDHELD);
                break;
            // 获取身份证有效期
            case R.id.merchant_detail_card_year:
                selectTime();
                break;
            // 获取营业类型
            case R.id.merchant_detail_type:
                initReason();
                reasonPicker.show();
                break;
            case R.id.merchant_detail_submit:
                if (bitmap1 == null) {
                    showToast("请上传身份证正面照片");
                    return;
                }
                if (bitmap2 == null) {
                    showToast("请上传身份证反面照片");
                    return;
                }
                if (bitmap3 == null) {
                    showToast("请上传手持身份证照片");
                    return;
                }
                if (TextUtils.isEmpty(merchant_detail_name.getText().toString().trim())) {
                    showToast("请输入姓名");
                    return;
                }
                if (TextUtils.isEmpty(merchant_detail_card_number.getText().toString().trim())) {
                    showToast("请输入身份证号码");
                    return;
                }
                if (TextUtils.isEmpty(merchant_detail_card_year.getText().toString().trim())) {
                    showToast("请选择身份证有效期");
                    return;
                }
                if (TextUtils.isEmpty(merchant_detail_address.getText().toString().trim())) {
                    showToast("请选择商铺地址");
                    return;
                }
                if (TextUtils.isEmpty(merchant_details_address_detail.getText().toString().trim())) {
                    showToast("请输入商铺详细地址");
                    return;
                }
                if (TextUtils.isEmpty(mctScope)) {
                    showToast("请选择营业范围");
                    return;
                }
                if (!validateCard(merchant_detail_card_number.getText().toString().trim())) {
                    showToast("请选输入正确的身份证号");
                    return;
                }
                loadDialog.show();
                try {
                    url1 = ImageConvertUtil.getFile(bitmap1).getCanonicalPath();
                    url2 = ImageConvertUtil.getFile(bitmap2).getCanonicalPath();
                    url3 = ImageConvertUtil.getFile(bitmap3).getCanonicalPath();
                    //文件名称
                    folderName = TimeUtils.getNowTime() + "/" + merchant_detail_card_number.getText().toString().trim();
                    //开始上传图片
                    upload(folderName, url1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_DATA:
                    if (thread == null) {//如果已创建就不再重新创建子线程了

                        Toast.makeText(MerchantsDetailActivity1.this, "Begin Parse Data", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(MerchantsDetailActivity1.this, "Parse Succeed", Toast.LENGTH_SHORT).show();
                    isLoaded = true;
                    break;

                case MSG_LOAD_FAILED:
                    Toast.makeText(MerchantsDetailActivity1.this, "Parse Failed", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

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
                merchant_detail_address.setText(tx);
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

    private void selectTime() {
        TimePickerView pvTime = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                //设置时间
                merchant_detail_card_year.setText(Utility.getTime(date));
            }
        }).setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("年", "月", "日", "时", "分", "秒")
                .build();
        pvTime.show();
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

    // 配置识别出来的数据
    private void setResultListData() {
        if (IdName != null && !IdName.isEmpty()) {
            merchant_detail_name.setText(IdName);
            merchant_detail_card_number.setText(IdNumber);
        }
        if (IdValidDate != null && !IdValidDate.isEmpty()) {
            merchant_detail_card_year.setText(IdValidDate.substring(11));
        }
    }

    /**
     * 获取营业范围
     */
    public void getBankAndPlace() {
        RequestParams params = new RequestParams();
        HttpRequest.getBankAndPlace(params, SPUtils.get(MerchantsDetailActivity1.this, "Token", "-1").toString(), new ResponseCallback() {
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
                    exitApp(MerchantsDetailActivity1.this);
                } else {
                    showToast(failuer.getEmsg());
                }
            }
        });


    }

    private void initReason() {
        List<String> name = new ArrayList<>();
        for (int i = 0; i < mdBean.size(); i++) {
            name.add(mdBean.get(i).getPlaceName());
        }
        reasonPicker = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                merchant_detail_type.setText(name.get(options1));
                mctScope = mdBean.get(options1).getPlaceId();
            }
        }).setTitleText("营业范围").setContentTextSize(22).setTitleSize(22).setSubCalSize(21).build();
        reasonPicker.setPicker(name);
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
                case Id_HANDHELD:
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
                            bitmap3 = bitmap;
                            merchant_detail_take_card.setImageBitmap(bitmap);
                        } else {
                            merchant_detail_take_card.setImageResource(R.mipmap.merchant_detail_take_car);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
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
                            merchant_detail_card_is.setImageBitmap(bitmap);
                            bitmap1 = bitmap;
                        } else {
                            merchant_detail_card_is.setImageResource(R.mipmap.merchant_detail_car1);
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
                            merchant_detail_card_the.setImageBitmap(bitmap);
                            bitmap2 = bitmap;

                        } else {
                            merchant_detail_card_the.setImageResource(R.mipmap.merchant_detail_car2);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OcrSDKKit.getInstance().release();
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
                    idCardIs_url = result.accessUrl;
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
                    // setResult(RESULT_OK);
                    Log.e("2222", "长传成功");
                    idCardNo_url = result.accessUrl;
                    upload2(folderName, url3);
                }

                @Override
                public void onFail(CosXmlRequest request, CosXmlClientException exception, CosXmlServiceException serviceException) {
                    if (cosxmlTask.getTaskState() != TransferState.PAUSED) {
                        cosxmlTask = null;

                        Log.e("2222", "上传失败");

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
            Log.e("参数-------》", "aaaaaaaaa");

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
                    setResult(RESULT_OK);
                    idCardTask_url = result.accessUrl;
                    Log.e("3333", "上传成功");

                    loadDialog.dismiss();
                    Intent intent = new Intent(MerchantsDetailActivity1.this, MerchantsDetailActivity22.class);
                    intent.putExtra("userName", merchant_detail_name.getText().toString().trim());
                    intent.putExtra("idCard_number", merchant_detail_card_number.getText().toString().trim());
                    intent.putExtra("idCard_year", merchant_detail_card_year.getText().toString().trim());
                    intent.putExtra("address", merchant_detail_address.getText().toString().trim());
                    intent.putExtra("address_detail", merchant_details_address_detail.getText().toString().trim());
                    intent.putExtra("detail_type", mctScope);
                    intent.putExtra("idCardIs_url", idCardIs_url);
                    intent.putExtra("idCardNo_url", idCardNo_url);
                    intent.putExtra("idCardTask_url", idCardTask_url);
                    startActivity(intent);
                }

                @Override
                public void onFail(CosXmlRequest request, CosXmlClientException exception, CosXmlServiceException serviceException) {
                    if (cosxmlTask.getTaskState() != TransferState.PAUSED) {
                        cosxmlTask = null;
                        Log.e("3333", "上传失败");
                    }
                    exception.printStackTrace();
                    serviceException.printStackTrace();
                }
            });

        }
    }

    /*********开始--->查询是否入住过******/
    /**
     * 查询商户信息
     */
    public void getMerchant() {
        RequestParams params = new RequestParams();
        HttpRequest.getMerchant(params, SPUtils.get(MerchantsDetailActivity1.this, "Token", "-1").toString(), new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    JSONObject data = new JSONObject(result.getJSONObject("data").toString());
                    String mctAuditState = data.getString("mctAuditState");
                    String mct_url1 = data.getString("mctCardFileY");
                    String mct_url2 = data.getString("mctCardFileN");
                    String mct_url3 = data.getString("mctCardFileHand");
                    String mct_name = data.getString("mctUserName");
                    String mct_card_number = data.getString("mctCartNum");
                    String mct_card_year = data.getString("mctCartAging");
                    String mct_address = data.getString("mctCity");
                    String mct_detail_address = data.getString("mctAddress");
                    String mct_type = data.getString("placeName");

                    if (mctAuditState.equals("1")) {
                        // 审核中不让查看
                        merchant_detail_audit.setVisibility(View.GONE);
                        startActivity(new Intent(MerchantsDetailActivity1.this, MerchantsDetailSubActivity.class));
                    } else if (mctAuditState.equals("2")) {
                        // 审核成功可以查看，开始赋值
                        merchant_detail_audit.setVisibility(View.GONE);
                        merchant_detail_card_is.setImageURI(mct_url1);
                        merchant_detail_card_the.setImageURI(mct_url2);
                        merchant_detail_take_card.setImageURI(mct_url3);
                        merchant_detail_name.setText(mct_name);
                        merchant_detail_card_number.setText(mct_card_number);
                        merchant_detail_card_year.setText(mct_card_year);
                        merchant_detail_address.setText(mct_address);
                        merchant_details_address_detail.setText(mct_detail_address);
                        merchant_detail_type.setText(mct_type);
                    } else {
                        // 审核驳回可以查看，开始赋值
                        merchant_detail_audit.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(OkHttpException failuer) {

            }
        });


    }

}
