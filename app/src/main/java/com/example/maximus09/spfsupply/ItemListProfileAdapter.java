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
import java.util.List;


public class ItemListProfileAdapter extends ArrayAdapter<ItemsProfile> {

    private Context mProfileContext;
    int mResourses;

    public ItemListProfileAdapter(Context context, int resource, @NonNull ArrayList<ItemsProfile> objects) {
        super(context, resource, objects);
        this.mProfileContext = context;
        this.mResourses = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String itemsNames = getItem(position).getManName();

        ItemsProfile itemsProfile = new ItemsProfile(itemsNames);

        LayoutInflater inflaters = LayoutInflater.from(mProfileContext);
        convertView = inflaters.inflate(mResourses, parent, false);

        TextView itemProfileText = (TextView)convertView.findViewById(R.id.companyName_list_profile);
        itemProfileText.setText(itemsNames);

        return convertView;
    }
}
