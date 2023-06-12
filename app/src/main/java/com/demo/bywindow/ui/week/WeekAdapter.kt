package com.demo.bywindow.ui.week

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.demo.bywindow.R
import com.demo.bywindow.logic.model.WeatherAppearance
import com.demo.bywindow.logic.model.WeekResponse

class WeekAdapter(private val weekWeatherList: List<WeekResponse.Data>) : RecyclerView.Adapter<WeekAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val dateText: TextView = view.findViewById(R.id.week_date_text)
        val weatherIcon: ImageView = view.findViewById(R.id.week_weather_pic)
        val weatherText: TextView = view.findViewById(R.id.week_weather)
        val temperText: TextView = view.findViewById(R.id.week_temper_info)
        val windText: TextView = view.findViewById(R.id.week_wind)
        val windSpeedText: TextView = view.findViewById(R.id.week_wind_speed)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.week_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val weekWeather = weekWeatherList[position]

        holder.dateText.text = weekWeather.date

        val weatherIconId = WeatherAppearance.getImgId(weekWeather.weatherImg)
        holder.weatherIcon.setImageResource(weatherIconId)

        holder.weatherText.text = weekWeather.weather

        holder.temperText.text = with(weekWeather) {
            "${temperatureNight}℃~${temperatureDay}℃"
        }

        holder.windText.text = weekWeather.wind
        holder.windSpeedText.text = weekWeather.windSpeed
    }

    override fun getItemCount() = weekWeatherList.size

}