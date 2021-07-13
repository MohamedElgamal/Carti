package com.example.triples

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.triples.databinding.ActivityWebsiteBinding

class WebsiteActivity : AppCompatActivity() {
    lateinit var binding : ActivityWebsiteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebsiteBinding.inflate(layoutInflater)
        initWebView()
        setContentView(binding.root)
    }
    private fun initWebView(){
        binding.websiteWebView.settings.javaScriptEnabled = true
        binding.websiteWebView.loadUrl("http://carti.elkayal.me/")
    }
}