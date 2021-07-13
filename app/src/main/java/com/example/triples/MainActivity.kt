package com.example.triples

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.example.triples.DataContainers.InvoiceItems
import com.example.triples.DataContainers.Products
import com.example.triples.DataContainers.ProductsItem
import com.example.triples.Fragments.*
import com.example.triples.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() , MenuFragment.OnProductMenuClick
    ,InvoiceFragment.OnItemInvoiceClickedFragmentReplace ,SettingFragment.OnInvoiceSettingClicked{
    private lateinit var binding : ActivityMainBinding
    private lateinit var bottomNavigation : MeowBottomNavigation
    private lateinit var fragment : Fragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        setupMeowNavigationBar()

    }
    private fun init(){
        fragment = MenuFragment(this)
        replaceFrameFragment()
    }

    private fun setupMeowNavigationBar(){
        bottomNavigation = binding.mainMeowNavigationBar
        bottomNavigation.add(MeowBottomNavigation.Model(1 ,R.drawable.home_icon))
        bottomNavigation.add(MeowBottomNavigation.Model(2 ,R.drawable.cart_icon))
        bottomNavigation.add(MeowBottomNavigation.Model(3 ,R.drawable.invoice_ic))
        bottomNavigation.add(MeowBottomNavigation.Model(4 ,R.drawable.setting_icon))
        bottomNavigation.setOnClickMenuListener {
            when(it.id){
                1 -> fragment = MenuFragment(this)
                2 -> fragment = CartFragment()
                3 -> fragment = InvoiceFragment(this)
                4 -> fragment = SettingFragment(this)
                else -> fragment = ErrorFragment()
            }
            replaceFrameFragment()
        }

    }

    private fun replaceFrameFragment(){
        supportFragmentManager
            .beginTransaction()
            .replace(binding.mainFrameLayout.id, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onProductMenuClicked(product: ProductsItem) {
        fragment = ProductFragment(product)
        replaceFrameFragment()
    }

    override fun fragmentReplace(items: MutableList<InvoiceItems>) {
        fragment = InvoiceItemFragment(items)
        replaceFrameFragment()
    }

    override fun onInvoiceSettingClicked() {
        fragment = InvoiceFragment(this )
        replaceFrameFragment()
    }
}