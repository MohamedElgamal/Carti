package com.example.triples.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.triples.ApisConnectionClasses.AbstractConnectionHandler;
import com.example.triples.ApisConnectionClasses.CartRemoveConnection;
import com.example.triples.DataContainers.CartResponse;
import com.example.triples.DataContainers.Products;
import com.example.triples.DataContainers.ProductsItem;
import com.example.triples.R;
import com.example.triples.databinding.CartProductItemBinding;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CartProductAdapter  extends RecyclerView.Adapter<CartProductAdapter.ViewHolder>
        implements AbstractConnectionHandler<CartResponse> {
    String userToken;
    LayoutInflater inflater;
    List<ProductsItem> productItems;/*****ProductItem may be modified Later*********/
    OnCartProductClick onCartProductClick;

    private CartRemoveConnection  cartRemoveConnection = new CartRemoveConnection(this);

    public CartProductAdapter(LayoutInflater inflater
            , List<ProductsItem> productItems , OnCartProductClick onCartProductClick , String userToken) {
        this.inflater = inflater;
        this.productItems = productItems;
        this.onCartProductClick = onCartProductClick;
        this.userToken = userToken;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        CartProductItemBinding binding = CartProductItemBinding.inflate(inflater
                , parent , false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        int qty = productItems.get(position).getQty();
        String productName = productItems.get(position).getName();
        String productImage = productItems.get(position).getImage();
        Double productPrice = productItems.get(position).getPrice();
        holder.binding.cartProductNameItem.setText(productName);
        holder.binding.cartProductPricePerProductItem.setText("" + productPrice+" EGP");
        holder.binding.cartProductTotalPriceProductItem.setText("" + (productPrice * qty)+" EGP");
        holder.binding.cartProductQuantityItem.setText(""+qty);
        holder.binding.cartProductNumberItem.setText("" + (position +1 ));
        Glide.with(inflater.getContext())
                .load("http://carti.elkayal.me/" + productImage)
                .into(holder.binding.cartProductImageItem);

        holder.binding.cartProductAddItem.setOnClickListener(new OnAddCartProduct(
                productItems.get(position) ,holder , position));

        holder.binding.cartProductRemoveItem.setOnClickListener(new OnRemoveCartProduct(
                productItems.get(position) , holder , position));
    }


    @Override
    public int getItemCount() {
        if(productItems == null) return 0;
        return productItems.size();
    }

    /************ProductItem may be modified Later***************/
    public void updateData(List<ProductsItem> productItems){
        this.productItems = productItems;
        notifyDataSetChanged();
    }

    public Context getContext(){
        return inflater.getContext();
    }

    public void removeItem(int position){
        productItems.remove(position);
        notifyItemChanged(position);
        notifyItemRangeRemoved(position , 1);
    }

    public void removeItemSwipe(int position){
        cartRemoveConnection.connectToRemove(this.userToken ,
                ""+productItems.get(position).getBarcode(),
                productItems.get(position).getQty());
        productItems.remove(position);
        notifyItemChanged(position);
        notifyItemRangeRemoved(position , 1);
    }
    @Override
    public void onSuccessConnection(CartResponse data) {
        // no implemention needed
    }

    @Override
    public void onFailedConnection() {
        // no implemention needed
    }

    @Override
    public void onFailureConnection() {
        // no implemention needed
    }

    public interface OnCartProductClick{
        void onAddClicked(int qty , ProductsItem productsItem);
        void onRemoveClicked(int qty , ProductsItem productsItem);
    }

    public class OnAddCartProduct implements View.OnClickListener{

        private ProductsItem productsItem;
        private ViewHolder holder;
        private int position;

        public OnAddCartProduct( ProductsItem productsItem, ViewHolder holder, int position) {
            this.productsItem = productsItem;
            this.holder = holder;
            this.position = position;
        }

        @Override
        public void onClick(View view) {
            int defaultQty = Integer.parseInt((String) holder.binding.cartProductQuantityItem.getText());
            Double totalProductCost = 0.0;
            defaultQty++;
            if(defaultQty > 1){
                holder.binding.cartProductRemoveItem.setImageResource(R.drawable.remove_ic);
            }
            totalProductCost =defaultQty * productsItem.getPrice();
            holder.binding.cartProductQuantityItem.setText(""+defaultQty);
            holder.binding.cartProductTotalPriceProductItem.setText("" + totalProductCost+" EGP");
            onCartProductClick.onAddClicked(defaultQty ,productsItem);
        }
    }
    public class OnRemoveCartProduct implements View.OnClickListener{
        private ProductsItem productsItem;
        private ViewHolder holder;
        private int position;

        public OnRemoveCartProduct(ProductsItem productsItem, ViewHolder holder, int position) {
            this.productsItem = productsItem;
            this.holder = holder;
            this.position = position;
        }

        @Override
        public void onClick(View view) {
            int defaultQty = Integer.parseInt((String) holder.binding.cartProductQuantityItem.getText());
            Double totalProductCost = 0.0;
            defaultQty--;
            if(defaultQty < 1){
                removeItem(holder.getAdapterPosition());
            }

            totalProductCost =defaultQty * productsItem.getPrice();
            holder.binding.cartProductQuantityItem.setText(""+defaultQty);
            holder.binding.cartProductTotalPriceProductItem.setText("" + totalProductCost +" EGP");
            /**Important what will happen if we send 0 qty to the end point Is it handled?**/
            onCartProductClick.onRemoveClicked(defaultQty ,productsItem );
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CartProductItemBinding binding;
        public ViewHolder(@NonNull @NotNull CartProductItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
