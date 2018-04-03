package com.example.maximus09.spfsupply;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;


public class TestFragment extends Fragment {

    public static TestFragment newInstance(String image_link) {
        Bundle args = new Bundle();
        args.putString("url", image_link);

        TestFragment fragment = new TestFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.welcome_photo_items, container, false);

        String image_link = getArguments().getString("url");

        ImageView imageView = (ImageView)view.findViewById(R.id.image_item_welcome);

        Glide.with(this).load(image_link).into(imageView);

        return view;
    }
}
