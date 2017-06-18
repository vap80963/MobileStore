package com.tin.chigua.mywebo.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tin.chigua.mywebo.R;
import com.tin.chigua.mywebo.bean.PicUrlBean;

import java.util.List;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by hasee on 6/16/2017.
 */

public class ShowImageViewPagerAdPater extends PagerAdapter {

    private Context mContext;
    private List<PicUrlBean> mPicUrlBeanList;
    private ProgressBar mProgressBar;
    private PhotoView mPhotoView;
    private TextView mCountTv;
    private ImageButton mMenu;

    public ShowImageViewPagerAdPater(Context context,List<PicUrlBean> picUrlBeanList) {
        mContext = context;
        mPicUrlBeanList = picUrlBeanList;
    }

    private void initView(View view) {
        mPhotoView = (PhotoView) view.findViewById(R.id.activity_show_image_img);
        mCountTv = (TextView) view.findViewById(R.id.show_image_img_count);
        mProgressBar = (ProgressBar) view.findViewById(R.id.show_image_progressbar);
        mMenu = (ImageButton) view.findViewById(R.id.show_image_menu);
    }

    @Override
    public int getCount() {
        return mPicUrlBeanList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager)container).removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        String url = mPicUrlBeanList.get(position).original_pic;
        View view = LayoutInflater.from(mContext).inflate(R.layout.activity_show_image,container,false);
        initView(view);
        mCountTv.setVisibility(View.GONE);
        mMenu.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);
//        mCountTv.setVisibility(View.GONE);
        //加载图片
        Glide.with(mContext)
                .load(url)
                .placeholder(R.drawable.camera)
                .fitCenter()
//                .listener(new RequestListener<String, GlideDrawable>() {
//                    @Override
//                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
//                        mProgressBar.setVisibility(View.GONE);
//                        return true;
//                    }
//                })
//                .animate(R.anim.)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .error(R.drawable.image_error)
                .into(mPhotoView);
        ((ViewPager)container).addView(view);
        return view;
    }
}

