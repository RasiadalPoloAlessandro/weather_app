package com.weater_app.weater_app.data.models

import android.util.Log
import androidx.lifecycle.ViewModel

class WeatherViewModel : ViewModel() {

    fun getData(city : String){

        Log.i("Selected city ", city)
    }
}