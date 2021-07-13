package com.example.triples

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

class PreferencesHelper(context : Context , prefFileName : String) :IPreferencesHelper {
    val mPref : SharedPreferences = context.getSharedPreferences(prefFileName
        , Context.MODE_PRIVATE)

    val PREF_KEY_AUTH_TOKEN :String = "PREF_KEY_AUTH_TOKEN"
    val PREF_KEY_AUTH_EMAIL :String = "PREF_KEY_AUTH_EMAIL"
    val PREF_KEY_AUTH_PASSWORD :String = "PREF_KEY_AUTH_PASSWORD"
    val PREF_KEY_AUTH_USERNAME :String = "PREF_KEY_AUTH_USERNAME"

    override fun getAuthToken(): String {
        return mPref.getString(PREF_KEY_AUTH_TOKEN , "Not Found").toString()
    }

    override fun setAuthToken(token: String) {
        mPref.edit().putString(PREF_KEY_AUTH_TOKEN , token).apply()
        Log.e("Token : " , ""+ token)
    }

    override fun getAuthEmail(): String {
        return mPref.getString(PREF_KEY_AUTH_EMAIL , "Not Found").toString()
    }

    override fun setAuthEmail(email: String) {
        mPref.edit().putString(PREF_KEY_AUTH_EMAIL , email).apply()
    }

    override fun getAuthPassword(): String {
        return mPref.getString(PREF_KEY_AUTH_PASSWORD , "Not Found").toString()
    }

    override fun setAuthPassword(password: String) {
        mPref.edit().putString(PREF_KEY_AUTH_PASSWORD , password).apply()
    }

    override fun getAuthUsername(): String {
        return mPref.getString(PREF_KEY_AUTH_USERNAME, "Not Found").toString()
    }

    override fun setAuthUsername(username: String) {
        mPref.edit().putString(PREF_KEY_AUTH_USERNAME , username).apply()
    }

    override fun deleteSharedPreference() {
        mPref.edit().clear().apply()
        mPref.edit().commit()
    }
}