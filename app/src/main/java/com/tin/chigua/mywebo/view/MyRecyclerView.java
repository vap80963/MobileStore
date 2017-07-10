package com.tin.chigua.mywebo.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by hasee on 6/4/2017.
 */

public class MyRecyclerView extends RecyclerView {


    private float default_size;

    public MyRecyclerView(Context context) {
        super(context);
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
//        init(context);
//        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.MyRecyclerView);
//        default_size = a.getDimension(R.styleable.MyRecyclerView_default_size,100);
//        a.recycle();
    }

    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);


    }

    //回调函数的一个尝试
    public void startRequest(RequestInterface requestInterface){
        requestInterface.startRequest();
    }

    public interface RequestInterface {
        void startRequest();
    }

}
