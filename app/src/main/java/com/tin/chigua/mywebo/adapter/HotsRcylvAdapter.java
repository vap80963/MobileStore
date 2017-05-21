package com.tin.chigua.mywebo.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tin.chigua.mywebo.R;
import com.tin.chigua.mywebo.bean.StatusesBean;
import com.tin.chigua.mywebo.utils.CircleTransform;
import com.tin.chigua.mywebo.utils.TimeFormatUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hasee on 5/7/2017.
 */

public class HotsRcylvAdapter extends RecyclerView.Adapter<HotsRcylvAdapter.MyViewHolder> {

    private List<StatusesBean> mList = new ArrayList<>();
    private Context mContext;

    public HotsRcylvAdapter(Context context, List<StatusesBean> data){
        mContext = context;
        mList = data;
    }

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder viewHolder = new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_common_rcylv,parent,false));
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        StatusesBean statuses = mList.get(position);
        Uri uri = Uri.parse(statuses.user.avatar_large);
        Glide.with(mContext)
                .load(uri)
                .centerCrop()
                .transform(new CircleTransform(mContext))
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(holder.mIcon);
        holder.mContent.setText(statuses.text);
        holder.mTime.setText(TimeFormatUtils.parseToYYMMDD(statuses.created_at));
        String source = Html.fromHtml(statuses.source).toString();
        if(!source.equals("")){
            holder.mSource.setText("来自" + source);
        }
        holder.mUserName.setText(statuses.user.name);
        holder.mReportsCount.setText("" + statuses.reposts_count);
        holder.mCommentsCount.setText("" + statuses.comments_count);
        holder.mAttitudesCount.setText("" + statuses.attitudes_count);
        // 如果设置了回调，则设置点击事件
        if (mOnItemClickLitener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.itemView, pos);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener()
            {
                @Override
                public boolean onLongClick(View v)
                {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemLongClick(holder.itemView, pos);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private ImageView mIcon;
        private TextView mUserName;
        private TextView mTime;
        private TextView mSource;
        private TextView mContent;
        private TextView mReportsCount;
        private TextView mCommentsCount;
        private TextView mAttitudesCount;


        public MyViewHolder(View itemView) {
            super(itemView);
            mContent = (TextView) itemView.findViewById(R.id.item_common_rcyl_user_content);
            mUserName = (TextView) itemView.findViewById(R.id.item_common_rcyl_user_name);
            mTime = (TextView) itemView.findViewById(R.id.item_common_rcyl_user_time);
            mSource = (TextView) itemView.findViewById(R.id.item_common_rcyl_user_source);
            mIcon = (ImageView) itemView.findViewById(R.id.item_common_rcyl_user_icon);
            mReportsCount = (TextView) itemView.findViewById(R.id.item_common_rcyl_user_report_count);
            mCommentsCount = (TextView) itemView.findViewById(R.id.item_common_rcyl_user_comment_count);
            mAttitudesCount = (TextView) itemView.findViewById(R.id.item_common_rcyl_user_like_count);
        }


    }

}
