package com.example.maximus09.spfsupply;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.maximus09.spfsupply.data.model.ResponseAllManufacturers;

import java.util.List;


public abstract class ItemListManufacturersAdapter extends RecyclerView.Adapter<ItemListManufacturersAdapter.ViewHolder> {

    private List<ResponseAllManufacturers.ManufacturersData> items;
    private Context context;

    public ItemListManufacturersAdapter(List<ResponseAllManufacturers.ManufacturersData> items, Context context) {
        this.items = items;
        this.context = context;
    }


    @NonNull
    @Override
    public ItemListManufacturersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_manufacturers_item_list, parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemListManufacturersAdapter.ViewHolder holder, final int position) {
        holder.manufacturer_name.setText(items.get(position).getCompany_name());
        Glide.with(context).load(items.get(position).getLogo()).into(holder.manufacturer_logo);

        if (items.get(position).getLogo() == null) {
            Glide.with(context).load(R.drawable.add_order_number).into(holder.manufacturer_logo);
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Click(items.get(position));
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
        public ImageView manufacturer_logo;
        public TextView manufacturer_name;
        public CardView cardView;


        public ViewHolder(View itemView) {
            super(itemView);
            manufacturer_logo = (ImageView)itemView.findViewById(R.id.logo_manufacturer);
            manufacturer_name = (TextView)itemView.findViewById(R.id.company_name_manufacturer);
            cardView = (CardView) itemView.findViewById(R.id.card_manuf_admin);
        }
    }

    public void updateListManufacturer(List<ResponseAllManufacturers.ManufacturersData> list) {
        this.items = list;
        notifyDataSetChanged();
    }

    public abstract void Click(ResponseAllManufacturers.ManufacturersData resp);


}
