package com.example.newpost.home_fragment.home_list.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.newpost.R;
import com.example.newpost.bean.CeshiBean;

import java.util.List;

/**
 * 作者: qgl
 * 创建日期：2020/11/7
 * 描述:排行榜adapter
 */
public class EarningsrankingAdapter extends BaseQuickAdapter<CeshiBean, BaseViewHolder> {

    public EarningsrankingAdapter(int layoutResId, @Nullable List<CeshiBean> data) {
        super(layoutResId,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CeshiBean item) {

        helper.setText(R.id.earnings_ranking_item_name,item.getName());
        if (item.getType().equals("1")){
            helper.setVisible(R.id.earnings_ranking_item_img_ranking,true);
            helper.setImageResource(R.id.earnings_ranking_item_img_ranking,R.mipmap.earningsranking_list_item_img1);
            helper.setVisible(R.id.earnings_ranking_item_tv_ranking,false);
        }else if (item.getType().equals("2")){
            helper.setVisible(R.id.earnings_ranking_item_img_ranking,true);
            helper.setImageResource(R.id.earnings_ranking_item_img_ranking,R.mipmap.earningsranking_list_item_img4);
            helper.setVisible(R.id.earnings_ranking_item_tv_ranking,false);
        }else if (item.getType().equals("3")){
            helper.setVisible(R.id.earnings_ranking_item_img_ranking,true);
            helper.setImageResource(R.id.earnings_ranking_item_img_ranking,R.mipmap.earningsranking_list_item_img5);
            helper.setVisible(R.id.earnings_ranking_item_tv_ranking,false);
        }else {
            helper.setVisible(R.id.earnings_ranking_item_img_ranking,false);
            helper.setVisible(R.id.earnings_ranking_item_tv_ranking,true);
            helper.setText(R.id.earnings_ranking_item_tv_ranking,item.getType());
        }

    }
}
