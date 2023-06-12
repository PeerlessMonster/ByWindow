package com.demo.bywindow.logic.model

import com.demo.bywindow.R

object WeatherAppearance {

    private val weaImg = mapOf(
        "xue" to R.drawable.ic_light_snow,
        "lei" to R.drawable.ic_thunder_shower,
        "shachen" to R.drawable.ic_moderate_haze,
        "wu" to R.drawable.ic_fog,
        "bingbao" to R.drawable.ic_sleet,
        "yun" to R.drawable.ic_cloudy,
        "yu" to R.drawable.ic_light_rain,
        "yin" to R.drawable.ic_partly_cloud_day,
        "qing" to R.drawable.ic_clear_day
    )
    fun getImgId(name: String) = weaImg[name]!!

    private val weaVideo = mapOf(
        "xue" to R.raw.snow,
        "lei" to R.raw.thunder,
        "shachen" to R.raw.sandstorm,
        "wu" to R.raw.fog,
        "bingbao" to R.raw.rain,
        "yun" to R.raw.cloud,
        "yu" to R.raw.rain,
        "yin" to R.raw.dark_cloud,
        "qing" to R.raw.sunny
    )
    fun getVideoId(name: String) = weaVideo[name]!!

    data class AirQuality(
        val text: String,
        val textColorId: Int,
        val backgroundColorId: Int
        )
    private val airQualitysign = arrayOf(
        AirQuality("优", R.color.white, R.color.green_standard),
        AirQuality("良", R.color.black, R.color.yellow_standard),
        AirQuality("轻度污染", R.color.white, R.color.orange_standard),
        AirQuality("中度污染", R.color.black, R.color.red_standard),
        AirQuality("重度污染",R.color.white, R.color.purple_standard),
        AirQuality("严重污染", R.color.white, R.color.brown_standard)
    )
    fun getAirQualitySignConfig(figure: Int): AirQuality {
        var i = figure / 50
        if (i > 5) {
            i = 5
        }
        return airQualitysign[i]
    }

}