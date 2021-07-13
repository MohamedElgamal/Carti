package com.example.triples

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.WindowManager
import com.example.triples.ApisConnectionClasses.AbstractConnectionHandler
import com.example.triples.ApisConnectionClasses.UserConnection
import com.example.triples.DataContainers.Constants
import com.example.triples.DataContainers.User
import com.example.triples.DataContainers.UserResponse
import com.example.triples.databinding.ActivitySplashScreenBinding

class SplashScreenActivity : AppCompatActivity() , AbstractConnectionHandler<UserResponse>{
    lateinit var binding : ActivitySplashScreenBinding
    lateinit var sharePreferencesHelper: PreferencesHelper
    lateinit var userConnection : UserConnection
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN
                ,WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        Handler().postDelayed({
            if(checkInternetConnectivity()){
                autoLogin()
            }
            else{
                intent = Intent(this , InternetConnectivityActivity::class.java)
                startActivity(intent)
            }
            finish()
        } ,3000 )
    }

    private fun init(){
        sharePreferencesHelper = PreferencesHelper(this , Constants.CREDENTIALS_FILE_PATH)
        userConnection = UserConnection(this)
    }

    private fun startLoginActivity(){
        intent = Intent(this , LoginActivity::class.java)
        startActivity(intent)
    }

    private fun startMainActivity(){
        intent = Intent(this , MainActivity::class.java)
        startActivity(intent)
    }

    private fun autoLogin(){
        Log.e("Token " , "${sharePreferencesHelper.getAuthToken()}")
        userConnection.connectToGetUser(sharePreferencesHelper.getAuthToken())
    }

    private fun checkInternetConnectivity() : Boolean{
        val connectionManager : ConnectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE)
            as ConnectivityManager
        val activeNetwork : NetworkInfo? = connectionManager.activeNetworkInfo
        val isConnected : Boolean = activeNetwork?.isConnectedOrConnecting == true
        if(isConnected){
            return true
        }
        return false
    }

    override fun onSuccessConnection(data: UserResponse?) {
        startMainActivity()
    }

    override fun onFailedConnection() {
        startLoginActivity()
    }

    override fun onFailureConnection() {
        startLoginActivity()
    }
}