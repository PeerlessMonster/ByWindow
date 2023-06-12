package com.demo.bywindow.ui.hour

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.demo.bywindow.R
import com.demo.bywindow.databinding.FragmentHourBinding
import com.demo.bywindow.logic.Repository
import com.demo.bywindow.logic.model.HourResponse
import com.demo.bywindow.logic.model.Weather

class HourFragment : Fragment() {

    private var _binding: FragmentHourBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHourBinding.inflate(inflater, container, false)

        val hourResponse = Repository.generateHourWeather()
        showWeatherInfo(hourResponse)

        return binding.root
    }

    private fun showWeatherInfo(weather: HourResponse) {
        val layoutManager = LinearLayoutManager(this.context)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        val hourView = binding.hourView
        hourView.layoutManager = layoutManager
        val adapter = HourAdapter(weather.data)
        hourView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}