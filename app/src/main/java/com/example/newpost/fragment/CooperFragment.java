package com.example.newpost.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.newpost.R;
import com.example.newpost.base.BaseFragment;
import com.example.newpost.cooper_fragment.CooperExpandActivity;
import com.example.newpost.cooper_fragment.CooperExpandScanActivity;

/**
 * 作者: qgl
 * 创建日期：2020/10/21
 * 描述:合作方
 */
public class CooperFragment extends Fragment implements View.OnClickListener {

    private LinearLayout cooper_fragment_expand;
    public static CooperFragment newInstance(String requestJson) {
        CooperFragment fragment = new CooperFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cooper_fragment, container, false);
        initView(view);
        return view;
    }

    public void initView(View view){
        cooper_fragment_expand = view.findViewById(R.id.cooper_fragment_expand);
        cooper_fragment_expand.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cooper_fragment_expand:
                startActivity(new Intent(getActivity(), CooperExpandActivity.class));
                break;
        }
    }
}
