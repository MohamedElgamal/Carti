package com.example.triples.ApisConnectionClasses

import com.example.triples.Apis.ApiManager
import com.example.triples.DataContainers.CategoriesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoriesConnection constructor(abstractConnectionHandler : AbstractConnectionHandler<CategoriesResponse>)
    : Callback<CategoriesResponse> {
    var abstractConnectionHandler = abstractConnectionHandler
    fun connectToRetrieveCategories(){
        ApiManager
            .getApisGsonFactory()
            .categories("")
            .enqueue(this)
    }

    override fun onResponse(
        call: Call<CategoriesResponse>,
        response: Response<CategoriesResponse>) {
        if(response.isSuccessful){

            abstractConnectionHandler.onSuccessConnection(response.body())
        }
        else{
            abstractConnectionHandler.onFailedConnection()
        }
    }

    override fun onFailure(call: Call<CategoriesResponse>, t: Throwable) {
        abstractConnectionHandler.onFailureConnection()
    }
}