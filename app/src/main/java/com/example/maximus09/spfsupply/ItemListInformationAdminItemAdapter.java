package com.example.maximus09.spfsupply;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.maximus09.spfsupply.data.model.ResponseAllOrdersUser;
import com.example.maximus09.spfsupply.struct.ProductListInfoManufactureAdmin;

import java.util.List;

public abstract class ItemListInformationAdminItemAdapter extends RecyclerView.Adapter<ItemListInformationAdminItemAdapter.ViewHolder> {

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
    public void onBindViewHolder(@NonNull ItemListInformationAdminItemAdapter.ViewHolder holder, final int position) {
        holder.textView_product_name.setText(items.get(position).getTitle());

        Glide.with(context).load(items.get(position).getLogo()).into(holder.imageView_product);
        // circle image. set after getLogo().apply(RequestOptions.circleCropTransform()
        //apply(RequestOptions.circleCropTransform()

        holder.imageView_delete_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClickDeleteProduct(items.get(position));
                //Toast.makeText(context, "Pressed Delete", Toast.LENGTH_LONG).show();
            }
        });

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

    public abstract void ClickDeleteProduct(ProductListInfoManufactureAdmin productListInfo);

}
