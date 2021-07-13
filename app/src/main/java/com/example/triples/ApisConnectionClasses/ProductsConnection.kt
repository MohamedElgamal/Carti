package com.example.triples.ApisConnectionClasses

import com.example.triples.Apis.ApiManager
import com.example.triples.DataContainers.CategoriesResponse
import com.example.triples.DataContainers.ProductsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductsConnection constructor(abstractConnectionHandler : AbstractConnectionHandler<ProductsResponse>)
    : Callback<ProductsResponse> {
    var abstractConnectionHandler = abstractConnectionHandler
    fun connectToRetrieveProducts(id : String){
        ApiManager
            .getApisGsonFactory()
            .products(id)
            .enqueue(this)
    }

    override fun onResponse(call: Call<ProductsResponse>, response: Response<ProductsResponse>) {
        if(response.isSuccessful){

            abstractConnectionHandler.onSuccessConnection(response.body())
        }
        else{
            abstractConnectionHandler.onFailedConnection()
        }
    }

    override fun onFailure(call: Call<ProductsResponse>, t: Throwable) {
        abstractConnectionHandler.onFailureConnection()
    }
}