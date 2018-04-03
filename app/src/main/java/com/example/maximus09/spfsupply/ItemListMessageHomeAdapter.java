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


public class ItemListMessageHomeAdapter extends ArrayAdapter<ItemMessages> {

    private Context mContextMessages;
    int mResourceMessage;

    public ItemListMessageHomeAdapter(Context context, int resource, ArrayList<ItemMessages> objects) {
        super(context, resource, objects);
        this.mContextMessages = context;
        this.mResourceMessage = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String itemOrderNumber = getItem(position).getCompanyNameMessages();
        String itemDate = getItem(position).getCompanyOrderNumMess();

        ItemMessages itemMessages = new ItemMessages(itemOrderNumber, itemDate);

        LayoutInflater inflateMessage = LayoutInflater.from(mContextMessages);
        convertView = inflateMessage.inflate(mResourceMessage, parent, false);

        TextView itemOrder = (TextView)convertView.findViewById(R.id.order_message_home_item);
        TextView itemsDate = (TextView)convertView.findViewById(R.id.date_message_home_item);

        itemOrder.setText(itemOrderNumber);
        itemsDate.setText(itemDate);

        return convertView;
    }
}
