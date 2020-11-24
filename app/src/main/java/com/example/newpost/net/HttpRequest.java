package com.example.newpost.net;

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


}
