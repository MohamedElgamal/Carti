package com.example.triples.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.triples.DataContainers.ProductsItem
import com.example.triples.R
import com.example.triples.databinding.FragmentProductBinding


class ProductFragment constructor(val product : ProductsItem) : Fragment() {
    lateinit var binding  : FragmentProductBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductBinding.inflate(layoutInflater)
        bindViewData()
        return binding.root
    }
    fun bindViewData(){
        binding.productImage.setImageResource(R.drawable.product_image)//to be updated to glide
        binding.productNameText.text = product.name
        binding.productPriceText.text = "" + product.price+ " EGP"
        binding.productPackageSizeText.text = "" + product.weight
        binding.productDetailsContentText.text = product.description
        Glide.with(this)
            .load("http://carti.elkayal.me/${product.image}")
            .into(binding.productImage)
    }

}