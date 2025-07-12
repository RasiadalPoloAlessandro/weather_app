package com.weater_app.weater_app.data.api

//T refers to WeatherModel (I prefer to use generic type in case of other implementations)
sealed class NetWorkResponse<out T> {

    data class success<out T>(val data: T) : NetWorkResponse<T>()
    data class Error(val message : String) : NetWorkResponse<Nothing>()
    object Loading : NetWorkResponse<Nothing>()
}