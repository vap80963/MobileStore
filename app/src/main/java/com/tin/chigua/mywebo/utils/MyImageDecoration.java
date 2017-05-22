package com.tin.chigua.mywebo.utils;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by hasee on 5/21/2017.
 */

public class MyImageDecoration extends RecyclerView.ItemDecoration {

    private int mSpace;
    private static Context mContext;

    public MyImageDecoration(int space) {
        mSpace = space;
    }



    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.right = mSpace;
        outRect.bottom = mSpace;
        if (parent.getChildItemId(view) % 2 == 0){
            outRect.right = 0;
        }

    }
}
