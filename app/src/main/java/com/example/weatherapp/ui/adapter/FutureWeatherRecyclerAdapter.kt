package com.example.weatherapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.weatherapp.R
import com.example.weatherapp.databinding.DailyItemBinding
import com.example.weatherapp.model.forecast.Forecastday

class FutureWeatherRecyclerAdapter(val context: Context, private val weather: List<Forecastday>) :
    RecyclerView.Adapter<FutureWeatherRecyclerAdapter.FutureWeatherViewHolder>() {

    class FutureWeatherViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = DailyItemBinding.bind(view)
    }

    override fun getItemCount(): Int = weather.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FutureWeatherViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.daily_item, parent, false)
        return FutureWeatherViewHolder(view)
    }

    override fun onBindViewHolder(holder: FutureWeatherViewHolder, position: Int) {
        val future = weather[position]
        holder.binding.apply {
            dailyInfoTextView.text = future.date
            dailyTemperatureTextView.text = future.day.avgtemp_c.toString() + "°"
            dailyWeatherImageView.load("https:" + future.day.condition.icon)
        }
    }
}
