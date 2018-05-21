package com.example.maximus09.spfsupply;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.maximus09.spfsupply.data.model.ResponseBuyersInformation;

import java.util.List;


public class ItemListProfileAdapter extends RecyclerView.Adapter<ItemListProfileAdapter.ViewHolder> {

   private List<ResponseBuyersInformation.Permissions> items;
    public Context context;


    public ItemListProfileAdapter(List<ResponseBuyersInformation.Permissions> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemListProfileAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_profile_perm_item_list, parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemListProfileAdapter.ViewHolder holder, final int position) {

        Glide.with(context).load(items.get(position).getLogo()).into(holder.image_logo);
        holder.company_name.setText(items.get(position).getCompany_name());

        if (items.get(position).getChecked().equals("0")) {
            holder.imageView.setImageResource(R.drawable.uncheckes);
        } else {
            holder.imageView.setImageResource(R.drawable.checkes);
        }


        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (items.get(position).getChecked().equals("1")) {
                    items.get(position).setChecked("0");
                    holder.imageView.setImageResource(R.drawable.uncheckes);
                } else {
                    holder.imageView.setImageResource(R.drawable.checkes);
                    items.get(position).setChecked("1");
                }

                notifyDataSetChanged();
            }
        });

    }


    public List<ResponseBuyersInformation.Permissions> getPermissList(){
        return items;
    }


    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        return items.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image_logo;
        public TextView company_name;
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);

            image_logo = (ImageView) itemView.findViewById(R.id.imageView5);
            company_name = (TextView) itemView.findViewById(R.id.companyName_list_profile);
            imageView = (ImageView) itemView.findViewById(R.id.ivCheck_profile);

        }
    }

    public void updatePermissionsList(List<ResponseBuyersInformation.Permissions> list) {
        this.items = list;
        notifyDataSetChanged();
    }

}
