package com.tin.chigua.mywebo.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tin.chigua.mywebo.R;

/**
 * Created by hasee on 5/5/2017.
 */

public class SquareFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_square,container,false);
        return v;
    }
}