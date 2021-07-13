package com.example.triples.ApisConnectionClasses

import android.util.Log
import com.example.triples.Apis.ApiManager
import com.example.triples.DataContainers.CreditCardResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreditCardConnection (abstractConnectionHandler: AbstractConnectionHandler<CreditCardResponse>)
    :Callback<CreditCardResponse>{
    val abstractConnectionHandler = abstractConnectionHandler

    fun connectToGetLink(token : String){
        ApiManager.getApisGsonFactory()
            .creditCardCheckout(token , "")
            .enqueue(this)
    }

    override fun onResponse(
        call: Call<CreditCardResponse>,
        response: Response<CreditCardResponse>
    ) {
        if(response.isSuccessful){
            abstractConnectionHandler.onSuccessConnection(response.body())
        }
        else{
            abstractConnectionHandler.onFailedConnection()
            Log.e("onFailed : " , " RESPONSE " + response)
        }
    }

    override fun onFailure(call: Call<CreditCardResponse>, t: Throwable) {
        abstractConnectionHandler.onFailureConnection()
    }
}