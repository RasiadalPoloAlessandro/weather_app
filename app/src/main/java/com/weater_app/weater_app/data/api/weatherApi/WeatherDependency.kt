package com.weater_app.weater_app.data.api.weatherApi

import com.weater_app.weater_app.BuildConfig
import com.weater_app.weater_app.data.cache.GetWeatherCase
import com.weater_app.weater_app.data.cache.WeatherCacheManager
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object WeatherDependency {
    private const val baseUrl = "https://api.openweathermap.org/data/2.5/"

    private fun getInstance(): Retrofit {
        val apiKeyInterceptor = Interceptor { chain ->
            val original = chain.request()
            val url = original.url.newBuilder()
                .addQueryParameter("units", "metric")
                .addQueryParameter("lang", "it")
                .addQueryParameter("appid", BuildConfig.API_KEY)
                .build()

            val request = original.newBuilder()
                .url(url)
                .build()

            chain.proceed(request)
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

    private val cacheManager: WeatherCacheManager by lazy {
        WeatherCacheManager()
    }

    private val getWeatherUseCase: GetWeatherCase by lazy {
        GetWeatherCase(weatherApi, cacheManager)
    }

    fun provideWeatherViewModelFactory(): WeatherViewModelFactory {
        return WeatherViewModelFactory(getWeatherUseCase)
    }
}