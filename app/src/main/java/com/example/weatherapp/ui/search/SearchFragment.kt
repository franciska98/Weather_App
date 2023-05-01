package com.example.weatherapp.ui.search

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.weatherapp.databinding.FragmentSearchBinding
import com.example.weatherapp.model.forecast.Location
import com.example.weatherapp.ui.adapter.MyCitiesRecyclerAdapter
import com.example.weatherapp.ui.viewModel.CityViewModel
import dagger.hilt.android.AndroidEntryPoint

const val CITY_EXTRA = "city"

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private val viewModel: CityViewModel by activityViewModels()
    private var _cityName = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        binding.searchInputFields.citySearchView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.length > 2) viewModel.getAllCities(s.toString())
            }
        })

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

        binding.searchInputFields.citySearchView.setOnItemClickListener { parent, view, position, id ->
            val selectedCity = parent.getItemAtPosition(position) as String
            val split: List<String> = selectedCity.split(", ")
            _cityName = split[0]
            val intent = Intent(requireContext(), CityItemActivity::class.java)
            intent.putExtra(CITY_EXTRA, _cityName)
            startActivity(intent)
        }

        viewModel.getLocationWithWeather(requireContext())
        viewModel.locationWithWeather.observe(viewLifecycleOwner) { citiesWithWeather ->
            val adapter = MyCitiesRecyclerAdapter(
                requireContext(),
                citiesWithWeather,
                onStarClick = ::onAdapterClicked,
            )
            binding.historyRecyclerView.adapter = adapter
        }

        return binding.root
    }

    private fun onAdapterClicked(location: Location) {
        viewModel.deleteLocationFromDb(requireContext(), location)
    }
}
