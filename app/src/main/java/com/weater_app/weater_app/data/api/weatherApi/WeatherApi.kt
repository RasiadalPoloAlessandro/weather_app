package com.weater_app.weater_app.data.api.weatherApi

import com.weater_app.weater_app.data.api.weatherApi.city_data.CityResponse
import com.weater_app.weater_app.data.models.WeatherModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("/data/2.5/forecast")
    suspend fun getCurrentWeather(
        @Query("q") cityName: String,
    ): Response<WeatherModel>


    @GET("/data/2.5/forecast")
    suspend fun getCurrentWeatherByCoord(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
    ): Response<WeatherModel>

    @GET("geo/1.0/direct")
    suspend fun getCityInformation(
        @Query("q") query: String
    ): Response<List<CityResponse>>
}