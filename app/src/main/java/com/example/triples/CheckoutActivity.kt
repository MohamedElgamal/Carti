package com.example.triples

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.triples.databinding.ActivityCheckoutBinding

class CheckoutActivity : AppCompatActivity() {
    lateinit var binding : ActivityCheckoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        binding.checkoutCash.setOnClickListener(){
            cashCheckout()
        }
        binding.checkoutCredit.setOnClickListener(){
            creditCardCheckout()
        }
        setContentView(binding.root)
    }
    private fun cashCheckout(){
        val intent = Intent(this , CashActivity::class.java)
        startActivity(intent)
    }

    private fun creditCardCheckout(){
        val intent = Intent(this , CreditCardActivity::class.java)
        startActivity(intent)
    }

}