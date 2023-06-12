package com.demo.bywindow

import android.app.Activity
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Vibrator
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import android.widget.VideoView
import androidx.appcompat.app.AlertDialog
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.demo.bywindow.databinding.ActivityMainBinding
import com.demo.bywindow.databinding.TitleBinding
import com.demo.bywindow.logic.Repository
import com.demo.bywindow.logic.model.DayResponse
import com.demo.bywindow.logic.model.MiniWeather
import com.demo.bywindow.logic.model.WeatherAppearance
import com.demo.bywindow.ui.TitleLayout
import com.demo.bywindow.ui.WeatherService
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var backgroundWeather: VideoView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    private lateinit var manager: NotificationManager

    private val viewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    companion object {
        private lateinit var rootView: DrawerLayout
        // 外部控制打开抽屉
        fun openDrawer() {
            rootView.openDrawer(GravityCompat.START)
        }

        private lateinit var instance: MainActivity
        // 外部关闭此Activity
        fun close() = instance.finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        instance = this
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 设置状态栏字体为亮色
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        // 隐藏原生ActionBar
        supportActionBar?.hide()
        // 设置下拉刷新进度条颜色
        swipeRefreshLayout = binding.mainFrame
        swipeRefreshLayout.setColorSchemeResources(R.color.purple_700)
        // 定义一个震动管理器
        val vibrator = this.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        backgroundWeather = binding.backgroundWeather

        viewModel.weatherLiveData.observe(this) { result ->
            swipeRefreshLayout.isRefreshing = false

            val weather = result.getOrNull()
            if (weather != null) {
                vibrator.vibrate(100)

                showWeatherInfo(weather.day)
            } else {
                vibrator.vibrate(500)
                if (Repository.isWeatherSaved()) {
                    Toast.makeText(this, "无法成功获取天气信息", Toast.LENGTH_SHORT).show()
                } else {
                    AlertDialog.Builder(this).apply {
                        setTitle("无网络连接")
                        setMessage("请连接网络以查看天气！")
                        setCancelable(false)
                        setPositiveButton("退出") { _, _ ->
                            finish()
                        }
                        show()
                    }
                }

                result.exceptionOrNull()?.printStackTrace()
            }
        }

        if (!Repository.isWeatherSaved()) {
            swipeRefreshLayout.isRefreshing = true

            viewModel.refreshWeather("")
        } else {
            val dayWeather = Repository.getDayWeather()
            showWeatherInfo(dayWeather)
        }
        initBottomNavigation()
        initDrawerLayout()
    }

    private fun showWeatherInfo(dayWeather: DayResponse) {
        val placeName = findViewById<TextView>(R.id.placeName)
        placeName.text = dayWeather.city
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.refreshWeather(dayWeather.city)
        }

        val updateInfo = findViewById<TextView>(R.id.updateInfo)
        updateInfo.text = with(dayWeather) {
            "$updateDate $updateTime 更新"
        }

        val backgroundVideoId = WeatherAppearance.getVideoId(dayWeather.weatherImg)
        showBackgroundVideo(backgroundVideoId)

        val weatherImgId = WeatherAppearance.getImgId(dayWeather.weatherImg)
        val weatherInfo = with(dayWeather) {
            "$weather   ${temperatureNight}℃~${temperatureDay}℃"
        }
        showNotification(
            MiniWeather(dayWeather.city, dayWeather.weather, weatherImgId, weatherInfo)
        )
    }

    private fun showBackgroundVideo(id: Int) {
        val uri = Uri.parse("android.resource://${packageName}/$id")
        backgroundWeather.run {
            setVideoURI(uri)
            setOnPreparedListener {
                it.run {
                    setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING)
                    isLooping = true
                }
            }
        }
    }

    private fun showNotification(weather: MiniWeather) {
        stopShowNotification()
        val intent = Intent(this, WeatherService::class.java).apply {
            putExtra("weather", weather)
        }
        startService(intent)
    }

    private fun initBottomNavigation() {
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_detail, R.id.navigation_home, R.id.navigation_week, R.id.navigation_hour
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    private fun initDrawerLayout() {
        rootView = binding.rootView

        binding.settingView.setNavigationItemSelectedListener(object : NavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when (item.itemId) {
                    R.id.refresh -> {
                        val choiceList = arrayOf(
                            "每30分钟刷新",
                            "每1小时刷新",
                            "每2小时刷新",
                            "每3小时刷新",
                            "每4小时刷新",
                            "每6小时刷新",
                            "每8小时刷新",
                            "每12小时刷新",
                            "每24小时刷新"
                        )
                        AlertDialog.Builder(this@MainActivity).apply {
                            setTitle("立即刷新？主界面下拉即可")
                            setSingleChoiceItems(choiceList, -1) { _, _ -> }
                            setPositiveButton("确认") { _, _ -> }
                            setNegativeButton("取消") { _, _ -> }
                            show()
                        }
                    }
                    R.id.changeBackground -> {
                        val choiceList = arrayOf(
                            "动态天气背景",
                            "静态壁纸背景",
                            "自定义"
                        )
                        val dialog = AlertDialog.Builder(this@MainActivity).run {
                            setSingleChoiceItems(choiceList, -1) { dialog, which ->
                                when (which) {
                                    0 -> {}
                                    1 -> {}
                                    2 -> {
                                        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
                                        intent.addCategory(Intent.CATEGORY_OPENABLE)
                                        intent.type = "image/*"
                                        startActivityForResult(intent, FROM_ALBUM)
                                    }
                                }
                                dialog?.cancel()
                            }
                            show()
                        }
                    }
                }
                rootView.closeDrawers()
                return true
            }
        })
    }

    private val FROM_ALBUM = 1
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            FROM_ALBUM -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    data.data?.let { uri ->
                        val bitmap = getBitmapFromUri(uri)

                        val backgroundWeather = binding.backgroundWeather
                        val backgroundCustom = binding.backgroundCustom
                        backgroundWeather.visibility = View.GONE
                        backgroundCustom.visibility = View.VISIBLE
                        backgroundCustom.setImageBitmap(bitmap)
                    }
                }
            }
        }
    }

    private fun getBitmapFromUri(uri: Uri) =
        contentResolver.openFileDescriptor(uri, "r")?.use {
        BitmapFactory.decodeFileDescriptor(it.fileDescriptor)
    }

    override fun onResume() {
        super.onResume()
        backgroundWeather.start()
    }

    override fun onPause() {
        super.onPause()
        backgroundWeather.pause()
    }

    override fun onDestroy() {
        super.onDestroy()

        backgroundWeather.suspend()
        stopShowNotification()
    }

    private fun stopShowNotification() {
        val intent = Intent(this, WeatherService::class.java)
        stopService(intent)
    }
}