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
import com.example.maximus09.spfsupply.data.model.ResponseAllManufacturersUser;

import java.util.List;

/**
 * Created by maximus09 on 10.05.2018.
 */

public abstract class ItemListManufacturerAllUserAdapter extends RecyclerView.Adapter<ItemListManufacturerAllUserAdapter.ViewHolder>{

    private List<ResponseAllManufacturersUser.ManufactAll> items;
    private Context context;

    public ItemListManufacturerAllUserAdapter(List<ResponseAllManufacturersUser.ManufactAll> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemListManufacturerAllUserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.custom_list_all_home_user, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemListManufacturerAllUserAdapter.ViewHolder holder, final int position) {

        Glide.with(context).load(items.get(position).getLogo()).into(holder.ivLogo);
        holder.tvComp_name.setText(items.get(position).getCompany_name());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClickAllUserList(items.get(position));
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
        public CardView cardView;
        public ImageView ivLogo;
        public TextView tvComp_name;

        public ViewHolder(View itemView) {
            super(itemView);

            cardView = (CardView)itemView.findViewById(R.id.cvListUserItemAll);
            ivLogo = (ImageView)itemView.findViewById(R.id.image_custom_item_user_all);
            tvComp_name = (TextView)itemView.findViewById(R.id.company_name_list_all);

        }
    }

    public void updateAllUserList (List<ResponseAllManufacturersUser.ManufactAll> list){
        this.items = list;
        notifyDataSetChanged();
    }

    public abstract void ClickAllUserList(ResponseAllManufacturersUser.ManufactAll manufacturersAllData);

}
