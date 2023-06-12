package com.demo.bywindow.logic.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceCreator {

    private const val BASE_URL = "https://www.yiketianqi.com/"

    const val APP_ID = "41253714"
    const val APP_SECRET = "M3iDgMHH"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)
    inline fun <reified T> create(): T = create(T::class.java)

}