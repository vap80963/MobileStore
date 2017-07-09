package com.tin.chigua.mywebo.activities;

import android.os.Bundle;

import com.tin.chigua.mywebo.R;
import com.tin.chigua.mywebo.ui.ToolbarX;

public class Main2Activity extends BaseActivity {

    private ToolbarX mToolbarX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mToolbarX = getToolbarX();
        mToolbarX.hide();
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        HomeFragment fragment = HomeFragment.newInstance();
//        if(fragment)
//        fragmentManager.beginTransaction().add(R.id.home_fragment,).commit();

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main2;
    }
}
