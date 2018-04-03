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



public class ItemListOrderHomeAdapter extends ArrayAdapter<ItemOrdersHome> {

    private Context mContextOrderHome;
    int mResourceOrderHome;

    public ItemListOrderHomeAdapter(@NonNull Context context, int resource, @NonNull ArrayList<ItemOrdersHome> objects) {
        super(context, resource, objects);
        this.mContextOrderHome = context;
        this.mResourceOrderHome = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String itemOrderHome = getItem(position).getOrderHome();
        String itemDateHome = getItem(position).getDateHome();
        String itemPriceHome = getItem(position).getPriceHome();

        ItemOrdersHome itemOrdersHome = new ItemOrdersHome(itemOrderHome, itemDateHome, itemPriceHome);

        LayoutInflater inflaterOrderHome = LayoutInflater.from(mContextOrderHome);
        convertView = inflaterOrderHome.inflate(mResourceOrderHome, parent, false);

        TextView itemsOrderNumberHome = (TextView)convertView.findViewById(R.id.orderNumber_list_orders_home);
        TextView itemsPriceHome = (TextView)convertView.findViewById(R.id.price_order_home);
        TextView itemsDateHome = (TextView)convertView.findViewById(R.id.date_orders_home);

        itemsOrderNumberHome.setText(itemOrderHome);
        itemsPriceHome.setText(itemPriceHome);
        itemsDateHome.setText(itemDateHome);


        return convertView;
    }
}
