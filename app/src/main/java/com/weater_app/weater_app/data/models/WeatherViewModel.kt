package com.weater_app.weater_app.data.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weater_app.weater_app.data.api.weatherApi.Constant
import com.weater_app.weater_app.data.api.NetWorkResponse
import com.weater_app.weater_app.data.api.weatherApi.RetrofitInstance
import com.weater_app.weater_app.data.api.weatherApi.weather_data.WeatherModel
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {


    private val weatherApi = RetrofitInstance.weatherApi

    private val _weatherResult = MutableLiveData<NetWorkResponse<WeatherModel>>()
    val weatherResult : LiveData<NetWorkResponse<WeatherModel>> = _weatherResult

    fun getData(city : String){
        _weatherResult.value = NetWorkResponse.Loading

        viewModelScope.launch {
            try{
                val response = weatherApi.getCurrentWeather(city, Constant.apikey)
                if(response.isSuccessful)
                    response.body()?.let {
                        _weatherResult.value = NetWorkResponse.success(it)
                    }
                else
                    _weatherResult.value = NetWorkResponse.Error("Failed to load data")
            }catch (e : Exception) {
                _weatherResult.value = NetWorkResponse.Error("Failed to load data")
            }

        }
    }
}