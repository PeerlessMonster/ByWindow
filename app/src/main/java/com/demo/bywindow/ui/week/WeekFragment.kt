package com.demo.bywindow.ui.week

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.demo.bywindow.MainViewModel
import com.demo.bywindow.R
import com.demo.bywindow.databinding.FragmentWeekBinding
import com.demo.bywindow.logic.Repository
import com.demo.bywindow.logic.model.WeekResponse

class WeekFragment : Fragment() {

    private var _binding: FragmentWeekBinding? = null
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
        _binding = FragmentWeekBinding.inflate(inflater, container, false)

        viewModel.weatherLiveData.observe(viewLifecycleOwner) { result ->
            val weather = result.getOrNull()
            if (weather != null) {
                showWeatherInfo(weather.week)
            }
        }
        if (Repository.isWeatherSaved()) {
            val weekWeather = Repository.getWeekWeather()
            showWeatherInfo(weekWeather)
        }

        return binding.root
    }

    private fun showWeatherInfo(weather: WeekResponse) {
        val layoutManager = LinearLayoutManager(this.context)
        val weekView = binding.weekView
        weekView.layoutManager = layoutManager
        val adapter = WeekAdapter(weather.data)
        weekView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}