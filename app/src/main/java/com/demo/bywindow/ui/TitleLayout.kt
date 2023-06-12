package com.demo.bywindow.ui

import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageButton
import android.widget.LinearLayout
import com.demo.bywindow.MainActivity
import com.demo.bywindow.R
import com.demo.bywindow.ui.settings.LocationActivity

class TitleLayout(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    init {
        LayoutInflater.from(context).inflate(R.layout.title, this)

        val locationBtn = findViewById<ImageButton>(R.id.location)
        locationBtn.setOnClickListener {
            val intent = Intent(context, LocationActivity::class.java)
            context.startActivity(intent)
        }
        val settingBtn = findViewById<ImageButton>(R.id.settings)
        settingBtn.setOnClickListener {
            MainActivity.openDrawer()
        }
    }

}