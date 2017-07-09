package com.tin.chigua.mywebo.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.tin.chigua.mywebo.R;

/**
 * Created by hasee on 6/27/2017.
 */

public class BitmapUtils {



    public static Bitmap decodeSampleBitmapFromGlide(Resources resource, GlideDrawable glideDrawable
                            ,int reqWidth, int reqHeight){
        int resId = 1;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resource,resId,options);
        options.inSampleSize = calculateSampleSize(options,reqWidth,reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(resource,R.id.toolbar,options);
    }

    private static int calculateSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {

        final int height = options.outHeight;
        final int width = options.outWidth;
        int sampleSize = 1;

        if(height > reqHeight || width > reqWidth){
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            while (halfHeight / sampleSize >= reqHeight && halfWidth /sampleSize >= reqWidth){
                sampleSize *= 2;
            }
        }
        return sampleSize;
    }

}
