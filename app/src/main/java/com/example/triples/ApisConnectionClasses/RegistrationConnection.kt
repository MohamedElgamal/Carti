package com.example.triples.ApisConnectionClasses

import com.example.triples.Apis.ApiManager
import com.example.triples.DataContainers.TokenResponse
import com.example.triples.DataContainers.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistrationConnection constructor(abstractConnectionHandler : AbstractConnectionHandler<TokenResponse>)
    :Callback<TokenResponse> {
    val abstractConnectionHandler = abstractConnectionHandler
    fun connectToCreateAccount(user : User){
        ApiManager.getApisGsonFactory().registration(user.name
            ,user.email
            ,user.password
            ,user.email)
            .enqueue(this)
    }

    override fun onResponse(call: Call<TokenResponse>, response: Response<TokenResponse>) {
        if(response.isSuccessful){
            abstractConnectionHandler.onSuccessConnection(response.body())
        }
        else{
            abstractConnectionHandler.onFailedConnection()
        }
    }

    override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
        abstractConnectionHandler.onFailureConnection()
    }
}