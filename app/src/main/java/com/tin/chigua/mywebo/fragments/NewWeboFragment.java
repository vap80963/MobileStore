package com.tin.chigua.mywebo.fragments;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.tin.chigua.mywebo.R;

/**
 * Created by hasee on 6/11/2017.
 */

public class NewWeboFragment extends DialogFragment {

    private AlertDialog.Builder mBuilder;
    private LayoutInflater mInflater;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mBuilder = new AlertDialog.Builder(getActivity(),R.style.AppTheme_FullScreen);
        mInflater = getActivity().getLayoutInflater();
        View view = mInflater.inflate(R.layout.fragment_new_webo,null);
//        mBuilder.setView(view).setPositiveButton("确定", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                LUtils.toastShort(getActivity(),"发表成功");
//            }
//        }).setNegativeButton("取消",null);
        mBuilder.setView(view);
        return mBuilder.create();
    }


//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_new_webo,container,false);
//        return view;
//    }
//
//    public void showEditDialog(View view){
//
//        NewWeboFragment newWeboFragment = (NewWeboFragment) newInsatance();
//        newWeboFragment.show(getFragmentManager(),"new_webo_dialog");
//    }

    public static NewWeboFragment newInsatance(){
        NewWeboFragment weboFragment = null;
        if (null == weboFragment){
            weboFragment = new NewWeboFragment();
        }
        return weboFragment;
    }

}
