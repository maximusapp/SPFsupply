package com.example.maximus09.spfsupply;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.maximus09.spfsupply.struct.OrdersData;

import java.util.List;

/**
 * Created by maximus09 on 15.05.2018.
 */

public abstract class ItemListAddDocumentOrderNumber extends RecyclerView.Adapter<ItemListAddDocumentOrderNumber.ViewHolder> {

    private List<OrdersData.Documents> items;
    private Context context;

    public ItemListAddDocumentOrderNumber(List<OrdersData.Documents> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemListAddDocumentOrderNumber.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.custom_item_document_order_number, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemListAddDocumentOrderNumber.ViewHolder holder, final int position) {

        if (items.get(position).getName() != null) {
            holder.tvDocName.setText(items.get(position).getName());
        }

        holder.ivDeletDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClikDeletDocument(items.get(position));

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

        TextView tvDocName;
        ImageView ivDeletDoc;

        public ViewHolder(View itemView) {
            super(itemView);

            tvDocName = (TextView)itemView.findViewById(R.id.documentName);
            ivDeletDoc = (ImageView)itemView.findViewById(R.id.documentDelete);

        }
    }

    public void updateListOfDoc(List<OrdersData.Documents> list){
        this.items = list;
        notifyDataSetChanged();
    }

    public abstract void ClikDeletDocument(OrdersData.Documents documents);

}
