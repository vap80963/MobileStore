package com.tin.chigua.mywebo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tin.chigua.mywebo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hasee on 7/11/2017.
 */

public class ShowDetailAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<String> mList = new ArrayList<>();

    public ShowDetailAdapter(Context context, List<String> list){
        mContext = context;
        mList.addAll(list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_show_detail_rcylv,parent,false);
        return new MyDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (mList == null || position < 0 ||holder == null){
            return;
        }
        MyDetailViewHolder detailViewHolder = null;
        if (holder instanceof MyDetailViewHolder) {
            detailViewHolder = (MyDetailViewHolder) holder;
            detailViewHolder.mTextView.setText(mList.get(position));
        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyDetailViewHolder extends RecyclerView.ViewHolder{

        private TextView mTextView;

        public MyDetailViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.item_show_detail_tv);
        }

    }

}
