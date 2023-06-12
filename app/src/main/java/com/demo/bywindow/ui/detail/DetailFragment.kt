package com.demo.bywindow.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.demo.bywindow.MainViewModel
import com.demo.bywindow.databinding.FragmentDetailBinding
import com.demo.bywindow.logic.Repository
import com.demo.bywindow.logic.model.DayResponse
import com.demo.bywindow.logic.model.WeatherAppearance

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)

        viewModel.weatherLiveData.observe(viewLifecycleOwner) { result ->
            val weather = result.getOrNull()
            if (weather != null) {
                showWeatherInfo(weather.day)
            }
        }
        if (Repository.isWeatherSaved()) {
            val dayWeather = Repository.getDayWeather()
            showWeatherInfo(dayWeather)
        }

        return binding.root
    }

    private fun showWeatherInfo(weather: DayResponse) = with(binding) {
        val airQuality = weather.air
        val windPosition = weather.wind
        val windSpeed = weather.windSpeed
        val windStrong = weather.windStrong
        val humidity = weather.humidity
        val pressure = weather.pressure

        airQualityFigure.text = airQuality

        val airQualitySignConfig = WeatherAppearance.getAirQualitySignConfig(airQuality.toInt())
        airQualitySign.text = airQualitySignConfig.text
        airQualitySign.setTextColor(airQualitySignConfig.textColorId)
        airQualitySign.setBackgroundResource(airQualitySignConfig.backgroundColorId)

        windPostionText.text = windPosition
        windSpeedText.text = windSpeed
        windStrongText.text = windStrong

        val windSpeedFigure = windSpeed.run {
            substring(0, length - 1)
        }
        windSpeedPgb.progress = windSpeedFigure.toInt()
        val windStrongFigure = windStrong.run {
            substring(0, length - 4)
        }
        windSpeedPgb.progress = windStrongFigure.toInt()

        humidityText.text = humidity
        val humidityFigure = humidity.run {
            substring(0, length - 1)
        }
        humidityPgb.progress = humidityFigure.toInt()

        pressureText.text = "${pressure}mPa"
        pressurePgb.progress = pressure
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}