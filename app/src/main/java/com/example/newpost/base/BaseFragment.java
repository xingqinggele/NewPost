package com.example.newpost.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * 作者: qgl
 * 创建日期：2020/10/21
 * 描述:基类fragment
 */
public abstract class BaseFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(getLayoutInflaterResId(),container,false);
        initView(rootView);
        initListener();
        initPresenter();
        loadData();
        return rootView;
    }
    /**
     * 加载数据
     */
    protected void loadData() {
    }

    /**
     * 创建presenter
     */
    protected void initPresenter() {
    }

    /**
     * 控件设置监听
     */
    protected void initListener() {
    }

    /**
     * 初始化控件
     *
     * @param rootView
     */
    protected abstract void initView(View rootView);

    /**
     * 布局xml的id
     *
     * @return
     */
    protected abstract int getLayoutInflaterResId();
}
