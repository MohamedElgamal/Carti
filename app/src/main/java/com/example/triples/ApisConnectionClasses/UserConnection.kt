package com.example.triples.ApisConnectionClasses

import android.util.Log
import com.example.triples.Apis.ApiManager
import com.example.triples.DataContainers.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserConnection (abstractConnectionHandler: AbstractConnectionHandler<UserResponse>)
    :Callback<UserResponse>{
    val abstractConnectionHandler = abstractConnectionHandler

    fun connectToGetUser(token : String){
        ApiManager.getApisGsonFactory()
            .userInfo("Bearer $token", "")
            .enqueue(this)
    }

    override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
        if(response.isSuccessful)
            abstractConnectionHandler.onSuccessConnection(response.body())
        else{
            Log.e("Failed : " , "Response $response")
            abstractConnectionHandler.onFailedConnection()
        }

    }

    override fun onFailure(call: Call<UserResponse>, t: Throwable) {
        Log.e("Failure : " , "Trace :  $t")
        abstractConnectionHandler.onFailureConnection()
    }
}