package com.demo.bywindow.logic.model

data class HourResponse(val data: List<Data>) {
    data class Data(
        val time: String,
        val weather: String,
        val weatherImg: String,
        val temperature: String
    )
}