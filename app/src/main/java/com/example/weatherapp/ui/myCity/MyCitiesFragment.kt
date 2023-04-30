package com.example.weatherapp.ui.myCity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.weatherapp.databinding.FragmentMyCitiesBinding
import com.example.weatherapp.ui.adapter.MyCitiesRecyclerAdapter
import com.example.weatherapp.ui.viewModel.CityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyCitiesFragment : Fragment() {
    private lateinit var binding: FragmentMyCitiesBinding
    private val cityViewModel: CityViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentMyCitiesBinding.inflate(inflater, container, false)

        cityViewModel.getMyCitiesWithWeather(requireContext())

        cityViewModel.myCityWeather.observe(viewLifecycleOwner) { myCitiesWithWeather ->
            val adapter = MyCitiesRecyclerAdapter(requireContext(), myCitiesWithWeather)
            binding.myCitiesRecyclerView.adapter = adapter
        }

        return binding.root
    }
}
