package com.example.newpost.home_fragment.home_team.adapter;

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
public class HomeTeamAdapter extends BaseQuickAdapter<MerchantsTransferBean, BaseViewHolder> {
    public HomeTeamAdapter(List<MerchantsTransferBean> mData){
        super(R.layout.item_home_team,mData);
    }
    @Override
    protected void convert(BaseViewHolder holder, MerchantsTransferBean report) {
        SimpleDraweeView home_team_logo = holder.itemView.findViewById(R.id.home_team_logo);
        home_team_logo.setImageURI((new Uri.Builder()).scheme("res").path(String.valueOf(R.mipmap.logo_ceshi)).build());
        holder.setText(R.id.home_team_name,report.getMctUserName());
        holder.setText(R.id.home_team_phone,report.getAppUserPhone());

    }

}
