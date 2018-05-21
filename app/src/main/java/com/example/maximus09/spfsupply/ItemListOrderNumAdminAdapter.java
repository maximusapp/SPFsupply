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

public class ItemListOrderNumAdminAdapter extends RecyclerView.Adapter<ItemListOrderNumAdminAdapter.ViewHolder> {

    private List<OrdersData.Products> items;
    private Context context;

    public ItemListOrderNumAdminAdapter(List<OrdersData.Products> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemListOrderNumAdminAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.order_number_item_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemListOrderNumAdminAdapter.ViewHolder holder, int position) {

        if(items.get(position).getProduct_logo() == null || items.get(position).getProduct_logo().length() == 0) {
            holder.ivLogo.setImageResource(R.drawable.shape_buyers);
        } else {
            Glide.with(context).load(items.get(position).getProduct_logo()).into(holder.ivLogo);
        }


        holder.tvProductTitle.setText(items.get(position).getProduct_title());
        holder.tvProductPrice.setText(items.get(position).getPrice());


    }

    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        return items.size();
    }

    public class ViewHolder  extends RecyclerView.ViewHolder {
        public ImageView ivLogo;
        public TextView tvProductTitle;
        public TextView tvProductPrice;

        public ViewHolder(View itemView) {
            super(itemView);

            ivLogo = (ImageView)itemView.findViewById(R.id.imageView_order_numbet_admin_list);
            tvProductTitle = (TextView) itemView.findViewById(R.id.product_title_order_number_admin_list);
            tvProductPrice = (TextView)itemView.findViewById(R.id.product_price_order_number_admin_list);

        }
    }

    public void updateProductList(List<OrdersData.Products> list){
        this.items = list;
        notifyDataSetChanged();
    }

}
