package com.example.newpost.home_fragment.home_merchants.merchantsquery.adapter;

import android.net.Uri;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.newpost.R;
import com.example.newpost.home_fragment.home_merchants.merchantstransfer.bean.MerchantsTransferBean;
import com.example.newpost.home_fragment.home_merchants.merchantstransfer.bean.MermachineBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by qgl on 2020/3/11.
 * Describe:
 */
public class MerchantsQueryAdapter extends BaseQuickAdapter<MermachineBean, BaseViewHolder> {
    public MerchantsQueryAdapter(List<MermachineBean> mData){
        super(R.layout.item_merchant_squery,mData);
    }
    @Override
    protected void convert(BaseViewHolder holder, MermachineBean report) {
        SimpleDraweeView merchant_person_logo = holder.itemView.findViewById(R.id.query_img);
        merchant_person_logo.setImageURI((new Uri.Builder()).scheme("res").path(String.valueOf(R.mipmap.daiti_img)).build());
        holder.setText(R.id.query_tv_number,"序列号："+report.getPosId());
    }

}
