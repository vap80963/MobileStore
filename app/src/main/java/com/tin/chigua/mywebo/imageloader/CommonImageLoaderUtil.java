package com.tin.chigua.mywebo.imageloader;

import android.content.Context;

/**
 * Created by hasee on 7/10/2017.
 */

public class CommonImageLoaderUtil {

    public static final int PIC_LARGE = 0;
    public static final int PIC_MEDIUM = 1;
    public static final int PIC_SMALL = 2;

    public static final int LOAD_STRATEGY_NORMAL = 0;
    public static final int LOAD_STRATEGY_ONLY_WIFI = 1;

    private BaseImageLoaderStrategy imageStrategy;

    private CommonImageLoaderUtil(){
        imageStrategy = new GlideImageLoaderStrategy();
    }

    private static class CommonImageLoaderHolder{
        private static final CommonImageLoaderUtil INSTANCE = new CommonImageLoaderUtil();
    }

    public static CommonImageLoaderUtil getInstance(){
        return CommonImageLoaderHolder.INSTANCE;
    }

    public void loadImage(Context context, ImageLoader imageLoader){
        imageStrategy.loadImage(context,imageLoader);
    }

    public void setImageStrategy(BaseImageLoaderStrategy baseImageLoaderStrategy){
        imageStrategy = baseImageLoaderStrategy;
    }

}
