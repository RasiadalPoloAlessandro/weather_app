package com.weater_app.weater_app.data.api

import com.weater_app.weater_app.BuildConfig
import com.weater_app.weater_app.data.api.weatherApi.CityViewModelFactory
import com.weater_app.weater_app.data.api.weatherApi.WeatherApi
import com.weater_app.weater_app.data.api.weatherApi.WeatherViewModelFactory
import com.weater_app.weater_app.data.cache.GetCity
import com.weater_app.weater_app.data.cache.GetWeatherCase
import com.weater_app.weater_app.data.cache.WeatherCacheManager
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CityDependency {
    private const val baseUrl = "https://api.openweathermap.org"

    private fun getInstance(): Retrofit {
        val apiKeyInterceptor = Interceptor { chain ->
            val original = chain.request()

            val cityUrl = original.url.newBuilder()
                .addQueryParameter("limit", "10")
                .addQueryParameter("appid", BuildConfig.API_KEY)
                .build()

            val cityRequest = original.newBuilder()
                .url(cityUrl)
                .build()

            chain.proceed(cityRequest)
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(apiKeyInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val weatherApi : WeatherApi = getInstance().create(WeatherApi::class.java)

    private val getCity : GetCity by lazy {
        GetCity(weatherApi)
    }

    fun provideCityViewModelFactory(): CityViewModelFactory{
        return CityViewModelFactory(getCity)
    }
}