package com.example.newpost.home_fragment.home_message.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.newpost.ceshi.Bean;

import java.util.List;

/**
 * 作者: qgl
 * 创建日期：2020/11/13
 * 描述: 系统消息adapter
 */
public class SystemMeAdapter extends BaseQuickAdapter<Bean, BaseViewHolder> {
    public SystemMeAdapter(int layoutResId, @Nullable List<Bean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Bean item) {

    }
}
