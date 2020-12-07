package com.example.newpost.home_fragment.home_lecture_hall.adapter;

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
public class LecturehallAdapter extends BaseQuickAdapter<MerchantsTransferBean, BaseViewHolder> {
    public LecturehallAdapter(List<MerchantsTransferBean> mData){
        super(R.layout.item_lecturehall,mData);
    }
    @Override
    protected void convert(BaseViewHolder holder, MerchantsTransferBean report) {
        SimpleDraweeView merchant_person_logo = holder.itemView.findViewById(R.id.lecturehall_img);
        merchant_person_logo.setImageURI((new Uri.Builder()).scheme("res").path(String.valueOf(R.mipmap.daiti_img)).build());
    }

}
