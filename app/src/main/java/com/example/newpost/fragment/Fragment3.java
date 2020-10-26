package com.example.newpost.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.newpost.R;


/**
 * 作者: qgl
 * 创建日期：2020/10/21
 * 描述:fragment1
 */
public class Fragment3 extends Fragment {
    public static Fragment3 newInstance(String requestJson) {
        Fragment3 fragment = new Fragment3();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment3, container, false);
        return view;
    }
}
