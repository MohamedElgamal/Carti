package com.example.triples.ApisConnectionClasses

import android.util.Log
import com.example.triples.Apis.ApiManager
import com.example.triples.DataContainers.CashResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CheckoutCashConnection (abstractConnectionHandler: AbstractConnectionHandler<CashResponse>)
    : Callback<CashResponse> {
    val abstractConnectionHandler = abstractConnectionHandler
    fun connectToCashCheckout(token : String , qrCode : String){
        ApiManager.getApisGsonFactory()
            .cashCheckout(token , qrCode)
            .enqueue(this)
    }

    override fun onResponse(call: Call<CashResponse>, response: Response<CashResponse>) {
        Log.e("QR CODE REQUEST " , " " + response.body().toString())
        if(response.isSuccessful){
            abstractConnectionHandler.onSuccessConnection(response.body())
        }
        else{
            abstractConnectionHandler.onFailedConnection()
        }
    }

    override fun onFailure(call: Call<CashResponse>, t: Throwable) {
        Log.e("QR CODE ERROR : " , ""+ t)
        abstractConnectionHandler.onFailureConnection()
    }
}