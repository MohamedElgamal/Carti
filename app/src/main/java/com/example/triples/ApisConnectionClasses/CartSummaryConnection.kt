package com.example.triples.ApisConnectionClasses

import com.example.triples.Apis.ApiManager
import com.example.triples.DataContainers.CartResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.abs

class CartSummaryConnection (abstractConnectionHandler: AbstractConnectionHandler<CartResponse>)
    :Callback<CartResponse>{

    val abstractConnecHandler = abstractConnectionHandler

    fun connectToGetCartSummary(token : String){
        ApiManager.getApisGsonFactory()
            .cartSummary(token , "")
            .enqueue(this)
    }

    override fun onResponse(call: Call<CartResponse>, response: Response<CartResponse>) {
        if(response.isSuccessful){
            abstractConnecHandler.onSuccessConnection(response.body())
        }
        else{
            abstractConnecHandler.onFailedConnection()
        }
    }

    override fun onFailure(call: Call<CartResponse>, t: Throwable) {
        abstractConnecHandler.onFailureConnection()
    }
}