package com.example.triples

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.example.triples.databinding.AlertDialogBinding

class BaseActivity : AppCompatActivity() {
    protected lateinit var dialog :Dialog
    protected lateinit var binding : AlertDialogBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    private fun initAlertDialog(activity :Activity ){
        binding = AlertDialogBinding.inflate(layoutInflater)
        dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(binding.root)

    }
     fun showAlertDialog( activity : Activity , message :String){
        initAlertDialog(activity)
        binding.alertDialogErrorText.text = message
        binding.alertDialogErrorButton.setOnClickListener(){
            dialog.dismiss()
        }
        dialog.show()
    }

}