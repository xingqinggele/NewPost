package com.example.newpost.home_fragment.home_terminal.merchantscallback;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.newpost.R;

import java.util.List;

/**
 * @author hiphonezhu@gmail.com
 * @version [NestedScrolling, 16/9/13 14:50]
 */
public class MyAdapter_a extends RecyclerView.Adapter<MyAdapter_a.MyViewHolder> {
    Context mContext;
    List<String> mDatas;
    public MyAdapter_a(Context context, List<String> datas)
    {
        mContext = context;
        mDatas = datas;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv.setText(mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatas != null? mDatas.size() : 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView tv;

        public MyViewHolder(View view)
        {
            super(view);
            tv = (TextView) view.findViewById(R.id.tv_item);
        }
    }
}
