package com.example.triples.ApisConnectionClasses

import com.example.triples.Apis.ApiManager
import com.example.triples.DataContainers.CartResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartAddConnection(abstractConnectionHandler: AbstractConnectionHandler<CartResponse>) : Callback<CartResponse>{
    val abstractConnectHandler = abstractConnectionHandler
    fun connectForAdd(token :String , barcode : String , qty : Int){
        ApiManager.getApisGsonFactory()
            .addItemToCart(token , barcode , qty)
            .enqueue(this)
    }

    override fun onResponse(call: Call<CartResponse>, response: Response<CartResponse>) {
        if(response.isSuccessful){
            abstractConnectHandler.onSuccessConnection(response.body())
        }
        else{
            abstractConnectHandler.onFailedConnection()
        }
    }

    override fun onFailure(call: Call<CartResponse>, t: Throwable) {
        abstractConnectHandler.onFailureConnection()
    }
}