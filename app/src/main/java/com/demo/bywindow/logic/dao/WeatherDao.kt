package com.demo.bywindow.logic.dao

import android.content.Context
import androidx.core.content.edit
import com.demo.bywindow.ByWindowApp
import com.demo.bywindow.R
import com.demo.bywindow.logic.model.DayResponse
import com.demo.bywindow.logic.model.Weather
import com.demo.bywindow.logic.model.WeekResponse
import com.google.gson.Gson

object WeatherDao {

    private val storeName = ByWindowApp.context.getString(R.string.store_name)
    private val dayWeatherName = ByWindowApp.context.getString(R.string.day_weather)
    private val weekWeatherName = ByWindowApp.context.getString(R.string.week_weather)

    private fun sharedPreferences() =
        ByWindowApp.context.getSharedPreferences(storeName, Context.MODE_PRIVATE)

    fun saveWeather(weather: Weather) {
        val dayWeather = weather.day
        val weekWeather = weather.week
        sharedPreferences().edit {
            putString(dayWeatherName, Gson().toJson(dayWeather))
            putString(weekWeatherName, Gson().toJson(weekWeather))
        }
    }

    fun getDayWeather(): DayResponse {
        val json = sharedPreferences().getString(dayWeatherName, "")
        return Gson().fromJson(json, DayResponse::class.java)
    }

    fun getWeekWeather(): WeekResponse {
        val json = sharedPreferences().getString(weekWeatherName, "")
        return Gson().fromJson(json, WeekResponse::class.java)
    }

    fun isWeatherSaved() = with(sharedPreferences()) {
        contains(dayWeatherName) && contains(weekWeatherName)
    }

}