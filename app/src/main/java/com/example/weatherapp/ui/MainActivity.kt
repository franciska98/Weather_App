package com.example.weatherapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.ui.adapter.PageAdapter
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewPager.adapter = PageAdapter(this)
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, index ->
            tab.icon = when (index) {
                0 -> { getDrawable(R.drawable.icons_android_ic_search) }
                1 -> { getDrawable(R.drawable.icons_android_ic_star_zero) }
                2 -> { getDrawable(R.drawable.icons_android_ic_settings) }
                else -> { getDrawable(R.drawable.ic_launcher_background) }
            }
        }.attach()
    }
}
