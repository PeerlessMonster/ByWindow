package com.demo.bywindow.logic.model

import com.google.gson.annotations.SerializedName

data class WeekResponse(val data: List<Data>) {
    data class Data(
        val date: String,
        @SerializedName("wea") val weather: String,
        @SerializedName("wea_img") val weatherImg: String,
        @SerializedName("tem_day") val temperatureDay: String,
        @SerializedName("tem_night") val temperatureNight: String,
        @SerializedName("win") val wind: String,
        @SerializedName("win_speed") val windSpeed: String,
    )
}