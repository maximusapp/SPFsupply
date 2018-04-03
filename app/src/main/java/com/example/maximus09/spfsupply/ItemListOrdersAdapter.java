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


public class ItemListOrdersAdapter extends ArrayAdapter<ItemsOrders> {

    private Context mContextOrder;
    int mResoursesOrder;

    public ItemListOrdersAdapter(Context context, int resource, ArrayList<ItemsOrders> objects) {
        super(context, resource, objects);
        this.mContextOrder = context;
        this.mResoursesOrder = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String itemCompanyName = getItem(position).getCompanyName();
        String itemOrderNumber = getItem(position).getOrdersNumber();
        String itemOrdersPrice = getItem(position).getOrdersPrice();
        String itemOrdersDate = getItem(position).getOrdersDate();

        ItemsOrders itemsOrders = new ItemsOrders(itemCompanyName, itemOrderNumber, itemOrdersPrice, itemOrdersDate);

        LayoutInflater inflaterOrder = LayoutInflater.from(mContextOrder);
        convertView = inflaterOrder.inflate(mResoursesOrder, parent, false);

        TextView itemsCompanyName = (TextView)convertView.findViewById(R.id.companyName_list_orders);
        TextView itemsCompanyOrders = (TextView)convertView.findViewById(R.id.order_number);
        TextView itemsPrice = (TextView)convertView.findViewById(R.id.price_order);
        TextView itemsDateOrders = (TextView)convertView.findViewById(R.id.date_orders);

        itemsCompanyName.setText(itemCompanyName);
        itemsCompanyOrders.setText(itemOrderNumber);
        itemsPrice.setText(itemOrdersPrice);
        itemsDateOrders.setText(itemOrdersDate);

        return convertView;
    }
}
