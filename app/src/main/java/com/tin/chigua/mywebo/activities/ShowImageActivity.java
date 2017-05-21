package com.tin.chigua.mywebo.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tin.chigua.mywebo.R;
import com.tin.chigua.mywebo.ui.ToolbarX;

/**
 * Created by hasee on 5/21/2017.
 */

public class ShowImageActivity extends BaseActivity {

    private ToolbarX mToolbarX;
    private ImageView mImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_FullScreen);
        super.onCreate(savedInstanceState);
        init();
        mToolbarX = getToolbarX();
        mToolbarX.hide();
        String uri = getIntent().getStringExtra("img_uri");
        Glide.with(this)
                .load(uri)
                .centerCrop()
                .placeholder(R.color.gray)
                .error(R.drawable.add)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(mImageView);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void init() {
        mImageView = (ImageView) findViewById(R.id.activity_show_image_img);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_show_image;
    }
}
