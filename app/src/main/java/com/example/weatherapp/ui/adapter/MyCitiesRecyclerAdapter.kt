package com.example.weatherapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.weatherapp.R
import com.example.weatherapp.databinding.CardCityItemBinding
import com.example.weatherapp.model.CurrentResponse

class MyCitiesRecyclerAdapter(val context: Context, private val cities: List<CurrentResponse>) :
    RecyclerView.Adapter<MyCitiesRecyclerAdapter.MyCitiesViewHolder>() {

    class MyCitiesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = CardCityItemBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyCitiesViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.card_city_item, parent, false)
        return MyCitiesViewHolder(view)
    }

    override fun getItemCount(): Int = cities.size

    override fun onBindViewHolder(holder: MyCitiesViewHolder, position: Int) {
        val cityWithWeather = cities[position]
        holder.binding.apply {
            cityNameTextView.text = cityWithWeather.location.name
            cityTemperatureTextView.text = "${cityWithWeather.current.temp_c}°"
            weatherIconImageView.load("https:" + cityWithWeather.current.condition.icon)
            cityInfoTextView.text = cityWithWeather.location.localtime
            cityInfoDistanceTextView.text = "lat:${cityWithWeather.location.lat} lon:${cityWithWeather.location.lon}"
        }
    }
}
