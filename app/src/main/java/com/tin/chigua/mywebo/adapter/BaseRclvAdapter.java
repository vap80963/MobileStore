package com.tin.chigua.mywebo.adapter;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.KeyCharacterMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tin.chigua.mywebo.R;
import com.tin.chigua.mywebo.bean.PicUrlBean;
import com.tin.chigua.mywebo.bean.StatusesBean;
import com.tin.chigua.mywebo.utils.CircleTransform;
import com.tin.chigua.mywebo.utils.RichTextUtil;
import com.tin.chigua.mywebo.utils.TimeFormatUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by hasee on 5/16/2017.
 */

public class BaseRclvAdapter extends RecyclerView.Adapter {

    private List<StatusesBean> mList = new LinkedList<>();
    private Context mContext;
    private static int width;
    private static int height;
    private volatile Map<String,String> isFisrtInit = new HashMap<>();
    private static final int space = 10;
    private LayoutInflater mInflater;

    private static final int TYPE_ITEM = 001;  //recyclerview的item类型
    private static final int TYPE_FOOTER = 002; //底部footer
    private int load_more_status = 003;  //上拉加载的状态，有上拉刷新、正在加载
    /*
    上拉加载的两个状态
     */
    private static final int PULL_LOAD_MORE = 100;  //上拉刷新
    private static final int LOADING_MORE= 101;  //正在加载
    private static final int LOAD_COMPLETE= 102;  //加载结束



    public BaseRclvAdapter(Context context, List<StatusesBean> data){
        mContext = context;
        mList = data;
        mInflater = LayoutInflater.from(mContext);
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
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_ITEM){  //如果为TYPE_ITEM类型，则加载MyItemVIewHolder
            View itemView = mInflater.inflate(R.layout.item_common_rcylv,parent,false);
            return new MyItemViewHolder(itemView);
        }else if (viewType == TYPE_FOOTER){  //如果为TYPE_FOOTER类型，则加载MyFooterViewHolder
            View footerView = mInflater.inflate(R.layout.item_footer_rcylv,parent,false);
            return new MyFooterViewHolder(footerView);
        }
        return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        if (viewHolder instanceof MyItemViewHolder){
            final MyItemViewHolder holder = (MyItemViewHolder) viewHolder;
            if (width == 0 || height == 0){
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) holder.mIcon.getLayoutParams();
                width = lp.width;
                height = lp.height;
            }
            StatusesBean statuses = mList.get(position);
            /**
             * 记载微博发布者的头像
             */
            Uri uri = Uri.parse(statuses.user.avatar_large);
            Glide.with(mContext)
                    .load(uri)
                    .asBitmap()
                    .transform(new CircleTransform(mContext))
                    .centerCrop()
                    .placeholder(R.color.gray)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.drawable.image_error)
                    .priority(Priority.HIGH)
                .into(holder.mIcon);
            //同样可以实现加载成为圆形图片
