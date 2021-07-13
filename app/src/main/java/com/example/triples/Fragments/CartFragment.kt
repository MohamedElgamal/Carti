package com.example.triples.Fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.triples.*
import com.example.triples.Adapters.CartProductAdapter
import com.example.triples.ApisConnectionClasses.AbstractConnectionHandler
import com.example.triples.ApisConnectionClasses.CartAddConnection
import com.example.triples.ApisConnectionClasses.CartRemoveConnection
import com.example.triples.ApisConnectionClasses.CartSummaryConnection
import com.example.triples.DataContainers.*
import com.example.triples.databinding.FragmentCartBinding


class CartFragment : Fragment() , CartProductAdapter.OnCartProductClick{
    lateinit var binding  : FragmentCartBinding
    lateinit var adapter : CartProductAdapter
    lateinit var itemTouchHelper: ItemTouchHelper

    var productItems = ArrayList<ProductsItem>()

    lateinit var onCartAddConnection : OnCartAddConnection
    lateinit var cartAddConnection : CartAddConnection

    lateinit var onCartRemoveConnection: OnCartRemoveConnection
    lateinit var cartRemoveConnection: CartRemoveConnection

    lateinit var onCartSummaryConnection: OnCartSummaryConnection
    lateinit var  cartSummaryConnection: CartSummaryConnection

    lateinit var sharePreferencesHelper: PreferencesHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(layoutInflater)
        init()

        binding.cartScanBtn.setOnClickListener(){
            val intent = Intent(context , BarcodeActivity::class.java)
            startActivity(intent)
        }

        binding.cartCheckoutBtn.setOnClickListener(){
            checkout()
        }
        setupRecyclerView()
        return binding.root
    }

    private fun init(){
        sharePreferencesHelper = PreferencesHelper(requireContext() , Constants.CREDENTIALS_FILE_PATH)
        onCartAddConnection = OnCartAddConnection()
        cartAddConnection = CartAddConnection(onCartAddConnection)
        onCartRemoveConnection = OnCartRemoveConnection()
        cartRemoveConnection = CartRemoveConnection(onCartRemoveConnection)
        onCartSummaryConnection = OnCartSummaryConnection()
        cartSummaryConnection = CartSummaryConnection(onCartSummaryConnection)
    }

    private fun checkout(){
        if(productItems.size != 0){
            val intent = Intent(context , CheckoutActivity::class.java)
            startActivity(intent)
        }
    }

    private fun noProductMessageViewVisibility(){
        binding.cartNoProductsText.visibility = View.INVISIBLE
    }

    private fun setupRecyclerView(){
        adapter = CartProductAdapter(layoutInflater ,productItems , this
            ,"Bearer "+sharePreferencesHelper.getAuthToken() )
        binding.cartProductsRecyclerView.adapter =  adapter
        binding.cartProductsRecyclerView.layoutManager = LinearLayoutManager(context
            , RecyclerView.VERTICAL , false)
        itemTouchHelper = ItemTouchHelper(SwipeToDeleteProductHelper(adapter))
        itemTouchHelper.attachToRecyclerView(binding.cartProductsRecyclerView)
    }

    override fun onAddClicked(qty: Int, productsItem: ProductsItem?) {
        cartAddConnection.connectForAdd("Bearer "+sharePreferencesHelper.getAuthToken()
            ,""+productsItem?.barcode,1)

    }

    override fun onRemoveClicked(qty: Int, productsItem: ProductsItem?) {
        cartRemoveConnection.connectToRemove("Bearer "+sharePreferencesHelper.getAuthToken()
            ,""+productsItem?.barcode,1)
    }

    override fun onResume() {
        super.onResume()
        if(DataTunnel.productsItem != null){
            cartAddConnection.connectForAdd("Bearer "+sharePreferencesHelper.getAuthToken()
                ,"" +DataTunnel.productsItem.barcode , 1 )
            noProductMessageViewVisibility()
            DataTunnel.productsItem = null
        }
        else if(productItems.size == 0){
            cartSummaryConnection.connectToGetCartSummary("Bearer "
                    + sharePreferencesHelper.getAuthToken())
        }
    }


    inner class OnCartAddConnection : AbstractConnectionHandler<CartResponse>{
        override fun onSuccessConnection(data: CartResponse?) {
            var productItem = ArrayList<ProductsItem>()
            for(item in data?.items!!){
                productItem.add(ProductsItem().cartItemCastFactory(item!!))
            }
            productItems = productItem
            adapter.updateData(productItem)
            binding.cartTotalInvoiceNumberText.text =  "${data.total} EGP"
        }

        override fun onFailedConnection() {
            Toast.makeText(context , "Can't add the product please try later", Toast.LENGTH_SHORT)
                .show()
        }

        override fun onFailureConnection() {
            Toast.makeText(context , "Check the internet connection or the firewall",Toast.LENGTH_SHORT)
                .show()
        }
    }

    inner class OnCartRemoveConnection : AbstractConnectionHandler<CartResponse>{
        override fun onSuccessConnection(data: CartResponse?) {
            var productItem = ArrayList<ProductsItem>()
            for(item in data?.items!!){
                productItem.add(ProductsItem().cartItemCastFactory(item!!))
            }
            productItems = productItem
            adapter.updateData(productItem)
            binding.cartTotalInvoiceNumberText.text =  "${data.total} EGP"
        }

        override fun onFailedConnection() {
            Toast.makeText(context , "Product can't be removed" , Toast.LENGTH_SHORT)
                .show()
        }

        override fun onFailureConnection() {
            Toast.makeText(context , "Check the internet connection or the firewall" , Toast.LENGTH_SHORT)
                .show()
        }
    }

    inner class OnCartSummaryConnection : AbstractConnectionHandler<CartResponse>{
        override fun onSuccessConnection(data: CartResponse?) {
            adapter.updateData(productItems)
            if(data?.items != null){
                for(item in data.items){
                    productItems.add(ProductsItem().cartItemCastFactory(item!!))
                }
            }
            if(productItems.size != 0)
                noProductMessageViewVisibility()
            adapter.updateData(productItems)
        }

        override fun onFailedConnection() {
            //no imp needed
        }

        override fun onFailureConnection() {
            //no imp needed
        }
    }
}