package com.example.triples

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.example.triples.databinding.ActivityMainBinding
import com.example.triples.databinding.InternetConnectivityBinding

class InternetConnectivityActivity : AppCompatActivity() {
    private lateinit var binding : InternetConnectivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = InternetConnectivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.internetConnectCheckConnectionButton.setOnClickListener(){
            checkInternetConnectivity()
        }
    }

    fun checkInternetConnectivity(){
        val connectionManager : ConnectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        val activeNetwork : NetworkInfo? = connectionManager.activeNetworkInfo
        val isConnected : Boolean = activeNetwork?.isConnectedOrConnecting == true
        if(isConnected){
            startLoginActivity()
        }
    }
    fun startLoginActivity(){
        binding.internetConnectImageView.setImageResource(R.drawable.internet_connected_2_ic)
        binding.internetConnectTextView.text = getString(R.string.connected_to_internet)
        Handler().postDelayed({
            val intent :Intent = Intent(this , LoginActivity::class.java)
            startActivity(intent)
            finish()
        } ,1500 )
    }
}