package com.demo.bywindow.ui.settings

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Vibrator
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.demo.bywindow.ByWindowApp
import com.demo.bywindow.MainActivity
import com.demo.bywindow.MainViewModel
import com.demo.bywindow.R
import com.demo.bywindow.databinding.ActivityLocationBinding
import com.demo.bywindow.logic.Repository
import com.demo.bywindow.logic.model.CityResponse

class LocationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLocationBinding

    private val viewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 定义一个震动管理器
        val vibrator = this.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        viewModel.weatherLiveData.observe(this) { result ->
            val weather = result.getOrNull()
            if (weather != null) {
                vibrator.vibrate(100)
                Toast.makeText(this, "天气信息已切换至【${weather.day.city}】", Toast.LENGTH_SHORT).show()

                MainActivity.close()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                vibrator.vibrate(500)
                AlertDialog.Builder(this).apply {
                    setTitle("无法成功获取天气信息")
                    setMessage("请检查网络连接，并输入正确的城市名！")
                    setCancelable(true)
                    setPositiveButton("重试") { _, _ -> }
                    show()
                }

                result.exceptionOrNull()?.printStackTrace()
            }
        }

        binding.citySubmit.setOnClickListener {
            val city = binding.cityInput.text.toString()
            viewModel.refreshWeather(city)
        }

        val hotCityList = Repository.generateHotCities()
        showHotCities(hotCityList)
        binding.back.setOnClickListener {
            finish()
        }
    }

    private fun showHotCities(hotCityList: CityResponse) {
        val cityView = binding.cityView
        val layoutManager = StaggeredGridLayoutManager(6, StaggeredGridLayoutManager.VERTICAL)
        cityView.layoutManager = layoutManager
        val adapter = CityAdapter(hotCityList.data) { city ->
            viewModel.refreshWeather(city)
        }
        cityView.adapter = adapter
    }
}