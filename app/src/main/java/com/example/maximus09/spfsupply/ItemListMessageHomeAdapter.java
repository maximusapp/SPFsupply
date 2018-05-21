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
import com.example.maximus09.spfsupply.data.model.ResponseAllMessageUser;

import java.util.List;


public abstract class ItemListMessageHomeAdapter extends RecyclerView.Adapter<ItemListMessageHomeAdapter.ViewHolder> {

    private List<ResponseAllMessageUser.ChatsDataUser> items;
    private Context context;

    public ItemListMessageHomeAdapter(List<ResponseAllMessageUser.ChatsDataUser> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemListMessageHomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_message_home_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemListMessageHomeAdapter.ViewHolder holder, final int position) {
        holder.order_number.setText(items.get(position).getChat_name());
        holder.order_date.setText(items.get(position).getOrder_date());

        Glide.with(context).load(items.get(position).getManufacturers_logo()).into(holder.logo);

        if (items.get(position).getHave_new().equals("1")){
            holder.indicator.setVisibility(View.VISIBLE);
        } else {
            holder.indicator.setVisibility(View.INVISIBLE);
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClickMessagesUser(items.get(position));
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
        public ImageView indicator;
        public ImageView logo;
        public TextView order_number;
        public TextView order_date;
        public CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            indicator = (ImageView)itemView.findViewById(R.id.indicator_messages_user);
            logo = (ImageView)itemView.findViewById(R.id.image_messages_user);
            order_number = (TextView)itemView.findViewById(R.id.order_message_title_user);
            order_date = (TextView)itemView.findViewById(R.id.date_message_home_user);
            cardView = (CardView)itemView.findViewById(R.id.cvMessageUserItem);
        }
    }

    public void updateListMessagesUser(List<ResponseAllMessageUser.ChatsDataUser> list){
        this.items = list;
        notifyDataSetChanged();
    }

    public abstract void ClickMessagesUser(ResponseAllMessageUser.ChatsDataUser chatsData);

}
