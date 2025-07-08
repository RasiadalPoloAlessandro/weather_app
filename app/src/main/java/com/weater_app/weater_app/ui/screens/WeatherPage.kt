package com.weater_app.weater_app.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.weater_app.weater_app.data.models.WeatherViewModel
import com.weater_app.weater_app.ui.components.CreateChart
import com.weater_app.weater_app.ui.components.WeatherAttributes
import com.weater_app.weater_app.ui.components.WeatherMainCard
import com.weater_app.weater_app.ui.components.WeatherTopBar
import com.weater_app.weater_app.ui.components.WeatherWarning


@Composable
fun WeatherPage(navController: NavController, viewModel: WeatherViewModel) {
    var city by remember {
        mutableStateOf("")
    }


    Column {
        WeatherTopBar(navController)
        Spacer(modifier = Modifier.height(32.dp))
        WeatherMainCard()
        Spacer(modifier = Modifier.height(32.dp))
        WeatherWarning()
        Spacer(modifier = Modifier.height(40.dp))
        CreateChart()
        Spacer(modifier = Modifier.height(40.dp))
        WeatherAttributes()
    }
}
