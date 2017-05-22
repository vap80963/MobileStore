package com.tin.chigua.mywebo.adapter;

import android.content.Context;
import android.content.Intent;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hasee on 5/19/2017.
 */

public class ShowImageAdapter extends RecyclerView.Adapter {

    private List<PicUrlBean> mPicUrlBeen = new ArrayList<>();
    private Context mContext;

    public ShowImageAdapter(Context context,List<PicUrlBean> picUrlBeen){
        mContext = context;
        mPicUrlBeen.addAll(picUrlBeen);
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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyImageViewHolder imageViewHolder = null;
        if(holder instanceof MyImageViewHolder){
            imageViewHolder = (MyImageViewHolder) holder;
        }
        final PicUrlBean pic = mPicUrlBeen.get(position);
        pic.original_pic = pic.thumbnail_pic.replace("thumbnail","large");
        pic.bmiddle_pic = pic.thumbnail_pic.replace("thumbnail","bmiddle");
        String picUrl = pic.thumbnail_pic;
        Glide.with(mContext)
             .load(picUrl)
             .centerCrop()
//             .fitCenter()
             .diskCacheStrategy(DiskCacheStrategy.RESULT)
             .error(R.drawable.add)
             .placeholder(R.color.gray)
             .into(imageViewHolder.mImageButton);
        if(null != mOnItemClickListener){
            imageViewHolder.mImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ShowImageActivity.class);
                    intent.putExtra("img_uri",pic.original_pic);
                    mContext.startActivity(intent);
                }
            });
        }

    }


    @Override
    public int getItemCount() {
        return mPicUrlBeen.size();
    }

    class MyImageViewHolder extends RecyclerView.ViewHolder{

        private ImageButton mImageButton;

        public MyImageViewHolder(View itemView) {
            super(itemView);
            mImageButton = (ImageButton) itemView.findViewById(R.id.item_img_rcylv_img);

        }
    }

}
