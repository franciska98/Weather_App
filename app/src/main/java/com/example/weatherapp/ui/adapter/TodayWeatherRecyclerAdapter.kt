package com.example.weatherapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.weatherapp.R
import com.example.weatherapp.databinding.DailyItemBinding
import com.example.weatherapp.model.forecast.Hour

class TodayWeatherRecyclerAdapter(val context: Context, private val weather: List<Hour>) :
    RecyclerView.Adapter<TodayWeatherRecyclerAdapter.TodayWeatherViewHolder>() {

    class TodayWeatherViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = DailyItemBinding.bind(view)
    }

    override fun getItemCount(): Int = weather.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodayWeatherViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.daily_item, parent, false)
        return TodayWeatherViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodayWeatherViewHolder, position: Int) {
        val hour = weather[position]
        holder.binding.apply {
            if (itemCount > 7) {
                dailyInfoTextView.text = hour.getHourTime()
                dailyTemperatureTextView.text = "${hour.temp_c}°"
                dailyWeatherImageView.load("https:" + hour.condition.icon)
            }
        }
    }
}
