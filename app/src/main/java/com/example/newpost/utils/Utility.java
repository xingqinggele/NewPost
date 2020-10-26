package com.example.newpost.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 作者: qgl
 * 创建日期：2020/10/24
 * 描述:工具类
 */
public class Utility {
    /**
     * 是否大陆手机
     *
     * @param str
     * @return
     */
    public static boolean isChinaPhoneLegal(String str) {
        String regExp = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }
}
