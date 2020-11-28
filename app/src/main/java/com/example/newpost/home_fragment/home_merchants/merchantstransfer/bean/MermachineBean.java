package com.example.newpost.home_fragment.home_merchants.merchantstransfer.bean;

/**
 * 作者: qgl
 * 创建日期：2020/11/27
 * 描述: 终端机器列表
 */
public class MermachineBean {
    public String getPosId() {
        return posId;
    }

    public void setPosId(String posId) {
        this.posId = posId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    private String posId;
    private String version;
}
