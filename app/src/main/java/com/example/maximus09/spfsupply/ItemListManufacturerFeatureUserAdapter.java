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
 * Created by maximus09 on 11.05.2018.
 */

public abstract class ItemListManufacturerFeatureUserAdapter extends RecyclerView.Adapter<ItemListManufacturerFeatureUserAdapter.ViewHolder> {

    private List<ResponseAllManufacturersUser.ManufactFeature> items;
    private Context context;

    public ItemListManufacturerFeatureUserAdapter(List<ResponseAllManufacturersUser.ManufactFeature> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemListManufacturerFeatureUserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.custom_list_features_home_user, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemListManufacturerFeatureUserAdapter.ViewHolder holder, final int position) {

        Glide.with(context).load(items.get(position).getLogo()).into(holder.ivLogo);
        holder.tvComp_name.setText(items.get(position).getCompany_name());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClickFeatureUserList(items.get(position));
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

            cardView = (CardView)itemView.findViewById(R.id.cvListUserItemFeature);
            ivLogo = (ImageView)itemView.findViewById(R.id.image_custom_item_user_feature);
            tvComp_name = (TextView)itemView.findViewById(R.id.company_name_list_feature);

        }
    }

    public void updateFeaturesUserList (List<ResponseAllManufacturersUser.ManufactFeature> list){
        this.items = list;
        notifyDataSetChanged();
    }

    public abstract void ClickFeatureUserList(ResponseAllManufacturersUser.ManufactFeature manufacturersFeaturesData);

}
