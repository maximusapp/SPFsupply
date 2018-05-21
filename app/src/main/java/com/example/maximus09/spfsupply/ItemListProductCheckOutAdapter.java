package com.example.maximus09.spfsupply;

import android.annotation.SuppressLint;
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
import com.example.maximus09.spfsupply.struct.BasketsData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maximus09 on 11.05.2018.
 */

public abstract class ItemListProductCheckOutAdapter extends RecyclerView.Adapter<ItemListProductCheckOutAdapter.ViewHolder> {


    private final int TYPE_HEADER = 0;
    private final int TYPE_ITEM = 1;

    private List<BasketsData> list;
    private List<ListOfChekOut> items;
    private Context context;

    public ItemListProductCheckOutAdapter(List<BasketsData> items, Context context) {
        this.items = new ArrayList<>();
        this.list = items;
        this.context = context;

        gen();
    }

    private void gen() {
        items.clear();

        if(list == null)
            return;

        for(int i = 0; i < list.size(); i++) {

            //header
            items.add(new ListOfChekOut(list.get(i).getManufacturers_id(), list.get(i).getManufacturers_title()));

            //items
            for(int j = 0; j < list.get(i).getManufacturers_products().size(); j++) {
                items.add(new ListOfChekOut(list.get(i).getManufacturers_products().get(j)));

            }

        }

    }

    @Override
    public int getItemViewType(int position) {
        ListOfChekOut item = items.get(position);

        if (item.isHeader()) {
            return TYPE_HEADER;
        } else {
            return TYPE_ITEM;
        }

        //return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public ItemListProductCheckOutAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        switch (viewType) {
            case TYPE_HEADER:{
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_check_out_header_item, parent, false);
                return new ViewHolderFrom(v);
            }

            case TYPE_ITEM:
            default:{
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_chek_out_item, parent, false);
                return new ViewHolderTo(v);
            }

        }

    }

    @Override
    public void onBindViewHolder(@NonNull ItemListProductCheckOutAdapter.ViewHolder holder, int position) {

        final ListOfChekOut item = items.get(position);

        // holder.setText(item.getTitle());
        switch (getItemViewType(position)){
            case TYPE_HEADER:
                itemConfigAdmin(item, (ViewHolderFrom) holder);
                break;
            case TYPE_ITEM:
                itemConfigUser(item, (ViewHolderTo) holder);
                break;
        }

    }


    private void itemConfigAdmin(ListOfChekOut messages, ViewHolderFrom viewHolderFrom){
        viewHolderFrom.tvTitleHeader.setText(messages.getManufacturers_title());
        //messages.setManufacturers_title(messages.getManufacturers_title());
    }

    private void itemConfigUser(final ListOfChekOut messages, final ViewHolderTo viewHolderTo) {

        viewHolderTo.tvProdRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClickDeleteProductUser(messages.getManufacturers_product());
            }
        });


        // Handle Decrement Button press.
        viewHolderTo.ivDecrem.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {

                String s = messages.getManufacturers_product().getCount();

                int s1 = Integer.parseInt(s);

                if (s1 == 0)
                    return;

                    s1--;
                if (s1 == 1)
                    viewHolderTo.ivDecrem.setVisibility(View.INVISIBLE);
                    viewHolderTo.tvCount.setText(Integer.toString(s1));

                int ans = Integer.parseInt(messages.getManufacturers_product().getPrice())  * s1;
                    viewHolderTo.tvProdPrice.setText(Integer.toString(ans));

                    messages.getManufacturers_product().setCount(String.valueOf(s1));
            }
        });


        // Handle Increment Button press
        viewHolderTo.ivIncrem.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {

                String s = messages.getManufacturers_product().getCount();
                int s1 = Integer.parseInt(s);

                if (s1 == 0)
                    return;

                s1++;
                if (s1 > 1)
                    viewHolderTo.ivDecrem.setVisibility(View.VISIBLE);
                    viewHolderTo.tvCount.setText(Integer.toString(s1));

                int ans = Integer.parseInt(messages.getManufacturers_product().getPrice())  * s1;
                    viewHolderTo.tvProdPrice.setText(String.valueOf(ans));

                    messages.getManufacturers_product().setCount(String.valueOf(s1));

            }
        });


        Glide.with(context).load(messages.getManufacturers_product().getLogo()).into(viewHolderTo.ivLogoProd);
        viewHolderTo.tvProdTitle.setText(messages.getManufacturers_product().getTitle());
        viewHolderTo.tvCount.setText(messages.getManufacturers_product().getCount());
        viewHolderTo.tvProdPrice.setText(messages.getManufacturers_product().getPrice());
        //messages.setMessage(messages.getMessage());
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
    public class ViewHolderFrom extends ViewHolder{

        TextView tvTitleHeader;
        CardView cardView;

        public ViewHolderFrom(View itemView) {
            super(itemView);

            tvTitleHeader = (TextView)itemView.findViewById(R.id.tvHeaderTitleManuf);
            cardView = (CardView)itemView.findViewById(R.id.cvHeaderTitle);

        }
    }

    // Handle Send To ViewHolder.
    public class ViewHolderTo extends ViewHolder{

        ImageView ivLogoProd;
        TextView tvProdTitle;
        TextView tvProdPrice;
        TextView tvProdRemove;

        ImageView ivDecrem;
        TextView tvCount;
        ImageView ivIncrem;


        public ViewHolderTo(View itemView) {
            super(itemView);

            ivLogoProd = (ImageView)itemView.findViewById(R.id.ivLogoProduct);
            tvProdTitle = (TextView)itemView.findViewById(R.id.product_title_check_out);
            tvProdPrice = (TextView)itemView.findViewById(R.id.tvPrice);
            tvProdRemove = (TextView)itemView.findViewById(R.id.remove1_check);

            ivDecrem = (ImageView)itemView.findViewById(R.id.ivDecrement);
            tvCount = (TextView)itemView.findViewById(R.id.count);
            ivIncrem = (ImageView)itemView.findViewById(R.id.ivIncrement);

        }
    }

    public void updateListBasket(List<BasketsData> list){
        this.list = list;
        gen();
        notifyDataSetChanged();
    }

    public abstract void ClickDeleteProductUser(BasketsData.ManufacturersProduct listOfChekOut);

}
