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


public class ItemListBuyersAdapter extends ArrayAdapter<ItemsBuyers> {

    private Context mContext;
    int mResourses;

    public ItemListBuyersAdapter(Context context, int resource, ArrayList<ItemsBuyers> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResourses = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String itemsName = getItem(position).getCompanyName();

        ItemsBuyers itemsBuyers = new ItemsBuyers(itemsName);

        LayoutInflater inflaters = LayoutInflater.from(mContext);
        convertView = inflaters.inflate(mResourses, parent, false);

        TextView itemText = (TextView)convertView.findViewById(R.id.text_list_buyers);

        itemText.setText(itemsName);

        return convertView;
    }
}
