package com.example.maximus09.spfsupply;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.maximus09.spfsupply.data.model.ResponseAllOrders;

import java.util.List;


public abstract class ItemListOrdersAdapter extends RecyclerView.Adapter<ItemListOrdersAdapter.ViewHolder> {

    private List<ResponseAllOrders.OrdersData> items;
    private Context context;

    public ItemListOrdersAdapter(List<ResponseAllOrders.OrdersData> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemListOrdersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_orders_item_list, parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemListOrdersAdapter.ViewHolder holder, final int position) {

        holder.company_name_order.setText(items.get(position).getManufacturers_company_name());
        holder.order_number.setText(items.get(position).getOrder_name());
        holder.price_order.setText(items.get(position).getTotal_count());
        holder.date_order.setText(items.get(position).getOrder_date());


        if (items.get(position).getIs_view().equals("0")) {
            holder.indicator.setVisibility(View.VISIBLE);
        }else {
            holder.indicator.setVisibility(View.INVISIBLE);

        }


        Glide.with(context).load(items.get(position).getManufacturers_logo()).into(holder.logo);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClickOrderNumber(items.get(position));
            }
        });




    }

    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView indicator;
        public ImageView logo;
        public TextView company_name_order;
        public TextView order_number;
        public TextView price_order;
        public TextView date_order;
        public CardView cardView;


        public ViewHolder(View itemView) {
            super(itemView);
            indicator = (ImageView)itemView.findViewById(R.id.indicator_orders);
            logo = (ImageView)itemView.findViewById(R.id.image_orders);
            company_name_order = (TextView)itemView.findViewById(R.id.companyName_list_orders);
            order_number = (TextView)itemView.findViewById(R.id.order_number);
            price_order = (TextView)itemView.findViewById(R.id.price_order);
            date_order = (TextView)itemView.findViewById(R.id.date_orders);
            cardView = (CardView)itemView.findViewById(R.id.cardView_order_admin);

        }
    }

    public void updateListOrders(List<ResponseAllOrders.OrdersData> list){
        this.items = list;
        notifyDataSetChanged();
    }

    public abstract void ClickOrderNumber(ResponseAllOrders.OrdersData ordersData);

}
