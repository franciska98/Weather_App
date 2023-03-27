package com.example.weatherapp.ui.adapter

import android.content.res.Resources
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.weatherapp.MyCitiesFragment
import com.example.weatherapp.SearchFragment
import com.example.weatherapp.SettingsFragment

class PageAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> { SearchFragment() }
            1 -> { MyCitiesFragment() }
            2 -> { SettingsFragment() }
            else -> { throw Resources.NotFoundException("Position not found") }
        }
    }
}
