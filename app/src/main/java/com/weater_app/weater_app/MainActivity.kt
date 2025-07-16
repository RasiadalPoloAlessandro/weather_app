package com.weater_app.weater_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.weater_app.weater_app.data.api.weatherApi.WeatherDependency
import com.weater_app.weater_app.data.models.WeatherViewModel
import com.weater_app.weater_app.navigation.Routes
import com.weater_app.weater_app.ui.screens.CitySelectionPage
import com.weater_app.weater_app.ui.screens.WeatherPage
import com.weater_app.weater_app.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val viewModelFactory = WeatherDependency.provideWeatherViewModelFactory()
        val weatherViewModel = ViewModelProvider(this, viewModelFactory)[WeatherViewModel::class.java]
        setContent {
            MyApplicationTheme {

                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Routes.weatherPage
                ) {
                    composable(Routes.weatherPage) {
                        WeatherPage(navController, weatherViewModel)
                    }

                    composable(Routes.weather_CitySelection) {
                        CitySelectionPage(navController, weatherViewModel)
                    }
                }
            }
        }
    }
}