package com.example.triples

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.triples.databinding.ActivityUpdateUserBinding


class UpdateUserActivity : AppCompatActivity() {
    lateinit var  binding : ActivityUpdateUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateUserBinding.inflate(layoutInflater)
        initWebView()
        setContentView(binding.root)
    }

    private fun initWebView(){
        binding.updateUserWebView.settings.javaScriptEnabled = true
        binding.updateUserWebView.loadUrl("")
        binding.updateUserWebView.setWebViewClient(object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                return if (url != null && (url.startsWith("http://") || url.startsWith("https://"))) {
                    view.context.startActivity(
                        Intent(Intent.ACTION_VIEW, Uri.parse("http://carti.elkayal.me/login"))
                    )
                    true
                } else {
                    false
                }
            }
        })
    }
}