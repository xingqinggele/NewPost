package com.example.newpost.app;

import android.app.Application;
import android.content.Context;

/**
 * 作者: qgl
 * 创建日期：2020/10/19
 * 描述:appliction
 */
public class MyApp extends Application {
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
}
