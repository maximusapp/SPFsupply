package com.example.maximus09.spfsupply;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by maximus09 on 23.03.2018.
 */

public class ItemListManufacturersAdapter extends ArrayAdapter<ItemsManufacturers> {

    private Context mContextManufacturers;
    int mResuorsesManufacturers;

    public ItemListManufacturersAdapter(Context context, int resource, ArrayList<ItemsManufacturers> objects) {
        super(context, resource, objects);
        this.mContextManufacturers = context;
        this.mResuorsesManufacturers = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String itemManufacturers = getItem(position).getManufacturersName();

        LayoutInflater inflaterManufacturers = LayoutInflater.from(mContextManufacturers);
        convertView = inflaterManufacturers.inflate(mResuorsesManufacturers, parent, false);

        TextView itemsManufacturers = (TextView)convertView.findViewById(R.id.text_list_manufacturers);

        itemsManufacturers.setText(itemManufacturers);

        return convertView;
    }
}
