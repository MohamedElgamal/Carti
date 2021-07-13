package com.example.triples.ApisConnectionClasses

import android.util.Log
import com.example.triples.Apis.ApiManager
import com.example.triples.DataContainers.InvoicesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class InvoicesConnection (abstractConnectionHandler  :AbstractConnectionHandler<InvoicesResponse>)
    :Callback<InvoicesResponse>{
    val abstractConnectionHandler = abstractConnectionHandler

    fun connectToGetInvoice(token : String){
        ApiManager.getApisGsonFactory()
            .invoices(token , "")
            .enqueue(this)
    }

    override fun onResponse(call: Call<InvoicesResponse>, response: Response<InvoicesResponse>) {
        if(response.isSuccessful){
            abstractConnectionHandler.onSuccessConnection(response.body())
        }
        else{
            abstractConnectionHandler.onFailedConnection()
            Log.e("Error Invoice " , "" + response)
        }
    }

    override fun onFailure(call: Call<InvoicesResponse>, t: Throwable) {
        abstractConnectionHandler.onFailureConnection()
        Log.e("onFailure Invoice " , "" +t)
    }
}