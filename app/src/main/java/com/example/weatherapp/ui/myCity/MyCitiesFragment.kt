package com.example.weatherapp.ui.myCity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.weatherapp.databinding.FragmentMyCitiesBinding
import com.example.weatherapp.model.forecast.Location
import com.example.weatherapp.ui.adapter.MyCitiesRecyclerAdapter
import com.example.weatherapp.ui.viewModel.ForecastViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyCitiesFragment : Fragment() {
    private lateinit var binding: FragmentMyCitiesBinding
    private val viewModel: ForecastViewModel by activityViewModels()
    lateinit var adapter: MyCitiesRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentMyCitiesBinding.inflate(inflater, container, false)

        viewModel.getLocationWithWeather(requireContext())

        viewModel.locationWithWeather.observe(viewLifecycleOwner) { myCitiesWithWeather ->
            adapter = MyCitiesRecyclerAdapter(
                requireContext(),
                myCitiesWithWeather,
                onStarClick = ::onAdapterClicked,
            )
            binding.myCitiesRecyclerView.adapter = adapter
        }

        return binding.root
    }

    private fun onAdapterClicked(location: Location) {
        viewModel.deleteLocationFromDb(requireContext(), location)
    }
}
