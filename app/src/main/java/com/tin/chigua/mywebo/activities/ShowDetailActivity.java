package com.tin.chigua.mywebo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

import com.tin.chigua.mywebo.R;
import com.tin.chigua.mywebo.adapter.ShowDetailAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hasee on 6/19/2017.
 */

public class ShowDetailActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private List<String> mList;
    private ImageView mImagv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);
        init();
        initRclv();

    }

    private void initRclv() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.HORIZONTAL));
        mRecyclerView.setAdapter(new ShowDetailAdapter(ShowDetailActivity.this,mList));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    private void init() {
        mRecyclerView = (RecyclerView) findViewById(R.id.activity_show_detail_rclv);
        mToolbar = (Toolbar) findViewById(R.id.activity_show_detail_toolbar);
        mImagv = (ImageView) findViewById(R.id.activity_show_detail_user_img);
        Intent intent = getIntent();
        String imgUrl = intent.getStringExtra("img_url");

        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mToolbar.setTitle("Show Detail");
        mList = new ArrayList<>();
        for (int i = 0;i < 20;i++){
            mList.add("我是第 "+ i  + "个");
        }
    }
}
