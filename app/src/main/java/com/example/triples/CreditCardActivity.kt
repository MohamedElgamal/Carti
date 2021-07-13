package com.example.triples

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.example.triples.ApisConnectionClasses.AbstractConnectionHandler
import com.example.triples.ApisConnectionClasses.CreditCardConnection
import com.example.triples.DataContainers.Constants
import com.example.triples.DataContainers.CreditCardResponse
import com.example.triples.databinding.ActivityCreditCardBinding

class CreditCardActivity : AppCompatActivity() ,AbstractConnectionHandler<CreditCardResponse>{
    lateinit var binding : ActivityCreditCardBinding
    lateinit var creditCardConnection : CreditCardConnection
    lateinit var paymentUrl : String
    lateinit var sharePreferencesHelper: PreferencesHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreditCardBinding.inflate(layoutInflater)
        enableJavaScriptWebView()
        setWebViewClient()
        initConnection()
        setContentView(binding.root)
    }

    private fun initConnection(){
        sharePreferencesHelper = PreferencesHelper(this , Constants.CREDENTIALS_FILE_PATH)
        creditCardConnection = CreditCardConnection(this)
        creditCardConnection.connectToGetLink("Bearer "+sharePreferencesHelper.getAuthToken())
    }

    private fun setWebViewClient(){
        binding.creditCardWebView.webViewClient = CreditCardWebViewClient()
    }

    private fun enableJavaScriptWebView(){
        binding.creditCardWebView.settings.javaScriptEnabled = true
    }

    private fun bindUrlToWebView(){
        binding.creditCardWebView.loadUrl("" + paymentUrl)
    }

    override fun onSuccessConnection(data: CreditCardResponse?) {
        paymentUrl = data?.url!!
        bindUrlToWebView()
    }

    override fun onFailedConnection() {

    }

    override fun onFailureConnection() {

    }

    private fun clearActivityBackStack(){
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NO_HISTORY
        startActivity(intent)
    }

    inner class CreditCardWebViewClient : WebViewClient(){
        override fun doUpdateVisitedHistory(view: WebView?, url: String?, isReload: Boolean) {
            if(url.equals("http://carti.elkayal.me/login")){
                clearActivityBackStack()
                finish()
            }
            super.doUpdateVisitedHistory(view, url, isReload)
        }
    }
}