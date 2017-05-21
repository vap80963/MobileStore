package com.tin.chigua.mywebo.utils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by hasee on 5/21/2017.
 */

public class MyImageDecoration extends RecyclerView.ItemDecoration {

    private int mSpace;

    public MyImageDecoration(int space) {
        mSpace = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = mSpace;
        outRect.bottom = mSpace;
        if (parent.getChildItemId(view) % 3 == 0){
            outRect.left = 0;
        }
    }
}
