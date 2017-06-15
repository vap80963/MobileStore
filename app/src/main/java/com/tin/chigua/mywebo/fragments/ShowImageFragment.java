package com.tin.chigua.mywebo.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tin.chigua.mywebo.R;
import com.tin.chigua.mywebo.utils.LUtils;

/**
 * Created by hasee on 6/15/2017.
 */

public class ShowImageFragment extends DialogFragment {

    private static final String IMAGE_URL = "image_url";

    private AlertDialog.Builder mBuilder;
    private LayoutInflater mInflater;

    private ImageView mImageView;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mBuilder = new AlertDialog.Builder(getActivity(), R.style.TranslucentTheme);
        mInflater = getActivity().getLayoutInflater();
        View view = mInflater.inflate(R.layout.fragment_show_image,null);
        init(view);
        String uri = getArguments().getString(IMAGE_URL);
        Glide.with(getActivity())
                .load(uri)
                .centerCrop()
                .placeholder(R.color.gray)
                .error(R.drawable.add)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(mImageView);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mBuilder.setView(view).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LUtils.toastShort(getActivity(),"发表成功");
            }
        }).setNegativeButton("取消",null);
        return mBuilder.create();
    }

    private void init(View view) {
        mImageView = (ImageView) view.findViewById(R.id.activity_show_image_img);
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

    public static ShowImageFragment newInsatance(String url){
        ShowImageFragment showImageFragment = null;
        if (null == showImageFragment){
            showImageFragment = new ShowImageFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putString(IMAGE_URL,url);
        showImageFragment.setArguments(bundle);
        return showImageFragment;
    }

}
