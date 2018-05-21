package com.example.maximus09.spfsupply;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.maximus09.spfsupply.struct.OrdersData;

import java.util.List;

public class ItemListOrderNumUserAdapter extends RecyclerView.Adapter<ItemListOrderNumUserAdapter.ViewHolder> {

    private List<OrdersData.Products> items;
    private Context context;

    public ItemListOrderNumUserAdapter(List<OrdersData.Products> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemListOrderNumUserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.custom_order_number_home_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemListOrderNumUserAdapter.ViewHolder holder, int position) {

        if (items.get(position).getProduct_logo() != null) {
            Glide.with(context).load(items.get(position).getProduct_logo()).into(holder.ivLogo);
        } else {
            holder.ivLogo.setImageResource(R.drawable.shape_buyers);
        }

        holder.tvTitle.setText(items.get(position).getProduct_title());
        holder.tvPrice.setText(items.get(position).getPrice());

    }

    @Override
    public int getItemCount() {
        if (items == null){
            return 0;
        }
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivLogo;
        TextView tvTitle;
        TextView tvPrice;

        public ViewHolder(View itemView) {
            super(itemView);

            ivLogo = (ImageView)itemView.findViewById(R.id.imageView_order_number_user_list);
            tvTitle = (TextView)itemView.findViewById(R.id.product_title_order_number_user_list);
            tvPrice = (TextView)itemView.findViewById(R.id.product_price_order_number_user_list);

        }
    }

    public void updateOrderProductUser(List<OrdersData.Products> list){
        this.items = list;
        notifyDataSetChanged();
    }

}