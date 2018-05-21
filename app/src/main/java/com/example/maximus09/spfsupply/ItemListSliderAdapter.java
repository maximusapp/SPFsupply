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
import com.example.maximus09.spfsupply.data.model.ResponseSlidersData;

import java.util.List;


public abstract class ItemListSliderAdapter extends RecyclerView.Adapter<ItemListSliderAdapter.ViewHolder> {

    private List<ResponseSlidersData.SlidersData> items;
    private Context context;

    public ItemListSliderAdapter(List<ResponseSlidersData.SlidersData> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemListSliderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.custom_slider_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemListSliderAdapter.ViewHolder holder, final int position) {
        Glide.with(context).load(items.get(position).getLogo()).into(holder.ivLogo);
        holder.tvCompanyName.setText(items.get(position).getCompany_name());

        if (items.get(position).is_slider.equals("0")) {
            holder.imageView.setImageResource(R.drawable.uncheckes);
        } else {
            holder.imageView.setImageResource(R.drawable.checkes);
        }




        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (items.get(position).is_slider.equals("1")) {
                    items.get(position).setIs_slider("0");
                    ClickSlider(items.get(position));
                    holder.imageView.setImageResource(R.drawable.uncheckes);
                } else {
                    holder.imageView.setImageResource(R.drawable.checkes);
                    items.get(position).setIs_slider("1");
                    ClickSlider(items.get(position));
                }
                notifyDataSetChanged();
            }
        });
    }


    public List<ResponseSlidersData.SlidersData> getSlidersData(){
        return items;
    }


    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivLogo;
        TextView tvCompanyName;
        public ImageView imageView;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);

            ivLogo = (ImageView)itemView.findViewById(R.id.image_custom_slider);
            tvCompanyName = (TextView)itemView.findViewById(R.id.company_name_slider);
            imageView = (ImageView)itemView.findViewById(R.id.checkbox_custom_item_slider);
            cardView =(CardView)itemView.findViewById(R.id.cvSleder);

        }
    }

    public void updateSliderList (List<ResponseSlidersData.SlidersData> list){
        this.items = list;
        notifyDataSetChanged();
    }

    public abstract void ClickSlider(ResponseSlidersData.SlidersData sliderActiveData);

}
