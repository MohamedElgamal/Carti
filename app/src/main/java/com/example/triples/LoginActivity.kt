package com.example.triples

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Toast
import com.example.triples.Apis.ApiManager
import com.example.triples.ApisConnectionClasses.AbstractConnectionHandler
import com.example.triples.ApisConnectionClasses.ConnectionHandler
import com.example.triples.ApisConnectionClasses.LoginConnection
import com.example.triples.DataContainers.Constants
import com.example.triples.DataContainers.TokenResponse
import com.example.triples.DataContainers.User
import com.example.triples.DataContainers.UserResponse
import com.example.triples.databinding.ActivityLoginBinding
import com.example.triples.databinding.AlertDialogBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() , AbstractConnectionHandler<TokenResponse>{

    private var user = User("" , "" , "" , "")
    private lateinit var loginConnection : LoginConnection
    private lateinit var binding : ActivityLoginBinding

    private lateinit var dialog :Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loginConnection = LoginConnection(this)
        test()
        binding.loginRegisterHereText.setOnClickListener(){
            startRegistrationActivity()
        }
        binding.loginSignInButton.setOnClickListener(){
            signInButtonClicked()
        }
        //displayResponse()
        /*
        * **************************go directly to main activity**************************/
        /*val intent = Intent(this , MainActivity::class.java)
        startActivity(intent)*/
        /********************************************************************************/
    }

    private fun validateFields(textLayout : TextInputLayout ,
                               editText : TextInputEditText , message : String) :Boolean{
        val text = editText.text.toString()
        if(text == ""){
            textLayout.error = message
            return false
        }
        return true
    }

    private fun test(){
        var pref  = PreferencesHelper(this , Constants.CREDENTIALS_FILE_PATH)
        Log.e("Data : " , "Mail : " + pref.getAuthEmail() + " Pass : " + pref.getAuthPassword()
        + " token : " + pref.getAuthToken())
    }
    private fun startRegistrationActivity(){
        val intent = Intent(this , RegistrationActivity::class.java)
        startActivity(intent)
    }

    private fun signInButtonClicked(){
        var fullStateValidation = validateFields(binding.loginEmailLayout
            , binding.loginEmailText , getString(R.string.email_is_empty)) and
                validateFields(binding.loginPasswordLayout
                    , binding.loginPasswordText ,getString(R.string.password_field_empty))
        if(fullStateValidation){
            assignUserValues()
            loginConnection.connectToLogin(user)
        }
    }

    private fun assignUserValues(){
        user.email = binding.loginEmailText.text.toString()
        user.password = binding.loginPasswordText.text.toString()
        user.phoneType = "Android"
    }

    private fun startMainActivity(){
        val intent = Intent(this , MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onSuccessConnection(data: TokenResponse?) {
        val token = data?.token
        var pref  = PreferencesHelper(this , Constants.CREDENTIALS_FILE_PATH)
        pref.setAuthPassword(user.password)
        pref.setAuthEmail(user.email)
        pref.setAuthToken("" + token)
        startMainActivity()
    }

    override fun onFailedConnection() {
        alertErrorDialog(getString(R.string.wrong_data_entered))
    }

    override fun onFailureConnection() {
        Toast.makeText(this , "Check intenet Connection" , Toast.LENGTH_SHORT).show()
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

  /*  private fun displayResponse(){
        ApiManager.getApisGsonFactory()
            .userInfo("Bearer 33|O0AngHUsKs8p2jKlPINmqckuVQkSjCofcZ70l" , "")
            .enqueue(this)

    }

    override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
        var userResponse : UserResponse
        if(response.isSuccessful){
            userResponse = response.body()!!
            Log.e("Data : " , "" +userResponse.name
            +"email " + userResponse.email)
        }
        else{
            Log.e("Data : " , "Error " + response.code())
        }
    }

    override fun onSuccessConnection(data: TokenResponse?) {
        TODO("Not yet implemented")
    }

    override fun onFailure(call: Call<UserResponse>, t: Throwable) {
        Log.e("Login " , "OnFailure")
    }*/
}