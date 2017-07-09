package com.tin.chigua.mywebo.ui;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.tin.chigua.mywebo.R;

/**
 * Created by hasee on 5/6/2017.
 */

public class ToolbarX {

    private Toolbar mToolbar;
    private AppCompatActivity mActivity;
    private ActionBar mActionBar;
    private RelativeLayout rlCustom;

    public ToolbarX(Toolbar toolbar, AppCompatActivity activity) {
        mToolbar = toolbar;
        mActivity = activity;
        mActivity.setSupportActionBar(mToolbar);
        mActionBar = mActivity.getSupportActionBar();
        rlCustom = (RelativeLayout) mToolbar.findViewById(R.id.rlCustom);
    }

    public ToolbarX setTitle(String title){
        mActionBar.setDisplayShowTitleEnabled(true);
        mActionBar.setTitle(title);
        return this;
    }

    public ToolbarX setSubTitle(String title){
        mActionBar.setSubtitle(title);
        return this;
    }

    public ToolbarX setTitle(int resId){
        mActionBar.setTitle(resId);
        return this;
    }
    public ToolbarX setSubTitle(int resId){
        mActionBar.setSubtitle(resId);
        return this;
    }

    public ToolbarX setNavigationOnClickListener(View.OnClickListener listener){
        mToolbar.setNavigationOnClickListener(listener);
        return this;
    }

    public ToolbarX setNavigationIcon(int resId){
        mToolbar.setNavigationIcon(resId);
        return this;
    }

    public ToolbarX setDisplayHomeAsUpEnabled(boolean show){
        mActionBar.setDisplayHomeAsUpEnabled(show);
        return this;
    }

    public ToolbarX show(){
        mActionBar.show();
        return this;
    }

    public ViewGroup getCustomView(){
        return rlCustom;
    }

    public ToolbarX setCustomeView(View view){
        rlCustom.removeAllViews();
        rlCustom.addView(view);
        return this;
    }

    public ToolbarX hide() {
        mActionBar.hide();
        return this;
    }

}
