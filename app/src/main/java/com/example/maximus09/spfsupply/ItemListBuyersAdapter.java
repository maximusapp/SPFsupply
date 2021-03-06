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
import com.example.maximus09.spfsupply.data.model.ResponseAllBuyers;

import java.util.List;


public abstract class ItemListBuyersAdapter extends RecyclerView.Adapter<ItemListBuyersAdapter.ViewHolder> {

    private List<ResponseAllBuyers.AccountData> items;
    private Context context;

    public ItemListBuyersAdapter(List<ResponseAllBuyers.AccountData> items, Context context) {
        this.items = items;
        this.context = context;
    }


    @NonNull
    @Override
    public ItemListBuyersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_buyers_item_list, parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemListBuyersAdapter.ViewHolder holder, final int position) {
        holder.company_name.setText(items.get(position).getCompany_name());
        if (items.get(position).getCompany_logo() == null || items.get(position).getCompany_logo().length() == 0) {
            holder.image_logo.setImageResource(R.drawable.shape_buyers);
        } else {
            Glide.with(context).load(items.get(position).getCompany_logo()).into(holder.image_logo);
        }


        if (items.get(position).getIs_new().equals("1")) {
            holder.image_indicator.setVisibility(View.VISIBLE);
        } else {
            holder.image_indicator.setVisibility(View.INVISIBLE);
        }

        //holder.image_indicator.setVisibility(View.VISIBLE);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClickBuyers(items.get(position));
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

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image_indicator;
        public ImageView image_logo;
        public TextView company_name;
        public CardView cardView;


        public ViewHolder(View itemView) {
            super(itemView);
            image_indicator = (ImageView)itemView.findViewById(R.id.indicator_buyers);
            image_logo = (ImageView) itemView.findViewById(R.id.logo_buyers);
            company_name = (TextView) itemView.findViewById(R.id.company_name_buyers);
            cardView = (CardView) itemView.findViewById(R.id.cardView_buyers);
        }
    }

    public void updateBuyersList(List<ResponseAllBuyers.AccountData> list) {
        this.items = list;
        notifyDataSetChanged();
    }

    public abstract void ClickBuyers(ResponseAllBuyers.AccountData accountData);

}
