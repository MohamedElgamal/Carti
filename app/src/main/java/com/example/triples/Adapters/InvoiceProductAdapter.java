package com.example.triples.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.triples.DataContainers.InvoiceItems;
import com.example.triples.DataContainers.ProductsItem;
import com.example.triples.databinding.ProductInvoiceItemBinding;
import com.example.triples.databinding.ProductItemBinding;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class InvoiceProductAdapter extends RecyclerView.Adapter<InvoiceProductAdapter.ViewHolder> {
    LayoutInflater layoutInflater;
    List<InvoiceItems> products;
    public InvoiceProductAdapter(LayoutInflater layoutInflater, List<InvoiceItems> products ) {
        this.layoutInflater = layoutInflater;
        this.products = products;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        ProductInvoiceItemBinding binding = ProductInvoiceItemBinding.inflate(layoutInflater , parent , false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        //don't forget to add glide to download the image online
        String name = products.get(position).getName();
        Double price = products.get(position).getPrice();
        int qty = products.get(position).getQty();
        String productImage = products.get(position).getImage();
        holder.binding.productNameTextView.setText(name);
        holder.binding.productPriceTextView.setText("" + price + " EGP");
        holder.binding.productQuantityTextView.setText("qty : " + qty);
        Glide.with(layoutInflater.getContext())
                .load("http://carti.elkayal.me/" + productImage)
                .into(holder.binding.productImageView);

    }

    @Override
    public int getItemCount() {
        if(products == null) return 0;
        return products.size();
    }

    public void updateData(List<InvoiceItems> productItems){
        products = productItems;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ProductInvoiceItemBinding binding;
        public ViewHolder(@NonNull @NotNull ProductInvoiceItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
