package com.tin.chigua.mywebo.imageloader;

import android.content.Context;

/**
 * Created by hasee on 7/10/2017.
 * Class Note:
 * abstract class/interface defined to load image
 * (Strategy Pattern used here)
 */

public interface BaseImageLoaderStrategy {

    void loadImage(Context context,ImageLoader imageLoader);

}
