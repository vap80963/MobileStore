package com.tin.chigua.mywebo.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.tin.chigua.mywebo.R;
import com.tin.chigua.mywebo.bean.PicUrlBean;
import com.tin.chigua.mywebo.bean.StatusesBean;
import com.tin.chigua.mywebo.utils.MyImageDecoration;
import com.tin.chigua.mywebo.utils.RichTextUtil;
import com.tin.chigua.mywebo.utils.TimeFormatUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hasee on 5/16/2017.
 */

public class BaseRclvAdapter extends RecyclerView.Adapter {

    private List<StatusesBean> mList = new ArrayList<>();
    private Context mContext;
    private static int width;
    private static int height;
    private static final int space = 10;

    public BaseRclvAdapter(Context context, List<StatusesBean> data){
        mContext = context;
        mList = data;
    }

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view , int position);
    }

    private BaseRclvAdapter.OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(BaseRclvAdapter.OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_common_rcylv,parent,false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        final MyViewHolder holder = (MyViewHolder) viewHolder;
        if (width == 0 || height == 0){
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) holder.mIcon.getLayoutParams();
            width = lp.width;
            height = lp.height;
        }
        StatusesBean statuses = mList.get(position);
        Uri uri = Uri.parse(statuses.user.avatar_large);
        Glide.with(mContext)
                .load(uri)
                .asBitmap()
                .placeholder(R.drawable.add)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.add)
//                .into(holder.mIcon);
                .into(new BitmapImageViewTarget(holder.mIcon){
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable drawable = RoundedBitmapDrawableFactory.create(mContext.getResources(),resource);
                        drawable.setCircular(true);
                        holder.mIcon.setImageDrawable(drawable);
                    }
                });
        holder.mContent.setText(RichTextUtil.getSpanString(mContext,statuses.text));
        holder.mContent.setMovementMethod(LinkMovementMethod.getInstance());
        holder.mTime.setText(TimeFormatUtils.parseToYYMMDD(statuses.created_at));
        String source = Html.fromHtml(statuses.source).toString();
        if(!source.equals("")){
            holder.mSource.setText("来自" + source);
        }
        holder.mUserName.setText(statuses.user.name);
        if(statuses.reposts_count > 0){
            holder.mReportsCount.setText("" + statuses.reposts_count);
        }
        if (statuses.comments_count > 0){
            holder.mCommentsCount.setText("" + statuses.comments_count);
        }
        if (statuses.attitudes_count > 0){
            holder.mAttitudesCount.setText("" + statuses.attitudes_count);
        }
        List<PicUrlBean> contentPics = new ArrayList<>();
        contentPics = statuses.pic_urls;
        if(contentPics != null && contentPics.size() > 0){
            holder.mContentRcylv.setVisibility(View.VISIBLE);
            loadImages(holder.mContentRcylv,contentPics);
        }else {
            holder.mContentRcylv.setVisibility(View.GONE);
        }
        if(statuses.retweeted_status != null){
            String mReditText = "@" + statuses.retweeted_status.user.screen_name + " " + statuses.retweeted_status.text;
            holder.mReditLl.setVisibility(View.VISIBLE);
            holder.mReditContent.setText(RichTextUtil.getSpanString(mContext,mReditText));
            List<PicUrlBean> reditPics = new ArrayList<>();
            reditPics = statuses.retweeted_status.pic_urls;
            if(reditPics != null && reditPics.size() > 0){
                holder.mReditRcylv.setVisibility(View.VISIBLE);
                loadImages(holder.mReditRcylv,reditPics);
            }else {
                holder.mReditRcylv.setVisibility(View.GONE);
            }
        }else {
            holder.mReditLl.setVisibility(View.GONE);
        }
        // 如果设置了回调，则设置点击事件
        if (mOnItemClickLitener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener()
            {
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


    private void loadImages(RecyclerView view, List<PicUrlBean> pics) {
        RecyclerView recyclerView = new RecyclerView(mContext);
        recyclerView = view;
        recyclerView.removeAllViews();
        final ShowImageAdapter adapter = new ShowImageAdapter(mContext,pics);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext,3);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
//        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(int position) {
//                return position == 0 ? 2 : 1;
//            }
//        });
        recyclerView.addItemDecoration(new MyImageDecoration(space));
//        recyclerView.setRecyclerListener(new RecyclerView.RecyclerListener() {
//            @Override
//            public void onViewRecycled(RecyclerView.ViewHolder holder) {
//
//            }
//        });
        adapter.setOnItemClickListener(new ShowImageAdapter.OnItemClickListener() {
            @Override
            public void OnClickListener(View v, int position) {
                
            }

            @Override
            public void OnLongClickListener(View v, int position) {

            }
        });
        recyclerView.setAdapter(adapter);
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder{

        private ImageView mIcon;
        private RecyclerView mContentRcylv;
        private RecyclerView mReditRcylv;
        private TextView mUserName;
        private TextView mTime;
        private TextView mSource;
        private TextView mContent;
        private TextView mReportsCount;
        private TextView mCommentsCount;
        private TextView mAttitudesCount;
        private LinearLayout mReditLl;
        private TextView mReditContent;


        public MyViewHolder(View itemView) {
            super(itemView);
            mContent = (TextView) itemView.findViewById(R.id.item_common_rcyl_user_content);
            mUserName = (TextView) itemView.findViewById(R.id.item_common_rcyl_user_name);
            mTime = (TextView) itemView.findViewById(R.id.item_common_rcyl_user_time);
            mSource = (TextView) itemView.findViewById(R.id.item_common_rcyl_user_source);
            mIcon = (ImageView) itemView.findViewById(R.id.item_common_rcyl_user_icon);
            mContentRcylv = (RecyclerView) itemView.findViewById(R.id.item_common_rcyl_user_content_rcylv);
            mReditRcylv = (RecyclerView) itemView.findViewById(R.id.item_common_rcyl_redit_rcylv);
            mReportsCount = (TextView) itemView.findViewById(R.id.item_common_rcyl_user_report_count);
            mCommentsCount = (TextView) itemView.findViewById(R.id.item_common_rcyl_user_comment_count);
            mAttitudesCount = (TextView) itemView.findViewById(R.id.item_common_rcyl_user_like_count);
            mReditLl = (LinearLayout) itemView.findViewById(R.id.item_common_rcyl_redit_ll);
            mReditContent = (TextView) itemView.findViewById(R.id.item_common_rcyl_redit_content);
        }
    }

}
