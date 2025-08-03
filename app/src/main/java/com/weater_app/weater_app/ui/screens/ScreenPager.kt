package com.weater_app.weater_app.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.weater_app.weater_app.data.models.WeatherViewModel

@Composable
fun ScreenPager(navController: NavController, viewModel: WeatherViewModel) {
    //pageCount is based on how many cities are stored in cache
    val pageCount = viewModel.getTotalPages()

    val pagerState = rememberPagerState(pageCount = { pageCount })

    HorizontalPager(
        state = pagerState,
        modifier = Modifier.fillMaxSize()
    ) { page ->
        when (page) {
            0 -> {
                //Current position
                WeatherPage(navController, viewModel)
            }
            else -> {
                // Other cities
                val weatherData = viewModel.getWeatherDataByIndex(page)

                Log.i("Pagine totali: ", pageCount.toString())
                Log.i("City Index: ", page.toString())
                Log.i("Weather Data: ", weatherData?.city ?: "null")

                WeatherPage(navController, viewModel, preloadedWeatherData = weatherData)
            }
        }
    }
}