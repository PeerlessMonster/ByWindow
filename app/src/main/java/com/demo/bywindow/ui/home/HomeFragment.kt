package com.demo.bywindow.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.demo.bywindow.databinding.FragmentHomeBinding
import com.demo.bywindow.logic.Repository
import com.demo.bywindow.logic.model.DayResponse
import com.demo.bywindow.logic.model.WeatherAppearance
import com.demo.bywindow.MainViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

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

    private fun showWeatherInfo(dayWeather: DayResponse) = with(binding) {
        temperMainText.text = dayWeather.temperature

        val weatherIconId = WeatherAppearance.getImgId(dayWeather.weatherImg)
        weatherIcon.setImageResource(weatherIconId)

        val weatherInfo = with(dayWeather) {
            "$weather   ${temperatureNight}℃~${temperatureDay}℃"
        }
        weatherInfoText.text = weatherInfo
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}