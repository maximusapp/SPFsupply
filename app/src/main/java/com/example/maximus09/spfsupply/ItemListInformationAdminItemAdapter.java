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
import com.example.maximus09.spfsupply.struct.ProductListInfoManufactureAdmin;

import java.util.List;

public class ItemListInformationAdminItemAdapter extends RecyclerView.Adapter<ItemListInformationAdminItemAdapter.ViewHolder> {

    private List<ProductListInfoManufactureAdmin> items;
    private Context context;

    public ItemListInformationAdminItemAdapter(List<ProductListInfoManufactureAdmin> productListInfoManufactureAdmins, Context context) {
        this.items = productListInfoManufactureAdmins;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemListInformationAdminItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.custom_info_product_admin_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemListInformationAdminItemAdapter.ViewHolder holder, int position) {
        holder.textView_product_name.setText(items.get(position).getTitle());
        Glide.with(context).load(items.get(position).getLogo()).into(holder.imageView_product);
    }

    @Override
    public int getItemCount() {
        if (items == null){
            return 0;
        }
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView_product;
        public TextView textView_product_name;
        public ImageView imageView_delete_product;

        public ViewHolder(View itemView) {
            super(itemView);

            imageView_product = (ImageView)itemView.findViewById(R.id.image_product_info_admin);
            textView_product_name = (TextView)itemView.findViewById(R.id.prod_title_info_admin);
            imageView_delete_product = (ImageView)itemView.findViewById(R.id.image_delete_info_admin);

        }
    }

    public void updateListManufacturer(List<ProductListInfoManufactureAdmin> list){
        this.items = list;
        notifyDataSetChanged();
    }

}
