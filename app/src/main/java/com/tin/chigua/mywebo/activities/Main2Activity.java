package com.tin.chigua.mywebo.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.RelativeLayout;

import com.tin.chigua.mywebo.R;
import com.tin.chigua.mywebo.ui.ToolbarX;

public class Main2Activity extends AppCompatActivity {

    private ToolbarX mToolbarX;
    private RelativeLayout customSwitchTabRl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
//        customSwitchTabRl = (RelativeLayout) getLayoutInflater().inflate(R.layout.switch_tab_rl,null);
//        mToolbarX = getToolbarX();
//        mToolbarX.setDisplayHomeAsUpEnabled(false).setCustomeView(customSwitchTabRl);
//        mToolbarX.hide();
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        HomeFragment fragment = HomeFragment.newInstance();
//        if(fragment)
//        fragmentManager.beginTransaction().add(R.id.home_fragment,).commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }


//    @Override
//    public int getLayoutId() {
//        return R.layout.activity_main2;
//    }
}
