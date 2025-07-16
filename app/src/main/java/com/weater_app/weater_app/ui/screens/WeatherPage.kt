package com.weater_app.weater_app.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.weater_app.weater_app.data.api.NetWorkResponse
import com.weater_app.weater_app.data.models.WeatherViewModel
import com.weater_app.weater_app.ui.components.CreateChart
import com.weater_app.weater_app.ui.components.WeatherAttributes
import com.weater_app.weater_app.ui.components.WeatherMainCard
import com.weater_app.weater_app.ui.components.WeatherTopBar
import com.weater_app.weater_app.ui.components.WeatherWarning


@Composable
fun WeatherPage(navController: NavController, viewModel: WeatherViewModel) {

    WeatherTopBar(navController)

    val city by remember {
        mutableStateOf("")
    }

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(city) {
        viewModel.getWeather(city)
    }


    Column {
        when{
            uiState.isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }

            uiState.errorMessage != null -> {
                Text(
                    text = "Errore: ${uiState.errorMessage}",
                    color = Color.Red,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Button(
                    onClick = { viewModel.getWeather(city, forceRefresh = true) },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text("Riprova")
                }
            }
            uiState.weatherData != null -> {
                val data = uiState.weatherData

                Spacer(modifier = Modifier.height(32.dp))
                data?.let {
                    WeatherMainCard(it.city, it.temperature)
                    Spacer(modifier = Modifier.height(32.dp))
                    WeatherWarning()
                    Spacer(modifier = Modifier.height(40.dp))
                    CreateChart()
                    Spacer(modifier = Modifier.height(40.dp))
                    WeatherAttributes(data.humidity, data.windSpeed, data.pressure, data.visibility)
                }
            }
        }
    }




}
