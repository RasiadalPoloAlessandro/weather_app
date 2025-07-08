package com.weater_app.weater_app.data.models

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.weater_app.weater_app.data.api.Constant
import com.weater_app.weater_app.data.api.RetrofitInstance
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {


    private val weatherApi = RetrofitInstance.weatherApi

    fun getData(city : String){


        viewModelScope.launch {
            val response = weatherApi.getCurrentWeather(city, Constant.apikey)
            if(response.isSuccessful)
                Log.i("Response ", response.body().toString())
            else
                Log.i("Error ", response.message())

            Log.i("Città Cercata: ", city)
        }
    }
}