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
import com.example.maximus09.spfsupply.data.model.ResponseManufDataUser;

import java.util.List;

/**
 * Created by maximus09 on 11.05.2018.
 */

public abstract class ItemListProductInfoHome extends RecyclerView.Adapter<ItemListProductInfoHome.ViewHolder>{

    private List<ResponseManufDataUser.ProductsUser> items;
    private Context context;

    public ItemListProductInfoHome(List<ResponseManufDataUser.ProductsUser> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemListProductInfoHome.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.custom_information_home_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemListProductInfoHome.ViewHolder holder, final int position) {

        Glide.with(context).load(items.get(position).getLogo()).into(holder.ivLogo);
        holder.tvTitle.setText(items.get(position).getTitle());
        holder.tvPrice.setText(items.get(position).getPrice());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClickAllProductList(items.get(position));
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
        CardView cardView;
        ImageView ivLogo;
        TextView tvTitle;
        TextView tvPrice;

        public ViewHolder(View itemView) {
            super(itemView);

            cardView = (CardView)itemView.findViewById(R.id.cvInfoHome);
            ivLogo = (ImageView)itemView.findViewById(R.id.ivLogo);
            tvTitle = (TextView)itemView.findViewById(R.id.product_title_home);
            tvPrice = (TextView)itemView.findViewById(R.id.product_price_home);

        }
    }

    public void updateAllProductList (List<ResponseManufDataUser.ProductsUser> list){
        this.items = list;
        notifyDataSetChanged();
    }

    public abstract void ClickAllProductList(ResponseManufDataUser.ProductsUser productsUser);


}
