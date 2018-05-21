package com.example.maximus09.spfsupply;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public abstract class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ViewHolder> {

    private ArrayList<ItemsDrawer> items;

    private Context context;

    public ItemListAdapter(ArrayList<ItemsDrawer> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.custom_drawer_menu_admin,parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemListAdapter.ViewHolder holder, final int position) {


        holder.tvMenuItem.setText(items.get(position).getMenuItem());
        holder.tvNewCount.setText(items.get(position).getCount());

        holder.tvNewCount.setVisibility(items.get(position).getCount().length() == 0 || items.get(position).getCount().equals("0")? View.GONE:View.VISIBLE);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Click(holder.getAdapterPosition() );
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
        public TextView tvMenuItem;
        public TextView tvNewCount;
        public CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);

            tvMenuItem = (TextView)itemView.findViewById(R.id.drawer_menu_text);
            tvNewCount = (TextView)itemView.findViewById(R.id.drawer_menu_count);
            cardView = (CardView)itemView.findViewById(R.id.cardView_drawer_menu);

        }

}

    public void updateOrderProductUser(ArrayList<ItemsDrawer> list){
        this.items = list;
        notifyDataSetChanged();
    }

    public abstract void Click(int position);


}


