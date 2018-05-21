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
import com.example.maximus09.spfsupply.data.model.ResponseAllOrdersUser;
import com.example.maximus09.spfsupply.data.model.ResponseOrdersById;

import java.util.List;


public abstract class ItemListOrderHomeAdapter extends RecyclerView.Adapter<ItemListOrderHomeAdapter.ViewHolder> {

    private List<ResponseAllOrdersUser.OrderDataUser> items;
    private Context context;

    public ItemListOrderHomeAdapter(List<ResponseAllOrdersUser.OrderDataUser> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemListOrderHomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_order_home_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemListOrderHomeAdapter.ViewHolder holder, final int position) {
        holder.order_price.setText(items.get(position).getTotal_count());
        holder.order_date.setText(items.get(position).getOrder_date());
        holder.order_number.setText(items.get(position).getOrder_name());

        Glide.with(context).load(items.get(position).getManufacturers_logo()).into(holder.logo);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClickOrderNumHome(items.get(position));
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView logo;
        public TextView order_number;
        public TextView order_date;
        public TextView order_price;
        public CardView cardView;


        public ViewHolder(View itemView) {
            super(itemView);
            logo = (ImageView)itemView.findViewById(R.id.image_orders_user);
            order_number = (TextView)itemView.findViewById(R.id.order_number_user);
            order_date = (TextView)itemView.findViewById(R.id.date_orders_user);
            order_price = (TextView)itemView.findViewById(R.id.price_order_user);
            cardView = (CardView)itemView.findViewById(R.id.cvOrderHomeItem);
        }
    }

    public void updateListOrdersUser(List<ResponseAllOrdersUser.OrderDataUser> list){
        this.items = list;
        notifyDataSetChanged();
    }

    public abstract void ClickOrderNumHome(ResponseAllOrdersUser.OrderDataUser ordersData);

}
