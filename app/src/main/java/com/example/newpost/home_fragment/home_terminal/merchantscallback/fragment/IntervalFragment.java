package com.example.newpost.home_fragment.home_terminal.merchantscallback.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.newpost.R;

/**
 * 作者: qgl
 * 创建日期：2020/11/30
 * 描述: 区间回调
 */
public class IntervalFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.interval_fragment, container, false);
        return view;
    }
}
