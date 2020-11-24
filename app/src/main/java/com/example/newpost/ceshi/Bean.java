package com.example.newpost.ceshi;

/**
 * 作者: qgl
 * 创建日期：2020/11/12
 * 描述:
 */
public class Bean {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    private String name;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    private int price;
    private String pass;

}
