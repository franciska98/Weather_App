package com.example.weatherapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.weatherapp.databinding.FragmentSearchBinding
import com.example.weatherapp.ui.viewModel.CityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private val viewModel: CityViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        viewModel.cities.observe(
            viewLifecycleOwner,
        ) { cities ->
            val cityList = cities.map { it.toString() }
            binding.searchInputFields.citySearchView
                .setAdapter(
                    ArrayAdapter(
                        requireContext(),
                        com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
                        cityList,
                    ),
                )
        }

        return binding.root
    }
}
