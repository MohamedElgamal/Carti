package com.example.triples.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.triples.Adapters.OffersAdapter
import com.example.triples.Adapters.ProductAdapter
import com.example.triples.ApisConnectionClasses.AbstractConnectionHandler
import com.example.triples.ApisConnectionClasses.CategoriesConnection
import com.example.triples.ApisConnectionClasses.ProductsConnection
import com.example.triples.DataContainers.*
import com.example.triples.OffersViewPagerHelper
import com.example.triples.R
import com.example.triples.databinding.FragmentMenuBinding
import com.google.android.material.tabs.TabLayout
import org.intellij.lang.annotations.JdkConstants


class MenuFragment constructor(val onProductMenuClick: OnProductMenuClick): Fragment() ,ProductAdapter.OnProductClick {
    lateinit var binding : FragmentMenuBinding
    lateinit var productsAdapter : ProductAdapter

    var categoriesList = ArrayList<CategoriesItem>()
    var offersList = ArrayList<Offers>()
    var productList = ArrayList<ProductsItem>()


    /*Apis Call*/
    var categoriesCall = CategoriesCall()
    lateinit var categoriesConnection : CategoriesConnection

    var productsCall = ProductsCall()
    lateinit var productsConnection : ProductsConnection

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        tempOffersDB()//to be removed
        binding = FragmentMenuBinding.inflate(layoutInflater)
        establishCategoryConnection()
        setupOfferViewPager()
        setupProductRecyclerView()
        return binding.root
    }

    private fun tempOffersDB(){
        offersList.add(Offers("Offer1" , R.drawable.offer_3))
        offersList.add(Offers("Offer2" , R.drawable.offer_2))
        offersList.add(Offers("Offer3" , R.drawable.offer_4))
        offersList.add(Offers("Offer4" , R.drawable.offer_5))
    }

    private fun setupOfferViewPager(){
        var offerHelper = OffersViewPagerHelper(binding.menuOffersViewPager)
        offerHelper.setupViewPager(OffersAdapter(layoutInflater , offersList ,binding.menuOffersViewPager))
    }

    private fun setupProductRecyclerView(){
        productsAdapter =ProductAdapter(layoutInflater , productList , this)
        binding.menuProductRecyclerView.adapter = productsAdapter
        binding.menuProductRecyclerView.layoutManager = LinearLayoutManager(context , RecyclerView.VERTICAL , false)
    }

    private fun establishCategoryConnection(){
        categoriesConnection = CategoriesConnection(categoriesCall)
        categoriesConnection.connectToRetrieveCategories()
    }

    private fun establishProductConnection(categoryId : String){
        productsConnection = ProductsConnection(productsCall)
        productsConnection.connectToRetrieveProducts(categoryId)
    }

    private fun bindProductsCategories(){
        for(category in categoriesList){
            val tab = binding.menuTabLayout.newTab()
            tab.text = category.name
            tab.tag =category
            binding.menuTabLayout.addTab(tab)
        }
        binding.menuTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                var categoryItem  = tab?.tag as CategoriesItem
                establishProductConnection("" + categoryItem.id)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })
    }

    override fun onProductClicked(products: ProductsItem, position: Int) {
        onProductMenuClick.onProductMenuClicked(products)
    }

    interface OnProductMenuClick{
        fun onProductMenuClicked(product :  ProductsItem)
    }

    /*Api classes inner classes implementation */
    inner class CategoriesCall :AbstractConnectionHandler<CategoriesResponse>{
        override fun onSuccessConnection(data: CategoriesResponse) {
            categoriesList = data.categories as ArrayList<CategoriesItem>
            bindProductsCategories()
        }

        override fun onFailedConnection() {
            Log.e("Failed" , " something went wrong")
        }

        override fun onFailureConnection() {
            Log.e("check" , " your internet connection or firewall access denied")
        }
    }

    inner class ProductsCall : AbstractConnectionHandler<ProductsResponse>{
        override fun onSuccessConnection(data: ProductsResponse?) {
            productList = data?.products as ArrayList<ProductsItem>
            productsAdapter.updateData(productList)
        }

        override fun onFailedConnection() {
            Log.e("Failed" , " something went wrong")
        }

        override fun onFailureConnection() {
            Log.e("check" , " your internet connection or firewall access denied")
        }
    }
}