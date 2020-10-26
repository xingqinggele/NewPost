package com.example.newpost.adapter;

import android.support.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.newpost.R;
import com.example.newpost.bean.CeshiBean;

import java.util.List;

/**
 * 作者: qgl
 * 创建日期：2020/10/21
 * 描述:测试adapter
 */
public class CeshiAdapter extends BaseQuickAdapter<CeshiBean, BaseViewHolder> {
    public CeshiAdapter(int layoutResId, @Nullable List<CeshiBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CeshiBean item) {
        helper.setText(R.id.cs_title,item.getTitle());
    }
}
