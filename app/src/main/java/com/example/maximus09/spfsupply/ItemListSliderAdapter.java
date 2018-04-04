package com.example.maximus09.spfsupply;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;


public class ItemListSliderAdapter extends ArrayAdapter<ItemSlider> {

    private Context mContext;
    int mResourses;

    public ItemListSliderAdapter(@NonNull Context context, int resource, @NonNull ArrayList<ItemSlider> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResourses = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        int itemImage = getItem(position).getImageView();

        CheckBox itemCheck = (CheckBox) getItem(position).getCheckBoxCustom();

        LayoutInflater inflaters = LayoutInflater.from(mContext);
        convertView = inflaters.inflate(mResourses, parent, false);

        ItemSlider itemSlider = getItem(position);

        ImageView imageView = (ImageView)convertView.findViewById(R.id.image_custom_slider);
        imageView.setImageResource(itemImage);

        final CheckBox checkBoxCustom = (CheckBox)convertView.findViewById(R.id.checkbox_custom_item_slider);

        final CheckBox checkBox = (CheckBox)convertView.findViewById(R.id.bottom_checkbox_slider);
       // if (checkBox.isChecked()) {}



        return convertView;
    }

}
