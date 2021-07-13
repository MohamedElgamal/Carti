package com.example.triples.ApisConnectionClasses

import com.example.triples.Apis.ApiManager
import com.example.triples.DataContainers.CartResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartRemoveConnection (abstractConnectionHandler: AbstractConnectionHandler<CartResponse>) :Callback<CartResponse> {
    val abstractConnectionHandler = abstractConnectionHandler

    fun connectToRemove(token : String , barcode : String , qty : Int){
        ApiManager.getApisGsonFactory()
            .removeItemFromCart(token , barcode , qty)
            .enqueue(this)
    }

    override fun onResponse(call: Call<CartResponse>, response: Response<CartResponse>) {
        if(response.isSuccessful){
            abstractConnectionHandler.onSuccessConnection(response.body())
        }
        else{
            abstractConnectionHandler.onFailedConnection()
        }
    }

    override fun onFailure(call: Call<CartResponse>, t: Throwable) {
        abstractConnectionHandler.onFailureConnection()
    }
}