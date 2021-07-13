package com.example.triples.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.triples.DataContainers.Offers;
import com.example.triples.R;
import com.example.triples.databinding.OfferImageBinding;
import com.makeramen.roundedimageview.RoundedImageView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.ViewHolder> {
    LayoutInflater layoutInflater;
    List<Offers> offers;
    ViewPager2 viewPager2;

    public OffersAdapter(LayoutInflater layoutInflater , List<Offers> offers , ViewPager2 viewPager2) {
        this.layoutInflater = layoutInflater;
        this.offers = offers;
        this.viewPager2 = viewPager2;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        OfferImageBinding binding = OfferImageBinding.inflate(layoutInflater , parent , false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        int resId = offers.get(position).getImageResourceId();
        Log.e("Image ResId : " , " " + resId);
        holder.offerImage.setImageResource(resId);
        if(position == offers.size() - 2 ){
            viewPager2.post(runnable);
        }
    }

    @Override
    public int getItemCount() {
        if(offers == null) return 0;
        return offers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        RoundedImageView offerImage;
        public ViewHolder(@NonNull @NotNull OfferImageBinding binding) {
            super(binding.getRoot());
            offerImage = binding.offerImage;
        }

    }

    private Runnable runnable = new Runnable(){
        @Override
        public void run() {
            offers.addAll(offers);
            notifyDataSetChanged();
        }
    };
}
