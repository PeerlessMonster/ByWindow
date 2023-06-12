package com.demo.bywindow.logic

import android.util.Log
import androidx.lifecycle.liveData
import com.demo.bywindow.logic.dao.WeatherDao
import com.demo.bywindow.logic.model.CityResponse
import com.demo.bywindow.logic.model.HourResponse
import com.demo.bywindow.logic.model.Weather
import com.demo.bywindow.logic.network.WeatherNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.util.Date
import kotlin.coroutines.CoroutineContext

object Repository {

    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData<Result<T>>(context) {
            val result = try {
                block()
            } catch (e: Exception) {
                Result.failure<T>(e)
            }
            emit(result)
        }

    fun refreshWeather(placeName: String) = fire(Dispatchers.IO) {
        coroutineScope {
            val deferredDay = async {
                WeatherNetwork.getDayWeather(placeName)
            }
            val deferredWeek = async {
                WeatherNetwork.getWeekWeather(placeName)
            }
            val dayResponse = deferredDay.await()
            val weekResponse = deferredWeek.await()
            val weather = Weather(dayResponse, weekResponse)

            WeatherDao.saveWeather(weather)
            Log.d("Weather", "requested number: ${dayResponse.numberRequested}")

            Result.success(weather)
        }
    }

    /*
     * 由于天气api不提供“免费基础小时天气”，故在此生成数据仅供展示“小时”页面
     */
    fun generateHourWeather(): HourResponse {
        val now = Date()
        val nowHour = now.hours

        val data = ArrayList<HourResponse.Data>()
        for (i in 0 until 12) {
            val hour = (nowHour + i) % 24
            val time = "${String.format("%02d", hour)}:00"
            val weather = "阴"
            val weatherImg = "yin"
            val temperature = (25..30).random().toString()
            data.add(HourResponse.Data(time, weather, weatherImg, temperature))
        }

        return HourResponse(data)
    }

    /*
     * 搜索天气的热门城市
     */
    fun generateHotCities(): CityResponse {
        val data = listOf(
            "北京", "天津", "上海", "重庆", "广州", "深圳", "南京", "杭州", "福州", "长沙", "武汉", "海口",
            "成都", "西安", "厦门", "苏州"
        )
        return CityResponse(data)
    }

    fun getDayWeather() = WeatherDao.getDayWeather()

    fun getWeekWeather() = WeatherDao.getWeekWeather()

    fun isWeatherSaved() = WeatherDao.isWeatherSaved()

}