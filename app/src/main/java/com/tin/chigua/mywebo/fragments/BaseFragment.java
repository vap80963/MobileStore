package com.tin.chigua.mywebo.fragments;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.tin.chigua.mywebo.R;

/**
 * Created by hasee on 5/5/2017.
 */

public class BaseFragment extends Fragment {

    @Override
    public void startActivity(Intent intent) {
        getActivity().startActivity(intent);
        getActivity().overridePendingTransition(R.anim.anim_in_right_left,R.anim.anim_out_right_left);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        getActivity().startActivityForResult(intent,requestCode);
        getActivity().overridePendingTransition(R.anim.anim_in_right_left,R.anim.anim_out_right_left);
    }
}
