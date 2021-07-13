package com.example.triples.ApisConnectionClasses

interface ConnectionHandler{
    fun onSuccessConnection(token : String)
    fun onFailedConnection()
    fun onFailureConnection()
}