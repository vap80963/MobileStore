package com.tin.chigua.mywebo.utils;

import android.graphics.BitmapFactory;

/**
 * Created by hasee on 6/27/2017.
 */

public class BitmapUtils {



//    public static Bitmap decodeSampleBitmapFromGlide(Resources resource, Bitmap bitmap
//                            ,int reqWidth, int reqHeight){
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true;
//        BitmapFactory.decodeByteArray(resource,bitmap,options);
//        options.inSampleSize = calculateSampleSize(options,reqWidth,reqHeight);
//        options.inJustDecodeBounds = false;
//        return BitmapFactory.decodeResource(resource,resId,options);
//    }

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
