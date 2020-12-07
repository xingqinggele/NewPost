package com.example.newpost.net;

import com.example.newpost.home_fragment.home_terminal.merchantstransfer.bean.MermachineBean;

import java.util.List;

/**
 * 作者：zb.
 * <p>
 * 时间：On 2019-05-05.
 * <p>
 * 描述：所有的请求接口
 */
public class HttpRequest {
    /**
     * 登录接口
     * @param params
     * @param callback
     */
    public static void getLogin(RequestParams params, String token,ResponseCallback callback) {
        RequestMode.postRequest(Urls.commUrls+"pos/userInfo/login",  params,token, callback, null);
    }
    /**
     * 获取验证码接口
     * @param params
     * @param callback
     */
    public static void getRegister_Code(RequestParams params,String token, ResponseCallback callback) {
        RequestMode.postRequest(Urls.commUrls+"pos/userInfo/getPhoneCode", params,token, callback, null);
    }

      /**
     * 注册接口
     * @param params
     * @param callback
     */
    public static void getRegister(RequestParams params,String token, ResponseCallback callback) {
        RequestMode.postRequest(Urls.commUrls, params,token, callback, null);
    }

    /**
     * 修改密码
     * @param params
     * @param callback
     */
    public static void getUpdPassword(RequestParams params,String token, ResponseCallback callback) {
        RequestMode.postRequest(Urls.commUrls+"pos/api/updPassword", params, token,callback, null);
    }

    /**
     * 忘记密码
     * @param params
     * @param callback
     */
    public static void getRetrievePassword(RequestParams params,String token, ResponseCallback callback) {
        RequestMode.postRequest(Urls.commUrls+"pos/userInfo/forPassword", params, token,callback, null);
    }
    /**
     * 获取腾讯云秘钥
     * @param params
     * @param callback
     */
    public static void getTenXunKey(RequestParams params,String token, ResponseCallback callback) {
        RequestMode.postRequest(Urls.commUrls+"pos/api/getTenXunSecret", params, token,callback, null);
    }

    /**
     * 获取营业范围
     * @param params
     * @param callback
     */
    public static void getBankAndPlace(RequestParams params,String token, ResponseCallback callback) {
        RequestMode.postRequest(Urls.commUrls+"pos/param/getBankAndPlace", params, token,callback, null);
    }
/**
     * 入住
     * @param params
     * @param callback
     */
    public static void getSmallStay(RequestParams params,String token, ResponseCallback callback) {
        RequestMode.postRequest(Urls.commUrls+"pos/mct/addMerchant", params, token,callback, null);
    }


    /**
     * 获取腾讯文字识别type
     * @param params
     * @param callback
     */
    public static void getTengXunType(RequestParams params,String token, ResponseCallback callback) {
        RequestMode.postRequest(Urls.commUrls+"pos/param/getTengXunType", params, token,callback, null);
    }




    /**
     * 查询商户
     * @param params
     * @param callback
     */
    public static void getMerchant(RequestParams params,String token, ResponseCallback callback) {
        RequestMode.postRequest(Urls.commUrls+"pos/mct/getMerchant", params, token,callback, null);
    }

    /**
     * 修改商户
     * @param params
     * @param callback
     */
    public static void getModifyMerchant(RequestParams params,String token, ResponseCallback callback) {
        RequestMode.postRequest(Urls.commUrls+"pos/mct/updMerchant", params, token,callback, null);
    }


 /**
     * 获取分润表
     * @param params
     * @param callback
     */
    public static void getProfit(RequestParams params,String token, ResponseCallback callback) {
        RequestMode.postRequest1(Urls.commUrls+"pos/api/getProfit", params, token,callback, null);
    }


/**
     * 修改分润表
     * @param params
     * @param callback
     */
    public static void getAddProfitSet(RequestParams params,String token, ResponseCallback callback) {
        RequestMode.postRequest(Urls.commUrls+"pos/api/addProfitSet", params, token,callback, null);
    }

/**
     * 获取分享吗
     * @param params
     * @param callback
     */
    public static void getUserShare(RequestParams params,String token, ResponseCallback callback) {
        RequestMode.postRequest1(Urls.commUrls+"pos/api/getUserShare", params, token,callback, null);
    }


/**
     * 终端管理总页面
     * @param params
     * @param callback
     */
    public static void getUserPos(RequestParams params,String token, ResponseCallback callback) {
        RequestMode.postRequest1(Urls.commUrls+"pos/pos/getUserPos", params, token,callback, null);
    }

/**
     * 获取下级终端用户列表
     * @param params
     * @param callback
     */
    public static void getPosUserList(RequestParams params,String token, ResponseCallback callback) {
        RequestMode.postRequest1(Urls.commUrls+"pos/pos/getPosUserList", params, token,callback, null);
    }
    /**
     * 终端划拨
     * @param params
     * @param callback
     */
    public static void updPosListTo(RequestParams params, String token, List<MermachineBean> mermachineBeans ,ResponseCallback callback) {
        RequestMode.postRequest2(Urls.commUrls+"pos/pos/updPosListTo", params, token,mermachineBeans,callback, null);
    }

    /**
     * 终端回调
     * @param params
     * @param callback
     */
    public static void updPosListFrom(RequestParams params, String token, List<MermachineBean> mermachineBeans ,ResponseCallback callback) {
        RequestMode.postRequest2(Urls.commUrls+"pos/pos/updPosListFrom", params, token,mermachineBeans,callback, null);
    }

     /**
     * 终端划列表
     * @param params
     * @param callback
     */
    public static void getPosList(RequestParams params,String token, ResponseCallback callback) {
        RequestMode.postRequest1(Urls.commUrls+"pos/pos/getPosList", params, token,callback, null);
    }




}
