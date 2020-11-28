package com.example.newpost.adapter;

import android.graphics.Color;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.newpost.R;
import com.example.newpost.ceshi.Bean;

import java.util.List;

/**
 * Created by 陈泽宇 on 2020/3/11.
 * Describe:
 */
public class WriteReportAdapter extends BaseQuickAdapter<Bean, BaseViewHolder> {
    public WriteReportAdapter(List<Bean> mData){
        super(R.layout.item_read_report,mData);
    }
    @Override
    protected void convert(BaseViewHolder holder, Bean report) {


    }

}
