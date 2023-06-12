package com.demo.bywindow.ui.settings

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.demo.bywindow.MainViewModel
import com.demo.bywindow.R
import com.demo.bywindow.logic.model.CityResponse

class CityAdapter(val hotCityList: List<String>, val callback: (String) -> Unit) : RecyclerView.Adapter<CityAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val citySelectBtn: Button = view.findViewById(R.id.city_select)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.city_item, parent, false)
        val holder = ViewHolder(view)

        holder.citySelectBtn.setOnClickListener {
            val position = holder.adapterPosition
            val selectCity = hotCityList[position]
            callback(selectCity)
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val hotCity = hotCityList[position]
        holder.citySelectBtn.text = hotCity

    }

    override fun getItemCount() = hotCityList.size

}