package com.tin.chigua.mywebo.imageloader;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.stream.StreamModelLoader;
import com.tin.chigua.mywebo.R;
import com.tin.chigua.mywebo.utils.NetworkUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by hasee on 7/10/2017.
 * Class Note:
 * provide way to load image
 */

public class GlideImageLoaderStrategy implements BaseImageLoaderStrategy {


    @Override
    public void loadImage(Context context, ImageLoader imageLoader) {

        boolean flag = false;
        if(!flag){
            loadNormal(context,imageLoader);
            return;
        }

        int strategy =imageLoader.getWifiStrategy();
        if(strategy == CommonImageLoaderUtil.LOAD_STRATEGY_ONLY_WIFI){
            int netType = NetworkUtils.getNetWorkState(context);
            //如果是在wifi下才加载图片，并且当前网络是wifi,直接加载
            if(netType == NetworkUtils.NETWORK_WIFI) {
                loadNormal(context, imageLoader);
            } else {
                //如果是在wifi下才加载图片，并且当前网络不是wifi，加载缓存
                loadCache(context, imageLoader);
            }
        }else{
            //如果不是在wifi下才加载图片
            loadNormal(context,imageLoader);
        }

    }

    private void loadNormal(Context context, ImageLoader img) {
        Glide.with(context).load(img.getUrl()).fitCenter()
                .crossFade().placeholder(img.getPlaceHolder()).diskCacheStrategy(DiskCacheStrategy.RESULT)
                .error(R.drawable.ic_icon_image_error).into(img.getImgView());
    }

    private void loadCache(Context context, ImageLoader img) {
        Glide.with(context).using(new StreamModelLoader<String>() {
            @Override
            public DataFetcher<InputStream> getResourceFetcher(final String model, int i, int i1) {
                return new DataFetcher<InputStream>() {
                    @Override
                    public InputStream loadData(Priority priority) throws Exception {
                        throw new IOException();
                    }

                    @Override
                    public void cleanup() {

                    }

                    @Override
                    public String getId() {
                        return model;
                    }

                    @Override
                    public void cancel() {

                    }
                };
            }
        }).load(img.getUrl()).fitCenter().crossFade().placeholder(img.getPlaceHolder())
                .error(R.drawable.ic_icon_image_error)
                .diskCacheStrategy(DiskCacheStrategy.ALL).into(img.getImgView());
    }

}
