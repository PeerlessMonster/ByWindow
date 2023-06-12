package com.demo.bywindow.logic.network

import com.demo.bywindow.logic.model.DayResponse
import com.demo.bywindow.logic.model.WeekResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("free/day?appid=${ServiceCreator.APP_ID}&appsecret=${ServiceCreator.APP_SECRET}&unescape=1")
    fun getDayWeather(@Query("city") placeName: String): Call<DayResponse>

    @GET("free/week?appid=${ServiceCreator.APP_ID}&appsecret=${ServiceCreator.APP_SECRET}&unescape=1")
    fun getWeekWeather(@Query("city") placeName: String): Call<WeekResponse>

}