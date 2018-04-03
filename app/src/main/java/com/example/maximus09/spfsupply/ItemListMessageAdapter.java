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


public class ItemListMessageAdapter extends ArrayAdapter<ItemMessages> {

    private Context mContextMessages;
    int mResourceMessage;

    public ItemListMessageAdapter(Context context, int resource, ArrayList<ItemMessages> objects) {
        super(context, resource, objects);
        this.mContextMessages = context;
        this.mResourceMessage = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String itemMessageCompany = getItem(position).getCompanyNameMessages();
        String itemMessageOrder = getItem(position).getCompanyOrderNumMess();

        ItemMessages itemMessages = new ItemMessages(itemMessageCompany, itemMessageOrder);

        LayoutInflater inflateMessage = LayoutInflater.from(mContextMessages);
        convertView = inflateMessage.inflate(mResourceMessage, parent, false);

        TextView itemsCompanyNameMessage = (TextView)convertView.findViewById(R.id.companyName_list_messages);
        TextView itemNumberMessMessage = (TextView)convertView.findViewById(R.id.order_number_messages);

        itemsCompanyNameMessage.setText(itemMessageCompany);
        itemNumberMessMessage.setText(itemMessageOrder);

        return convertView;
    }
}
