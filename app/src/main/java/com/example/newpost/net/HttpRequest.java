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
     * 测试接口
     *
     * @param params
     * @param callback
     */
    public static void getCar_illegal(RequestParams params, ResponseCallback callback) {
        RequestMode.postRequest(Urls.commUrls + "system/user/login", params, callback, null);
    }
    /**
     * 登录接口
     * @param params
     * @param callback
     */
    public static void getLogin(RequestParams params, ResponseCallback callback) {
        RequestMode.postRequest(Urls.commUrls, params, callback, null);
    }
    /**
     * 注册接口
     * @param params
     * @param callback
     */
    public static void getRegister(RequestParams params, ResponseCallback callback) {
        RequestMode.postRequest(Urls.commUrls, params, callback, null);
    }
}
