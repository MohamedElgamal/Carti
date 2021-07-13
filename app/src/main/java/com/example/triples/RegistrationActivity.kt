package com.example.triples

import android.app.Dialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import com.example.triples.ApisConnectionClasses.AbstractConnectionHandler
import com.example.triples.ApisConnectionClasses.ConnectionHandler
import com.example.triples.ApisConnectionClasses.RegistrationConnection
import com.example.triples.DataContainers.Constants
import com.example.triples.DataContainers.TokenResponse
import com.example.triples.DataContainers.User
import com.example.triples.databinding.ActivityRegistrationBinding
import com.example.triples.databinding.AlertDialogBinding


class RegistrationActivity : AppCompatActivity() ,AbstractConnectionHandler<TokenResponse> {
    private lateinit var binding : ActivityRegistrationBinding
    private lateinit var dialog :Dialog
    private var user = User("" , "" , "" , "")

    /*REGEX VALIDATION PATTERNS */
    private val emailRegex = Regex("[a-zA-Z0-9+_.-]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+")
    private val phoneRegex = Regex("\\+(?:[0-9] ?){6,14}[0-9]")
    private val nameRegex = Regex("[a-zA-Z]{4,}(?: [a-zA-Z]+){0,}")
    private val passwordRegex = Regex("(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{10,}")//Min 10 chars, at least 1 letter and 1 number

    //
    private lateinit var registrationConnection  :RegistrationConnection

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        registrationConnection = RegistrationConnection(this)
        binding.registrationSignUpButton.setOnClickListener(){
            fieldsValidation()
        }
        binding.registrationLoginText.setOnClickListener(){
            moveToLoginActivity()
        }
    }
    private fun fieldsValidation(){
        var fullStateValidation : Boolean = ( phoneValidation() and  nameValidation()
                and passwordValidation() and  repasswordValidation() and  emailValidation() )
        if(fullStateValidation == true){
            assignUserValues()
            registrationConnection.connectToCreateAccount(user)
        }
    }

    private fun assignUserValues(){
        user.name = binding.registrationUsernameText.text.toString()
        user.email = binding.registrationEmailText.text.toString()
        user.password = binding.registrationPasswordText.text.toString()
        user.phoneType = "Android"
    }

    private fun emailValidation() :Boolean{
        val emailText = binding.registrationEmailText.text.toString()
        if(emailText == ""){
            binding.registrationEmailLayout.error = getString(R.string.email_is_empty)
            return false
        }
        else if(!emailRegex.containsMatchIn(emailText)){
            binding.registrationEmailLayout.error = getString(R.string.email_not_valid)
            return  false
        }
        else{
            binding.registrationEmailLayout.isErrorEnabled = false
            return true
        }
    }

    private fun phoneValidation():Boolean{
        val phoneText = binding.registrationPhoneText.text.toString()
        if(phoneText == ""){
            binding.registrationPhoneLayout.error = getString(R.string.phone_is_empty)
            return false
        }
        else if(!phoneRegex.containsMatchIn(phoneText)){
            binding.registrationPhoneLayout.error = getString(R.string.phone_not_valid)
            return false
        }
        else{
            binding.registrationPhoneLayout.isErrorEnabled = false
            return true
        }
    }

    private fun nameValidation():Boolean{
        val nameText = binding.registrationUsernameText.text.toString()
        if(nameText == ""){
            binding.registrationUsernameLayout.error = getString(R.string.name_empty)
            return false
        }
        else if(!nameRegex.containsMatchIn(nameText)){
            binding.registrationUsernameLayout.error = getString(R.string.at_least_4_char)
            return false
        }
        else{
            binding.registrationUsernameLayout.isErrorEnabled = false
            return true
        }
    }

    private fun passwordValidation():Boolean{
        val passwordText = binding.registrationPasswordText.text.toString()
        if(passwordText == "")
        {
            binding.registrationPasswordLayout.error = getString(R.string.password_field_empty)
            return false
        }
        else if(!passwordRegex.containsMatchIn(passwordText)){
            binding.registrationPasswordLayout.error = getString(R.string.at_least_10_char)
            return false
        }
        else
        {
            binding.registrationPasswordLayout.isErrorEnabled = false
            return true
        }
    }
    private fun repasswordValidation():Boolean{
        val repasswordText = binding.registrationRepasswordText.text.toString()
        val passwordText = binding.registrationPasswordText.text.toString()
        if(repasswordText == "")
        {
            binding.registrationRepasswordLayout.error = getString(R.string.repassword_field_empty)
            return false
        }
        else if(repasswordText != passwordText){
            binding.registrationRepasswordLayout.error = getString(R.string.two_password_not_identical)
            return false
        }
        else
        {
            binding.registrationRepasswordLayout.isErrorEnabled = false
            return true
        }
    }

    private fun alertErrorDialog(message : String){
        var bindingDialog : AlertDialogBinding
        bindingDialog = AlertDialogBinding.inflate(layoutInflater)
        dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(bindingDialog.root)
        bindingDialog.alertDialogErrorText.text = message
        bindingDialog.alertDialogErrorButton.setOnClickListener(){
            dialog.dismiss()
        }
        dialog.show()
    }

    override fun onSuccessConnection(data: TokenResponse?) {
        val token = data?.token
        var pref  = PreferencesHelper(this , Constants.CREDENTIALS_FILE_PATH)
        pref.setAuthToken("" + token)
        pref.setAuthUsername(user.name)
        pref.setAuthEmail(user.email)
        pref.setAuthPassword(user.password)
        //handle going to next activity******
        moveToMainActivity()

    }

    private fun moveToMainActivity(){
        val intent = Intent(this , MainActivity::class.java)
        startActivity(intent)
    }

    private fun moveToLoginActivity(){
        val intent = Intent(this , LoginActivity::class.java)
        startActivity(intent)
    }

    override fun onFailedConnection() {
       alertErrorDialog(getString(R.string.email_alread_exist))
    }

    override fun onFailureConnection() {
        Toast.makeText(this , "Check internet Connection" , Toast.LENGTH_LONG).show()
    }
}