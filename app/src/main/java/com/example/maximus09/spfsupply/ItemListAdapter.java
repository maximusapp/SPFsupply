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


public class ItemListAdapter extends ArrayAdapter<ItemsDrawer> {

    private Context mContext;
    int mResourses;

    public ItemListAdapter(Context context, int resource, ArrayList<ItemsDrawer> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResourses = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String itemName = getItem(position).getMenuItem();
        String countMess = getItem(position).getCount();

        ItemsDrawer itemsDrawer = new ItemsDrawer(itemName, countMess);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResourses, parent, false);

        TextView itemText = (TextView)convertView.findViewById(R.id.menu_item_text);
        TextView itemCount = (TextView)convertView.findViewById(R.id.menu_item_numb);

        itemText.setText(itemName);
        itemCount.setText(countMess);

        return convertView;

    }
}
