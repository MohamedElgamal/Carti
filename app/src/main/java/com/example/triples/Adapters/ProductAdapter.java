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
import com.example.triples.DataContainers.Products;
import com.example.triples.DataContainers.ProductsItem;
import com.example.triples.R;
import com.example.triples.databinding.ProductItemBinding;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    LayoutInflater layoutInflater;
    List<ProductsItem> products;
    OnProductClick onProductClick;
    public ProductAdapter(LayoutInflater layoutInflater, List<ProductsItem> products , OnProductClick onProductClick) {
        this.layoutInflater = layoutInflater;
        this.products = products;
        this.onProductClick = onProductClick;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        ProductItemBinding binding = ProductItemBinding.inflate(layoutInflater , parent , false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        //don't forget to add glide to download the image online
        String name = products.get(position).getName();
        Double price = products.get(position).getPrice();
        Double qunatityType = products.get(position).getWeight();
        String productImage = products.get(position).getImage();

        holder.productNameText.setText(name);
        holder.productPriceText.setText("" + price +" EGP");
        holder.productQuantityTypeText.setText("" + qunatityType + " Kg");
        Glide.with(layoutInflater.getContext())
                .load("http://carti.elkayal.me/" + productImage)
                .into(holder.productImage);
        holder.productDetailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onProductClick.onProductClicked(products.get(position) , position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(products == null) return 0;
        return products.size();
    }

    public void updateData(List<ProductsItem> productItems){
        products = productItems;
        notifyDataSetChanged();
    }
    public interface OnProductClick{
        void onProductClicked(ProductsItem products , int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView productImage;
        TextView productPriceText;
        TextView productQuantityTypeText;
        TextView productNameText;
        Button productDetailsBtn;
        public ViewHolder(@NonNull @NotNull ProductItemBinding binding) {
            super(binding.getRoot());
            productImage = binding.productImageView;
            productPriceText = binding.productPriceTextView;
            productQuantityTypeText = binding.productQuantityTextView;
            productNameText = binding.productNameTextView;
            productDetailsBtn = binding.productDetailsBtn;
        }
    }
}
