package com.example.triples.ApisConnectionClasses

import com.example.triples.Apis.ApiManager
import com.example.triples.DataContainers.CategoriesResponse
import com.example.triples.DataContainers.ProductBarcodeResponse
import com.example.triples.DataContainers.ProductsItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BarcodeSearchConnection
    (abstractConnectionHandler : AbstractConnectionHandler<ProductBarcodeResponse>) : Callback<ProductBarcodeResponse>{
    var abstractConnectionHandler = abstractConnectionHandler

    fun connectForBarcodeSearch(barcode : String){
        ApiManager.getApisGsonFactory()
            .searchByBarcode(barcode)
            .enqueue(this)
    }

    override fun onResponse(call: Call<ProductBarcodeResponse>, response: Response<ProductBarcodeResponse>) {
        if(response.isSuccessful){
            abstractConnectionHandler.onSuccessConnection(response.body())
        }
        else{
            abstractConnectionHandler.onFailedConnection()
        }
    }

    override fun onFailure(call: Call<ProductBarcodeResponse>, t: Throwable) {
        abstractConnectionHandler.onFailureConnection()
    }

}