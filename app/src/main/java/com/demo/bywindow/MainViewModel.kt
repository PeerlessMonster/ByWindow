package com.demo.bywindow

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.demo.bywindow.logic.Repository

class MainViewModel : ViewModel() {

    private val placeLiveData = MutableLiveData<String>()

    val weatherLiveData = Transformations.switchMap(placeLiveData) { placeName ->
        Repository.refreshWeather(placeName)
    }

    fun refreshWeather(placeName: String) {
        placeLiveData.value = placeName
    }

}