//                    .into(new BitmapImageViewTarget(holder.mIcon){
//                        @Override
//                        protected void setResource(Bitmap resource) {
//                            RoundedBitmapDrawable drawable = RoundedBitmapDrawableFactory.create(mContext.getResources(),resource);
//                            drawable.setCircular(true);
//                            holder.mIcon.setImageDrawable(drawable);
//                        }
//
//                        @Override
//                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                            super.onResourceReady(resource, glideAnimation);
////                            handlerDataChange(position);
//                        }
//                    });
            /**
             * 加载微博内容
             */
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
            /**
             * 以下为加载照片部分
             */
            List<PicUrlBean> contentPics = new ArrayList<>();
            contentPics = statuses.pic_urls;
            if(contentPics != null && contentPics.size() > 0){
                holder.mContentPhotosRcylv.setVisibility(View.VISIBLE);
                loadImages(holder.mContentPhotosRcylv,contentPics);
            }else {
                holder.mContentPhotosRcylv.setVisibility(View.GONE);
            }
            /**
             * 以下为转发内容显示
             */
            if(statuses.retweeted_status != null){
                String mReditText = "@" + statuses.retweeted_status.user.screen_name + " " + statuses.retweeted_status.text;
                holder.mReditLl.setVisibility(View.VISIBLE);
                holder.mReditContent.setText(RichTextUtil.getSpanString(mContext,mReditText));
                List<PicUrlBean> reditPics = new ArrayList<>();
                reditPics = statuses.retweeted_status.pic_urls;
                if(reditPics != null && reditPics.size() > 0){
                    holder.mReditPhotosRcylv.setVisibility(View.VISIBLE);
                    loadImages(holder.mReditPhotosRcylv,reditPics);
                }else {
                    holder.mReditPhotosRcylv.setVisibility(View.GONE);
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
        }else if (viewHolder instanceof MyFooterViewHolder){ //如果为底部，则
            MyFooterViewHolder footerViewHolder = (MyFooterViewHolder) viewHolder;
            switch (load_more_status){
                case PULL_LOAD_MORE:
                footerViewHolder.foot_view_ll.setVisibility(View.VISIBLE);
                footerViewHolder.foot_view_item_tv.setText("上拉加载更多...");
                    break;
                case LOADING_MORE:
                    footerViewHolder.foot_view_item_tv.setText("正在加载更多数据...");
                    break;
                case LOAD_COMPLETE:
                    footerViewHolder.foot_view_ll.setVisibility(View.GONE);
            }
        }
    }

    private void handlerDataChange(final int position) {
        Handler handler = new Handler();
        final Runnable r = new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        };
        handler.post(r);

    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()){
            return TYPE_FOOTER;
        }else {
            return TYPE_ITEM;
        }
    }

    public void changeMoreStatus(int status){
        load_more_status = status;
    }

    public static void MoveToPosition(RecyclerView recyclerView, int position){
        RecyclerView.LayoutManager layoutManager = null;
        layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager){
            LinearLayoutManager manager = (LinearLayoutManager) layoutManager;
            int firstItem = manager.findFirstVisibleItemPosition();
            int lastItem = manager.findLastVisibleItemPosition();
            if(position < firstItem){
                recyclerView.smoothScrollToPosition(position);
//                recyclerView.scrollToPosition(position);
            }else if (position == firstItem){
                return;
            }else if(position < 0){
                throw new KeyCharacterMap.UnavailableException("The position is error,out of the range");
            }
        }
    }

    /**
     * 用于微博内容页加载显示图片
     * @param recyclerView
     * @param pics
     */
    private void loadImages(RecyclerView recyclerView, List<PicUrlBean> pics) {

//        recyclerView.removeAllViews();
        GridLayoutManager gridLayoutManager = null;
        final GridRclvAdapter adapter = new GridRclvAdapter(mContext,pics);
        if(pics.size() == 1){
            gridLayoutManager = new GridLayoutManager(mContext,1);
        }else {
            gridLayoutManager = new GridLayoutManager(mContext,3);
        }
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
//        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(int position) {
//                return position == 0 ? 2 : 1;
//            }
//        });
//        recyclerView.addItemDecoration(new MyImageDecoration(space));
//        recyclerView.setRecyclerListener(new RecyclerView.RecyclerListener() {
//            @Override
//            public void onViewRecycled(RecyclerView.ViewHolder holder) {
//
//            }
//        });
        adapter.setOnItemClickListener(new GridRclvAdapter.OnItemClickListener() {
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
        return mList.size() + 1;
    }


    class MyItemViewHolder extends RecyclerView.ViewHolder{

        private ImageView mIcon;
        private RecyclerView mContentPhotosRcylv;
        private RecyclerView mReditPhotosRcylv;
        private TextView mUserName;
        private TextView mTime;
        private TextView mSource;
        private TextView mContent;
        private TextView mReportsCount;
        private TextView mCommentsCount;
        private TextView mAttitudesCount;
        private LinearLayout mReditLl;
        private TextView mReditContent;


        public MyItemViewHolder(View itemView) {
            super(itemView);
            mContent = (TextView) itemView.findViewById(R.id.item_common_rcyl_user_content);
            mUserName = (TextView) itemView.findViewById(R.id.item_common_rcyl_user_name);
            mTime = (TextView) itemView.findViewById(R.id.item_common_rcyl_user_time);
            mSource = (TextView) itemView.findViewById(R.id.item_common_rcyl_user_source);
            mIcon = (ImageView) itemView.findViewById(R.id.item_common_rcyl_user_icon);
            mContentPhotosRcylv = (RecyclerView) itemView.findViewById(R.id.item_common_rcyl_user_content_rcylv);
            mReditPhotosRcylv = (RecyclerView) itemView.findViewById(R.id.item_common_rcyl_redit_rcylv);
            mReportsCount = (TextView) itemView.findViewById(R.id.item_common_rcyl_user_report_count);
            mCommentsCount = (TextView) itemView.findViewById(R.id.item_common_rcyl_user_comment_count);
            mAttitudesCount = (TextView) itemView.findViewById(R.id.item_common_rcyl_user_like_count);
            mReditLl = (LinearLayout) itemView.findViewById(R.id.item_common_rcyl_redit_ll);
            mReditContent = (TextView) itemView.findViewById(R.id.item_common_rcyl_redit_content);
        }
    }

    class MyFooterViewHolder extends RecyclerView.ViewHolder{

        private LinearLayout foot_view_ll;
        private TextView foot_view_item_tv;
        public MyFooterViewHolder(View view) {
            super(view);
            foot_view_ll = (LinearLayout) view.findViewById(R.id.foot_view_ll);
            foot_view_item_tv=(TextView)view.findViewById(R.id.foot_view_item_tv);
        }

    }
}
