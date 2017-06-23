package com.tin.chigua.mywebo.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tin.chigua.mywebo.R;
import com.tin.chigua.mywebo.activities.ShowImageActivity;
import com.tin.chigua.mywebo.bean.PicUrlBean;
import com.tin.chigua.mywebo.constant.MyApplication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hasee on 5/19/2017.
 */

public class GridRclvAdapter extends RecyclerView.Adapter {

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_img_rcylv,parent,false);
        return new MyImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MyImageViewHolder imageViewHolder = null;
        if(holder instanceof MyImageViewHolder){
            imageViewHolder = (MyImageViewHolder) holder;
        }
        final PicUrlBean pic = mPicUrlBeanList.get(position);
        pic.original_pic = pic.thumbnail_pic.replace("thumbnail","large");
        pic.bmiddle_pic = pic.thumbnail_pic.replace("thumbnail","bmiddle");
        String picUrl = pic.bmiddle_pic;
        //加载图片
//        if(FriendsFragment.isReclvIdle){
            Glide.with(mContext)
                    .load(picUrl)
                    .centerCrop()
                    .crossFade()
                    .placeholder(R.color.gray)
//             .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .error(R.drawable.image_error)
                    .into(imageViewHolder.mImageButton);
//        }
        if(null != mOnItemClickListener){
            imageViewHolder.mImageButton.setOnClickListener(new View.OnClickListener() {
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
    public int getItemCount() {
        return mPicUrlBeanList.size();
    }

    class MyImageViewHolder extends RecyclerView.ViewHolder{

        private ImageButton mImageButton;

        public MyImageViewHolder(View itemView) {
            super(itemView);
            mImageButton = (ImageButton) itemView.findViewById(R.id.item_img_rcylv_img);

        }
    }

}
