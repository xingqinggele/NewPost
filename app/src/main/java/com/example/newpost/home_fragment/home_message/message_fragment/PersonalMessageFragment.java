package com.example.newpost.home_fragment.home_message.message_fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.newpost.R;
import com.example.newpost.home_fragment.home_message.MessageActivity;

/**
 * 作者: qgl
 * 创建日期：2020/11/13
 * 描述:个人消息
 */
public class PersonalMessageFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.personal_message_fragment, container, false);
        initView(view);
        return view;
    }

    public void initView(View v){
        ((MessageActivity)getActivity()).setListSize(6,1);
    }
}
