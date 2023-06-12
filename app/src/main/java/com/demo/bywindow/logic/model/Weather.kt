package com.demo.bywindow.logic.model

import java.io.Serializable

data class Weather(val day: DayResponse, val week: WeekResponse)
// 包装同时请求的DayResponse、WeekResponse

data class MiniWeather(
    val placeName: String,
    val weather: String,
    val weatherImgId: Int,
    val weatherInfo: String
    ) : Serializable
// 包装启动前台Service时传递