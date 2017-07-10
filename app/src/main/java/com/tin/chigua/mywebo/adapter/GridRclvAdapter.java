package com.tin.chigua.mywebo.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.tin.chigua.mywebo.R;
import com.tin.chigua.mywebo.activities.ShowImageActivity;
import com.tin.chigua.mywebo.bean.PicUrlBean;
import com.tin.chigua.mywebo.constant.MyApplication;
import com.tin.chigua.mywebo.fragments.FriendsFragment;
import com.tin.chigua.mywebo.imageloader.CommonImageLoaderUtil;
import com.tin.chigua.mywebo.imageloader.ImageLoader;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hasee on 5/19/2017.
 */

public class GridRclvAdapter extends RecyclerView.Adapter {

    private static final int RCLV_ITEM = 1110;
    private static final int SINGLE_ITEM = 1111;

    private List<PicUrlBean> mPicUrlBeanList = new ArrayList<>();
    private Context mContext;

    private MyApplication mApplication;

    public GridRclvAdapter(Context context, List<PicUrlBean> picUrlBeen){
        mContext = context;
        mPicUrlBeanList.addAll(picUrlBeen);
//        mPicUrlBeen = picUrlBeen;
    }

    public interface OnItemClickListener{
        void OnClickListener(View v,int position);
        void OnLongClickListener(View v,int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType){
            case RCLV_ITEM:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_img_rcylv,parent,false);
                return new MyImageRclvViewHolder(view);
            case SINGLE_ITEM:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_img_single,parent,false);
                return new MyImageSingleViewHolder(view);
            default:
                break;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        final PicUrlBean pic = mPicUrlBeanList.get(position);
        pic.original_pic = pic.thumbnail_pic.replace("thumbnail","large");
        pic.bmiddle_pic = pic.thumbnail_pic.replace("thumbnail","bmiddle");
        String picUrl = pic.bmiddle_pic;

        MyImageRclvViewHolder imageViewHolder = null;
        MyImageSingleViewHolder singleViewHolder = null;
        if(holder instanceof MyImageRclvViewHolder){
            imageViewHolder = (MyImageRclvViewHolder) holder;
            loadImage(imageViewHolder.mImageButton,picUrl);
            setOnClickListener(imageViewHolder.mImageButton,position);
        }else if (holder instanceof MyImageSingleViewHolder){
            singleViewHolder = (MyImageSingleViewHolder) holder;
            loadImage(singleViewHolder.mImageButton,picUrl);
            setOnClickListener(singleViewHolder.mImageButton,position);
        }
    }

    private void loadImage(ImageButton imageButton, String picUrl){
        //加载图片
        if(FriendsFragment.isReclvIdle) {
//            Glide.with(mContext).load(picUrl).fitCenter().crossFade().placeholder(R.color.gray)
//                    .diskCacheStrategy(DiskCacheStrategy.RESULT).error(R.drawable.ic_icon_image_error)
//                    .into(imageViewHolder.mImageButton);
            ImageLoader imageLoader = new ImageLoader.Builder().url(picUrl)
                    .imgView(imageButton).build();
            CommonImageLoaderUtil.getInstance().loadImage(mContext,imageLoader);
        }
    }

    private void setOnClickListener(ImageButton imageButton,final int position){
        if(null != mOnItemClickListener){
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ShowImageActivity.class);
                    intent.putExtra("position",position);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("url_list", (Serializable) mPicUrlBeanList);
                    intent.putExtra("img_url_list",bundle);
                    mContext.startActivity(intent);
//                    ShowImageFragment imageFragment = ShowImageFragment.newInsatance(pic.original_pic);
//                    HomeFragment homeFragment = HomeFragment.newInstance();
//                    imageFragment.show(homeFragment.getChildFragmentManager(),"show_image");
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(mPicUrlBeanList.size() == 1){
            return SINGLE_ITEM;
        }else if (mPicUrlBeanList.size() > 1){
            return RCLV_ITEM;
        }
        return 0;
    }

    @Override
    public int getItemCount() {
        return mPicUrlBeanList.size();
    }

    class MyImageSingleViewHolder extends RecyclerView.ViewHolder{

        private ImageButton mImageButton;

        public MyImageSingleViewHolder(View itemView) {
            super(itemView);
            mImageButton = (ImageButton) itemView.findViewById(R.id.item_img_single_imgv);

        }
    }

    class MyImageRclvViewHolder extends RecyclerView.ViewHolder{

        private ImageButton mImageButton;

        public MyImageRclvViewHolder(View itemView) {
            super(itemView);
            mImageButton = (ImageButton) itemView.findViewById(R.id.item_img_rcylv_img);

        }
    }

}
