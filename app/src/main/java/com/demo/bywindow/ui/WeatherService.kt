package com.demo.bywindow.ui

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.demo.bywindow.MainActivity
import com.demo.bywindow.R
import com.demo.bywindow.logic.model.MiniWeather

class WeatherService : Service() {

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val weather = intent.getSerializableExtra("weather") as MiniWeather

        val clickIntent = Intent(this, MainActivity::class.java)
        val pi = PendingIntent.getActivity(this, 0, clickIntent, PendingIntent.FLAG_IMMUTABLE)
        val notification = NotificationCompat.Builder(this, "weather_service")
            .setContentTitle(weather.placeName)
            .setContentText(weather.weatherInfo)
            .setSmallIcon(R.drawable.icon)
            .setLargeIcon(BitmapFactory.decodeResource(resources, weather.weatherImgId))
            .setContentIntent(pi)
            .build()
        startForeground(1, notification)

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onCreate() {
        super.onCreate()

        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("weather_service", "通知栏显示天气信息", NotificationManager.IMPORTANCE_DEFAULT)
            manager.createNotificationChannel(channel)
        }
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

}