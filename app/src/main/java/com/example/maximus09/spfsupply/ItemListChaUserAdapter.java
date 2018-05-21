package com.example.maximus09.spfsupply;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.maximus09.spfsupply.data.model.Message;

import java.util.List;

/**
 * Created by maximus09 on 10.05.2018.
 */

public class ItemListChaUserAdapter extends RecyclerView.Adapter<ItemListChaUserAdapter.ViewHolder>{

    private List<Message> items;
    private Context context;

    public ItemListChaUserAdapter(List<Message> items, Context context) {
        this.items = items;
        this.context = context;
    }

    private final int TYPE_HEADER = 0;
    private final int TYPE_ITEM = 1;

    @Override
    public int getItemViewType(int position) {
        Message item = items.get(position);
        return item.getTo_type().equals("admin") ? TYPE_HEADER : TYPE_ITEM;
    }

    @NonNull
    @Override
    public ItemListChaUserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        switch (viewType) {
            case TYPE_HEADER:{
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_chat_item_to, parent, false);
                return new ItemListChaUserAdapter.ViewHolderTo(v);
            }

            case TYPE_ITEM:
            default:{
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_chat_item_from, parent, false);
                return new ItemListChaUserAdapter.ViewHolderFrom(v);
            }

        }
    }

    @Override
    public void onBindViewHolder(@NonNull ItemListChaUserAdapter.ViewHolder holder, int position) {
        final Message item = items.get(position);

        // holder.setText(item.getTitle());
        switch (getItemViewType(position)){
            case TYPE_HEADER:
                itemConfigUser(item, (ItemListChaUserAdapter.ViewHolderTo) holder);
                break;
            case TYPE_ITEM:
                itemConfigAdmin(item, (ItemListChaUserAdapter.ViewHolderFrom) holder);
                break;
        }
    }


    private void itemConfigAdmin(Message messages, ItemListChaUserAdapter.ViewHolderFrom viewHolderFrom){
        viewHolderFrom.tvMessageFrom.setText(messages.getMessage());
        messages.setMessage(messages.getMessage());
    }

    private void itemConfigUser(Message messages, ItemListChaUserAdapter.ViewHolderTo viewHolderTo) {

        viewHolderTo.tvMessage.setText(messages.getMessage());
        messages.setMessage(messages.getMessage());

    }


    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        return items.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(View itemView) {
            super(itemView);

        }
    }

    // Handle second ViewHolder.
    public class ViewHolderFrom extends ItemListChaUserAdapter.ViewHolder {

        public ImageView ivLogo;
        public TextView tvMessageFrom;

        public ViewHolderFrom(View itemView) {
            super(itemView);

            ivLogo = (ImageView)itemView.findViewById(R.id.ivLogoFrom);
            tvMessageFrom = (TextView)itemView.findViewById(R.id.tvMessageFrom);

        }
    }

    // Handle Send To ViewHolder.
    public class ViewHolderTo extends ItemListChaUserAdapter.ViewHolder {
        public TextView tvMessage;


        public ViewHolderTo(View itemView) {
            super(itemView);
            tvMessage = (TextView)itemView.findViewById(R.id.tvMessageTo);


        }
    }


    public void updateListMessagesUser(List<Message> list){
        this.items = list;
        notifyDataSetChanged();
    }


}

