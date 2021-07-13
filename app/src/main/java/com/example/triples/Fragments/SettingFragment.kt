package com.example.triples.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.triples.*
import com.example.triples.DataContainers.Constants
import com.example.triples.databinding.FragmentSettingBinding


class SettingFragment(onInvoiceSettingClicked : OnInvoiceSettingClicked): Fragment() {
    val onInvoiceSettingClicked = onInvoiceSettingClicked

    lateinit var binding : FragmentSettingBinding
    lateinit var sharePreferencesHelper: PreferencesHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingBinding.inflate(layoutInflater)
        init()
        binding.settingLogoutSection.setOnClickListener(){
            logout()
            returnToLogin()
        }
        binding.settingOurWebsite.setOnClickListener(){
            startWebsiteActivity()
        }
        binding.settingAboutUs.setOnClickListener(){
            startAboutActivity()
        }
        binding.settingInvoices.setOnClickListener(){
            moveToInvoiceFragment()
        }
        binding.settingPersonalSetting.setOnClickListener(){
            startUserActivity()
        }
        return binding.root
    }

    private fun init(){
        sharePreferencesHelper = PreferencesHelper(requireContext() , Constants.CREDENTIALS_FILE_PATH)
    }

    private fun moveToInvoiceFragment(){
        onInvoiceSettingClicked.onInvoiceSettingClicked()
    }

    private fun startWebsiteActivity() {
        val intent = Intent(context, WebsiteActivity::class.java)
        startActivity(intent)
    }

    private fun startAboutActivity(){
        val intent = Intent(context, AboutActivity::class.java)
        startActivity(intent)
    }

    private fun startUserActivity(){
        val intent = Intent(context, UserActivity::class.java)
        startActivity(intent)
    }

    private fun logout(){
        sharePreferencesHelper.deleteSharedPreference()
    }

    private fun returnToLogin(){
        val intent = Intent(context, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NO_HISTORY
        startActivity(intent)
    }

    interface OnInvoiceSettingClicked{
        fun onInvoiceSettingClicked()
    }
}