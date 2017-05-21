package com.tin.chigua.mywebo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by hasee on 5/7/2017.
 */

public class HomePagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragmentList;

    public HomePagerAdapter(FragmentManager fm,List<Fragment> fragmentList) {
        super(fm);
        mFragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}
