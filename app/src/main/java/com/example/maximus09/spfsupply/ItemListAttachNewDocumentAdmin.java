package com.example.maximus09.spfsupply;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.io.File;
import java.util.List;

public abstract class ItemListAttachNewDocumentAdmin extends RecyclerView.Adapter<ItemListAttachNewDocumentAdmin.ViewHolder>{

    private Context context;
    private List<File> files;

    public ItemListAttachNewDocumentAdmin(Context context, List<File> files) {
        this.context = context;
        this.files = files;
    }

    @NonNull
    @Override
    public ItemListAttachNewDocumentAdmin.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.custom_attachment_products_admin_items, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemListAttachNewDocumentAdmin.ViewHolder holder, final int position) {
         if (files == null || position >= files.size()) {
            holder.imageView_attachedDocument.setImageResource(R.drawable.icon_plus);
            holder.imageView_attachedDocument.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    OnAdd();

                }
            });
        } else {
            holder.imageView_attachedDocument.setImageResource(R.drawable.delete_image_background_red);
        }



    }

    @Override
    public int getItemCount() {
        if (files == null) {
            return 1;
        }
        return files.size() + 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public  ImageView imageView_attachedDocument;
        public TextView textView_productTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView_attachedDocument = (ImageView)itemView.findViewById(R.id.image_attach_new_prod);
            textView_productTitle = (TextView)itemView.findViewById(R.id.textView_product_custom_title);
        }
    }

    public void updateListOfProducts(List<File> list){
        this.files = list;
        notifyDataSetChanged();
    }

    public abstract void OnAdd();

}
