package com.example.newpost.home_fragment.home_merchants.newmerchants.bean;

/**
 * 作者: qgl
 * 创建日期：2020/11/20
 * 描述:
 */
public class Student {
    private String name;
    private int age;

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getName() {
        return name;
    }
    public int getAge() {
        return age;
    }
}
