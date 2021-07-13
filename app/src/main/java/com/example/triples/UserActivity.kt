package com.example.triples

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.triples.ApisConnectionClasses.AbstractConnectionHandler
import com.example.triples.ApisConnectionClasses.UserConnection
import com.example.triples.DataContainers.Constants
import com.example.triples.DataContainers.UserResponse
import com.example.triples.databinding.ActivityUserBinding

class UserActivity : AppCompatActivity(), AbstractConnectionHandler<UserResponse> {
    lateinit var sharedPrefernce: PreferencesHelper
    lateinit var userConnection: UserConnection
    lateinit var binding: ActivityUserBinding
    lateinit var data: UserResponse
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        init()
        userConnection.connectToGetUser("${sharedPrefernce.getAuthToken()}")
       /* binding.userUpdateDataBtn.setOnClickListener(){
            startUpdateUserActivity()
        }*/
        setContentView(binding.root)
    }

    private fun init() {
        sharedPrefernce = PreferencesHelper(this, Constants.CREDENTIALS_FILE_PATH)
        userConnection = UserConnection(this)
    }

    private fun bindDataToView(data: UserResponse?) {
        binding.userUsernameText.text = data?.name
        binding.userEmailText.text = data?.email
        binding.userJoinDateText.text = data?.createdAt
        Glide.with(this)
            .load("${data?.profilePhotoUrl}")
            .into(binding.userAccountImage)
    }

    private fun startUpdateUserActivity(){
        val intent = Intent(this , UpdateUserActivity::class.java)
        startActivity(intent)
    }

    override fun onSuccessConnection(data: UserResponse?) {
        this.data = data!!
        bindDataToView(data)
    }

    override fun onFailedConnection() {
        //NO IMP NEEDED
    }

    override fun onFailureConnection() {
        //NO IMP NEEDED
    }




}