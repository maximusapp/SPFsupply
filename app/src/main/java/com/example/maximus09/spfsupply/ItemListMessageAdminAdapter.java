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
import com.example.maximus09.spfsupply.data.model.ResponseAllMessagesAdmin;

import java.util.List;


public class ItemListMessageAdminAdapter extends RecyclerView.Adapter<ItemListMessageAdminAdapter.ViewHolder> {

    private List<ResponseAllMessagesAdmin.ChatsData> items;
    private Context context;

    public ItemListMessageAdminAdapter(List<ResponseAllMessagesAdmin.ChatsData> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemListMessageAdminAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_message_item_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemListMessageAdminAdapter.ViewHolder holder, int position) {
        holder.company_name.setText(items.get(position).getManufacturers_company_name());
        holder.order_number.setText(items.get(position).getOrder_id());
        Glide.with(context).load(items.get(position).getManufacturers_logo()).into(holder.logo);

    }

    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView indicator;
        public ImageView logo;
        public TextView company_name;
        public TextView order_number;

        public ViewHolder(View itemView) {
            super(itemView);
            indicator = (ImageView)itemView.findViewById(R.id.indicator_messages_admin);
            logo = (ImageView)itemView.findViewById(R.id.image_messages_admin);
            company_name = (TextView)itemView.findViewById(R.id.companyName_list_messages_admin);
            order_number = (TextView)itemView.findViewById(R.id.order_number_messages_admin);
        }
    }

    public void updateListMessagesAdmin(List<ResponseAllMessagesAdmin.ChatsData> list){
        this.items = list;
        notifyDataSetChanged();
    }

}

