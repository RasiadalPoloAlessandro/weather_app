package com.weater_app.weater_app.data.api.weatherApi.weather_data

data class WeatherData(

    val city : String,
    val temperature : String,
    val description : String,
    val humidity: String,
    val windSpeed: String,
    val country : String,
    val pressure: String,
    val visibility: String,
    val timestamp: Long = System.currentTimeMillis()
) {


}