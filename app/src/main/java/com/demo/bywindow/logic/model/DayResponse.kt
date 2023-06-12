package com.demo.bywindow.logic.model

import com.google.gson.annotations.SerializedName

data class DayResponse(
    val city: String,
    @SerializedName("wea") val weather: String,
    @SerializedName("wea_img") val weatherImg: String,
    @SerializedName("tem") val temperature: String,
    @SerializedName("tem_day") val temperatureDay: String,
    @SerializedName("tem_night") val temperatureNight: String,
    @SerializedName("win") val wind: String,
    @SerializedName("win_speed") val windSpeed: String,
    @SerializedName("win_meter") val windStrong: String,
    val air: String,
    val pressure: Int,
    val humidity: String,

    @SerializedName("date") val updateDate: String,
    @SerializedName("update_time") val updateTime: String,
    @SerializedName("nums") val numberRequested: Int
)