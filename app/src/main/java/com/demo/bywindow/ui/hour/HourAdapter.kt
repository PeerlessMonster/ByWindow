package com.demo.bywindow.ui.hour

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.demo.bywindow.R
import com.demo.bywindow.logic.model.HourResponse
import com.demo.bywindow.logic.model.WeatherAppearance

class HourAdapter(private val hourWeatherList: List<HourResponse.Data>) : RecyclerView.Adapter<HourAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val timeText: TextView = view.findViewById(R.id.hour_time)
        val weatherIcon: ImageView = view.findViewById(R.id.hour_weather_pic)
        val weatherText: TextView = view.findViewById(R.id.hour_weather)
        val temperText: TextView = view.findViewById(R.id.hour_temper)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.hour_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val hourWeather = hourWeatherList[position]

        holder.timeText.text = hourWeather.time

        val weatherImgId = WeatherAppearance.getImgId(hourWeather.weatherImg)
        holder.weatherIcon.setImageResource(weatherImgId)

        holder.weatherText.text = hourWeather.weather

        holder.temperText.text = "${hourWeather.temperature}℃"
    }

    override fun getItemCount() = hourWeatherList.size

}