package com.example.newpost.home_fragment.home_terminal.merchantstransfer.adapter;

import android.net.Uri;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.newpost.R;
import com.example.newpost.home_fragment.home_terminal.merchantstransfer.bean.MerchantsTransferBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by qgl on 2020/3/11.
 * Describe:
 */
public class MerchantsTransferPersonAdapter extends BaseQuickAdapter<MerchantsTransferBean, BaseViewHolder> {
    public MerchantsTransferPersonAdapter(List<MerchantsTransferBean> mData){
        super(R.layout.item_merchants_trans_person,mData);
    }
    @Override
    protected void convert(BaseViewHolder holder, MerchantsTransferBean report) {
        SimpleDraweeView merchant_person_logo = holder.itemView.findViewById(R.id.merchant_person_logo);
        merchant_person_logo.setImageURI((new Uri.Builder()).scheme("res").path(String.valueOf(R.mipmap.logo_ceshi)).build());
        holder.setText(R.id.merchat_person_name,report.getMctUserName());
        holder.setText(R.id.merchats_person_phone,report.getAppUserPhone());

    }

}
