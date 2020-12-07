package com.example.newpost.home_fragment.home_terminal.merchantstransfer.bean;

/**
 * 作者: qgl
 * 创建日期：2020/11/25
 * 描述: 终端划拨Bean
 */
public class MerchantsTransferBean {

    public String getAppUserPhone() {
        return appUserPhone;
    }

    public void setAppUserPhone(String appUserPhone) {
        this.appUserPhone = appUserPhone;
    }

    public String getMctUserName() {
        return mctUserName;
    }

    public void setMctUserName(String mctUserName) {
        this.mctUserName = mctUserName;
    }

    public String getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(String appUserId) {
        this.appUserId = appUserId;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    private String appUserPhone;
    private String mctUserName;
    private String appUserId;
    private boolean isChecked;

}
