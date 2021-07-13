package com.example.triples

import android.os.Handler
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.triples.Adapters.OffersAdapter


class OffersViewPagerHelper constructor(var viewPager : ViewPager2) {
     var sliderHandler  = Handler()
     var sliderRunnable : Runnable = Runnable {
         viewPager.setCurrentItem(viewPager.currentItem + 1)
     }
    fun setupViewPager(offersAdapter: OffersAdapter){
        viewPager.adapter = offersAdapter
        setupAnimation()
        autoSliding()
    }
    fun setupAnimation(){
        viewPager.clipToPadding = false
        viewPager.clipChildren = false
        viewPager.offscreenPageLimit = 3
        viewPager.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        var compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(40))
        compositePageTransformer.addTransformer(){ view: View, fl: Float ->
            var r :Float = Math.abs(fl)
            view.scaleY = 0.85f + r * 0.15f
        }
        viewPager.setPageTransformer(compositePageTransformer)
    }

    fun autoSliding(){
        viewPager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                sliderHandler.removeCallbacks(sliderRunnable)
                sliderHandler.postDelayed(sliderRunnable ,4500 )
            }
        })
    }
}