package com.example.triples

interface IPreferencesHelper {

    fun getAuthToken() : String
    fun setAuthToken(token : String)

    fun getAuthEmail() : String
    fun setAuthEmail(email : String)

    fun getAuthPassword() : String
    fun setAuthPassword(password : String)

    fun getAuthUsername() : String
    fun setAuthUsername(username : String)

    fun deleteSharedPreference()
}