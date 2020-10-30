package com.example.newpost.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.example.newpost.R;
import com.example.newpost.base.BaseFragment;
import com.gyf.barlibrary.ImmersionBar;


/**
 * 作者: qgl
 * 创建日期：2020/10/21
 * 描述:fragment2
 */
public class Fragment2 extends BaseFragment {

    public static Fragment2 newInstance(String requestJson) {
        Fragment2 fragment = new Fragment2();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initView(View rootView) {

    }

    @Override
    protected int getLayoutInflaterResId() {

        return R.layout.fragment2;
    }


}
